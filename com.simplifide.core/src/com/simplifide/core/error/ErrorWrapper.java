/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class ErrorWrapper {

	public static String ERROR_ID = CoreActivator.PLUGIN_ID + ".syntaxMarker";
        
        public static String ATTRIBUTE_ERROR_LOCATION = "errorlocation";
        public static String ATTRIBUTE_SOURCE_LOCATION = "sourcelocation";
        public static String ATTRIBUTE_ERROR_TYPE = "errortype";
        
	private TopError error;
	
	public ErrorWrapper(TopError error) {
	    this.error = error;
	}
	
	private int convertSeverity() {
		int esev = error.getErrorSeverity();
		if (esev == TopError.ERROR) return IMarker.SEVERITY_ERROR;
		if (esev == TopError.WARNING) return IMarker.SEVERITY_WARNING;
		if (esev == TopError.PARSE) return IMarker.SEVERITY_INFO;
		return IMarker.SEVERITY_INFO;
	}
	
        
        protected abstract String getPostfix();
        
        private boolean checkError(int errorType) {
            
            
            String postfix = this.getPostfix();
            if (!HardwareChecker.isErrorEnabled()) {
                if (errorType == TopError.TYPE_SYNTAX) return true;
                return false;
            }
            
            switch(errorType) {
                case TopError.TYPE_SYNTAX : return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ERROR_SYNTAX + postfix);
                case TopError.TYPE_NOTDEF : return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ERROR_NOT_DEFINED + postfix);
                case TopError.TYPE_MISMATCH : return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.ERROR_TYPE_MISMATCH + postfix);
                case TopError.TYPE_LATCH : return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.WARNING_LATCH + postfix);
                case TopError.TYPE_VARIABLE_NOT_ASSIGNED : 
                    return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.WARNING_NOT_ASSIGNED + postfix);
                case TopError.TYPE_VARIABLE_NOT_USED :
                    return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.WARNING_NOT_USED + postfix);
                default : return false;
            }
            
        }
        
	public void createError(IResource resource, int sourceLocation, int errorLocation) {
		this.createErrorInternal(resource,sourceLocation,errorLocation);   
	}
	
	public void createErrorInternal(IResource resource, int sourceLocation, int errorLocation) {
		try {
			if (error.getLocation() != null) {
				ReferenceLocation loc = error.getLocation();
				int line  = loc.getLine();
				int start = loc.getDocPosition();
				int end   = loc.getDocPosition() + loc.getLength();
				IMarker marker = resource.createMarker(ERROR_ID);
				marker.setAttribute(IMarker.CHAR_START, start);
				marker.setAttribute(IMarker.CHAR_END, end);
				marker.setAttribute(IMarker.MESSAGE, error.getErrorMessage());
				marker.setAttribute(IMarker.SEVERITY, this.convertSeverity());
				marker.setAttribute(IMarker.LINE_NUMBER, line);
				marker.setAttribute(ATTRIBUTE_SOURCE_LOCATION, sourceLocation);
                marker.setAttribute(ATTRIBUTE_ERROR_LOCATION, errorLocation);
                marker.setAttribute(ATTRIBUTE_ERROR_TYPE, error.getErrorType());       
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
}
