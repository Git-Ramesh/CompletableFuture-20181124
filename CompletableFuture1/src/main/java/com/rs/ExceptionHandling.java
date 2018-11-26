package com.rs;

import java.util.concurrent.CompletableFuture;

public class ExceptionHandling {
	public static void main(String[] args) {
		System.out.println("main: Thread? " + Thread.currentThread());
		CompletableFuture<String> future = CompletableFuture.supplyAsync(ExceptionHandling::getMessage);
		future.exceptionally(err -> {
			System.out.println("Error: " + err.getMessage());
			throw new RuntimeException("Errorr");
		}).handle((res, err) -> {
			if(res != null) {
				return res;
			} else {
				return err.getMessage();
			}
		}).thenAccept(ExceptionHandling::printIt);
//		CompletableFuture.supplyAsync(() -> {
//			throw new IllegalArgumentException("Invalid number 2");
//
//		}).exceptionally(th -> {
//			throw new RuntimeException("ABC");
//		}).handle((msg, err) -> {
//			if(msg != null) {
//				return msg;
//			} else {
//				return err.getMessage();
//			}
//		}).thenAccept(err -> System.out.println("Error: " + err));
		System.out.println("main: Thread? " + Thread.currentThread() + " release");
		sleep(10000);
	}

	public static String getMessage() {
		System.out.println("getMessage: Thread? " + Thread.currentThread());
		throw new RuntimeException("RE: by Ramesh");
//		sleep(200);
//		return "Hello!";
	}

	public static void printIt(String msg) {
		System.out.println("printIt: Thread? " + Thread.currentThread());
		sleep(200);
		System.out.println(msg);
	}

	private static final void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
