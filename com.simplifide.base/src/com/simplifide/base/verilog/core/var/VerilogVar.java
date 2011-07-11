/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.var;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;

public class VerilogVar extends SystemVar{

	private ReferenceItem<VarRange> rangeRef;
	
	public VerilogVar(String name, ReferenceItem type, OperatingTypeVar op) {
        super(name,type,op);
        
    }
	
	 public ReferenceItem<ModuleObjectWithList> getOutputs() {
	        if (this.getSearchType() == ReferenceUtilities.REF_SIGNAL ||
	        	this.getSearchType() == ReferenceUtilities.REF_VARIABLE) {
	            return ModuleObjectWithList.singleton(this).createReferenceItem();
	        }
	        return super.getOutputs();
	    }

	 
	public String getTypeString() {
		switch(this.getOpTypeVar().getVariableType()) {
			case SystemVar.SIGNAL    :
			case SystemVar.VARIABLE  : 
			case SystemVar.REG       : 
			case SystemVar.WIRE      : return "signal";
			case SystemVar.CONSTANT  : return "constant";
			case SystemVar.INPUT     : return "input";
			case SystemVar.OUTPUT    : return "output"; 
			case SystemVar.INOUT     : return "inout";
			case SystemVar.PARAMETER : return "parameter";
		}
		return "";
		//if (this.getOpTypeVar().getVariableType() == SystemVar.SIGNAL) return "signal";
		//else if (this.getSearchType() == ReferenceUtilities.REF_VARIABLE) return "variable";
		//else if (this.getOpTypeVar().getVariableType() == OperatingTypeVar.TYPE_CONSTANT) return "constant";
		
	}
	 
	public String createRegDeclaration(String uname) {
		String tdec = this.getType().getVerilogName() + " " + uname;
		return "reg " + tdec + ";";
	}
	 
	public String createWireDeclaration(String uname) {
		String tdec = this.getType().getVerilogName() + " " + uname;
		return "wire " + tdec + ";";
	}
    
    public String createInputDeclaration(String uname) {
    	String tdec = this.getType().getVerilogName() + " " + uname;
		return "input " + tdec + ";";
    }
    
    public String createOutputDeclaration(String uname) {
    	String tdec = this.getType().getVerilogName() + " " + uname;
		return "output " + tdec + ";";
    }
	 
	public void setRangeRef(ReferenceItem<VarRange> rangeRef) {
		this.rangeRef = rangeRef;
	}

	public ReferenceItem<VarRange> getRangeRef() {
		return rangeRef;
	}
	
	
}
