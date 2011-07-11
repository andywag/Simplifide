/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.base;




import java.io.Reader;

import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.grammar.BaseLexer;
import com.simplifide.base.sourcefile.antlr.grammar.BaseParser;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.stream.TemplateHandler;
import com.simplifide.base.vhdl.parse.VhdlFactory;
import com.simplifide.base.vhdl.parse.grammar.VhdlLexer;
import com.simplifide.base.vhdl.parse.grammar.VhdlParser;







/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 1:45:44 PM
 * To change this template use File | Settings | File Templates.
 */


public class VhdlParserTop extends ParserTop
{
    public VhdlParserTop() {}


    protected BaseLexer createLexer(Reader read){
        return new VhdlLexer(read);
    }

    protected BaseParser createParser(PositionStream stream) {
        return new VhdlParser(stream); 
    }
    
     protected antlr.ASTFactory createNodeFactory() {
        return new VhdlFactory(new java.util.Hashtable());
     }
     
     protected  PositionStream createStream(ParseContext context){
        return new VhdlPositionStream(context);
     }


	@Override
	protected TemplateHandler createTemplateHandler(PositionStream stream,
			ParseDescriptor desc) {
		return new VhdlTemplateHandler(stream,desc);
	}
   



}
