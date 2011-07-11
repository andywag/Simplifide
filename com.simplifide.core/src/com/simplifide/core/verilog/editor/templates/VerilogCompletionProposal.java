package com.simplifide.core.verilog.editor.templates;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.editors.completion.SourceCompletionProposal;

public class VerilogCompletionProposal extends SourceCompletionProposal{

	public VerilogCompletionProposal(ReferenceItem item,
			NewReplaceValue repValue, int languageType) {
		super(item, repValue, languageType);
	}
	
	protected String getPrefix() {
		NewReplaceValue rep = this.getRepValue();
		return rep.getVerilogReplacePrefix();
	}

}
