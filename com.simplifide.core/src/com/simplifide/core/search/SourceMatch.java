/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.search;

import org.eclipse.search.ui.text.Match;

import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;

public class SourceMatch extends Match{
	
	
	
	
	public SourceMatch(ReferenceUsage usage) {
		super(usage,Match.UNIT_CHARACTER,usage.getLocation().getDocPosition(),usage.getLocation().getLength());
	}

    public boolean comparePosition(SourceMatch match) {
            ReferenceLocation thisLocation = match.getUsage().getLocation();
            ReferenceLocation nextLocation = this.getUsage().getLocation();
            
            return thisLocation.compareLocation(nextLocation);
        }
        
	public ReferenceUsage getUsage() {
		return (ReferenceUsage) this.getElement();
	}
	
}
