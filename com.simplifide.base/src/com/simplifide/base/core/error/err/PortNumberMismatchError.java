/*
 * CodeSegTypeMismatchError.java
 *
 * Created on February 27, 2006, 10:47 AM
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
public class PortNumberMismatchError extends TopError{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/** Creates a new instance of CodeSegTypeMismatchError */
    public PortNumberMismatchError() {}
    public PortNumberMismatchError(LocalReference ref) {super(ref);}

  
    
  public String getErrorMessageSmall()
  {
      
      return "Number of Ports not equal";
  }

   

    
}
