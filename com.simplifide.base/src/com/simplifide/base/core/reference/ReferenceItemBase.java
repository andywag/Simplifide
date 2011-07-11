/*
 * ReferenceItemBase.java
 *
 * Created on August 7, 2006, 9:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import java.util.Comparator;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;

/**
 *
 * @author awagner
 *
 * Basic Item to use for creating references between objects. It is a shame this
 * is using a high order object
 *
 */
public class ReferenceItemBase<T extends ReferenceItemBase> extends ModuleObject<T>
{
    
    public static ReferenceItemBaseComparator BASE_COMPARATOR = new ReferenceItemBaseComparator();
    
    private int type;
    private Comparator compare = BASE_COMPARATOR;
    /** Creates a new instance of ReferenceItemBase */
    public ReferenceItemBase() {}
    public ReferenceItemBase(String name, int type) {
       this(name,type,BASE_COMPARATOR);
    }
    public ReferenceItemBase(String name, int type, Comparator compare) {
        super(name);
        this.type = type;
        this.compare = compare;
    }
    
    
    public static ReferenceItemBase createReferenceItemBase(ModuleObject obj) {
        return new ReferenceItemBase(obj.getname(),obj.getSearchType());
    }
    
    public boolean startsWithValidType(ReferenceItemBase base) {
        boolean outtype = false;
        if (!this.startsWith(base)) {
            outtype = false;
        } else if (this.checkType(base.getType()) == 0) outtype = true;
        return outtype;
    }
    
    public boolean startsWith(ReferenceItemBase base) {
        String uname = base.getname();
        if (uname.length() == 0) return true;
        if (this.getname().length() < uname.length()) return false;
        String tname = this.getname().substring(0,uname.length());
        //if (uname.equals(tname)) return true;
        if (uname.equalsIgnoreCase(tname)) return true;
        else return false;
    }
    
    public int compareTo(Object o) {
        ReferenceItemBase base = (ReferenceItemBase) o;
        
        int comp =  this.getname().compareToIgnoreCase(base.getname());
        if (comp != 0) return comp;
        return this.checkType(base.getType());
    }
    
    /** Special Case Added for Mod Instance Types. The cleanliness of this operation is getting more questionable */
    public int checkType(int base) {
        //return ReferenceUtilities.checkType(this.getType(),base);
        return BASE_COMPARATOR.checkType(this.getType(),base);
    }
    
    private static class ReferenceItemBaseComparator implements Comparator<ReferenceItemBase> {
        private ReferenceItemBaseComparator() {}

        public int compare(ReferenceItemBase o1, ReferenceItemBase o2) {
            int comp =  o1.getname().compareToIgnoreCase(o2.getname());
            if (comp != 0) return comp;
            return this.checkType(o1.getType(),o2.getType());
        }
        
        public int checkType(int o1,int o2) {
            return ReferenceUtilities.checkType(o1,o2);
        }

       
    }
    
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }

    public Comparator getCompare() {
        return compare;
    }

    public void setCompare(Comparator compare) {
        this.compare = compare;
    }
}
