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
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;





/**
 *
 * @author awagner
 */
public class VhdlUseClauseASTNode extends TopASTNode
{
    
  
	private static final long serialVersionUID = 1L;

	public VhdlUseClauseASTNode() {super();}
   public VhdlUseClauseASTNode(Token tok)
   {
      super(tok);
   }
   
   public TopObjectBase generateModuleSmallNew(ParseContext context)
   {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        TopObjectBase item = null;
         
            ModuleObject nitem =  (ModuleObject) child.generateSearchTypeNew(context,ParseContext.SEARCHFINDITEM,ReferenceUtilities.REF_INSTANCE_PACKAGE); 
            SuperModule smod = context.getRefHandler().getSuperModuleReference().getObject();
            smod.addParentList(nitem.createReferenceItem());
       
         if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
            ReferenceItem titem = (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCEGLOBAL,ReferenceUtilities.REF_MODULEOBJECT);
            
            if (titem != null) {
                ModuleObject obj = titem.getObject(); 
                if (obj instanceof InstancePackage) {
                	InstancePackage pack = (InstancePackage) obj;
                	ReferenceItem ref = pack.getPackageModuleReference();
                	smod.getContextList().addReferenceItem(ref);
                	return ref;
                }
                else {
                	smod.getContextList().addReferenceItem(titem);
                }
                return titem;
            }
        }
        return item;
   }
   
   
    
}
