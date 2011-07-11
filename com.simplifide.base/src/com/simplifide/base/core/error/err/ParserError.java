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

public final class ParserError extends TopError
{


 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String errorMessage;

  public ParserError() {init();}
  
  public ParserError(LocalReference ref, String errMessage)
  {
      super(ref);
      this.errorMessage = errMessage;
  }
  
 public int getAnnotationType()
 {
    return TopError.PARSE;
 }
  
 public int getErrorSeverity() {return TopError.PARSE;}
 
  private void init()
  {

  }

  public String getErrorMessageSmall()
  {
      return this.errorMessage;
  }

  public String getOutputMessageSmall() {return "Tool Failed Parsing";}

    


}
