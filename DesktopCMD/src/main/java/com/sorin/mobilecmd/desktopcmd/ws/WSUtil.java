package com.sorin.mobilecmd.desktopcmd.ws;

import java.net.InetAddress;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.services.RemoteService;

public class WSUtil {
	private static final Logger log = Logger.getLogger(WSUtil.class);

	private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static final RemoteService remoteService = (RemoteService) applicationContext.getBean("httpInvoker");
		
	public static boolean login(String username, char[] password) throws Exception {
		log.debug("login - try and login with username and password: " + username + " " + password.toString());
		HttpRequestObject loginRequest = new LoginRequest(username, password);
		
		log.debug("login - executing");
		loginRequest.execute();
		log.debug("login - after executing");
		
		if (!loginRequest.isOK()) {
			log.debug("login - not OK! ------- EXIT");
			throw new Exception("Could not login to the account; Please try again");
		}
		
		log.debug("login - OK! ------- get user");
		User user = loginRequest.getResponseObject();
		log.debug("login - user is: " + user.getDisplayName());
		Session.getSession().setUser(user);
		
		log.debug("login - registerComputer");
		registerComputer();
		log.debug("login - after registerComputer");
		log.debug("login - getClientFiles");
		getClientFiles();
		log.debug("login - after getClientFiles");
		
		return true;
	}
	
	public static void registerComputer() throws Exception {
		log.debug("registerComputer - try and register this computer with ip: " + InetAddress.getLocalHost().getHostAddress());
		HttpRequestObject registerComputerRequest = new RegisterComputerRequest(InetAddress.getLocalHost().getHostAddress(), InetAddress.getLocalHost().getHostName());
		
		log.debug("registerComputer - executing");
		registerComputerRequest.execute();
		log.debug("registerComputer - after executing");
				
		if (!registerComputerRequest.isOK()) {
			log.debug("registerComputer - not OK! ------- EXIT");
			throw new Exception("Could not register this account; Please try again");
		}
		
		log.debug("registerComputer - OK! ------- get client");		
		Client client = registerComputerRequest.getResponseObject();
		log.debug("registerComputer - client is: " + client.getComputerName());
		Session.getSession().setClient(client);
	}
	
	public static void getClientFiles() throws Exception {
		int clientId = Session.getSession().getClientId();
		log.debug("getClientFiles - try and get the client files for clientId: " + clientId);
		log.debug("getClientFiles - before Service Call");
		List<ClientFile> files = remoteService.getClientFiles(clientId);
		log.debug("getClientFiles - after Service Call");
		log.debug("getClientFiles - we have number of files: " + files.size());
		
		Session.getSession().setFiles(files);
	}
	
	public static boolean saveConfiguration(boolean allowCmd, List<ClientFile> files) throws Exception {
		int clientId = Session.getSession().getClientId();
		log.debug("saveConfiguration - try and save this configuration for with: ");
		log.debug("saveConfiguration - clientId: " + clientId);
		log.debug("saveConfiguration - allowCmd: " + allowCmd);
		log.debug("saveConfiguration - files: " + files.size());
		
		log.debug("saveConfiguration - before Service Call");
		boolean response = remoteService.saveConfiguration(clientId, allowCmd, files);
		log.debug("saveConfiguration - the response was " + response);
		
		return response;
	}
}

