package com.sorin.mobilecmd.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sorin.mobilecmd.data.ClientFile;

public class ClientFileRowMapper implements RowMapper<ClientFile> {
	@Override
	public ClientFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		ClientFile clientFile = new ClientFile();
		
		clientFile.setClientFileId(rs.getInt("ClientFileID"));
		clientFile.setClientId(rs.getInt("ClientID"));
		clientFile.setPath(rs.getString("Path"));
		clientFile.setFolder((rs.getInt("IsFolder") > 0));
		clientFile.setRecursive((rs.getInt("IsRecursive") > 0));
		clientFile.setDateAdded(rs.getDate("DateAdded"));
		clientFile.setLastAccessDate(rs.getDate("LastAccessDate"));
		
		return clientFile;
	}
}