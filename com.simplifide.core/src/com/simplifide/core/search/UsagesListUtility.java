/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.search;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.newfunction.SuperFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileAlone;
import com.simplifide.core.verilog.editor.describer.VerilogFile;

public class UsagesListUtility {

	/** Returns a complete list of design files to search over */
    private static UniqueList<DesignFile> getCompileList(EditorFindItem findItem,
    		List<EditorFindItem> itemList) {
        
    	ReferenceItem ref = findItem.getItem();
    	ModuleObject obj = ref.getObject();
    	if (obj instanceof ClassModule ||
    	    obj instanceof ClassEntity ||
    		obj instanceof ClassInstanceModule ||
    		obj instanceof SuperFunction)
    		{
    			ReferenceLocation loc = ref.getLocation();
    			DesignFile dfile = LocationOperations.getDesignFile(loc);
    			if (dfile instanceof VerilogFile) {
    				EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
    	    		return suite.getCompileList();
    			}
    		
    	}
    	
        UniqueList<DesignFile> compileList = new UniqueList<DesignFile>();
        for (EditorFindItem item : itemList) { // Need to add the design files with the items first
            ReferenceLocation location = item.getItem().getLocation();
            DesignFile dfile = (DesignFile) LocationOperations.getDesignFile(location);
            if (dfile != null)
            	compileList.addObject(dfile.createReferenceItem());
        }
        for (EditorFindItem item : itemList) {
            ReferenceLocation location = item.getItem().getLocation();
            DesignFile dfile = (DesignFile) LocationOperations.getDesignFile(location);
            if (dfile != null)
            	compileList.addAll(dfile.getCompileInfo().getDependentList()); // Questionable Operation
        }
        
        return compileList;
    }
    
    /** Returns a complete list of objects which have the correct name */
    private static List<ReferenceUsage> getUsageList(EditorFindItem findItem, 
    												 DesignFile currentFile) {
    	List<EditorFindItem> itemList = EditorUtilities.getRenameList(findItem);
    	
    	UniqueList<DesignFile> compileList;
    	if (currentFile == null) {
            compileList = getCompileList(findItem,itemList);
    	}
    	else {
    		compileList = new UniqueList();
    		compileList.addReferenceItem(currentFile.createReferenceItem());
    	}
        
        List<ReferenceUsage> usages = new ArrayList<ReferenceUsage>();
        /* Add All of the possible references from the dependent files */
        for (DesignFile dfile : compileList.getRealSelfList()) {
            List<ReferenceUsage> refList = dfile.getDesignFileBuilder().findReferenceItems(findItem);
            if (refList != null) usages.addAll(refList);
        }
        
        List<ReferenceUsage> usageList = new ArrayList<ReferenceUsage>();
      
        for (ReferenceUsage usage : usages) {
        	for (EditorFindItem uitem : itemList) {
        		ReferenceItem it = uitem.getItem().checkUsage(usage.getItem());
        		if (it != null) {
        			if ( (usage.getUsageType() == -1) || 
        				 (ReferenceUtilities.checkType(uitem.getItem().getType(), usage.getUsageType()) == 0) ||
        				  usage.getItem().getObject() instanceof InstanceModule) {
        				usageList.add(usage);
        				break;
        			}
        		}
        	}
        }
        
        for (EditorFindItem uitem : itemList) {
        	ReferenceLocation loc = uitem.getItem().getLocation();
        	if (uitem.getItem().getObject() instanceof HardwareModule) continue; // Kludge to keep hardware modules out
        	DesignFile dfile = LocationOperations.getDesignFile(loc);
        	if (dfile != null || currentFile instanceof DesignFileAlone) {
            	String text = "";
            	if (dfile != null) text = dfile.getTextAtLine(loc.getLine());
                ReferenceUsage usage = new ReferenceUsage(uitem.getItem(),text,uitem.getItem().getLocation());
                usageList.add(usage);
        	}

        }
        
        return usageList;
    }
    
    /** Returns a list of usages for the Editor Find Item */
    public static List<ReferenceUsage> getFilteredUsages(EditorFindItem item, DesignFile dfile){
        List<ReferenceUsage> newUsage = new ArrayList<ReferenceUsage>();
        List<ReferenceUsage> usage = getUsageList(item, dfile);
        boolean add;
        for (int i=0;i<usage.size();i++) {
            ReferenceUsage compUsage1 = usage.get(i);
            add = true;
            for (int j=i+1;j<usage.size();j++) {
                ReferenceUsage compUsage2 = usage.get(j);
                if (compUsage1.getLocation().compareLocation(compUsage2.getLocation())) {
                	add = false;
                	if (compUsage2.getText().equalsIgnoreCase("")) compUsage2.setText(compUsage1.getText());
                }
            }
            if (add) {
                newUsage.add(compUsage1);
            }
        }
        return newUsage;
    }
    
    /** Returns a tree of usages for the list of items. The usage list utility compiles the 
     *  design searching for actual usages of the items so they don't have to be stored in 
     *  memory
     */
    public static RefactorTreeHolder.Root getTreeUsages(EditorFindItem item,
    		String newName, boolean global) {
        RefactorTreeHolder.Root root = new RefactorTreeHolder.Root(); 
        List<ReferenceUsage> usageList = getUsageList(item, null);
        for (ReferenceUsage usage : usageList) {
        	usage.setText(newName);
            root.addElement(new SourceMatch(usage));
        }
        return root;
    }
    
    
    public static RefactorTreeHolder.Root getSimpleTreeUsages(List<EditorFindItem> itemList, String newName) {
        RefactorTreeHolder.Root root = new RefactorTreeHolder.Root(); 

        for (EditorFindItem item : itemList) {
        	ReferenceItem it = item.getItem();
        	ReferenceUsage usage = new ReferenceUsage(it,newName,it.getLocation());
            root.addElement(new SourceMatch(usage));
        }
        return root;
    }
    /*
    public static void postRefactor(List<EditorFindItem> itemList) {
        UniqueList<DesignFile> designList = getCompileList(itemList);
        for (DesignFile design : designList.getRealSelfList()) {
            design.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
        }
    }*/
}
