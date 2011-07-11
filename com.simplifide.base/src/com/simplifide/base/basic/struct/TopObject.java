/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct;

import java.util.HashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.simplifide.base.basic.struct.change.TopObjectChange;


public class TopObject implements Cloneable,ChangeListener, Comparable {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
    private final transient HashSet listener = new HashSet();
    
    
   
    
    public TopObject(){this("");}
    public TopObject(String name1) {
        this.name = name1;
    }
    public TopObject(TopObject inval) {
        this(inval.getname());
    }
    
    
   

    
    
    
    public String getDisplayName() {return this.name;}
    
    
    
    
    public final void clearListeners() {
        this.listener.clear();
    }
    
    public final void addListener(ChangeListener l) {
        this.listener.add(l);
    }
    public final void removeListener(ChangeListener l) {
        this.listener.remove(l);
    }
    
    
    
    public final void debugListener() {

        final ChangeListener[] ls = (ChangeListener[])listener.toArray(new ChangeListener[listener.size()]);
        for (int i=0;i<ls.length;i++) {
            //com.simplifide.core.codegen.basic.util.IDEout.printlnOver("Listen" + this + " " + ls[i]);
        }
        
    }
    
    
    public final void fireChange() {
        TopObjectChange uch = new TopObjectChange(this,this,TopObjectChange.CHANGE);
        this.valueChanged(uch);
    }
     
    protected void valueChanged(ChangeEvent event) {
      
        
       
        final ChangeListener[] ls = (ChangeListener[]) listener.toArray(new
                ChangeListener[listener.size()]);
        
        for (int i = 0; i < ls.length; i++) {
            ls[i].stateChanged(event);
        }
        
    }
    
    
    
    public void stateChanged(ChangeEvent e) {
    }
    public String getname() {return name;}
    
    
    public void setname(String name1) {name = name1; }
    
    
    
    
    public int getnumber() {return 0;}
    
    /** Used for Sorting Objects */
    
    public int compareTo(Object o) {
        if (o instanceof TopObject) {
            TopObject op = (TopObject) o;
            return this.getname().compareTo(op.getname());
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
   
    
 
}
