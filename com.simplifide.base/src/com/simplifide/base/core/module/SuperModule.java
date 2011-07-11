/*
 * SuperModule.java
 *
 * Created on June 28, 2005, 11:01 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.core.module;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;



/**
 *
 * @author awagner
 *
 * The supermodule contains subblocks as it's base objects and instance modules in instanceHolder
 * This is the main temporary storage holder for items contained in this file. It contains multiple methods
 * for handling the operations related to the reference items. 
 * 
 * clearReferences    : Is called before a compile which deletes all of the objects which are created in this file
 * cleanReferences    : This method deletes all of the methods which are only used locally for this module
 * finalizeReferences : This method is called when the file is saved to promote changes to the rest of the design
 */
public class SuperModule<T extends ModuleObjectNew> extends SuperModuleSource<T> {
    
    
    private ReferenceItem<CoreProjectBasic> projectReference;
    // List of Defines for Modules to handle "`ifndef AAA; `define AAA"
    private ModuleObjectWithList<DefineObject> defines = new ModuleObjectWithList("defines");
    
    // Verilog Only References
    private ReferenceItem contextRef;
    private ReferenceItem fileContextRef;
    private UniqueList contextList = new UniqueList();
    private boolean containsDefines = false;
    
    /** Creates a new instance of SuperModule */
    public SuperModule() {}
    public SuperModule(String name) {
        super(name);
    }
    
   
    
    public String getNameWithoutExtension() {
    	String str = this.getname();
    	int pos = str.length();
    	for (int i=0;i<str.length();i++) {
    		if (str.charAt(i) == '.') {
    			pos = i;
    			break;
    		}
    	}
    	return str.substring(0,pos);
    }
    
    
    public int getSearchType() {
        return ReferenceUtilities.REF_SUPERMODULE;
    }
    
   
    
    /** @todo : Remove the Reference Items from the project context */
    public void deleteObject() {
       this.contextList.clearList();
       this.clearReferences();
       this.finalizeReference();
    }
    
    
    
    public void finalizeReference() {
    	for (int i=this.getnumber()-1;i>=0;i--) {
    		ReferenceItem item = this.getObject(i);
    		if (item != null && !item.hasObject()) {
    			if (item.finalizeObject()) this.removeObject(i);
    			this.projectReference.removeObject(item);
    		}
    	}
    }
    
   
    
    /** Removes the references from the supermodule objects which should correspondingly clean
     *  out objects which have been deleted. Used when an object needs to be reparsed */
    public void clearReferences() {
    	this.contextList.clearList();
        for (ReferenceItem item : this.getGenericSelfList()) {
        	if (item != null) {
        		if (item.getType() != ReferenceUtilities.REF_FUNCTION_HOLDER && item.getType() != ReferenceUtilities.REF_DEFINE_OBJECT) {
           		 	item.clearObject();
                    item.finalizeObject();
        		}
        	}
        	
        }
    }
    
    /** Clears out reference items to non public methods. Required to keep memory usages smaller 
     *  @todo : Implements this method
     */
    public void cleanReferences() {
        for (ReferenceItem item : this.getGenericSelfList()) {
        	if (item != null && item.getObject() != null) {
        		item.getObject().cleanObject();
        	}
        }
    }
    
    
    
    
    /** Not sure what clear corresponds to. The addition of cleanReferences to this
     * expression created a nasty bug which took 2 days to find. Care needs to be taken with
     * this operation */
    public void clear(boolean force) {
        
        
    }
    
    
 

    public ReferenceItem<CoreProjectBasic> getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ReferenceItem<CoreProjectBasic> projectReference) {
        this.projectReference = projectReference;
    }
	public void setContextRef(ReferenceItem contextRef) {
		this.contextRef = contextRef;
	}
	public ReferenceItem getContextRef() {
		return contextRef;
	}
	public void setFileContextRef(ReferenceItem fileContextRef) {
		this.fileContextRef = fileContextRef;
	}
	public ReferenceItem getFileContextRef() {
		return fileContextRef;
	}
	public void setContextList(UniqueList contextList) {
		this.contextList = contextList;
	}
	public UniqueList getContextList() {
		return contextList;
	}
	public void setDefines(ModuleObjectWithList<DefineObject> defines) {
		this.defines = defines;
	}
	public ModuleObjectWithList<DefineObject> getDefines() {
		return defines;
	}
	public void setContainsDefines(boolean containsDefines) {
		this.containsDefines = containsDefines;
	}
	public boolean isContainsDefines() {
		return containsDefines;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
