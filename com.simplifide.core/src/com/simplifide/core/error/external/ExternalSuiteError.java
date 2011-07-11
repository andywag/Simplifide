/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error.external;

import java.util.ArrayList;

public class ExternalSuiteError {

	private String name;
	private ArrayList<ExternalFileError> errorList;
	
	public ExternalSuiteError(String name) {
		this.name = name;
		init();
	}
	
	
	private void init() {
		this.errorList = new ArrayList<ExternalFileError>(); 
	}

	public void addExternalError(String filename, ExternalError error) {
		for (ExternalFileError fileError : errorList) {
			if (fileError.getFileName().equals(filename)) {
				fileError.getErrorList().add(error);
				return;
			}
		}
		ExternalFileError fileError = new ExternalFileError(filename);
		fileError.getErrorList().add(error);
		this.errorList.add(fileError);
		
	}
	
	
	
	public void setErrorList(ArrayList<ExternalFileError> errorList) {
		this.errorList = errorList;
	}

	public ArrayList<ExternalFileError> getErrorList() {
		return errorList;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}
	
}
