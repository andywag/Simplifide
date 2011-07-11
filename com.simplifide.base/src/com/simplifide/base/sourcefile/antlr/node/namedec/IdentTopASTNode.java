/*
 * NameTopASTNode.java
 *
 * Created on January 16, 2006, 6:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.error.ErrorOptions;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.error.err.TopNotDefinedError;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.newfunction.FunctionCall;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;


/**
 *
 * @author awagner
 */
public abstract class IdentTopASTNode extends TopASTNode {
    
    /** Creates a new instance of NameTopASTNode */
    public IdentTopASTNode() {}
    public IdentTopASTNode(Token tok) {
        super(tok);
    }
    
    /** This method handles searching for the exact place of a usage for name operations. This returns
     *  a different value in the case of a name node. I am not sure of the reason for this operation.
     */
    protected ReferenceLocation resolveExactUsageLocation(ParseContext context, ReferenceItem item) {
        return context.createReferenceLocation(this);
    }
    /** This method handles searching for the exact place of a usage for name operations. This returns
     *  a different value in the case of a name node. I am not sure of the reason for this operation.
     */
    protected ReferenceLocation checkInternalLocation(ParseContext context, ReferenceItem item) {
        return null;
    }
    
    
    /** @todo : Need better method of type handing for finder operation */
    private void usageFinder(ParseContextUsages context, ReferenceItem item) {   
        if (item != null) {
            //List<EditorFindItem> itemList = context.getFindItem();
            EditorFindItem baseItem = context.getEditorFindItem();
            
            if (item.getname().equalsIgnoreCase(baseItem.getItem().getname())) {
            	ReferenceItem ritem = item;
            	if (ritem != null) {
                    ReferenceLocation location = this.resolveExactUsageLocation(context, ritem);
                    if (location != null) {
                        ReferenceUsage usage = new ReferenceUsage(ritem,"", location);
                        context.addUsage(usage);
                    }
                }
            }
        }
    }
    
    
    
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        ModuleObjectFindItem item = this.createFindItem(context, pos);
        ReferenceItem out = context.getRefHandler().findProjectObject(item, context.getType());
        if (out == null)
            out = context.getRefHandler().findContextObject(item, context.getType());
        return out;
    }
    
    
    private TopObjectBase generateNormal(ParseContext context, ModuleObjectFindItem item) {
        
        TopObjectBase out = null;
        if (context.getSearchMode() == ParseContext.SEARCHREFERENCEGLOBAL)
            out =  context.getRefHandler().findGlobalObject(item,context.getType());
        else if (context.getSearchMode() == ParseContext.SEARCHREFERENCEPROJECT)
            out =  context.getRefHandler().findProjectObject(item,context.getType());
        else if (context.getSearchMode() == ParseContext.SEARCHREFERENCECONTEXT) {
            out =  context.getRefHandler().findContextObject(item,context.getType());
        }
        else if (context.getSearchMode() == ParseContext.SEARCHREFERENCELOCAL) {
            out =  context.getRefHandler().findLocalObject(item,context.getType());
        }
        else if (context.getSearchMode() == ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY) {
        	out =  context.getRefHandler().findProjectAndLibraryObject(item,context.getType());
        }
        else if (context.getSearchMode() == ParseContext.SEARCHFINDITEM)
            out = item;
        else if (context.getSearchMode() == ParseContext.SEARCH_REFERENCE_ACTIVE) {
            out = context.getRefHandler().findActiveObject(item, context.getType());
        }
        else if (context.getSearchMode() == ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL) {
            out =  context.getRefHandler().findContextObject(item,context.getType());
            if (out == null) {
            	out =  context.getRefHandler().findGlobalObject(item,context.getType());
            }
            if (out == null) {
            	out =  context.getRefHandler().findProjectAndLibraryObject(item,context.getType());

            }
        }
        return out;
    }
    
    private String getPostFix(String instr) {
    	String[] us1 = instr.split("\\.");
    	String[] us2 = us1[us1.length-1].split("::");
    	return us2[us2.length - 1];
    }
    
    private void handleFindUsages(ParseContextUsages context, TopObjectBase out) {
    	
    	if (!(out instanceof ReferenceItem)) return;
    	
    	EditorFindItem baseItem = context.getEditorFindItem();
        String str = this.getRealText();
        
        ReferenceItem ref = baseItem.getItem();
        
        String compString = str;
        String refString = ref.getname();
        
        compString = this.getPostFix(compString);
        refString  = this.getPostFix(refString);
        
        //if (str.equalsIgnoreCase(ref.getname())) { // Standard Mode of Operation
        if (compString.equalsIgnoreCase(refString)) { // Standard Mode of Operation	
        	this.usageFinder(context,(ReferenceItem) out);
        	
        }
        else if (out instanceof ReferenceItem && ((ReferenceItem)out).getObject() instanceof FunctionCall) {
        	ReferenceItem ritem = (ReferenceItem) out;
        	FunctionCall func = (FunctionCall) ritem.getObject();
        	ReferenceItem instR = func.getInstanceRef();
        	if (instR.getLocation().compareLocation(baseItem.getItem().getLocation())) {
        		 ReferenceLocation location = this.resolveExactUsageLocation(context,ritem);
                 if (location != null) {
                     ReferenceUsage usage = new ReferenceUsage(ritem,"", location);
                     context.addUsage(usage);
                 }
        	}
        }
        else {
        	int ind = str.indexOf(baseItem.getItem().getname());
            if (ind != -1) {
            	ModuleObjectFindItem item = this.createFindItem(context,this.getPosition().getStartPos()+ind);
            	TopObjectBase base = this.generateNormal(context, item);
            	if (base instanceof ReferenceItem) {
                	this.usageFinder(context, (ReferenceItem)base);
            	}
            }
        }
        
        
        
        
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        ModuleObjectFindItem item = this.createFindItem(context, -1);
        TopObjectBase out = this.generateNormal(context, item);
        
        if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
        	this.handleFindUsages((ParseContextUsages)context, out);
        	// Special Case for Name Attribute and possibly others
        	TopASTNode child = this.getFirstASTChild();
        	if (child != null) {
        		child = child.getNextASTSibling();
        		if (child != null) child.generateModule(context);
        	}
        }
        /*if (out != null && out instanceof ReferenceItem) {
            if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
        	this.usageFinder((ParseContextUsages)context,(ReferenceItem)out);
            }
        }*/
        
        if (context.getSearchMode() == ParseContext.SEARCHREFERENCEGLOBAL ||
                context.getSearchMode() == ParseContext.SEARCHREFERENCECONTEXT ||
                context.getSearchMode() == ParseContext.SEARCHREFERENCELOCAL ||
                context.getSearchMode() == ParseContext.SEARCHREFERENCEPROJECT ||
                context.getSearchMode() == ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY) {
            if (context.getDefinedMode() == ParseContext.ERROR_NOT_DEFINED_ENABLED && 
            	context.getPass() != BuildInterface.BUILD_FILE_CONTEXT &&
            	context.getPass() != ParseContext.BUILD_FIND_USAGES) {
                if (ErrorOptions.noDefEnabled()) {
                    this.addNotDefinedError((ReferenceItem)out,context,item);
                }
            }
        }
        return out;
        
    }
    
    
    private void addErrorCondition(ReferenceItem uitem, ParseContext context, ModuleObjectFindItem item) {
    	ReferenceLocation loc = context.createReferenceLocation(this);
       if (context.getErrorEnableHolder().errorNotdefined) {	
    	   TopError error = new TopNotDefinedError(loc,item,context.getType());
    	   if (error != null) {     
    		   context.getErrorList().add(error);
    	   }
       }
    }
    
    private void addNotDefinedError(ReferenceItem uitem, ParseContext context, ModuleObjectFindItem item) {
        if (uitem == null) {
        	this.addErrorCondition(uitem,context,item); 
        	return;
        }
        if (uitem.getObject() == null) this.addErrorCondition(uitem,context,item);
    }
    
    /** Create the Find Item to Search Over */
    public abstract ModuleObjectFindItem createFindItem(ParseContext context, int pos);
    
    
    
}
