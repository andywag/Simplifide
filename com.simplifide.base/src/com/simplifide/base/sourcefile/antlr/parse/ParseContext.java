/*
 * ParseContext.java
 *
 * Created on February 15, 2006, 10:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.parse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.error.ErrorEnableHolder;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceHandler;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

/**
 *
 * @author awagner
 */
public class ParseContext implements Cloneable, BuildInterface,ParseContextConstants
{

    // Search Mode Types

    
    private ReferenceHandler refHandler;   
    
    private List<TopError> errorList = new ArrayList<TopError>();
    
    private int definedMode = ERROR_NOT_DEFINED_ENABLED;
    private int type;
    private int pass;
    private int searchMode = SEARCHREFERENCELOCAL;
    private int docPosition;
    
    private ErrorEnableHolder errorEnableHolder;
    
    private ReferenceLocation baseLocation;
    private SearchContext searchContext;
    private ParseDescriptor descriptor;
    
    /** Creates a new instance of ParseContext */
    public ParseContext(ParseDescriptor descriptor, int pass) {
        this.pass = pass;
        this.setDescriptor(descriptor);
        init();
    }

    private void init() {
        this.refHandler = this.createReferenceHandler();
        
    }
    
    protected ReferenceHandler createReferenceHandler() {
    	return new ReferenceHandler();
    }
    
    public String debugString()
    {
        return "";
        
    }
    
    // Keeps the error all the way through the process;
    public ParseContext copy()
    {
        try {
            ParseContext context = (ParseContext) this.clone();
            return (ParseContext) context;
        } catch (CloneNotSupportedException ex) {
            BaseLog.logError(ex);
        }
        return null;
    }

   
    public URI getURILocation() {
    	return this.getBaseLocation().getUri();
    }
    
    public ReferenceLocation createReferenceLocation(TopASTNode node) {
        if (node.getPosition() != null && this.getBaseLocation() != null) {
            int location = node.getPosition().getStartPos();
            return this.getBaseLocation().copyLocation(location,node.getPosition().getLength(), node.getPosition().getStartLine());
        }
        if (this.getBaseLocation() != null)
            return this.getBaseLocation().copyLocation(0, 0, 0);
        else if (node.getPosition() != null) {
            NodePosition pos = node.getPosition();
            return new ReferenceLocation(this.baseLocation.getUri(),pos.getStartPos(),pos.getLength(),pos.getStartLine());
        }
        else 
            return new ReferenceLocation(this.baseLocation.getUri(),0,0,0);
    }
   


    
    /** Restores the storage for Parse Context */
    public void restoreStorage(Storage store)
    {
        this.setActiveReference(store.activeReference);
        this.setDefinedMode(store.definedMode);
        this.setType(store.type);
    }
    
    /** Creates the storage for Parse Context */
    public Storage createStorage()
    {
        return new Storage(this.getActiveReference(),this.definedMode,this.type);
    }
    
    /** Storage class so that context can be saved and restored */
    public static class Storage
    {
        public ReferenceItem activeReference;
        public int definedMode;
        public int type;
        
        public Storage( ReferenceItem ref, int definedMode, int type)
        {
            this.activeReference = ref;
            this.definedMode = definedMode;
            this.type = type;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   

   

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(int searchMode) {
        this.searchMode = searchMode;
    }

   

    

 
   

    public ReferenceItem getActiveReference() {
        return this.getRefHandler().getActiveReference();
    }

    public void setActiveReference(ReferenceItem activeReference) {
        this.getRefHandler().setActiveReference(activeReference);
    }   

    public ReferenceHandler getRefHandler() {
        return refHandler;
    }

    public void setRefHandler(ReferenceHandler refHandler) {
        this.refHandler = refHandler;
    }

   

    public ReferenceLocation getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(ReferenceLocation baseLocation) {
        this.baseLocation = baseLocation;
    }

   

    public int getDefinedMode() {
        return definedMode;
    }

    public void setDefinedMode(int definedMode) {
        this.definedMode = definedMode;
    }
	public void setErrorList(List<TopError> errorList) {
		this.errorList = errorList;
	}
	public List<TopError> getErrorList() {
		return errorList;
	}
	public void setErrorEnableHolder(ErrorEnableHolder errorEnableHolder) {
		this.errorEnableHolder = errorEnableHolder;
	}
	public ErrorEnableHolder getErrorEnableHolder() {
		return errorEnableHolder;
	}
	public void setSearchContext(SearchContext searchContext) {
		this.searchContext = searchContext;
	}
	public SearchContext getSearchContext() {
		return searchContext;
	}
	public void setDocPosition(int docPosition) {
		this.docPosition = docPosition;
	}
	public int getDocPosition() {
		return docPosition;
	}

	public void setDescriptor(ParseDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public ParseDescriptor getDescriptor() {
		return descriptor;
	}


	
    
}
