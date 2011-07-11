/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.warnings;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;





/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @todo : Need to update actions
 */


public final class VariableNotUsedWarning extends TopWarning
{
    private static final long serialVersionUID = 1L;
 
   
    private ReferenceItem varRef;
    
    public VariableNotUsedWarning() {}
  
    public VariableNotUsedWarning(ReferenceLocation loc, ReferenceItem varRef) {
      super("Variable Not Used",loc);
      this.varRef = varRef;
    }
  
    public int getErrorType() {
          return TopError.TYPE_VARIABLE_NOT_USED;
    }
    
  public String getErrorMessageSmall(){
      return varRef.getDisplayName() + " is Not Used";
  }

  

    
   

    


}
