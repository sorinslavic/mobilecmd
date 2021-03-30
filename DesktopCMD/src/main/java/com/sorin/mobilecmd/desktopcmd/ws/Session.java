package com.sorin.mobilecmd.desktopcmd.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.data.User;

public class Session {
	private static final Logger log = Logger.getLogger(Session.class);
	private final static Session session = new Session();
	private final static Map<String, Object> valueMap = new HashMap<String, Object>();
	
	private Session() {	}
	
	public static final Session getSession() {
		return session;
	}
	
	public void setUser(User user) {
		valueMap.put("user", user);
		
		log.debug("setUser - the user will be: " + user.getDisplayName());
	}
	
	public User getUser() {
		return (User)valueMap.get("user");
	}

	public void setClient(Client client) {
		valueMap.put("client", client);
		
		log.debug("setClient - the client will be: " + client.getIpAddress() + " name " + client.getComputerName());
	}
	
	public Client getClient() {
		return (Client)valueMap.get("client");
	}
	
	public int getClientId() {
		return getClient().getClientId();
	}
	
	public int getUserId() {
		return getUser().getUserID();
	}

	public void setFiles(List<ClientFile> files) {
		valueMap.put("files", files);
		
		log.debug("setFiles - we will have no of files: " + files.size());
	}
		
	@SuppressWarnings("unchecked")
	public List<ClientFile> getFiles() {
		return (List<ClientFile>) valueMap.get("files");
	}
}
