package com.prf.auth;

import java.util.List;
import java.util.stream.Collectors;

import com.prf.common.JwtResponse;
import com.prf.security.JwtUtils;
import com.prf.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class AuthController{
	
	  private AuthenticationManager authenticationManager;

	  private JwtUtils jwtUtils;
	  
	  @Autowired
	  public AuthController(AuthenticationManager authenticationManager,JwtUtils jwtUtils) {
			this.authenticationManager = authenticationManager;
			this.jwtUtils = jwtUtils;
		}
	  
	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.generateJwtToken(authentication);
	    
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
	    
	    
	    List<String> permisos = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	    
	    

	    return ResponseEntity.ok(new JwtResponse(jwt, 
	                         userDetails.getId(), 
	                         userDetails.getUsername(), 
	                         permisos,
	                         userDetails.getNombre(),
	                         userDetails.getImg_perfil(),
	                         userDetails.getEmail(), 
	                         userDetails.getModulos(),
	                         userDetails.getPerfiles()
	                         ));
	    
	    // String accessToken, Long id, String username, List<String> permisos, String nombre, String imgString, 
		//String email_address , List<Modulo> modulos, List<Perfil> perfiles
	  }
	
}