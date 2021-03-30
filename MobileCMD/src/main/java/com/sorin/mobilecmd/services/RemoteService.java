package com.sorin.mobilecmd.services;

import java.util.List;

import com.sorin.mobilecmd.data.ClientFile;

public interface RemoteService {
	/**
	 * @param clientId
	 * @return the list of Files for this client computer
	 */
	public List<ClientFile> getClientFiles(int clientId);
	
	/**
	 * @param clientId
	 * @param allowCmd
	 * @param files
	 * @return true if the operation was successful / false otherwise
	 */
	public boolean saveConfiguration(int clientId, boolean allowCmd, List<ClientFile> files);
}
