/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.templates;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;

import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.editors.templates.SourceTemplateProcessor;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.verilog.editor.VerilogScanner;

public class VhdlTemplateProcessor extends SourceTemplateProcessor{

	public static String[] TICK_VALUES = new String[] {"DELAYED","STABLE","QUIET","LAST_VALUE",
		"LAST_EVENT","LAST_ACTIVE","EVENT","TRANSACTION","BASE","LEFT","RIGHT","HIGH","LOW","POS",
		"VAL","SUCC","PRED","LEFTOF","RIGHTOF","LENGTH","RANGE","REVERSE_RANGE"
	};
	
	static {
		Arrays.sort(TICK_VALUES);
	}
	
	
	public VhdlTemplateProcessor(GeneralEditor editor) {
		super((SourceEditor)editor);
	}
	
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		return VhdlTemplateAccess.getDefault().getContextTypeRegistry().getContextType(VhdlTemplateContextType.VHDL_ID);
	}
	
	protected Template[] getTemplates() {
    	Template[] lits = this.createLiteralTemplates(VerilogScanner.keywords, VhdlTemplateContextType.VHDL_ID );
    	Template[] temps =  VhdlTemplateAccess.getDefault().getTemplateStore().getTemplates(VhdlTemplateContextType.VHDL_ID);
    	//return this.mergeTemplates(lits, temps);
    	return temps;
	}

	@Override
	protected String getContextTypeId(ITextViewer viewer, IRegion region) {
		return VhdlTemplateContextType.VHDL_ID;
	}
	
	@Override
	protected int getLanguageType() {
		return TemplateGenerator.TEMPLATE_VHDL;
	}

	@Override
	public ICompletionProposal[] createTickProposals(NewReplaceValue rep) {
		Arrays.sort(TICK_VALUES);
		ArrayList props = new ArrayList();
		for (String str : TICK_VALUES) {
			if (str.startsWith(rep.getPostfix().toUpperCase())) {
				TickCompletionProposal comp = new TickCompletionProposal(str,rep.getDpos()+1,rep.getPostfix().length());
				props.add(comp);
			}
		}
		return (ICompletionProposal[]) props.toArray(new ICompletionProposal[props.size()]);
			
	}
	
	public char[] getCompletionProposalAutoActivationCharacters() {
		String tick = "'";

		char[] chars = new char[] {'.','(',tick.toCharArray()[0]};
		
		
		return chars;
	}

	@Override
	public ICompletionProposal[] createVerilogTickProposals(NewReplaceValue rep, ParseContext context,ITextViewer viewer) {
		return new ICompletionProposal[0];
	}

	

}
