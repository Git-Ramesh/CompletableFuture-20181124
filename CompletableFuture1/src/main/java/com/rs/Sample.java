package com.rs;

import java.util.concurrent.CompletableFuture;

public class Sample {
	public static int compute() {
		System.out.println("compute: " + Thread.currentThread());
		sleep(1000);
		return 2;
	}

	public static void printIt(int value) {
		System.out.println(value + " --- " + Thread.currentThread());
	}

	public static CompletableFuture<Integer> create() {
		System.out.println("create: Thread: " + Thread.currentThread());
		return CompletableFuture.supplyAsync(Sample::compute);
	}

//	public static void main(String[] args) {
//		//Create a pipeline to complete later
//		CompletableFuture<Integer> future = new CompletableFuture<Integer>();
//												future.thenApply(data -> data * 2)
//												.thenApply(data -> data + 1).thenAccept(Sample::printIt);
//												
//		System.out.println("Build the pipeline");
////		sleep(200);
//		future.complete(2);
//		//sleep(3000);
//												
//	}
	public static void main(String[] args) {
		System.out.println("main " + Thread.currentThread());
		CompletableFuture<Integer> future = create();
		sleep(100);
		//sleep(2000); 
		future.thenAccept(Sample::printIt);
		System.out.println("main end");
		sleep(5000);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
