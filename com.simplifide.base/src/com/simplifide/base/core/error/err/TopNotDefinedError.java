/*
 * GeneralNotDefined.java
 *
 * Created on January 16, 2006, 7:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error.err;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author awagner
 */
public class TopNotDefinedError extends TopError{
    
    /** Creates a new instance of GeneralNotDefined */

   
    
    private ModuleObject object; 
    private int type;
	
    public TopNotDefinedError() {} 
   
   
   
    public TopNotDefinedError(ReferenceLocation loc, ModuleObject object ,int type) {
    	super("Not Defined Error",loc);
    	this.object = object;
    	this.type = type;
    }
   
    public int getErrorType() {
  	  return TopError.TYPE_NOTDEF;
    }
   
    
    public String getErrorMessageSmall() {
    	String outstr = "Object Not Defined";
    	if (object != null)
    		outstr += StringOps.addParens(object.getname());
        return outstr;
    }

 
   

   
   
    
}
