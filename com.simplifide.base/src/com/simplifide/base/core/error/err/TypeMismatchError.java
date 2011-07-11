/*
 * CodeSegTypeMismatchError.java
 *
 * Created on February 27, 2006, 10:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error.err;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author awagner
 */
public class TypeMismatchError extends TopError{

    private ReferenceItem<? extends ModuleObject> out;
    private ReferenceItem<? extends ModuleObject> in;


    /** Creates a new instance of CodeSegTypeMismatchError */
    public TypeMismatchError() {}

    public TypeMismatchError(ReferenceLocation loc,  ReferenceItem<? extends ModuleObject> out, ReferenceItem<? extends ModuleObject> in)
    {
        super("Type Mismatch",loc);
        this.setOut(out);
        this.setIn(in);
    }

    public String getErrorMessageSmall()
  {
      if (getOut() != null && getIn() != null)
            return ("Type Mismatch : Found (" + getOut().getDisplayName() + ")" + " Required ( " + getIn().getDisplayName() +")" );
      
      return "Type Mismatch";
  }
   
    
  

    public ReferenceItem<? extends ModuleObject> getOut() {
        return out;
    }

    public void setOut(ReferenceItem<? extends ModuleObject> out) {
        this.out = out;
    }

    public ReferenceItem<? extends ModuleObject> getIn() {
        return in;
    }

    public void setIn(ReferenceItem<? extends ModuleObject> in) {
        this.in = in;
    }

   


    
}
