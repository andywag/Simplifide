/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/

package com.simplifide.core.editors.completion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.ReferenceItemComparator;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.newfunction.FunctionCall;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.EnumHolder;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.template.GeneralContentProcessor;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.templates.SourceTemplateProcessor;

public abstract class SourceContentProcessor extends GeneralContentProcessor{

	
	public SourceContentProcessor(SourceEditor editor) {
		super(editor);
	}
	
	protected abstract int getLanguageType();
	
	private ReferenceItem getBaseItemContext(NewReplaceValue rep, int offset,ParseContext context) {
		
		if (rep.getPrefix().equalsIgnoreCase("work")) { // Work Finds the Internal Project Reference
			return context.getRefHandler().getProjectReference();
		}
		ReferenceItem baseItem = null;
		if (rep.getType() == NewReplaceValue.TYPE_NONE) { // This refers to a ctrl+SPACE
			if (context.getRefHandler().getSearchReference() != null && context.getRefHandler().getSearchReference().getObject() != null) {
				ModuleObjectContextHolder holder = (ModuleObjectContextHolder)context.getRefHandler().getSearchReference().getObject();
	            holder.appendObject(context.getRefHandler().getLocalReference());
	            holder.appendObject(context.getRefHandler().getActiveReference());
				baseItem = holder.createReferenceItem();
			}
			
        } else {
            ParserTop parser = getSourceEditor().getDesignFile().getParser();
            ModuleObjectFindItem obj = (ModuleObjectFindItem) parser.findNameObject(context,rep.getPrefix(),ReferenceUtilities.REF_MODULEOBJECT);
            if (obj == null && rep.getType() == NewReplaceValue.TYPE_PAR) {
            	obj = (ModuleObjectFindItem) parser.findNameObject(context,"",ReferenceUtilities.REF_MODULEOBJECT);
            }
            if (obj != null) { // Type 
                ReferenceItem ritem = context.getRefHandler().findContextObjectWithFixed(obj);
                baseItem = ritem;
            }
            if (obj != null && baseItem == null) {
            	baseItem = context.getRefHandler().findGlobalObject(obj, ReferenceUtilities.REF_MODULEOBJECT);
            }
        }
		return baseItem;
	}
	
	/** This method needs to be able to delineate between an old module as well as new */
	private ReferenceItem getBaseItem(NewReplaceValue rep, int offset) {
		ParseContext context = EditorUtilities.getParseContext(getSourceEditor().getDesignFile().getParseDescriptor(), offset);
		ReferenceItem baseItem =  this.getBaseItemContext(rep, offset, context);
		//ParseDescriptor desc = getEditor().getDesignFile().getParseDescriptor();
		/*if (baseItem == null || baseItem.getObject() == null || baseItem.getType() == 20000) { 
			context.getRefHandler().setActiveReference(desc.getOldModule().getReference());
			context.getRefHandler().setSearchReference(desc.getOldModule().getContextRef());
			baseItem = this.getBaseItemContext(rep, offset, context);
		}*/
		
		return baseItem;
	}

	private List<ReferenceItem> getReferenceList(ReferenceItem baseItem, NewReplaceValue rep) {
		List<ReferenceItem> alist = new ArrayList<ReferenceItem>();
        if (baseItem != null && baseItem.getObject() != null) {
        	if (rep.getType() == NewReplaceValue.TYPE_PAR) {
        		ModuleObjectBaseItem base = new ModuleObjectBaseItem(rep.getPostfix());
                alist = baseItem.findParenItemList(base, ReferenceUtilities.REF_MODULEOBJECT);
                Collections.sort(alist,ReferenceItemComparator.getInstance());
        	}
        	else {
        		if (baseItem.getObject() instanceof FunctionHolder) {
        			FunctionHolder holder = (FunctionHolder) baseItem.getObject();
        			ReferenceItem<InstanceFunction> instR = (ReferenceItem<InstanceFunction>) holder.getObject(0);
        			baseItem = instR.getTypeReference();
    			}
    			else if (baseItem.getObject() instanceof FunctionCall) {
    				FunctionCall call = (FunctionCall) baseItem.getObject();
    				ReferenceItem<InstanceFunction> instR = call.getInstanceRef();
    				baseItem = instR.getTypeReference();
    			}
        		if (baseItem != null) {
        			ModuleObjectBaseItem base = new ModuleObjectBaseItem(rep.getPostfix());
                    alist = baseItem.findPrefixItemList(base, ReferenceUtilities.REF_MODULEOBJECT);
                    Collections.sort(alist,ReferenceItemComparator.getInstance());
        		}
    			
        	}
        }
        return alist;
	}
	

	
	/** Create the actual completion proposal --- overriden by {@link SourceTemplateProcessor}*/
	protected ICompletionProposal[] createCompletionProposal(ReferenceItem item, NewReplaceValue value, ITextViewer viewer) {
		return new ICompletionProposal[] {new SourceCompletionProposal(item,value,this.getLanguageType())};
	}

	/** Convert the list of ReferenceItems to Completion Proposals */
	private ICompletionProposal[] convertList(List<ReferenceItem> refList, NewReplaceValue repValue, ITextViewer viewer) {
		
		ArrayList<ICompletionProposal> propList = new ArrayList<ICompletionProposal>();
		for (int i=0;i<refList.size();i++) {
			ICompletionProposal[] outprop = this.createCompletionProposal(refList.get(i), repValue, viewer);
			for (ICompletionProposal prop : outprop) {
				propList.add(prop);
			}
		}
		return propList.toArray(new ICompletionProposal[propList.size()]);
	}
	
	/** Used for Standalone when there is not templates utilitized. In general this function is not used */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		NewReplaceValue rep = NewReplaceValue.createReplaceValue(viewer.getDocument(),offset);
		ParseContext context = EditorUtilities.getParseContext(getSourceEditor().getDesignFile().getParseDescriptor(), offset);
		return this.computeCompletionProposals(viewer, offset,rep, context);
	}
	
	
	private List<ReferenceItem> removeQuoted(List<ReferenceItem> refList) {
		List<ReferenceItem> outprop = new ArrayList<ReferenceItem>();
		for (int i=refList.size()-1;i>=0;i--) {
			ReferenceItem ref = refList.get(i);
			
			ref = this.checkEnumVar(ref);
			String distName = ref.getDisplayName();
			if (!distName.startsWith("\"") && 
				!distName.startsWith("'")  &&
				ref.getType() != ReferenceUtilities.REF_ASSIGN &&
				ref.getType() != ReferenceUtilities.REF_PROCESS_STATEMENT )  {
			
					outprop.add(ref);
			}
			
		}
		return outprop;
		
	}
	
	/** Convert the enum holder to a system var */
	private ReferenceItem checkEnumVar(ReferenceItem ref) {
		if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_ENUM_HOLDER) == 0) {
			EnumHolder hold = (EnumHolder) ref.getObject();
			ReferenceItem<SystemVar> tvar = (ReferenceItem<SystemVar>) hold.getObject(0);
			return tvar;
		}
		return ref;
	}
	
	
	/** Called from the template process function which supercedes the standard call */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset, NewReplaceValue rep, ParseContext context) {
		
		// Returns the item which is found from this replace value 
		ReferenceItem baseItem =  this.getBaseItemContext(rep, offset, context);
		// Returns a list of possible values based on the replace value
		List<ReferenceItem> alist = this.getReferenceList(baseItem, rep);
		
		ParseDescriptor desc = getSourceEditor().getDesignFile().getParseDescriptor();

		// Parenthesis case alpha(.. without space
		// This doesn't seem necessary at all
		if (alist.size() == 0 && rep.getType() == NewReplaceValue.TYPE_PAR) {
			String pre = rep.getPrefix();
			rep.setPrefix("");
			rep.setType(NewReplaceValue.TYPE_NONE);
			ReferenceItem baseItem2 = this.getBaseItem(rep, offset);
			alist = this.getReferenceList(baseItem2, rep);
			rep.setType(NewReplaceValue.TYPE_PAR);
			rep.setPrefix(pre);
		}
		if (alist.size() > 0) {
			ReferenceItem ref = alist.get(0);
			if (ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_PROJECT_TOP) == 0) {
				if (desc.isErrored()) {
					if (baseItem != null && ReferenceUtilities.checkType(baseItem.getType(), ReferenceUtilities.REF_PROJECT_TOP) != 0) {
						alist = desc.getCompletionList().findPrefixItemList(new ModuleObjectBaseItem(rep.getPostfix()), ReferenceUtilities.REF_MODULEOBJECT);
					}
				}				
			}
		}
		if (alist.size() == 0 && desc.isErrored() && baseItem == null && rep.getType() == NewReplaceValue.TYPE_NONE) {
			alist = desc.getCompletionList().findPrefixItemList(new ModuleObjectBaseItem(rep.getPostfix()), ReferenceUtilities.REF_MODULEOBJECT);
		}
		
		
		
		alist = this.removeQuoted(alist);
		alist = this.filterList(alist);
		
		
		// Convert the List of ReferenceItems to an array of CompletionProposals
		ICompletionProposal[] props = this.convertList(alist, rep, viewer);
		return props;
	}

	private List<ReferenceItem> filterList(List<ReferenceItem> alist) {
		ArrayList<ReferenceItem> outList = new ArrayList<ReferenceItem>();
		ReferenceItem last = null;
		for (ReferenceItem ref : alist) {
			if (last != null) {
				if (!(ref.getname().equalsIgnoreCase(last.getname()) && ref.getType() == last.getType())) {
					outList.add(ref);
				}
			}
			else {
				outList.add(ref);
			}
			last = ref;
		}
		return outList;
	}
	
	

	public char[] getCompletionProposalAutoActivationCharacters() {
		char[] chars = new char[] {'.','(','`'};
		return chars;
	}

	

	
	public SourceEditor getSourceEditor() {
		return (SourceEditor) this.getEditor();
	}

}
