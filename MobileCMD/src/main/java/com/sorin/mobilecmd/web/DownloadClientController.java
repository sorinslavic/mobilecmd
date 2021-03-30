package com.sorin.mobilecmd.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.MobileCMDPropertiesFacade;

@Controller
public class DownloadClientController {
	private final MobileCMDPropertiesFacade propsFacade;
	private final UserService userService;
	
	@Autowired
	public DownloadClientController(MobileCMDPropertiesFacade propsFacade, UserService userService) {
		this.propsFacade = propsFacade;
		this.userService = userService;
	}
	
	/**
	 * Download 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/downloadClient.htm", method = RequestMethod.GET)
	public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = propsFacade.getClientFilePath();
		File file = new File(realPath + filePath);
		FileInputStream fis = new FileInputStream(file);
	
		response.setHeader("Content-Disposition","attachment; filename=\"" + file.getName() +"\"");					 
		FileCopyUtils.copy(fis, response.getOutputStream());
		
		userService.addClientDownload(request.getRemoteAddr());
				
		return null;
	}
}
