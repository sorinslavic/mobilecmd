package com.sorin.mobilecmd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sorin.mobilecmd.data.ClientFile;
import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.ParameterParser;

@Controller
public class ClientFilesController {	
	private static final Logger log = Logger.getLogger(ClientFilesController.class);
	
	private final UserService userService;
	
	@Autowired
	public ClientFilesController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/clientFiles.htm", method = RequestMethod.GET)
	public ModelAndView getFiles(HttpServletRequest request) {
		int clientId = ParameterParser.getIntParameter(request, "clientId");
		log.debug("getFiles - get the files for clientId: " + clientId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		String computerName = userService.getClientName(clientId);
		model.put("computerName", computerName);
		
		List<ClientFile> clientFileList = userService.getClientFiles(clientId);
		model.put("clientFileList", clientFileList);
		
		return new ModelAndView("popup-clientFiles", model);
	}
}
