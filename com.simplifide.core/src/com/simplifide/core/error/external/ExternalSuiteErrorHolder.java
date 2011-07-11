package com.simplifide.core.error.external;

import java.util.ArrayList;

public class ExternalSuiteErrorHolder {

	private ArrayList<ExternalSuiteError> errorList = new ArrayList<ExternalSuiteError>();
	
	public ExternalSuiteErrorHolder() {
		
	}

	public void clearErrorList(String name) {
		for (ExternalSuiteError error : errorList) {
			if (error.getName().equalsIgnoreCase(name)) {
				errorList.remove(error);
				break;
			}
		}
	}
	
	public void addError(String filename, String type, ExternalError error) {
		for (ExternalSuiteError error1 : errorList) {
			if (error1.getName().equalsIgnoreCase(type)) {
				error1.addExternalError(filename, error);
				return;
			}
		}
		ExternalSuiteError err = new ExternalSuiteError(type);
		err.addExternalError(filename, error);
		this.errorList.add(err);
	}
	
	public void setErrorList(ArrayList<ExternalSuiteError> errorList) {
		this.errorList = errorList;
	}

	public ArrayList<ExternalSuiteError> getErrorList() {
		return errorList;
	}
	
}
