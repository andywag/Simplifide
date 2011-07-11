

 
header{package com.simplifide.base.vhdl.parse.grammar;


import com.simplifide.base.sourcefile.antlr.tok.*;


}

class VhdlParser extends Parser;
options {
    k = 3;                        // two token lookahead
    buildAST = true;
    exportVocab = Vhdl;
}

tokens
{
    ROOT;
    DESIGNUNIT;
    CONTEXTCLAUSE;
    LIBRARYCLAUSE;
    LIBRARYUNIT;
    USECLAUSE;
    PRIMARYUNIT;
    SECONDARYUNIT;
    ARCHITECTUREBODY;
    ARCHITECTUREDECLARATIVEPART;
    ARCHITECTURESTATEMENTPART;
    ARCHITECTURESTATEMENT;
    BLOCKDECLARATIVEITEM;
    SUBPROGRAMDECLARATION;

    PACKAGEBODY;
    PACKAGEDECLARATION;
    PACKAGEDECLARATIVEPART;
    PACKAGEBODYDECLARATIVEPART;

    ENTITYDECLARATION;
    ENTITYHEADER;
    ENTITYDECLARATIVEPART;
    ENTDEC1;
    ENTDEC2;
    GENERICCLAUSE;
    PORTCLAUSE;
    PORTLIST;
    PORTELEMENT;
    CONSTANTPORT;
    SIGNALPORT;

    INTERFACEVARIABLEDECLARATION;
    SUBTYPEINDICATION;
    SUBTYPEFIRSTNAME;
    MODE;

    CONSTANTDECLARATION;
    SIGNALDECLARATION;
    VARIABLEDECLARATION;
    VARASSIGN;
    SIGNALKIND;
    
    SUBTYPEDECLARATION;

    TYPEDECLARATION;
    ENUMERATIONTYPEDEFINITION;

	FILETYPEDEFINITION;
	ACCESSTYPEDEFINITION;
    RECORDTYPEDEFINITION;
    RECORDELEMENT;
    ELEMENTDECLARATION;

    UNCONSTRAINEDARRAYDEFINITION;
    CONSTRAINEDARRAYDEFINITION;
    INDEXSUBTYPEDEFINITION;
    INDEXCONSTRAINT;
    DISCRETERANGE;
    RANGE;

    IDENTIFIERLIST;
    IDENTIFIER;
    NAME;
    NAMEDOT;
    NAMEAPOSTROPHY;
    NAMEATTRIBUTE;
    NAMEEXPRESSION;
    NAMEPARAMETERPART;
    NAMERANGE;

    LABELCOLON;

    COMPONENTINSTANTIAIONSTATEMENT;
    // Need Component Name
    GENERICMAPASPECT;
    PORTMAPASPECT;
    ASSOCIATIONLIST;
    ASSOCIATIONARROW;
    ASSOCIATIONNOARROW;

    EXPRESSION;
    RELATION;
    SHIFTEXPRESSION;
    SIMPLEEXPRESSION;
    TERM;
    FACTOR;
    PRIMARY;
    PARENOP;
    
    ELEMENTASSOCIATION;
    AGGREGATE;

    PROCESSSTATEMENT;
    PROCESSHEAD;
    PROCESSDECLARATIVEPART;
    PROCESSSTATEMENTPART;

    IFSTATEMENT;
    IFFIRSTCOND;
    IFCOND;
    IFLASTCOND;
    IFEND;

    CASESTATEMENT;
    CASESTATEMENTALTERNATIVE;
    CASEEND;
    CHOICES;

    SEQUENCEOFSTATEMENTS;

    CONCURRENTSIGNALASSIGNMENTSTATEMENT;
    CONDITIONALSIGNALASSIGNMENT;
    CONDITIONALWAVEFORMS;
    CONDITIONALWAVEFORMSBI;

    SELECTEDSIGNALASSIGNMENT;
    SELECTEDWAVEFORMS;
    SELECTEDWAVEFORMITEM;

    
    BITSTRINGLIT;
    STRINGLIT;
    LITERAL;
    ADDINGTERM;
    UNARYOP;

    ENUMERATIONLITERAL;
    CHARACTERLIT;
    NUMERICLITERAL;

    TARGET;
    FUNCTIONDECLARATION;
	PROCEDUREDECLARATION;
	
	FUNCTIONPARAMETERLIST;
    
    COMPONENTDECLARATION;

    SEQUENTIALSTATEMENT;
    SIGNALASSIGNMENTSTATEMENT;
    VARIABLEASSIGNMENTSTATEMENT;
    SENSITIVITYLIST;

    SUBPROGRAMDBODY;
    SUBPROGRAMDECLARATIVEPART;
    SUBPROGRAMSTATEMENTPART;

    WAITSTATEMENT;      // Not Supported
    ASSERTIONSTATEMENT; // Not Supported
    REPORTSTATEMENT;    // Not Supported
    
    NEXTSTATEMENT;      // Not Supported
    EXITSTATEMENT;      // Not Supported
    RETURNSTATEMENT;    // Not Supported
    BREAKSTATEMENT;     // Not Supported
    PROCEDURECALLSTATEMENT; // Not Supported

	LOOPSTATEMENT;      
	ITERATIONSCHEMEQ;      
	ITERATIONSCHEMEWHILE;
	ITERATIONSCHEMEFOR;

    SIGNALPORTDEC;
    CONSTANTPORTDEC;
    ALIASTYPE;
    ALIASDECLARATION;

    INSTUNITCOMPOPT;
    INSTUNITCOMP;
    INSTUNITENT;
    INSTUNITCONF;

    COMPONENTISDUMMY;
    GENERICCLAUSEDUMMY;
    PORTCLAUSEDUMMY;

    CONCURRENTASSERTIONSTATEMENT;
    
    LABELCOLONWRAP;

    PORTASSIGNMENT;
    CONSTANTPORTINQ;
    SIGNALPORTMODEQ;
    SIGNALPORTBUSQ;
    
    ATTRIBUTEDECLARATION;
    ATTRIBUTESPECIFICATION;

	GENERATESTATEMENT;
	GENERATEDECLARATIVEPART;
	GENERATESTATEMENTPART;
	GENERATEFOR;
	GENERATEIF;
	PARAMETERSPECIFICATION;
	FILEDECLARATION;
	FILEOPENINFORMATION;
	OPTS;
	
	POSTPONEDQ;
	FUNCTIONPURE;
	BLOCKSTATEMENT;
	PHYSICALLITERAL;
	
	EXPRESSIONORRANGE;
}

{
   
}


// PROTECTED TYPES

protected_type_definition : protected_type_declaration |
							protected_type_body;

protected_type_declaration : PROTECTED protected_type_declarative_part
							 END PROTECTED (name)?;

protected_type_declarative_part : (protected_type_declarative_item)*;

protected_type_declarative_item : subprogram_declaration
				//| subprogram_instantiation_declaration
				| attribute_specification
				| use_clause;

protected_type_body : PROTECTED BODY protected_type_body_declarative_part
 					  END PROTECTED BODY (name)?;

protected_type_body_declarative_part : (protected_type_body_declarative_item)*;

protected_type_body_declarative_item : (subprogram_declaration) => subprogram_declaration
| subprogram_body
//| subprogram_instantiation_declaration
| package_declaration
| package_body
//| package_instantiation_declaration
| type_declaration
| subtype_declaration
| constant_declaration
| variable_declaration
| file_declaration
| alias_declaration
| attribute_declaration
| attribute_specification
| use_clause
| group_template_declaration
| group_declaration;

abstract_literal
  : DECIMAL_LITERAL
  | BASED_LITERAL
  ;

access_type_definition
  : ACCESS subtype_indication
  {#access_type_definition = #(#[ACCESSTYPEDEFINITION,"AccessTypeDefinition"],#access_type_definition);};

across_aspect
  : identifier_list ( tolerance_aspect )? ( VARASGN expression )? ACROSS
  ;

actual_designator
  : expression
  | OPEN
  ;

actual_parameter_part
  : association_list
  ;

actual_part
  : (name LPAREN)=> name LPAREN actual_designator RPAREN
  | actual_designator
  ;


aggregate
  : LPAREN element_association ( COMMA element_association )* RPAREN
  {#aggregate = #(#[AGGREGATE,"Aggregate"],#aggregate);};

alias_type : ( COLON alias_indication )?
  {#alias_type = #(#[ALIASTYPE,"AliasType"],#alias_type);};

alias_declaration
  : ALIAS alias_designator alias_type IS name_without_attribute ( signature )? SEMI
  {#alias_declaration = #(#[ALIASDECLARATION,"AliaseDeclaration"],#alias_declaration);};

alias_designator
  : identifier
  | CHARACTER_LITERAL
  | STRING_LITERAL
  ;

alias_indication
  : (subnature_indication)=> subnature_indication
  | subtype_indication
  ;

allocator
  : NEW ( (name APOSTROPHE)=> qualified_expression | subtype_indication )
  ;

architecture_body
  : ARCHITECTURE identifier OF name IS
    architecture_declarative_part
    BEGIN
    architecture_statement_part
    END ( ARCHITECTURE )? ( identifier )? SEMI
{#architecture_body = #(#[ARCHITECTUREBODY,"ArchitectureBody"],#architecture_body);};
  

architecture_declarative_part
  : ( block_declarative_item )*
    {#architecture_declarative_part = #(#[ARCHITECTUREDECLARATIVEPART,"ArchitectureDeclarativePart"],#architecture_declarative_part);};
  

architecture_statement
  : (block_statement
  | (( label_colon )? ( POSTPONED )? PROCESS)=> process_statement
  | (( label_colon )? ( POSTPONED )? procedure_call SEMI)=> concurrent_procedure_call_statement
  | (( label_colon )? ( POSTPONED )? ASSERT)=>concurrent_assertion_statement
  | (( label_colon )? ( POSTPONED )? ( conditional_signal_assignment | selected_signal_assignment ))=>concurrent_signal_assignment_statement
  | (label_colon instantiated_unit)=>component_instantiation_statement
  | (label_colon generation_scheme GENERATE)=>generate_statement
  | (( label_colon )? BREAK ( break_list )? ( sensitivity_clause )?)=>concurrent_break_statement);
      

architecture_statement_part
  : ( architecture_statement )*
    {#architecture_statement_part = #(#[ARCHITECTURESTATEMENTPART,"ArchitectureStatementPart"],#architecture_statement_part);};
  

array_nature_definition
  : (ARRAY LPAREN index_subtype_definition)=> unconstrained_nature_definition
  | constrained_nature_definition
  ;

array_type_definition
  : (unconstrained_array_definition)=> unconstrained_array_definition
  | constrained_array_definition
  ;

assertion
  : ASSERT condition ( REPORT expression )? ( SEVERITY expression )?
  ;

assertion_statement :  assertion SEMI
    {#assertion_statement = #(#[ASSERTIONSTATEMENT,"AssertionStatement"],#assertion_statement);};

association_element
  : (formal_part ARROW)=> association_arrow | association_no_arrow;

association_arrow : formal_part ARROW (actual_part)?
    {#association_arrow = #(#[ASSOCIATIONARROW,"AssociationArrow"],#association_arrow);};

association_no_arrow : actual_part
    {#association_no_arrow = #(#[ASSOCIATIONNOARROW,"AssociationNoArrow"],#association_no_arrow);};

association_list :(association_element)? ( COMMA association_element )*
   {#association_list = #(#[ASSOCIATIONLIST,"AssociationList"],#association_list);};

attribute_declaration
  : ATTRIBUTE label_colon name SEMI
  {#attribute_declaration = #(#[ATTRIBUTEDECLARATION,"attribute_declaration"],#attribute_declaration);};

// Need to add several tokens here, for they are both, VHDLAMS reserved words
// and attribute names.
// (25.2.2004, e.f.)
attribute_designator
  : identifier
  | RANGETOK
  | ACROSS
  | THROUGH
  //| REFERENCE
  | TOLERANCE
  ;

attribute_specification
  : ATTRIBUTE attribute_designator OF entity_specification IS expression SEMI
  {#attribute_specification = #(#[ATTRIBUTESPECIFICATION,"attribute_specification"],#attribute_specification);};

base_unit_declaration
  : identifier SEMI
  ;

binding_indication
  : ( USE entity_aspect )? ( generic_map_aspect ) ( port_map_aspect )
  ;

block_configuration
  : FOR block_specification
    ( use_clause )*
    ( configuration_item )*
    END FOR SEMI
  ;

block_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | signal_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | component_declaration
  | attribute_declaration
  | attribute_specification
  | configuration_specification
  | disconnection_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  | nature_declaration
  | subnature_declaration
  | quantity_declaration
  | terminal_declaration
      {#block_declarative_item = #(#[BLOCKDECLARATIVEITEM,"BlockDeclarativeItem"],#block_declarative_item);};

block_declarative_part
  : ( block_declarative_item )*
  ;

block_header
  : ( generic_clause ( generic_map_aspect SEMI )? )?
    ( port_clause ( port_map_aspect SEMI )? )?
  ;

block_specification
  : (identifier)=> identifier ( LPAREN index_specification RPAREN )?
  | name
  ;

block_statement
  : label_colon BLOCK ( LPAREN expression RPAREN )? ( IS )?
    block_header
    block_declarative_part BEGIN
    block_statement_part 
    END BLOCK ( identifier )? SEMI
       {#block_statement = #(#[BLOCKSTATEMENT,"BlockStatement"],#block_statement);};


block_statement_part
  : ( architecture_statement )*
  ;

branch_quantity_declaration
  : QUANTITY ( (across_aspect)=> across_aspect )?
    ( through_aspect )? terminal_aspect SEMI
  ;

break_element
  : ( break_selector_clause )? name ARROW expression
  ;

break_list
  : break_element ( COMMA break_element )*
  ;

break_selector_clause
  : FOR name USE
  ;

break_statement
  : BREAK ( break_list )? ( WHEN condition )? SEMI
  {#break_statement = #(#[BREAKSTATEMENT,"BreakStatement"],#break_statement);};

case_statement
  : label_colon_wrap CASE expression IS( case_statement_alternative )+ case_end
  {#case_statement = #(#[CASESTATEMENT,"CaseStatement"],#case_statement);};



case_end : END CASE ( identifier )? SEMI
      {#case_end  = #(#[CASEEND,"CaseEnd"],#case_end);};

case_statement_alternative
  : WHEN choices ARROW sequence_of_statements
  {#case_statement_alternative = #(#[CASESTATEMENTALTERNATIVE,"CaseStatementAlternative"],#case_statement_alternative);};

choice:
	expression (direction expression)?
  | OTHERS
  ;

choices : choice ( BAR choice )*
  {#choices  = #(#[CHOICES,"Choices"],#choices);};

component_configuration
  : FOR component_specification
    ( binding_indication SEMI )?
    ( block_configuration )?
    END FOR SEMI
  ;

component_is_dummy :  ( IS )?
    {#component_is_dummy  = #(#[COMPONENTISDUMMY,"ComponentIsDummy"],#component_is_dummy);};

component_declaration
  : COMPONENT identifier component_is_dummy
    entity_header
    END COMPONENT ( identifier )? SEMI
        {#component_declaration = #(#[COMPONENTDECLARATION,"ComponentDeclaration"],#component_declaration);};
  

component_instantiation_statement
  : label_colon instantiated_unit ( generic_map_aspect ) ( port_map_aspect ) SEMI
        {#component_instantiation_statement = #(#[COMPONENTINSTANTIAIONSTATEMENT,"ComponentInstantiationStatement"],#component_instantiation_statement);};

component_specification
  : instantiation_list COLON name
  ;

composite_nature_definition
  : array_nature_definition
  | record_nature_definition
  ;

composite_type_definition
  : array_type_definition
  | record_type_definition
  ;

concurrent_assertion_statement
  : ( label_colon )? ( POSTPONED )? assertion SEMI
          {#concurrent_assertion_statement = #(#[CONCURRENTASSERTIONSTATEMENT,"concurrent_assertion_statement"],#concurrent_assertion_statement);};

concurrent_break_statement
  : ( label_colon )? BREAK ( break_list )? ( sensitivity_clause )?
    ( WHEN condition )? SEMI
  ;

concurrent_procedure_call_statement
  : ( label_colon )? ( POSTPONED )? procedure_call SEMI
  ;

concurrent_signal_assignment_statement : label_colon_wrap postponedQ ( conditional_signal_assignment | selected_signal_assignment )
 {#concurrent_signal_assignment_statement = #(#[CONCURRENTSIGNALASSIGNMENTSTATEMENT,"ConcurrentSignalAssignmentStatement"],#concurrent_signal_assignment_statement);};

postponedQ : ( POSTPONED )?
	{#postponedQ = #(#[POSTPONEDQ,"postponedQ"],#postponedQ);};

condition
  : expression
  ;

condition_clause
  : UNTIL condition
  ;

conditional_signal_assignment
  : target LE opts conditional_waveforms SEMI
   {#conditional_signal_assignment = #(#[CONDITIONALSIGNALASSIGNMENT,"ConditionalSignalAssignment"],#conditional_signal_assignment);};

conditional_waveforms
  : waveform ( (WHEN condition ELSE)=> conditional_waveforms_bi )?
    ( WHEN condition )?
     {#conditional_waveforms = #(#[CONDITIONALWAVEFORMS,"ConditionalWaveforms"],#conditional_waveforms);};

conditional_waveforms_bi
  : WHEN condition ELSE waveform
    ( (WHEN condition ELSE)=> conditional_waveforms_bi )?
       {#conditional_waveforms_bi = #(#[CONDITIONALWAVEFORMSBI,"ConditionalWaveformsBi"],#conditional_waveforms_bi);};

configuration_declaration
  : CONFIGURATION identifier OF name IS
    configuration_declarative_part
    block_configuration
    END ( CONFIGURATION )? ( identifier )? SEMI
  ;

configuration_declarative_item
  : use_clause
  | attribute_specification
  | group_declaration
  ;

configuration_declarative_part
  : ( configuration_declarative_item )*
  ;

configuration_item
  : block_configuration
  | component_configuration
  ;

configuration_specification
  : FOR component_specification binding_indication SEMI
  ;

constant_declaration : CONSTANT identifier_list COLON subtype_indication var_assign SEMI
{#constant_declaration = #(#[CONSTANTDECLARATION,"ConstantDeclaration"],#constant_declaration);};
  

constrained_array_definition : ARRAY index_constraint OF subtype_indication
 {#constrained_array_definition = #(#[CONSTRAINEDARRAYDEFINITION,"ConstrainedArrayDefinition"],#constrained_array_definition);}; 

constrained_nature_definition
  : ARRAY index_constraint OF subnature_indication
  ;

constraint
  : range_constraint
  | index_constraint
  ;

context_clause : ( context_item )* 
    {#context_clause = #(#[CONTEXTCLAUSE,"ContextClause"],#context_clause);};

context_item : library_clause | use_clause;

delay_mechanism
  : TRANSPORT
  | ( REJECT expression )? INERTIAL
  ;


source_text : ( design_unit )* EOF 
	{#source_text = #(#[ROOT,"Root"],#source_text);};
source_text_name : name  
	{#source_text_name = #(#[ROOT,"Root"],#source_text_name);};

design_unit
  : context_clause library_unit {#design_unit = #(#[DESIGNUNIT,"DesignUnit"],#design_unit);};
  

designator
  : identifier
  | STRING_LITERAL
  ;

direction
  : TO
  | DOWNTO
  ;

disconnection_specification
  : DISCONNECT guarded_signal_specification AFTER expression SEMI
  ;

discrete_range : ((range)=> range | subtype_indication)
     {#discrete_range = #(#[DISCRETERANGE,"DiscreteRange"],#discrete_range);}; 

element_association
  : ( (choices ARROW)=> choices ARROW )? expression
    {#element_association = #(#[ELEMENTASSOCIATION,"ElementAssociation"],#element_association);};

element_declaration : identifier_list COLON element_subtype_definition SEMI
        {#element_declaration = #(#[ELEMENTDECLARATION,"ElementDeclaration"],#element_declaration);};

element_subnature_definition
  : subnature_indication
  ;

element_subtype_definition
  : subtype_indication
  ;

entity_aspect
  : ENTITY name ( LPAREN identifier RPAREN )?
  | CONFIGURATION name
  | OPEN
  ;

entity_class
  : ENTITY
  | ARCHITECTURE
  | CONFIGURATION
  | PROCEDURE
  | FUNCTION
  | PACKAGE
  | TYPE
  | SUBTYPE
  | CONSTANT
  | SIGNAL
  | VARIABLE
  | COMPONENT
  | LABEL
  | LITERAL
  | UNITS
  | GROUP
  | FILE
  | NATURE
  | SUBNATURE
  | QUANTITY
  | TERMINAL
  ;

entity_class_entry
  : entity_class ( BOX )?
  ;

entity_class_entry_list
  : entity_class_entry ( COMMA entity_class_entry )*
  ;

entity_declaration
  : ENTITY identifier IS entity_header entity_declarative_part ent_dec1 ent_dec2
    {#entity_declaration = #(#[ENTITYDECLARATION,"EntityDeclaration"],#entity_declaration);};

ent_dec1 : ( BEGIN entity_statement_part )?
    {#ent_dec1 = #(#[ENTDEC1,"EntDec1"],#ent_dec1);};

ent_dec2 :  END ( ENTITY )? ( identifier )? SEMI
    {#ent_dec2 = #(#[ENTDEC2,"EntDec2"],#ent_dec2);};

entity_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  //| subprogram_instantiation_declaration
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | signal_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | attribute_declaration
  | attribute_specification
  | disconnection_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  | nature_declaration
  | subnature_declaration
  | quantity_declaration
  | terminal_declaration;


//subprogram_instantiation_declaration : subprogram_kind designator "is" "new" name (signature)? (generic_map_aspect)?;

entity_declarative_part
  : ( entity_declarative_item )*
{#entity_declarative_part = #(#[ENTITYDECLARATIVEPART,"EntityDeclarativePart"],#entity_declarative_part);};
  

entity_designator
  : entity_tag ( signature )?
  ;

generic_clause_dummy : ( generic_clause )?
    {#generic_clause_dummy = #(#[GENERICCLAUSEDUMMY,"GeneriClauseDummy"],#generic_clause_dummy);};

port_clause_dummy : ( port_clause )?
    {#port_clause_dummy = #(#[PORTCLAUSEDUMMY,"PortClauseDummy"],#port_clause_dummy);};

entity_header
  : generic_clause_dummy port_clause_dummy
  {#entity_header = #(#[ENTITYHEADER,"EntityHeader"],#entity_header);};

entity_name_list
  : entity_designator ( COMMA entity_designator )*
  | OTHERS
  | ALL
  ;

entity_specification
  : entity_name_list COLON entity_class
  ;

entity_statement
  : (( label_colon )? ( POSTPONED )? ASSERT)=> concurrent_assertion_statement
  | (( label_colon )? ( POSTPONED )? PROCESS)=> process_statement
  | concurrent_procedure_call_statement
  ;

entity_statement_part
  : ( entity_statement )*
  ;

entity_tag
  : identifier
  | CHARACTER_LITERAL
  | STRING_LITERAL
  ;

enumeration_literal : (identifier | character_lit)
      {#enumeration_literal = #(#[ENUMERATIONLITERAL,"EnumerationLiteral"],#enumeration_literal);};

character_lit : CHARACTER_LITERAL
      {#character_lit = #(#[CHARACTERLIT,"CharacterLit"],#character_lit);};

enumeration_type_definition
  : LPAREN enumeration_literal ( COMMA enumeration_literal )* RPAREN
    {#enumeration_type_definition = #(#[ENUMERATIONTYPEDEFINITION,"EnumerationTypeDefinition"],#enumeration_type_definition);};
    

exit_statement
  :  EXIT ( identifier )? ( WHEN condition )? SEMI
  {#exit_statement = #(#[EXITSTATEMENT,"ExitStatement"],#exit_statement);};

// NOTE that NAND/NOR are in (...)* now (used to be in (...)?).
// (21.1.2004, e.f.)
expression : unary_op ( options{greedy=true;}: combined_operator unary_op )*
    {#expression = #(#[EXPRESSION,"Expression"],#expression);};
 
/*  
relation
  : shift_expression
    ( options{greedy=true;}: relational_operator shift_expression )?
    {#relation = #(#[RELATION,"Relation"],#relation);};  
    
shift_expression
  : simple_expression
    ( options{greedy=true;}: shift_operator simple_expression )?
      {#shift_expression = #(#[SHIFTEXPRESSION,"ShiftExpression"],#shift_expression);};  
 
term : factor ( options{greedy=true;}: multiplying_operator factor )*
    {#term = #(#[TERM,"Term"],#term);};
    
factor_op : unary_op ( options{greedy=true;}: DOUBLESTAR unary_op )?
    {#factor_op = #(#[FACTOR,"Factor"],#factor_op);};

 */
 
simple_expression
  :  unary_op ( options{greedy=true;}: combined_operator unary_op )*
  {#simple_expression = #(#[SIMPLEEXPRESSION,"SimpleExpression"],#simple_expression);};

/*
adding_term : ( PLUS | MINUS )? term
  {#adding_term = #(#[ADDINGTERM,"AddingTerm"],#adding_term);};
*/

combined_operator : logical_operator | relational_operator | shift_operator | adding_operator | multiplying_operator | DOUBLESTAR;

relational_operator : EQ | NEQ | LOWERTHAN | LE | GREATERTHAN | GE;
logical_operator : AND | OR | NAND | NOR | XOR | XNOR ;
shift_operator : SLL | SRL | SLA | SRA | ROL | ROR ;
adding_operator : PLUS | MINUS | AMPERSAND;
multiplying_operator : MUL | DIV | MOD | REM;

//factor :  factor_op;
  



unary_op : ( PLUS | MINUS | ABS | NOT)? primary
    {#unary_op = #(#[UNARYOP,"UnaryOp"],#unary_op);};

file_declaration
  : FILE identifier_list COLON subtype_indication (file_open_information )? SEMI
  {#file_declaration = #(#[FILEDECLARATION,"FileDeclaration"],#file_declaration);};

file_logical_name
  : expression
  ;

file_open_information
  : ( OPEN expression )? IS file_logical_name
 {#file_open_information = #(#[FILEOPENINFORMATION,"FileOpenInformation"],#file_open_information);};

file_type_definition
  : FILE OF name
  {#file_type_definition = #(#[FILETYPEDEFINITION,"FileTypeDefinition"],#file_type_definition);};

formal_parameter_list
  : port_list
  ;

formal_part
  : name ( LPAREN  name RPAREN )?
  ;

free_quantity_declaration
  : QUANTITY identifier_list COLON subtype_indication
    ( VARASGN expression )? SEMI
  ;

/*
function_call
  : name ( LPAREN actual_parameter_part RPAREN )?
  ;
*/
// Modified from above
function_call
  : name LPAREN actual_parameter_part RPAREN 
  ;

generate_statement
  : label_colon generation_scheme
    GENERATE
    generate_declarative_part
    generate_statement_part
    END GENERATE ( identifier )? SEMI
  {#generate_statement = #(#[GENERATESTATEMENT,"generate_statement"],#generate_statement);};

generate_declarative_part : ( ( block_declarative_item )* BEGIN )?
	{#generate_declarative_part = #(#[GENERATEDECLARATIVEPART,"generate_declarative_part"],#generate_declarative_part);};

generate_statement_part : ( architecture_statement )*
	{#generate_statement_part = #(#[GENERATESTATEMENTPART,"generate_statement_part"],#generate_statement_part);};



generation_scheme
  : generate_for
  | generate_if;

generate_for : FOR parameter_specification
	{#generate_for = #(#[GENERATEFOR,"generate_for"],#generate_for);};
	
generate_if :  IF condition
	{#generate_if = #(#[GENERATEIF,"generate_if"],#generate_if);};

generic_clause : GENERIC LPAREN port_list RPAREN SEMI
  {#generic_clause = #(#[GENERICCLAUSE,"GenericClause"],#generic_clause);};



generic_map_aspect
  : (GENERIC MAP LPAREN association_list RPAREN) ?
  {#generic_map_aspect = #(#[GENERICMAPASPECT,"GenericMapAspect"],#generic_map_aspect);};

group_constituent
  : name
  | CHARACTER_LITERAL
  ;

group_constituent_list
  : group_constituent ( COMMA group_constituent )*
  ;

group_declaration
  : GROUP label_colon name
    LPAREN group_constituent_list RPAREN SEMI
  ;

group_template_declaration
  : GROUP identifier IS LPAREN entity_class_entry_list RPAREN SEMI
  ;

guarded_signal_specification
  : signal_list COLON name
  ;

identifier : (BASIC_IDENTIFIER | EXTENDED_IDENTIFIER | REFERENCE)
  {#identifier = #(#[IDENTIFIER,"Identifier"],#identifier);};

identifier_list
  : identifier ( COMMA identifier )*
  {#identifier_list = #(#[IDENTIFIERLIST,"IdentifierList"],#identifier_list);};

if_statement
  : label_colon_wrap if_first_cond
    ( if_cond )* 
    ( if_last_cond )?
    if_end
  {#if_statement = #(#[IFSTATEMENT,"IfStatement"],#if_statement);};

if_end : END IF ( identifier )? SEMI
   {#if_end = #(#[IFEND,"IfEnd"],#if_end);};

if_first_cond : IF condition THEN sequence_of_statements
    {#if_first_cond = #(#[IFFIRSTCOND,"IfFirstCond"],#if_first_cond);};

if_cond       : ELSIF condition THEN sequence_of_statements
    {#if_cond = #(#[IFCOND,"IfCond"],#if_cond);};

if_last_cond  : ELSE sequence_of_statements
    {#if_last_cond = #(#[IFLASTCOND,"IfLastCond"],#if_last_cond);};

index_constraint : LPAREN discrete_range ( COMMA discrete_range )* RPAREN
   {#index_constraint = #(#[INDEXCONSTRAINT,"IndexConstraint"],#index_constraint);}; 

index_specification
  : (discrete_range)=> discrete_range
  | expression
  ;

index_subtype_definition : name RANGETOK BOX
    {#index_subtype_definition= #(#[INDEXSUBTYPEDEFINITION,"index_subtype_definition"],#index_subtype_definition);};

inst_unit_comp_opt : ( COMPONENT )?
    {#inst_unit_comp_opt= #(#[INSTUNITCOMPOPT,"inst_unit_comp_opt"],#inst_unit_comp_opt);};

inst_unit_comp : inst_unit_comp_opt name
    {#inst_unit_comp= #(#[INSTUNITCOMP,"inst_unit_comp"],#inst_unit_comp);};

inst_unit_ent : ENTITY name_dot_only ( LPAREN identifier RPAREN )?
    {#inst_unit_ent= #(#[INSTUNITENT,"inst_unit_ent"],#inst_unit_ent);};

inst_unit_conf : CONFIGURATION name
    {#inst_unit_conf= #(#[INSTUNITCONF,"inst_unit_conf"],#inst_unit_conf);};



instantiated_unit
  : inst_unit_comp
  | inst_unit_ent
  | inst_unit_conf
  ;

instantiation_list
  : identifier ( COMMA identifier )*
  | OTHERS
  | ALL
  ;

port_assignment : ( VARASGN expression )? ( SIGNAL )? {#port_assignment = #(#[PORTASSIGNMENT,"port_assignment"],#port_assignment);};


constant_port_dec : ( CONSTANT )? {#constant_port_dec = #(#[CONSTANTPORTDEC,"ConstantPortDec"],#constant_port_dec);};
constant_port_inq : (IN) ? {#constant_port_inq = #(#[CONSTANTPORTINQ,"constant_port_inq"],#constant_port_inq);};


constant_port
  : constant_port_dec identifier_list COLON  constant_port_inq subtype_indication port_assignment
    {#constant_port = #(#[CONSTANTPORT,"ConstantPort"],#constant_port);};
  
signal_port_dec :( SIGNAL )? {#signal_port_dec = #(#[SIGNALPORTDEC,"SignalPortDec"],#signal_port_dec);};
signal_port_modeQ : (mode)? {#signal_port_modeQ = #(#[SIGNALPORTMODEQ,"signal_port_modeQ"],#signal_port_modeQ);};
signal_port_busQ : (BUS)? {#signal_port_busQ = #(#[SIGNALPORTBUSQ,"signal_port_busQ"],#signal_port_busQ);};

signal_port
  : signal_port_dec identifier_list COLON signal_port_modeQ subtype_indication signal_port_busQ port_assignment
  {#signal_port = #(#[SIGNALPORT,"SignalPort"],#signal_port);};


interface_declaration
  : (constant_port)=> constant_port
  | (signal_port)=> signal_port
  | interface_variable_declaration
  | interface_file_declaration
  | interface_terminal_declaration
  | interface_quantity_declaration
  ;

port_element : interface_declaration
    {#port_element = #(#[PORTELEMENT,"PortElement"],#port_element);};
  

interface_file_declaration
  : FILE identifier_list COLON subtype_indication
  ;

port_list : (port_element)? ( semi_port_element )*
    {#port_list = #(#[PORTLIST,"PortList"],#port_list);};
  
semi_port_element : SEMI port_element;


interface_quantity_declaration
  : QUANTITY identifier_list COLON ( IN | OUT )? subtype_indication
    ( VARASGN expression )?
  ;





interface_terminal_declaration
  : TERMINAL identifier_list COLON subnature_indication
  ;

interface_variable_declaration
  : ( VARIABLE )? identifier_list COLON
    ( mode )? subtype_indication ( VARASGN expression )?
    {#interface_variable_declaration = #(#[INTERFACEVARIABLEDECLARATION,"InterfaceVariableDeclaration"],#interface_variable_declaration);};
  

iteration_scheme
  : iteration_scheme_while | iteration_scheme_for
  ;

iteration_scheme_while : WHILE condition
	{#iteration_scheme_while = #(#[ITERATIONSCHEMEWHILE,"iteration_scheme_while"],#iteration_scheme_while);};

iteration_scheme_for : FOR parameter_specification
	{#iteration_scheme_for = #(#[ITERATIONSCHEMEFOR,"iteration_scheme_for"],#iteration_scheme_for);};

label_colon : identifier COLON
      {#label_colon = #(#[LABELCOLON,"LabelColon"],#label_colon);};

library_clause
  : LIBRARY logical_name_list SEMI {#library_clause = #(#[LIBRARYCLAUSE,"LibraryClause"],#library_clause);};
  

library_unit
  : (( ARCHITECTURE | PACKAGE BODY )=> secondary_unit | primary_unit)
    {#library_unit = #(#[LIBRARYUNIT,"LibraryUnit"],#library_unit);};
  

literal
  :
 ( NULLTOK
  | (BIT_STRING_LITERAL) => bit_string_lit
  | (DBLQUOTE)=> string_lit
  | (enumeration_literal)=> enumeration_literal
  | numeric_literal)
    {#literal = #(#[LITERAL,"Literal"],#literal);};
  

bit_string_lit : BIT_STRING_LITERAL
    {#bit_string_lit = #(#[BITSTRINGLIT,"BitStringLit"],#bit_string_lit);};

string_lit : STRING_LITERAL
    {#string_lit = #(#[STRINGLIT,"StringLit"],#string_lit);}; 

logical_name
  : identifier
  ;

logical_name_list
  : logical_name ( COMMA logical_name )*
  ;



loop_statement : label_colon_wrap iteration_scheme_q
    LOOP
    sequence_of_statements 
    END LOOP ( identifier )? SEMI
  {#loop_statement = #(#[LOOPSTATEMENT,"LoopStatement"],#loop_statement);};

iteration_scheme_q : ( iteration_scheme )?
	{#iteration_scheme_q = #(#[ITERATIONSCHEMEQ,"iteration_scheme_q"],#iteration_scheme_q);};

mode
  : IN | OUT | INOUT | BUFFER | LINKAGE
    {#mode = #(#[MODE,"Mode"],#mode);};



// was
//   name
//     : simple_name
//     | operator_symbol
//     | selected_name
//     | indexed_name
//     | slice_name
//     | attribute_name
//     ;
// changed to avoid left-recursion to name (from selected_name, indexed_name,
// slice_name, and attribute_name, respectively)
// (2.2.2004, e.f.)



name_without_attribute
  : (( identifier  ) //: (( identifier | STRING_LITERAL )
    ( options{greedy=true;}:
      (
          name_dot 
        | name_apostrophy
        | (LPAREN expression ( COMMA expression )* RPAREN)=>
          name_expression  
        | (LPAREN actual_parameter_part RPAREN)=>
          name_parameter_part  
        | name_range
      )
    )*)
    {#name_without_attribute = #(#[NAME,"Name"],#name_without_attribute);};

name_dot_only
  : (( identifier  ) 
    ( options{greedy=true;}:
      (
          name_dot 
      )
    )*)
    {#name_dot_only = #(#[NAME,"Name"],#name_dot_only);};

name
  : (( identifier  ) //: (( identifier | STRING_LITERAL )
    ( options{greedy=true;}:
      (
          name_dot 
        
        | name_apostrophe_or_aggregate
        //| name_apostrophy
        //| name_attribute
        | name_expression
        //| (LPAREN expression ( COMMA expression )* RPAREN)=> name_expression  
        //| (LPAREN actual_parameter_part RPAREN)=> name_parameter_part  
        //| name_range
      )
    )*)
    {#name = #(#[NAME,"Name"],#name);};

name_apostrophe_or_aggregate : (signature)? APOSTROPHE ((LPAREN) => aggregate | attribute_designator)
	{#name_apostrophe_or_aggregate = #(#[NAMEATTRIBUTE,"NameAttribute"],#name_apostrophe_or_aggregate);};

name_dot : DOT suffix
    {#name_dot = #(#[NAMEDOT,"NameDot"],#name_dot);};
name_apostrophy : APOSTROPHE aggregate
    {#name_apostrophy = #(#[NAMEAPOSTROPHY,"NameApostrophy"],#name_apostrophy);};
name_attribute : ( signature )? APOSTROPHE attribute_designator
    {#name_attribute = #(#[NAMEATTRIBUTE,"NameAttribute"],#name_attribute);};
name_expression : LPAREN expression_or_range ( COMMA expression_or_range )* RPAREN
    {#name_expression = #(#[NAMEEXPRESSION,"NameExpression"],#name_expression);};
name_parameter_part : LPAREN actual_parameter_part RPAREN
    {#name_parameter_part = #(#[NAMEPARAMETERPART,"NameParameterPart"],#name_parameter_part);};
name_range : LPAREN discrete_range ( COMMA discrete_range )* RPAREN
    {#name_range = #(#[NAMERANGE,"NameRange"],#name_range);};

expression_or_range : expression ((direction | ARROW) expression)? | OPEN
	{#expression_or_range = #(#[EXPRESSIONORRANGE,"ExpressionOrRange"],#expression_or_range);};


nature_declaration
  : NATURE identifier IS nature_definition SEMI
  ;

nature_definition
  : scalar_nature_definition
  | composite_nature_definition
  ;

nature_element_declaration
  : identifier_list COLON element_subnature_definition
  ;

next_statement
  : NEXT ( identifier )? ( WHEN condition )? SEMI
      {#next_statement = #(#[NEXTSTATEMENT,"NextStatement"],#next_statement);};

numeric_literal : ((abstract_literal)=> abstract_literal | physical_literal)
    {#numeric_literal = #(#[NUMERICLITERAL,"NumericLiteral"],#numeric_literal);};
  

object_declaration
  : constant_declaration
  | signal_declaration
  | variable_declaration
  | file_declaration
  | terminal_declaration
  | quantity_declaration
  ;

opts : ( GUARDED )? ( delay_mechanism )?
  {#opts= #(#[OPTS,"opts"],#opts);};

package_body : PACKAGE BODY identifier IS package_body_declarative_part END ( PACKAGE BODY )? ( identifier )? SEMI
  {#package_body= #(#[PACKAGEBODY,"PackageBody"],#package_body);};

package_body_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | use_clause
  | group_template_declaration
  | group_declaration
  ;

package_body_declarative_part
  : ( package_body_declarative_item )*
    {#package_body_declarative_part= #(#[PACKAGEBODYDECLARATIVEPART,"PackageBodyDeclarativePart"],#package_body_declarative_part);};

package_declaration : PACKAGE identifier IS package_declarative_part END ( PACKAGE )? ( identifier )? SEMI
    {#package_declaration = #(#[PACKAGEDECLARATION,"PackageDeclaration"],#package_declaration);};

package_declarative_item
  : subprogram_declaration
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | signal_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | component_declaration
  | attribute_declaration
  | attribute_specification
  | disconnection_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  | nature_declaration
  | subnature_declaration
  | terminal_declaration
  ;

package_declarative_part
  : ( package_declarative_item )*
    {#package_declarative_part = #(#[PACKAGEDECLARATIVEPART,"PackageDeclarativePart"],#package_declarative_part);};  


parameter_specification
  : identifier IN discrete_range
  {#parameter_specification = #(#[PARAMETERSPECIFICATION,"ParameterSpecification"],#parameter_specification);};  

physical_literal : ( abstract_literal )? name
    {#physical_literal = #(#[PHYSICALLITERAL,"PhysicalLiteral"],#physical_literal);};  


physical_type_definition
: range_constraint UNITS base_unit_declaration
    ( secondary_unit_declaration )* 
    END UNITS ( identifier )?
  ;

port_clause
  : PORT LPAREN port_list RPAREN SEMI
    {#port_clause = #(#[PORTCLAUSE,"PortClause"],#port_clause);};
  



port_map_aspect : (PORT MAP LPAREN association_list RPAREN)?
    {#port_map_aspect = #(#[PORTMAPASPECT,"PortMapAspect"],#port_map_aspect);};
  

primary
  : (aggregate
  	//| (LPAREN expression RPAREN)=> paren_op // Not Needed because encapsulated in aggregate
    // | ((function_call)=> function_call     // Encompassed in name_expression
    //| (name APOSTROPHE)=> qualified_expression // Name Apostrophe
    //| (identifier) => name
    // | (identifier CHARACTER_LITERAL expression APOSTROPHE RPAREN)
    | literal
    | allocator) 
    
    {#primary = #(#[PRIMARY,"Primary"],#primary);};

  

paren_op : LPAREN expression RPAREN
  {#paren_op = #(#[PARENOP,"ParenOp"],#paren_op);};  

primary_unit : entity_declaration | configuration_declaration | package_declaration
    {#primary_unit = #(#[PRIMARYUNIT,"PrimaryUnit"],#primary_unit);};

procedural_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | variable_declaration
  | alias_declaration
  | attribute_declaration
  | attribute_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  ;

procedural_declarative_part
  : ( procedural_declarative_item )*
  ;

procedural_statement_part
  : ( sequential_statement )*
  ;

procedure_call
  : name ( LPAREN actual_parameter_part RPAREN )?
  ;

procedure_call_statement
  : ( label_colon )? procedure_call SEMI
  {#procedure_call_statement = #(#[PROCEDURECALLSTATEMENT,"ProcedureCallStatement"],#procedure_call_statement);};

process_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | attribute_declaration
  | attribute_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  ;

process_declarative_part : ( process_declarative_item )*
  {#process_declarative_part = #(#[PROCESSDECLARATIVEPART,"ProcessDeclarativePart"],#process_declarative_part);};

process_statement
  : process_head
    process_declarative_part
    BEGIN
    process_statement_part 
    END ( POSTPONED )? PROCESS ( identifier )? SEMI
  {#process_statement = #(#[PROCESSSTATEMENT,"ProcessStatement"],#process_statement);};  

process_head : label_colon_wrap postponedQ PROCESS ( LPAREN (sensitivity_list | ALL) RPAREN )? ( IS )?
 {#process_head = #(#[PROCESSHEAD,"ProcessHead"],#process_head);}; 

process_statement_part
  : ( sequential_statement )*
   {#process_statement_part = #(#[PROCESSSTATEMENTPART,"ProcessStatementPart"],#process_statement_part);}; 

qualified_expression
  : name APOSTROPHE ( (aggregate)=> aggregate | LPAREN expression RPAREN )
  ;

quantity_declaration
  : (free_quantity_declaration)=> free_quantity_declaration
  | (branch_quantity_declaration)=> branch_quantity_declaration
  | source_quantity_declaration
  ;

quantity_list
  : name ( COMMA name )*
  | OTHERS
  | ALL
  ;

quantity_specification
  : quantity_list COLON name
  ;

range
  : ((simple_expression direction simple_expression)=>simple_expression direction simple_expression | name)
       {#range = #(#[RANGE,"Range"],#range);}; 

range_constraint
  : RANGETOK range
  ;

record_nature_definition
  : RECORD ( nature_element_declaration )+
    END RECORD ( identifier )?
  ;

record_type_definition : RECORD record_element END RECORD ( identifier )?
        {#record_type_definition = #(#[RECORDTYPEDEFINITION,"RecordTypeDefinition"],#record_type_definition);};  

record_element : (element_declaration)+
        {#record_element = #(#[RECORDELEMENT,"RecordElement"],#record_element);};  





report_statement
  :  REPORT expression ( SEVERITY expression )? SEMI
   {#report_statement = #(#[REPORTSTATEMENT,"ReportStatement"],#report_statement);};  

return_statement
  :  RETURN ( expression )? SEMI
  {#return_statement = #(#[RETURNSTATEMENT,"ReturnStatement"],#return_statement);}; 

scalar_nature_definition
  : name ACROSS name THROUGH name REFERENCE
  ;

scalar_type_definition
  : (range_constraint UNITS)=> physical_type_definition
  | enumeration_type_definition
  | range_constraint
  ;

secondary_unit
  : (architecture_body | package_body)
    {#secondary_unit = #(#[SECONDARYUNIT,"SecondaryUnit"],#secondary_unit);};  


secondary_unit_declaration
  : identifier EQ physical_literal SEMI
  ;

selected_signal_assignment : WITH expression SELECT target LE opts selected_waveforms SEMI
   {#selected_signal_assignment = #(#[SELECTEDSIGNALASSIGNMENT,"SelectedSignalAssignment"],#selected_signal_assignment);};

selected_waveforms
  : selected_waveform_item ( COMMA selected_waveform_item )*
  {#selected_waveforms = #(#[SELECTEDWAVEFORMS,"selected_waveforms"],#selected_waveforms);};

selected_waveform_item : waveform WHEN choices
	{#selected_waveform_item = #(#[SELECTEDWAVEFORMITEM,"selected_waveform_item"],#selected_waveform_item);};

sensitivity_clause
  : ON sensitivity_list
  ;

sensitivity_list
  : name ( COMMA name )*
        {#sensitivity_list = #(#[SENSITIVITYLIST,"SensitivityList"],#sensitivity_list);};

sequence_of_statements : ( sequential_statement )*
      {#sequence_of_statements = #(#[SEQUENCEOFSTATEMENTS,"SequenceOfStatements"],#sequence_of_statements);};  
  
label_colon_wrap : ( label_colon )?
      {#label_colon_wrap = #(#[LABELCOLONWRAP,"label_colon_wrap"],#label_colon_wrap);};  

sequential_statement
  : ( label_colon_wrap ( WAIT)=> wait_statement
  | (ASSERT)=> assertion_statement
  | (REPORT)=> report_statement
  | (target LE)=> signal_assignment_statement
  | (target VARASGN)=> variable_assignment_statement
  | (label_colon_wrap IF)=> if_statement
  | (label_colon_wrap CASE)=> case_statement
  | ( label_colon_wrap (iteration_scheme )? LOOP)=> loop_statement
  | ( NEXT)=> next_statement
  | ( EXIT)=> exit_statement
  | ( RETURN)=> return_statement
  | ( NULLTOK SEMI)=> NULLTOK SEMI
  | ( BREAK)=> break_statement
  | procedure_call_statement)
  {#sequential_statement = #(#[SEQUENTIALSTATEMENT,"SequentialStatement"],#sequential_statement);};
  





signal_assignment_statement
  : target LE ( delay_mechanism )? waveform SEMI
  {#signal_assignment_statement = #(#[SIGNALASSIGNMENTSTATEMENT,"SignalAssignmentStatement"],#signal_assignment_statement);};

signal_declaration : SIGNAL identifier_list COLON subtype_indication signal_kind_or_var_assign SEMI
    {#signal_declaration = #(#[SIGNALDECLARATION,"SignalDeclaration"],#signal_declaration);};

signal_kind_or_var_assign : signal_kind var_assign;

signal_kind : ((REGISTER | BUS)?) {#signal_kind = #(#[SIGNALKIND,"SignalKind"],#signal_kind);};
    exception catch [RecognitionException ex] {signal_kind_exception(ex);}


var_assign : ( VARASGN expression )? {#var_assign = #(#[VARASSIGN,"VarAssign"],#var_assign);};
	exception catch [RecognitionException ex] {var_assign_exception(ex);}

signal_list
  : name ( COMMA name )*
  | OTHERS
  | ALL
  ;

signature
  : LBRACKET ( name ( COMMA name )* )? ( RETURN name )? RBRACKET
  ;

// NOTE that sign is applied to first operand only (LRM does not permit
// `a op -b' - use `a op (-b)' instead).
// (3.2.2004, e.f.)



source_aspect
  : SPECTRUM simple_expression COMMA simple_expression
  | NOISE simple_expression
  ;

source_quantity_declaration
  : QUANTITY identifier_list COLON subtype_indication source_aspect SEMI
  ;



subnature_declaration
  : SUBNATURE identifier IS subnature_indication SEMI
  ;

subnature_indication
  : name ( index_constraint )? 
    ( TOLERANCE expression ACROSS expression THROUGH )?
  ;

subprogram_body
  : subprogram_specification IS
    subprogram_declarative_part
    BEGIN
    subprogram_statement_part
    END ( subprogram_kind )? ( designator )? SEMI
    {#subprogram_body = #(#[SUBPROGRAMDBODY,"subprogram_body"],#subprogram_body);};

subprogram_declaration
  : subprogram_specification SEMI
  {#subprogram_declaration = #(#[SUBPROGRAMDECLARATION,"SubprogramDeclaration"],#subprogram_declaration);};

subprogram_declarative_item
  : (subprogram_declaration)=> subprogram_declaration
  | subprogram_body
  | type_declaration
  | subtype_declaration
  | constant_declaration
  | variable_declaration
  | file_declaration
  | alias_declaration
  | attribute_declaration
  | attribute_specification
  | use_clause
  | group_template_declaration
  | group_declaration
  ;

subprogram_declarative_part
  : ( subprogram_declarative_item )*
  {#subprogram_declarative_part = #(#[SUBPROGRAMDECLARATIVEPART,"SubprogramDeclarativePart"],#subprogram_declarative_part);};

subprogram_kind
  : PROCEDURE
  | FUNCTION
  ; 

subprogram_specification
  : procedure_declaration | function_declaration;
 
procedure_declaration : PROCEDURE designator function_parameter_list
	{#procedure_declaration = #(#[PROCEDUREDECLARATION,"ProcedureDeclaration"],#procedure_declaration);};

function_declaration :  function_pure FUNCTION designator function_parameter_list RETURN name
        {#function_declaration = #(#[FUNCTIONDECLARATION,"FunctionDeclaration"],#function_declaration);};

function_parameter_list : ( LPAREN formal_parameter_list RPAREN )?
	{#function_parameter_list = #(#[FUNCTIONPARAMETERLIST,"function_parameter_list"],#function_parameter_list);};
	

function_pure : ( PURE | IMPURE )?
	{#function_pure = #(#[FUNCTIONPURE,"FunctionPure"],#function_pure);};


subprogram_statement_part
  : ( sequential_statement )*
 {#subprogram_statement_part = #(#[SUBPROGRAMSTATEMENTPART,"SubprogramStatementPart"],#subprogram_statement_part);};

subtype_declaration: SUBTYPE identifier IS subtype_indication SEMI
        {#subtype_declaration = #(#[SUBTYPEDECLARATION,"SubtypeDeclaration"],#subtype_declaration);};

// VHDLAMS 1076.1-1999 declares first name as optional. Here, second name
// is made optional to prevent antlr nondeterminism.
// (9.2.2004, e.f.)
subtype_indication
  : subtype_first_name ( name )? ( constraint )? ( (TOLERANCE)=> tolerance_aspect )?
{#subtype_indication = #(#[SUBTYPEINDICATION,"SubtypeIndication"],#subtype_indication);}; 
 
 subtype_first_name : (name | (LPAREN name RPAREN))
	{#subtype_first_name = #(#[SUBTYPEFIRSTNAME,"SubtypeFirstName"],#subtype_first_name);}; 

suffix
  : identifier
  | CHARACTER_LITERAL
  | STRING_LITERAL
  | ALL
  ;

target
  : name | aggregate
  {#target = #(#[TARGET,"Target"],#target);};



terminal_aspect
  : name ( TO name )?
  ;

terminal_declaration
  : TERMINAL identifier_list COLON subnature_indication SEMI
  ;

through_aspect
  : identifier_list ( tolerance_aspect )? ( VARASGN expression )? THROUGH
  ;

timeout_clause
  : FOR expression
  ;

tolerance_aspect
  : TOLERANCE expression
  ;

type_declaration : TYPE identifier ( IS type_definition )? SEMI
  {#type_declaration= #(#[TYPEDECLARATION,"TypeDeclaration"],#type_declaration);};
   

type_definition
  : scalar_type_definition
  | composite_type_definition
  | access_type_definition
  | file_type_definition
  | protected_type_definition
  ;

unconstrained_array_definition : ARRAY LPAREN index_subtype_definition ( COMMA index_subtype_definition )* RPAREN OF subtype_indication
  {#unconstrained_array_definition= #(#[UNCONSTRAINEDARRAYDEFINITION,"unconstrained_array_definition"],#unconstrained_array_definition);};

unconstrained_nature_definition
  : ARRAY LPAREN index_subtype_definition ( COMMA index_subtype_definition )*
    RPAREN OF subnature_indication
  ;

// NOTE that name is used here, so need to check if result of name is 
// a seleceted_name (i.e. prefix DOT suffix).
// (30.3.2004, e.f.)
  
use_clause :   
   (USE | CONTEXT) name ( COMMA name )* SEMI {#use_clause = #(#[USECLAUSE,"Use_Clause"],#use_clause);};
  


variable_assignment_statement
  : target VARASGN expression SEMI
    {#variable_assignment_statement = #(#[VARIABLEASSIGNMENTSTATEMENT,"VariableAssignmentStatement"],#variable_assignment_statement);};

variable_declaration : ( SHARED )? VARIABLE identifier_list COLON subtype_indication var_assign SEMI
      {#variable_declaration = #(#[VARIABLEDECLARATION,"VariableDeclaration"],#variable_declaration);};

wait_statement :  WAIT ( sensitivity_clause )? ( condition_clause )? ( timeout_clause )? SEMI
        {#wait_statement = #(#[WAITSTATEMENT,"WaitStatement"],#wait_statement);};

waveform
  : waveform_element ( COMMA waveform_element )*
  | UNAFFECTED
  ;

waveform_element
  : expression ( AFTER expression )?
  ;

//--------------------------------------------------------------------------
class VhdlLexer extends Lexer;

options {
  // two token lookahead to handle 2-char tokens "**", "==", ...
  k = 3;
  caseSensitive=false;
  caseSensitiveLiterals=false;
  charVocabulary = '\3'..'\377';
}

tokens {
  ARCHITECTURE="architecture";
  ACCESS="access";
  ACROSS="across";
  OPEN="open";
  ALIAS="alias";
  IS="is";
  RETURN="return";
  NEW="new";
  OF="of";
  BEGIN="begin";
  END="end";
  BLOCK="block";
  POSTPONED="postponed";
  PROCESS="process";
  ASSERT="assert";
  ARRAY="array";
  REPORT="report";
  SEVERITY="severity";
  ATTRIBUTE="attribute";
  RANGETOK="range";
  USE="use";
  FOR="for";
  SHARED="shared";
  VARIABLE="variable";
  GROUP="group";
  QUANTITY="quantity";
  BREAK="break";
  WHEN="when";
  CASE="case";
  OTHERS="others";
  COMPONENT="component";
  UNTIL="until";
  ELSE="else";
  CONFIGURATION="configuration";
  CONSTANT="constant";
  TRANSPORT="transport";
  REJECT="reject";
  INERTIAL="inertial";
  TO="to";
  DOWNTO="downto";
  DISCONNECT="disconnect";
  AFTER="after";
  ENTITY="entity";
  PROCEDURE="procedure";
  FUNCTION="function";
  PACKAGE="package";
  TYPE="type";
  SUBTYPE="subtype";
  SIGNAL="signal";
  LABEL="label";
  LITERAL="literal";
  UNITS="units";
  FILE="file";
  NATURE="nature";
  SUBNATURE="subnature";
  TERMINAL="terminal";
  ALL="all";
  EXIT="exit";
  ABS="abs";
  NOT="not";
  GENERATE="generate";
  IF="if";
  GENERIC="generic";
  MAP="map";
  THEN="then";
  ELSIF="elsif";
  IN="in";
  OUT="out";
  BUS="bus";
  WHILE="while";
  LIBRARY="library";
  NULLTOK="null";
  AND="and";
  OR="or";
  NAND="nand";
  NOR="nor";
  XOR="xor";
  XNOR="xnor";
  LOOP="loop";
  INOUT="inout";
  BUFFER="buffer";
  LINKAGE="linkage";
  MOD="mod";
  REM="rem";
  NEXT="next";
  GUARDED="guarded";
  BODY="body";
  PORT="port";
  PROTECTED="protected";
  RECORD="record";
  THROUGH="through";
  WITH="with";
  SELECT="select";
  ON="on";
  WAIT="wait";
  SLL="sll";
  SRL="srl";
  SLA="sla";
  SRA="sra";
  ROL="rol";
  ROR="ror";
  REGISTER="register";
  PROCEDURAL="procedural";
  SPECTRUM="spectrum";
  NOISE="noise";
  TOLERANCE="tolerance";
  PURE="pure";
  IMPURE="impure";
  UNAFFECTED="unaffected";
  REFERENCE="reference";
  CONTEXT="context";

}


WS_ :
        (  ' '
         | '\t'
         | '\f' );
              
    
NEWLINE : ((  "\r\n"  // Evil DOS
            | '\r'    // Macintosh
            | '\n'    // Unix (the right way)
           )
            { newline(); });

// comment without NEWLINE
// (2.2.2004, e.f.)


COMMENT
  : "--" ( ~'\n' )*;
  

DOUBLESTAR
  : "**"
  ;
ASSIGN
  : "=="
  ;
LE
  : "<="
  ;
GE
  : ">="
  ;
ARROW
  : "=>"
  ;

NEQ
  : "/="
  ;
VARASGN
  : ":="
  ;
BOX
  : "<>"
  ;

DBLQUOTE
  : '\"'
  ;
SEMI
  : ';'
  ;
COMMA
  : ','
  ;
AMPERSAND
  : '&'
  ;
APOSTROPHE
  : '\''
    (
     (( 'a'..'z' | '0'..'9' | '&' | '\'' | '?' 
        | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|' | '$'
        | ' ' | OTHER_SPECIAL_CHARACTER | '\\'
        | '#' | '[' | ']' | '_' | '*' | '"' ) '\'')=>
      ( 'a'..'z' | '0'..'9' | '&' | '\'' | '(' | ')' | '?'
        | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|' | '$'
        | ' ' | OTHER_SPECIAL_CHARACTER | '\\'
        | '#' | '[' | ']' | '_' | '*' | '"' ) '\''
      {$setType(CHARACTER_LITERAL);}
    )?
  ;
LPAREN
  : '('
  ;
RPAREN
  : ')'
  ;
LBRACKET
  : '['
  ;
RBRACKET
  : ']'
  ;
COLON
  : ':'
  ;
MUL
  : '*'
  ;
DIV
  : '/'
  ;
PLUS
  : '+'
  ;
MINUS
  : '-'
  ;
LOWERTHAN
  : '<'
  ;
GREATERTHAN
  : '>'
  ;
EQ
  : '='
  ;
BAR
  : '|'
  ;

DOT
  : '.'
  ;
BACKSLASH
  : '\\'
  ;

BASIC_IDENTIFIER
  : 'a'..'z' ( 'a'..'z' | '0'..'9' | '_' )*
  ;

EXTENDED_IDENTIFIER
  : '\\' ( 'a'..'z' | '0'..'9' | '&' | '\'' | '(' | ')'
    | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|'
    | ' ' | OTHER_SPECIAL_CHARACTER
    | '#' | '[' | ']' | '_' )+ '\\'
  ;



BIT_STRING_LITERAL
  :  ( 'b' | 'o' | 'x' ) '\"' (BASED_INTEGER | '\'' | '(' | ')' | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|') '\"'
  ;

//BIT_STRING_LITERAL
//  : (SIZE)? ( 'b' | 'o' | 'x' | "sb" | "so" | "sx" ) '\"' (BASED_INTEGER | 'u' | 'x' | 'z' | '\'' | '(' | ')' | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|') '\"'
//  ;

/*
STRING_LITERAL
  : '\"' ( 'a'..'z' | '0'..'9' | '&' | '\'' | '(' | ')'
    | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|'
    | ' ' | OTHER_SPECIAL_CHARACTER | '\\' | "\"\"" | '*' 
    | '#' | '[' | ']' | '_' )* '\"'
  ;
*/  
  
STRING_LITERAL
  : '\"' ( ~'\"' )* '\"'
  ;

// NOTE that BASED_LITERAL is implicitely defined by referencing in the
// parser grammar.
// (2.2.2004, e.f.)
DECIMAL_LITERAL : INTEGER (
      ( ( '.' INTEGER )? ( EXPONENT )? )
    | ( '#' BASED_INTEGER ( '.' BASED_INTEGER )? '#' ( EXPONENT )? ) {$setType(BASED_LITERAL);}
    | ('s' |'u')? ( 'b' | 'o' | 'x' ) '\"' (BASED_INTEGER | '\'' | '(' | ')' | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|') '\"'
    );

  
protected
OTHER_SPECIAL_CHARACTER : '\u00a0'..'\u00ff' ;
  
protected
ALL_CHARACTER : '\u0000'..'\u00ff' ;

protected
INTEGER
  : '0'..'9' ( '_' | '0'..'9' )*
  ;

protected
EXPONENT
  : 'e' ( '+' | '-' )? INTEGER
  ;

protected
BASED_INTEGER
  : ( '0'..'9' | 'a'..'f' ) ( '_' | '0'..'9' | 'a'..'f' )*
  ;
