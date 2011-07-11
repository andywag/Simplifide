/*
 * PortList.java
 *
 * Created on March 15, 2007, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectComparator;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author Andy
 *
 * May Never use this class
 */
public class PortList<T extends PortTop> extends ModuleObjectWithList<T>{
    
    private static PortOrderComparator COMPARE = new PortOrderComparator();
    private static IoPortOrderComparator IOCOMPARE = new IoPortOrderComparator();
    /** Creates a new instance of PortList */
    public PortList() {}
    public PortList(String name) {
        super(name);
    }
    
    public List<PortTop> getPorts() {
    	ArrayList<PortTop> ports = new ArrayList<PortTop>();
    	for (ReferenceItem<? extends PortTop> pr : this.getGenericSelfList()) {
    		PortTop p = pr.getObject();
    		ports.add(p);
    	}
    	return ports;
    }
    
    
    
    public PortList copyWithConnectPorts() {
    	PortList ports = new PortList(this.getname());
    	for (ReferenceItem<? extends PortTop> portRef : this.getGenericSelfList()) {
    		PortConnect con = portRef.getObject().convertToConnectPort();
    		con.setPortNumber(portRef.getObject().getPortNumber());
    		ports.addReferenceItem(con.createReferenceItem());
    	}
    	return ports;
    }
    
    /** Propogates the generic value down to all variables */
    public void setGeneric(boolean gen) {
    	for (ReferenceItem<? extends PortTop> portRef : this.getGenericSelfList()) {
    		portRef.getObject().getLocalVar().setGeneric(gen);
    	}
    }
    
    /** Returne the Comparator for use on the reference list */
    protected ModuleObjectComparator getComparator() {
        return super.getComparator();
        //return IOCOMPARE;
    }
    
    public void updateHdlDoc(ModuleObject parent) {
    	for (T obj : this.getRealSelfList()) {
    		obj.updateHdlDoc(parent);
    	}
    }
    
    // Returns the port corresponding to this index
    public PortTop getPort(int index) {
    	return null;
    }
    
    public List<PortTop> getInputOutputOrderedList() {
    	List<PortTop> portList = new ArrayList();
    	List<ReferenceItem<? extends PortTop>> nlist = (List) this.getGenericSelfList().clone();
        Collections.sort(nlist,IOCOMPARE);
        for (ReferenceItem<? extends PortTop> portRef : nlist) {
        	portList.add(portRef.getObject());
        }
        return portList;
    }
    
    public List<ReferenceItem<? extends PortTop>> getOrderedRefList() {
        List<ReferenceItem<? extends PortTop>> nlist = (List) this.getGenericSelfList().clone();
        Collections.sort(nlist,COMPARE);
        return nlist;
    }
    
    public List<PortTop> getOrderedPortList() {
    	ArrayList<PortTop> ports = new ArrayList<PortTop>();
    	List<ReferenceItem<? extends PortTop>> refs = this.getOrderedRefList();
    	for (ReferenceItem<? extends PortTop> ref : refs) {
    		ports.add(ref.getObject());
    	}
    	return ports;
    }
    
    
    
    public int getSearchType() {return ReferenceUtilities.REF_PORT_LIST;}
    
    public static class PortOrderComparator implements Comparator<ReferenceItem<? extends PortTop>> {
        
        public PortOrderComparator() {}
        
        public int compare(ReferenceItem<? extends PortTop> o1, ReferenceItem<? extends PortTop> o2) {
            Integer i1 = Integer.valueOf(o1.getObject().getPortNumber());
            Integer i2 = Integer.valueOf(o2.getObject().getPortNumber());
            return i1.compareTo(i2);
        }
        
    }
    
    public static class IoPortOrderComparator implements ModuleObjectComparator<ReferenceItem<? extends PortTop>>,
    	Comparator<ReferenceItem<? extends PortTop>>
    {
        
        public IoPortOrderComparator() {}
        
        public int compare(ReferenceItem<? extends PortTop> o1, ReferenceItem<? extends PortTop> o2) {
            Integer i1 = Integer.valueOf(o1.getObject().getSearchType());
            Integer i2 = Integer.valueOf(o2.getObject().getSearchType());
            int delta = i1.compareTo(i2);
            if (delta != 0) return delta;
            return o1.getname().compareTo(o2.getname());
        }

		public int comparePrefix(ReferenceItem<? extends PortTop> o1,
				ReferenceItem<? extends PortTop> o2) {
			Integer i1 = Integer.valueOf(o1.getObject().getSearchType());
            Integer i2 = Integer.valueOf(o2.getObject().getSearchType());
            int delta = i1.compareTo(i2);
            if (delta != 0) return delta;
            return o1.getname().compareTo(o2.getname());
		}
        
    }
    
    
    
    
}
