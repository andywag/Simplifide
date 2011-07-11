/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.completion;


import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.hover.SourceInformationControlCreator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.hover.HoverFactory;
import com.simplifide.core.project.library.storage.ShadowReference;
import com.simplifide.core.refactor.instance.CreateInstanceRefactorProcessor;
import com.simplifide.core.refactor.instance.CreateInstanceRefactoring;
import com.simplifide.core.refactor.instance.CreateInstanceWizard;
import com.simplifide.core.resources.IconManager;

public class InstanceCompletionProposal implements ICompletionProposal, 
													ICompletionProposalExtension3, 
													ICompletionProposalExtension5,
													CompletionProposalInt{

	private ReferenceItem item;
	private NewReplaceValue repValue;
	private int languageType;
	private SourceEditor editor;
	
	public InstanceCompletionProposal(ReferenceItem item, 
			NewReplaceValue repValue, 
			int languageType,
			SourceEditor editor) {
		this.item = item;
		this.repValue = repValue;
		this.languageType = languageType;
		this.editor = editor;
	}

	private InstanceModule getInstanceModule() {
		ReferenceItem refi = null;
		
		ParseContext context = EditorUtilities.getParseContext(editor.getDesignFile().getParseDescriptor(), editor.getCaretPosition());
        refi = context.getRefHandler().getActiveReference();
		
		if (refi != null) {
			if (refi.getType() == ReferenceUtilities.REF_HARDWARE_MODULE) {
				HardwareModule hard = (HardwareModule) refi.getObject();
				InstanceModule instanceModule = (InstanceModule) hard.getInstModRef().getObject();
				return instanceModule;
			}
			else if (refi.getType() == ReferenceUtilities.REF_ENTITY) {
				Entity entity = (Entity) refi.getObject();
				InstanceModule instanceModule = (InstanceModule) entity.getInstanceModRef().getObject();
				return instanceModule;
			}
		}
		
		return null;
	}
	
	public void apply(IDocument document) {
		InstanceModule instanceModule = (InstanceModule) item.getObject();
		URI uri = editor.getDesignFile().getUri();
		ReferenceLocation loc = new ReferenceLocation(uri,repValue.getSpos(),repValue.getLength(),-1);
		
		if (!HardwareChecker.isRefactoringEnabled()) return;
		
		CreateInstanceRefactorProcessor processor = new CreateInstanceRefactorProcessor( null ,
				 this.getInstanceModule(),editor,loc,instanceModule );
         CreateInstanceRefactoring ref = new CreateInstanceRefactoring( processor ); 
         CreateInstanceWizard wizard = new CreateInstanceWizard( ref );
         RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard ); 
         try { 
             String titleForFailedChecks = ""; //$NON-NLS-1$ 
             op.run(editor.getEditorSite().getShell(), titleForFailedChecks ); 
         } catch( InterruptedException irex ) { 
             
         }

		/*
		if (this.repValue.getType() != NewReplaceValue.TYPE_PAR) {
            String prefix = this.repValue.getReplacePrefix();
            String replaceString = prefix + this.item;
            if (item.getType() == ReferenceUtilities.REF_ENUMVAR || this.repValue.getType() == NewReplaceValue.TYPE_NONE) {
            	replaceString = this.item.toString();
            }
            if (item.getType() == ReferenceUtilities.REF_MODINSTANCE_CONNECT) {
            	ModInstanceConnect connect = (ModInstanceConnect) item.getObject();
            	String name = connect.getname();
            	if (prefix.length() == 1) replaceString = name;
            	else replaceString = prefix + name;
            }
            if (ReferenceUtilities.checkType(item.getType(),ReferenceUtilities.REF_PORT_TOP) == 0) {
            	if (!prefix.equalsIgnoreCase("(")) {
                	replaceString = prefix + item.getname();
            	}
            	else {
            		replaceString = item.getname();
            	}
            }
            if (replaceString.startsWith(".")) {
            	replaceString = replaceString.substring(1);
            }
           
            try {
				document.replace(this.repValue.getSpos(), this.repValue.getLength(), replaceString);
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}
		}
		else {
			String prefix = this.repValue.getReplacePrefix();
			String replaceString = prefix + this.item;
			 try {
					document.replace(this.repValue.getSpos(), this.repValue.getLength(), replaceString);
				} catch (BadLocationException e) {
					HardwareLog.logError(e);
				}
		}*/
		
	}

	public String getAdditionalProposalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public IContextInformation getContextInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getDisplayString() {
		return item.getDisplayName();
	}

	public Image getImage() {
		return IconManager.getImage(item.getType());
	}

	public Point getSelection(IDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public IInformationControlCreator getInformationControlCreator() {
		return new SourceInformationControlCreator(null);
	}

	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		// TODO Auto-generated method stub
		return 0;
	}

	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	private ModuleObject handleInstanceModule(InstanceModule umodule) {
		ReferenceItem<Entity> entR = umodule.getEntityReference();
		if (entR.isShadow()) {
			ModuleObject uobj = ((ShadowReference)umodule.getEntityReference()).loadObject();
			return uobj;
		}
		else {
			return entR.getObject();
		}
		
	}
	
	private ModuleObject handleInstancePackagae(InstancePackage umodule) {
		if (umodule.getPackageModuleReference().isShadow()) {
			ModuleObject uobj = ((ShadowReference)umodule.getPackageModuleReference()).loadObject();
			return uobj;
		}
		return null;
	}
	
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		if (item != null) {
			ModuleObject obj = item.getObject();
			if (obj instanceof InstanceModule) {
				InstanceModule imod = (InstanceModule) obj;
				ModuleObject uobj = this.handleInstanceModule(imod);
				if (uobj != null) {
					obj = uobj;
				}
			}
			if (obj instanceof InstancePackage) {
				InstancePackage imod = (InstancePackage) obj;
				ModuleObject uobj = this.handleInstancePackagae(imod);
				if (uobj != null) {
					obj = uobj;
				}
			}
			return HoverFactory.getTextHover(obj, languageType);
		}
		return null;
	}

	public ReferenceItem getItem() {
		return item;
	}
}
