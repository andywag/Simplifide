
header
{
    package com.simplifide.base.verilog.parse.grammar.system;
    import com.simplifide.base.sourcefile.antlr.tok.*;

}

class SystemVerilogParser extends VerilogParser;
options
{
    k = 2;                        // two token lookahead
    buildAST = true;
    exportVocab = SystemVerilog;
}


tokens {

        IMPORTCLAUSE;
        // System Verilog Additions
        SYSVARDEC; //
        SYSVARTYPE;
        SIGNTYPE;

   
             

        IOPORTDEC;
        IODEC;
        SYSNODEC;
 STRUCTDEF;  


        PACKTYPE;
        STRUCTTYPE;
       

        TOPDEC;

        CONSTDEC;
        INPUTOUTPUTDEC;
        SYSDEC;
       
      
        

        DECTYPE;
        DECNOTYPE;

        PACKAGE;

        IMPORTSTATE;
        VARIDENT;
        FIRSTIDENT;
        LOCALIDENT;
        
        // Module Tokens
       
        MODULEDECLARATION;
       
       	// Instantiation Tokens
        MODULEINSTANTIATION;
		MODULEINSTANCE;
		NAMEOFINSTANCE;
		LISTOFPORTORDERED;
		LISTOFPORTNAMED;
		NAMEPORTSTAR;
		NAMEPORTEXPR;
		NAMEPORTIDENT;
		
		PARAMETERVALUEASSIGNMENT;
		ORDEREDPARAMETERLIST;
		NAMEDPARAMETERLIST;
		ORDEREDPARAMETERASSIGNMENT;
		NAMEDPARAMETERASSIGNMENT;
       
        // Ports
        PARAMETERPORTLIST;
        ANSIPORTLIST;
        NOANSIPORTLIST;
        // Type Tokens
        DATATYPEORNULL;
        PARAMETERDIMENSIONNODE;
        TYPEDECINTEGERVECTOR;
		TYPEDECINTEGERATOM;
		TYPEDECIDENTIFIER;
		TYPEDECNONINTEGER;
		TYPEDECIO;
		TYPEDECVIRTUAL;
		
		SIGNING;
		PACKEDDIMENSION;
		PORTTYPENET;
		PORTTYPETRIREG;
		PORTTYPEEMPTY;
		RANGEQ;
		
		TYPEDEF;    // TypeDef
		TYPEDEFDATATYPE;
		LIFETIME;

		VARIABLEDECLARATION;
		LISTOFVARIABLEIDENTIFIERS;
		VARIABLEIDENT;
		IODECLARATIONTOP;
		IODECLARATION;
		
		
		NETDECLARATION;
		LISTOFNETDECLASSIGNMENTS;
		NETDECLARATIONASSIGNMENT;
		
		
		
		PARAMETERDECLARATION;
		PARAMETERDECLARATIONSEMI;
		
		LISTOFPARAMASSIGNMENTS;
		PARAMASSIGNMENT;
		
		
		ANSIPORTLISTQ;
		
		GENERATEIF;
		GENERATECASE;
		GENERATEFOR;
		GENERATEMODULEBLOCK;
		GENERATEMODULEBLOCKIDENT;
		GENERATEIDENTIFIER;
		GENERATEFORHEAD;
		
		COLONIDENTIFIERQ;
		GENVARASSIGNMENT;
		GENVARDECLASSIGNMENT;

		GENERATEMODULECASEITEMNORMAL;
		GENERATEMODULECASEITEMDEFAULT;

		PACKEDQ; 
		TAGGEDQ;
		
		TASKFUNCTIONPREFIX;
		TASKFUNCTIONPORTLIST;
		TASKBODYDECLARATION;
		TASKHEADDECLARATION;
		FUNCTIONBODYDECLARATION;
		TFPORTITEM;
		TFPORTLIST;
		TFPORTDIRECTION;
		
		FUNCTIONDATATYPE;
		
		STRUCTUNION;
		STRUCTUNIONMEMBERLIST;
		STRUCTUNIONMEMBER;
		
		ENUMTYPE;
		ENUMDECTYPE;
		ENUMDECTYPEIDENT;
		ENUMLIST;
		ENUMNAMEDECLARATION;

		ASSIGNEXPRESSIONQ;
		EXTERNQ;
		CONSTQ;
		PARBLOCK;
		CLASSDECLARATION;
		CLASSBODY;
		CLASSBODYLIST;
		CLASSHEAD;
		CLASSMETHOD;
		CLASSPROPERTY;
		CLASSPROPERTYLIST;
		CLASSMETHODNORMAL;
		CLASSMETHODPROTO;
		CLASSMETHODQUALIFIERLIST;
		
		CLASSEXTENDS;
		CLASSEXTENDSARGS;
		
		INTERFACEDECLARATION;
		INTERFACEDECLARATIONNORMAL;
		INTERFACEDECLARATIONEXTERNAL;
		INTERFACEDECLARATIONNOANSIHEADER;
		INTERFACEHEADERDOTSTAR;
		INTERFACEHEADERNORMAL;
		INTERFACEBODY;
		
		PROGRAMDECLARATION;
		PROGRAMDECLARATIONNORMAL;
		PROGRAMDECLARATIONEXTERNAL;
		PROGRAMDECLARATIONNOANSIHEADER;
		PROGRAMBODY;
		
		PACKAGEDECLARATION;
		PACKAGEBODY;
		
		MODPORTDECLARATION;
		MODPORTITEM;
		MODPORTDECLARATIONITEM;
		MODPORTDECLARATIONPREFIX;
		MODPORTHIER;
		MODPORTDOT;
		TIMEUNITSDECLARATIONQ;
		
		SUBROUTINECALLSTATEMENT;
		LOOPSTATEMENT;
		CAST;
		VARIABLEDIMENSION;
		DPIIMPORTEXPORT;
		FUNCTIONPROTOTYPE;
		TASKPROTOTYPE;
		
		FUNCTIONHEADDECLARATIONRETURN;
		FUNCTIONHEADDECLARATIONNORETURN;
		
		COVERGROUPDECLARATION;
		PROPERTYDECLARATION;
		VIRTUALQ;
		SEQUENCEDECLARATION;
		CLOCKINGDECLARATION;
		
		CONCURRENTASSERTIONITEM;
		ASSERTPROPERTYSTATEMENT;
		ASSUMEPROPERTYSTATEMENT;
		COVERPROPERTYSTATEMENT;
		EXPECTPROPERTYSTATEMENT;
		
		STATEMENTNAMEPREFIX;
		FUNCTIONBODYDECLARATIONLIST;
		TASKBODYDECLARATIONLIST;
		
		CONSTRAINTDECLARATION;
		CONSTRAINTSET;
		CONSTRAINTEXPRESSIONTRIGGER;
		CONSTRAINTEXPRESSIONIF;
		CONSTRAINTEXPRESSIONFOR;
		STATICQ;
		
		ANSIPORTHEADERQ;
		ANSIPORTDECLARATION;
		ANSIPORTDECLARATIONNORMAL;
		ANSIPORTDECLARATIONDOT;
		ANSIPORTHEADERNORMAL;
		ANSIPORTHEADERINTERFACE;
		
		PORTEXPRESSION;
		PORTREFERENCE;
		PORTDIRECTIONQ;
		
		ASSOCIATIVEDIMENSIONQ;
		CLASSARRAYNEW;
		CLASSNEW;
		DYNAMICARRAYNEW;
		PACKAGEIMPORTDECLARATION;
		PACKAGEIMPORTITEM;
		
		STATEMENT;
}

source_text : ((include_directive) => include_directive | design_unit)* EOF
   {#source_text = #(#[ROOT,"Root"],#source_text);};

design_unit : (("extern")? ("module" | "macromodule"))  => module_declaration    
			| (("extern")? "primitive") => udp_declaration
			| (("extern")? "interface") =>interface_declaration
			| (("extern")?  "program" lifetime program_identifier) => program_declaration
			| package_declaration 
			| package_item 
			//| bind_directive
    {#design_unit = #(#[DESIGNUNIT,"DesignUnit"],#design_unit);};

//////// BIND Directive
bind_directive : "bind" hierarchical_identifier constant_select bind_instantiation ;
bind_instantiation : module_instantiation; // Same as Program or Interface


// MODULE DECLARATION
//module_head : ("module" | "macromodule") lifetime (IDENTIFIER | DEFINE) parameter_port_list port_list SEMI
//	{#module_head = #(#[MODULEHEAD, "Module_Head"], #module_head);};

externQ : ("extern")?
	{#externQ = #(#[EXTERNQ, "externQ"], #externQ);};

module_head : externQ ("module" | "macromodule") lifetime (identifier) parameter_port_list 
	((interface_header_dot_star) => interface_header_dot_star | port_list) SEMI
	{#module_head = #(#[MODULEHEAD, "Module_Head"], #module_head);};

module_declaration : module_head module_body "endmodule" (COLON identifier)?
	{#module_declaration = #(#[MODULEDECLARATION, "module_declaration"], #module_declaration);};

module_body : (module_item)*
	{#module_body = #(#[MODULEBODY, "Module_Body"], #module_body);};

// MODULE ITEMS

concurrent_assertion_item_pred : ((block_identifier COLON)? ("cover" | "assume" | "assert") "property");

module_common_item : ("bind") => bind_directive
	| (concurrent_assertion_item_pred) => concurrent_assertion_item
	| module_or_generate_item_declaration
	//| interface_instantiation -- Included in module_instantiation
	//| program_instantiation   -- Included in module_instantiation
	| event_control
	| continuous_assign
	| net_alias
	| ("initial") => initial_construct
	| final_construct
	| always_construct;
	
module_item : /* port_declaration | */ non_port_module_item
	{#module_item = #(#[MODULEITEM, "Module_Item"], #module_item);};

port_declaration : io_declaration_top;	

module_or_generate_item : parameter_override  
	| (gate_type) => gate_instantiation 
	//| udp_instantiation included in module_instantiation
	| (module_instantiation) => module_instantiation
	| module_common_item;

// TODO genvar declaration should be contained in other declaration but needs to be checked
module_or_generate_item_declaration : (package_or_generate_item_declaration) => package_or_generate_item_declaration
	//| genvar_declaration
	| include_directive 
	| (clocking_declaration) => clocking_declaration
	| "default" "clocking" clocking_identifier SEMI;
	
non_port_module_item : generated_module_instantiation
	| module_or_generate_item
	| specify_block
	| parameter_declaration_semi //| specparam_declaration // Included in parameter declaration
	| (("extern")? "program") => program_declaration
	| module_declaration
	| timeunits_declaration;


////// INTERFACE ITEMS
interface_item : /* port_declaration | */ non_port_interface_item;

non_port_interface_item :  (("default")? "clocking") => clocking_declaration
	| (("extern")? "program") => program_declaration
	| (module_instantiation) => module_instantiation
	| generated_module_instantiation
	| parameter_declaration_semi //specparam_declaration
	| (interface_or_generate_item) => interface_or_generate_item
	| interface_declaration
	| timeunits_declaration;

interface_or_generate_item : module_common_item
	| modport_declaration
	| extern_tf_declaration;
	
extern_tf_declaration : "extern" ("forkjoin")? method_prototype ;

////// PROGRAM ITEMS
	
program_item : /*variable_declaration |*/  non_port_program_item;

non_port_program_item :continuous_assign
	| (concurrent_assertion_item_pred) => concurrent_assertion_item
	| module_or_generate_item_declaration
	| parameter_declaration_semi //specparam_declaration
	| initial_construct
	| timeunits_declaration;

/*
module_item :  (io_declaration_top)| non_port_module_item
	{#module_item = #(#[MODULEITEM, "Module_Item"], #module_item);};
	  
	  
non_port_module_item : ((module_instantiation) => module_instantiation | 
						(task_enable) => task_enable |
						(net_alias) => net_alias |
						(typedef_declaration |
	    				 data_declaration |
	    				 net_declaration |
	    				 gate_declaration |
	    				 parameter_declaration_semi |
            			 parameter_override |
            			 continuous_assign |
            			 clocking_declaration |
            			 specify_block |
            			 initial_statement |
            			 always_statement |
            			 task_declaration |
            			 function_declaration |
            			 context_item | 
            			 generate_item));
            			*/ 


port_list : (LPAREN ansi_port_list RPAREN)?
	{#port_list = #(#[PORTLIST, "port_list"], #port_list);};

//noansi_port_list : port (COMMA port)*
//	{#noansi_port_list = #(#[NOANSIPORTLIST, "noansi_port_list"], #noansi_port_list);}; 
	
//port : port_expression;// | DOT port_identifier LPAREN (port_expression)? RPAREN;
port_expression : /*port_reference |*/ LCURLY port_reference ( COMMA port_reference )* RCURLY	
	{#port_expression = #(#[PORTEXPRESSION, "port_expression"], #port_expression);};

port_reference : port_identifier constant_select
	{#port_reference = #(#[PORTREFERENCE, "port_reference"], #port_reference);};

		
ansi_port_list : (ansi_port_declaration_or_port_expression)? (COMMA ansi_port_declaration_or_port_expression)*
	{#ansi_port_list = #(#[ANSIPORTLIST, "ansi_port_list"], #ansi_port_list);};

ansi_port_declaration_or_port_expression : ansi_port_declaration_top | port_expression;

ansi_port_declaration_top : (identifier LBRACK) => ansi_port_declaration_normal | ansi_port_declaration;			
			
ansi_port_declaration : ansi_port_headerQ (ansi_port_declaration_normal | ansi_port_declaration_dot)
	{#ansi_port_declaration = #(#[ANSIPORTDECLARATION, "ansi_port_declaration"], #ansi_port_declaration);};


ansi_port_headerQ : (options{greedy=true;} : ansi_port_header)?
	{#ansi_port_headerQ = #(#[ANSIPORTHEADERQ, "ansi_port_headerQ"], #ansi_port_headerQ);};

						
ansi_port_declaration_normal :  port_identifier packed_dimension assign_expressionQ
	{#ansi_port_declaration_normal = #(#[ANSIPORTDECLARATIONNORMAL, "ansi_port_declaration_normal"], #ansi_port_declaration_normal);};

ansi_port_declaration_dot    :  DOT port_identifier LPAREN (expression )? RPAREN
	{#ansi_port_declaration_dot = #(#[ANSIPORTDECLARATIONDOT, "ansi_port_declaration_dot"], #ansi_port_declaration_dot);};

		
ansi_port_header : ("interface") => ansi_port_header_interface | ansi_port_header_normal;
				 		
ansi_port_header_normal    : port_directionQ data_type_or_implicit 
	{#ansi_port_header_normal = #(#[ANSIPORTHEADERNORMAL, "ansi_port_header_normal"], #ansi_port_header_normal);};

ansi_port_header_interface : "interface" (options{greedy=true;} : DOT modport_identifier)?
	{#ansi_port_header_interface = #(#[ANSIPORTHEADERINTERFACE, "ansi_port_header_interface"], #ansi_port_header_interface);};

port_directionQ : (options{greedy=true;} : port_direction)?
		{#port_directionQ = #(#[PORTDIRECTIONQ, "port_directionQ"], #port_directionQ);};
	


/*
net_port_header : (port_direction)? port_type;
variable_port_header : (port_direction)? data_type;
interface_port_header : interface_identifier ( DOT modport_identifier)?
						| "interface" ( DOT modport_identifier)?;
*/
	
non_generic_port_declaration : io_declaration;

	
io_declaration_top : non_generic_port_declaration SEMI
	{#io_declaration_top = #(#[IODECLARATIONTOP, "io_declaration_top"], #io_declaration_top);};


io_declaration : ("input" | "inout" | "output" | "ref") port_type list_of_variable_identifiers
	{#io_declaration = #(#[IODECLARATION, "io_declaration"], #io_declaration);}; 

io_type : ("input" | "inout" | "output");
port_direction : ("input" | "inout" | "output" | ("const")? "ref"); 	


// Ports
parameter_port_list : (POUND LPAREN ( parameter_port_declaration) 
	( COMMA parameter_port_declaration)*  RPAREN)?
	{#parameter_port_list = #(#[PARAMETERPORTLIST, "parameter_port_list"], #parameter_port_list);};
	
// Module Instances

module_instantiation : identifier parameter_value_assignment module_instance ( COMMA module_instance )* SEMI
	{#module_instantiation = #(#[MODULEINSTANTIATION, "module_instantiation"], #module_instantiation);};
	
// Instance Parameter Section
	
parameter_value_assignment : (POUND LPAREN list_of_parameter_assignments RPAREN)?
	{#parameter_value_assignment = #(#[PARAMETERVALUEASSIGNMENT, "parameter_value_assignment"], #parameter_value_assignment);};

list_of_parameter_assignments : ordered_parameter_list | named_parameter_list;
ordered_parameter_list : ordered_parameter_assignment ( COMMA ordered_parameter_assignment)*
	{#ordered_parameter_list = #(#[ORDEREDPARAMETERLIST, "ordered_parameter_list"], #ordered_parameter_list);};

named_parameter_list   :  named_parameter_assignment  ( COMMA named_parameter_assignment )*
	{#named_parameter_list = #(#[NAMEDPARAMETERLIST, "named_parameter_list"], #named_parameter_list);};
	
ordered_parameter_assignment : ((data_type) => data_type | expression) 
	{#ordered_parameter_assignment = #(#[ORDEREDPARAMETERASSIGNMENT, "ordered_parameter_assignment"], #ordered_parameter_assignment);};

named_parameter_assignment : DOT identifier LPAREN (expression) RPAREN
	{#named_parameter_assignment = #(#[NAMEDPARAMETERASSIGNMENT, "named_parameter_assignment"], #named_parameter_assignment);};




	// Module Instance Declaration
module_instance : name_of_instance LPAREN list_of_port_connections RPAREN
	{#module_instance = #(#[MODULEINSTANCE, "module_instance"], #module_instance);};
name_of_instance : identifier (range)*
	{#name_of_instance = #(#[NAMEOFINSTANCE, "name_of_instance"], #name_of_instance);};
	// Instance Port List
list_of_port_connections : (list_of_port_ordered | list_of_port_named)?; 

list_of_port_ordered : expression (COMMA expression)*
	{#list_of_port_ordered = #(#[LISTOFPORTORDERED, "list_of_port_ordered"], #list_of_port_ordered);};
list_of_port_named :  named_port (COMMA named_port)*
	{#list_of_port_named = #(#[LISTOFPORTNAMED, "list_of_port_named"], #list_of_port_named);};

	// Named Port
named_port : (named_port_star | named_port_expr); // | named_port_ident);

named_port_star  : DOT STAR
	{#named_port_star = #(#[NAMEPORTSTAR, "named_port_star"], #named_port_star);};
named_port_expr  : DOT identifier LPAREN (expression)? RPAREN
	{#named_port_expr = #(#[NAMEPORTEXPR, "named_port_expr"], #named_port_expr);};
	
	/*
named_port_ident : DOT IDENTIFIER
 	{#named_port_ident = #(#[NAMEPORTIDENT, "named_port_ident"], #named_port_ident);};
*/


//

// Parameter Declarations

//local_parameter_declaration ::= localparam [ signing ] { packed_dimension } [ range ] list_of_param_assignments ;
//								| localparam data_type list_of_param_assignments ;

//parameter_declaration ::= parameter [ signing ] { packed_dimension } [ RANGEORTYPE ] list_of_param_assignments
//						| parameter data_type list_of_param_assignments
//						| parameter type list_of_type_assignments



parameter_port_declaration : ("parameter" | "localparam" | "specparam")? data_type_or_implicit param_assignment
		//| (data_type)? list_of_param_assignments
		| "type" list_of_type_assignments;

list_of_type_assignments :type_assignment (options{greedy=true;} : COMMA type_assignment )*;

type_assignment : type_identifier ASSIGN data_type;

parameter_declaration_semi : parameter_declaration SEMI
	{#parameter_declaration_semi = #(#[PARAMETERDECLARATIONSEMI, "parameter_declaration_semi"], #parameter_declaration_semi);};

parameter_type_ident : ("parameter" | "localparam" | "specparam");

parameter_declaration : parameter_type_ident (parameter_type_dec | parameter_signal_dec)
	{#parameter_declaration = #(#[PARAMETERDECLARATION, "parameter_declaration"], #parameter_declaration);};

parameter_type_dec   : "type" list_of_type_assignments;
parameter_signal_dec : data_type_or_implicit list_of_param_assignments;

list_of_param_assignments : param_assignment (options{greedy=true;}: COMMA param_assignment)*
	{#list_of_param_assignments = #(#[LISTOFPARAMASSIGNMENTS, "list_of_param_assignments"], #list_of_param_assignments);};

param_assignment : name (ASSIGN expression)?
	{#param_assignment = #(#[PARAMASSIGNMENT, "param_assignment"], #param_assignment);};



// specparam_declaration ::= specparam [ range ] list_of_specparam_assignments ;


data_type_or_implicit : (signing_item | range) => parameter_dimension_node | data_type_or_null  ;

data_type_or_null : (options{greedy=true;} : data_type )?
	{#data_type_or_null = #(#[DATATYPEORNULL, "data_type_or_null"], #data_type_or_null);};
	
parameter_dimension_node : signing packed_dimension
	{#parameter_dimension_node = #(#[PARAMETERDIMENSIONNODE, "parameter_dimension_node"], #parameter_dimension_node);};

// Type Declarations 
data_type : data_type_wo_identifier 
	| type_dec_identifier;

data_type_wo_identifier : type_dec_virtual
	| type_dec_integer_vector
	| type_dec_integer_atom
	| type_dec_non_integer
	| type_dec_io
	| struct_union
	| enum_type;

typedef_declaration : "typedef" typedef_datatype
 	{#typedef_declaration = #(#[TYPEDEF, "TypeDef"], #typedef_declaration);};
 
typedef_datatype : typedef_dec identifier packed_dimension SEMI
	{#typedef_datatype = #(#[TYPEDEFDATATYPE, "TypeDefDataType"], #typedef_datatype);};

typedef_dec : (name_dot_colon_pound) => name_dot_colon_pound | data_type;

type_dec_integer_vector : integer_vector_type ("logic")? signing packed_dimension
	{#type_dec_integer_vector = #([TYPEDECINTEGERVECTOR, "type_dec_integer_vector"], #type_dec_integer_vector);};
	
type_dec_integer_atom   : integer_atom_type signing
	{#type_dec_integer_atom = #([TYPEDECINTEGERATOM, "type_dec_integer_atom"], #type_dec_integer_atom);};
	
type_dec_identifier     : name_dot_colon_only signing packed_dimension parameter_value_assignment
	{#type_dec_identifier = #([TYPEDECIDENTIFIER, "type_dec_identifier"], #type_dec_identifier);};
	
type_dec_non_integer    : ("time" | "shortreal" | "real" | "realtime" | "class")
	{#type_dec_non_integer = #([TYPEDECNONINTEGER, "type_dec_non_integer"], #type_dec_non_integer);};
	
type_dec_virtual : "virtual" ("interface")? name parameter_value_assignment
	{#type_dec_virtual = #([TYPEDECVIRTUAL, "type_dec_virtual"], #type_dec_virtual);};

type_dec_io : (port_direction (type_dec_integer_vector 
							| type_dec_integer_atom 
							| type_dec_non_integer
							| (name_dot_colon_pound signing packed_dimension identifier) => type_dec_identifier 
							| port_type_empty))
	{#type_dec_io = #([TYPEDECIO, "type_dec_io"], #type_dec_io);};						

integer_type : integer_vector_type | integer_atom_type;

// Disabled byte and itn due to conflicts with verilog
//integer_atom_type : "byte" | "shortint" | "int" | "longint" | "integer";
integer_atom_type :  "shortint" | "longint" | "integer" | "int";
integer_vector_type : "logic" | "reg" | net_type_or_trireg ;
net_type : ("supply0" | "supply1" | "tri" | "triand" | "trior" | "tri0" | "tri1" | "wire" | "wand" | "wor");
net_type_or_trireg : net_type | "trireg" | "genvar";


// Structures
struct_union : ("struct" | "union") taggedq packedq signing LCURLY struct_union_member_list RCURLY packed_dimension
	{#struct_union = #([STRUCTUNION, "struct_union"], #struct_union);};

struct_union_member_list : (struct_union_member)+
	{#struct_union_member_list = #([STRUCTUNIONMEMBERLIST, "struct_union_member_list"], #struct_union_member_list);};

struct_union_member : data_type_or_void list_of_variable_identifiers SEMI
	{#struct_union_member = #([STRUCTUNIONMEMBER, "struct_union_member"], #struct_union_member);};

data_type_or_void : data_type | "void";

// Enums
enum_type : "enum" enum_dec_type LCURLY enum_list RCURLY
	{#enum_type = #([ENUMTYPE, "enum_type"], #enum_type);};

enum_dec_type : enum_dec_type_ident signing packed_dimension
	{#enum_dec_type = #([ENUMDECTYPE, "enum_dec_type"], #enum_dec_type);};

enum_dec_type_ident : (integer_atom_type | integer_vector_type | identifier)?
	{#enum_dec_type_ident = #([ENUMDECTYPEIDENT, "enum_dec_type_ident"], #enum_dec_type_ident);};

enum_list : enum_name_declaration (COMMA enum_name_declaration)*
	{#enum_list = #([ENUMLIST, "enum_list"], #enum_list);};

enum_name_declaration : enum_identifier enum_name_range enum_name_init 
	{#enum_name_declaration = #([ENUMNAMEDECLARATION, "enum_name_declaration"], #enum_name_declaration);};
	
enum_name_range : rangeQ;
enum_name_init : assign_expressionQ;

assign_expressionQ : ( ASSIGN expression)?
	{#assign_expressionQ = #([ASSIGNEXPRESSIONQ, "assign_expressionQ"], #assign_expressionQ);};


 
// Casting
cast : casting_type  (cast_paren | cast_curl)
	{#cast = #([CAST, "cast"], #cast);};
	
cast_paren : CASTPAREN (expression)? RPAREN;
cast_curl  : CASTCURLY (expression)? RCURLY;
casting_type : (simple_type | number | ("signed" | "unsigned" | "void"))?;
simple_type : integer_type | type_dec_non_integer | name;

port_type : data_type_wo_identifier
			| (name_dot_colon_only packed_dimension identifier) => type_dec_identifier
			| port_type_empty;		

port_type_net : net_type signing packed_dimension
	{#port_type_net = #([PORTTYPENET, "port_type_net"], #port_type_net);};

port_type_trireg : "trireg" signing packed_dimension
	{#port_type_trireg = #([PORTTYPETRIREG, "port_type_trireg"], #port_type_trireg);};

port_type_empty : signing packed_dimension
	{#port_type_empty = #([PORTTYPEEMPTY, "port_type_empty"], #port_type_empty);};

signing : (signing_item)?
	{#signing = #([SIGNING, "signing"], #signing);};
	
signing_item : ("signed" | "unsigned");

packedq : ("packed")? 
	{#packedq = #([PACKEDQ, "packedq"], #packedq);};
 
taggedq : ("tagged")? 
	{#taggedq = #([TAGGEDQ, "taggedq"], #taggedq);}; 	
 	

variable_dimension : packed_dimension associative_dimensionQ 
	{#variable_dimension = #([VARIABLEDIMENSION, "variable_dimension"], #variable_dimension);};
// Temporary packed dimension (needs fixing)	
packed_dimension :  (options{greedy=true;}: range)*
	{#packed_dimension = #([PACKEDDIMENSION, "packed_dimension"], #packed_dimension);};

associative_dimensionQ : (associative_dimension)?
	{#associative_dimensionQ = #([ASSOCIATIVEDIMENSIONQ, "associative_dimensionQ"], #associative_dimensionQ);};


associative_dimension :  LBRACK data_type RBRACK
						| LBRACK STAR RBRACK
						| LBRACK RBRACK                              // EMPTY
						| LBRACK DOLLAR ( COLON expression)? RBRACK; // QUEUE
						


rangeQ : (range)?
	{#rangeQ = #([RANGEQ, "range_q"], #rangeQ);};
	
  range :((LBRACK range_param COLON) => LBRACK range_param COLON (expression|DOLLAR) RBRACK |
        LBRACK ( (data_type) => data_type | (expression | DOLLAR)) RBRACK)
      {#range = #(#[VARRANGE, "VarRange"], #range);};
	
// Variable Declarations

data_declaration : 
	//virtual_interface_declaration
	variable_declaration // Need Constants and Oters
	//[ const ] [ lifetime ] variable_declaration
	| type_declaration
	| package_import_declaration
	;
	
	
	
package_import_declaration : "import" package_import_item (COMMA  package_import_item )* SEMI
	{#package_import_declaration = #([PACKAGEIMPORTDECLARATION, "package_import_declaration"], #package_import_declaration);};


package_import_item : package_identifier DOUBLECOLON (identifier | STAR)
	{#package_import_item = #([PACKAGEIMPORTITEM, "package_import_item"], #package_import_item);};



type_declaration : typedef_declaration;
constQ : (options{greedy=true;}:"const")?
	{#constQ = #([CONSTQ, "const_q"], #constQ);};

//variable_declaration ::= data_type list_of_variable_decl_assignments ;
variable_declaration :constQ lifetime data_type  list_of_variable_identifiers SEMI
	{#variable_declaration = #([VARIABLEDECLARATION, "variable_declaration"], #variable_declaration);};

list_of_variable_identifiers : variable_ident ( options{greedy=true;}: COMMA variable_ident)*
 	{#list_of_variable_identifiers = #([LISTOFVARIABLEIDENTIFIERS, "list_of_variable_identifiers"], #list_of_variable_identifiers);};

variable_ident :   identifier variable_dimension assign_expressionQ
	{#variable_ident = #([VARIABLEIDENT, "variable_ident"], #variable_ident);};


	
lifetime : ("static" | "automatic")?
	{#lifetime = #([LIFETIME, "lifetime"], #lifetime);};
	
//net_declaration14 ::=
//net_type_or_trireg [ drive_strength | charge_strength ] [ vectored | scalared ]
//[ signing ] { packed_dimension } [ delay3 ] list_of_net_decl_assignments ;
net_declaration  : net_type_or_trireg signing packed_dimension delayQ list_of_net_decl_assignments SEMI
	{#net_declaration = #([NETDECLARATION, "net_declaration"], #net_declaration);};
	
//list_of_net_decl_assignments ::= net_decl_assignment { , net_decl_assignment }
list_of_net_decl_assignments : net_decl_assignment (COMMA net_decl_assignment)*
	{#list_of_net_decl_assignments = #([LISTOFNETDECLASSIGNMENTS, "list_of_net_decl_assignments"], #list_of_net_decl_assignments);};

//net_decl_assignment ::= net_identifier { unpacked_dimension } [ = expression ]
net_decl_assignment : identifier packed_dimension (ASSIGN delayQ expression)? 
	{#net_decl_assignment = #([NETDECLARATIONASSIGNMENT, "net_decl_assignment"], #net_decl_assignment);};
	 
/////// UDP Section (DISABLED)

udp_head_declaration : ("extern")? "primitive" udp_identifier LPAREN (udp_port_list | udp_declaration_port_list | DOT STAR) RPAREN SEMI;
udp_declaration : udp_head_declaration (options{greedy=true;}: (udp_port_declaration )* udp_body "endprimitive" ( COLON udp_identifier )?)?;
udp_port_list : output_port_identifier COMMA input_port_identifier ( COMMA input_port_identifier )*;	
udp_declaration_port_list : udp_output_declaration COMMA udp_input_declaration ( COMMA udp_input_declaration )* ;
udp_port_declaration : (udp_output_declaration | udp_input_declaration | udp_reg_declaration )  SEMI;
udp_output_declaration : "output" "reg" port_identifier (ASSIGN constant_expression)?;
udp_input_declaration : "input" list_of_udp_port_identifiers;
udp_reg_declaration : "reg" variable_identifier;
udp_body : combined_body;// | sequential_body;
//combinational_body : "table" combinational_entry (combinational_entry )* "endtable";
//sequential_body : (udp_initial_statement)? "table" sequential_entry (sequential_entry)* "endtable" ;
/////// TOO DIFFICULT TO DEAL WITH
combined_body : (udp_initial_statement)? "table" table_entries "endtable";
table_entries :( ~("endtable") )+ ;
//combinational_entry : level_input_list COLON level_symbol SEMI;
//sequential_entry : level_input_list COLON next_state COLON next_state SEMI;
//combined_entry : level_input_list COLON next_state (COLON next_state)? SEMI;

udp_initial_statement : "initial" output_port_identifier ASSIGN init_val SEMI;
//init_val : number;
//seq_input_list : level_input_list | edge_input_list;
//level_input_list : level_symbol ( level_symbol )*;
//edge_input_list : (level_symbol)* edge_indicator (level_symbol)*;
//edge_indicator : ( level_symbol level_symbol ) | edge_symbol;
//current_state : level_symbol;
//next_state : level_symbol | MINUS;
//output_symbol : identifier | QUESTION | STAR;
//level_symbol : identifier | QUESTION | STAR;
//edge_symbol : identifier | QUESTION | STAR;



initial_construct : initial_statement;

//////// GENERATE SECTION

generated_module_instantiation : "generate" (generate_module_item)* "endgenerate"
	{#generated_module_instantiation = #(#[GENERATEITEM, "generated_module_instantiation"], #generated_module_instantiation);};

generate_module_item :
	generate_module_conditional_statement
	| generate_module_case_statement
	| generate_module_loop_statement
	| generate_module_identifier_block
	| module_item; // Not Correct



generate_module_conditional_statement : "if" expression generate_module_item (options{greedy=true;}: "else" generate_module_item)?
	{#generate_module_conditional_statement = #(#[GENERATEIF, "generate_module_conditional_statement"], #generate_module_conditional_statement);};

generate_module_case_statement : "case" expression genvar_module_case_item (genvar_module_case_item)* "endcase"
	{#generate_module_case_statement = #(#[GENERATECASE, "generate_module_case_statement"], #generate_module_case_statement);};
	
genvar_module_case_item : generate_module_case_item_normal
						| generate_module_case_default;	
		
generate_module_case_item_normal : expression ( COMMA expression )* COLON generate_module_item
	{#generate_module_case_item_normal = #(#[GENERATEMODULECASEITEMNORMAL, "generate_module_case_item_normal"], #generate_module_case_item_normal);};

generate_module_case_default : "default" ( COLON)? generate_module_item
	{#generate_module_case_default = #(#[GENERATEMODULECASEITEMDEFAULT, "generate_module_case_default"], #generate_module_case_default);};


generate_module_loop_statement : generate_module_loop_head generate_module_named_block
		{#generate_module_loop_statement = #(#[GENERATEFOR, "generate_module_loop_statement"], #generate_module_loop_statement);};
			
generate_module_loop_head : "for" LPAREN genvar_decl_assignment SEMI expression SEMI genvar_assignment RPAREN 
		{#generate_module_loop_head = #(#[GENERATEFORHEAD, "generate_module_loop_head"], #generate_module_loop_head);};
	
generate_module_identifier_block : (INDENTIFIER COLON)? generate_module_block
		{#generate_module_identifier_block = #(#[GENERATEIDENTIFIER, "generate_module_identifier_block"], #generate_module_identifier_block);};


// Not Complete
genvar_assignment : identifier (ASSIGN expression | "++" | "--")
	{#genvar_assignment = #(#[GENVARASSIGNMENT, "genvar_assignment"], #genvar_assignment);};

genvar_decl_assignment :("genvar")? identifier ASSIGN expression
	{#genvar_decl_assignment = #(#[GENVARDECLASSIGNMENT, "genvar_decl_assignment"], #genvar_decl_assignment);};

	
generate_module_named_block :
	((generate_module_block) => generate_module_block| 
	(identifier COLON)       => generate_module_block_ident |
	                            generate_module_item);
	
generate_module_block_ident : identifier COLON generate_module_block
	{#generate_module_block_ident = #(#[GENERATEMODULEBLOCKIDENT, "generate_module_block_ident"], #generate_module_block_ident);};

generate_module_block : "begin" colon_identifierq (generate_module_item)* "end" colon_identifierq
	{#generate_module_block = #(#[GENERATEMODULEBLOCK, "generate_module_block"], #generate_module_block);};

colon_identifierq : (COLON identifier)?
	{#colon_identifierq = #(#[COLONIDENTIFIERQ, "colon_identifierq"], #colon_identifierq);};



ansi_port_listq : (LPAREN ansi_port_list RPAREN)?
	{#ansi_port_listq = #(#[ANSIPORTLISTQ, "ansi_port_listq"], #ansi_port_listq);};


// Package Declaration
package_identifier : identifier;

package_declaration : "package" package_identifier SEMI package_body "endpackage" ( COLON package_identifier)?
	{#package_declaration = #(#[PACKAGEDECLARATION, "package_declaration"], #package_declaration);};

package_body : (package_item)*
	{#package_body = #(#[PACKAGEBODY, "package_body"], #package_body);};



package_item : (include_directive) => include_directive 
			| (class_sub_call) => class_sub_call 
			//| constraint_declaration
		    | package_or_generate_item_declaration 
			| parameter_declaration_semi 
			| anonymous_program 
			| timeunits_declaration;

data_dec_pred : (constQ lifetime data_type) | "typedef" | "import";

package_or_generate_item_declaration : (("virtual")? "class") => class_declaration
	| (data_dec_pred)  => data_declaration
	| task_declaration
	//| ((class_constructor_declaration) => class_constructor_declaration
	| function_declaration
	| dpi_import_export // (TBD)
	| constraint_declaration //| extern_constraint_declaration (TBD)
	
	//| parameter_declaration ;(Indluced in data_declaration)
	//| local_parameter_declaration (Indluced in data_declaration)
	|  covergroup_declaration 
	| overload_declaration 
	| concurrent_assertion_item_declaration;


anonymous_program : "program" (anonymous_program_item)* "endprogram";

anonymous_program_item : task_declaration
						//| (class_constructor_declaration) => class_constructor_declaration
						| function_declaration
						| class_declaration
						| covergroup_declaration (TBD)
						;
// End Package Declaration
dpi_import_export : ("import" | "export") STRING (dpi_import_property)? (c_identifier ASSIGN)? dpi_method_proto
	{#dpi_import_export = #(#[DPIIMPORTEXPORT, "dpi_import_export"], #dpi_import_export);};

dpi_import_property : "context" | "pure";
dpi_method_proto : method_prototype;
////// DPI 



////// END DPI

//////// PROGRAM SECTION

program_declaration : program_declaration_normal
	| program_declaration_external
	{#program_declaration = #(#[PROGRAMDECLARATION, "program_declaration"], #program_declaration);};

program_declaration_normal : program_nonansi_header timeunits_declarationQ program_body "endprogram" colon_identifierq
	{#program_declaration_normal = #(#[PROGRAMDECLARATIONNORMAL, "program_declaration_normal"], #program_declaration_normal);};

program_declaration_external :  "extern" program_nonansi_header
	{#program_declaration_external = #(#[PROGRAMDECLARATIONEXTERNAL, "program_declaration_external"], #program_declaration_external);};

program_nonansi_header : "program" lifetime identifier 
	((interface_header_dot_star) => interface_header_dot_star | interface_header_normal) SEMI
	{#program_nonansi_header = #(#[PROGRAMDECLARATIONNOANSIHEADER, "program_nonansi_header"], #program_nonansi_header);};

program_body : (program_item)*
	{#program_body = #(#[PROGRAMBODY, "program_body"], #program_body);};

/*
program_dot_star : LPAREN DOT STAR RPAREN;
program_header : ("extern")? "program" lifetime program_identifier parameter_port_list (port_list | program_dot_star) SEMI;
program_declaration : program_header (options{greedy=true;}: timeunits_declaration)? (program_item)* "endprogram" ( COLON program_identifier )?;
*/
//////// END PROGRAM SECTION

timeunits_declarationQ : (options{greedy=true;}: timeunits_declaration)?
	{#timeunits_declarationQ = #(#[TIMEUNITSDECLARATIONQ, "timeunits_declarationQ"], #timeunits_declarationQ);};

// "interface" should be options but conflicts with variable_identifier
virtual_interface_declaration : "virtual" "interface" interface_identifier list_of_virtual_interface_decl ;
list_of_virtual_interface_decl : variable_identifier ( ASSIGN interface_instance_identifier )?
	( COMMA variable_identifier ( ASSIGN interface_instance_identifier )? )*;
	
	
interface_declaration : //(interface_declaration_dot_star) => interface_declaration_dot_star |
	interface_declaration_normal
	| interface_declaration_external
	{#interface_declaration = #(#[INTERFACEDECLARATION, "interface_declaration"], #interface_declaration);};
	

interface_declaration_normal : interface_nonansi_header timeunits_declarationQ interface_body "endinterface" colon_identifierq
	{#interface_declaration_normal = #(#[INTERFACEDECLARATIONNORMAL, "interface_declaration_normal"], #interface_declaration_normal);};

interface_declaration_external :  "extern" interface_nonansi_header
	{#interface_declaration_external = #(#[INTERFACEDECLARATIONEXTERNAL, "interface_declaration_external"], #interface_declaration_external);};


interface_nonansi_header : "interface" lifetime identifier 
	((interface_header_dot_star) => interface_header_dot_star | interface_header_normal) SEMI
	{#interface_nonansi_header = #(#[INTERFACEDECLARATIONNOANSIHEADER, "interface_nonansi_header"], #interface_nonansi_header);};

interface_header_dot_star : LPAREN DOT STAR RPAREN
	{#interface_header_dot_star = #(#[INTERFACEHEADERDOTSTAR, "interface_header_dot_star"], #interface_header_dot_star);};

interface_header_normal :  parameter_port_list port_list
	{#interface_header_normal = #(#[INTERFACEHEADERNORMAL, "interface_header_normal"], #interface_header_normal);};

interface_body : (interface_item)*
	{#interface_body = #(#[INTERFACEBODY, "interface_body"], #interface_body);};


modport_declaration : "modport" modport_item (COMMA modport_item)* SEMI
	{#modport_declaration = #(#[MODPORTDECLARATION, "modport_declaration"], #modport_declaration);};

modport_item : identifier LPAREN modport_ports_declaration (COMMA modport_ports_declaration )* RPAREN
	{#modport_item = #(#[MODPORTITEM, "modport_item"], #modport_item);};

// Converged into this from many options. This is a superset with failing grammars accepted

modport_ports_declaration : modport_declaration_item;
modport_declaration_item : modport_declaration_prefix (modport_dot | modport_hier)
	{#modport_declaration_item = #(#[MODPORTDECLARATIONITEM, "modport_declaration_item"], #modport_declaration_item);};

modport_declaration_prefix : ("clocking" | "import" | "export" | port_direction)?						  
	{#modport_declaration_prefix = #(#[MODPORTDECLARATIONPREFIX, "modport_declaration_prefix"], #modport_declaration_prefix);};
			
modport_hier : identifier (LBRACK (expression)?  RBRACK DOT identifier)?
	{#modport_hier = #(#[MODPORTHIER, "modport_hier"], #modport_hier);};

modport_dot  : DOT identifier LPAREN (expression)? RPAREN
	{#modport_dot = #(#[MODPORTDOT, "modport_dot"], #modport_dot);};
						  







// Class Declarations

virtualQ : ("virtual")?
	{#virtualQ = #(#[VIRTUALQ, "virtualQ"], #virtualQ);};

class_head :  virtualQ "class" lifetime identifier parameter_port_list class_extends class_extends_args SEMI
	{#class_head = #(#[CLASSHEAD, "class_head"], #class_head);};

class_extends_args : (LPAREN list_of_arguments RPAREN)? 
	{#class_extends_args = #(#[CLASSEXTENDSARGS, "class_extends_args"], #class_extends_args);};
	
class_extends : ( "extends" class_type  )?
	{#class_extends = #(#[CLASSEXTENDS, "class_extends"], #class_extends);};
	
	
class_body : class_body_list "endclass" ( COLON identifier)?
	{#class_body = #(#[CLASSBODY, "class_body"], #class_body);};

class_body_list : (class_item)*
	{#class_body_list = #(#[CLASSBODYLIST, "class_body_list"], #class_body_list);};


class_declaration : class_head class_body
	{#class_declaration = #(#[CLASSDECLARATION, "class_declaration"], #class_declaration);};

class_type : ps_class_identifier parameter_value_assignment  (DOUBLECOLON class_identifier parameter_value_assignment )*;
class_identifier : identifier;
ps_class_identifier : identifier;

list_or_arguments : expression (COMMA expression)*;
class_item :  ((("static" | "default")? "constraint" ) => class_constraint
			| (class_sub_call) => class_sub_call
			| include_directive
			| ((class_method) => class_method
			| (class_property) => class_property
			| typedef_declaration
			| class_declaration
			| timeunits_declaration
			| covergroup_declaration));
			
class_sub_call : subroutine_call (SEMI)?;
			
class_property_list : (options{greedy=true;}: property_qualifier)*
	{#class_property_list = #(#[CLASSPROPERTYLIST, "class_property_list"], #class_property_list);};
			
class_property : constQ class_property_list data_declaration
	{#class_property = #(#[CLASSPROPERTY, "class_property"], #class_property);};
			

/*class_method : ("extern")? (method_qualifier)* 
	(//method_prototype SEMI 
	 //(class_constructor_total) => class_constructor_total
	 function_declaration 
	 | task_declaration 
	 ) */
class_method : (class_method_proto | class_method_normal);
	 //{#class_method = #(#[CLASSMETHOD, "class_method"], #class_method);};

class_method_qualifier_list : (method_qualifier)*
	{#class_method_qualifier_list = #(#[CLASSMETHODQUALIFIERLIST, "class_method_qualifier_list"], #class_method_qualifier_list);};

class_method_proto : "extern" class_method_qualifier_list class_method_prototype
	{#class_method_proto = #(#[CLASSMETHODPROTO, "class_method_proto"], #class_method_proto);};
	
class_method_normal : class_method_qualifier_list (function_declaration | task_declaration)
	{#class_method_normal = #(#[CLASSMETHODNORMAL, "class_method_normal"], #class_method_normal);};
	 
//class_constructor_total : ((class_constructor_declaration) => class_constructor_declaration |
//						   class_constructor_prototype);
						   
//class_constructor_declaration : class_constructor_prototype class_constructor_decq;

//class_constructor_decq : (class_constructor_dec);
class_constructor_prototype : "function" (class_scope)? "new" (LPAREN tf_port_list RPAREN)? SEMI;


class_constraint :(constraint_prototype) => constraint_prototype 
			| constraint_declaration;

class_item_qualifier : "static" | "protected" | "local";
property_qualifier : "rand" | "randc" | "protected" | "local" | "static";
method_qualifier : "virtual" | "pure" | class_item_qualifier;
method_prototype : (task_prototype | function_prototype) ;

//class_constructor_dec : (function_statement_or_null)* class_constructor_dec_super class_constructor_dec_tail;


//class_constructor_dec_super : ("super" DOT "new" (LPAREN list_of_arguments RPAREN )? SEMI  (function_statement_or_null)* )?;
//class_constructor_dec_tail :  "endfunction" ( COLON "new" )?;

// TODO : SUPPORT THE expression options

class_array_new : "new" ((LBRACK) => dynamic_array_new | (LPAREN) => class_new | expression)
	{#class_array_new = #(#[CLASSARRAYNEW, "class_array_new"], #class_array_new);};

class_new :  LPAREN list_of_arguments RPAREN 	
	{#class_new = #(#[CLASSNEW, "class_new"], #class_new);};

dynamic_array_new :  LBRACK expression RBRACK ( LPAREN expression RPAREN )?
	{#dynamic_array_new = #(#[DYNAMICARRAYNEW, "dynamic_array_new"], #dynamic_array_new);};



// TODO Needs Definition
class_scope : "asdlfja;slkdfja;lskjdf;alskjdf"; // Needs Definition
// End Class Declaration

block_item_declaration : data_declaration
//|  local_parameter_declaration
| parameter_declaration SEMI
| overload_declaration
;

// Constraints
//constraint_prototype : ( "static" )? "constraint" constraint_identifier;
//constraint_identifier : IDENTIFIER;

// End Constraints

// Tasks and Functions
// DELETE task_function_prefix
//task_function_prefix : (interface_identifier DOT | class_scope)?
// 	{#task_function_prefix = #(#[TASKFUNCTIONPREFIX, "task_function_prefix"], #task_function_prefix);};
 	
//task_function_port_list : (LPAREN (tf_port_list)? RPAREN)?
//	{#task_function_port_list = #(#[TASKFUNCTIONPORTLIST, "task_function_port_list"], #task_function_port_list);};

task_function_port_list : (LPAREN (tf_port_list)? RPAREN)?	
	{#task_function_port_list = #(#[TASKFUNCTIONPORTLIST, "task_function_port_list"], #task_function_port_list);};

// TASK
task_declaration : "task" lifetime task_body_declaration 
        {#task_declaration = #(#[TASK, "task_declaration"], #task_declaration);};
        
task_body_declaration : task_head_declaration (options{greedy=true;}: task_body_declaration_list  "endtask" (COLON task_identifier)?)?
	{#task_body_declaration = #(#[TASKBODYDECLARATION, "task_body_declaration"], #task_body_declaration);};

// task_function_prefix not needed as task_identifier is name
task_head_declaration : task_identifier task_function_port_list SEMI
	{#task_head_declaration = #(#[TASKHEADDECLARATION, "task_head_declaration"], #task_head_declaration);};

task_body_declaration_list : (task_body_item)*
	{#task_body_declaration_list = #(#[TASKBODYDECLARATIONLIST, "task_body_declaration_list"], #task_body_declaration_list);};

task_body_item : //((tf_item_declaration) => tf_item_declaration 
 				//| io_declaration_top
				(data_declaration) => data_declaration
				| statement_or_null;

tf_item_declaration :((block_item_declaration) => block_item_declaration | tf_port_declaration);

tf_port_list : tf_port_item (COMMA tf_port_item )*
	{#tf_port_list = #(#[TFPORTLIST, "tf_port_list"], #tf_port_list);};

tf_port_item : /*tf_port_direction*/ data_type_or_implicit port_identifier variable_dimension ( ASSIGN expression )?
	{#tf_port_item = #(#[TFPORTITEM, "tf_port_item"], #tf_port_item);};

//tf_port_direction : ( port_direction)?
//	{#tf_port_direction = #(#[TFPORTDIRECTION, "tf_port_direction"], #tf_port_direction);};

tf_port_declaration : /*tf_port_direction*/ data_type_or_implicit list_of_tf_variable_identifiers ;
// ENDTASK

// FUNCTION
function_declaration : "function" lifetime function_body_declaration
	{#function_declaration = #(#[FUNCTION, "function_declaration"], #function_declaration);};
        
function_body_declaration : function_head_declaration ( options{greedy=true;}:  function_body_declaration_list "endfunction" (COLON function_identifier)?)?
	{#function_body_declaration = #(#[FUNCTIONBODYDECLARATION, "function_body_declaration"], #function_body_declaration);};
	
function_body_declaration_list :(function_body_item)*
	{#function_body_declaration_list = #(#[FUNCTIONBODYDECLARATIONLIST, "function_body_declaration_list"], #function_body_declaration_list);};
	
// task_function_prefix not needed as task_identifier is name
function_head_declaration : ((name_dot_colon_only LPAREN) =>  function_head_declaration_no_return
		| function_head_declaration_return );
		
function_head_declaration_return : function_data_type_or_implicit function_head_declaration_no_return
		{#function_head_declaration_return = #(#[FUNCTIONHEADDECLARATIONRETURN, "function_head_declaration_return"], #function_head_declaration_return);};

function_head_declaration_no_return : function_identifier task_function_port_list SEMI
		{#function_head_declaration_no_return = #(#[FUNCTIONHEADDECLARATIONNORETURN, "function_head_declaration_no_return"], #function_head_declaration_no_return);};


class_method_prototype :(function_prototype | task_prototype);
function_prototype : "function" lifetime function_head_declaration
	{#function_prototype = #(#[FUNCTIONPROTOTYPE, "function_prototype"], #function_prototype);};
	
task_prototype : "task" lifetime task_head_declaration
	{#task_prototype = #(#[TASKPROTOTYPE, "task_prototype"], #task_prototype);};


function_body_item : //(tf_item_declaration) => tf_item_declaration 
					 //| io_declaration_top
					 (data_declaration) => data_declaration
					 | function_statement_or_null;

function_statement : statement_or_null;
function_statement_or_null : function_statement;



function_data_type_or_implicit : (function_data_type) => function_data_type 
								| parameter_dimension_node;
								
function_data_type : data_type | "void"
	{#function_data_type = #(#[FUNCTIONDATATYPE, "function_data_type"], #function_data_type);};


list_of_arguments : (expression)? ( COMMA (expression)? )* 
	| DOT identifier LPAREN (expression)? RPAREN ( COMMA  DOT identifier LPAREN (expression)? RPAREN )*;


// End Tasks and Functions

timeunits_declaration : ("timeunit" | "timeprecision") time_literal SEMI;
time_literal : NUMBER identifier;


// Assignment and expression
assign_op : ASSIGN | LE | PLUSEQ | MINUSEQ | MULTEQ | DIVEQ | MODEQ | ANDEQ | OREQ | XOREQ | LELEEQ | GRGREQ | LELELEEQ | GEGEGEEQ;
unique_priority : ("unique" | "priority")?;
// alias statement 
//alias_statement : lvalue ASSIGN lvalue ( ASSIGN lvalue )* SEMI;
net_alias : "alias" lvalue ASSIGN lvalue ( ASSIGN lvalue )* SEMI;
// overload_declaration
overload_declaration : "bind" overload_operator "function" data_type function_identifier LPAREN overload_proto_formals RPAREN SEMI;
overload_operator : (binary_operator) => binary_operator | unary_operator | assign_op; // May Need Inc/Dec
overload_proto_formals : data_type (COMMA data_type)*;

// Delay-Event-Control
delay_or_event_control : delay_control | event_control | repeat_control;
repeat_control : "repeat" LPAREN expression RPAREN event_control;
// TODO : ADD hierarchical_event_identifier && sequence_instance 
event_control : AT ( name | (LPAREN event_expression RPAREN) | STAR | LPAREN STAR RPAREN);
// TODO : ADD sequence_instance
event_expression :sub_event_expression (options{greedy=true;}: ("or" | COMMA)  sub_event_expression )*
    {#event_expression = #(#[EVENTEXPRESSION, "EventExpression"], #event_expression);};

action_block : (options{greedy=true;}:statement_or_null)? (options{greedy=true;}: "else" statement_or_null)?;
// final construct
final_construct : "final" function_statement;

// Assertions

statement_name_prefix : (block_identifier COLON)?
	{#statement_name_prefix = #(#[STATEMENTNAMEPREFIX, "statement_name_prefix"], #statement_name_prefix);};

concurrent_assertion_item : statement_name_prefix concurrent_assertion_statement
	{#concurrent_assertion_item = #(#[CONCURRENTASSERTIONITEM, "concurrent_assertion_item"], #concurrent_assertion_item);};

concurrent_assertion_statement : assert_property_statement | assume_property_statement | cover_property_statement;

assert_property_statement : "assert" "property" LPAREN property_spec RPAREN action_block
	{#assert_property_statement = #(#[ASSERTPROPERTYSTATEMENT, "assert_property_statement"], #assert_property_statement);};

assume_property_statement : "assume" "property" LPAREN property_spec RPAREN SEMI
	{#assume_property_statement = #(#[ASSUMEPROPERTYSTATEMENT, "assume_property_statement"], #assume_property_statement);};

cover_property_statement : "cover" "property" LPAREN property_spec RPAREN statement_or_null
	{#cover_property_statement = #(#[COVERPROPERTYSTATEMENT, "cover_property_statement"], #cover_property_statement);};

expect_property_statement : "expect" LPAREN property_spec RPAREN action_block
	{#expect_property_statement = #(#[EXPECTPROPERTYSTATEMENT, "expect_property_statement"], #expect_property_statement);};



property_instance : ps_property_identifier ( LPAREN ( actual_arg_list )? RPAREN )?;
// @TODO sequence_declaration
concurrent_assertion_item_declaration :property_declaration | sequence_declaration;

property_declaration : "property" identifier property_entries "endproperty" (COLON identifier)?
	{#property_declaration = #(#[PROPERTYDECLARATION, "property_declaration"], #property_declaration);};

property_entries :( ~("endproperty") )+ ;



/*
property_declaration : "property" property_identifier ( LPAREN ( list_of_formals )? RPAREN )? SEMI
	(options{greedy=true;}:assertion_variable_declaration)*
	property_spec SEMI
"endproperty" ( COLON property_identifier )?;
*/

property_spec : (options{greedy=true;}:clocking_event)? ( "disable" "iff" LPAREN expression_or_dist RPAREN )? property_expr;

// @TODO sequence_expression , Infinite recursion with and/or

property_expr : property_expr_rec (options{greedy=true;}: property_and_or)*;

property_expr_rec : (sequence_expr) => sequence_expr ((POINTDASH | POINTEQ)  property_expr)? 
	| LPAREN property_expr RPAREN
	| "not" property_expr
	| "if" LPAREN expression_or_dist RPAREN property_expr (options{greedy=true;}: "else" property_expr )?
	| property_instance
	| clocking_event property_expr;

property_and_or : ("or" | "and") property_expr;

// @TODO constraints to add the dist
expression_or_dist : expression ( "dist" LCURLY dist_list RCURLY )?;

list_of_formals : formal_list_item ( COMMA formal_list_item );
formal_list_item : formal_identifier (ASSIGN actual_arg_expr)?;
assertion_variable_declaration : data_type list_of_variable_identifiers SEMI;
// Sequences

sequence_declaration : "sequence" identifier sequence_entries "endsequence" (COLON identifier)?
	{#sequence_declaration = #(#[SEQUENCEDECLARATION, "sequence_declaration"], #sequence_declaration);};

sequence_entries :( ~("endsequence") )+ ;

/* 
sequence_declaration :	"sequence" sequence_identifier ( LPAREN (list_of_formals)? RPAREN )? SEMI
	(assertion_variable_declaration)*
	sequence_expr SEMI
"endsequence" ( COLON sequence_identifier )?
*/


sequence_expr : sequence_expr_rec (options{greedy=true;}: sequence_and_or_intersect)*;

sequence_expr_rec : cycle_delay_range sequence_expr (options{greedy=true;}: cycle_delay_range sequence_expr)*
	//| sequence_expr cycle_delay_range sequence_expr (cycle_delay_range sequence_expr)*
	| (LPAREN) => LPAREN (expression_or_dist) (COMMA sequence_match_item)* RPAREN (boolean_abbrev)? (options{greedy=true;}: cycle_delay_range sequence_expr)*
	| expression_or_dist (boolean_abbrev )? ("throughout" sequence_expr)?
	
	//// | sequence_instance (sequence_abbrev )? (TBD May be included in 2)
	//| LPAREN sequence_expr (COMMA sequence_match_item)* RPAREN ( boolean_abbrev )?
	//| sequence_expr "and" sequence_expr
	//| sequence_expr "intersect" sequence_expr
	//| sequence_expr "or" sequence_expr
	| "first_match" LPAREN sequence_expr (COMMA sequence_match_item)* RPAEN
	//| expression_or_dist "throughout" sequence_expr
	//| sequence_expr "within" sequence_expr
	| clocking_event sequence_expr;

sequence_and_or_intersect : ("and" | "intersect" | "or" | "within") sequence_expr;


sequence_match_item : (total_assignment) => total_assignment 
				| (inc_or_dec_expression) => inc_or_dec_expression 
				| subroutine_call;
				
sequence_instance : ps_sequence_identifier ( LPAREN ( actual_arg_list )? RPAREN )?;

boolean_abbrev : consecutive_repetition | non_consecutive_repetition | goto_repetition;
sequence_abbrev : consecutive_repetition;

consecutive_repetition : LBRACK STAR const_or_range_expression RBRACK;
non_consecutive_repetition : LBRACK ASSIGN const_or_range_expression RBRACK;
goto_repetition : LBRACK TRIGGER const_or_range_expression RBRACK;

const_or_range_expression : constant_expression (COLON (constant_expression | DOLLAR))?;
	


cycle_delay_range : POUNDPOUND (number 
	| identifier
	| LPAREN constant_expression RPAREN
	| LBRACK const_or_range_expression RBRACK);
	


actual_arg_list : actual_arg_expr ( COMMA actual_arg_expr )*
	| DOT formal_identifier LPAREN actual_arg_expr RPAREN ( COMMA DOT formal_identifier LPAREN actual_arg_expr RPAREN )*;

actual_arg_expr : event_expression | DOLLAR;

staticQ : ("static" | "default")?
	   {#staticQ = #(#[STATICQ,"staticQ"],#staticQ);};

// CONSTRAINTS
constraint_declaration : staticQ "constraint" constraint_identifier constraint_block
	   {#constraint_declaration = #(#[CONSTRAINTDECLARATION,"constraint_declaration"],#constraint_declaration);};

constraint_block : LCURLY (constraint_block_item)* RCURLY ;
constraint_block_item : "solve" identifier_list "before" identifier_list SEMI
	| constraint_expression;

constraint_expression : ( expression_or_dist) => expression_or_dist SEMI
	| constraint_expression_trigger
	| constraint_expression_if
	| constraint_expression_for;

constraint_expression_trigger : expression TRIGGER constraint_set
	   {#constraint_expression_trigger = #(#[CONSTRAINTEXPRESSIONTRIGGER,"constraint_expression_trigger"],#constraint_expression_trigger);};

constraint_expression_if : "if" LPAREN expression RPAREN constraint_set (options{greedy=true;}: "else" constraint_set )?
	   {#constraint_expression_if = #(#[CONSTRAINTEXPRESSIONIF,"constraint_expression_if"],#constraint_expression_if);};
	  
constraint_expression_for : "foreach" LPAREN array_identifier (LBRACK loop_variables RBRACK)?  RPAREN constraint_set
	   {#constraint_expression_for = #(#[CONSTRAINTEXPRESSIONFOR,"constraint_expression_for"],#constraint_expression_for);};

constraint_set : (LCURLY)=> ( LCURLY ( constraint_expression)* RCURLY) 
					| constraint_expression
	   {#constraint_set = #(#[CONSTRAINTSET,"constraint_set"],#constraint_set);};

dist_list : dist_item ( COMMA dist_item )*;
dist_item : value_range ( dist_weight )?;
dist_weight : (COLONEQUALS | COLONDIV) expression;
constraint_prototype : ("static")? "constraint" constraint_identifier SEMI;
extern_constraint_declaration : ("static")? "constraint" class_scope constraint_identifier constraint_block;
identifier_list : identifier (COMMA identifier)*;
name_list : name (COMMA name)*;

value_range :  (LBRACK) => LBRACK expression COLON expression RBRACK | 
	expression ;
loop_variables : ( index_variable_identifier )? ( COMMA (index_variable_identifier)? )*;
// Clocking Block


clocking_declaration : ("default")? "clocking" (clocking_identifier)? clocking_event SEMI
	(clocking_item )*
	"endclocking" ( COLON clocking_identifier )?
	   {#clocking_declaration = #(#[CLOCKINGDECLARATION,"clocking_declaration"],#clocking_declaration);};
	
	
clocking_event : AT (identifier | LPAREN event_expression RPAREN);

clocking_item : default_clocking_item
			| normal_clocking_item
			| concurrent_assertion_item_declaration;

default_clocking_item : "default" default_skew SEMI;
normal_clocking_item : clocking_direction list_of_clocking_decl_assign SEMI;
			
default_skew :	("input"|"output") clocking_skew ("output" clocking_skew);
clocking_direction : ("inout" |"input"|"output" ) (options{greedy=true;}: clocking_skew)? ("output" (options{greedy=true;}: clocking_skew)?)?; 
					
					
list_of_clocking_decl_assign : clocking_decl_assign ( COMMA clocking_decl_assign )*;
clocking_decl_assign : signal_identifier ( EQUAL hierarchical_identifier )?;

clocking_skew : (edge_identifier)? (delay_control)?;
clocking_drive : (cycle_delay)? clockvar_expression LE (cycle_delay)? expression;

cycle_delay : POUNDPOUND (number | identifier | LPAREN expression RPAREN);
clockvar : hierarchical_identifier;
clockvar_expression : clockvar select;
	 
/////// RANDSEQUENCE
randsequence_statement : "randsequence" LPAREN (production_identifier)? RPAREN
	(production)+
"endsequence";

production_name : production_identifier;
production : (function_data_type)? production_name ( LPAREN tf_port_list RPAREN )? COLON rs_rule (BOR rs_rule )* SEMI;
rs_rule : rs_production_list ( COLON ASSIGN expression (rs_code_block)? )?;
rs_production_list : (rs_prod)+
		| "rand" "join" ( LPAREN expression RPAREN )? (production_item)+ ;
rs_code_block : LBRACK (options{greedy=true;}:  data_declaration)* (statement_or_null)* RBRACK;
rs_prod : production_item
	| rs_code_block
	| rs_if_else
	| rs_repeat
	| rs_case;
production_item : production_identifier (options{greedy=true;}: LPAREN list_of_arguments RPAREN)?;
rs_if_else : "if" LPAREN expression RPAREN production_item ( "else" production_item )?;
rs_repeat : "repeat" LPAREN expression RPAREN production_item;
rs_case :  "case" LPAREN expression RPAREN (rs_case_item)+ "endcase";
rs_case_item : expression ( COMMA expression )* COLON production_item
	| "default" ( COLON )? production_item;

/////// END RANDSEQUENCE

/////// COVERGROUP DECLARATION

covergroup_declaration : "covergroup" covergroup_entries "endgroup" (COLON identifier)?
	{#covergroup_declaration = #(#[COVERGROUPDECLARATION, "covergroup_declaration"], #covergroup_declaration);};

covergroup_entries :( ~("endgroup") )+ ;

/* 
covergroup_declaration : "covergroup" covergroup_identifier ( ( ( tf_port_list )? ) )? ( coverage_event )? SEMI
	(coverage_spec_or_option SEMI )*
"endgroup" ( COLON covergroup_identifier )?

coverage_spec_or_option : coverage_spec | coverage_option ;

coverage_option : ("option" | "type_option")  DOT member_identifier ASSIGN expression
coverage_spec : cover_point | cover_cross;
*/

/////// END COVERGROUP DECLARATION

	 
// STATEMENTS

 
statement : (options{greedy=true;}: block_identifier COLON )? statement_item
        {#statement = #(#[STATEMENT, "statement"], #statement);};


statement_item : (unique_priority "if") => conditional_statement |
				 statement_no_condition;

state_list : (statement | typedef_declaration)*
        {#state_list = #(#[STATELIST, "StateList"], #state_list);};

statement_no_condition : (ident_semi) => ident_semi 
	| (total_assignment) => total_assignment//blocking_assignment SEMI | nonblocking_assignment SEMI
	| procedural_continuous_assignment 
	| (unique_priority case_keyword) => case_statement
	//| conditional_statement
	| (inc_or_dec_expression) => inc_or_dec_expression SEMI
	| disable_statement
	| event_trigger
	|  ("forever" | "repeat" | "while" | "for" | "foreach" | "do") => loop_statement
	| jump_statement
	| ("fork") => par_block
	| procedural_timing_control_statement
	| (pre_block_nameQ "begin") => seq_block
	| wait_statement
	| procedural_assertion_statement
	| (clocking_drive) => clocking_drive SEMI
	| randsequence_statement
	| randcase_statement
	| expect_property_statement
	| (variable_declaration) => variable_declaration // Not Sure why but ...
	| subroutine_call_statement;
	 


// Kind of a kludge to handle functions without port lists
ident_semi : ((name_dot_colon_range) => name_dot_colon_range | cast) SEMI;

total_assignment : lvalue assign_op delay_or_event_controlq  ( ("new") => class_array_new | expression ) SEMI
	 {#total_assignment = #(#[TOTALASSIGN, "TotalAssign"], #total_assignment);};

randcase_statement : "randcase" (randcase_item)+ "endcase";
randcase_item : expression COLON statement_or_null;
 
disable_statement : "disable" (hierarchical_identifier | "fork") SEMI;
event_trigger : (TRIGGER | TRIGGER2) delay_or_event_controlq hierarchical_event_identifier SEMI;


 jump_statement : ("return" (expression)? | "break" | "continue") SEMI;
 procedural_assertion_statement : concurrent_assertion_statement
								| immediate_assert_statement;

immediate_assert_statement : "assert" LPAREN expression RPAREN action_block;		

par_block : "fork" ( COLON block_identifier )? (options{greedy=true;}: statement_or_null )* join_keyword ( COLON block_identifier )? 
	{#par_block = #(#[PARBLOCK, "par_block"], #par_block);};

join_keyword : "join" | "join_any" | "join_none";						
 
// TODO support the  portion of this statement
subroutine_call_statement : subroutine_call (statement | SEMI)
	{#subroutine_call_statement = #(#[SUBROUTINECALLSTATEMENT, "subroutine_call_statement"], #subroutine_call_statement);};
//subroutine_call : (name_no_expression | "new") ( LPAREN (expression)? (COMMA (expression)?)* RPAREN );

subroutine_call : ("randomize") => randomize_call 
	| subroutine_call_normal;

subroutine_call_normal : (name_no_expression) (name_expression) ("with" ((LCURLY) => constraint_block | expression) )?;

// | "void"  LPAREN function_subroutine_call RPAREN SEMI
randomize_call : "randomize"  (options{greedy=true;}:LPAREN (variable_identifier_list | "null" )? RPAREN )? (options{greedy=true;}:"with" constraint_block )?;

case_item : (case_normal) => case_normal 
		| case_default 
		| case_pattern_item;

//cond_pattern : expression "matches" pattern;
case_pattern_item : pattern ( LAND expression )? COLON statement_or_null;
cond_predicate : LPAREN expression ("matches" pattern)? RPAREN;

pattern : variable_identifier
	| DOT (STAR | expression)
	| "tagged" member_identifier (pattern )?
	| LBRACK (pattern_brack_simple | pattern_brack_complex) RBRACK;


pattern_brack_simple : pattern ( COMMA pattern )*;
pattern_brack_complex :member_identifier COLON pattern ( COMMA member_identifier COLON pattern )*;

// TODO : need wait_order
wait_statement :("wait" (LPAREN expression RPAREN statement_or_null
					| "fork" SEMI))
        {#wait_statement = #(#[WAITSTATEMENT, "wait_statement"], #wait_statement);};

/////// LOOP STATEMENT
loop_statement :
        ("forever" statement_or_null |
        "repeat" LPAREN expression RPAREN statement_or_null |
        "while" LPAREN expression RPAREN statement_or_null |
        "for" LPAREN for_initialization SEMI expression SEMI for_step RPAREN statement |
        "do" statement_or_null "while" LPAREN expression RPAREN SEMI |
        "foreach" LPAREN array_identifier (LBRACK loop_variables RBRACK)? RPAREN statement)
        {#loop_statement = #(#[LOOPSTATEMENT, "loop_statement"], #loop_statement);};
        
        

        
for_initialization : ((options{greedy=true;}: data_type)? variable_assignment ( COMMA (options{greedy=true;}:  data_type)? variable_assignment )*)?;

for_step : for_step_assignment ( COMMA for_step_assignment )*;
// TODO : should be operator_assignment
for_step_assignment : (inc_or_dec_expression) => inc_or_dec_expression | assignment;
//////// END LOOP STATEMENT


//TODO This is not correct
constant_select : select;
select : (options{greedy=true;}: LBRACK expression RBRACK)* (options{greedy=true;}: LBRACK part_select_range RBRACK )?;
part_select_range : (constant_range) => constant_range | indexed_range;

indexed_range : expression (PLUS | MINUS) COLON constant_expression;
constant_range : constant_expression : constant_expression;
constant_expression : expression; // @TODO need to create real constant_expression
inc_or_dec_expression : inc_or_dec_operator variable_lvalue
					| variable_lvalue inc_or_dec_operator;
					
variable_lvalue : lvalue;

binary_operator :
        PLUS        |
        MINUS       |
        STAR        |
        DIV         |
        MOD         |
        EQUAL       |
        NOT_EQ      |
        EQ_CASE     |
        NOT_EQ_CASE |        
        LAND        |
        LOR         |
        LT_         |
        LE          |
        GT          |
        GE          |
        BAND        |
        BOR         |
        BXOR        |
        BXNOR       |
        SR          |
        SL	        |
        SRS         | 
        SLS         |
        POW         |
        RXNOR       |
        EQQUEQ      |
	    NOTQUEQ     | 
	    POINTDASH   |
	    POINTEQ;

/////// EXPRESSIONS

exp5 : exp6 (post_inside_expression)?;
exp4 : exp5 ("with" ((LCURLY) => constraint_block | expression) )?;

expression : //primary
	//| unary_operator primary
	//| inc_or_dec_expression
	//| LPAREN operator_assignment RPAREN
	//| expression binary_operator expression
	//| conditional_expression
	exp4; 
	//| inside_expression
	//| tagged_union_expression;

//tagged_union_expression : "tagged" member_identifier (exp6)?;
post_inside_expression :"inside" ((LCURLY) => LCURLY open_range_list RCURLY | expression);
//inside_expression : exp6 "inside" (open_range_list)*;
open_range_list : open_value_range ( COMMA open_value_range )*;
open_value_range : value_range;


primary_literal : (time_literal) => time_literal | number | string_literal ;
string_literal : STRING;

primary : (casting_type (CASTPAREN | CASTCURLY)) => cast 
	//| (inc_dec_expression) => inc_dec_expression 
	//| (DEFINE) => DEFINE
	|	primary_literal
	| (inc_dec_expression) => inc_dec_expression
	| (randomize_call) => randomize_call // Needed because name is used instead of subroutine call
	//| name ("with" constraint_block)? // randomize statement
	| name 
	// [ implicit_class_handle . | class_scope | package_scope ] hierarchical_identifier select
	//| (subroutine_call) => subroutine_call
	
	| empty_queue
	| concatenation  // TBD
	
	//| multiple_concatenation TBD
	
	//| LPAREN mintypmax_expression RPAREN
	//| cast
	//| streaming_expression TBD
	//| sequence_method_call TBD
	//| "$"
	
	
	
	| "null";

empty_queue : LBRACK RBRACK;

/////// END EXPRESSIONS



variable_assignment : variable_lvalue (ASSIGN expression)?;

list_of_tf_variable_identifiers : port_identifier variable_dimension ( ASSIGN expression)?
		( COMMA port_identifier variable_dimension (ASSIGN expression)? )*;

list_of_udp_port_identifiers : port_identifier ( COMMA port_identifier )*;
list_of_variable_assignments : variable_assignment (options{greedy=true;}:  COMMA variable_assignment )*;
variable_identifier_list : variable_identifier ( COMMA variable_identifier )*;

identifier : IDENTIFIER | DOLLAR_IDENTIFIER | DEFINE | time_unit | "do" | "new" | "super" |"randomize" | "string"  ;

block_identifier : identifier;
task_identifier : name_dot_colon_only; // Overkill really just (identifier name_colon)
function_identifier : name_dot_colon_only; // Overkill really just (identifier name_colon)
port_identifier : identifier;
interface_identifier : identifier;
enum_identifier : identifier;
ps_property_identifier : name;
property_identifier : name;
sequence_identifier : name;
clocking_identifier : name;
signal_identifier : name;
hierarchical_identifier : name; 
formal_identifier : name;
constraint_identifier : name;
array_identifier : name;
index_variable_identifier : name;
udp_identifier : name;
variable_identifier : name;
output_port_identifier : name;
input_port_identifier : name;
program_identifier : identifier;
interface_instance_identifier : name;
c_identifier : identifier;
member_identifier : identifier;
production_identifier : identifier;
hierarchical_event_identifier : name;
covergroup_identifier : identifier;
ps_sequence_identifier : identifier;
type_identifier : identifier;
modport_identifier : identifier;

class SystemVerilogLexer extends VerilogLexer;

options {
    testLiterals = false;   // don't automatically test for literals
    importVocab = SystemVerilog;
    k = 4;                  // 3 characters of lookahead
}
{
}

 
// System Verilog Assignment Operators
PLUSEQ      : "+=";
MINUSEQ     : "-=";  
MULTEQ      : "*="; 
DIVEQ       : "/="; 
MODEQ       : "%="; 
ANDEQ       : "&="; 
OREQ        : "|="; 
XOREQ       : "^=";
LELEEQ      : "<<=";
GRGREQ      : ">>=";
LELELEEQ    : "<<<="; 
GEGEGEEQ    : ">>>=";
COLONDIV    : ":/";

// Binary Operators
EQQUEQ   : "=?=";
NOTQUEQ  : "!?=";

POINTDASH : "|->";
POINTEQ   :  "|=>";
POUNDPOUND : "##";
//DOLLAR : "$" ~('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$');
//DOLLAR : "$" ~('\3'..'\377');
TRIGGER2 : "->>";

COLONEQUALS : ":=";


CRAP : "$$#";




