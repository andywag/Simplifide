/*
 * TopASTNodeNew.java
 *
 * Created on April 4, 2007, 12:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.node;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Generified Version of Top AST Node
 * @author Andy
 * @param T tag
 */
public class TopASTNodeNew<T extends TopObjectBase> extends TopASTNode{
    
    private ArrayList<TopASTNode> nodeList;
    /** Creates a new instance of TopASTNodeNew */
    public TopASTNodeNew() {}

    private void deleteArrayList() {
        this.nodeList = null;
    }
    private void createArrayList() {
        nodeList = new ArrayList();
        TopASTNode child = this.getFirstASTChild();
        while (child != null) {
            nodeList.add(child);
            child = child.getNextASTSibling();
        }
    }
    
     /** Convenience method to convert module object to reference item 
      * @param node 
      * @param context 
      * @return 
      */ 
     protected ReferenceItem<ModuleObject> handleGenerate(TopASTNode node, ParseContext context) {
        ModuleObject nobj = (ModuleObject) node.generateModule(context);
        if (nobj instanceof ReferenceItem) {
            return (ReferenceItem<ModuleObject>) nobj;
        } 
        else if (nobj == null) {
            return null;
        }
        else {
            return nobj.createReferenceItem();
        }
    }
     

    
    /** Return the node at this child location 
     * @param index 
     * @return 
     */
    public TopASTNode getNode(int index) {
        return nodeList.get(index);
    }
    
   
    /**
     * 
     * @param context 
     * @return 
     */
    public T generateModuleNewer(ParseContext context) {
        return (T) this.generateModule(context);
    }
    
    /** Override with Generice Return Type */
    public T generateModule(ParseContext context) {
        ParseContext.Storage store = context.createStorage();
        T sc = null;
        try { 
            this.createArrayList();
            sc = this.generateModuleSmallNew(context);
        } catch (Exception e) {
            this.handleError(e, context);
        }
        finally {
            context.restoreStorage(store);
            this.deleteArrayList();
        }
        return sc;
    }

    /** Override with Generic Return Type */
    public T generateModuleSmallNew(ParseContext context) {
        return null;
    }
    
	public void addObjects(ReferenceItem parent, ModuleObject child1) {
		if (child1 == null) return;
		
		if (child1 instanceof ReferenceItem) {
			ReferenceItem child = (ReferenceItem) child1;
			ModuleObject childval = child.getObject();
			if (childval instanceof NoSortList) {
				NoSortList<ModuleObject> lis = (NoSortList) childval;
				for (ReferenceItem ref : lis.getGenericSelfList()) {
					parent.addReferenceItem(ref);
				}
			}
			else {
				parent.addReferenceItem(child);
			}
		}
		else if (child1 instanceof NoSortList) {
			NoSortList<ModuleObject> lis = (NoSortList) child1;
			for (ReferenceItem ref : lis.getGenericSelfList()) {
				parent.addReferenceItem(ref);
			}
		}
		
		
	}
	
	
	
   


    
    
}
