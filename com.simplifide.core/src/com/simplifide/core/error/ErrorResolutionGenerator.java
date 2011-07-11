/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IMarkerResolutionGenerator2;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.core.source.SourceLocationHandler;
import com.simplifide.core.source.design.DesignFile;

public class ErrorResolutionGenerator implements IMarkerResolutionGenerator, IMarkerResolutionGenerator2{

    
    private IMarkerResolution[] resolutions;
    
    public ErrorResolutionGenerator() {
        
    }
    
    public IMarkerResolution[] getResolutions(IMarker marker) {
        
        int sourceLocation = marker.getAttribute(ErrorWrapper.ATTRIBUTE_SOURCE_LOCATION,-1);
        int errorLocation = marker.getAttribute(ErrorWrapper.ATTRIBUTE_ERROR_LOCATION, -1);
        
        DesignFile dfile = (DesignFile) SourceLocationHandler.getInstance().getLocation(sourceLocation);
        TopError error = dfile.getError(errorLocation);
      
        return ErrorResolutionFactory.getMarkerResolutions(error);
    }

    public boolean hasResolutions(IMarker marker) {
        int errorType = marker.getAttribute(ErrorWrapper.ATTRIBUTE_ERROR_TYPE, -1);
        return ErrorResolutionFactory.hasResolutions(errorType);
        
    }

}
