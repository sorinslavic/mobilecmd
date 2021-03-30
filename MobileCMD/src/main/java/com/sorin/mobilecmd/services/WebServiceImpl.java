package com.sorin.mobilecmd.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sorin.mobilecmd.xml.schema.Client;
import com.sorin.mobilecmd.xml.schema.ClientFile;
import com.sorin.mobilecmd.xml.schema.GetClientFilesRequest;
import com.sorin.mobilecmd.xml.schema.GetClientsRequest;
import com.sorin.mobilecmd.xml.schema.LoginRequest;
import com.sorin.mobilecmd.xml.schema.LoginResponse;
import com.sorin.mobilecmd.xml.schema.ObjectFactory;
import com.sorin.mobilecmd.xml.schema.RunCommand;
import com.sorin.mobilecmd.xml.schema.RunResponse;

@Service
public class WebServiceImpl implements WebService {
	private static final Logger log = Logger.getLogger(WebServiceImpl.class);
	
	private final JdbcTemplate jdbc;
	
	@Autowired
	public WebServiceImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		log.debug("login - username: " + loginRequest.getUsername() + " / password: " + loginRequest.getPassword());
		Object[] inParams = {loginRequest.getUsername(), loginRequest.getPassword()};
		String sql = "select UserID, Username, Password, FirstName, LastName " +
					" from Users" +
					" where UPPER(UserName) = UPPER(?) " +
					"   and Password = ? ";
		try {			
			return jdbc.queryForObject(sql, new RowMapper<LoginResponse>() {

				@Override
				public LoginResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					LoginResponse response = new LoginResponse();
					
					response.setUserID(rs.getInt("UserID"));
					response.setUsername(rs.getString("Username"));
					response.setPassword(rs.getString("Password"));
					response.setFirstName(rs.getString("FirstName"));
					response.setLastName(rs.getString("LastName"));
					
					return response;
				}
			}, inParams);
		} catch (EmptyResultDataAccessException noUser) {
			log.debug("login - could not login username: " + loginRequest.getUsername() + " with password: " + loginRequest.getPassword());
			throw noUser;
		}
	}

	@Override
	public List<Client> getClients(GetClientsRequest getClientsRequest) {
		log.debug("getClients - userID: " + getClientsRequest.getUserID());
		String sql = "select ClientID, IpAddress, ComputerName, AllowCommands " +
					" from Clients " +
					" where UserID = ? " +
					" order by ComputerName";
		return jdbc.query(sql, new RowMapper<Client>() {

			@Override
			public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
				Client response = new Client();
				
				response.setClientID(rs.getInt("ClientID"));
				response.setIpAddress(rs.getString("IpAddress"));
				response.setComputerName(rs.getString("ComputerName"));
				response.setAllowCommands(rs.getBoolean("AllowCommands"));
				
				return response;
			}
		}, getClientsRequest.getUserID());
	}

	@Override
	public List<ClientFile> getClientFiles(GetClientFilesRequest getClientFilesRequest) {
		log.debug("getClientFiles - clientID: " + getClientFilesRequest.getClientID());
		String sql = "select ClientFileID, Path, IsFolder, IsRecursive" +
					" from ClientFiles " +
					" where ClientID = ? " +
					" order by UPPER(Path)";
		return jdbc.query(sql, new RowMapper<ClientFile>() {

			@Override
			public ClientFile mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientFile response = new ClientFile();
				
				response.setClientFileID(rs.getInt("ClientFileID"));
				response.setFilePath(rs.getString("Path"));
				response.setFolder(rs.getBoolean("IsFolder"));
				response.setRecursive(rs.getBoolean("IsRecursive"));
				
				return response;
			}
		}, getClientFilesRequest.getClientID());
	}

	@Override
	public RunResponse runCommand(RunCommand request) throws Exception {
		log.debug("runCommand - request " + request);
		RunResponse rr = (new ObjectFactory()).createRunResponse();
		try {
			log.debug("runCommand - conect to client: " + request.getIpAddress());
			Socket callClient = new Socket(request.getIpAddress(), 44100);
			log.debug("runCommand - connected: " + callClient);
			
			DataOutputStream outputStream = new DataOutputStream(callClient.getOutputStream());

			log.debug("runCommand - writer is ok!" + outputStream);
			log.debug("runCommand - we will write: " + request.getCommand());
			outputStream.writeUTF(request.getCommand());
			outputStream.flush();
			
			log.debug("runCommand - flushed");
			
			String returnMessage = "No Message:(";
			try {
				DataInputStream inputStream = new DataInputStream(callClient.getInputStream());
				returnMessage = inputStream.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage = "1;Failed to read the return message! " + e.getMessage();
			}
			
			log.debug("runCommand - the response message is: " + returnMessage);
			
			int separator = returnMessage.indexOf(";");
			if (separator > -1) {
				rr.setSuccess((returnMessage.substring(0, separator).equals("0")));
				rr.setMessage(returnMessage.substring(separator + 1));
			} else {
				rr.setSuccess(false);
				rr.setMessage("Failed to read the message!");
			}
			
		} catch (Exception e) {
			throw new Exception("The desktop was unresponsive! " + e.getMessage());
		} 
		
		return rr;
	}
}
