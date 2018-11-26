package com.rs.app.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.app.model.User;
import com.rs.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") long id) {
		System.out.println("UserController#getUser(long id): Thread? " + Thread.currentThread());
		 User user = this.userService.getUser(id);
		 return user;
	}
	
	public CompletableFuture<Integer> getDouble(int num) {
		System.out.println("UserContoller#getDouble(int num): Thread? " + Thread.currentThread());
		CompletableFuture<Integer> future = new CompletableFuture<>();
		future.thenApply(data -> data * 2).thenAccept(System.out::println);
		return future;
	}
	

	@PostMapping("/")
	public User addUser(@RequestBody User user) throws InterruptedException, ExecutionException {
		System.out.println("UserController#addUser(user): Thread? " + Thread.currentThread());
		User savedUser = this.userService.addUser(user);
		// future.complete(user);
		int i = 0;
		while (i < 10) {
			Thread.sleep(200);
			System.out.println("Other operations by main..");
			i++;
		}
		System.out.println("got the user");
		return savedUser;
	}

	@GetMapping("/all")
	public List<User> allUsers() {
		System.out.println("");
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> inc(3));
		List<User> users = this.userService.getAllUsers();
		future.thenAccept(this::printIt);
		return users;
	}
	
	private int inc(int num) {
		System.out.println("inc: Thread? " + Thread.currentThread());
		sleep(1000);
		return num +1;
	}
	
	private void printIt(int num) {
		System.out.println("printIt: Thread? " + Thread.currentThread());
		sleep(200);
		System.out.println(num);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		this.userService.deleteUser(id);
	}
	
	private static final void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
