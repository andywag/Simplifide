/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.port;

import java.util.List;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

/* Need to Split Up Instances from Module Ports ASAP. The operation of this is also
 * kind of a kludge.
 */

public class PortListASTNode extends TopASTNodeGeneric<ReferenceItem<PortList>> {
    
	public PortListASTNode() {}
    
    
    
    private void checkDoc(TopASTNode node,List<ReferenceItem<? extends PortTop>> lis) {
    	if (node != null && lis != null) {
    		TopASTToken tok = node.getToken();
    		if (tok != null) {
    			HdlDoc doc = tok.getDoc();
    			lis.get(0).getObject().setDoc(doc);
    		}
    	}
    }
    
    public ReferenceItem<PortList> createObjectSmall(ParseContext context) {
        

        PortList outList = new PortList("List");
        TopASTNode ch = this.getFirstASTChild(); // First Port
        NoSortList<PortTop> sc = null;
        int index = 0;
        while (ch != null) {
            sc = (NoSortList) ch.generateModule(context);
            List<ReferenceItem<? extends PortTop>> portList = null;
            if (sc!= null) {
                portList = sc.getGenericSelfList();
                for (ReferenceItem<? extends PortTop> portItem : portList) {
                    portItem.getObject().setPortNumber(index); // Set the index of this port...
                    outList.addReferenceItem(portItem);
                    index++;
                }
            }
            ch = ch.getNextASTSibling(); // semicolon
            this.checkDoc(ch, portList);
            if (ch != null)
                ch = ch.getNextASTSibling();
        }
        
        return outList.createReferenceItem();
    }
    
    
}
