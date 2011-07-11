/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.segment.assign;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatement;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// selected_waveforms : selected_waveform_item ( COMMA selected_waveform_item )*

public class SelectedWaveformsNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList<CaseConditionStatement> list = new NoSortList<CaseConditionStatement>();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ReferenceItem<CaseConditionStatement> state = (ReferenceItem<CaseConditionStatement>) child.generateModule(context);
			list.addObject(state);
			child = child.getNextASTSibling();
			if (child != null) child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}
	
}
