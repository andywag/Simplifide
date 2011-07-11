/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.error.err;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.basic.struct.reference.LocalReference;
import com.simplifide.base.basic.util.IDEout;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.reference.ReferenceLocation;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */




public class TopError extends TopObjectBase 
{

    public static final int ERROR = 0;//IMarker.SEVERITY_ERROR;
    public static final int WARNING = 1;//IMarker.SEVERITY_WARNING;
    public static final int PARSE = 2;//IMarker.SEVERITY_WARNING;
    
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_SYNTAX = 1;
    public static final int TYPE_NOTDEF = 2;
    public static final int TYPE_MISMATCH = 3;
    
    public static final int TYPE_LATCH = 4;
    public static final int TYPE_VARIABLE_NOT_ASSIGNED = 5;
    public static final int TYPE_VARIABLE_NOT_USED = 6;
    
    //public static String ERROR_ID = Activator.PLUGIN_ID + ".syntaxMarker";

  //protected int type;
  /** @deprecated */
  private LocalReference reference;
  
  private ReferenceLocation location;
  
  public TopError() {}
  public TopError(String name) {super(name);}

  public TopError(String name, ReferenceLocation loc) {
	  super(name);
	  this.location = loc;  
  }

  
  public TopError(LocalReference ref)
  {
      this.reference = ref;
  }
  
  public int getErrorSeverity() {return TopError.ERROR;}
  
  public String getMarkerID() {
	  //return ERROR_ID;
	  return "";
  }
  
  public int getErrorType() {
	  return TopError.TYPE_UNKNOWN;
  }
  
 
  
  
  public int getStart()
  {
      
    if (this.reference != null)
        if (this.reference.getPosition() != null)
           return this.reference.getPosition().getStartPos();
    return 0;
  }
  
  public int getEnd()
  {
      if (this.reference != null)
         if (this.reference.getPosition() != null)
            return this.reference.getPosition().getEndPos();
      return 0;
  }  
  
   public String toString()
   {
       return this.getErrorMessage();
   }

  public Action[] getActions()
  {
      return new Action[0];// {new DumbAction()};
  }

  public String getErrorMessage()
  {
      return this.getErrorMessageSmall();
      /*+ "(" + this.getLineNumber() + "," +
              this.getColNumber() + "," + this.getLength() + ")";
  */
       }

  public String getErrorMessageSmall()
  {
      return "Bad Error";
  }

  public int getAnnotationType()
  {
    return ERROR;
  }




   

    public LocalReference getReference() {
        return reference;
    }

    public void setReference(LocalReference reference) {
        this.reference = reference;
    }

    public String getOutputMessage() {
        String pos = this.getOutputType();
        pos += reference.getSource().getname();
        pos += StringOps.addParens("" + reference.getPosition().getStartLine() + "," + reference.getPosition().getStartCol());
        pos += " : " + this.getOutputMessageSmall();
        return pos;
    }

    public String getOutputMessageSmall() {return this.getErrorMessageSmall();}
    public String getOutputType() {return "Error : ";}


    public void setLocation(ReferenceLocation location) {
		this.location = location;
	}
	public ReferenceLocation getLocation() {
		return location;
	}


	public class DumbAction extends AbstractAction
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DumbAction()
        {
            super("Dumb Action");
            this.setEnabled(true);
        }

        public void actionPerformed(ActionEvent e) {

            IDEout.printlnOver("Acted Upon");
        }


    }

   



}
