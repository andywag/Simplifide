/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.warnings;

import com.simplifide.base.basic.struct.reference.LocalReference;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.reference.ReferenceLocation;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */




public class TopWarning extends TopError
{

  

  public TopWarning() {}
  public TopWarning(String name1) {super(name1);}

  public TopWarning(String name, ReferenceLocation loc) {
          super(name,loc);
  }
  
  /**@deprecated */
  public TopWarning(LocalReference ref)
  {
      super(ref);
  }
  public int getErrorSeverity() {return TopError.WARNING;}
   
  public int getAnnotationType()
  {
    return TopError.WARNING;
  }

  public String getOutputType() {return "Warning : ";}


}
