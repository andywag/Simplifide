/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct.change;

import com.simplifide.base.basic.struct.TopObjectBase;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jan 20, 2003
 * Time: 12:45:18 PM
 * To change this template use Options | File Templates.
 */
public class ListObjectChange extends TopObjectChange
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ADD = 10;
    public static final int REMOVE = 11;
    public static final int DELETE = 12;
    public static final int NUMBERCHANGE = 13;


    private int index;

    //public ListObjectChange() {super();}
    public ListObjectChange(TopObjectBase top1,TopObjectBase bot1,int chval,int ind1)
    {
        super(top1,bot1,chval);
        setIndex(ind1);
    }
    
    public String changeName()
    {
        if (this.getType() == ADD) return "Add Object";
        if (this.getType() == REMOVE) return "Remove Object";
        if (this.getType()== DELETE) return "Delete Object";
        return "other";
            
    }

  

    public final int getIndex() {
        return index;
    }

    private void setIndex(int index) {
        this.index = index;
    }

}
