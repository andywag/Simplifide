package com.simplifide.core.scripteditor.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.template.GeneralContentProcessor;
import com.simplifide.core.baseeditor.template.GenerateTemplateContext;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.editors.completion.SourceCompletionProposal;
import com.simplifide.core.editors.templates.SourceTemplateProcessor;
import com.simplifide.core.scalaext.ScalaStartup;
import com.simplifide.core.scripteditor.ScriptUtilities;
import com.simplifide.core.verilog.editor.templates.VerilogTemplateProcessor;
import com.simplifide.core.vhdl.editor.VhdlEditor;
import com.simplifide.scala2.command.Command;
import com.simplifide.scala2.command.CommandSection;
import com.simplifide.scala2.top.ParserContext;


public class ScriptContentProcessor extends GeneralContentProcessor{

	public ScriptContentProcessor(GeneralEditor editor) {
		super(editor);
	}
	
	private int getLength(ITextViewer viewer, int offset) {
		IDocument doc = viewer.getDocument();
		int len = 0;
		char c;
		try {
			c = doc.getChar(offset);
			while (!Character.isWhitespace(c)) {
				len++;
				c = doc.getChar(offset-len);
			}
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		
		return len;
	}
	
	private List<Command> getCommands(ITextViewer viewer,int offset) {
		ParserContext context = ScriptUtilities.getScriptContext(viewer, offset);
		if (context.getCommandSection() == null) {
			List<Command> commands =  ScalaStartup.loadCommands();//InterfaceCommands.getJavaCommands();
			return commands;
		}
		else {
			CommandSection section =context.getCommandSection();
			List<Command> commands = section.getCommandList();
			return commands;
		}
		
	}
	
	private List<ICompletionProposal> createSignalProposals(ITextViewer viewer, int offset) {
		SourceTemplateProcessor temp = new VerilogTemplateProcessor((SourceEditor)this.getEditor());
		ICompletionProposal[] sigs =  temp.computeCompletionProposals(viewer, offset);
		ArrayList<ICompletionProposal> arr = new ArrayList<ICompletionProposal>();
		for (ICompletionProposal sig : sigs) {
			if (sig instanceof SourceCompletionProposal) {
				SourceCompletionProposal so = (SourceCompletionProposal) sig;
				if (so.getItem() != null && 
					so.getItem().getObject() != null &&
					so.getItem().getObject() instanceof SystemVar) {
					arr.add(sig);
				}
			}
		}
		return arr;
	}
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		
		NewReplaceValue rep = NewReplaceValue.createReplaceValue2(viewer.getDocument(),offset);
		
		
		
		int len = this.getLength(viewer, offset-1);
		GenerateTemplateContext docContext = new GenerateTemplateContext(new ScriptTemplateContextType(), 
				viewer.getDocument(), offset-len, len);
		Region reg = new Region(offset-len,len);
		
		ArrayList<ICompletionProposal> props = new ArrayList<ICompletionProposal>();
		//List<Command> commands = (List<Command>) InterfaceCommands.getJavaCommands();
		
		List<Command> commands = getCommands(viewer, offset);
		boolean vhdl = (this.getEditor() instanceof VhdlEditor);
		for (Command command : commands) {
			if (command.command().startsWith(rep.getPrefix())) {
				props.add(ScriptTemplateProposal.createProposal(command, reg, docContext,vhdl));
			}
			
		}
		
		props.addAll(createSignalProposals(viewer, offset));
		Collections.sort(props,new CompCompare());
		return props.toArray(new ICompletionProposal[props.size()]);
	}
	
	public static class CompCompare implements Comparator<ICompletionProposal> {

		public int compare(ICompletionProposal o1, ICompletionProposal o2) {
			if (o1 instanceof ScriptTemplateProposal && !(o2 instanceof ScriptTemplateProposal)) {
				return -1;
			}
			else if (!(o1 instanceof ScriptTemplateProposal) && (o2 instanceof ScriptTemplateProposal)) {
				return 1;
			}
			return o1.getDisplayString().compareToIgnoreCase(o2.getDisplayString());
		}
		
	}

}
