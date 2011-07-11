package com.simplifide.base.core.class1;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ClassInstanceModule extends InstanceModule{
	
	private ModuleObjectWithList<ClassInstanceModule> children = new ModuleObjectWithList<ClassInstanceModule>();
	
    public ClassInstanceModule(String name, ReferenceItem<CoreProjectBasic> projectRef) {
    	super(name,projectRef);   	
    }
    
	 public int getSearchType(){return ReferenceUtilities.REF_INSTANCE_CLASS;}

	 
	public void handleClassBodyAddition(ReferenceItem<FunctionDeclaration> decR) {
		
	}

	public ReferenceItem<ClassInstanceModule> getSuperR() {
		ClassEntity entity = (ClassEntity) this.getEntity();
		return entity.getSuperR();
	}
	
	public boolean hasNavigatorChildren() {
		if (this.createReferenceItem().isShadow()) return false;
		if (this.getArchitectureReference().isShadow()) return false;
		if (this.getArchitecture() != null) return this.getArchitecture().hasNavigatorChildren();
		return false;
	}
	
	public List<ReferenceItem> getNavigatorList() {
		return this.getArchitectureReference().getNavigatorList();
	}
	
	 public ReferenceItem findBaseReference(ModuleObjectIndexTop item)
	    { 
	        int type = item.getBase().getType();
	        //if (ReferenceUtilities.checkType(ReferenceUtilities.REF_INSTANCE_MODULE,type) == 0) return this;
	        ReferenceItem ref = null;
	        if (this.getArchitecture() != null) ref = this.getArchitecture().findBaseReference(item);
	        if (ref == null && this.getEntity() != null) ref = this.getEntity().findBaseReference(item); 
	        return ref;
	    }
	
	    public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem) {
	    	ArrayList<ReferenceItem> refList = new ArrayList<ReferenceItem>();
			refList.add(this.getArchitectureReference());
			refList.add(this.getEntityReference()); 
			return refList;
	    }
	 
	 public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item) {
		 ArrayList<ReferenceItem> refList = new ArrayList<ReferenceItem>();
		 refList.add(this.getArchitectureReference());
		 return refList;
	 }

	public void setChildren(ModuleObjectWithList<ClassInstanceModule> children) {
		this.children = children;
	}

	public ModuleObjectWithList<ClassInstanceModule> getChildren() {
		return children;
	}
	
}
