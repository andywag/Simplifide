/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.python.util;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.error.external.ExternalError;
import com.simplifide.core.error.external.ExternalSuiteErrorHolder;
import com.simplifide.core.project.EclipseSuite;

public final class PythonError {

	private static PythonError instance;   
	
	private ExternalSuiteErrorHolder errorHolder;
	
	private PythonError() {
		this.errorHolder = new ExternalSuiteErrorHolder();
	}
	
	public static PythonError getDefault() {
		if (instance == null) {
			instance = new PythonError();
		}
		return instance;
	}
	
	public void clearErrorList(String name) {
		this.errorHolder.clearErrorList(name);
		//this.errorHolder = new ExternalSuiteErrorHolder();
		//this.errorList = new ExternalSuiteError();
	}
	
	public void writeErrors() {
		EclipseSuite activeSuite = ActiveSuiteHolder.getDefault().getSuite();
		activeSuite.getErrorHandler().addExternalError(this.errorHolder);
	}
	
	public void addError(String type,
						 String filename, 
						 int line,
						 int col,
						 int length, 
						 int severity,
						 String message,
						 int ecode, 
						 String prefix) {
		String d;
		if (ecode == -1) {
			if (prefix == null) {
				d = "Line" + "(" + line + ") " + message; 
			}
			else {
				 d = prefix + "(" + line + ") " + message;
			}
		}
		
		else {
			d = message;
		}
			
		this.addError(type, filename, line, col, length, severity, d);
		

	}
	
	public void addError(String type,
						 String filename, 
						 int line,
						 int col,
						 int length, 
						 int severity,
						 String message) { 
		
		ExternalError ferr = new ExternalError(line,col,length,severity,message);
		this.errorHolder.addError(filename, type, ferr);
		//this.errorList.addExternalError(filename, ferr);
	}


	public void setErrorHolder(ExternalSuiteErrorHolder errorHolder) {
		this.errorHolder = errorHolder;
	}

	public ExternalSuiteErrorHolder getErrorHolder() {
		return errorHolder;
	}

	
	
	
	
}
