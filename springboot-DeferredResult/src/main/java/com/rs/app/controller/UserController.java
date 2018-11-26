package com.rs.app.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.app.exception.UserAlreadyAvailableException;
import com.rs.app.model.User;
import com.rs.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

//	@PostMapping("/")
//	public DeferredResult<ResponseEntity<User>> saveUser(@RequestBody User newUser) {
//		System.out.println("saveUser: Thread? " + Thread.currentThread());
//		DeferredResult<ResponseEntity<User>> deferredResult = new DeferredResult<>(30000L);
//		deferredResult.onError((Throwable th) -> {
//			System.out.println("onError..");
//			deferredResult
//					.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(th.getMessage()));
//		});
//		ForkJoinPool.commonPool().submit(() -> {
//			deferredResult.setResult(ResponseEntity.ok(userService.addUser(newUser)));
//		});
//		deferredResult.onTimeout(() -> {
//			deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
//					.body("Unable to complete the request with in the time"));
//		});
//		deferredResult.onCompletion(() -> System.out.println("Processing completed.."));
//		System.out.println("saveUser: Thread? " + Thread.currentThread() + " is free");
//		return deferredResult;
//	}

	@PostMapping("/")
	public CompletableFuture<User> saveUser(@RequestBody User newUser) {
		System.out.println("saveUser: Thread? " + Thread.currentThread());
		CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> this.userService.addUser(newUser));
//		future.exceptionally(th -> {
//			return handleException(th);
//		}).thenRunAsync(() -> {
//			System.out.println("thenRunAsync...");
//			System.out.println("attempted to add the user");
//		});
		future.handle((user, th) -> {
			if (user != null) {
				return user;
			} else {
				return handleException(th);
			}
		}).thenRunAsync(() -> {
			System.out.println("thenRunAsync...");
			System.out.println("attempted to add the user");
		});
		//future.completeExceptionally(new IllegalArgumentException("abc"));
		System.out.println("saveUser: Thread? " + Thread.currentThread() + " is free");
		return future;

	}

	private User handleException(Throwable th) {
		System.out.println("handleException..");
		// return new User();
		throw new UserAlreadyAvailableException("User might already exist");
	}
}
