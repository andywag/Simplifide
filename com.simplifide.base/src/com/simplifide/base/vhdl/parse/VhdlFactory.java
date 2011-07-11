/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse;


import java.util.Hashtable;

import antlr.ASTFactory;
import antlr.collections.AST;

import com.simplifide.base.sourcefile.antlr.node.CharacterLitASTNode;
import com.simplifide.base.sourcefile.antlr.node.NotSupportedASTNode;
import com.simplifide.base.sourcefile.antlr.node.NumberASTNode;
import com.simplifide.base.sourcefile.antlr.node.PassThroughASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameAttributeASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameDotASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameExpressionASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NameRangeASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NewNameASTNode;
import com.simplifide.base.sourcefile.antlr.node.port.PortListASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarListASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.types.EnumerationTypeASTNode;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;
import com.simplifide.base.vhdl.parse.node.LabelColonWrapNode;
import com.simplifide.base.vhdl.parse.node.VhdlDesignUnitASTNode;
import com.simplifide.base.vhdl.parse.node.VhdlRootASTNode;
import com.simplifide.base.vhdl.parse.node.alias.AliasDeclarationNode;
import com.simplifide.base.vhdl.parse.node.block.BlockStatementNode;
import com.simplifide.base.vhdl.parse.node.context.VhdlContextClauseASTNode;
import com.simplifide.base.vhdl.parse.node.context.VhdlLibraryClauseASTNode;
import com.simplifide.base.vhdl.parse.node.context.VhdlUseClauseASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlArchitectureBodyASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlArchitectureStatementASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlArchitectureTopPartASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlEntityDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlEntityHeaderASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlPackageBodyASTNode;
import com.simplifide.base.vhdl.parse.node.designunits.VhdlPackageDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.function.FunctionParameterListNode;
import com.simplifide.base.vhdl.parse.node.function.ProcedureDeclarationNode;
import com.simplifide.base.vhdl.parse.node.function.SubProgramDeclarativePartNode;
import com.simplifide.base.vhdl.parse.node.function.VhdlFunctionDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.function.VhdlSubProgramBodyASTNode;
import com.simplifide.base.vhdl.parse.node.function.VhdlSubProgramDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.generate.GenerateDeclarativePartNode;
import com.simplifide.base.vhdl.parse.node.generate.GenerateForNode;
import com.simplifide.base.vhdl.parse.node.generate.GenerateIfNode;
import com.simplifide.base.vhdl.parse.node.generate.GenerateStatementNode;
import com.simplifide.base.vhdl.parse.node.generate.GenerateStatementPartNode;
import com.simplifide.base.vhdl.parse.node.generate.ParameterSpecificationNode;
import com.simplifide.base.vhdl.parse.node.instance.InstanceNameNode;
import com.simplifide.base.vhdl.parse.node.instance.MapAspectASTNode;
import com.simplifide.base.vhdl.parse.node.instance.VhdlComponentDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.instance.VhdlInstancePortDotASTNode;
import com.simplifide.base.vhdl.parse.node.instance.VhdlInstancePortListASTNode;
import com.simplifide.base.vhdl.parse.node.instance.VhdlInstanceTopASTNode;
import com.simplifide.base.vhdl.parse.node.instance.VhdlPortNoDotNode;
import com.simplifide.base.vhdl.parse.node.literals.VhdlAllLiteralASTNode;
import com.simplifide.base.vhdl.parse.node.loop.IterationForNode;
import com.simplifide.base.vhdl.parse.node.loop.IterationWhileNode;
import com.simplifide.base.vhdl.parse.node.loop.LoopStatementNode;
import com.simplifide.base.vhdl.parse.node.misc.LabelColonASTNode;
import com.simplifide.base.vhdl.parse.node.misc.PhysicalLiteralNode;
import com.simplifide.base.vhdl.parse.node.port.InterfaceVariableDeclarationNode;
import com.simplifide.base.vhdl.parse.node.port.ModeASTNode;
import com.simplifide.base.vhdl.parse.node.port.PortAssignmentNode;
import com.simplifide.base.vhdl.parse.node.port.VhdlPortClauseASTNode;
import com.simplifide.base.vhdl.parse.node.port.VhdlPortNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.SelectedSignalAssignmentNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.SelectedWaveFormItemNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.SelectedWaveformsNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.VhdlConcurrentSignalAssignmentNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.VhdlConditionalSignalAssignmentNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.VhdlConditionalWaveformsASTNode;
import com.simplifide.base.vhdl.parse.node.segment.assign.VhdlConditionalWaveformsBiASTNode;
import com.simplifide.base.vhdl.parse.node.segment.condition.VhdlCaseConditionASTNode;
import com.simplifide.base.vhdl.parse.node.segment.condition.VhdlCaseStatementASTNode;
import com.simplifide.base.vhdl.parse.node.segment.condition.VhdlChoicesASTNode;
import com.simplifide.base.vhdl.parse.node.segment.condition.VhdlIfConditionASTNode;
import com.simplifide.base.vhdl.parse.node.segment.condition.VhdlIfStatementASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.AddOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.AddingTermASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.ExpressionOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.FactorOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.MultiplyOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.ParenOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.RelationOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.core.ShiftOpASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlProcessDeclarativePartASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlProcessHeadASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlProcessStatementASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlProcessStatementPartASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlSensitivityListASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlSequenceOfStatementsASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.segment.VhdlSequentialStatementASTNode;
import com.simplifide.base.vhdl.parse.node.segment.process.segment.VhdlSignalAssignmentStatementASTNode;
import com.simplifide.base.vhdl.parse.node.string.VhdlBitStringASTNode;
import com.simplifide.base.vhdl.parse.node.string.VhdlStringASTNode;
import com.simplifide.base.vhdl.parse.node.vars.VhdlAggregateASTNode;
import com.simplifide.base.vhdl.parse.node.vars.VhdlElementAssociationASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.ExpressionOrRangeASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.FileDeclarationNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlConstantASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlSignalASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVarAssignASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVarDecASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVarDiscreteRangeASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVarRangeASTNode;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVariableASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.AcessTypeDeclarationNode;
import com.simplifide.base.vhdl.parse.node.vars.types.FileTypeDeclarationNode;
import com.simplifide.base.vhdl.parse.node.vars.types.SubtypeFirstNameNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlConstrainedArrayDefinitionASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlElementDeclarationASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlIndexConstraintASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlStructDecASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlStructListASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlSubTypeDefASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlTypeDefASTNode;
import com.simplifide.base.vhdl.parse.node.vars.types.VhdlUnconstrainedArrayDefinitionASTNode;






/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 5:40:04 AM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlFactory extends ASTFactory {
	
	public static Hashtable<Integer,Class>map = new Hashtable<Integer,Class>();
	
	static {
		
                map.put(Integer.valueOf(VhdlTokenTypes.ROOT) ,VhdlRootASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.DESIGNUNIT) ,VhdlDesignUnitASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ENTITYDECLARATION) ,VhdlEntityDeclarationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ENTITYDECLARATIVEPART) ,VhdlArchitectureTopPartASTNode.EntityDeclaration.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ENTITYHEADER) ,VhdlEntityHeaderASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ENTDEC2) ,VhdlEntityDeclarationASTNode.EntDec2.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ARCHITECTUREBODY) ,VhdlArchitectureBodyASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ARCHITECTUREDECLARATIVEPART) ,VhdlArchitectureTopPartASTNode.Declaration.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PACKAGEDECLARATION) ,VhdlPackageDeclarationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PACKAGEDECLARATIVEPART) ,VhdlArchitectureTopPartASTNode.PackageDeclaration.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PACKAGEBODY) ,VhdlPackageBodyASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PACKAGEBODYDECLARATIVEPART) ,VhdlArchitectureTopPartASTNode.PackageBodyDeclaration.class);
                map.put(Integer.valueOf(VhdlTokenTypes.COMPONENTDECLARATION) ,VhdlComponentDeclarationASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.IDENTIFIER) ,IdentASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SIGNALDECLARATION) ,VhdlSignalASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CONSTANTDECLARATION) ,VhdlConstantASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.VARIABLEDECLARATION) ,VhdlVariableASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.FILEDECLARATION) ,FileDeclarationNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.VARASSIGN) ,VhdlVarAssignASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SIGNALKIND) ,NotSupportedASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.PORTCLAUSE) ,VhdlPortClauseASTNode.Port.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERICCLAUSE) ,VhdlPortClauseASTNode.Generic.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PORTLIST) ,PortListASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PORTELEMENT) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CONSTANTPORT) ,VhdlPortNode.Constant.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SIGNALPORT) ,VhdlPortNode.Signal.class);
                map.put(Integer.valueOf(VhdlTokenTypes.INTERFACEVARIABLEDECLARATION) ,InterfaceVariableDeclarationNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SIGNALPORTDEC) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.MODE) ,ModeASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PORTASSIGNMENT) ,PortAssignmentNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.LABELCOLON) ,LabelColonASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SUBTYPEDECLARATION) ,VhdlSubTypeDefASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.TYPEDECLARATION) ,VhdlTypeDefASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.RECORDTYPEDEFINITION) ,VhdlStructDecASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.RECORDELEMENT) ,VhdlStructListASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ELEMENTDECLARATION) ,VhdlElementDeclarationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ACCESSTYPEDEFINITION) ,AcessTypeDeclarationNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.FILETYPEDEFINITION) ,FileTypeDeclarationNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.CONSTRAINEDARRAYDEFINITION) ,VhdlConstrainedArrayDefinitionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.UNCONSTRAINEDARRAYDEFINITION) ,VhdlUnconstrainedArrayDefinitionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.INDEXCONSTRAINT) ,VhdlIndexConstraintASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.ENUMERATIONTYPEDEFINITION) ,EnumerationTypeASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.ARCHITECTURESTATEMENTPART) ,VhdlArchitectureTopPartASTNode.Statement.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ARCHITECTURESTATEMENT) ,VhdlArchitectureStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.COMPONENTINSTANTIAIONSTATEMENT) ,VhdlInstanceTopASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.INSTUNITCOMP) ,InstanceNameNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.INSTUNITENT) ,InstanceNameNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.INSTUNITCONF) ,InstanceNameNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERICMAPASPECT) ,MapAspectASTNode.Generic.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PORTMAPASPECT) ,MapAspectASTNode.Port.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ASSOCIATIONLIST) ,VhdlInstancePortListASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ASSOCIATIONARROW) ,VhdlInstancePortDotASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ASSOCIATIONNOARROW) ,VhdlPortNoDotNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SUBTYPEINDICATION) ,VhdlVarDecASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.IDENTIFIERLIST) ,VarListASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SUBTYPEFIRSTNAME) ,SubtypeFirstNameNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.IDENTIFIER) ,IdentASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.PROCESSSTATEMENT) ,VhdlProcessStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PROCESSHEAD) ,VhdlProcessHeadASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SENSITIVITYLIST) ,VhdlSensitivityListASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PROCESSSTATEMENTPART) ,VhdlProcessStatementPartASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PROCESSSTATEMENT) ,VhdlProcessStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PROCESSDECLARATIVEPART) ,VhdlProcessDeclarativePartASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SEQUENTIALSTATEMENT) ,VhdlSequentialStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SIGNALASSIGNMENTSTATEMENT) ,VhdlSignalAssignmentStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.VARIABLEASSIGNMENTSTATEMENT) ,VhdlSignalAssignmentStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SEQUENCEOFSTATEMENTS) ,VhdlSequenceOfStatementsASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.IFSTATEMENT) ,VhdlIfStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.IFCOND) ,VhdlIfConditionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.IFFIRSTCOND) ,VhdlIfConditionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.IFLASTCOND) ,VhdlIfConditionASTNode.Default.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.CASESTATEMENT) ,VhdlCaseStatementASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CASESTATEMENTALTERNATIVE) ,VhdlCaseConditionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CHOICES) ,VhdlChoicesASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.CONCURRENTSIGNALASSIGNMENTSTATEMENT) ,VhdlConcurrentSignalAssignmentNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CONDITIONALSIGNALASSIGNMENT) ,VhdlConditionalSignalAssignmentNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SELECTEDSIGNALASSIGNMENT) ,SelectedSignalAssignmentNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SELECTEDWAVEFORMS) ,SelectedWaveformsNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SELECTEDWAVEFORMITEM) ,SelectedWaveFormItemNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.NAME) ,NewNameASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.NAMERANGE) ,NameRangeASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.NAMEDOT) ,NameDotASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.NAMEEXPRESSION) ,NameExpressionASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.NAMEATTRIBUTE) ,NameAttributeASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.RANGE) ,VhdlVarRangeASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.DISCRETERANGE) ,VhdlVarDiscreteRangeASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.EXPRESSIONORRANGE) ,ExpressionOrRangeASTNode.class);

                
                map.put(Integer.valueOf(VhdlTokenTypes.EXPRESSION) ,ExpressionOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.RELATION) ,RelationOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SHIFTEXPRESSION) ,ShiftOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SIMPLEEXPRESSION) ,AddOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.TERM) ,MultiplyOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.FACTOR) ,FactorOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PRIMARY) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.LITERAL) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ENUMERATIONLITERAL) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ADDINGTERM) ,AddingTermASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.UNARYOP) ,AddingTermASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PARENOP) ,ParenOpASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.BITSTRINGLIT) ,VhdlBitStringASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.STRINGLIT) ,VhdlStringASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.NUMERICLITERAL) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.DECIMAL_LITERAL) ,NumberASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CHARACTER_LITERAL) ,CharacterLitASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.CONDITIONALWAVEFORMS) ,VhdlConditionalWaveformsASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.CONDITIONALWAVEFORMSBI) ,VhdlConditionalWaveformsBiASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.SUBPROGRAMDECLARATION) ,VhdlSubProgramDeclarationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SUBPROGRAMDBODY) ,VhdlSubProgramBodyASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.SUBPROGRAMDECLARATIVEPART) ,SubProgramDeclarativePartNode.class);

                map.put(Integer.valueOf(VhdlTokenTypes.FUNCTIONDECLARATION) ,VhdlFunctionDeclarationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PROCEDUREDECLARATION) ,ProcedureDeclarationNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.FUNCTIONPARAMETERLIST) ,FunctionParameterListNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.CONTEXTCLAUSE) ,VhdlContextClauseASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.LIBRARYCLAUSE) ,VhdlLibraryClauseASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.USECLAUSE) ,VhdlUseClauseASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ALL) ,VhdlAllLiteralASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.AGGREGATE) ,VhdlAggregateASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ELEMENTASSOCIATION) ,VhdlElementAssociationASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ALIASDECLARATION) ,AliasDeclarationNode.class);
                
               
                
                map.put(Integer.valueOf(VhdlTokenTypes.LABELCOLONWRAP) ,LabelColonWrapNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ATTRIBUTEDECLARATION) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ATTRIBUTESPECIFICATION) ,NotSupportedASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.GENERATESTATEMENT) ,GenerateStatementNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERATEFOR) ,GenerateForNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERATEIF) ,GenerateIfNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERATEDECLARATIVEPART) 
                        ,GenerateDeclarativePartNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.GENERATESTATEMENTPART) 
                        ,GenerateStatementPartNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.PARAMETERSPECIFICATION)
                        ,ParameterSpecificationNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.LOOPSTATEMENT) ,LoopStatementNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ITERATIONSCHEMEQ) ,PassThroughASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ITERATIONSCHEMEFOR) ,IterationForNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ITERATIONSCHEMEWHILE) ,IterationWhileNode.class);
                
                //map.put(Integer.valueOf(VhdlTokenTypes.PROCEDURECALLSTATEMENT) ,ProcedureCallNode.class);
                //map.put(Integer.valueOf(VhdlTokenTypes.SUBTYPEFIRSTNAME) ,PassThroughASTNode.class);
                
                map.put(Integer.valueOf(VhdlTokenTypes.WAITSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.ASSERTIONSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.REPORTSTATEMENT) ,NotSupportedASTNode.class);
               
                map.put(Integer.valueOf(VhdlTokenTypes.NEXTSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.EXITSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.RETURNSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.BREAKSTATEMENT) ,NotSupportedASTNode.class);
               
                map.put(Integer.valueOf(VhdlTokenTypes.CONCURRENTASSERTIONSTATEMENT) ,NotSupportedASTNode.class);
                map.put(Integer.valueOf(VhdlTokenTypes.BLOCKSTATEMENT), BlockStatementNode.class);
        
                map.put(Integer.valueOf(VhdlTokenTypes.PHYSICALLITERAL), PhysicalLiteralNode.class);

	}
	
    public VhdlFactory(java.util.Hashtable tab) {
        super(tab);
        
    }
    
    public Class getASTNodeType(int tokenType) {
    	return TopASTNode.class;
    }
    
    public AST create(int index) {
        AST ast = this.decodeType(index);
        if (ast != null) return ast;
        
        return super.create(index);
    }
    public AST create(int index,String name) {
        AST ast = this.decodeType(index);
        if (ast != null) {
            ast.setText(name);
            return ast;
        }
        return super.create(index,name);
    }
    
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
