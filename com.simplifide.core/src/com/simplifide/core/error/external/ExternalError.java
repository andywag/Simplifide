/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error.external;

import com.simplifide.base.basic.struct.NodePosition;

public class ExternalError {
	
	private NodePosition position;
	private String message;
	private int severity;
	private int errorCode;
	
	public ExternalError(NodePosition position, String message) {
		this.position = position;
		this.message = message;
	}
	
	public ExternalError(int line,
			 int col,
			 int length, 
			 int severity,
			 String message) {
		
		this.position = new NodePosition(-1,length-1,line,col);
		this.severity = severity;
		this.message = message;
		
	}
	
	public void setPosition(NodePosition position) {
		this.position = position;
	}
	public NodePosition getPosition() {
		return position;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getSeverity() {
		return severity;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	
	
	
}
