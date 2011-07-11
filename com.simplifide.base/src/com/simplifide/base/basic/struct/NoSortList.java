/*
 * ModuleObjectNoSortList.java
 *
 * Created on March 15, 2007, 4:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import java.util.List;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author Andy
 */
public class NoSortList<T extends ModuleObject> extends ModuleObjectWithList<T>{
    
    public static NoSortComparator COMPARATOR = new NoSortComparator();
    /** Creates a new instance of ModuleObjectNoSortList */
    public NoSortList() {}
    public NoSortList(String name) {super(name);}

    
    
    public int getSearchType() {
        return ReferenceUtilities.REF_NO_SORT_LIST;
    }
    
    protected ModuleObjectComparator getComparator() {
        return COMPARATOR;
    }
    
    public ReferenceItem findBaseReference(ReferenceItem item) {
    	for (ReferenceItem ref : this.getGenericSelfList()) {
    		if (ref.getname().equalsIgnoreCase(item.getname())) return ref;
    	}
    	return null;
    }
    
    /** @TODO Really crummy way of doing this */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        ModuleObjectWithList list = new ModuleObjectWithList();
        for (ReferenceItem ref : this.getGenericSelfList()) {
        	list.addReferenceItem(ref);
        }
        return list.findPrefixItemList(item, type);
    }
     
    @Override
	public void removeObject(ReferenceItem<? extends T> inval) {
		List<ReferenceItem<? extends T>> refList= this.getGenericSelfList();
		for (int i=0;i<refList.size();i++) {
			ReferenceItem ref = refList.get(i);
			if (ref.getname().equals(inval.getname())) {
				this.getArrayList().removeObject(i);
				return;
			}
		}
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}




	private static class NoSortComparator<T extends ModuleObject> implements ModuleObjectComparator<T> {
        public NoSortComparator() {}
        public int comparePrefix(ModuleObject o1, ModuleObject o2) {
            return -1;
        }

        public int compare(ModuleObject o1, ModuleObject o2) {
            return -1;
        }
        
    }
}
