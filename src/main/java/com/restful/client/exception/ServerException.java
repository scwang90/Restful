package com.restful.client.exception;


import com.restful.client.api.ErrorMessage;
import com.restful.exception.RestfulException;

public class ServerException extends RestfulException {

	private static final long serialVersionUID = 3153750355951678657L;

	public ServerException(ErrorMessage message) {
		super(message.getErrorMessage());
	}
	
	public ServerException(String message) {
		super(message);
	}

	public ServerException(String msg, Throwable e) {
		super(msg, e);
	}

}
