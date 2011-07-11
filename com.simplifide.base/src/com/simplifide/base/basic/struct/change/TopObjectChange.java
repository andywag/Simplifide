/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct.change;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jan 23, 2003
 * Time: 6:09:54 PM
 * To change this template use Options | File Templates.
 */

import javax.swing.event.ChangeEvent;

import com.simplifide.base.basic.struct.TopObject;

public class TopObjectChange extends ChangeEvent
{
    public static final int CHANGE = 0;
    public static final int DELETE = 1;
    public static final int REPLACE = 2;

    public static final int NAMECHANGE = 3;
    public static final int DESCCHANGE = 4;

    public static final int ERRORADD = 5;
    public static final int ERRORREMOVE = 6;

    private TopObject topValue;

    private int type;


    public TopObjectChange(TopObject top1,TopObject bot1,int typeval)
    {
        super(bot1);
        this.setTopValue(top1);
        this.setType(typeval);

    }
    
    public String toString()
    {
        return "Change Event(" + changeName() + ")"  + "From" + this.topValue;
    }
    
    public String changeName()
    {
        if (type == CHANGE) return "Change";
        if (type == DELETE) return "Delete";
        if (type == REPLACE) return "Replace";
        if (type == NAMECHANGE) return "Name Change";
        if (type == DESCCHANGE) return "Description Change";
        else return "Other";
    }

    public final TopObject getTopValue() {
        return topValue;
    }

    private void setTopValue(TopObject topValue) {
        this.topValue = topValue;
    }

    public int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    public final TopObject getBotValue() {
        return (TopObject) this.getSource();
    }


}
