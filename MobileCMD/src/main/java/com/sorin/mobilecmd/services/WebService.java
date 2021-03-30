package com.sorin.mobilecmd.services;

import java.util.List;

import com.sorin.mobilecmd.xml.schema.Client;
import com.sorin.mobilecmd.xml.schema.ClientFile;
import com.sorin.mobilecmd.xml.schema.GetClientFilesRequest;
import com.sorin.mobilecmd.xml.schema.GetClientsRequest;
import com.sorin.mobilecmd.xml.schema.LoginRequest;
import com.sorin.mobilecmd.xml.schema.LoginResponse;
import com.sorin.mobilecmd.xml.schema.RunCommand;
import com.sorin.mobilecmd.xml.schema.RunResponse;

/**
 * Interface that will manage all request that arrived by SOAP Web Service;
 * Do not worry about exception - unless required in response logic - the Web Service will forward any errors to the SOAP Client
 */
public interface WebService {
	
	/**
	 * This is called from the Web Service login endpoint;
	 * @param loginRequest
	 * @return
	 */
	public LoginResponse login(LoginRequest loginRequest);
	
	/**
	 * Return a list of Desktop clients for this user
	 * @param getClientsRequest
	 * @return
	 */
	public List<Client> getClients(GetClientsRequest getClientsRequest);

	/**
	 * Return a list of Files for the desired desktop client
	 * @param getClientFilesRequest
	 * @return
	 */
	public List<ClientFile> getClientFiles(GetClientFilesRequest getClientFilesRequest);

	/**
	 * Call this client and execute the Command
	 * @param request
	 * @return the System response and type - success or error
	 * @throws Exception 
	 */
	public RunResponse runCommand(RunCommand request) throws Exception;
}
