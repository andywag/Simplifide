/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.function.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.module.PackageModuleBody;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.refactor.function.FunctionRefactoringProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class CreateFunctionRefactorProcessor extends
		FunctionRefactoringProcessor {

	private static final String ID = CoreActivator.PLUGIN_ID
			+ "editors.refactor.rename.CreateFunctionRefactorProcessor";

	public CreateFunctionRefactorProcessor(
			ReferenceItem<InstanceFunction> entityRef, SourceEditor editor) {
		super(entityRef, editor);
	}

	/**
	 * Creates a list (singleton) of changes instantiations with changed
	 * connections
	 */
	protected List<SourceMatch> createFunctionChange(
			ReferenceLocation location, FunctionDeclaration wrap, boolean head) {
		String cfile = "refactor/verilog/function";
		if (this.isVhdlFile())
			cfile = "refactor/vhdl/function";
		
		HashMap map = new HashMap();
		map.put("object", wrap);
		map.put("head", head);
		
		String temp1 = TemplateGenerator.generateTemplate(cfile, wrap);
		if (location != null) {
			String temp = TemplateGenerator.generateTemplate(cfile, wrap);
			return this.singleMatch(temp, location);
		}
		return new ArrayList<SourceMatch>();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
 		ReferenceItem<InstanceFunction> funcR = this.getFuncR();
 		RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
 		ReferenceLocation loc = null;
		if (funcR != null) {
			boolean head = true;
			InstanceFunction inst = funcR.getObject();
			FunctionDeclaration func = funcR.getObject().getFunctionDeclaration();
			ModuleObject obj = inst.getEnclosingObject().getObject();
			
			if (obj instanceof PackageModule) {
				InstancePackage pack = ((PackageModule) obj).getInstancePackage().getObject();
				if (pack.getPackageBodyReference() != null) {
					TopModule mod = (TopModule) pack.getPackageBodyReference().getObject();
					loc = mod.getRefactorAdditionLocation();
				}
				head = false;
			}
			else if (obj instanceof PackageModuleBody) {
				InstancePackage pack = ((PackageModuleBody) obj).getInstancePackage().getObject();
				if (pack.getPackageModuleReference() != null) {
					TopModule mod = (TopModule) pack.getPackageModule();
					loc = mod.getRefactorAdditionLocation();
				}
			}
			root.addElements(this.createFunctionChange(loc, func,head));

		}
		
		return this.createRootChange(root);

	}

	@Override
	public String getIdentifier() {
		return ID;
	}

	@Override
	public String getProcessorName() {
		return "Create Function";
	}

}
