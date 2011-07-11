/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.instance.InstancePortListASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlInstancePortListASTNode extends InstancePortListASTNode
{


	private static final long serialVersionUID = 1L;

	 public void format(FormatPosition position) {
			NodePosition pos = this.getPosition();
			FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
			newPosition.setIndent(FormatSupport.getInstance().getModuleIndent());
			newPosition.setMinimum(FormatSupport.getInstance().getModuleIndent());
			super.format(newPosition);
	 }
	

    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
    	PortList portList = new PortList("Ports");
    	//NoSortList<PortConnect> list = new NoSortList<PortConnect>("PortList");
        TopASTNode ch = this.getFirstASTChild();
        while (ch != null)
        {
            ReferenceItem<PortConnect> port = (ReferenceItem<PortConnect>) ch.generateModule(context);
            if (port != null) {
                portList.addReferenceItem(port);
            }
            ch = ch.getNextASTSibling(); // Port
            if (ch != null) ch = ch.getNextASTSibling(); // ,
        }
        return portList.createReferenceItem();
    }

   




}
