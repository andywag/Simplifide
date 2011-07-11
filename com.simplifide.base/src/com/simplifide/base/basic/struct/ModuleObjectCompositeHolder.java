package com.simplifide.base.basic.struct;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;

public class ModuleObjectCompositeHolder extends ModuleObjectWithList{

	/** Creates a new instance of ModuleObjectContextHolder */
    public ModuleObjectCompositeHolder() {}
    public ModuleObjectCompositeHolder(String name) {
        super(name);
    }
    
    public static ModuleObjectCompositeHolder dualHolder(String name, ReferenceItem item1, ReferenceItem item2) {
        ModuleObjectCompositeHolder holder = new ModuleObjectCompositeHolder(name);
        holder.addObject(item1);
        holder.addObject(item2);
        return holder;
    }
    
    /** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        ArrayList<ReferenceItem> list = this.getReferenceList().getArrayList();
        for (ReferenceItem refItem : list) {
            ReferenceItem nitem = refItem.findBaseReference(item);
            if (nitem != null) return nitem;
        }
        return null;
    }
    
    /** @todo : Need to clean up types */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        ArrayList< ReferenceItem> newList = new ArrayList();
        ArrayList<ReferenceItem> list = this.getReferenceList().getArrayList();
        for (ReferenceItem refItem : list) {
            List<ReferenceItem> ulist = refItem.findPrefixItemList(item,type);
            newList.addAll(ulist);
        }
        return newList;
    }
}
