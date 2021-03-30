package com.sorin.mobilecmd.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.data.Device;
import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.data.mappers.ClientFileRowMapper;
import com.sorin.mobilecmd.data.mappers.ClientRowMapper;
import com.sorin.mobilecmd.data.mappers.DeviceRowMapper;
import com.sorin.mobilecmd.data.mappers.UserRowMapper;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	private final JdbcTemplate jdbc;
	
	@Autowired
	public UserServiceImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public User login(String username, String password) {
		log.debug("login - username: " + username + " / password: " + password);
		Object[] inParams = {username, password};
		String sql = "select UserID, Username, Password, DateJoined, " +
					"	FirstName, LastName " +
					" from Users" +
					" where UPPER(UserName) = UPPER(?) " +
					"   and Password = ? ";
		try {			
			return jdbc.queryForObject(sql, new UserRowMapper(), inParams);
		} catch (EmptyResultDataAccessException noUser) {
			log.debug("login - could not login username: " + username + " with password: " + password);
			return null;
		}
	}

	@Override
	public int getUserId(String username) {
		log.debug("getUserId - username: " + username);
		try {
			return jdbc.queryForInt("select UserID from Users where UPPER(UserName) = UPPER(?)", username);
		} catch (EmptyResultDataAccessException noUser) {
			log.debug("getUserId - no user exists for username: " + username);
			return -1;
		}
	}

	@Transactional
	@Override
	public User registerUser(User user) {
		Object[] inParams = {user.getUserName(), user.getPassword(), user.getFirstName(), user.getLastName()};
		jdbc.update("insert into Users (UserName, Password, DateJoined, FirstName, LastName) " +
				" values (?, ?, CURRENT_TIMESTAMP, ?, ?)", inParams);
		
		
		user.setUserID(jdbc.queryForInt("select last_insert_id()"));
		return user;
	}

	@Override
	public void addClientDownload(String ipAddress) {
		jdbc.update("insert into Downloads(IpAddress) values (?)", ipAddress);
	}

	@Override
	public int getDownloadCount() {
		return jdbc.queryForInt("select max(DownloadCount) from Downloads");
	}

	@Override
	public List<Device> getDeviceList(int userID) {
		log.debug("getDeviceList - userID: " + userID);
		String sql = "select DeviceID, DeviceIMEI, UserID, DeviceType, DeviceName, LastAccessDate, PhoneNumber " +
					" from Devices " +
					" where UserID = ? " +
					" order by LastAccessDate desc";
		return jdbc.query(sql, new DeviceRowMapper(), userID);
	}


	@Override
	public Client getClient(int clientId) {
		log.debug("getClient - clientId: " + clientId);
		String sql = "select ClientID, UserID, IpAddress, ComputerName, " +
							" DateInstalled, LastAccessDate, AllowCommands " +
					" from Clients " +
					" where ClientID = ? ";
		try {
			return jdbc.queryForObject(sql, new ClientRowMapper(), clientId);
		} catch (EmptyResultDataAccessException noUser) {
			log.debug("getClient - no user client for clientId: " + clientId);
			return null;
		}		
	}
	
	@Override
	public List<Client> getClientList(int userID) {
		log.debug("getClientList - userID: " + userID);
		String sql = "select ClientID, UserID, IpAddress, ComputerName, " +
							" DateInstalled, LastAccessDate, AllowCommands " +
					" from Clients " +
					" where UserID = ? " +
					" order by LastAccessDate desc";
		return jdbc.query(sql, new ClientRowMapper(), userID);
	}

	@Override
	public Client registerComputer(int userId, String ipAddress, String computerName) {
		log.debug("registerComputer - userId: " + userId + " / ipAddress: " + ipAddress + " / computerName: " + computerName);
		int currentClientId = -1;
		try {
			Object[] selectParams = {userId, ipAddress};
			currentClientId = jdbc.queryForInt("select ClientID from Clients where UserID = ? and IpAddress = ?", selectParams);
		} catch (EmptyResultDataAccessException noUser) {
			log.debug("registerComputer - no client registered for this computer");
			currentClientId = -1;
		}
		
		if (currentClientId < 0) {
			Object[] insertParams = {userId, ipAddress, computerName};
			jdbc.update("insert into Clients (UserID, IpAddress, ComputerName, DateInstalled, LastAccessDate, AllowCommands) " +
						" values (?, ?, ?, CURRENT_DATE, null, null) ", insertParams);
			currentClientId = jdbc.queryForInt("select last_insert_id()");			
		} else {
			Object[] updateParams = {computerName, currentClientId};
			jdbc.update("update Clients set ComputerName = ? where ClientID = ?", updateParams);
		}
		
		return getClient(currentClientId);
	}

	@Override
	public List<ClientFile> getClientFiles(int clientId) {
		log.debug("getClientFiles - clientId: " + clientId);
		
		String sql = "select ClientFileID, ClientID, Path, IsFolder, IsRecursive, DateAdded, LastAccessDate " +
				" from ClientFiles " +
				" where ClientID = ? " +
				" order by Path ";
		return jdbc.query(sql, new ClientFileRowMapper(), clientId);		
	}

	@Override
	public String getClientName(int clientId) {
		log.debug("getClientName - clientId: " + clientId);
		
		return jdbc.queryForObject("select ComputerName from Clients where ClientID = ?", String.class, clientId);
	}

}
	
