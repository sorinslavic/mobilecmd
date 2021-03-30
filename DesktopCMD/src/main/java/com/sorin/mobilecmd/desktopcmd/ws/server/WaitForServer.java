package com.sorin.mobilecmd.desktopcmd.ws.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class WaitForServer implements Runnable {
	private static final Logger log = Logger.getLogger(WaitForServer.class);

	@Override
	public void run() {
		log.debug("run - start wait for server");
		try {
			ServerSocket listener = new ServerSocket(44100);
			Socket socket = null;
			
			log.debug("run - listening to: " + listener);
			while(true) {
				log.debug("run - waiting for client ...");
				socket = listener.accept();
				
				log.debug("run - new client !!! " + socket);
				Thread t = new Thread(new RunCommandThread(socket));
				t.start();
				log.debug("run - started new thread ... wait?");
			}
			
		} catch (IOException e) {
			log.debug("run- Failed to create the server socket: " + e.getMessage());
			e.printStackTrace();
		}		
	}

}
