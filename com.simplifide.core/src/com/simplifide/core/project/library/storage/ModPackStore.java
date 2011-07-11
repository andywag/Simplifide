package com.simplifide.core.project.library.storage;

import java.io.Serializable;
import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceItem;

public class ModPackStore implements Serializable{

	private static final long serialVersionUID = 3920449904986065289L;

	
	private ArrayList<InstanceObject> instList = new ArrayList<InstanceObject>();
	
	public ModPackStore() {}

	public static ModPackStore createModPackStore(ReferenceItem<ModuleObjectNew> item) {
		ModPackStore store = new ModPackStore();

		//for (ReferenceItem it : item.getObject().getGenericSelfList()) {
		//	instList.add(it);
		//}
		return store;
	}
	
	
	public void setInstList(ArrayList<InstanceObject> instList) {
		this.instList = instList;
	}

	public ArrayList<InstanceObject> getInstList() {
		return instList;
	}
	
	
}
