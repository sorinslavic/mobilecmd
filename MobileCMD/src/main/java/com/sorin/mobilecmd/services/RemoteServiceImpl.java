package com.sorin.mobilecmd.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.data.mappers.ClientFileRowMapper;

@Service
public class RemoteServiceImpl implements RemoteService {
	private static final Logger log = Logger.getLogger(RemoteServiceImpl.class);
	
	private final JdbcTemplate jdbc;
		
	@Autowired
	public RemoteServiceImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
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


	@Transactional
	@Override
	public boolean saveConfiguration(int clientId, boolean allowCmd, List<ClientFile> files) {
		log.debug("saveConfiguration - clientId: " + clientId + " allowCmd: " + allowCmd);
		log.debug("saveConfiguration - no of files: " + files.size());
		try {
			int allowCommands = (allowCmd) ? 1 : 0;			
			Object[] updateParams = {allowCommands, clientId};
			jdbc.update("update Clients set AllowCommands = ? where ClientID = ?", updateParams);
			
			jdbc.update("delete from ClientFiles where ClientID = ?", clientId);
			
			for (ClientFile file : files) {
				Object[] insertParams = {clientId, file.getPath(), file.isFolder() ? 1 : 0, file.isRecursive() ? 1 : 0, file.getDateAdded(), file.getLastAccessDate()};
				jdbc.update("insert into ClientFiles (ClientID, Path, IsFolder, IsRecursive, DateAdded, LastAccessDate) " +
						" values (?, ?, ?, ?, ?, ?) ", insertParams);
			}
		} catch (Exception e) {
			log.debug("saveConfiguration - failed with exception: " + e.getMessage());
			return false;
		}
		
		return true;
	}
}