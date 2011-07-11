package com.simplifide.base.verilog.parse.nodes.generate;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.generate.GenerateBlock;
import com.simplifide.base.core.generate.GenerateCaseStatement;
import com.simplifide.base.core.generate.GenerateForStatement;
import com.simplifide.base.core.generate.GenerateIfStatement;
import com.simplifide.base.core.generate.GenerateStatement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;

public class GenerateItemNode extends TopASTNodeGeneric<ReferenceItem<GenerateStatement>>{
	
	
	public boolean canFold() {
		return true;
	}
	
	public static class If extends GenerateItemNode {
		
		public void format(FormatPosition position) {
			TopASTNode child = this.getFirstASTChild(); // "if"
			TopASTNode expNode = child.getNextASTSibling(); // expression
			TopASTNode trueNode = expNode.getNextASTSibling(); // generate_module_item
			
			NodePosition npos = trueNode.getPosition();
			FormatPosition pos = position.addNewPosition(npos.getStartPos(),npos.getEndPos());
			pos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
			
			child = trueNode.getNextASTSibling(); // "else"
			if (child != null) {
				TopASTNode falseNode = child.getNextASTSibling();
				npos = falseNode.getPosition();
				pos = position.addNewPosition(npos.getStartPos(),npos.getEndPos());
				pos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
				
			}
			
			
			
		}
		
		public ReferenceItem<GenerateStatement> createObjectSmall(ParseContext context) {
			// Node Handling 
			TopASTNode child = this.getFirstASTChild(); // "if"
			TopASTNode expNode = child.getNextASTSibling(); // expression
			TopASTNode trueNode = expNode.getNextASTSibling(); // generate_module_item
			child = trueNode.getNextASTSibling(); // "else"
			TopASTNode elseNode = null;
			if (child != null) elseNode = child.getNextASTSibling(); // generate_module_item
			
			// Generation of Objects
			ReferenceItem expRef = (ReferenceItem) expNode.generateModule(context);
			GenerateIfStatement gif = new GenerateIfStatement(expNode.getRealText(),expRef);
			
			GenerateIfStatement.Statement state = new GenerateIfStatement.Statement(" if " + expNode.getRealText());
			state.setBlockText(" if " + expNode.getRealText());
			ReferenceItem trueRef = (ReferenceItem) trueNode.generateModule(context);
			this.addObjects(state.createReferenceItem(), trueRef);
			gif.setTrueRef(state.createReferenceItemWithLocation(context.createReferenceLocation(trueNode)));
			
			if (trueRef.getObject() instanceof GenerateBlock) {
				GenerateBlock block = (GenerateBlock) trueRef.getObject();
				gif.setBlockName(block.getname());
			}
			
			if (elseNode != null) {
				state = new GenerateIfStatement.Statement("else");
				ReferenceItem falseRef = (ReferenceItem) elseNode.generateModule(context);
				this.addObjects(state.createReferenceItem(), falseRef);
				falseRef.setObject(state);
				gif.setFalseRef(state.createReferenceItemWithLocation(context.createReferenceLocation(elseNode)));
			}
			

			
			ReferenceItem gifRef = gif.createReferenceItemWithLocation(context.createReferenceLocation(this));
			
		    return gifRef;
		}
	}
	
	public static class For extends GenerateItemNode {
		
		public void format(FormatPosition position) {
			ForHead headNode = (ForHead) this.getFirstASTChild();
			GenerateBlockNode namedNode =(GenerateBlockNode) headNode.getNextASTSibling(); // generate_module_named_block
			
			NodePosition npos = namedNode.getPosition();
			FormatPosition pos = position.addNewPosition(npos.getStartPos(),npos.getEndPos());
			pos.setIndent(position.getIndent());
			namedNode.format(pos);
			
			
		}
		
		public ReferenceItem<GenerateStatement> createObjectSmall(ParseContext context) {
			ForHead headNode = (ForHead) this.getFirstASTChild();
			GenerateBlockNode namedNode =(GenerateBlockNode) headNode.getNextASTSibling(); // generate_module_named_block
			ReferenceItem<GenerateBlock> ref = namedNode.createObject(context);
			
			GenerateForStatement fors = new GenerateForStatement(ref.getname(),null);
			fors.setBlockName(namedNode.getBlockName());
			fors.setBlockText(headNode.getRealText());
			ReferenceItem uref = fors.createReferenceItemWithLocation(context.createReferenceLocation(this));
			this.addObjects(uref, ref);
			
			return uref;
			
		}
	}
	
	public static class ForHead extends GenerateItemNode {
		

		
		public ReferenceItem<GenerateStatement> createObjectSmall(ParseContext context) {
			return null;
		}
	}
	
	
	
	
	// generate_module_identifier_block : (INDENTIFIER COLON)? generate_module_block
	public static class Identifier extends GenerateItemNode {
		public ReferenceItem<GenerateStatement> createObjectSmall(ParseContext context) {
			String blockName = "generate";
			TopASTNode child = this.getFirstASTChild();
			if (child.getType() == SystemVerilogTokenTypes.IDENTIFIER) {
				blockName = child.getRealText();
				child = child.getNextASTSibling();
				child = child.getNextASTSibling();
			}
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			ref.changeName(blockName);
			return ref;
		}
	
	}
	
	
	// generate_module_case_statement ::=
	// case constant_expression genvar_module_case_item { genvar_module_case_item } endcase
	public static class Case extends GenerateItemNode {
		
		public void format(FormatPosition position) {
			TopASTNode child = this.getFirstASTChild(); // case
			child = child.getNextASTSibling(); // expression
			TopASTNode first = child.getNextASTSibling(); // First Item
			child = first;
			
			int start = child.getPosition().getStartPos();
			int stop = 0;
			while (child != null && child.getType() != SystemVerilogTokenTypes.LITERAL_endcase) {
				stop = child.getPosition().getEndPos();
				child = child.getNextASTSibling();
			}
			FormatPosition pos = position.addNewPosition(start,stop);
			pos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
			child = first;
			while (child != null && child.getType() != SystemVerilogTokenTypes.LITERAL_endcase) {
				child.format(pos);
				child = child.getNextASTSibling();
			}
			
			
		}
		
		public ReferenceItem<GenerateStatement> createObjectSmall(ParseContext context) {
			GenerateCaseStatement casea = new GenerateCaseStatement("case");
			TopASTNode child = this.getFirstASTChild(); // "case"
			//child = child.getNextASTSibling(); // LPAREN
			TopASTNode expNode = child.getNextASTSibling(); // Contant Expression
			//child = expNode.getNextASTSibling(); // RPAREN
			child = child.getNextASTSibling();
			int index = 0;
			while (child != null) {
				if (child.getType() == SystemVerilogTokenTypes.LITERAL_endcase) break;
				ReferenceItem<GenerateCaseStatement.Item> ref = (ReferenceItem<GenerateCaseStatement.Item>) child.generateModule(context);
				if (ref != null) {
					ref.changeName(ref.getname() + "_" + index);
					this.addObjects(casea.createReferenceItem(), ref);
				}
				child = child.getNextASTSibling();
				index++;
			}
			
			casea.setname("case" + expNode.getRealText() + "");
			
			ReferenceItem ref = (ReferenceItem) expNode.generateModuleSmallNew(context);
			casea.setCaseCondition(ref);
			
			return casea.createReferenceItemWithLocation(context.createReferenceLocation(this));
	
		}
	}
	
	//generate_module_case_item_normal : expression ( COMMA expression )* COLON generate_module_item
	public static class CaseItemNormal extends TopASTNodeGeneric<ReferenceItem<GenerateCaseStatement.Item>> {
	
		
		public boolean canFold() {return true;}
		public ReferenceItem<GenerateCaseStatement.Item> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			NoSortList expressions = new NoSortList();
			while (child != null) {
				ReferenceItem exp = (ReferenceItem) child.generateModuleSmallNew(context);
				expressions.addReferenceItem(exp);
				child = child.getNextASTSibling();
				if (child.getType() == SystemVerilogTokenTypes.COLON) break;
			}
			child = child.getNextASTSibling();
			ReferenceItem item = (ReferenceItem) child.generateModuleSmallNew(context);
			
			GenerateCaseStatement.Item genItem = new GenerateCaseStatement.Item("case_item",expressions,item);
			
			return genItem.createReferenceItemWithLocation(context.createReferenceLocation(this));
		}
		
	}
	
	// generate_module_case_default : "default" ( COLON)? generate_module_item
	public static class CaseItemDefault extends TopASTNodeGeneric<ReferenceItem<GenerateCaseStatement.Item>> {
		
		public boolean canFold() {return true;}
		public ReferenceItem<GenerateCaseStatement.Item> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			if (child.getType() == SystemVerilogTokenTypes.COLON) {
				child = child.getNextASTSibling();
			}
			ReferenceItem object = (ReferenceItem) child.generateModuleSmallNew(context);
			GenerateCaseStatement.Item item = new GenerateCaseStatement.Item("default", null, object);
			return item.createReferenceItemWithLocation(context.createReferenceLocation(this));

		}

	}
	
	
	
}
