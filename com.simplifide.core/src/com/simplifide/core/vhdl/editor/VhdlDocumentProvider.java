package com.simplifide.core.vhdl.editor;

import com.simplifide.core.baseeditor.color.SourcePartitionScanner;
import com.simplifide.core.editors.SourceDocumentProvider;

public class VhdlDocumentProvider extends SourceDocumentProvider{

	@Override
	public SourcePartitionScanner createPartitionScanner() {
		return new VhdlPartitionScanner();
	}

}
