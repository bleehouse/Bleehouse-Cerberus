package com.bleehouse.controller.rest;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bleehouse.model.json.request.AuthenticationRequest;
import com.bleehouse.model.json.response.AuthenticationResponse;
import com.bleehouse.model.security.CerberusUser;
import com.bleehouse.security.TokenUtils;

@RestController
@RequestMapping("${bleehouse.route.authentication}")
public class AuthenticationController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${bleehouse.token.header}")
  private String tokenHeader;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenUtils tokenUtils;

  @Autowired
  private UserDetailsService userDetailsService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) throws AuthenticationException {

    // Perform the authentication
    Authentication authentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),
        authenticationRequest.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Reload password post-authentication so we can generate token
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    
    // Determine device type from User-Agent header or default to "web"
    String userAgent = request.getHeader("User-Agent");
    String deviceType = determineDeviceType(userAgent);
    String token = this.tokenUtils.generateToken(userDetails, deviceType);

    // Return the token
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }
  
  private String determineDeviceType(String userAgent) {
    if (userAgent == null) return "web";
    userAgent = userAgent.toLowerCase();
    if (userAgent.contains("mobile")) return "mobile";
    if (userAgent.contains("tablet")) return "tablet";
    return "web";
  }

  @RequestMapping(value = "${bleehouse.route.authentication.refresh}", method = RequestMethod.GET)
  public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
    String token = request.getHeader(this.tokenHeader);
    String username = this.tokenUtils.getUsernameFromToken(token);
    CerberusUser user = (CerberusUser) this.userDetailsService.loadUserByUsername(username);
    if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
      String refreshedToken = this.tokenUtils.refreshToken(token);
      return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }

}
