/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error;

import org.eclipse.ui.IMarkerResolution;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.error.warnings.LatchWarning;

public class ErrorResolutionFactory {

    
        public static IMarkerResolution[] getMarkerResolutions(TopError error) {
        
        if (error == null) return new IMarkerResolution[0];
            
        if (error.getErrorType() == TopError.TYPE_LATCH) {
            return new IMarkerResolution[] {new LatchResolution((LatchWarning)error)};
        }
        
       
        return new IMarkerResolution[0];
       
        }
        
        public static boolean hasResolutions(int type) {
            if (type == TopError.TYPE_LATCH) return true;
            return false;
        }
        
}
