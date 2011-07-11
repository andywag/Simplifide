package com.simplifide.core.refactor.function;

import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.MainRefactorProcessor;

public abstract class FunctionRefactoringProcessor extends MainRefactorProcessor{

	private ReferenceItem<InstanceFunction> funcR;
	
	public FunctionRefactoringProcessor(ReferenceItem<InstanceFunction> funcR, SourceEditor editor) {
		super(editor);
		this.funcR = funcR;
	}

	public void setFuncR(ReferenceItem<InstanceFunction> funcR) {
		this.funcR = funcR;
	}

	public ReferenceItem<InstanceFunction> getFuncR() {
		return funcR;
	}

	

}
