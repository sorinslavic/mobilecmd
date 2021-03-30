package com.sorin.mobilecmd.desktopcmd.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;

public class ReadStreamThread extends Thread {
	private static final Logger log = Logger.getLogger(ReadStreamThread.class);
	
	private InputStream stream;
	private String message = "";
	
	public ReadStreamThread(InputStream stream) {
		this.stream = stream;
	}
	
	public void run() {
		int BUFFER_SIZE = 4096;
		char[] buffer = new char[BUFFER_SIZE];
		int n;
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			while ((n = reader.read(buffer)) != -1) {
				sb.append(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		
		message = sb.toString();
		
		log.debug("run - the message is: " + message);
	}
	
	public String getMessage() {
		log.debug("getMessage - the message is: " + message);
		return message;
	}

}
