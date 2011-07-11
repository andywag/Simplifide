/*
 * CoreProjectSuite.java
 *
 * Created on May 12, 2006, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.api.file.FileHolder;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceItemNew;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 */
public class CoreProjectBasic<T extends InstanceModuleTop> extends CoreProjectTop<T>{
    
    public static final String DESIGN = "design";
    public static final String GLOBAL = "Global"; 
    
    /** Reference to the suite holding this library */
    private ReferenceItem<? extends CoreProjectSuite> suiteReference;
    /** Reference to this object */
    private ReferenceItem<CoreProjectBasic> projectReference;   
    /** Location of this object related to the Project Locator */
    private int suiteLocation;
    
    private InstancePackage projectContext;
    
    private boolean finishedCompile = false;;
    
    public CoreProjectBasic() {
    }
    public CoreProjectBasic(String name) {
        super(name);
        init();
    }
    /*
     * @param holder
     * @param suite
     */
    public CoreProjectBasic(FileHolder holder, ReferenceItem<CoreProjectSuite> suite) {
        super(holder);
        this.suiteReference = suite;
        init();
    }
    
    private void init() {
        this.projectReference = new ReferenceItemNew(this);
        this.createContext();
    }
    
	private void createContext() {
		this.projectContext = new InstancePackage(GLOBAL,this.createReferenceItem());
		PackageModule pack = new PackageModule(GLOBAL);
		this.projectContext.setPackageModuleReference(pack.createReferenceItem());
	}
        
    public int getSearchType() {return ReferenceUtilities.REF_PROJECT_BASIC;}
    public ReferenceItem createReferenceItem() {return this.projectReference;}
    
    
    public ReferenceLocation getFileLocation(String filename) {
    	return null;
    }
    
    public List<InstanceModule> getAllInstanceModules() {
    	List<ReferenceItem> moduleRefs = this.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_INSTANCE_MODULE);
    	ArrayList<InstanceModule> modules = new ArrayList<InstanceModule>();
    	for (ReferenceItem moduleRef : moduleRefs) {
    		modules.add((InstanceModule)moduleRef.getObject());
    	}
    	return modules;
    	
    }
    
    public List<ReferenceItem<ClassInstanceModule>> getClasses() {
    	ArrayList<ReferenceItem<ClassInstanceModule>> classes = new ArrayList<ReferenceItem<ClassInstanceModule>>();
    	for (ReferenceItem<? extends T> ref : this.getGenericSelfList()) {
    		if (ref.getType() == ReferenceUtilities.REF_INSTANCE_CLASS) {
    			classes.add((ReferenceItem<ClassInstanceModule>)ref);
    		}
    	}
    	return classes;
    }
    
     /** Remove Dummy Components from Prefix List */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item,
                                                            int type) {
        List<? extends ReferenceItem> preList = super.findPrefixItemList(item, type);
        ArrayList<ReferenceItem> nlist = new ArrayList<ReferenceItem>();
        for (ReferenceItem uitem : preList) {
        	if (uitem.getname().endsWith("_Context")) continue;
        	else if (uitem.getType() == ReferenceUtilities.REF_INSTANCE_MODULE) {
                InstanceModule uinst = (InstanceModule) uitem.getObject();
                if (uinst.displayInPrefix()) {
                    nlist.add(uitem);
                }
            }
            else {
                nlist.add(uitem);
            }
        }
        return nlist;
    }
    
    
   
    
    public void deleteObject() {
        this.suiteReference = null;
        this.projectReference = null;
        super.deleteObject();
    }
    

    /** Attaches the Suite and Project to the Design Files */
    public void attachSuite() {}
    
    
    public int getSuiteLocation() {
        return suiteLocation;
    }
    
    public void setSuiteLocation(int suiteLocation) {
        this.suiteLocation = suiteLocation;
    }
   
    public ReferenceItem<T> addModuleObject(T object, ReferenceLocation loc) {
    	
   
        ReferenceItem<T> uitem = object.createReferenceItem();
        
        uitem.setLocation(loc);
        this.addObject(uitem);
        ReferenceItem last = null;
        for (int i=0;i<this.getnumber();i++) {
        	ReferenceItem<? extends T> oR = this.getObject(i);
        	if (last != null && last.getname().compareToIgnoreCase(oR.getname()) > 0) {
        		String alpha;
        	}
        	last = oR;
        		
        }
       
        return uitem;
    }
    
    
    public ReferenceItem<? extends CoreProjectSuite> getSuiteReference() {
        return suiteReference;
    }
    
    public void setSuiteReference(ReferenceItem<? extends CoreProjectSuite> suiteReference) {
        this.suiteReference = suiteReference;
    }
    
    public ReferenceItem< CoreProjectBasic> getProjectReference() {
        return projectReference;
    }
    
    public void setProjectReference(ReferenceItem<CoreProjectBasic> projectReference) {
        this.projectReference = projectReference;
    }
    
    public boolean isFinishedCompile() {
        return finishedCompile;
    }
    
    public void setFinishedCompile(boolean finishedCompile) {
        this.finishedCompile = finishedCompile;
    }
	public void setProjectContext(InstancePackage projectContext) {
		this.projectContext = projectContext;
	}
	public InstancePackage getProjectContext() {
		return projectContext;
	}
	
    
   
    
  
    
    
    
    
    
    
    
}
