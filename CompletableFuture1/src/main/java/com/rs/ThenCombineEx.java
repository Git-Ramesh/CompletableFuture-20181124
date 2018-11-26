package com.rs;

import java.util.concurrent.CompletableFuture;

public class ThenCombineEx {
//==================thenCombine====================
//	public static void main(String[] args) {
//		System.out.println("Main start: Thread? " + Thread.currentThread());
//		create(2).thenCombine(create(3), (result1, result2) -> result1 + result2).thenAccept(System.out::println);
//		System.out.println("End end: Thread? " + Thread.currentThread());
//	}
	public static void main(String[] args) {
		System.out.println("Main start: Thread? " + Thread.currentThread());
		//CompletableFuture<CompletableFuture<Integer>> future = create(2).supplyAsync(() -> inc(3));
		create(3).thenCompose(data -> inc(data)).thenAccept(System.out::println);
		create(3).thenCombine(inc(2), (r1, r2) -> r1 + r2).thenAccept(System.out::println);
	}
 
	private static CompletableFuture<Integer> create(int num) {
		System.out.println("create: Thread? " + Thread.currentThread());
		sleep(200);
		return CompletableFuture.supplyAsync(() -> num);
	}

	private static CompletableFuture<Integer> inc(int num) {
		sleep(1000);
		System.out.println("inc");
		return CompletableFuture.supplyAsync(() -> num + 1);
	} 

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
