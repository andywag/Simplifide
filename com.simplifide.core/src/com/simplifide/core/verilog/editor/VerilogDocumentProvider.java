package com.simplifide.core.verilog.editor;

import com.simplifide.core.baseeditor.color.SourcePartitionScanner;
import com.simplifide.core.editors.SourceDocumentProvider;

public class VerilogDocumentProvider extends SourceDocumentProvider{

	@Override
	public SourcePartitionScanner createPartitionScanner() {
		return new VerilogPartitionScanner();
	}

}
