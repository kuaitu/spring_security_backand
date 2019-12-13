package com.axistools.utils;


public class ValidationException extends LogicException {
	private static final long serialVersionUID = 6403753824429416438L;
	
	private String msg = "";
	
	
	public ValidationException(String ...errors) {
		for (int i = 0; i < errors.length; i++) {
			msg += errors[i];
			if (i != errors.length - 1) {
				msg += ",";
			}
		}
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
}
