package com.example.service;

import com.example.model.User;

public interface UserService {
	void saveUser(User user);

	User findByUsername(String username);

	boolean authenticate(String username, String password);

	boolean userExists(String username);

}
