/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.err;

import com.simplifide.base.basic.struct.reference.LocalReference;




/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class GeneralError extends TopError
{


 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String errorMessage;

  public GeneralError() {init();}
  public GeneralError( String errMessage) {this(null,errMessage);}
  public GeneralError(LocalReference ref, String errMessage)
  {
      super(ref);
      this.errorMessage = errMessage;
  }
  
 
  
  private void init()
  {

  }

  public String getErrorMessageSmall()
  {
      return this.errorMessage;
  }

    


}
