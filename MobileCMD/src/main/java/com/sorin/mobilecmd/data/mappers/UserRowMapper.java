package com.sorin.mobilecmd.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sorin.mobilecmd.data.User;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		
		user.setUserID(rs.getInt("UserID"));
		user.setUserName(rs.getString("Username"));
		user.setPassword(rs.getString("Password"));
		user.setDateJoined(rs.getDate("DateJoined"));
		user.setFirstName(rs.getString("FirstName"));
		user.setLastName(rs.getString("LastName"));
		
		return user;
	}
}