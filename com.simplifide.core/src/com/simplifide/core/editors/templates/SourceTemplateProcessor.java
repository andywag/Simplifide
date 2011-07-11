/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.templates;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.interfac.InterfaceInstanceModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.template.GenerateTemplateContext;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.editors.completion.SourceCompletionProposal;
import com.simplifide.core.editors.completion.SourceContentProcessor;
import com.simplifide.core.editors.completion.SourceTemplateProposal;
import com.simplifide.core.resources.BasicIconManager;
import com.simplifide.core.vhdl.editor.VhdlEditor;

public abstract class SourceTemplateProcessor extends SourceContentProcessor{

	public SourceTemplateProcessor(SourceEditor editor) {
		super(editor);
	}
	
	private boolean compareTemplate(Template template, String prefix) {
		if (template == null) return false;
		String tempName = template.getName();
		if (tempName.length() >= prefix.length()) {
			if (tempName.startsWith(prefix)) return true;
		}
		return false;
	}
	
	private TemplateProposal createTemplateProposal(ModuleObject obj, ITextViewer viewer, Template template, NewReplaceValue repval) {
		IRegion region = new Region(repval.getSpos(),repval.getLength());
		TemplateContextType contextType = this.getContextType(null, null);
		GenerateTemplateContext docContext = new GenerateTemplateContext(contextType, viewer.getDocument(), repval.getSpos(), repval.getLength());
		Image tempImage = BasicIconManager.getRealImage(BasicIconManager.MAIN_FORM);
		TemplateProposal prop = new SourceTemplateProposal(obj,template,docContext,region,tempImage);
		return prop;
	} 
	
	protected TemplateProposal createFunctionProposal(ReferenceItem ref, ITextViewer viewer, Template template, NewReplaceValue repval) {
		IRegion region = new Region(repval.getSpos(),repval.getLength());
		TemplateContextType contextType = this.getContextType(null, null);
		GenerateTemplateContext docContext = new GenerateTemplateContext(contextType, viewer.getDocument(), repval.getSpos(), repval.getLength());
		Image tempImage = BasicIconManager.getRealImage(BasicIconManager.MAIN_FUNCTION_DECLARATION);
		TemplateProposal prop = new FunctionTemplateProposal(ref,template,docContext,region,tempImage,this.getLanguageType());
		
		return prop;
	} 
	
	protected BaseTemplate createBaseTemplate(FunctionDeclaration dec, NewReplaceValue value) {
		BaseTemplate temp = new BaseTemplate(dec,this.getContextTypeId(null, null));
		return temp;
	}
	
	protected ICompletionProposal[] createFunctionProposal(FunctionHolder<InstanceFunction> holder, NewReplaceValue value, ITextViewer viewer) {
		ArrayList<ICompletionProposal> prop = new ArrayList<ICompletionProposal>();
		for (InstanceFunction inst : holder.getRealSelfList()) {
			FunctionDeclaration dec = null;
			if (inst.getHead() != null) {
				dec = inst.getHead().getObject().getDeclarationRef().getObject();
			}
			else if (inst.getBody() != null) {
				dec = inst.getBody().getObject().getDeclarationRef().getObject();
			}
			else {
				continue;
			}
			
			BaseTemplate temp = this.createBaseTemplate(dec,value);
			prop.add(this.createFunctionProposal(inst.createReferenceItem(),viewer, temp, value));
		}
		return prop.toArray(new ICompletionProposal[prop.size()]);
	}	
	
	protected ICompletionProposal[] createBasicCompletionProposal(ReferenceItem item, NewReplaceValue value, ITextViewer viewer) {
		return new ICompletionProposal[] {new SourceCompletionProposal(item,value,this.getLanguageType())};
	}
	
	
	private ICompletionProposal[] createInstanceCompletionProposal(ReferenceItem item, NewReplaceValue value, ITextViewer viewer) {
		try {
			if (!HardwareChecker.isRefactoringEnabled()) {
				return this.createBasicCompletionProposal(item, value, viewer);
			}
			else {
				IRegion region = new Region(value.getSpos(),value.getLength());
				boolean vhdl = (this.getEditor() instanceof VhdlEditor);
				TemplateContextType contextType = this.getContextType(null, null);
				GenerateTemplateContext docContext = new GenerateTemplateContext(this.getContextType(viewer, region), viewer.getDocument(), value.getSpos(), value.getLength());
				ICompletionProposal prop = InstanceTemplateProposal.createInstanceProposal((InstanceModule)item.getObject(), viewer, value, 
						docContext, vhdl);
				//InstanceCompletionProposal prop = new InstanceCompletionProposal(item,value,this.getLanguageType(),this.getSourceEditor());	
				return new ICompletionProposal[] {prop};
			}
		}
		catch (Exception e) {
			return new ICompletionProposal[0];
		}
		
	}
	/** Converts the reference item to a completion proposal */
	protected ICompletionProposal[] createCompletionProposal(ReferenceItem item, NewReplaceValue value, ITextViewer viewer) {
		ModuleObject object = item.getObject();
		
		if (object instanceof FunctionHolder) {
			return this.createFunctionProposal((FunctionHolder<InstanceFunction>)object, value, viewer);
		}
		else if (object instanceof InstanceModule 
				&& !(object instanceof InterfaceInstanceModule)
				&& !(object instanceof ClassInstanceModule)) { 
			return this.createInstanceCompletionProposal(object.createReferenceItem(), value, viewer);
		}
		else if (object instanceof EntityHolder) {
			ReferenceItem<InstanceModule> imod = ((EntityHolder)object).getInstanceModRef();
			return this.createInstanceCompletionProposal(imod, value, viewer);

		}
		else if (object instanceof TemplateCompletionInterface) {
			TemplateCompletionInterface comp = (TemplateCompletionInterface) object;
			BaseTemplate template = new BaseTemplate(comp,this.getContextTypeId(null, null));
			return new ICompletionProposal[] {this.createTemplateProposal(null,viewer, template, value)};
		}
		else if (object instanceof InstancePackage) {
			if (object.getname().endsWith("_Context")) {
				return new ICompletionProposal[0];
			}
		}
	
		return this.createBasicCompletionProposal(item, value, viewer);
	}
	
	/** Loads up the template proposals from ... */
	private ICompletionProposal[] createTemplateProposals(ITextViewer viewer, int offset, NewReplaceValue rep) {
		if (rep.getType() == NewReplaceValue.TYPE_NONE) {
			Template[] templates = this.getTemplates();
			ArrayList<ICompletionProposal> propList = new ArrayList<ICompletionProposal>();
			for (int i=0;i<templates.length;i++) {
				if (this.compareTemplate(templates[i],rep.getPostfix())) { // Filters the templates based on prefix
					 TemplateProposal prop = this.createTemplateProposal(null,viewer,templates[i],rep);
					 propList.add(prop);
				}
			}
			return propList.toArray(new ICompletionProposal[propList.size()]);
		}
		else return new ICompletionProposal[0];
	}
	
	
	
	public abstract ICompletionProposal[] createTickProposals(NewReplaceValue rep);
	public abstract ICompletionProposal[] createVerilogTickProposals(NewReplaceValue rep, 
																	ParseContext context,
																	ITextViewer viewer);

	
	/** Main place where completion proposals are loaded */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		// Parse the string to determine the values to be replaced
		NewReplaceValue rep = NewReplaceValue.createReplaceValue2(viewer.getDocument(),offset);
		// Create the Tick Proposals (Only for VHDL)
		if (rep.getType() == NewReplaceValue.TYPE_TICK) {
			if (rep.getPrefix() != null && rep.getPrefix().length() > 0) return this.createTickProposals(rep);
			if (rep.getPrefix().length() > 0) {
				char last = rep.getPrefix().charAt(rep.getPrefix().length() - 1);
				if (Character.isJavaIdentifierPart(last)) return this.createTickProposals(rep);
			}
			return new ICompletionProposal[0];
		}
		// Loads the Template Proposals from both the standard templates and user defined
		
		// Only Create the Templates if they are enabled
		ICompletionProposal[] templates = new ICompletionProposal[0];
		if (HardwareChecker.isTemplateEnabled()) { // Check if Templates are Enabled
			templates = this.createTemplateProposals(viewer, offset, rep);
		}
             
        ICompletionProposal[] complete = new ICompletionProposal[0];
		ParseContext context = EditorUtilities.getParseContext(getSourceEditor().getDesignFile().getParseDescriptor(), offset);
		rep.setEnclosingContext(context.getActiveReference());
		
		if (rep.getType() == NewReplaceValue.TYPE_VERILOG_TICK) return this.createVerilogTickProposals(rep,context,viewer);

		
		if (HardwareChecker.isCompletionEnabled()) {
        	complete  = this.computeCompletionProposals(viewer, offset, rep, context);
        }
        
        // Combine the templates
		ICompletionProposal[] total = new ICompletionProposal[templates.length + complete.length];
		System.arraycopy(templates, 0, total, 0, templates.length);
		System.arraycopy(complete, 0, total, templates.length, complete.length);
		
		SearchContext searchContext = context.getSearchContext();
		
		// Context Sensitive Clause --- Should be turned into a function later
		if (searchContext == null) {
			if (rep.getFuntionContext() != null) {
				if (rep.getFuntionContext().prefix.equalsIgnoreCase("PROCESS")) {
					searchContext = new SearchContext.Signal(); 
				}
				ParserTop parser = getSourceEditor().getDesignFile().getParser();
	            ModuleObjectFindItem base = (ModuleObjectFindItem) parser.findNameObject(context,rep.getFuntionContext().prefix,ReferenceUtilities.REF_MODULEOBJECT);
	            ReferenceItem ref = null;
	            if (base != null) {
	            	ref = context.getRefHandler().findContextObjectWithFixed(base);
	            }
				
				if (ref != null) {
					if (ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
						SystemVar var = (SystemVar) ref.getObject();
						TypeVar tvar = var.getType();
						ReferenceItem ref2 = tvar.getRangeReference();
						if (ref2 != null && ref2.getObject() != null && ref2.getObject() instanceof TypeVar) {
							searchContext = new SearchContext.SignalOfType((TypeVar)ref2.getObject());
						}
					}
					else if (ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_FUNCTION_HOLDER) == 0) {
						int location = rep.getFuntionContext().loc;
						FunctionHolder holder = (FunctionHolder) ref.getObject();
						TypeVar[] types = holder.getTypeofInput(location);
						if (types.length > 0) {
							searchContext = new SearchContext.SignalOfTypes(types);
						}
						
					}
				}
			}
			if (searchContext == null) searchContext = new SearchContext();
			// Only Include Context Sensitive in Professional Version
			if (!HardwareChecker.isContextSensitiveCompletionEnabled()) {
				searchContext = new SearchContext();
			}
		}
		Arrays.sort(total, 0, total.length, new CompletionComparator(searchContext));
		return total;
	}

	protected abstract String getContextTypeId(ITextViewer viewer, IRegion region);
	protected abstract TemplateContextType getContextType(ITextViewer viewer, IRegion region);
	/** Returns a list of basic templates */
	protected abstract Template[] getTemplates(); 
	

	protected Template[] createLiteralTemplates(String[] literals, String context) {
		Template[] temps = new Template[literals.length];
		int i=0;
		for (String literal : literals) {
			temps[i] = new BaseTemplate(new BaseTemplate.LiteralInt(literal),context);
			i++;
		}
		return temps;
	}
	
	protected Template[] mergeTemplates(Template[] temp1, Template[] temp2) {
		Template[] ntemp = new Template[temp1.length + temp2.length];
    	for (int i=0;i<temp1.length;i++) {
    		ntemp[i] = temp1[i];
    	}
    	for (int i=0;i<temp2.length;i++) {
    		ntemp[temp1.length-1 + i] = temp2[i];
    	}
    	
    	return ntemp;
	}
	

	
	
}
