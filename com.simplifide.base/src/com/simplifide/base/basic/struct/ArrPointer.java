/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Oct 18, 2004
 * Time: 8:54:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrPointer extends TopObjectBase {
    
    public static ArrPointer FALSE = new ArrPointer(true);
    
    
    private int top;
    private int bot;
    private boolean fake;
    
    public ArrPointer() {}
    public ArrPointer(boolean type) {this(0,0,type);}
    public ArrPointer(int top) {this(top,top);}
    public ArrPointer(int top, int bot) {this(top,bot,false);}
    public ArrPointer(int top, int bot, boolean fake) {
        super("");
        this.top = top;
        this.bot = bot;
        this.fake = fake;
    }
    
    /** This function retuns the pointer with the lowest possible indices if it is 
     *  within this pointer. If not returns null */
    public ArrPointer getBasePointer(ArrPointer point)
    {
        if (point.getWidth() <= this.getWidth()) return new ArrPointer(point.getWidth()-1,0); 
        return null;
    }
    
    public boolean isEquivalent(ArrPointer point)
    {
        if (this.getWidth() == point.getWidth()) return true;
        return false;
    }

    public int getWidth()
    {
        return this.top - this.bot + 1;
    }
    

   
    
    public ArrPointer removeArray() {
    	
        if (this.getnumber() > 0) return (ArrPointer) this.getObject(0);
        else return new ArrPointer(fake);
    }
    
    public int getArrIndex(int i) {
        return this.bot + i;
    }
    
    public String toString() {
        if (this.isFake()) return "FAKE";
        else if (this.getnumber() == 0) return this.getArrayName();
        else return this.getArrayName() + this.getObject(0);
    }
    
    public String getVhdlName() {
        return "(" + this.top + " downto " + this.bot + ")";
    }
    
    public String getVerilogName() {
        return "[" + this.top + ":" + this.bot + "]";
    }
    
    public String getArrayName() {
        if (this.isFake()) return "FAKE";
        else if (this.top == this.bot) return "[" + top + "]";
        else return "[" + top + ":" + bot + "]";
    }
    
    
    
    public int resolveTotWidth() {
        int totwidth = this.top - this.bot + 1;
        if (this.getnumber() != 0) {
            ArrPointer p = (ArrPointer) this.getObject(0);
            totwidth = totwidth*p.resolveTotWidth();
        }
        return totwidth;
    }
    
    public void updateLower(int baseWidth) {
        
        if (this.getnumber() == 0) {
            if (this.getDiffNumber() == 1) {
                this.top = baseWidth-1;
                this.bot = 0;
            } else {
                if (baseWidth != 1)
                    this.addObject(new ArrPointer(baseWidth,baseWidth));
            }
        } else {
            for (int i=0;i<this.getnumber();i++) {
                ArrPointer point = (ArrPointer) this.getObject(i);
                point.updateLower(baseWidth);
            }
        }
        
    }
    
    public String getSmallDisplayName() {
        if (this.isFake()) return "FAKE";
        if (this.top == this.bot) return "[" + top + "]";
        else return "[" + top + ":" + bot + "]";
    }
    
    public String getDisplayName() {
        String tstring = this.getSmallDisplayName();
        for (int i=0;i<this.getnumber();i++) {
            ArrPointer p = (ArrPointer) this.getObject(i);
            tstring += p.getDisplayName();
        }
        return tstring;
    }
    
    
    public int getDiffNumber() {
        return this.top - this.bot + 1;
    }
    
    public int getTop() {
        return top;
    }
    
    public void setTop(int top) {
        this.top = top;
    }
    
    public int getBot() {
        return bot;
    }
    
    public void setBot(int bot) {
        this.bot = bot;
    }
    
    public boolean isFake() {
        return fake;
    }
    
    public void setFake(boolean fake) {
        this.fake = fake;
    }
    
    
    
}
