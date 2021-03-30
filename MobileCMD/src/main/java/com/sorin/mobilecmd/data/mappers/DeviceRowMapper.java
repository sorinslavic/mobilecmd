package com.sorin.mobilecmd.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sorin.mobilecmd.data.Device;

public class DeviceRowMapper implements RowMapper<Device> {
	@Override
	public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
		Device device = new Device();
			
		device.setDeviceId(rs.getInt("DeviceID"));
		device.setDeviceImei(rs.getLong("DeviceIMEI"));
		device.setUserId(rs.getInt("UserID"));
		device.setDeviceType(rs.getString("DeviceType"));
		device.setDeviceName(rs.getString("DeviceName"));
		device.setLastAccessDate(rs.getDate("LastAccessDate"));
		device.setPhoneNumber(rs.getString("PhoneNumber"));
			
		return device;
	}
}
