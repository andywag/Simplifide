/*
 * SuperModuleSource.java
 *
 * Created on February 14, 2007, 9:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import com.simplifide.base.basic.api.progress.ProgressInt;
import com.simplifide.base.basic.struct.ModuleObjectFindItemList;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.reference.HardwareSourceInt;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author awagner
 *
 * This class handles the file functionallity of the source file.
 *
 */
public class SuperModuleSource<T extends ModuleObjectNew> extends SuperModuleTop<T>{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HardwareSourceInt source;

     // Temporary Variable Used to store the dependencies for the file
    private ModuleObjectFindItemList parentList = new ModuleObjectFindItemList(); // Items which this depends on

    public SuperModuleSource() {}
    public SuperModuleSource(String name) {super(name);}
    
    
    
    public void resolveDependencies() {
        
    }
    
    /** Wrapper function to the hardware source to find usages of the moduleobjectfinditem stored in object */
    public Object findUsages(Object ref) {
        return source.findUsages(ref);
    }
    

    
    public void build(int pass, ProgressInt prog) {
  
        this.getSource().build(pass);
        if (prog != null)
            prog.process(1);
    }
    
    public void close() {
        this.source.close();
    }
    
   
    public void addParentList(ReferenceItem<? extends ModuleObjectFindItem> obj) {
    	this.parentList.addObject(obj);
    }
    public ModuleObjectFindItemList getParentList() {return this.parentList;}
    public void setParentList(ModuleObjectFindItemList parentList) {this.parentList = parentList;}
    
    
    public HardwareSourceInt getSource() {return this.source;}
    public void setSource(HardwareSourceInt source) {this.source = source;}
}
