/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.indent;

import com.simplifide.core.editors.indent.SourceIndentStrategy;


public class VerilogIndentStrategy extends SourceIndentStrategy {

    private static String[] IND_INDENT_STRING = { "beginaasdfasdf"};

    private static String[] IND_DEDENT_STRING = { "end" };

    private static String[] IND_BOTH_STRING = { "else" };

    
    
    // This is really kind of a pain to handle
    
    
    public VerilogIndentStrategy() {
    }

    @Override
    public String[] getBothStrings() {
       return IND_BOTH_STRING;
    }

    @Override
    public String[] getDedentStrings() {
        return IND_DEDENT_STRING;
    }

    @Override
    public String[] getIndentStrings() {
        return IND_INDENT_STRING;
    }

    

    

}
