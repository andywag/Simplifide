/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.warnings;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.error.err.TopError;
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


public final class LatchWarning extends TopWarning
{
    private static final long serialVersionUID = 1L;
 
    private ArrayList<ModuleObject> diffList;
    private ModuleObjectWithList<ModuleObject> totalList;

    public LatchWarning() {}
  
    public LatchWarning(ReferenceLocation loc, ArrayList<ModuleObject> diffList, ModuleObjectWithList<ModuleObject> totalList) {
      super("Latch Warning",loc);
      this.diffList = diffList;
      this.totalList = totalList;
  }
  
    public int getErrorType() {
          return TopError.TYPE_LATCH;
}
    
    public String getSensitivityList() {
        boolean first = true;
        String out = "";
        for (ModuleObject obj : this.totalList.getRealSelfList()) {
            if (!first) {
                out+=",";
            }
            first = false;
            out += obj.getname();
        }
        return out;
    }
    
  public String getErrorMessageSmall()
  {
      return "Latch Infered" + this.getDiffList() + " Not Declared";
  }

public void setDiffList(ArrayList<ModuleObject> diffList) {
    this.diffList = diffList;
}

public ArrayList<ModuleObject> getDiffList() {
    return diffList;
}

  

    
   

    


}
