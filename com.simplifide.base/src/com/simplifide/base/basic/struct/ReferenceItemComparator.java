/*
 * ReferenceItemComparator.java
 *
 * Created on March 13, 2007, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author Andy
 */
public class ReferenceItemComparator<T extends ModuleObject> implements ModuleObjectComparator<ReferenceItem<T>>{
    
    private static ReferenceItemComparator instance;
    /** Creates a new instance of ReferenceItemComparator */
    public ReferenceItemComparator() {}
    
    public static ReferenceItemComparator getInstance() {
        if (instance == null) instance = new ReferenceItemComparator();
        return instance;
    }
    
    public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
    	if (o1 == null) return 1;
    	if (o2 == null) return 1;
    	String o1Name = o1.getname();
    	String o2Name = o2.getname();
    	if (o1Name == null) return 1;
    	if (o2Name == null) return 1;
        int comp =  o1Name.compareToIgnoreCase(o2Name);
        if (comp != 0) return comp;
        return this.checkType(o1.getType(),o2.getType());
    }
    
    public int checkType(int o1,int o2) {
        return ReferenceUtilities.checkType(o1,o2);
    }

    public int comparePrefix(ReferenceItem<T> o1, ReferenceItem<T> o2) {
    	if (o1 == null || o2 == null) return 1;
        int type = this.checkType(o1.getType(),o2.getType());
        boolean nameCheck = this.startsWith(o1,o2);
        
        if (type != 0) return ModuleObjectComparator.MATCH_NO;
        else if (nameCheck) return ModuleObjectComparator.MATCH;
        else return ModuleObjectComparator.MATCH_NAME;
        
    }
    
    public boolean startsWith(ReferenceItem base, ReferenceItem comp) {
        String compName = comp.getname();
        String baseName = base.getname();
        if (compName.length() == 0) return true;
        if (baseName.length() < compName.length()) return false;
        String tname = baseName.substring(0,compName.length());
        //if (uname.equals(tname)) return true;
        if (compName.equalsIgnoreCase(tname)) return true;
        else return false;
    }
    
}
