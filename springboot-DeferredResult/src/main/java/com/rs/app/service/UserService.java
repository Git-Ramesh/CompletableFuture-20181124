package com.rs.app.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.rs.app.model.User;

public interface UserService {
	User addUser(User user);
	User getUser(long id);
	List<User> getAllUsers();
	void deleteUser(long id);
}
