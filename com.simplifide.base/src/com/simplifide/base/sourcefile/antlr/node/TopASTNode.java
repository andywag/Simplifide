/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import java.util.ArrayList;
import java.util.List;

import antlr.Token;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenCopy;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class TopASTNode extends antlr.CommonASTWithHiddenTokens  {
    
   
	private static final long serialVersionUID = 1L;
	private TopASTToken token;
    // private NodePosition position;
    
    
    public TopASTNode() {}
    public TopASTNode(Token tok) {super(tok);}
    
    
    public TopASTNode formatBase() {return null;}
    
    public void format(FormatPosition pos) {
    	TopASTNode child = this.getFirstASTChild();
    	while (child != null) {
    		child.format(pos);
    		child = child.getNextASTSibling();
    	}
    }
    
    public void addFormatIndent(FormatPosition position) {
    	NodePosition pos = this.getPosition();
    	if (pos != null) {
    		FormatPosition npos = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
    		npos.setIndent(position.getIndentOrZero() + FormatSupport.getInstance().getIndent());
    		this.format(npos);
    	}
    
    }
    
    public int getIndentLength() {return 3;}
    
    
    public void initialize(Token tok) {
        super.initialize(tok);
        this.token = (TopASTToken) tok;
    }
    
   
    
    public int getChildPosition(int pos) {
    	TopASTNode child = this.getFirstASTChild();
    	int ind = 0;
    	while (child != null) {
    		NodePosition nodePos = child.getPosition();
    		if (nodePos == null) return ind;
    		int spos = nodePos.getStartPos();
    		int epos = nodePos.getEndPos();
    		if (pos >= spos && pos <= epos) return ind;
    		ind = ind + 1;
    		child = child.getNextASTSibling();
    	}
    	return ind;
    }
    
    /** Used to specify whether this is an extra node which doesn't need to be stored */
    public boolean canPrune() {return false;}
    /** Operation to prune the node and return a subnode which is actually useful */
    public TopASTNode prunedNode() {return null;}
    
   
    public TopASTNode getPortListNode() {
    	return null;
    }
    
    /** Delete the node to free up memory */
    public void delete() {
        TopASTNode child = this.getFirstASTChild();
        while (child != null) {
            child.delete();
            child = child.getNextASTSibling();
        }
    }
    
    
    /**
     * Method which determines whether this node should be folded
     * @return 
     */
    public boolean canFold() {return false;}
    /**
     * Method which gives the fold name for this fold 
     * @return 
     */
    public String getFoldName(){return "Fold";}
    
    public String getRealTextSpace() {
    	 if (this.getNumberOfChildren() == 0) return this.getText();
         
         StringBuilder tstr = new StringBuilder();
         TopASTNode child = this.getFirstASTChild();
         while (child != null) {
             if (child.getNumberOfChildren() == 0) {
             	TopASTToken tok = child.getToken();
             	if (tok != null) tstr.append(tok.getText() + " ");
             }
             else tstr.append(child.getRealTextSpace());
             child = child.getNextASTSibling();
         }
         return tstr.toString();
    }
    
    /** Gets the text from all of the children nodes */
    public String getRealText() {
        if (this.getNumberOfChildren() == 0) return this.getText();
        
        StringBuilder tstr = new StringBuilder();
        TopASTNode child = this.getFirstASTChild();
        while (child != null) {
            if (child.getNumberOfChildren() == 0) {
            	TopASTToken tok = child.getToken();
            	if (tok != null) tstr.append(tok.getText());
            }
            else tstr.append(child.getRealText());
            child = child.getNextASTSibling();
        }
        return tstr.toString();
    }
    
    /**
     * Operation when an error is found
     */
    public void handleError(Exception e, ParseContext context) {
    	ReferenceLocation loc = context.createReferenceLocation(this);
    	
        BaseLog.logParseError("Parse Error in " + loc.getUri().toString() + " at Line" + loc.getLine(), e);
    	
        //this.addError(new ParserError(null,""),context);
    }
    
    
    /** Method used to find the context of the node, used for completion and navigation operations */
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        this.resolveContext(context);
        return null;
    }
    
    /** Method used to find the context of the node, used for completion and navigation operations */

    public void resolveContext(ParseContext context) {}
    
    /**
     * 
     * @param context 
     * @return 
     */
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = (TopASTNode) this.getFirstChild();
        TopObjectBase uobj = null;
        
        // Not sure if this is completely desirable but it is the least error prone
        
        while (child != null) {
            uobj = child.generateModule(context);
            child = (TopASTNode) child.getNextSibling();
        }
        
        
        if (this.getNumberOfChildren() == 1) return uobj;
        return null;
    }
    
    /**
     * 
     * @param context 
     * @param searchMode 
     * @param type 
     * @return 
     */
    public TopObjectBase generateSearchTypeNew(ParseContext context, int searchMode, int type) {
        int oldType = context.getType();
        int oldMode = context.getSearchMode();
        context.setSearchMode(searchMode);
        context.setType(type);
        TopObjectBase sc = this.generateModule(context);
        context.setSearchMode(oldMode);
        context.setType(oldType);
        return sc;
    }
    
    
    /**
     * 
     * @param context 
     * @return 
     */
    public TopObjectBase generateModule(ParseContext context) {
        ParseContext.Storage store = context.createStorage();
        try {
            
            ModuleObject sc = (ModuleObject) this.generateModuleSmallNew(context);
            context.restoreStorage(store);
            return sc;
        } catch (Exception e) {
            this.handleError(e, context);
            context.restoreStorage(store);
            return null;
        }
    }
    
   
    
    
    private List<TopASTNode> getAllNodes() {
    	ArrayList<TopASTNode> nlist = new ArrayList<TopASTNode>();
    	TopASTNode child = this.getFirstASTChild();
    	while (child != null) {
    		nlist.add(child);
    		child = child.getNextASTSibling();
    	}
    	return nlist;
    }
    /** Returns the last true leaf node. Returns null if none exist */
    private TopASTNode getLastLeafNode() {
        if (this.getNumberOfChildren() == 0) {
            if (this.getToken() != null) return this;
            else return null;
        } else {
        	List<TopASTNode> nodes = this.getAllNodes();
            for (int i=nodes.size()-1;i>=0;i--) {
                TopASTNode child = nodes.get(i).getLastLeafNode();
                if (child != null) return child;
            }
        }
        return null;
    }
    
    /** Returns the first true leaf node. Returns null if none exist */
    public TopASTNode getFirstLeafNode() {
        if (this.getNumberOfChildren() == 0) {
            if (this.getToken() != null) return this;
            else return null;
        } else {
            TopASTNode child = this.getFirstASTChild();
            while (child != null) {
                TopASTNode rchild = child.getFirstLeafNode();
                if (rchild != null) return rchild;
                child = child.getNextASTSibling();
            }
        }
        return null;
    }
    

    
    /**
     * Returns the files child. Convenience method which converts to TopASTNode
     */
    public TopASTNode getFirstASTChild() {
        return (TopASTNode) this.getFirstChild();
    }
    
    public TopASTNode getLastASTChild() {
    	TopASTNode child = this.getFirstASTChild();
    	TopASTNode lastChild = child;
    	while (child != null) {
    		lastChild = child;
    		child = child.getNextASTSibling();
    	}
    	return lastChild;
    }
    
    
    /**
     * Convenience method to convert next sibling to TopASTNode 
     */
    public TopASTNode getNextASTSibling() {
        return (TopASTNode) this.getNextSibling();
    }
    
   
    public TopASTToken getToken() {
        return token;
    }
    
    public void setToken(TopASTToken token) {
        this.token = token;
    }
    
 
    /**
     * Adds an error to the list of errors
     */
    public void addError(TopError error, ParseContext context) {
    	 
    	 context.getErrorList().add(error);
    }
    
    
    
    /**
     * Returns the node position which appears to be very inneficient
     */
    public NodePosition getPosition() {
        TopASTNode firstNode = this.getFirstLeafNode();
        NodePosition npos = null;

        if (firstNode == null) {
        	return null;
        	/*if (this.getNextASTSibling() != null) {
        		return this.getNextASTSibling().getPosition();
        	}*/
        	/*if (this.getNextASTSibling().getPosition() != null) {
        		TopASTNode node = this.getNextASTSibling();
        		npos = node.getPosition();
        		npos.setEndPos(npos.getStartPos());
        	}*/
        }
                
        else {
            TopASTNode lastNode = this.getLastLeafNode();
            TopASTToken firstToken = firstNode.getToken();
            TopASTToken lastToken  = lastNode.getToken();

            if (lastNode == null || firstToken == null || lastToken == null) return null;

            npos = firstToken.getNodePosition();
            if (!(firstToken instanceof TopASTTokenCopy)) { // Special Case for `identifier
                npos.setEndPos(lastToken.getEndPosition());   
            }
        }
       
        return npos;
    }
    
    
    /* The following methods are used to handle adding modules to block statements */
    
    private void addContextObject(ModuleObject sc, ParseContext context, ReferenceLocation loc) {
        if (sc != null) {
            context.getActiveReference().addModuleObject(sc,loc);
        }
    }
    
    protected ReferenceItem getCurrentReferenceItem(ParseContext context) {
        return context.getActiveReference();
    }
    
    private void addReferenceObject(ParseContext contect, ReferenceItem item, TopASTNode child) {
        this.getCurrentReferenceItem(contect).addReferenceItem(item);
        TopASTToken tok = child.getFirstLeafNode().getToken();
        if (tok.getDoc() != null) {
            item.getObject().changeDoc(tok.getDoc());
        }
    }
    
    /** Method which handles adding multiple objects to a given item. It handles 
     * when the object isn't a reference item and additionally handles hdldoc
     * @param context
     * @return
     */
    public TopObjectBase generateModuleStatements(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        ReferenceItem currentReference = this.getCurrentReferenceItem(context);
        while (child != null) {
            ReferenceLocation loc = context.createReferenceLocation(child);
            ModuleObject li = (ModuleObject) child.generateModule(context);
            // This needs to go. I am keeping this only because I have not removed the module object
            // part of this expression yet. This is quite ugly and needs to be fixed
            // Slightly Fixed but still requires the instanceof expression...
            if (li instanceof NoSortList) {
                NoSortList simple = (NoSortList) li;
                List<ReferenceItem> refList = simple.getGenericSelfList();
                for (ReferenceItem obj : refList) {
                    this.addReferenceObject(context, obj, child);
                }
            } else if (li instanceof ReferenceItem) {
                this.addReferenceObject(context, (ReferenceItem)li,child);
            } else this.addContextObject(li,context,loc);
            child = child.getNextASTSibling();
        }
        return null;
    }
    
    /** Check this node + the following to see if they are a match for a reference check */
    protected void handleFindUsagesOverNodes(ParseContext context, TopASTNode node, int type, int number) {
        TopASTNode node2 = (TopASTNode) node;
        for (int i=0;i<number;i++) {
            if (node2 instanceof IdentASTNode) {
                this.checkNodeForFindUsage(context, node2, type);
                break;
            }
            node2 = node2.getNextASTSibling();
            if (node2 == null) break;
        }
       
    }
    
    public void checkNodeForFindUsage(ParseContext  context, TopASTNode node, int compType) {
    	ParseContextUsages contextUsage = (ParseContextUsages) context;
    	String text = node.getRealText();
    	//List<EditorFindItem> itemList = contextUsage.getFindItem();
    	//for (EditorFindItem item : itemList) {
    	EditorFindItem item = contextUsage.getEditorFindItem();
    	if (item.getItem().getname().equalsIgnoreCase(text)) {
    		ReferenceLocation location = context.createReferenceLocation(node);
    		ReferenceUsage usage = new ReferenceUsage(item.getItem(),"",location,compType);
    		contextUsage.addUsage(usage);
    	}

    }
    
   
    /* End of block statement methods */
   
    
    private void generateUsed(ReferenceItem item) {
        if (item.getObject() instanceof SystemVar) {
            SystemVar tvar = (SystemVar) item.getObject();
            tvar.setUsed(true);
        }
    }
    
    private void generateAssigned(ReferenceItem item) {
        if (item.getObject() instanceof SystemVar) {
            SystemVar tvar = (SystemVar) item.getObject();
            tvar.setAssigned(true);
        }
    }
    
    /** Sets all of the variables which are used to used */
    public  void generateUsedList(ModuleObjectWithList<ModuleObject> depList) {
        for (ReferenceItem dep : depList.getGenericSelfList()) {
            this.generateUsed(dep);
        }
    }
    /** Sets all of the variables which are assigned to assigned */
    public void generateAssignedList(ModuleObjectWithList<ModuleObject> outList) {
        for (ReferenceItem dep : outList.getGenericSelfList()) {
            this.generateAssigned(dep);
        }
    }
    
    protected ReferenceItem convertModuleObject(ModuleObject nobj) {
        if (nobj instanceof ReferenceItem) {
            return (ReferenceItem<ModuleObject>) nobj;
        } else if (nobj != null){
            return nobj.createReferenceItem();
        }
        else {
            return null;
        }
    }
    
    protected void handleDoc(ModuleObject dec) {
	    TopASTToken tok = getFirstLeafNode().getToken();
	    HdlDoc doc = tok.getDoc();
		dec.setDoc(doc);
		dec.updateHdlDoc(dec);
	}
    
    
}



