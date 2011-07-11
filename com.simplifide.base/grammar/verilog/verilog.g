

header{package com.simplifide.base.verilog.parse.grammar.verilog;


import com.simplifide.base.sourcefile.antlr.tok.*;


}

class VerilogParser extends Parser;
options {
    k = 2;                        // two token lookahead
    buildAST = true;
    exportVocab = Verilog;
}


tokens {
    ROOT;
    DESIGNUNIT;
    
    CONTEXTCLAUSE; // Context Statements
    CONTEXTITEM;   
    DEFINEDIRECTIVE;
    INCLUDEDIRECTIVE;

	MODULE;
	MODULEHEAD;
        MODULEBODY;
	MODULEITEM;
	MODULEDEC;
	MODULENAME;

        PORTCLAUSE;
	PORTLIST;
        PORTELEMENT;

	PORTTOP;
	PORT;
	PORTNODOT;
	PORTDOT;
	PORTEXPRESSION;

	VARDEC;
	VARTYPE;
	VARRANGE;
	VARLIST;
	VARNAME;

    REGMEM;
    REGMEMARRAY;

	PARDEC;
	PARASSIGN;

	CONTINUOUS_ASSIGN;
	ASSIGNLIST;
	ASSIGNMENT;
	
	IDENTRANGE;
	SEGMENTRANGE;

	QUESTTOP;
    QUESTIONSEGMENT;
    PAROP;
    UNOP;
    BINOP;
    BINOPSMALL;
	CONCAT;

	ALWAYSTOP;
	ALWAYSHEAD;

	EVENTCONTROL;
	EVENTEXPRESSION;
	EVENTEXPRESSIONSUB;

	
	TOTALASSIGN;

	CONDITIONTOP;
	CONDITIONHEAD;
	CONDITIONELSE;

	CASETOP;
	CASEHEAD;
	CASELIST;
	CASEEXPRESSION;
	CASENORMAL;
	CASEDEFAULT;
	

	BEGENDBLOCK;
	SEQBLOCKNAMEQ;
	
	STATELIST;

	MODITEM;

        MODINSTANCETOP;
        MODINSTANCE;
        INSTANCENAME;
        INSTANCEPORTLIST;
        INSTANCEPORT;
        PORTCON;

        
        

        

       


	IDENTIFIERPATH;
	
	NAME;
	NAMERANGE;
	NAMEDOT;
	NAMEEXPRESSION;
	NAMEPOUNDEXPRESSION;
	
	DRIVESTRENGTHQ;
	DELAYQ;
	DELAYOREVENTCONTROLQ;
	
	TASK;
	FUNCTION;
	TASKVARDECLARATION;
	FUNCTIONVARDECLARATION;
	RANGEORTYPE;
	
	PARAMETEROVERRIDE;
	
	IFDEFDIRECTIVE;
	IFDEFHEAD;
	IFDEFCOND;
	
	TIMESCALEDIRECTIVE;
	
	TICKIFDEF   =  "`ifdef";
	TICKIFNDEF  = "`ifndef";
	TICKELSE    =  "`else";
	TICKENDIF     =  "`endif";
	TICKDEFINE  = "`define";
	TICKINCLUDE = "`include"; 
	TICKTIMESCALE = "`timescale";
	TICKUNDEF     = "`undef";
	
	GENERATEITEM;

	RANGEPARAM;
	
	PRIMITIVEDEFINITION;
	TABLEDEFINITION;


	SPECIFYBLOCK;
	SPECIFYITEM;
	SPECPARAMDECLARATION;
	PATHDECLARATION;
	EDGESENSITIVEPATHDECLARATION;
	SDPD;
	SIMPLEPATHDECLARATION;
	PARALLELPATHDECLARATION;
	FULLPATHDESCRIPTOR;
	LEVELSENSITIVEPATHDECLARATION;
	
	RANGEORTYPEQ;
	AUTOMATICQ;
	
	PREBLOCKNAMEQ;
	UNIQUEPRIORITY;
	MATCHES;
	NAMECOLON;
	INITIALORFINAL;
	WAITSTATEMENT;
	DISABLESTATEMENT;
}

{
 

}


//-----------------------------------------------------------------------------
// Source Text
//-----------------------------------------------------------------------------



source_text : ( context_item | design_unit)* EOF
   {#source_text = #(#[ROOT,"Root"],#source_text);};


  

source_text_name : name  
	{#source_text_name = #(#[ROOT,"Root"],#source_text_name);};

design_unit : module
    {#design_unit = #(#[DESIGNUNIT,"DesignUnit"],#design_unit);};


module : module_dec module_body "endmodule"
	{#module = #(#[MODULE, "Module"], #module);};



module_dec :  ( "module" | "macromodule" ) module_name (port_clause)? SEMI
    {#module_dec = #(#[MODULEDEC, "ModuleDec"], #module_dec);};


module_name : name_of_module
     {#module_name = #(#[MODULENAME, "ModuleName"], #module_name);};

module_body : (module_item)*
	{#module_body = #(#[MODULEBODY, "Module_Body"], #module_body);};

port_clause : LPAREN port_list RPAREN
  {#port_clause = #([PORTCLAUSE, "PortClause"], #port_clause);};  

port_list : ((port_element)? (COMMA port_element)*)
	 {#port_list = #([PORTLIST, "PortList"], #port_list);};


port_element : (port_no_dot)
	    {#port_element = #([PORTELEMENT, "PortElement"], #port_element);};

port_no_dot : identifier (range)?
    {#port_no_dot = #([PORTNODOT, "PortNoDot"], #port_no_dot);};


port_expression :
        port_reference |
	LBRACE port_reference ( COMMA port_reference )* RBRACE
        ;

port_reference :
        ( name_of_variable LBRACK expression COLON ) =>
        name_of_variable LBRACK expression COLON expression RBRACK |
        ( name_of_variable LBRACK ) =>
        name_of_variable LBRACK expression RBRACK |
        name_of_variable
        ;

module_item :
	 // ambiguity between net_declaration and continuous_assign,
	 // but parser gets it right: keyword chosen over identifier.
	  (
//        variable_declaration |
		generate_item |
		gate_instantiation |
        instantiation |
        parameter_override |
        continuous_assign |
        specify_block |
        initial_statement |
        always_construct |
        task_declaration |
        function_declaration);
	   
generate_item : "generate" (module_item)* "endgenerate"
	{#generate_item = #(#[GENERATEITEM, "generate_item"], #generate_item);};
         


/*
variable_declaration :
    parameter_declaration |
    reg_declaration |
    var_declaration;
*/






//----------------------------------------------------------------------------
// UDP specs
//----------------------------------------------------------------------------


   // Use a semantic predicate to determine whether a matched NUMBER
   // is a valid special value in the given context.
   // This kludge avoids having the special values in the Literals table,
   // thus avoiding a lexical conflict.
init_val :
        "1'b0" |
        "1'b1" |
        "1'bx" |
	n:NUMBER
	{ n.getText()=="0" || n.getText()=="1"}?
	;

primitive_definition : "primitive" primitive_entries "endprimitive"
	{#primitive_definition = #(#[PRIMITIVEDEFINITION, "primitive_definition"], #primitive_definition);};

table_definition : "table" table_entries "endtable"
	{#table_definition = #(#[TABLEDEFINITION, "table_definition"], #table_definition);};


primitive_entries :( ~("endprimitive") )+ ;

   // Don't try to parse table entries; just collect them.
   // There are ambiguities between edge_symbol and level_symbol,
   // and textbook Verilog examples don't seem to follow rules
   // completely. For example,
   //	"0    00    :    0;"
   // doesn't match grammar because of "00", but is frequently used.
table_entries :
	//(sequential_entry) => (sequential_entry)+ |
	//(combinational_entry)+
	(( ~(SEMI | "endtable") )+ SEMI)*
	;


/********
 ******** Start of commented out rules related to table entries.
 ********

combinational_entry :level_input_list COLON output_symbol SEMI;
sequential_entry :input_list COLON state COLON next_state SEMI;

input_list :
	(level_input_list) => level_input_list |
	edge_input_list
	;

level_input_list :
	(level_symbol)+
	;

edge_input_list :
	(level_symbol)* edge (level_symbol)*
	;

edge :
	LPAREN level_symbol level_symbol RPAREN |
	edge_symbol
	;

state :
	level_symbol
	;

next_state :
	output_symbol |
	MINUS
	;

   // Next 3 rules use semantic predicates to determine whether a matched
   // NUMBER or IDENTIFIER is a valid special value in the given context.
   // This kludge avoids having the special values in the Literals table,
   // thus avoiding a lexical conflict.
output_symbol:
	n:NUMBER
	{ n.getText()=="0" || n.getText()=="1"}?
      |
	i:IDENTIFIER
	{ i.getText()=="x" || i.getText()=="X"}?
	;

level_symbol:
	QUESTION
      |
	n:NUMBER
	{ n.getText()=="0" || n.getText()=="1"}?
      |
	i:IDENTIFIER
	{ i.getText()=="x" || i.getText()=="X" ||
	  i.getText()=="b" || i.getText()=="B" }?
	;

edge_symbol:
	STAR
      |
	i:IDENTIFIER
	{ i->getText()=="r" || i->getText()=="R" ||
	  i->getText()=="f" || i->getText()=="F" ||
	  i->getText()=="p" || i->getText()=="P" ||
	  i->getText()=="n" || i->getText()=="N" }?
	;

 ********
 ******** End of commented out rules related to table entries.
 ********/


// Overriden at SystemVerilog
task_declaration : "task" name_of_task SEMI task_var_declaration statement_or_null "endtask"
        {#task_declaration = #(#[TASK, "task"], #task_declaration);};
    
// Overridden at SystemVerilog    

function_declaration : "function" automaticq range_or_typeq name_of_function SEMI 
	function_var_declaration
        statement
        "endfunction"
        {#function_declaration = #(#[FUNCTION, "function_declaration"], #function_declaration);};


automaticq : ("automatic")?
	{#automaticq = #(#[AUTOMATICQ, "automatic_q"], #automaticq);};

range_or_typeq : (range_or_type)?
	{#range_or_typeq = #(#[RANGEORTYPEQ, "range_or_typeQ"], #range_or_typeq);};


task_var_declaration : ( options{greedy=true;}: /*(io_declaration SEMI) | */ variable_declaration)*
	{#task_var_declaration = #(#[TASKVARDECLARATION, "task_var_declaration"], #task_var_declaration);};
	
function_var_declaration : ( options{greedy=true;}: /*(io_declaration SEMI) | */ variable_declaration)*
	{#function_var_declaration = #(#[FUNCTIONVARDECLARATION, "function_var_declaration"], #function_var_declaration);};



range_or_type : range | "integer" | "real"
	{#range_or_type = #(#[RANGEORTYPE, "range_or_type"], #range_or_type);};


/** Crap methods which are overriden by systemverilog */

variable_declaration :
   "QQQQQDDDDDEEEEEEEFFFFFFFf" (range)? list_of_variables SEMI;
   
io_declaration :
   "PPPPPQQDDDDDEEEEEEEFFFFFFFf" (range)? list_of_variables SEMI;
  
/** End of Crap Methods */


//----------------------------------------------------------------------------
// Declarations
//----------------------------------------------------------------------------





// ---------------------------------------------------------------------------
// Variable Declations
// ---------------------------------------------------------------------------

// Nonfunctional --- Overriden by System Verilog Definition



/*

variable_declaration :
   var_type (range)? list_of_variables SEMI;
   {#var_declaration = #(#[VARDEC, "VarDec"], #var_declaration);};
   
parameter_declaration :
     parameter_type (range)? list_of_param_assignments SEMI
    {#parameter_declaration = #(#[PARDEC, "ParDec"], #parameter_declaration);};


reg_declaration :
        reg_type (range)? list_of_register_variables SEMI
   {#reg_declaration = #(#[VARDEC, "VarDec"], #reg_declaration);};
        

no_width_declaration :
        no_width_type (range)? list_of_register_variables SEMI
        ;
  */

// ---------------------------------------------------------------------------
// Variable Types
// ---------------------------------------------------------------------------

/*
parameter_type : ("parameter")
    {#parameter_type = #(#[VARTYPE, "VarType"], #parameter_type);};

var_type : ("input" | "output" | "inout")
   {#var_type = #(#[VARTYPE, "VarType"], #var_type);};

reg_type : ("reg" | "wire")
   {#reg_type = #(#[VARTYPE, "VarType"], #reg_type);};

no_width_type : ("time" | "real" | "event" | "integer")
    {#no_width_type = #(#[VARTYPE, "VarType"], #no_width_type);};
*/

// ---------------------------------------------------------------------------
// Variable Lists
// ---------------------------------------------------------------------------

list_of_param_assignments :
        param_assign ( COMMA param_assign )*
        {#list_of_param_assignments = #(#[VARLIST, "VarList"], #list_of_param_assignments);}
        ;

list_of_variables : variable_name (COMMA variable_name)*
	    {#list_of_variables = #(#[VARLIST, "VarList"], #list_of_variables);};



list_of_register_variables :
        register_variable ( COMMA register_variable )*
	    {#list_of_register_variables = #(#[VARLIST, "VarList"], #list_of_register_variables);};




// ---------------------------------------------------------------------------
// Variable Names
// ---------------------------------------------------------------------------


param_assign       :       param_assignment
	   {#param_assign = #(#[PARASSIGN, "ParAssign"], #param_assign);};

param_assignment : variable_name ASSIGN number;

variable_name : name_of_variable;
	   

register_variable :
        variable_name (register_memory_array)?
	    {#register_variable = #(#[REGMEM, "RegMem"], #register_variable);};

register_memory_array : LBRACK expression COLON expression RBRACK
	        {#register_memory_array = #(#[REGMEMARRAY, "RegMemArray"], #register_memory_array);};
/*
net_declaration :
        ( net_type (expandrange)? ) =>
        net_type (expandrange)? (delay)?
           list_of_assigned_variables SEMI |
        "trireg" (charge_strength)? (expandrange)? (delay)?
           list_of_variables SEMI
        ;
*/
net_type :
        
        "tri" |
        "tri1" |
        "supply0" |
        "wand" |
        "triand" |
        "tri0" |
        "supply1" |
        "wor" |
        "trior" |
	"trireg"
        ;

expandrange :
        "scalared" range |
	"vectored" range |
	range
        ;


continuous_assign : "assign"  drive_strengthQ delayQ list_of_assignments SEMI       
	{#continuous_assign = #(#[CONTINUOUS_ASSIGN, "Continuous_ASSIGN"], #continuous_assign);};
        
drive_strengthQ : (drive_strength)?
	{#drive_strengthQ = #(#[DRIVESTRENGTHQ, "DriveStrengthQ"], #drive_strengthQ);};
 
delayQ : (options{greedy=true;}: delay)?      
     {#delayQ = #(#[DELAYQ, "delayQ"], #delayQ);};  
       
list_of_assignments : assignment ( COMMA assignment )*
      {#list_of_assignments = #(#[ASSIGNLIST, "AssignList"], #list_of_assignments);};

parameter_override :"defparam" list_of_param_assignments SEMI
	{#parameter_override = #(#[PARAMETEROVERRIDE, "parameter_override"], #parameter_override);};



list_of_assigned_variables :
        name_of_variable ( ASSIGN expression )?
	( COMMA name_of_variable ( ASSIGN expression )? )*
        ;




charge_strength :
        LPAREN "small"  RPAREN |
        LPAREN "medium" RPAREN |
        LPAREN "large"  RPAREN
        ;

drive_strength :
        LPAREN strength0 COMMA strength1 RPAREN |
        LPAREN strength1 COMMA strength0 RPAREN
        ;

strength0 :
        "supply0" |
        "strong0" |
        "pull0" |
        "weak0" |
	"highz0"
        ;

strength1 :
        "supply1" |
        "strong1" |
        "pull1" |
        "weak1" |
	"highz1"
        ;

  range :((LBRACK range_param COLON) => LBRACK range_param COLON (expression|DOLLAR) RBRACK |
        LBRACK (expression|DOLLAR) RBRACK)
      {#range = #(#[VARRANGE, "VarRange"], #range);};


range_param : (expression|DOLLAR) (PLUS | MINUS)?  
	{#range_param = #(#[RANGEPARAM, "RangeParam"], #range_param);};






//----------------------------------------------------------------------------
// Primitive Instances
//----------------------------------------------------------------------------

gate_instantiation : gate_type (options{greedy=true;}: drive_strength)? (delay)? gate_instance ( COMMA gate_instance )* SEMI;

gate_type :
        "and" |
        "nand" |
        "or" |
        "nor" |
        "xor" |
        "xnor" |
        "buf" |
        "bufif0" |
        "bufif1" |
        "not" |
        "notif0" |
        "notif1" |
        "pulldown" |
        "pullup" |
        "nmos" |
        "rnmos" |
        "pmos" |
        "rpmos" |
        "cmos" |
        "rcmos" |
        "tran" |
        "rtran" |
        "rtranif0" |
        "tranif1" |
        "rtranif1"
        ;

delay : POUND number | 
		POUND identifier |
        POUND LPAREN mintypmax_expression ( COMMA mintypmax_expression ( COMMA mintypmax_expression )?)? RPAREN 
        ;

time_literal : (number | /*fixed_point_number*/) time_unit;


gate_instance :
        (name_of_gate_instance)? (range)*
	LPAREN terminal ( COMMA terminal )* RPAREN
        ;
/*
udp_instantiation :
	name_of_UDP (drive_strength)? (delay)?
          udp_instance ( COMMA udp_instance )* SEMI
	;
*/
udp_instance :
        (name_of_UDP_instance)?
	LPAREN terminal ( COMMA terminal )* RPAREN
        ;

terminal :
        expression
   // | identifier
        ;

//----------------------------------------------------------------------------
// Module Instantiations
//----------------------------------------------------------------------------

//instantiation : general_name module_instance ( COMMA module_instance )* SEMI
//  {#instantiation = #(#[MODINSTANCETOP,"ModInstanceTop"], #instantiation);};

instantiation : identifier module_instance SEMI
   {#instantiation = #(#[MODINSTANCETOP,"ModInstanceTop"], #instantiation);};

parameter_value_assignment :
        POUND LPAREN expression ( COMMA expression )* RPAREN
        ;

module_instance :
        identifier list_of_module_connections
   {#module_instance = #(#[MODINSTANCE,"ModInstance"], #module_instance);};


//instance_name : name_of_instance
//     {#instance_name = #(#[INSTANCENAME, "InstanceName"], #instance_name);};

list_of_module_connections :
        LPAREN (module_port_connection)? (COMMA module_port_connection )* RPAREN
  {#list_of_module_connections = #(#[INSTANCEPORTLIST, "InstanceList"], #list_of_module_connections);};


module_port_connection :
        (port_connection | named_port_connection)
  {#module_port_connection = #(#[INSTANCEPORT, "InstancePort"], #module_port_connection);};


named_port_connection : DOT identifier LPAREN (var_ident)? RPAREN
  {#named_port_connection = #(#[PORTDOT, "PortDot"], #named_port_connection);};

port_connection : var_ident
    {#port_connection = #(#[PORTCON, "PortConnection"], #port_connection);};




//----------------------------------------------------------------------------
// Behavioral Statements
//----------------------------------------------------------------------------

initial_statement : ("initial" | "final") statement
	{#initial_statement = #(#[INITIALORFINAL, "InitialFinal"], #initial_statement);};

// Need to remove the always_head from this and probably move into the statement portion
always_construct : always_keyword always_head statement
      {#always_construct = #(#[ALWAYSTOP, "AlwaysTop"], #always_construct);};

always_keyword : "always" | "always_latch" | "always_comb" | "always_ff";

always_head : delay_or_event_controlq
      {#always_head = #(#[ALWAYSHEAD, "AlwaysHead"], #always_head);};




statement_or_null : (statement) => statement | SEMI;

statement_no_condition : ( 
							(task_enable) => task_enable |
							(total_assignment) => total_assignment |
                           case_statement |
                           (loop_statement) => loop_statement |
                           procedural_timing_control_statement |
                           wait_statement |
                           event_trigger |
                           seq_block |
                           par_block |
                           
                           //system_task_enable |
                           disable_statement |
                           procedural_continuous_assignment);

statement : (conditional_statement) => conditional_statement |
	statement_no_condition ;
		



assignment : lvalue assign_op expression
	 {#assignment = #(#[ASSIGNMENT, "Assignment"], #assignment);};


total_assignment : lvalue assign_op delay_or_event_controlq  expression SEMI
	 {#total_assignment = #(#[TOTALASSIGN, "TotalAssign"], #total_assignment);};

delay_or_event_controlq : ( options{greedy=true;}: delay_or_event_control )?
 	{#delay_or_event_controlq = #(#[DELAYOREVENTCONTROLQ, "delay_or_event_controlq"], #delay_or_event_controlq);};


	/*
blocking_assignment :
        lvalue ASSIGN ( delay_or_event_control )?  expression
        ;

non_blocking_assignment :
        lvalue LE ( delay_or_event_control )?  expression
        ;
      */

conditional_statement : unique_priority  ( condition_head 
	( options { warnWhenFollowAmbig = false; } : condition_else)* )
	{#conditional_statement = #(#[CONDITIONTOP, "ConditionTop"], #conditional_statement);};

condition_head : "if" parop statement_or_null
	{#condition_head = #(#[CONDITIONHEAD, "ConditionHead"], #condition_head);};

condition_else : "else" (condition_head | statement_no_condition)
	{#condition_else = #(#[CONDITIONELSE, "ConditionElse"], #condition_else);};

unique_priority : ("unique" | "priority")?
	{#unique_priority = #(#[UNIQUEPRIORITY, "unique_priority"], #unique_priority);};

matches : ("matches")?
	{#matches = #(#[MATCHES	, "matches"], #matches);};



case_statement : unique_priority case_keyword case_head matches case_list "endcase"
	    {#case_statement = #(#[CASETOP, "CaseTop"], #case_statement);};

case_head : cond_predicate
	    {#case_head = #(#[CASEHEAD, "CaseHead"], #case_head);};

case_list :  (case_item)+
	    {#case_list = #(#[CASELIST, "CaseList"], #case_list);};

case_item : case_normal | case_default;

cond_predicate : parop;


case_expression : expression ( COMMA expression )*
	{#case_expression = #(#[CASEEXPRESSION, "CaseExpression"], #case_expression);};

case_normal : case_expression COLON statement_or_null
	{#case_normal = #(#[CASENORMAL, "CaseNormal"], #case_normal);};

case_default :  "default" (COLON)? statement_or_null
	{#case_default = #(#[CASEDEFAULT, "CaseDefault"], #case_default);};
	
case_keyword : "case" | "casez" | "casex" ;




loop_statement :
        "forever" statement_or_null |
        "repeat" LPAREN expression RPAREN statement_or_null |
        "while" LPAREN expression RPAREN statement_or_null |
        "for" LPAREN assignment SEMI expression SEMI assignment RPAREN statement;
        
        

procedural_timing_control_statement :delay_or_event_control statement_or_null ;

wait_statement :"wait" LPAREN expression RPAREN statement_or_null
        {#wait_statement = #(#[WAITSTATEMENT, "wait_statement"], #wait_statement);};

event_trigger :
        TRIGGER name_of_event SEMI
        ;

disable_statement :"disable" identifier SEMI
        {#disable_statement = #(#[DISABLESTATEMENT, "disable_statement"], #disable_statement);};


seq_block_nameQ : ( COLON name_of_block  )?
	{#seq_block_nameQ = #(#[SEQBLOCKNAMEQ, "seq_block_nameQ"], #seq_block_nameQ);};

pre_block_nameQ : ( name_of_block COLON )?
	{#pre_block_nameQ = #(#[PREBLOCKNAMEQ, "pre_block_nameQ"], #pre_block_nameQ);};

seq_block : pre_block_nameQ "begin"  seq_block_nameQ state_list "end" seq_block_nameQ
        {#seq_block = #(#[BEGENDBLOCK, "BegEndBlock"], #seq_block);};

state_list : (statement)*
        {#state_list = #(#[STATELIST, "StateList"], #state_list);};

par_block :
        "fork"
       
        ( COLON name_of_block  )?
        (statement)*
	"join"
        ;

 /*
block_declaration :
       var_declaration
        ;
*/

task_enable : name_of_task ( LPAREN (expression)? (COMMA (expression)?)* RPAREN )? SEMI;

system_task_enable :
        identifier /*SYSTEM_TASK_NAME*/ ( LPAREN expression (COMMA (expression)?)* RPAREN )?
	SEMI
        ;

procedural_continuous_assignment :
        "assign" assignment SEMI |
        "deassign" lvalue SEMI |
        "force" assignment SEMI |
        "release" lvalue SEMI
        ;

delay_or_event_control : delay_control | event_control;

//----------------------------------------------------------------------------
// Specify Section
//----------------------------------------------------------------------------

//specify_block : "specify" (specify_item)* "endspecify"
specify_block : "specify" specify_entries "endspecify" 
        {#specify_block = #(#[SPECIFYBLOCK, "SpecifyBlock"], #specify_block);};

specify_entries :( ~("endspecify") )* ;

specify_item : (spec_param_declaration |
        (path_declaration) => path_declaration |
        system_timing_check)
        {#specify_item = #(#[SPECIFYITEM, "SpecifyItem"], #specify_item);};
        
        //| sdpd;

spec_param_declaration : "specparam" list_of_specparam_assignments SEMI
       {#spec_param_declaration = #(#[SPECPARAMDECLARATION, "SpecParamDeclaration"], #spec_param_declaration);};


list_of_specparam_assignments : specparam_assignment ( COMMA specparam_assignment )*;

specparam_assignment :
        identifier assign_op expression
        ;

path_declaration :
        ((simple_path_declaration) => simple_path_declaration SEMI |
        (level_sensitive_path_declaration) => level_sensitive_path_declaration SEMI |
        edge_sensitive_path_declaration SEMI)
       {#path_declaration = #(#[PATHDECLARATION, "PathDeclaration"], #path_declaration);};

level_sensitive_path_declaration : ("if" LPAREN expression RPAREN 
	(simple_path_declaration) => simple_path_declaration | 
			edge_sensitive_path_declaration)        
	{#level_sensitive_path_declaration = #(#[LEVELSENSITIVEPATHDECLARATION, "LevelSensitivePathDeclaration"], #level_sensitive_path_declaration);};


simple_path_declaration : ((parallel_path_description) =>
		parallel_path_description ASSIGN path_delay_value |
        full_path_descriptor ASSIGN path_delay_value)
       {#simple_path_declaration = #(#[SIMPLEPATHDECLARATION, "SimplePathDeclaration"], #simple_path_declaration);};


parallel_path_description : LPAREN specify_terminal_descriptor PPATH specify_terminal_descriptor RPAREN
      {#parallel_path_description = #(#[PARALLELPATHDECLARATION, "ParallelPathDeclaration"], #parallel_path_description);};


full_path_descriptor : LPAREN list_of_path_terminals  FPATH list_of_path_terminals RPAREN
      {#full_path_descriptor = #(#[FULLPATHDESCRIPTOR, "FullPathDescriptor"], #full_path_descriptor);};


list_of_path_terminals :
        specify_terminal_descriptor ( COMMA specify_terminal_descriptor )*
        ;

specify_terminal_descriptor :
	(identifier LBRACK expression COLON) =>
           identifier LBRACK expression COLON expression RBRACK |
        (identifier LBRACK) =>
           identifier LBRACK expression RBRACK |
         identifier
        ;

path_delay_value :
        (path_delay_expression) => path_delay_expression |
        LPAREN list_of_path_delay_expressions RPAREN
        ;

list_of_path_delay_expressions :
        path_delay_expression COMMA path_delay_expression
	  ( COMMA path_delay_expression
	    ( COMMA path_delay_expression COMMA
              path_delay_expression COMMA path_delay_expression )? )?
        ;

path_delay_expression :
        mintypmax_expression
        ;

system_timing_check :
        "$setup" LPAREN timing_check_event COMMA timing_check_event COMMA
            timing_check_limit ( COMMA notify_register )? RPAREN SEMI |
        "$hold" LPAREN timing_check_event COMMA timing_check_event COMMA
            timing_check_limit ( COMMA notify_register )? RPAREN SEMI |
        "$period" LPAREN controlled_timing_check_event COMMA
            timing_check_limit ( COMMA notify_register )? RPAREN SEMI |
        "$width" LPAREN controlled_timing_check_event COMMA
            timing_check_limit ( COMMA expression COMMA notify_register )?
	    RPAREN SEMI |
        "$skew" LPAREN timing_check_event COMMA timing_check_event COMMA
            timing_check_limit ( COMMA notify_register )? RPAREN SEMI |
        "$recovery" LPAREN controlled_timing_check_event COMMA
            timing_check_event COMMA timing_check_limit
            ( COMMA notify_register )? RPAREN SEMI |
        "$setuphold" LPAREN timing_check_event COMMA timing_check_event COMMA
            timing_check_limit COMMA timing_check_limit
            ( COMMA notify_register )? RPAREN SEMI
        ;

timing_check_event :
        (timing_check_event_control)? specify_terminal_descriptor
        ( "&&&" timing_check_condition )?
        ;

controlled_timing_check_event :
        timing_check_event_control specify_terminal_descriptor
        ( "&&&" timing_check_condition )?
        ;

timing_check_event_control :
        "posedge" |
        "negedge" |
        edge_control_specifier
        ;

edge_control_specifier :
        "edge" LBRACK edge_descriptor ( COMMA edge_descriptor )* RBRACK
        ;

   // Use semantic predicates to determine whether a matched
   // NUMBER or IDENTIFIER is a valid special value in the given context.
   // This kludge avoids having the special values in the Literals table,
   // thus avoiding a lexical conflict.
edge_descriptor : "0x" | "1x" | n:NUMBER
	{ n.getText()=="01" || n.getText()=="10"}?
      |
	i:IDENTIFIER
	{ i.getText()=="x1" || i.getText()=="x0"}?
	;

timing_check_condition :
        scalar_timing_check_condition
        ;
scalar_timing_check_condition :
        expression
        ;

timing_check_limit :
        expression
        ;

notify_register :
        name_of_register
        ;



	
	/*
	(parallel_level_sensitive_path_description) =>
	parallel_level_sensitive_path_description
	     ASSIGN path_delay_value SEMI
      |
	full_level_sensitive_path_description
	     ASSIGN path_delay_value SEMI
        ;

parallel_level_sensitive_path_description :
	   LPAREN specify_terminal_descriptor (polarity_operator)?
	          PPATH specify_terminal_descriptor RPAREN
	;

full_level_sensitive_path_description :
	   LPAREN list_of_path_terminals (polarity_operator)?
	          FPATH list_of_path_terminals RPAREN
	;*/



polarity_operator :
	PLUS |
	MINUS
	;

edge_sensitive_path_declaration : LPAREN (edge_identifier)? specify_terminal_descriptor ( PPATH | FPATH ) (LPAREN)? 
        ( (list_of_path_terminals) => list_of_path_terminals |
	            specify_terminal_descriptor )
	      (polarity_operator)? (COLON data_source_expression)?
	   (RPAREN)?
	RPAREN
	ASSIGN path_delay_value SEMI
    {#edge_sensitive_path_declaration = #(#[EDGESENSITIVEPATHDECLARATION, "EdgeSensitivePathDeclaration"], #edge_sensitive_path_declaration);};





data_source_expression :
        expression
        ;

edge_identifier :
        "posedge" |
        "negedge"
        ;

sdpd : "if" LPAREN expression RPAREN (simple_path_declaration) SEMI
	{#sdpd = #(#[SDPD, "Sdpd"], #sdpd);};


//----------------------------------------------------------------------------
// Expressions
//----------------------------------------------------------------------------

lvalue : var_ident; 
var_ident : name | concatenation;

// Increment or Decrement Operator Needs Support
inc_dec_expression : inc_or_dec_operator lvalue 
					 | lvalue inc_or_dec_operator ;

segment_range : range;
	

concatenation : LCURLY concat_expr RCURLY
      {#concatenation = #(#[CONCAT, "Concat"], #concatenation);};

concat_expr : (concat_number) => concat_number | concat_normal;
concat_normal : expression ( COMMA expression )*;
concat_number : expression LCURLY expression RCURLY;


mintypmax_expression :
        expression ( COLON expression COLON expression)?
        ;
        

exp11 : primary;
primary : (number) => number 
		| (inc_dec_expression) => inc_dec_expression 
		
		| STRING;
		
exp10 : exp11 | parop;
exp9  : exp10 | unop;
exp8 :  exp9 (  options{greedy=true;}: binop )* {#exp8 = #(#[BINOP, "BinOp"], #exp8);};
exp7 : exp8 ( questop )? {#exp7 = #(#[QUESTTOP, "QuestionTop"], #exp7);};
exp6 : exp7;

parop : LPAREN expression RPAREN {#parop = #(#[PAROP, "ParOp"], #parop);};
unop : unary_operator exp9 {#unop = #(#[UNOP, "UnOp"], #unop);};
binop : binary_operator exp9 {#binop = #(#[BINOPSMALL, "BinOpSmall"], #binop);};
questop : QUESTION exp7 COLON exp7 {#questop = #(#[QUESTIONSEGMENT, "QuestionSegment"], #questop);};


expression : exp6;
cast : "asdfaslkdjfa;sljdf;alskjfd;aslkjdf;ajlsdf";
/*
function_call :
        name_of_function LPAREN expression_list RPAREN |
        IDENTIFIER ( LPAREN expression_list RPAREN )?
        ;     
function_call_general : name_of_function LPAREN expression_list RPAREN
	{#function_call_general = #(#[FUNCTIONCALLGENERAL, "function_call_general"], #function_call_general);};

function_call_system  : SYSTEM_TASK_NAME ( LPAREN expression_list RPAREN )?
	{#function_call_system = #(#[FUNCTIONCALLSYSTEM, "function_call_system"], #function_call_system);};

expression_list :expression ( COMMA expression )*
	{#expression_list = #(#[EXPRESSIONLIST, "expression_list"], #expression_list);};        
*/


unary_operator :
        PLUS   |
        MINUS  |
        LNOT   |
        BNOT   |
        BAND   |
        RNAND  |
        BOR    |
        RNOR   |
        BXOR   |
        RXNOR
        ;


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
        RXNOR       // Hack to handle ^^ for Intel
        ;

inc_or_dec_operator : PLUSPLUS | MINMIN;

//----------------------------------------------------------------------------
// Identifiers
//----------------------------------------------------------------------------

name_of_module :            local_identifier ;
name_of_port :              local_identifier ;
name_of_variable :          local_identifier ;
name_of_UDP :               local_identifier ;
name_of_UDP_instance :      local_identifier ;
name_of_event :             local_identifier ;
name_of_task :              name ;
real_identifier :           identifier ;
name_of_memory :            local_identifier ;
net_identifier :            identifier ;
name_of_function :          identifier ;
specparam_identifier :      identifier ;
udp_name_of_port :          identifier ;
name_of_register :          local_identifier ;
name_of_gate_instance :     local_identifier ;
name_of_instance :          local_identifier ;
name_of_block :             local_identifier ;
output_terminal_name :      local_identifier ;


//----------------------------------------------------------------------------
// General 
//----------------------------------------------------------------------------

name_dot_colon_pound : identifier (name_dot | name_colon | name_pound_expression)*
	{#name_dot_colon_pound = #(#[NAME,"name_dot_colon_pound"],#name_dot_colon_pound);};

name_dot_colon_only : identifier ( options{greedy=true;}: (name_colon | name_dot))*
	{#name_dot_colon_only = #(#[NAME,"name_dot_colon_only"],#name_dot_colon_only);};

name_dot_colon_range : identifier ( options{greedy=true;}: (name_colon | name_dot | name_range))*
	{#name_dot_colon_range = #(#[NAME,"name_dot_colon_range"],#name_dot_colon_range);};

name_dot_only : identifier (name_dot)*
 	{#name_dot_only = #(#[NAME,"name_dot_only"],#name_dot_only);};

name_no_expression
  : (( identifier  ) //: (( identifier | STRING_LITERAL )
    ( options{greedy=true;}:
      (
          name_dot 
        | name_range
        | name_colon
      )
    )*)
    {#name_no_expression = #(#[NAME,"Name"],#name_no_expression);};

name
  : (( identifier  ) //: (( identifier | STRING_LITERAL )
    ( options{greedy=true;}:
      (
          name_dot 
        | name_range
        | name_expression
        | name_pound_expression
        | name_colon
        
      )
    )*)
    {#name = #(#[NAME,"Name"],#name);};

name_dot : DOT local_identifier
	{#name_dot = #(#[NAMEDOT, "NameDot"], #name_dot);};

name_colon : DOUBLECOLON local_identifier
	{#name_colon = #(#[NAMECOLON, "NameColon"], #name_colon);};

name_range : segment_range
	{#name_range = #(#[NAMERANGE, "NameRange"], #name_range);};
	
name_expression : LPAREN (expression_or_dot_expression)? ( COMMA (expression_or_dot_expression)? )* RPAREN
	{#name_expression = #(#[NAMEEXPRESSION, "NameExpression"], #name_expression);};
	
name_pound_expression : POUND LPAREN (expression_or_int)? ( COMMA expression_or_int )* RPAREN
	{#name_pound_expression = #(#[NAMEPOUNDEXPRESSION, "NamePoundExpression"], #name_pound_expression);};	

expression_or_int : ("int") => "int" | expression;
expression_or_dot_expression : expression | DOT identifier LPAREN expression RPAREN;

ident_range : ((identifier segment_range) =>
        identifier segment_range | identifier)
        {#ident_range = #(#[IDENTRANGE, "IdentRange"], #ident_range);};

local_identifier : identifier;
identifier : IDENTIFIER | ESCAPED_IDENTIFIER | DOLLAR_IDENTIFIER;

/*
identifier_path : local_identifier ( name_dot )*
	{#identifier_path = #(#[IDENTIFIERPATH, "IdentifierPath"], #identifier_path);};
*/



delay_control : (delay_number) => delay_number |
        POUND LPAREN mintypmax_expression RPAREN;

delay_number : POUND  (number | identifier) (options{greedy=true;} :time_unit)?;

event_control : AT name | AT LPAREN event_expression RPAREN
     {#event_control = #(#[EVENTCONTROL, "EventControl"], #event_control);};

event_expression :STAR | (sub_event_expression ( ("or" | COMMA) sub_event_expression )*)
    {#event_expression = #(#[EVENTEXPRESSION, "EventExpression"], #event_expression);};

sub_event_expression : ((expression | "posedge" expression | "negedge" expression)) ("iff" expression)?
    {#sub_event_expression = #(#[EVENTEXPRESSIONSUB, "EventExpressionSub"], #sub_event_expression);};


//----------------------------------------------------------------------------
// Compiler directives
//----------------------------------------------------------------------------

context_clause : ( options{greedy=true;} : context_item)*
    {#context_clause = #(#[CONTEXTCLAUSE,"ContextClause"],#context_clause);};

context_item : (define_directive |  include_directive | ifdef_directive | timescale_directive)
    {#context_item = #(#[CONTEXTITEM,"ContextItem"],#context_item);};

define_directive : "`define" identifier ( options{greedy=true;}: expression)?
    {#define_directive = #(#[DEFINEDIRECTIVE,"DefineDirective"],#define_directive);};



include_directive : "`include" ( name | STRING ) 
    {#include_directive = #(#[INCLUDEDIRECTIVE,"IncludeDirective"],#include_directive);};
    
ifdef_directive : ifdef_head ifdef_cond "`endif"
	{#ifdef_directive = #(#[IFDEFDIRECTIVE,"ifdef_directive"],#ifdef_directive);};
	
ifdef_head :  ("`ifdef" | "`ifndef") identifier (module_item)*
	{#ifdef_head = #(#[IFDEFHEAD,"ifdef_head"],#ifdef_head);};
	
ifdef_cond :  ("`else" (module_item)*)?
	{#ifdef_cond = #(#[IFDEFCOND,"ifdef_cond"],#ifdef_cond);};

timescale_directive : TICKTIMESCALE number identifier DIV number identifier
	{#timescale_directive = #(#[TIMESCALEDIRECTIVE,"timescale_directive"],#timescale_directive);};

assign_op : ASSIGN | LE;

//time_units : ("us" | "ms" | "ns" | "ps");
time_unit : "s" | "ms" | "us" | "ns" | "ps" | "fs" | "step";

number : NUMBER /*| DEFINE NUMBER*/;



//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
// The Verilog scanner
//----------------------------------------------------------------------------

class VerilogLexer extends Lexer;

options {
    testLiterals = false;   // don't automatically test for literals
    k = 3;                  // 3 characters of lookahead
    charVocabulary = '\3'..'\377';
	//charVocabulary = '\u0000'..'\uFFFE';
}

 {

}



  // Operators
TICKTICK : "``";
TICKQUOTE : "`\"";
AT	    : "@"   ;
DOUBLECOLON : "::"  ;
COLON	    : ":"   ;
COMMA	    : ","   ;
DOT	    : "."   ;
ASSIGN	    : "="   ;
MINUS	    : "-"   ;
LBRACK	    : "["   ;
RBRACK	    : "]"   ;
LCURLY	    : "{"   ;
RCURLY	    : "}"   ;
LPAREN	    : "("   ;
RPAREN	    : ")"   ;
POUND	    : "#"   ;
QUESTION    : "?"   ;
SEMI	    : ";"   ;
PLUS        : "+"   ;
LNOT        : "!"   ;
BNOT        : "~"   ;
BAND        : "&"   ;
RNAND       : "~&"  ;
BOR         : "|"   ;
RNOR        : "~|"  ;
BXOR        : "^"  ;
RXNOR       : "~^" | "^~"  | "^^"; 
STAR        : "*"   ;
DIV         : "/"   ;
MOD         : "%"   ;
EQUAL       : "=="  ;
NOT_EQ      : "!="  ;
NOT_EQ_CASE : "!==" ;
EQ_CASE     : "===" ; 
LAND        : "&&"  ;
LOR         : "||"  ;
LT_         : "<"   ;
LE          : "<="  ;
GT          : ">"   ;
GE          : ">="  ;
SR          : ">>"  ;
SL          : "<<"  ;
SRS         : ">>>" ;
SLS         : "<<<" ;
POW         : "**"  ;
TRIGGER     : "->"  ;
PPATH       : "=>"  ;
FPATH       : "*>"  ;
CASTPAREN     : "'("  ;
CASTCURLY    : "'{"  ;
DOLLAR       : "$";
// IncDecOp 
PLUSPLUS : "++";
MINMIN   : "--";

    // an identifier.  Note that testLiterals is set to true!  This means
    // that after we match the rule, we look in the Literals table to see
    // if it's a literal or really an identifer.
IDENTIFIER
      options {testLiterals=true;}
    :
        ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'$'|'0'..'9')*;

DOLLAR_IDENTIFIER
      options {testLiterals=true;}
    :
        ('$') ('a'..'z'|'A'..'Z'|'_'|'$'|'0'..'9')+;
        
//ESCAPED_IDENTIFIER : '\\'! (~ '\040')+ ('\040'|'\t'|'\n')!;
ESCAPE_NEWLINE : '\\' (('\r' '\n') | '\n');
ESCAPED_IDENTIFIER : '\\'! (~ ('\040'|'\r'|'\n'))+ ('\040'|'\t'|'\n')!;

// string literals
STRING :	'"' (ESCAPE_NEWLINE|ESC|~('"'|'\\'|'\n'|'\r'))* '"';
//STRING :	'"' (~('"'))* '"';



// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|   '$'
		|   '%'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
		|	'0'..'3'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
			)?
		)
	;
	
	// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;

/*
    // string literals
STRING :
        '\"' (~('\"'|'\n'))* '\"'
        ;
     
        
STRING
  : '\"' ( 'a'..'z' | '0'..'9' | '&' | '\'' | '(' | ')'
    | '+' | ',' | '-' | '.' | '/' | ':' | ';' | '<' | '=' | '>' | '|'
    | ' ' | OTHER_SPECIAL_CHARACTER | '\\' | "\"\"" | '*' 
    | '#' | '[' | ']' | '_' )* '\"'
  ;
*/

    // "compiler" define/macro.
DEFINE options {testLiterals=true;} :
	'`' IDENTIFIER;



   // a dummy rule to force vocabulary to be all characters (except special
   //   ones that ANTLR uses internally (0 to 2)
protected
VOCAB :
        '\3'..'\177'
        ;

   // a numeric literal
NUMBER :
	( (SIZE)? BASE ) => SIZED_NUMBER |
	UNSIZED_NUMBER
	;

protected
SIZED_NUMBER :
	(SIZE)? BASE (SIZED_DIGIT (SIZED_DIGIT | '_')*)?
	;

protected
SIZE : (DIGIT)+;

protected
BASE :
	'\'' ( '0' | '1' | 'x' | 'X' | 'Z' | 'z' | 'd' | 'D' | 'h' | 'H' | 'o' | 'O' | 'b' | 'B' | "sd" | "sD" | "sh" | "sH" | "so" | "sO" | "sb" | "sB" )
	;

protected
SIZED_DIGIT : 
	DIGIT | HEXDIGIT | 'x' | 'X' | 'z' | 'Z' | '?' | ' ';

protected
UNSIZED_NUMBER :
	DIGIT (DIGIT | '_')* ( '.' (DIGIT | '_')* )? (EXPONENT)?
        ;

protected
DIGIT :
        ('0'..'9')
        ;

protected
HEXDIGIT :
        ('A'..'F'|'a'..'f')
        ;

protected
EXPONENT :
        ('e'|'E') ('+'|'-')? ('0'..'9')+
        ;

    // Whitespace -- ignored
WS_ :
        (  ' '
         | '\t'
         | '\f'
        );

NEWLINE_ : (  "\r\n"  // Evil DOS
            | '\r'    // Macintosh
            | '\n'    // Unix (the right way)
           )
            { newline(); };

    // Single-line comments
SL_COMMENT : "//" ( ~'\n' )*;

/*		"//"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
		{newline();};
*/
 




    // multiple-line comments
ML_COMMENT
    :   "/*" 
    
    	( options {
				generateAmbigWarnings=false;
			} : 
		{ LA(2)!='/' }? '*'
		|	'\r' '\n'		{newline();}
		|	'\r'			{newline();}
		|	'\n'			{newline();}
		|	~('*'|'\n'|'\r')
		| OTHER_SPECIAL_CHARACTER
		)*
		"*/";


protected
OTHER_SPECIAL_CHARACTER : '\u00a0'..'\u00ff' ;

