package com.naleen.expensetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naleen.expensetrackerapi.model.AuthModel;
import com.naleen.expensetrackerapi.model.JwtResponse;
import com.naleen.expensetrackerapi.model.User;
import com.naleen.expensetrackerapi.model.UserModel;
import com.naleen.expensetrackerapi.security.CustomUserDetailService;
import com.naleen.expensetrackerapi.service.UserService;
import com.naleen.expensetrackerapi.util.JwtTokenUtil;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {

		// Authenticate
		// Authentication authentication =
		// authenticate(authModel.getEmail(),authModel.getPassword());
		// Store in Security context
		// SecurityContextHolder.getContext().setAuthentication(authentication);
		
		authenticate(authModel.getEmail(),authModel.getPassword());
		
		// generate JWT token
		
		// first get the user details
		
		final UserDetails userDetails = userDetailService.loadUserByUsername(authModel.getEmail());
		
		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception  {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException ex) {
			throw new Exception("User is disabled");
		} catch (BadCredentialsException ex) {
			throw new Exception("Bad Credentials");
		} catch (Exception ex) {
			throw new Exception("Exception occured");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {

		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}

}
