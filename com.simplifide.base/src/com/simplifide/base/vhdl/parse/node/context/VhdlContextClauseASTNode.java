/*
 * VhdlArchitectureBodyASTNode.java
 *
 * Created on July 6, 2005, 8:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.vhdl.parse.node.context;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.project.VhdlStandardPackage;


/**
 *
 * @author awagner
 */
public class VhdlContextClauseASTNode extends TopASTNode {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VhdlContextClauseASTNode() {super();}
    public VhdlContextClauseASTNode(Token tok) {
        super(tok);
    }
    
    public boolean canFold() {return true;}
    public String getFoldName() {
        return "Context";
    }
    
    public void resolveContext(ParseContext context)
    {
         context.getRefHandler().setSearchReference(context.getRefHandler().getGlobalReference());
    }
    
    private TopObjectBase generateFileContext(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        
        while (child != null) {
            ModuleObject ss = (ModuleObject) child.generateModule(context);
            child = child.getNextASTSibling();
        }
        return null;
    }
    
    private TopObjectBase generateNormalContext(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        String moduleName = context.getRefHandler().getSuperModuleReference().getname();
        ModuleObjectContextHolder contextHolder = new ModuleObjectContextHolder(moduleName + "Context",context.getRefHandler().getGlobalReference());
        contextHolder.setStandardReference(VhdlStandardPackage.INSTANCE.createReferenceItem());
        
        while (child != null) {
            ReferenceItem ss = (ReferenceItem) child.generateModule(context);
            if (ss != null) {
                contextHolder.appendObject(ss);
                //contextHolder.appendFunction(ss); // Place all of the functions in the context holder of the next block
            }

            child = child.getNextASTSibling();
        }
        return contextHolder.createReferenceItem();
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT){
            return this.generateFileContext(context);
        }
        else {
            return this.generateNormalContext(context);
        }
        
    }
    
    
    
    
}
