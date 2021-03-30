package com.sorin.mobilecmd.desktop;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.ParameterParser;

@Controller
public class GetClientFilesController {
private static final Logger log = Logger.getLogger(GetClientFilesController.class);
	
	private final UserService userService;
	
	@Autowired
	public GetClientFilesController(UserService userService) {
		this.userService = userService;
	}
		
	/**
	 * Get the files shared for this computer.
	 */
	@RequestMapping(value="/getClientFiles.cmd", method=RequestMethod.GET)
	public HttpServletResponse getClientFiles(HttpServletRequest request, HttpServletResponse response) {
		int clientId = ParameterParser.getIntParameter(request, "clientId");

		ObjectOutputStream oos = null;
		log.debug("getClientFiles - clientId: " + clientId);
		try {
			List<ClientFile> clientFileList = userService.getClientFiles(clientId);
			if (clientFileList == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			} else {
				response.setContentType("text/html; charset=utf-8");
				response.setStatus(HttpStatus.OK.value());
				oos = new ObjectOutputStream(response.getOutputStream());
				oos.writeObject(clientFileList);
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