/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/

package com.simplifide.base.verilog.parse.base;

import java.io.Reader;

import antlr.ASTFactory;

import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.grammar.BaseLexer;
import com.simplifide.base.sourcefile.antlr.grammar.BaseParser;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.stream.TemplateHandler;
import com.simplifide.base.verilog.parse.VerilogFactory;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogLexer;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogParser;












/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 1:45:44 PM
 * To change this template use File | Settings | File Templates.
 */


public class VerilogParserTop extends ParserTop
{
   
    public VerilogParserTop() {}


    protected BaseLexer createLexer(Reader read) {
        return new SystemVerilogLexer(read);
    }

    protected BaseParser createParser(PositionStream stream){
        return new SystemVerilogParser(stream); 
    }
     
    protected  PositionStream createStream( ParseContext context){
        return new SystemVerilogPositionStream(context);
    }

    protected ASTFactory createNodeFactory() {
        return new VerilogFactory();
    }


	@Override
	protected TemplateHandler createTemplateHandler(PositionStream stream,
			ParseDescriptor desc) {
		return new VerilogTemplateHandler(stream,desc);
	}
   



}
