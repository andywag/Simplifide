/*
 * TypeComparator.java
 *
 * Created on March 19, 2007, 2:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.var.types;

import com.simplifide.base.basic.struct.ModuleObjectComparator;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 */
public class TypeComparator <T extends TypeVar> implements ModuleObjectComparator<ReferenceItem<T>> {
    
    public static TypeComparator TYPE_INSTANCE = new TypeComparator();
    /** Creates a new instance of TypeComparator */
    public TypeComparator() {}
    
    public static TypeComparator getInstance() {return TYPE_INSTANCE;}
    
    private int checkSameType(TypeVar type1, TypeVar type2) {
        if (type1.getSearchType() < type2.getSearchType()) return 1;
        if (type2.getSearchType() > type2.getSearchType()) return -1;
        return 0;
    }
    
    public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
        TypeVar typeVar1=null, typeVar2=null;
        if (o1 != null) {
            if (o1.getObject() != null) {
                typeVar1= o1.getObject().getBaseVar();
            }
        }
        if (o2 != null) {
            if (o2.getObject() != null) {
                typeVar2 = o2.getObject().getBaseVar();
            }
        }
        if (typeVar1 == null || typeVar2 == null) {
            return -1;
        }
        
        return typeVar1.typeCheckCompare(typeVar2);
        
        
    }

    public int comparePrefix(ReferenceItem<T> o1, ReferenceItem<T> o2) {
        return 0;
    }


   
    
    
}
