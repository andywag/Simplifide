/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error;

import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;

import com.simplifide.base.core.error.warnings.LatchWarning;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.source.SourceLocationHandler;

public class LatchResolution implements IMarkerResolution, IMarkerResolution2{

    private LatchWarning warn;
    
    public LatchResolution(LatchWarning warn) {
        this.warn = warn;
    }
    
    public String getLabel() {
        return "Complete the sensitivity list";
    }

    public void run(IMarker marker) {
        ReferenceLocation loc = warn.getLocation();
        String text = warn.getSensitivityList();
        SourceLocationHandler.getInstance().replaceText(loc,text);
    }

    public String getDescription() {
        return "Complete the sensitivity List";
    }

    public Image getImage() {
        // TODO Auto-generated method stub
        return null;
    }

  

}
