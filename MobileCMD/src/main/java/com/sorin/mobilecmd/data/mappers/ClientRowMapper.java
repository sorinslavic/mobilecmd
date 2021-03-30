package com.sorin.mobilecmd.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sorin.mobilecmd.data.Client;

public class ClientRowMapper implements RowMapper<Client> {
	
	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		Client client = new Client();
			
		client.setClientId(rs.getInt("ClientID"));
		client.setUserId(rs.getInt("UserID"));
		client.setIpAddress(rs.getString("IpAddress"));
		client.setComputerName(rs.getString("ComputerName"));
		client.setDateInstalled(rs.getDate("DateInstalled"));
		client.setLastAccessDate(rs.getDate("LastAccessDate"));
		client.setAllowCommands((rs.getInt("AllowCommands") > 0));
			
		return client;
	}
}