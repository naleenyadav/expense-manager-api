package com.naleen.expensetrackerapi.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.naleen.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.naleen.expensetrackerapi.exception.UserNotFoundException;
import com.naleen.expensetrackerapi.model.User;
import com.naleen.expensetrackerapi.model.UserModel;
import com.naleen.expensetrackerapi.repository.UserRepository;
import com.naleen.expensetrackerapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserModel userModel) {

		if (repository.existsByEmail(userModel.getEmail())) {
			throw new ItemAlreadyExistsException(
					"Email alrady exists in Database, Use diffent email- " + userModel.getEmail());
		}

		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);

	}

	@Override
	public User readUser() {
		Long userId  =  getLoggedInUser().getId();
		return repository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User does not exists with Id : "+userId));

	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser = readUser();
		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
		existingUser.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : existingUser.getPassword());
		existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
		return repository.save(existingUser);
	}

	@Override
	public void deleteUser() {
		
		User existingUser = readUser();
		repository.delete(existingUser);
		
	}

	@Override
	public User getLoggedInUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return repository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found with email : "+email));
	}

}
