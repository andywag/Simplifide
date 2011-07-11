/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.realvars;

import com.simplifide.base.core.var.types.NumericTypeVar;


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:33:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SystemParameter extends SystemVar
{

    public static final int RADIX_BINARY = 0;
    public static final int RADIX_OCTAL = 1;
    public static final int RADIX_DECIMAL = 2;
    public static final int RADIX_HEX = 3;
    
    private int radix;
    private String stringValue;
    
    public SystemParameter() {}
    
    public SystemParameter(String value, int radix) {
        super(value, NumericTypeVar.getInstanceRef(),new OperatingTypeVar.ConstantVar());
        this.stringValue = value;
        this.radix = radix;  
    }
    
    public String getHoverText() {
    	int negValue = this.getNegativeValue();
    	String ret = this.getHoverRadixText(this.radix) + " (" + this.getValue();
    	if (negValue < 0) ret = ret + "," + negValue +") ";
    	else ret += ")";
    	return ret;
    }
    
    public String getHoverRadixText(int rad) {
    	int value = this.getValue();
    	if (rad == RADIX_BINARY) return "'b" + Integer.toBinaryString(value);
    	else if (rad == RADIX_OCTAL) return "'o" + Integer.toOctalString(value);
    	else if (rad == RADIX_DECIMAL) return "'d" + Integer.toString(value);
    	else return "'h" + Integer.toHexString(value);
    }
    
    
    
    public int getVariableType() {
    	return SystemVar.CONSTANT;
    }

    private int getRealRadix() {
    	if      (this.getRadix() == RADIX_BINARY)  return 2;
    	else if (this.getRadix() == RADIX_OCTAL)   return 8;
    	else if (this.getRadix() == RADIX_DECIMAL) return 10;
    	else return 16;
    }
    
    public int getValue() {
    	return Integer.parseInt(this.getname(), this.getRealRadix());
    }
    
    public int getNegativeValue() {
    	int len = this.getname().length();
    	double max = Math.pow(getRealRadix(), len-1);
    
    	int value = this.getValue();
    	if (value > max) value = value - 2*(int)max;
    	return value;
    	
    }
    
   public Integer getNumericValue()
   {
       return Integer.valueOf(0);
   }

    public int getRadix() {
        return radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    
    public static class Width extends SystemParameter {
    	private int parWidth;
    	public Width(String value, int radix, int width) {
    		super(value, radix);
    		this.parWidth = width;
    	}
    	
    	public String getHoverText() {
    		return this.parWidth + super.getHoverText();
    	}
    	
		public void setParWidth(int parWidth) {
			this.parWidth = parWidth;
		}
		public int getParWidth() {
			return parWidth;
		}
    }
   

  
}
