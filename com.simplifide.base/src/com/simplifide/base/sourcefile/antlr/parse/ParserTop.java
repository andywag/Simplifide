/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.parse;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.grammar.BaseLexer;
import com.simplifide.base.sourcefile.antlr.grammar.BaseParser;
import com.simplifide.base.sourcefile.antlr.node.RootASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.stream.TemplateHandler;
import com.simplifide.base.sourcefile.antlr.stream.TemplateList;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.sourcefile.antlr.walk.PositionWalker;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 20, 2004
 * Time: 8:54:05 AM
 * To change this template use File | Settings | File Templates.
 * 
 */

public abstract class ParserTop
{

   
    public ParserTop() {}
    
    /** Create and return the template list
     * 
     * @param reader
     * @param desc
     * @return
     */
    public TemplateList createTemplateList(Reader reader, ParseDescriptor desc) {
    	BaseLexer lex = this.createLexer(reader);
    	PositionStream filt = this.createStream( null); // This is questionable
    	TemplateHandler handler = this.createTemplateHandler(filt, desc);
    	return handler.createTemplateList(lex);
        
    }

    public String expandMacros(Reader reader, ParseDescriptor desc) {
		ParseContext context = desc.createContext();
        PositionStream stream = this.tokenizeText(reader,desc.getname(),context,true);
		StringBuilder builder = new StringBuilder();
        List<TopASTToken> tokens = stream.getTokenList();
        tokens.remove(tokens.size()-1);
		for (TopASTToken token : tokens) {
			builder.append(token.getText());
		}
		String ret = builder.toString();
		return ret;
    }
    
    /**
     * Does the whole build operations for this sourcefile
     * @param reader 
     * @param desc 
     */
    public void compositeBuild(Reader reader, ParseDescriptor desc) {
        // Tokenize the text
    	ParseContext context = desc.createContext();
    	
        PositionStream stream = this.tokenizeText(reader,desc.getname(),context,false);
        desc.setStream(stream);
        // Parse the Stream
        boolean failed = this.parseText(desc);    
        // Generate the Module
        if (!failed) {
        	this.generateModule(desc, context);
        }
        
    }
    /**
     * 
     * @param read 
     * @return 
     */
    public PositionStream tokenizeText(Reader read, String name, ParseContext context, boolean space)
    {
        BaseLexer lex = this.createLexer(read);
        PositionStream filt = this.createStream(context);
        if (context != null && context.getDescriptor() != null) {
        	context.getDescriptor().clearDefines();
        }
        filt.setPreserveSpace(space);
        filt.createArrayList(lex,name);
        return filt;
    }
    
    /**
     * 
     * @param desc 
     */
    public boolean parseText(ParseDescriptor desc)
    {
    	
        boolean failed = this.parseReal(desc);  
        if (!failed) {
        	PositionWalker.walkIndex(desc.getRoot());
        }
        return failed;

    }
    
    /**
     * 
     * @param uparse 
     * @param context 
     */
    public  void generateModule(ParseDescriptor uparse, ParseContext context)
    {
    	
        uparse.getModule().clearReferences();
        if (uparse.getRoot() != null)
        {
            uparse.getRoot().generateModule(context);
            uparse.setErrorList(context.getErrorList());
            uparse.getModule().updateHdlDoc(new ModuleObject());
            if (uparse.getMode() == BuildInterface.BUILD_FIND_USAGES) {
            	ParseContextUsages contextUse = (ParseContextUsages) context;
            	uparse.setFindUsages(contextUse.getFindList());
            }
        }
        uparse.storeLastModule();
    }
    
    
    /**
     * 
     * @param desc 
     */
    public boolean parseReal(ParseDescriptor desc)
    {
        BaseParser parse = this.createParser(desc.getStream()); 
        parse.setASTFactory(this.createNodeFactory());
      try {
            parse.source_text();
            desc.setErrored(parse.syntaxError);
            
            desc.setRoot((TopASTNode)parse.getAST());
            if (!parse.syntaxError) {
            	desc.setOldRoot((TopASTNode)parse.getAST());
            }
            
            return parse.syntaxError;
           

        } catch (Exception e) {
            desc.setErrored(true);
            return true;
           
        }
        
    }
    
    

    /**
     * 
     * @param context 
     * @param text 
     * @param type 
     * @return 
     */
    public  ModuleObject findNameObject(ParseContext context, String text,int type )
    {
        RootASTNode root = parseName(text);
        if (root == null) return null;
        ModuleObject obj = root.generateNameModule(context,type);
        return obj;
    }

    private  RootASTNode parseName(String text)
    {

        StringReader sread = new StringReader(text);
        PositionStream stream = tokenizeText(sread,"",null,false); // Tokenize Text
        
        
        BaseParser parse = this.createParser(stream); // Create Parser
        parse.setASTFactory(this.createNodeFactory());
        try {        
            parse.source_text_name();
        } catch (TokenStreamException ex) {
            BaseLog.logError(ex);
        } catch (RecognitionException ex) {
            BaseLog.logError(ex);
        }
        return (RootASTNode) parse.getAST();
    }
   
    
  
    protected abstract BaseLexer createLexer(Reader read);
    protected abstract BaseParser createParser(PositionStream stream); 
    protected abstract antlr.ASTFactory createNodeFactory();
    protected abstract PositionStream createStream(ParseContext context);
	protected abstract TemplateHandler createTemplateHandler(PositionStream stream, ParseDescriptor context);
    

   
    


  

   
     
   

   

    



}
