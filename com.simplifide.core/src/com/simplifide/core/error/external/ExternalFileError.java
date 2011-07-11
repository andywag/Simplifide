/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error.external;

import java.util.ArrayList;

public class ExternalFileError {
	
	private String fileName;
	private ArrayList<ExternalError> errorList = new ArrayList();
	
	public ExternalFileError(String name) {
		this.fileName = name;
		
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setErrorList(ArrayList<ExternalError> errorList) {
		this.errorList = errorList;
	}

	public ArrayList<ExternalError> getErrorList() {
		return errorList;
	}
}
