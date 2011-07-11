package com.simplifide.base.verilog.parse.nodes.interfac;

import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.parse.nodes.class1.ClassBodyNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassHeadNode;

public class ClassDeclarationNode extends TopASTNodeGeneric<ReferenceItem>{

	
	 public boolean canFold() {return true;}
	
	 public TopASTNode formatBase() {return this;}
	 
	 public void format(FormatPosition position) {
		 
		 FormatPosition npos = position.addNewPosition(this.getPosition().getStartPos(),
				 this.getPosition().getEndPos());
		 npos.setIndent(position.getIndentOrZero());
		 TopASTNode child = this.getFirstASTChild();
		 child = child.getNextASTSibling();
		 child.format(npos);
		 
	 }
	 
	    public void resolveContext(ParseContext context) {
			// Node Handling
			ClassHeadNode headNode = (ClassHeadNode) this.getFirstASTChild();
			ClassBodyNode bodyNode = (ClassBodyNode) headNode.getNextASTSibling();
			
			String entityName = headNode.getClassName();
	        ModuleObjectBaseItem item = new ModuleObjectBaseItem(entityName);
	        ReferenceItem<InstanceModule> instRef = context.getRefHandler().findContextObject(item,ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
	        if (instRef == null) instRef = context.getRefHandler().findProjectAndLibraryObject(item,ReferenceUtilities.REF_INSTANCE_MODULE_TOP);

	        if (instRef == null) return; 
	        
	        ReferenceItem<Entity> entRef = item.findRealReferenceItem(instRef,ReferenceUtilities.REF_ENTITY);  
	        ReferenceItem<HardwareModule> bodyRef = instRef.getObject().getArchitectureReference();
	        //ModuleObjectCompositeHolder holder = ModuleObjectCompositeHolder.dualHolder("Context", entRef, bodyRef);
	        context.setActiveReference(bodyRef); // Changed for new menu (Not sure if it is a good idea)
	        context.getRefHandler().setLocalReference(bodyRef);
	        context.getRefHandler().setModuleReference(bodyRef);
	    }
	 
	    private void appendHdlDoc(Entity entity) {
	    	TopASTToken tok = this.getFirstLeafNode().getToken();
	        if (tok.getDoc() != null) {
	            entity.changeDoc(tok.getDoc());
	        }
	    }
	    
	 public ReferenceItem<ClassEntity> createObjectSmall(ParseContext context) {
			// Node Handling
			ClassHeadNode headNode = (ClassHeadNode) this.getFirstASTChild();
			ClassBodyNode bodyNode = (ClassBodyNode) headNode.getNextASTSibling();
			// Object Creation
			boolean inPackage = false;
			if (context.getActiveReference().getObject() instanceof TopModule &&
		        	!context.getActiveReference().getObject().getname().equals(CoreProjectBasic.GLOBAL)) {
				inPackage = true;
			}
			ReferenceItem oldRef = context.getActiveReference();
			
			ReferenceItem<ClassEntity> entityR = headNode.createObject(context);
			context.setActiveReference(entityR);
			ReferenceLocation loc = entityR.getLocation();
			// Create the Module Reference before creating the body items
			// so that the super class can be found
			ClassModule cmodule = new ClassModule(entityR.getname());
			ReferenceItem<HardwareModule> moduleR = cmodule.createReferenceItem();
	        ReferenceItem<ClassInstanceModule> imodR = context.getRefHandler().getProjectReference().findReference(entityR.getname(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
			if (imodR == null) {
				InstanceModule instanceModule = new ClassInstanceModule(entityR.getname(),context.getRefHandler().getProjectReference());
	            if (!inPackage) {
					imodR = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, loc);
	            }
	            else {
	            	imodR = instanceModule.createReferenceItem();
	            }
			}
			imodR.setLocation(entityR.getLocation());
	        imodR.getObject().setEntityReference(entityR);
	        entityR.getObject().setInstanceModRef(imodR);
			moduleR.setLocation(entityR.getLocation());
	        imodR.getObject().setArchitectureReference(moduleR);
	        moduleR.getObject().setInstModRef(imodR);
	        
	        if (entityR.getObject().getSuperR() != null) { // Add the Children for the class tree
	        	ClassInstanceModule parent = entityR.getObject().getSuperR().getObject();
	        	parent.getChildren().addReferenceItem(imodR);
	        }
	        
			context.getRefHandler().setModuleReference(moduleR);
			if (context.getPass() != BuildInterface.BUILD_FIND_USAGES) {
				context.setPass(BuildInterface.BUILD_FILE_OPEN); // Set to closed so variables are stored
			}
			bodyNode.createObject(context);
			

	        
	        this.appendHdlDoc(entityR.getObject());
	        context.setActiveReference(oldRef);
	        if (inPackage) {
	        	TopModule pack = (TopModule) context.getActiveReference().getObject();
	        	pack.addReferenceItem(imodR);
	        }
	        else {
		        SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
		        smod.addObject(moduleR);
	        }

	        
			return null;
		}
	
	
}
