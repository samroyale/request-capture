package com.se.data;

/**
 * Thrown to indicate that a request key has been specified in the wrong format.
 * 
 * @author seldred
 */
public class InvalidKeyFormatException extends RuntimeException {

	private static final long serialVersionUID = 5648662261404194566L;

	public InvalidKeyFormatException(String message) {
		super(message);
	}

	public InvalidKeyFormatException(Throwable cause) {
		super(cause);
	}

	public InvalidKeyFormatException(String message, Throwable cause) {
		super(message, cause);
	}	
}
