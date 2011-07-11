package com.simplifide.core.ui.views;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.hierarchy.ClassList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;
import com.simplifide.core.ui.views.quick.QuickClassHierarchy;

public class ClassContentProvider extends ReferenceItemTreeProvider{

	
	public boolean hasChildren(Object element) {
		ReferenceItem item = (ReferenceItem) element;
		if (item.getObject() instanceof ClassList) {
			ClassList lis = (ClassList) item.getObject();
			if (lis.getnumber() > 0) return true;
		}
		else if (item.getObject() instanceof ClassInstanceModule) {
			ClassInstanceModule cmod = (ClassInstanceModule) item.getObject();
			ModuleObjectWithList lis = cmod.getChildren();
			if (lis.getnumber() > 0) return true;
		}
		else if (item.getObject() instanceof QuickClassHierarchy.ClassInstanceModuleDelegate) {
			return true; // Must be true since this was the purpose of the creation
		}
		return false;
	}
	
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		ReferenceItem item = (ReferenceItem) parentElement;
		if (item.getObject() instanceof ClassList) {
			ClassList lis = (ClassList) item.getObject();
			List refs = lis.getNavigatorList();
			return refs.toArray();
		}
		else if (item.getObject() instanceof ClassInstanceModule) {
			ClassInstanceModule cmod = (ClassInstanceModule) item.getObject();
			ModuleObjectWithList lis = cmod.getChildren();
			return lis.getGenericSelfList().toArray();
		}
		else if (item.getObject() instanceof QuickClassHierarchy.ClassInstanceModuleDelegate) {
			QuickClassHierarchy.ClassInstanceModuleDelegate cmod = (QuickClassHierarchy.ClassInstanceModuleDelegate) item.getObject();
			return cmod.getGenericSelfList().toArray();
		}
		List<ReferenceItem> refList =  item.getNavigatorList();
		ArrayList lis = new ArrayList();
		for (ReferenceItem ref : refList) {
			if (!ref.getname().endsWith("_Context")) {
				lis.add(ref);
			}
		}
		return lis.toArray();
	}
}
