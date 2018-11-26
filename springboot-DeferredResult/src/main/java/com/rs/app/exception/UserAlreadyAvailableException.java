package com.rs.app.exception;

public class UserAlreadyAvailableException extends RuntimeException {
	private static final long serialVersionUID = 4583371998864977235L;

	public UserAlreadyAvailableException(String msg) {
		super(msg);
	}

	public UserAlreadyAvailableException(String msg, Throwable th) {
		super(msg, th);
	}
}
