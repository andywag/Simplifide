/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse;


import java.util.Hashtable;

import antlr.ASTFactory;
import antlr.collections.AST;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameDotASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameExpressionASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NewNameASTNode;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;
import com.simplifide.base.verilog.parse.nodes.FoldingNode;
import com.simplifide.base.verilog.parse.nodes.NewASTNode;
import com.simplifide.base.verilog.parse.nodes.VerilogNumberNode;
import com.simplifide.base.verilog.parse.nodes.VerilogRootASTNode;
import com.simplifide.base.verilog.parse.nodes.base.PackedDimensionNode;
import com.simplifide.base.verilog.parse.nodes.base.RangeASTNode;
import com.simplifide.base.verilog.parse.nodes.base.RangeParamASTNode;
import com.simplifide.base.verilog.parse.nodes.base.VariableDimensionNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassBodyNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassExtendsNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassHeadNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassMethodNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassPropertyNode;
import com.simplifide.base.verilog.parse.nodes.class1.ClassBodyNode.ClassBodyListNode;
import com.simplifide.base.verilog.parse.nodes.context.ContextClauseNode;
import com.simplifide.base.verilog.parse.nodes.context.DefineDirectiveNode;
import com.simplifide.base.verilog.parse.nodes.context.IfDefCondNode;
import com.simplifide.base.verilog.parse.nodes.context.IfDefNode;
import com.simplifide.base.verilog.parse.nodes.context.IncludeNode;
import com.simplifide.base.verilog.parse.nodes.expression.BinaryOpNode;
import com.simplifide.base.verilog.parse.nodes.expression.BinaryTopNode;
import com.simplifide.base.verilog.parse.nodes.expression.QuestionNode;
import com.simplifide.base.verilog.parse.nodes.expression.QuestionTopNode;
import com.simplifide.base.verilog.parse.nodes.expression.UnaryOpNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionBodyNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionHeadNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionTopNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionVarListNode;
import com.simplifide.base.verilog.parse.nodes.function.RangeOrTypeNode;
import com.simplifide.base.verilog.parse.nodes.function.TaskFunctionPortListNode;
import com.simplifide.base.verilog.parse.nodes.function.TfPortDirectionNode;
import com.simplifide.base.verilog.parse.nodes.function.TfPortItemNode;
import com.simplifide.base.verilog.parse.nodes.function.TfPortListNode;
import com.simplifide.base.verilog.parse.nodes.generate.ColonIdentifierQNode;
import com.simplifide.base.verilog.parse.nodes.generate.GenerateBlockNode;
import com.simplifide.base.verilog.parse.nodes.generate.GenerateItemNode;
import com.simplifide.base.verilog.parse.nodes.generate.GenerateNode;
import com.simplifide.base.verilog.parse.nodes.instance.ListOfPortConnectionsNode;
import com.simplifide.base.verilog.parse.nodes.instance.ModuleInstanceNode;
import com.simplifide.base.verilog.parse.nodes.instance.ModuleInstantiationNode;
import com.simplifide.base.verilog.parse.nodes.instance.ParameterAssignmentNode;
import com.simplifide.base.verilog.parse.nodes.instance.ParameterPortListNode;
import com.simplifide.base.verilog.parse.nodes.instance.ParameterValueAssignmentNode;
import com.simplifide.base.verilog.parse.nodes.interfac.ClassDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.interfac.InterfaceDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.interfac.ModPortDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.module.ModuleBodyNode;
import com.simplifide.base.verilog.parse.nodes.module.ModuleDecNode;
import com.simplifide.base.verilog.parse.nodes.module.ModuleItemNode;
import com.simplifide.base.verilog.parse.nodes.module.ModuleNode;
import com.simplifide.base.verilog.parse.nodes.module.VerilogDesignUnitASTNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopHeaderNormalNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopNoAnsiHeaderNode;
import com.simplifide.base.verilog.parse.nodes.name.DefineNode;
import com.simplifide.base.verilog.parse.nodes.name.VerilogNameRangeNode;
import com.simplifide.base.verilog.parse.nodes.pack.PackageBodyNode;
import com.simplifide.base.verilog.parse.nodes.pack.PackageDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.pack.PackageImportNode;
import com.simplifide.base.verilog.parse.nodes.port.NamedPortExpressionNode;
import com.simplifide.base.verilog.parse.nodes.port.PortTypeNode;
import com.simplifide.base.verilog.parse.nodes.portnew.AnsiPortDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.portnew.AnsiPortHeaderNode;
import com.simplifide.base.verilog.parse.nodes.portnew.AnsiPortTopNode;
import com.simplifide.base.verilog.parse.nodes.portnew.ModulePortListNode;
import com.simplifide.base.verilog.parse.nodes.primitive.PrimitiveASTNode;
import com.simplifide.base.verilog.parse.nodes.primitive.TableASTNode;
import com.simplifide.base.verilog.parse.nodes.segment.SubroutineCallNode;
import com.simplifide.base.verilog.parse.nodes.segment.always.AlwaysHeadNode;
import com.simplifide.base.verilog.parse.nodes.segment.always.AlwaysStatementNode;
import com.simplifide.base.verilog.parse.nodes.segment.always.TotalAssignmentNode;
import com.simplifide.base.verilog.parse.nodes.segment.basic.AssignListNode;
import com.simplifide.base.verilog.parse.nodes.segment.basic.AssignmentNode;
import com.simplifide.base.verilog.parse.nodes.segment.basic.ContinuousAssignmentNode;
import com.simplifide.base.verilog.parse.nodes.segment.cas.CaseHeadNode;
import com.simplifide.base.verilog.parse.nodes.segment.cas.CaseItemNode;
import com.simplifide.base.verilog.parse.nodes.segment.cas.CaseListNode;
import com.simplifide.base.verilog.parse.nodes.segment.cas.CaseStatementNode;
import com.simplifide.base.verilog.parse.nodes.segment.condition.ConditionHeadNode;
import com.simplifide.base.verilog.parse.nodes.segment.condition.ConditionStatementNode;
import com.simplifide.base.verilog.parse.nodes.segment.op.ConcatenationNode;
import com.simplifide.base.verilog.parse.nodes.segment.wrap.BeginEndNode;
import com.simplifide.base.verilog.parse.nodes.segment.wrap.NameBlockPrefixNode;
import com.simplifide.base.verilog.parse.nodes.segment.wrap.ParenNode;
import com.simplifide.base.verilog.parse.nodes.segment.wrap.StateListNode;
import com.simplifide.base.verilog.parse.nodes.segment.wrap.StatementNode;
import com.simplifide.base.verilog.parse.nodes.sv.AssertionNode;
import com.simplifide.base.verilog.parse.nodes.sv.DeclarationNode;
import com.simplifide.base.verilog.parse.nodes.sv.StatementNamePrefixNode;
import com.simplifide.base.verilog.parse.nodes.types.ConstantTypeNode;
import com.simplifide.base.verilog.parse.nodes.types.DataTypeOrNullNode;
import com.simplifide.base.verilog.parse.nodes.types.EnumNode;
import com.simplifide.base.verilog.parse.nodes.types.ParameterDimensionNode;
import com.simplifide.base.verilog.parse.nodes.types.StructureNode;
import com.simplifide.base.verilog.parse.nodes.types.TypeDecNode;
import com.simplifide.base.verilog.parse.nodes.types.TypeDefDataNode;
import com.simplifide.base.verilog.parse.nodes.types.TypeDefNode;
import com.simplifide.base.verilog.parse.nodes.vars.AssignExpressionQNode;
import com.simplifide.base.verilog.parse.nodes.vars.IoDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.vars.IoTopDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.vars.NetDeclAssignmentNode;
import com.simplifide.base.verilog.parse.nodes.vars.NetDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.vars.VariableDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.vars.VariableIdentNode;
import com.simplifide.base.verilog.parse.nodes.vars.VariableListNode;
import com.simplifide.base.verilog.parse.nodes.vars.param.ParamDeclarationListNode;
import com.simplifide.base.verilog.parse.nodes.vars.param.ParameterAssignNode;
import com.simplifide.base.verilog.parse.nodes.vars.param.ParameterDeclaratationSemiNode;
import com.simplifide.base.verilog.parse.nodes.vars.param.ParameterDeclarationNode;
import com.simplifide.base.verilog.parse.nodes.vars.param.ParameterListNode;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
VerilogFactory:40:04 AM
 * To change this tempVerilogFactoryle | Settings | File Templates.
 */

public class VerilogFactory extends ASTFactory {
    /**
     * 
     */
	
	public static Hashtable<Integer,Class> map = new Hashtable();
	
	static {
		
	map.put(Integer.valueOf(SystemVerilogTokenTypes.ROOT), VerilogRootASTNode.class);
	
    map.put(Integer.valueOf( SystemVerilogTokenTypes.DESIGNUNIT) ,VerilogDesignUnitASTNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEDECLARATION) , ModuleNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEHEAD) ,ModuleDecNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEBODY) ,ModuleBodyNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEITEM) ,ModuleItemNode.class);
    // Ports
    //map.put(Integer.valueOf(SystemVerilogTokenTypes.PORTLIST), ModulePortListNode.Top.class);
    //map.put(Integer.valueOf( SystemVerilogTokenTypes.NOANSIPORTLIST) ,ModulePortListNode.NoAnsi.class);
    //map.put(Integer.valueOf( SystemVerilogTokenTypes.ANSIPORTLIST) ,ModulePortListNode.Ansi.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PORTLIST), ModulePortListNode.Top.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTLIST) ,ModulePortListNode.Ansi.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PORTEXPRESSION), AnsiPortTopNode.Expression.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTDECLARATION) ,AnsiPortTopNode.Declaration.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTHEADERQ) ,AnsiPortHeaderNode.Header.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTHEADERNORMAL) ,AnsiPortHeaderNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTHEADERINTERFACE) ,AnsiPortHeaderNode.Interface.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PORTDIRECTIONQ) ,AnsiPortHeaderNode.Direction.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTDECLARATIONNORMAL) ,AnsiPortDeclarationNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ANSIPORTDECLARATIONDOT) ,AnsiPortDeclarationNode.Dot.class);
    
    // Types
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDEF ), TypeDefNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDEFDATATYPE ), TypeDefDataNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDECINTEGERVECTOR ),TypeDecNode.Vector.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDECINTEGERATOM ), TypeDecNode.Atom.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDECIDENTIFIER ), TypeDecNode.Identifier.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.TYPEDECIO ), TypeDecNode.IO.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PORTTYPEEMPTY ), PortTypeNode.Empty.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PORTTYPETRIREG ),PortTypeNode.TriReg.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PORTTYPENET ),PortTypeNode.Net.class);
    // Variables
    map.put(Integer.valueOf( SystemVerilogTokenTypes.VARIABLEDECLARATION ),VariableDeclarationNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.LISTOFVARIABLEIDENTIFIERS ),VariableListNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.VARIABLEIDENT ),VariableIdentNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.IODECLARATIONTOP ),IoTopDeclarationNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.IODECLARATION ),IoDeclarationNode.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARAMETERPORTLIST), ParameterPortListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARAMETERDECLARATIONSEMI), ParameterDeclaratationSemiNode.class);

    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARAMETERDECLARATION), ParameterDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARAMASSIGNMENT), ParameterAssignNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.LISTOFPARAMASSIGNMENTS), ParamDeclarationListNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.DATATYPEORNULL), DataTypeOrNullNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NETDECLARATION), NetDeclarationNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.LISTOFNETDECLASSIGNMENTS), VariableListNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NETDECLARATIONASSIGNMENT), NetDeclAssignmentNode.class);
    
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PARAMETERDIMENSIONNODE ),  ParameterDimensionNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PACKEDDIMENSION ),  PackedDimensionNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.VARIABLEDIMENSION ),VariableDimensionNode.class);

    map.put(Integer.valueOf( SystemVerilogTokenTypes.VARRANGE ),  RangeASTNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NUMBER ),  VerilogNumberNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.SIZED_NUMBER ),  VerilogNumberNode.class);
  
    map.put(Integer.valueOf( SystemVerilogTokenTypes.IDENTIFIER ),  IdentASTNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.DEFINE ),  DefineNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NAME ),  NewNameASTNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NAMEDOT ),  NameDotASTNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NAMECOLON ),  NameDotASTNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NAMERANGE ),  VerilogNameRangeNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.NAMEEXPRESSION ),  NameExpressionASTNode.class);
    
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEINSTANTIATION), ModuleInstantiationNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.MODULEINSTANCE), ModuleInstanceNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.LISTOFPORTORDERED), ListOfPortConnectionsNode.Ordered.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.LISTOFPORTNAMED), ListOfPortConnectionsNode.Named.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.NAMEPORTEXPR), NamedPortExpressionNode.class);
    map.put(Integer.valueOf( SystemVerilogTokenTypes.PARAMETERVALUEASSIGNMENT), ParameterValueAssignmentNode.class);
  
    map.put(Integer.valueOf (SystemVerilogTokenTypes.NAMEDPARAMETERLIST), ParameterListNode.Named.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ORDEREDPARAMETERLIST), ParameterListNode.Ordered.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ORDEREDPARAMETERASSIGNMENT), ParameterAssignmentNode.Ordered.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.NAMEDPARAMETERASSIGNMENT), ParameterAssignmentNode.Named.class);
    
    
    map.put(Integer.valueOf (SystemVerilogTokenTypes.CONTINUOUS_ASSIGN), ContinuousAssignmentNode.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ASSIGNMENT), AssignmentNode.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ASSIGNLIST), AssignListNode.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.TOTALASSIGN), TotalAssignmentNode.class);
   
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ALWAYSTOP), AlwaysStatementNode.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.ALWAYSHEAD), AlwaysHeadNode.class);
    
    map.put(Integer.valueOf (SystemVerilogTokenTypes.CONDITIONTOP), ConditionStatementNode.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.CONDITIONHEAD), ConditionHeadNode.First.class);
    map.put(Integer.valueOf (SystemVerilogTokenTypes.CONDITIONELSE), ConditionHeadNode.Else.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASETOP), CaseStatementNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASELIST), CaseListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASEHEAD), CaseHeadNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASEEXPRESSION), CaseItemNode.Expression.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASENORMAL), CaseItemNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CASEDEFAULT), CaseItemNode.Default.class);

    
    //map.put(Integer.valueOf (SystemVerilogTokenTypes.ALWAYSTOP), FoldingNode.class);
    //map.put(Integer.valueOf (SystemVerilogTokenTypes.CONDITIONTOP), FoldingNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STATEMENT), StatementNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.BEGENDBLOCK), BeginEndNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PAROP), ParenNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STATELIST), BeginEndNode.StateList.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PREBLOCKNAMEQ), NameBlockPrefixNode.Pre.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.SEQBLOCKNAMEQ), NameBlockPrefixNode.Post.class);

    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODINSTANCETOP), FoldingNode.class);
    
   
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSMETHODPROTO),ClassMethodNode.Proto.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSMETHODNORMAL),ClassMethodNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONPROTOTYPE),ClassMethodNode.FunctionProto.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKPROTOTYPE),ClassMethodNode.TaskProto.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTION), FunctionTopNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASK), FunctionTopNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONHEADDECLARATIONRETURN), FunctionHeadNode.Return.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONHEADDECLARATIONNORETURN), FunctionHeadNode.NoReturn.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKFUNCTIONPORTLIST),TaskFunctionPortListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKBODYDECLARATION), FunctionBodyNode.Task.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKHEADDECLARATION), FunctionHeadNode.Task.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONBODYDECLARATION), FunctionBodyNode.Function.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONBODYDECLARATIONLIST), FunctionBodyNode.FunctionBodyList.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKBODYDECLARATIONLIST), FunctionBodyNode.FunctionBodyList.class);

    map.put(Integer.valueOf(SystemVerilogTokenTypes.TFPORTLIST),TfPortListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TFPORTITEM),TfPortItemNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TFPORTDIRECTION),TfPortDirectionNode.class);
    
    
    
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.QUESTTOP), QuestionTopNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.QUESTIONSEGMENT), QuestionNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.BINOP), BinaryTopNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.BINOPSMALL), BinaryOpNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.UNOP), UnaryOpNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CONCAT), ConcatenationNode.class);
   
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.LITERAL_integer), ConstantTypeNode.IntegerNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.LITERAL_real), ConstantTypeNode.RealNode.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.DEFINEDIRECTIVE), DefineDirectiveNode.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CONTEXTCLAUSE), ContextClauseNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INCLUDEDIRECTIVE), IncludeNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.IFDEFDIRECTIVE), IfDefNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.IFDEFHEAD), IfDefCondNode.First.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.IFDEFCOND), IfDefCondNode.Else.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.RANGEPARAM), RangeParamASTNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TABLEDEFINITION), TableASTNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PRIMITIVEDEFINITION), PrimitiveASTNode.class);
    // Generate Statement Handling
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEITEM), GenerateNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEIF), GenerateItemNode.If.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEFOR), GenerateItemNode.For.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEFORHEAD), GenerateItemNode.ForHead.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATECASE), GenerateItemNode.Case.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEMODULECASEITEMNORMAL), GenerateItemNode.CaseItemNormal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEMODULECASEITEMDEFAULT), GenerateItemNode.CaseItemDefault.class);

    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEMODULEBLOCK), GenerateBlockNode.Block.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.GENERATEMODULEBLOCKIDENT), GenerateBlockNode.Ident.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.COLONIDENTIFIERQ), ColonIdentifierQNode.class);
    
    // Structures
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STRUCTUNION), StructureNode.Top.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STRUCTUNIONMEMBERLIST), StructureNode.List.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STRUCTUNIONMEMBER), StructureNode.Item.class);
    // Enum
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ENUMTYPE), EnumNode.Top.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ENUMDECTYPE), EnumNode.DecType.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ENUMDECTYPEIDENT), EnumNode.Ident.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ENUMLIST), EnumNode.List.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ENUMNAMEDECLARATION), EnumNode.Item.class);
    
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INITIALORFINAL), FoldingNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARBLOCK),FoldingNode.class);
    
    // INTERFACES
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEDECLARATION), ModuleTopDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEDECLARATIONNORMAL),InterfaceDeclarationNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEDECLARATIONEXTERNAL),InterfaceDeclarationNode.Extern.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEDECLARATIONNOANSIHEADER), ModuleTopNoAnsiHeaderNode.Interface.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEHEADERNORMAL),ModuleTopHeaderNormalNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEHEADERDOTSTAR),ModuleTopHeaderNormalNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.INTERFACEBODY),InterfaceDeclarationNode.Body.class);
    // Programs
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROGRAMDECLARATION), ModuleTopDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROGRAMDECLARATIONNORMAL),InterfaceDeclarationNode.Normal.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROGRAMDECLARATIONEXTERNAL),InterfaceDeclarationNode.Extern.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROGRAMDECLARATIONNOANSIHEADER), ModuleTopNoAnsiHeaderNode.Program.class);
   
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROGRAMBODY),InterfaceDeclarationNode.Body.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSDECLARATION),ClassDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSHEAD),ClassHeadNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSBODY),ClassBodyNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSBODYLIST),ClassBodyListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSPROPERTY),ClassPropertyNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLASSEXTENDS),ClassExtendsNode.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.STATEMENTNAMEPREFIX), StatementNamePrefixNode.class);

    map.put(Integer.valueOf(SystemVerilogTokenTypes.PROPERTYDECLARATION), DeclarationNode.Property.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.SEQUENCEDECLARATION), DeclarationNode.Sequence.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CLOCKINGDECLARATION), DeclarationNode.Clocking.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CONCURRENTASSERTIONITEM), AssertionNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.CONSTRAINTDECLARATION), DeclarationNode.Constraint.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.ASSIGNEXPRESSIONQ), AssignExpressionQNode.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.SUBROUTINECALLSTATEMENT), SubroutineCallNode.class);
    
    /*
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PARAMETERTYPEDECLARATION), ParameterTypeDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.LISTOFTYPEASSIGNMENTS), ParameterTypeDeclarationNode.List.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TYPEASSIGNMENT), ParameterTypeDeclarationNode.Type.class);
     */
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.LITERAL_new), NewASTNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PACKAGEDECLARATION), PackageDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PACKAGEBODY), PackageBodyNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PACKAGEIMPORTDECLARATION), PackageImportNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.PACKAGEIMPORTITEM), PackageImportNode.Item.class);
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTDECLARATION), ModPortDeclarationNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTITEM), ModPortDeclarationNode.Item.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTDECLARATIONITEM), ModPortDeclarationNode.DecItem.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTDECLARATIONPREFIX), ModPortDeclarationNode.DecPrefix.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTDOT), ModPortDeclarationNode.DecDot.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.MODPORTHIER), ModPortDeclarationNode.DecHier.class);
    
    
    map.put(Integer.valueOf(SystemVerilogTokenTypes.FUNCTIONVARDECLARATION), FunctionVarListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.TASKVARDECLARATION), FunctionVarListNode.class);
    map.put(Integer.valueOf(SystemVerilogTokenTypes.RANGEORTYPE), RangeOrTypeNode.class);
    
    
    
	}
	
    public VerilogFactory() {
        super(map);
        
    }
    
    /**
     * 
     * @param index 
     * @return 
     */
    public AST create(int index) {
        AST ast = this.decodeType(index);
        if (ast != null) return ast;
        
        return super.create(index);
    }
    /**
     * 
     * @param index 
     * @param name 
     * @return 
     */
    public AST create(int index,String name) {
    	AST ast = super.create(index,name);
    	ast.setText(name);
        return ast;
    }
    
    /**
     * 
     * @param index 
     * @return 
     */
    public AST decodeType(int index) {
    	Class uclass = map.get(Integer.valueOf(index));
    	if (uclass == null) return new TopASTNode();
    	TopASTNode node = null;
    	try {
			node = (TopASTNode) uclass.newInstance();
    	} catch (InstantiationException e) {
			
		} catch (IllegalAccessException e) {
			
		}
		if (node == null) {
			node = new TopASTNode();
		}
		return node;
		
    	
       
         

    }
    
    
    
}
