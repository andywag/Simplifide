

header{package com.simplifide.base.verilog.parse.grammar.verilog;


import com.simplifide.base.sourcefile.antlr.tok.*;


}

class PreParser extends Parser;
options {
    k = 2;                        // two token lookahead
    buildAST = true;
    exportVocab = PreProc;
}


tokens {
    
	

}

{
 

}



//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
// The Verilog scanner
//----------------------------------------------------------------------------

class PreLexer extends Lexer;

options {
    testLiterals = false;   // don't automatically test for literals
    k = 3;                  // 3 characters of lookahead
}

 {

}


    // an identifier.  Note that testLiterals is set to true!  This means
    // that after we match the rule, we look in the Literals table to see
    // if it's a literal or really an identifer.
IDENTIFIER
      options {testLiterals=true;}
    :
        ('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'$'|'0'..'9')*
        ;


ESCAPED_IDENTIFIER :
        '\\'! (~ '\040')+ ('\040'|'\t'|'\n')!
        ;


    // string literals
STRING :
        '"' (~('"'|'\n'))* '"'
        ;


   // a dummy rule to force vocabulary to be all characters (except special
   //   ones that ANTLR uses internally (0 to 2)
protected
VOCAB :
        '\3'..'\177'
        ;

   // a numeric literal
NUMBER :
	( (SIZE)? BASE SIZED_DIGIT ) => SIZED_NUMBER |
	UNSIZED_NUMBER
	;

protected
SIZED_NUMBER :
	(SIZE)? BASE SIZED_DIGIT (SIZED_DIGIT | '_')*
	;

protected
SIZE : (DIGIT)+;

protected
BASE :
	'\'' ( 'd' | 'D' | 'h' | 'H' | 'o' | 'O' | 'b' | 'B' )
	;

protected
SIZED_DIGIT :
	DIGIT | HEXDIGIT | 'x' | 'X' | 'z' | 'Z' | '?'
	;

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
              // handle newlines
         | (  "\r\n"  // Evil DOS
            | '\r'    // Macintosh
            | '\n'    // Unix (the right way)
           )
            { newline(); }
        )

        ;

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
		)*
		"*/";

