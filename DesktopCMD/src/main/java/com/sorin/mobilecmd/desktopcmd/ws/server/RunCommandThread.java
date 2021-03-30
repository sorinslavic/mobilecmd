package com.sorin.mobilecmd.desktopcmd.ws.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.sorin.mobilecmd.desktopcmd.util.RuntimeResponse;

public class RunCommandThread implements Runnable {
	private static final Logger log = Logger.getLogger(RunCommandThread.class);
	
	private final Socket socket;
	
	public RunCommandThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		log.debug("run - connection with client: " + socket);
		try {
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					   
			log.debug("run - solved both streams! - connection is OK");

			String command = inputStream.readUTF();
			log.debug("run - received command is: " + command);
			
			log.debug("run - running command!");
			RuntimeResponse rr = RuntimeResponse.run(command);						
			log.debug("run - ran command!");
			
			int exitValue = rr.getExitValue();
			String response = rr.getResponse();
			
			log.debug("run - command response !! value: " + exitValue);
			log.debug("run - command response !! response: " + response);
			
			String responseString = exitValue + ";" + response;
		    outputStream.writeUTF(responseString);
		    
		    outputStream.flush();
		    
		    log.debug("run - sent message back with outputStream");
		    
		    log.debug("run - closing streams");
		    inputStream.close();
		    outputStream.close();
		    log.debug("run - closed streams");
		} catch (IOException e) {
			log.debug("run - OUPS!!! - a phone tried to call us:( " + e.getMessage());
			// we should really log these exceptions :(
			e.printStackTrace();
		}
		
		 log.debug("run - THE END - for client: " + socket);
	}
}
