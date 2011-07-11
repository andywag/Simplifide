/*
 * CodeSegTypeMismatchError.java
 *
 * Created on February 27, 2006, 10:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error.err;

import java.util.ArrayList;

import javax.swing.Action;

import com.simplifide.base.basic.struct.reference.LocalReference;
import com.simplifide.base.core.segment.CodeSegmentInt;
import com.simplifide.base.core.var.types.TypeVar;



/**
 *
 * @author awagner
 */
public class CodeSegTypeMismatchError extends CodeSegTopError{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CodeSegmentInt out;
    private CodeSegmentInt in;


    /** Creates a new instance of CodeSegTypeMismatchError */
    public CodeSegTypeMismatchError() {}
    public CodeSegTypeMismatchError(LocalReference ref) {super(ref);}

  
    public CodeSegTypeMismatchError(LocalReference ref, CodeSegmentInt out, CodeSegmentInt in)
    {
        super(ref);
        this.setOut(out);
        this.setIn(in);
    }

    public static TopError checkError(CodeSegmentInt out, CodeSegmentInt in)
    {
        return checkError(null,out,in);
    }

    public static TopError checkError(LocalReference ref, CodeSegmentInt out, CodeSegmentInt in)
    {

        TypeVar intype, outtype;
        if (out != null && in != null)
        {
            intype = in.getType();
            outtype = out.getType();
            if (intype != null && outtype != null)
            {
            //    if (!intype.checkMatch(outtype)) return new CodeSegTypeMismatchError(ref,out,in);
            }
        }
        
        return null;
    }

     public Action[] getActions()
     {
        return this.returnCreateActions();
     }

     private Action[] returnCreateActions()
     {
        ArrayList<Action> list = new ArrayList<Action>();
        Action act1 = this.createChangeTypeAction(this.in,this.out);
        Action act2 = this.createChangeTypeAction(this.out,this.in);
        if (act1 != null) list.add(act1);
        if (act2 != null) list.add(act2);
        return (Action[]) list.toArray(new Action[1]);
     }

     /** @todo : Need to update the action handling for text */
     public Action createChangeTypeAction(CodeSegmentInt type1, CodeSegmentInt type2)
     {
         
         /*
         if (type1 instanceof SystemVar)
         {
             SystemVar tvar = (SystemVar) type1;
             String actionText = "Change " + type1 + " To " + type2.getType();
             LocalReference ref = (LocalReference) tvar.getReference(SystemVar.TYPEREFERENCE);
             if (ref != null)
             {
                ReplaceTextAction act = new ReplaceTextStringAction(actionText,ref,type2.getType().toString());
                return act;
             }
         }
         return null;
          */
         return null;
     }

  public String getErrorMessageSmall()
  {
      if (out != null && in != null)
          if (out.getType() != null && in.getType() != null)
            return ("Type Mismatch " + out.getType().getDisplayName() + " -- " + in.getType().getDisplayName() );
      
      return "Type Mismatch";
  }

    public CodeSegmentInt getOut() {
        return out;
    }

    public void setOut(CodeSegmentInt out) {
        this.out = out;
    }

    public CodeSegmentInt getIn() {
        return in;
    }

    public void setIn(CodeSegmentInt in) {
        this.in = in;
    }


    
}
