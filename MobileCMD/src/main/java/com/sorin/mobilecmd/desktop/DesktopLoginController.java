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

import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.ParameterParser;


@Controller
public class DesktopLoginController {
private static final Logger log = Logger.getLogger(DesktopLoginController.class);
	
	private final UserService userService;
	
	@Autowired
	public DesktopLoginController(UserService userService) {
		this.userService = userService;
	}
		
	/**
	 * Try and login into the application.  
	 */
	@RequestMapping(value="/login.cmd", method=RequestMethod.POST)
	public HttpServletResponse login(HttpServletRequest request, HttpServletResponse response) {
		String username = ParameterParser.getStringParameter(request, "username");
		String password = ParameterParser.getStringParameter(request, "password");

		ObjectOutputStream oos = null;
		log.debug("login - username: " + username + " / password: " + password);
		try {
			
			User user = userService.login(username, password);
			if (user == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			} else {
				response.setContentType("text/html; charset=utf-8");
				response.setStatus(HttpStatus.OK.value());
				oos = new ObjectOutputStream(response.getOutputStream());
				oos.writeObject(user);
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
