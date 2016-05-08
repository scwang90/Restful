package com.scwang.restful.exception;


import com.scwang.restful.api.ErrorMessage;

public class ServerException extends RestfulException{

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
