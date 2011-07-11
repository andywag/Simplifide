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

import java.util.List;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.node.instance.VhdlInstanceTopASTNode;





/**
 *
 * @author awagner
 */
public abstract class VhdlArchitectureTopPartASTNode extends TopASTNode {

    /** Creates a new instance of VhdlArchitectureBodyASTNode */

    public VhdlArchitectureTopPartASTNode() {super();}
    public VhdlArchitectureTopPartASTNode(Token tok) {
        super(tok);
    }

    public boolean canFold() {return false;}

    /** Return the current reference to add objects to. Used for entity which contains the 
     *  items in a seperate place */
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

    protected void setStartLocation(ParseContext context) {}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        //ReferenceItem currentReference = this.getCurrentReferenceItem(context);
        this.setStartLocation(context);
       
        
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

    private void addContextObject(ModuleObject sc, ParseContext context, ReferenceLocation loc) {
        if (sc != null) {
            context.getActiveReference().addModuleObject(sc,loc);
        }
    }

    public static class Declaration extends VhdlArchitectureTopPartASTNode {
       
        private static final long serialVersionUID = 1L;
        public Declaration() {super();}
        public Declaration(Token tok) {super(tok);}
        public String getFoldName() {return "Architecture Declarative Part";}
        
        public TopObjectBase generateModuleSmallNew(ParseContext context) {
        	TopObjectBase base =  super.generateModuleSmallNew(context);
        	ReferenceItem<HardwareModule> modR = context.getRefHandler().getModuleReference();
        	
        	return base;
        }
       
    }
    public static class Statement extends VhdlArchitectureTopPartASTNode {
        public Statement() {super();}
        public Statement(Token tok) {super(tok);}
        public String getFoldName() {return "Architecture Statement Part";}
        @Override
        public TopObjectBase generateModuleSmallNew(ParseContext context) {
            if (context.getSearchMode() != BuildInterface.BUILD_FILE_CONTEXT)
                return super.generateModuleSmallNew(context);
            else {
                TopASTNode child = this.getFirstASTChild();
                while (child != null) {
                    if (child instanceof VhdlInstanceTopASTNode) {
                        child.generateModule(context);
                    }
                    child = child.getNextASTSibling();
                }
            }
            return null;
        }


    }
    public static class PackageDeclaration extends VhdlArchitectureTopPartASTNode {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        public PackageDeclaration() {super();}
        public PackageDeclaration(Token tok) {super(tok);}
        public String getFoldName() {return "Package Declarative Part";}

    }

    public static class PackageBodyDeclaration extends VhdlArchitectureTopPartASTNode {
        
        private static final long serialVersionUID = 1L;
        public PackageBodyDeclaration() {super();}
        public PackageBodyDeclaration(Token tok) {super(tok);}
        public String getFoldName() {return "Package Body Part";}
    }
    public static class EntityDeclaration extends VhdlArchitectureTopPartASTNode {
        public EntityDeclaration() {super();}
        public EntityDeclaration(Token tok) {super(tok);}
        public String getFoldName() {return "Entity Declarative Part";}

        public ReferenceItem getCurrentReferenceItem(ParseContext context) {
            return context.getRefHandler().getSecondaryReference();
        }
    }




}
