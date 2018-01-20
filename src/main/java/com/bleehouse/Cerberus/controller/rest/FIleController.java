package com.bleehouse.Cerberus.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${cerberus.route.file}")
public class FIleController {

	  @RequestMapping(method = RequestMethod.POST)
	  @ResponseBody
	  @PreAuthorize("@securityService.hasProtectedAccess()")
	  public String upload(@RequestParam("files") MultipartFile[] file) throws Exception {
	    
		  file[0].getOriginalFilename().toString();
		  System.out.println(file[0].getOriginalFilename().toString());
		  
		  return "file " + file[0].getOriginalFilename().toString();
	  }	
	
}
