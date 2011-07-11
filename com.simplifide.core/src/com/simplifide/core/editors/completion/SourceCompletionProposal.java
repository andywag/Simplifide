/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.completion;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.hover.SourceInformationControlCreator;
import com.simplifide.core.baseeditor.template.GeneralCompletionProposal;
import com.simplifide.core.editors.hover.HoverFactory;
import com.simplifide.core.project.library.storage.ShadowReference;
import com.simplifide.core.resources.IconManager;

public class SourceCompletionProposal extends GeneralCompletionProposal{

	private ReferenceItem item;
	private NewReplaceValue repValue;
	private int languageType;
	
	public SourceCompletionProposal(ReferenceItem item, NewReplaceValue repValue, int languageType) {
		this.setItem(item);
		this.setRepValue(repValue);
		this.languageType = languageType;
	}

	protected String getPrefix() {
		return this.getRepValue().getReplacePrefix();
	}
	
	public void apply(IDocument document) {
		if (this.getRepValue().getType() != NewReplaceValue.TYPE_PAR) {
            String prefix = this.getPrefix();
            String replaceString = prefix + this.getItem();
            if (getItem().getType() == ReferenceUtilities.REF_ENUMVAR || this.getRepValue().getType() == NewReplaceValue.TYPE_NONE) {
            	// Not Sure why this was toString()
            	//replaceString = this.item.toString();
            	replaceString = this.getItem().getname();
            }
            if (getItem().getType() == ReferenceUtilities.REF_MODINSTANCE_CONNECT) {
            	ModInstanceConnect connect = (ModInstanceConnect) getItem().getObject();
            	String name = connect.getname();
            	if (prefix.length() == 1) replaceString = name;
            	else replaceString = prefix + name;
            }
            if (ReferenceUtilities.checkType(getItem().getType(),ReferenceUtilities.REF_PORT_TOP) == 0) {
            	if (!prefix.equalsIgnoreCase("(")) {
                	replaceString = prefix + getItem().getname();
            	}
            	else {
            		replaceString = getItem().getname();
            	}
            }
            if (replaceString.startsWith(".")) {
            	replaceString = replaceString.substring(1);
            }
            /*if (item.getType() == ReferenceUtilities.REF_COMPONENT) {
                replaceString = "";
            }
            if (item.getType() == ReferenceUtilities.REF_INSTANCE_MODULE) {
                replaceString = "";
            }*/
            try {
            	replaceString = replaceString.trim();
            	if (this.getRepValue().getLength() == 0) {
            		String ustr = document.get(this.getRepValue().getSpos()-1, 1);
            		document.replace(this.getRepValue().getSpos()-1, this.getRepValue().getLength()+1, ustr+replaceString);
            	}
            	else {
    				document.replace(this.getRepValue().getSpos(), this.getRepValue().getLength(), replaceString);
            	}
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}
		}
		else {
			String prefix = this.getPrefix();
			String replaceString = prefix + this.getItem();
			replaceString = replaceString.trim();
			 try {
					document.replace(this.getRepValue().getSpos(), this.getRepValue().getLength(), replaceString);
				} catch (BadLocationException e) {
					HardwareLog.logError(e);
				}
		}
		
	}

	
	
	public String getDisplayString() {
		return getItem().getDisplayName();
	}

	public Image getImage() {
		return IconManager.getImage(getItem().getType());
	}



	
	public IInformationControlCreator getInformationControlCreator() {
		return new SourceInformationControlCreator(null);
	}



	private ModuleObject handleInstanceModule(InstanceModule umodule) {
		if (umodule.getEntityReference().isShadow()) {
			ModuleObject uobj = ((ShadowReference)umodule.getEntityReference()).loadObject();
			return uobj;
		}
		return null;
	}
	
	private ModuleObject handleInstancePackagae(InstancePackage umodule) {
		if (umodule.getPackageModuleReference().isShadow()) {
			ModuleObject uobj = ((ShadowReference)umodule.getPackageModuleReference()).loadObject();
			return uobj;
		}
		return null;
	}
	
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		if (getItem() != null) {
			ModuleObject obj = getItem().getObject();
			if (obj instanceof InstanceModule) {
				InstanceModule imod = (InstanceModule) obj;
				ModuleObject uobj = this.handleInstanceModule(imod);
				if (obj != null) {
					obj = uobj;
				}
			}
			if (obj instanceof InstancePackage) {
				InstancePackage imod = (InstancePackage) obj;
				ModuleObject uobj = this.handleInstancePackagae(imod);
				if (obj != null) {
					obj = uobj;
				}
			}
			return HoverFactory.getTextHover(obj, languageType);
		}
		return null;
	}

	public void setItem(ReferenceItem item) {
		this.item = item;
	}

	public ReferenceItem getItem() {
		return item;
	}

	public void setRepValue(NewReplaceValue repValue) {
		this.repValue = repValue;
	}

	public NewReplaceValue getRepValue() {
		return repValue;
	}
}
