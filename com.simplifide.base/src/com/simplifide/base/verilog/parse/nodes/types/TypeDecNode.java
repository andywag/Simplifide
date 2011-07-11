/*
 * TypeDecNode.java
 *
 * Created on April 23, 2007, 1:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.types;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.types.ClassTypeVar;
import com.simplifide.base.core.var.types.InterfaceTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;
import com.simplifide.base.verilog.parse.nodes.base.PackedDimensionNode;


/**
 *
 * @author Andy
 */
public class TypeDecNode extends TopASTNode{
    
    /** Creates a new instance of TypeDecNode */
    public TypeDecNode() {}
    
    public TypeVar decodeStringTypes(String type) {
    	if (type.equalsIgnoreCase("bit")) return VerilogBaseTypes.BIT;
    	if (type.equalsIgnoreCase("byte")) return VerilogBaseTypes.BYTE;
    	if (type.equalsIgnoreCase("int")) return VerilogBaseTypes.INT;
    	
    	return null;
    }
    
    //integer_vector_type signing packed_dimension rangeQ
    /** Array Type Creation */
    public static class Vector extends TypeDecNode {
        public Vector() {}

        private TypeVar decodeType(int type) {
            if (type == SystemVerilogTokenTypes.LITERAL_reg) return VerilogBaseTypes.REG;
            if (type == SystemVerilogTokenTypes.LITERAL_wire) return VerilogBaseTypes.WIRE;
            else if (type == SystemVerilogTokenTypes.LITERAL_logic) return VerilogBaseTypes.LOGIC;
            else return VerilogBaseTypes.BIT;
        }
        
        public TopObjectBase generateModuleSmallNew(ParseContext context) {
        	TopASTNode typeNode = this.getFirstASTChild();
        	TopASTNode child = typeNode.getNextASTSibling();
        	PackedDimensionNode dimNode = (PackedDimensionNode) child.getNextASTSibling();
        	
            TypeVar utype = this.decodeType(typeNode.getToken().getType());
            ReferenceItem<TypeVar> typeR = utype.createReferenceItem();
            
            
            ArrayList<ReferenceItem<VarRange>> ranges = dimNode.createObjectSmallNew(context);
            for (ReferenceItem<VarRange> rangeR : ranges) {
                VerilogArrayType arrType = new VerilogArrayType(rangeR,typeR);
                typeR = arrType.createReferenceItem();
            }
            
            return typeR;
        }
    }
        // "byte" | "shortint" | "int" | "longint" | "integer";
        public static class Atom extends TypeDecNode {
        public Atom() {}

            private TypeVar decodeType(int type) {
                //if (type == SystemVerilogTokenTypes.LITERAL_byte) return VerilogBaseTypes.BYTE;
                if (type == SystemVerilogTokenTypes.LITERAL_shortint) return VerilogBaseTypes.SHORT;
                else if (type == SystemVerilogTokenTypes.LITERAL_longint) return VerilogBaseTypes.LONG;
                else return VerilogBaseTypes.INT;
            }

            public TopObjectBase generateModuleSmallNew(ParseContext context) {
                int type = this.getFirstASTChild().getToken().getType();
                return this.decodeType(type).createReferenceItem();
            }
    }
        
    public static class Identifier extends TypeDecNode {
    	public Identifier() {}

    	protected ReferenceItem<TypeVar> findClassInstType(ParseContext context,TopASTNode child) {
    		
    		ReferenceItem<InstanceModule> clR = (ReferenceItem<InstanceModule>) child.generateSearchTypeNew(context,  ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL, 
         			ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    		if (clR == null) {
    			clR = (ReferenceItem<InstanceModule>) child.generateSearchTypeNew(context,  ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY, 
             			ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    		}
    		
    		if (clR != null && clR.getType() == ReferenceUtilitiesInterface.REF_INSTANCE_CLASS) {
    			ClassTypeVar cl = new ClassTypeVar(clR); 
    			ReferenceItem<TypeVar> typeR = cl.createReferenceItemWithLocation(clR.getLocation());
    			return typeR;
    		}
    		else if (clR != null && clR.getType() == ReferenceUtilitiesInterface.REF_INSTANCE_INTERFACE) {
    			InterfaceTypeVar cl = new InterfaceTypeVar(clR); 
    			ReferenceItem<TypeVar> typeR = cl.createReferenceItemWithLocation(clR.getLocation());
    			return typeR;
    		}
    		return null;
    	}
    	
    	public TopObjectBase generateModuleSmallNew(ParseContext context) {
             TopASTNode child = this.getFirstASTChild();
             TypeVar type = this.decodeStringTypes(child.getRealText());
             if (type != null) return type.createReferenceItem();
             
             ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) 
             	child.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL, 
             			ReferenceUtilities.REF_TYPEVAR);
             
             if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
            	 context.setSearchMode(ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL);
            	 child.generateModule(context); // Used for actually finding class variables
             }
             
             if (typeRef == null) typeRef = findClassInstType(context, child);
             if (typeRef == null) { // Create a type for display if none exists
            	String typeName = this.getFirstLeafNode().getRealText();
             	TypeVar type1 = new TypeVar.NotDefined(typeName);
             	typeRef = type1.createReferenceItemWithLocation(context.createReferenceLocation(this));
             }
             
             return typeRef;

         }
    }
    //type_dec_io : (port_direction (type_dec_integer_vector 
	//		| type_dec_integer_atom 
	//		| (type_dec_identifier) => type_dec_identifier 
	//		| port_type_empty))
    
    public static class IO extends TypeDecNode {
    	public IO() {}
    	public TopObjectBase generateModuleSmallNew(ParseContext context) {
            TopASTNode directionNode = this.getFirstASTChild();
            TopASTNode typeNode = directionNode.getNextASTSibling();
            
            ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
            return typeR;

        }
    	
        private int decodeIoType(int type) {
            if (type == SystemVerilogTokenTypes.LITERAL_input) return OperatingTypeVar.IOVar.INPUT;
            if (type == SystemVerilogTokenTypes.LITERAL_output) return OperatingTypeVar.IOVar.OUTPUT;
            return OperatingTypeVar.IOVar.INOUT;
        }
    	
    	public int getIOTypeVar() {
    		return this.decodeIoType(this.getFirstASTChild().getToken().getType());
    	}
    	
    }
    
        
    
}
