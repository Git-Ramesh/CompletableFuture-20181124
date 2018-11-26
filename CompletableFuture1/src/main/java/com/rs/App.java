package com.rs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * 
 * @author Ramesh
 */
public class App {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("main start...");
		Thread.sleep(3000);
		CompletableFuture<Integer> completableFuture = calcAsync();
		System.out.println("main end...");
		Integer result = completableFuture.get();
		System.out.println("End");
	}

	public static CompletableFuture<Integer> calcAsync() {
		System.out.println("calcAsync: Thread? " + Thread.currentThread());
		CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(500);
			completableFuture.completedFuture(2);
			return null;
		});
		return completableFuture;
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
