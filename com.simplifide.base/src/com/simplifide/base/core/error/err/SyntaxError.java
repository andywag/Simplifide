/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.err;

import antlr.RecognitionException;

import com.simplifide.base.core.reference.ReferenceLocation;




/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class SyntaxError extends TopError
{

  private RecognitionException exception;
  public SyntaxError() {init();}
  
  
  public SyntaxError(RecognitionException exception, ReferenceLocation ref) {
	  super("Syntax Error", ref);
	  this.exception = exception;
  }
  
  public int getErrorType() {
	  return TopError.TYPE_SYNTAX;
  }
  
  private void init()
  {

  }

  public String getErrorMessage() {
	  if (exception != null) return this.exception.getLocalizedMessage();
	  return super.getErrorMessage();
  }
  
  public String getErrorMessageSmall()
  {
      return "Syntax Error";
  }

    


}
