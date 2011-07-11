/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 11, 2004
 * Time: 2:16:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModuleObject<T extends ModuleObject> extends TopObjectBase<T> implements Comparable {
    
    /** Kludge Variable to allow the storage of Names a.b.c. for code generation */
	private String compositeName;
	
    private HdlDoc doc;
    public ModuleObject() {super();}
    public ModuleObject(String name) {
        super(name);
        init();
    }
    
    private void init() {}
    
    /** Returns the basic method which is used in searching 
     * ie component/entity/arch --> Instance Module
     */
    public ModuleObject getBaseSearchClass() {
    	return this;
    }
    /** Return the type of object used for searching with the reference item */
    public int getSearchType() {return ReferenceUtilities.REF_MODULEOBJECT;}
    /** Return the Type of the Object */
    public String getTemplateName() {
        return "module_object";
    }
    public Object getTemplateObject() {
        return this;
    }
    
    public String getExtraGoToInformation() {
    	return "";
    }
    
    /*
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        list.add(item);
        return list;
    }*/
    
    /** Method which translates all of the javadoc elements created for the parent object to the
     * child
     * @param parent
     */
    public void updateHdlDoc(ModuleObject parent) {
    	
    }
    
    //protected ArrayListWrap createNewArrayList() {return new SortedArrayList<ModuleObject>(TopObjectComparator.Comp);}
    public ReferenceItem createReferenceItem() {return new ReferenceItem(this);}
    
    /**
     * 
     * @param loc 
     * @return 
     */
    public ReferenceItem createReferenceItemWithLocation(ReferenceLocation loc) {
        ReferenceItem item = this.createReferenceItem();
        item.setLocation(loc);
        return item;
    }
    
    /** Returns the name for this completion object */
    public String getCompletionName() {return this.getDisplayName();}
    /** Returns the Html Version of the Completion Name */
    public String getHtmlCompletionName() {return null;}
    /** Returns the displayName for the completion type */
    public String getCompletionType() {return "";}
    /** Returns an html version for the completion type */
    public String getHtmlCompletionType() {return null;}
    /** Returns an html version of the completion name */
    public String getHtmlDisplayName() {return null;}
    /** Returns an html version for the documentation window */
    public String getHtmlDocName() {return "";}
    
            
    /** Get a list of outputs associated with this expression 
     * @return 
     */
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        return new ModuleObjectWithList("Empty").createReferenceItem();
    }
    
    /** Method returns a list of dependent objects on this item 
     * @return 
     */
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        return new ModuleObjectWithList("Empty").createReferenceItem();
    }
    
    /** Deletes a private reference object. Used when a file is out of context to keep 
     * the memory usages small. This method is only used for architectures and package
     * bodies
     */
    public void cleanObject() {
        
    }
    
    /** Method which allows the object to be handled in a convenient manner. Main use is to when the 
     *  architecture body is deleted */
    public void clearChildrenReferences() {
    	for (ModuleObject item : this.getGenericSelfList()) {
    		ReferenceItem itemRef = (ReferenceItem) item;
    		itemRef.clearObject();
    	}
    }
    
    /** Handle when this object has been finalized */
    public void handleFinalization() {
    	
    }
    /** This method handles when the reference points to a null object, which correspondingly means
     *  that this object no longer exists
     */
    public void finalizeReference() {
    	
    }
    
    protected void handleDocChange() {
        
    }
    /** Used to add the documentation to the block. First sets the documentation and then updates the 
     *  contents of both the doc and the item based on the values */
    public void changeDoc(HdlDoc doc) {
        this.setDoc(doc);
        this.handleDocChange();
    }
    
    /** Method which returns the type of this object. Used for creating type mismatch errors */
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        return null;
    }
    
    
    public boolean hasNavigatorChildren() {
    	return false;
    }
    /** Method which returns the list of values which should be displayed in the navigator window */
    public List<ReferenceItem> getNavigatorList() {
        return new ArrayList<ReferenceItem>();
    }
    
    
    // Testing Before Delete
    /* 
    public boolean isEquivalentName(String uname) {
        if (uname.equalsIgnoreCase(this.getname())) return true;
        return false;
    }*/
    
   
    
   

    
      public HdlDoc getDoc() {
        return  doc;
    }

    public void setDoc(HdlDoc doc) {
        this.doc = doc;
    }
	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}
	public String getCompositeName() {
		if (compositeName == null) return this.getname();
		return compositeName;
	}
    
    
    
}
