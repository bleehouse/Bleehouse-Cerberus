package com.bleehouse.Cerberus.controller.rest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${cerberus.route.index}")
public class IndexController {
	
	  @RequestMapping(method = RequestMethod.GET)
	  //@PreAuthorize("hasRole('ADMIN')")
	  @PreAuthorize("@securityService.hasProtectedAccess()")
	  public String index(HttpServletRequest request) {
	    return request.getSession().getId();
	  }	

}
