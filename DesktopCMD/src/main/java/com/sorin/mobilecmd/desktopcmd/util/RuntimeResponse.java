package com.sorin.mobilecmd.desktopcmd.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.log4j.Logger;


public class RuntimeResponse {
	private static final Logger log = Logger.getLogger(RuntimeResponse.class);
	
	private final int exitValue;
	private final String response;

	public RuntimeResponse(int exitValue, String response) {
		this.exitValue = exitValue;
		this.response = response;
	}
	
	public int getExitValue() {
		return exitValue;
	}
	
	public String getResponse() {
		return response;
	}
	
	public String toString() {
		return "exitValue: " + exitValue + " and mesasge: " + response;
	}

	public static RuntimeResponse run(String command) {
		log.debug("run - run command : " + command);
		int tempExitValue = 99;
		String tempResponse = "Something (uncaught??) went wrong!";
		
		Vector<String> vcmd = new Vector<String>();
		vcmd.add("powershell.exe");
		vcmd.add("-NonInteractive");
		vcmd.add("-NoLogo");
		vcmd.add("-Command");
		vcmd.add(command);
		
		log.debug("run - Full command : " + vcmd);
		String[] cmd = new String[vcmd.size()];
	    cmd = vcmd.toArray(cmd);
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			log.debug("run - Could not getRuntime!");
			return new RuntimeResponse(5, "Could not getRuntime!");
		}
		
		// close output stream
		OutputStream os = process.getOutputStream();
		try {
			os.close();
		} catch (IOException ioe) { 
			log.debug("run - Could not close output stream");
			return new RuntimeResponse(4, "Could not close output stream");
		}
		
		// read input stream
		InputStream is = process.getInputStream();
		ReadStreamThread inputStreamThread = new ReadStreamThread(is);
		inputStreamThread.start();
		
		// read error stream	
		InputStream es = process.getErrorStream();
		ReadStreamThread errorStreamThread = new ReadStreamThread(es);
		errorStreamThread.start();
				

		log.debug("run - before exit value");
		try {
			tempExitValue = process.waitFor();
		} catch (InterruptedException ie) {
			log.debug("run - exception process.waitFor(); ------ " + ie.getMessage());
		}
		log.debug("run - the exit value is: " + tempExitValue);
		
		String inputStr = "ERROR - success string";
		log.debug("run - before exit inputStream");
		try {
			inputStreamThread.join();
			inputStr = inputStreamThread.getMessage();
			is.close();
			
			log.debug("run - the inputStreamThread value is: " + inputStr);
		} catch (Exception ex2) {
			log.debug("run - exception inputStreamThread ------ " + ex2.getMessage());
		}
		
		String errorStr = "ERROR - Something went wrong!";
		try {
			errorStreamThread.join();
			errorStr = errorStreamThread.getMessage();
			es.close();
			
			log.debug("run - the errorStreamThread value is: " + errorStr);
		} catch (Exception ex3) {
			log.debug("run - exception errorStreamThread ------ " + ex3.getMessage());
		}
		
		if (tempExitValue > 0) {
			tempResponse = errorStr;
		} else {
			tempResponse = inputStr;
			if (tempResponse.length() == 0)
				tempResponse = "Script was successfully executed!";
		}
		
		log.debug("run - returning: " + tempExitValue + " ---> " + tempResponse);
		return new RuntimeResponse(tempExitValue, tempResponse);
	}
	

	public static void main(String[] args) {
		System.out.println(run("notepad"));
	}
}

