/*
 * GeneralNotDefined.java
 *
 * Created on January 16, 2006, 7:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error.err;

import com.simplifide.base.basic.struct.reference.LocalReference;

/**
 *
 * @author awagner
 */
public class NotDefinedInContextError extends TopError{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of GeneralNotDefined */

    private String objName;
    
    public NotDefinedInContextError() {} 
   
   
    public NotDefinedInContextError(int type) {this(null,type);}
    public NotDefinedInContextError(int type,String name)
    {
        this(null,type);
        this.objName = name;
    }

    public NotDefinedInContextError(LocalReference ref, int type)
    {
        super(ref);
        //this.type = type;
    }

   
    public String getErrorMessageSmall()
    {
        /*
        String tstr = "";
        if (this.type == ObjectFinder.TYPE) tstr = "Type";
        else if (this.type == ObjectFinder.VAR) tstr = "Variable";
        else if (this.type == ObjectFinder.INSTANCE) tstr = "Instance";
        else if (this.type == ObjectFinder.PACKAGE) tstr = "Package";
        else if (this.type == ObjectFinder.PORT) tstr = "Port";
        else if (this.type == ObjectFinder.PROJECT) tstr = "Library";
        else tstr = "Segment";
        return tstr + " Not Defined In Context";
         */
        return "Disabled";
    }

    /*
     public Action[] getActions()
    {
      return this.returnCreateActions();
    }

     */
    
  
 

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }
    
}
