package com.simplifide.base.sourcefile.antlr.node;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class TopASTNodeGeneric<T extends TopObjectBase> extends TopASTNode {

	
	public TopObjectBase generateModule(ParseContext context) {
		return this.createObject(context);
	}
	
	public T createObject(ParseContext context) {
        ParseContext.Storage store = context.createStorage();
        try {
        	T obj = this.createObjectSmall(context);
        	context.restoreStorage(store);
            return obj;
        } catch (Exception e) {
            this.handleError(e, context);
            return null;
        }
	}
	
	public T createObjectSmall(ParseContext context) {
		return (T) this.generateModuleSmallNew(context);
	}
	
	public void addObjects(ReferenceItem parent, ModuleObject child1) {
		if (child1 == null) return;
		
		if (child1 instanceof ReferenceItem) {
			ReferenceItem child = (ReferenceItem) child1;
			ModuleObject childval = child.getObject();
			if (childval instanceof NoSortList) {
				NoSortList<ModuleObject> lis = (NoSortList) childval;
				for (ReferenceItem ref : lis.getGenericSelfList()) {
					this.addObjects(parent, ref);
					//parent.addReferenceItem(ref);
				}
			}
			else {
				parent.addReferenceItem(child);
			}
		}
		else if (child1 instanceof NoSortList) {
			NoSortList<ModuleObject> lis = (NoSortList) child1;
			for (ReferenceItem ref : lis.getGenericSelfList()) {
				parent.addReferenceItem(ref);
			}
		}
		
		
	}
	
	
	
}
