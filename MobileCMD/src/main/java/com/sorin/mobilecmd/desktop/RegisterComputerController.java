package com.sorin.mobilecmd.desktop;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.ParameterParser;

@Controller
public class RegisterComputerController {
private static final Logger log = Logger.getLogger(RegisterComputerController.class);
	
	private final UserService userService;
	
	@Autowired
	public RegisterComputerController(UserService userService) {
		this.userService = userService;
	}
		
	/**
	 * A desktop client has logged in into the application. We must add it in the user client list; 
	 */
	@RequestMapping(value="/registerComputer.cmd", method=RequestMethod.POST)
	public HttpServletResponse registerComputer(HttpServletRequest request, HttpServletResponse response) {
		int userId = ParameterParser.getIntParameter(request, "userId");
		String ipAddress = ParameterParser.getStringParameter(request, "ipAddress");
		String computerName = ParameterParser.getStringParameter(request, "computerName");

		ObjectOutputStream oos = null;
		log.debug("registerComputer - userId: " + userId + " / ipAddress: " + ipAddress + " / computerName: " + computerName);
		try {
			Client client = userService.registerComputer(userId, ipAddress, computerName);
			if (client == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			} else {
				response.setContentType("text/html; charset=utf-8");
				response.setStatus(HttpStatus.OK.value());
				oos = new ObjectOutputStream(response.getOutputStream());
				oos.writeObject(client);
				oos.flush();
				
			}
		} catch (IOException e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} finally {
			try {
				if (oos != null)
					oos.close();
			}
			catch (IOException ex) {}
		}
		
		return null;
	}
}
