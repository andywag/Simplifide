package com.simplifide.base.core.hierarchy;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;

public class ClassList extends ModuleObjectWithList<ClassInstanceModule>{

	public void updateClassList(List<ReferenceItem<ClassInstanceModule>> classes) {
		this.clearList();
		for (ReferenceItem<ClassInstanceModule> class1 : classes) {
			if (class1.getObject().getSuperR() == null) {
				this.addReferenceItem(class1);
			}
		}
	}
	
}
