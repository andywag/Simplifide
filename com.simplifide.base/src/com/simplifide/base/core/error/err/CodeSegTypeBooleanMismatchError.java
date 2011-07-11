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
import com.simplifide.base.core.segment.CodeSegmentInt;

/**
 *
 * @author awagner
 */
public class CodeSegTypeBooleanMismatchError extends CodeSegTopError{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CodeSegmentInt out;



    /** Creates a new instance of CodeSegTypeMismatchError */
    public CodeSegTypeBooleanMismatchError() {}
    public CodeSegTypeBooleanMismatchError(LocalReference ref) {super(ref);}

  
    public CodeSegTypeBooleanMismatchError(LocalReference ref, CodeSegmentInt out)
    {
        super(ref);
        this.setOut(out);
    }

    public static TopError checkError(CodeSegmentInt out)
    {
        return checkError(null,out);
    }

    public static TopError checkError(LocalReference ref, CodeSegmentInt out)
    {
       // if (out.getType() != VhdlStandardPackage.getInstance().getBoolean1()) return new CodeSegTypeBooleanMismatchError(ref,out);
        
        return null;
    }

  public String getErrorMessageSmall()
  {
      return "Expression is not boolean";
  }

    public CodeSegmentInt getOut() {
        return out;
    }

    public void setOut(CodeSegmentInt out) {
        this.out = out;
    }

    


    
}
