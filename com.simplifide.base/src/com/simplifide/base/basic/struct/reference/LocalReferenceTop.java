/*
 * LocalReferenceTop.java
 *
 * Created on January 31, 2006, 9:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct.reference;


/**
 *
 * @author awagner
 */
public class LocalReferenceTop {
    
    public static LocalReferenceTop.NullRef NULLREF = new LocalReferenceTop.NullRef();
    /** Creates a new instance of LocalReferenceTop */
    public LocalReferenceTop() {}
    public boolean hasReference() {return true;}
    
    
    public static class NullRef extends LocalReferenceTop
    {
        public NullRef() {}
        public boolean hasReference() {return false;}
    }
}
