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
import com.simplifide.base.basic.struct.reference.LocalReference;


/**
 *
 * @author awagner
 */
public class AlreadyDefinedError extends TopError{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of GeneralNotDefined */
    private ModuleObject var;
    
    public AlreadyDefinedError() {}
    public AlreadyDefinedError(ModuleObject var)
    {
       this(null,var);
    }
   
    public AlreadyDefinedError(LocalReference ref, ModuleObject var)
    {
        super(ref);
        this.var = var;
    }

   /*
    public String getErrorMessageSmall()
    {
        return var.getDisplayName() + " Already Defined in Context";
    }

     public Action[] getActions()
    {
      return new Action[]{this.returnGoToAction()};
    }

    private Action returnGoToAction()
    {
        String str = "Go to First Assignment of " + var.getDisplayName();
        LocalReference ref = (LocalReference) var.getReference(ModuleObject.ASSIGNMENT);
        return new GoToReferenceAction(str,ref);
    }
   */

    public ModuleObject getVar() {
        return var;
    }

    public void setVar(ModuleObject var) {
        this.var = var;
    }
    
}
