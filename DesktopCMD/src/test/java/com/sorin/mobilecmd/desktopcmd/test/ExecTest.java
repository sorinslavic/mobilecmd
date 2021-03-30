package com.sorin.mobilecmd.desktopcmd.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.apache.log4j.Logger;

import com.sorin.mobilecmd.desktopcmd.util.ReadStreamThread;
import com.sorin.mobilecmd.desktopcmd.util.RuntimeResponse;

public class ExecTest {
	private static final Logger log = Logger.getLogger(ExecTest.class);
	
	public static void main(String[] args) throws ExecuteException, IOException {
		Executor exe = new DefaultExecutor();
		CommandLine cmd = new CommandLine("ipconfig");
		cmd.addArgument("/all");
		
		exe.execute(cmd, new ExecuteResultHandler() {
			
			@Override
			public void onProcessFailed(ExecuteException e) {
				log.debug("Fail" + e.getMessage());
				
			}
			
			@Override
			public void onProcessComplete(int exitValue) {
				log.debug("Success" + exitValue);
				
			}
		});	
		
		System.out.println(RuntimeResponse.run("ipconfig /all"));
	}
}
