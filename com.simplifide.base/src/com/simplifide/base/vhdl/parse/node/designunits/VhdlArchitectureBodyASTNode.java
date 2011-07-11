/*
 * VhdlArchitectureBodyASTNode.java
 *
 * Created on July 6, 2005, 8:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.vhdl.parse.node.designunits;



import com.simplifide.base.basic.struct.DocPosition;
import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;





/**
 *
 * @author awagner
 */

/*
architecture_body
: ARCHITECTURE identifier OF name IS
  architecture_declarative_part
  BEGIN
  architecture_statement_part
  END ( ARCHITECTURE )? ( identifier )? SEMI
	
*/

public class VhdlArchitectureBodyASTNode extends VhdlTopDeclarationASTNode {
    
    /** Creates a new instance of VhdlArchitectureBodyASTNode */
    
   
    
    public VhdlArchitectureBodyASTNode() {super();}
  
    // Sets the start location for additions to this module
    protected void setStartLocation(ParseContext context,TopASTNode node) {
   	 	ReferenceLocation loc = context.createReferenceLocation(node);
        loc.setLength(0);
        loc.setDocPosition(loc.getDocPosition() + 2);
        context.getRefHandler().getModuleReference().getObject().setDeclarationStartLocation(loc);
   }
    
    public ReferenceItem getHardwareModule(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        String modName = child.getRealText();
        
        
        
        ModuleObjectBaseItem base = new ModuleObjectBaseItem(modName);
        ReferenceItem projectRef = context.getRefHandler().getProjectReference();
        ReferenceItem<InstanceModule> instModule = base.findRealReferenceItem(projectRef,ReferenceUtilities.REF_INSTANCE_MODULE);
        ReferenceItem modItem = base.findRealReferenceItem(instModule,ReferenceUtilities.REF_HARDWARE_MODULE);
        return modItem;
    }
    
    public ReferenceItem findHardwareModule(ParseContext context, TopASTNode node, int pos) {
    	NodePosition pos1 = node.getPosition();
    	if (pos >= pos1.getStartPos() && pos <= pos1.getEndPos()) {
    		return this.getHardwareModule(context);
    	}
    	return null;
    }
    
    /** Method used to find the context of the node, used for completion and navigation operations */
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        this.resolveContext(context);
        TopASTNode node = this.getFirstASTChild(); // architecture
        TopASTNode nameNode = node.getNextASTSibling(); // name of architecture
        node = nameNode.getNextASTSibling(); // of
        TopASTNode moduleNode = node.getNextASTSibling(); // module Name
        TopASTNode startNode = moduleNode.getNextASTSibling(); // is
        TopASTNode decNode = startNode.getNextASTSibling(); // Declarative Part
        node = decNode.getNextASTSibling();
        TopASTNode stateNode = node.getNextASTSibling();
        TopASTNode last = stateNode.getNextASTSibling();
        if (last != null) last = last.getNextASTSibling();
        
        ReferenceItem ref = this.findHardwareModule(context, nameNode,pos);
        if (ref == null && last != null) ref = this.findHardwareModule(context,last,pos);
        return ref;
    }
    
    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        String modName = child.getRealText();
        
        
        
        ModuleObjectBaseItem base = new ModuleObjectBaseItem(modName);
        ReferenceItem projectRef = context.getRefHandler().getProjectReference();
        
        
        ReferenceItem<InstanceModule> instModule = base.findRealReferenceItem(projectRef,ReferenceUtilities.REF_INSTANCE_MODULE);
        ReferenceItem modItem = base.findRealReferenceItem(instModule,ReferenceUtilities.REF_HARDWARE_MODULE);
        ReferenceItem<Entity> entR = base.findRealReferenceItem(instModule,ReferenceUtilities.REF_ENTITY);
        
        context.setActiveReference(modItem);
        //ReferenceItem localRef = this.createCompositeReferenceItem(modItem, entR);
        context.getRefHandler().setLocalReference(entR);
        context.getRefHandler().setModuleReference(modItem);
        
     // Update the Search Context to Include both the entity and architecture
        ReferenceItem<ModuleObjectContextHolder> oref = context.getRefHandler().getSearchReference();
        if (oref != null) {
        	ModuleObjectContextHolder cont = oref.getObject().copy();
        	if (entR != null && entR.getObject() != null && entR.getObject().getSearchReference() != null) {
        		ReferenceItem<ModuleObjectContextHolder> holdR = entR.getObject().getSearchReference();
        		cont.appendObject(holdR.getObject().getInternal().createReferenceItem());
        		context.getRefHandler().setSearchReference(cont.createReferenceItem());
        	}
        }
        
       
    }
    
    
    
    public boolean canFold() {return false;}
    public String getFoldName() {
    	TopASTNode child = this.getFirstASTChild();
    	TopASTNode ch2 = child.getNextASTSibling();
    	child = ch2.getNextASTSibling();
    	TopASTNode ch3 = child.getNextASTSibling();
    	
        return "architecture " + ch2.getRealText() + " of " + ch3.getRealText();
    }
  
    
    private void updateSearchReference(ParseContext context, ReferenceItem entR) {
        context.getRefHandler().getSearchReference();
    }
    
   
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        
        TopASTNode node = this.getFirstASTChild(); // architecture
        TopASTNode nameNode = node.getNextASTSibling(); // name of architecture
        
        node = nameNode.getNextASTSibling(); // of
        TopASTNode moduleNode = node.getNextASTSibling(); // module Name
        TopASTNode startNode = moduleNode.getNextASTSibling(); // is
        TopASTNode decNode = startNode.getNextASTSibling(); // Declarative Part
        node = decNode.getNextASTSibling();
        TopASTNode stateNode = node.getNextASTSibling();

        // Get the name of the module and architecture
        String archName = nameNode.getRealText();
        String modName = moduleNode.getRealText();
       
        
        // Find the InstanceModule associated with this module
        ReferenceItem<InstanceModule> imodref = context.getRefHandler().getProjectReference().findReference(modName, ReferenceUtilities.REF_INSTANCE_MODULE);
        HardwareModule mod = new HardwareModule(modName,archName,imodref);
        ReferenceItem modRef = this.addReferenceObject(context,mod,moduleNode);
        // Attach the Hardware Module to the Context
        context.getRefHandler().setModuleReference(modRef);
        // Find the Entity Associated with this Architecture
        ModuleObjectBaseItem base = new ModuleObjectBaseItem(modName);
        //ReferenceItem<InstanceModule> instRef = base.findRealReferenceItem(context.getRefHandler().getProjectReference(),ReferenceUtilities.REF_INSTANCE_MODULE);
        
        // Combine the Module and the Entity to be used together as the local reference for
        // Searching. 
        ReferenceItem<Entity> entR = base.findRealReferenceItem(imodref,ReferenceUtilities.REF_ENTITY);
        ReferenceItem localRef = this.createCompositeReferenceItem( modRef, entR);
        context.getRefHandler().setLocalReference(entR);
        
        // Update the Search Context to Include both the entity and architecture
        ReferenceItem<ModuleObjectContextHolder> oref = context.getRefHandler().getSearchReference();
        if (oref != null) {
        	ModuleObjectContextHolder cont = oref.getObject().copy();
        	if (entR != null && entR.getObject() != null && entR.getObject().getSearchReference() != null) {
        		ReferenceItem<ModuleObjectContextHolder> holdR = entR.getObject().getSearchReference();
        		cont.appendObject(holdR.getObject().getInternal().createReferenceItem());
        		context.getRefHandler().setSearchReference(cont.createReferenceItem());
        	}
        }
        
        this.setStartLocation(context, startNode);
        context.setType(ReferenceUtilities.REF_MODULEOBJECT);
        if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
        	decNode.generateModule(context); // Declarative Part
        }

        context.setType(ReferenceUtilities.REF_MODULEOBJECT);
        context.setSearchMode(ParseContext.SEARCHREFERENCECONTEXT);
        stateNode.generateModule(context); // Statement Part

        this.checkVariableDefinitions(context, imodref.getObject());

        if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
            moduleNode.generateModule(context);
        }
        context.getRefHandler().setSearchReference(oref);
        
        /*if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
        	ParseContextUsages cont = (ParseContextUsages) context;
        	EditorFindItem item = cont.getEditorFindItem();
        	if (item.getItem().getname().equalsIgnoreCase(modName)) {
        		ReferenceLocation loc = context.createReferenceLocation(moduleNode);
        		 ReferenceUsage usage = new ReferenceUsage(item.getItem(),"", loc);
                 cont.addUsage(usage);
        	}
        }*/
       
        return mod;
        
        
    }
    
    protected InstanceModuleTop createNewInstanceModule(String name, ParseContext context) {
        return new InstanceModule(name, context.getRefHandler().getProjectReference());
    }
    
    
    
    
    
    
}
