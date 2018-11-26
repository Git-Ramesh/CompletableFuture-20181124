package com.rs.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rs.app.exception.UserAlreadyAvailableException;
import com.rs.app.model.User;

@Service
public class UserServiceImpl implements UserService {
	private static List<User> users;
	
	static {
		UserServiceImpl.users = new ArrayList<>();
		users.add(new User(1, "Ramesh", "rameshpanthangi08@gmail.com", LocalDate.of(1995, 6, 18), "Hyderabad"));
		users.add(new User(2, "Sowmya", "sowmya.daddanala@gmail.com", LocalDate.of(1995, 9, 14), "Hyderabad"));
	}

	@Override
	public User addUser(User user) {
		System.out.println("addUser: Thread? " + Thread.currentThread());
		if(true) {
			throw new RuntimeException("Unable to add user");
		}
		System.out.println("UserServiceImpl#getUser(User user): Thread? " + Thread.currentThread());
		System.out.println("User: " + user);
		User savedUser = getUser(user.getId());
		if (savedUser == null ) {
			sleep(5000);
			UserServiceImpl.users.add(user);
			System.out.println("UserServiceImpl#getUser(User user): userSaved");
			return getUser(user.getId());
		} else {
			throw new RuntimeException("Unable to add user");
		}
	}

	@Override
	public User getUser(long id) {
		return users.stream()
					.parallel()
					.filter(user -> user.getId() == id)
					.findFirst()
					.orElse(null);

	}

	@Override
	public List<User> getAllUsers() {
		System.out.println("UserServiceImpl#getAllUsers");
		return users;
	}

	@Override
	public void deleteUser(long id) {
		User user = getUser(id);
		if (user == null) {
			throw new IllegalArgumentException("User not found!");
		} else {
			users.remove(user);
		}
	}

	private static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
