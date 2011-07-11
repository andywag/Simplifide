/*
 * VhdlStandardPackage.java
 *
 * Created on March 28, 2007, 9:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.project;

import com.simplifide.base.core.module.PackageModule;

/**
 *
 * @author Andy
 */
public final class VhdlStandardPackage extends PackageModule{
    
    public static VhdlStandardPackage INSTANCE = new VhdlStandardPackage();
    
    /** Creates a new instance of VhdlStandardPackage */
    private VhdlStandardPackage() {
        init();
    }
    
    private void init() {
        this.createTypes();
        this.createVars();
    }
    
    private void createTypes() {
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_BIT);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_BIT_VECTOR);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_INTEGER);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_NATURAL);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_POSITIVE);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_REAL);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_BOOLEAN);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_STRING);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_TIME);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_CHARACTER);
        this.addReferenceItem(VhdlStandardTypes.REF_TYPE_RESOLVED);
        this.addReferenceItem(VhdlStandardTypes.VAR_TEXT.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_LINE.createReferenceItem());
    }

  
    
    private void createVars() {
        this.addReferenceItem(VhdlStandardTypes.VAR_TRUE.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_FALSE.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_MESSAGE_NOTE.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_MESSAGE_WARNING.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_MESSAGE_ERROR.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_MESSAGE_FAILURE.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_EVENT.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_LEFT.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_RIGHT.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_RANGE.createReferenceItem());
        this.addReferenceItem(VhdlStandardTypes.VAR_RANGE_REVERSE.createReferenceItem());
       
        
    }
    
}
