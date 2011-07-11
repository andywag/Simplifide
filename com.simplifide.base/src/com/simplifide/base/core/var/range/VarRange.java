/*
 * VarRange.java
 *
 * Created on March 16, 2007, 4:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.var.range;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemParameter;

/**
 *
 * @author Andy
 */
public class VarRange extends ModuleObjectNew{
    
    public static final int DOWN = 0;
    public static final int UP   = 1;
    public static final int NONE = 2;
    
    private ReferenceItem<ModuleObjectNew> top;
    private ReferenceItem<ModuleObjectNew> bot;
    private int direction;
    /** Creates a new instance of VarRange */
    public VarRange() {}
    public VarRange(ReferenceItem top, ReferenceItem bot, int dir) {
        super("Range");
        this.top = top;
        this.bot = bot;
        this.direction = dir;
    }
    
    /** @todo : Clearly not properly supported */
    public VarRange(int top, int bot) {
        SystemParameter topval = new SystemParameter(Integer.toString(top),10);
        SystemParameter botval = new SystemParameter(Integer.toString(bot),10);
        this.top = topval.createReferenceItem();
        this.bot = botval.createReferenceItem();
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
    	ModuleObjectWithList outlist = new ModuleObjectWithList();
    	if (top != null) outlist.addAll(top.getDependants().getObject());
    	if (bot != null) outlist.addAll(bot.getDependants().getObject());
    	return outlist.createReferenceItem();
    }
    
    
    public int getWidth() {
    	ModuleObject obj = top.getObject();
    	if (obj instanceof SystemParameter) {
    		SystemParameter param = (SystemParameter) obj;
    		return param.getValue() + 1;
    	}
    	return 0;
    }
    
    public String getTopValue() {
    	if (top != null) {
    		return top.getDisplayName();
    	}
    	return "";
    }

    public String getBottomValue() {
    	if (bot != null) {
    		return bot.getDisplayName();
    	}
    	return "";
    }
    
    public String getVerilogName() {
    	
    	if (top == null) {
    		return "";
    	}
    	else {
    		String utop = top.getDisplayName();
    		String ubot = bot.getDisplayName();
    		if (utop.equalsIgnoreCase(ubot)) {
    			return "[" + utop + "]";
    		}
    		else {
    			return "[" + utop + ":" + ubot + "]";
    		}
    	}
    }
    
    private String getStringDirection() {
    	if (this.direction == UP) return "to";
    	else return "downto";
    }
    
    public String getVhdlName() {
    	
    	if (top == null) {
    		return "";
    	}
    	else {
    		String utop = top.getDisplayName();
    		String ubot = bot.getDisplayName();
    		if (utop.equalsIgnoreCase(ubot)) {
    			return "(" + utop + ")";
    		}
    		else {
    			return "(" + utop + " " + this.getStringDirection() + " " + ubot + ")";
    		}
    	}
    	
    
    }
    
    
    
    public String getDisplayName() {
        String dirString;
        if (this.direction == DOWN) dirString = " downto ";
        else dirString = " to ";
        String topStr = "???";
        String botStr = "???";
        if (top != null) topStr = top.getDisplayName();
        if (bot != null) botStr = bot.getDisplayName();
        String ustr = topStr +  dirString  + botStr;
        return StringOps.addParens(ustr);
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_VAR_RANGE;
    }
    
    public ReferenceItem<ModuleObjectNew> getTop() {
        return top;
    }

    public void setTop(ReferenceItem<ModuleObjectNew> top) {
        this.top = top;
    }

    public ReferenceItem<ModuleObjectNew> getBot() {
        return bot;
    }

    public void setBot(ReferenceItem<ModuleObjectNew> bot) {
        this.bot = bot;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
}
