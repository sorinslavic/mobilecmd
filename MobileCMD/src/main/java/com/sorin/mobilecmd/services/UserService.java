package com.sorin.mobilecmd.services;

import java.util.List;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.data.Device;
import com.sorin.mobilecmd.data.User;

public interface UserService {

	/**
	 * Simply try to login this user to the database;
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);

	/**
	 * Get the userId for this username
	 * @param username
	 * @return the userId or -1 if no such username
	 */
	public int getUserId(String username);

	/**
	 * Try and register the user details. Insert into Users table.
	 * @param user
	 * @return
	 */
	public User registerUser(User user);

	/**
	 * The client JAR was downloaded, let's record who did it.
	 * @param ipAddress
	 */
	public void addClientDownload(String ipAddress);

	/**
	 * @return how many times the Client JAR was downloaded
	 */
	public int getDownloadCount();

	/**
	 * @param userID
	 * @return the Devices that are registered with this User
	 */
	public List<Device> getDeviceList(int userID);
	
	/**
	 * @param clientId
	 * @return the Client application;
	 */
	public Client getClient(int clientId);

	/**
	 * @param userID
	 * @return the Client applications that are linked with this User
	 */
	public List<Client> getClientList(int userID);

	/**
	 * @param userId
	 * @param ipAddress
	 * @param computerName
	 * @return insert or update an existing computer and return the Client instance
	 */
	public Client registerComputer(int userId, String ipAddress, String computerName);
	
	/**
	 * @param clientId
	 * @return the list of Files for this client computer
	 */
	public List<ClientFile> getClientFiles(int clientId);

	/**
	 * @param clientId
	 * @return the computerName for this Client
	 */
	public String getClientName(int clientId);

}
