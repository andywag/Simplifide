/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.templates;

import java.util.ArrayList;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.project.define.DefineHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.template.GenerateTemplateContext;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.editors.templates.BaseTemplate;
import com.simplifide.core.editors.templates.MacroTemplateProposal;
import com.simplifide.core.editors.templates.SourceTemplateProcessor;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.resources.BasicIconManager;
import com.simplifide.core.verilog.editor.VerilogScanner;

public class VerilogTemplateProcessor extends SourceTemplateProcessor{

	public VerilogTemplateProcessor(GeneralEditor editor) {
		super((SourceEditor)editor);
		
		
	}
        protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
                return VerilogTemplateAccess.getDefault().getContextTypeRegistry().getContextType(VerilogTemplateContextType.VERILOG_ID);
        }
        
        protected Template[] getTemplates() {
        	Template[] lits = this.createLiteralTemplates(VerilogScanner.keywords, VerilogTemplateContextType.VERILOG_ID);
        	Template[] temps =  VerilogTemplateAccess.getDefault().getTemplateStore().getTemplates(VerilogTemplateContextType.VERILOG_ID);
        	return this.mergeTemplates(lits, temps);
        }

        @Override
        protected String getContextTypeId(ITextViewer viewer, IRegion region) {
        	return VerilogTemplateContextType.VERILOG_ID;
        }
		@Override
		protected int getLanguageType() {
			return TemplateGenerator.TEMPLATE_VERILOG;
		}
		
		@Override
		public ICompletionProposal[] createTickProposals(NewReplaceValue rep) {
			return new ICompletionProposal[0];
		}
		
		
		protected ICompletionProposal[] createBasicCompletionProposal(ReferenceItem item, NewReplaceValue value, ITextViewer viewer) {
			return new ICompletionProposal[] {new VerilogCompletionProposal(item,value,this.getLanguageType())};
		}
		
		protected BaseTemplate createBaseTemplate(FunctionDeclaration dec, NewReplaceValue value) {
			String prefix = value.getVerilogReplacePrefix();
			BaseTemplate temp = new BaseTemplate(dec,this.getContextTypeId(null, null),prefix);
			return temp;
		}
		
		private TemplateProposal createVerilogTickProposal(ReferenceItem ref, ITextViewer viewer, Template template, NewReplaceValue repval) {
			IRegion region = new Region(repval.getSpos(),repval.getLength());
			TemplateContextType contextType = this.getContextType(null, null);
			GenerateTemplateContext docContext = new GenerateTemplateContext(contextType, viewer.getDocument(), repval.getSpos(), repval.getLength());
			Image tempImage = BasicIconManager.getRealImage(BasicIconManager.MAIN_FUNCTION_DECLARATION);
			TemplateProposal prop = new MacroTemplateProposal(ref,template,docContext,region,tempImage,this.getLanguageType());
			
			return prop;
		} 
		
		public ICompletionProposal[] createVerilogTickProposals(NewReplaceValue rep,
										ParseContext context,
										ITextViewer viewer) {
			EclipseSuite suite = (EclipseSuite) context.getRefHandler().getGlobalReference().getObject();
			DefineHolder holder = suite.getDefineHolder();
			//ICompletionProposal[] props = new ICompletionProposal[holder.getnumber()];
			ArrayList<ICompletionProposal> props = new ArrayList<ICompletionProposal>();
			for (ReferenceItem ref : holder.getGenericSelfList()) {
				BaseTemplate temp = new BaseTemplate((TemplateCompletionInterface)ref.getObject(),this.getContextTypeId(null, null));
				if (ref.getname().startsWith(rep.getPostfix())) {
					props.add(this.createVerilogTickProposal(ref,viewer,temp, rep));
				}
			}
			return props.toArray(new ICompletionProposal[props.size()]);
			
			
		}
			
	
}
