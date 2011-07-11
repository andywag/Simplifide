package com.simplifide.base.verilog.parse.base;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import antlr.TokenStream;
import antlr.TokenStreamException;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.newfunction.FunctionCall;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.define.DefineCall;
import com.simplifide.base.core.project.define.DefineMacro;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenCopy;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenDelegate;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogLexer;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;


/** Convenience class to seperate the complexity of the verilog position stream
 *  Currently only holds define operations and macros
 * @author andy
 *
 */
public class SystemVerilogPositionStream extends VerilogPositionStream{

	public SystemVerilogPositionStream(ParseContext context) {
		super(context);
	}

	
	/** Handles the `define xxxx desc */
	 protected int handleDefineToken(TopASTToken tok, int pos, TokenStream stream, HdlDoc doc) throws TokenStreamException {
		 
		 StringBuilder total   = new StringBuilder();
		 StringBuilder builder = new StringBuilder();
		 int upos = 0;
		 boolean first = true;
		 TopASTToken ntok = (TopASTToken) stream.nextToken();
		 total.append(ntok.getText());
		 // Creates the text for the define head of the form
		 // alpha
		 // alpha(a,b,...)
		 while (ntok.getType() != SystemVerilogTokenTypes.NEWLINE_ &&
				ntok.getType() != SystemVerilogTokenTypes.EOF) {
			 total.append(ntok.getText());
			 if (ntok.getType() != SystemVerilogTokenTypes.ESCAPE_NEWLINE) builder.append(ntok.getText());
			 else builder.append("\n");
			 ntok = (TopASTToken) stream.nextToken();
			 if (ntok.getType() == SystemVerilogTokenTypes.IDENTIFIER && first) {
				 upos = ntok.getPosition();
				 first = false;
			 }
		 }
		 // Genereate the Define Object
		 DefineObject obj = MacroParser.generateMacro(builder.toString());
		 obj.setTotalText(total.toString());
		 if (doc != null) { // Appends the HDL Doc
	    	obj.setDoc(doc.copy());
		    doc.setDescription("");
	      }
		 // Attach the define object to the context list
		 this.getContext().getDescriptor().getDefines().add(obj);
		 obj.setPosition(new NodePosition(upos,upos + builder.length(),0,0));
		 
		 // Returns the Object
		 ReferenceLocation loc = this.getContext().getBaseLocation();
	     loc = loc.copyLocation(upos, -1,-1);
	     CoreProjectSuite suite = this.getContext().getRefHandler().getGlobalReference().getObject();
	     if (suite != null && suite.getDefineHolder() != null)
	    		suite.getDefineHolder().addObject(obj.createReferenceItemWithLocation(loc));
		 return builder.length() + tok.getLength();
		 
	 }
	 
	
	 /** Handles the `identifer xxxx*/
	 protected int handleTickIdentifier(TopASTToken tok, int pos, TokenStream stream) throws TokenStreamException{
	
		 int npos = pos + tok.getLength();
		 TopASTToken ntok = (TopASTToken) stream.nextToken();
		 StringBuilder builder = new StringBuilder();
		 
		 // Removes the white space before the paren 
		 while (ntok.getType() == SystemVerilogTokenTypes.WS_) {
			 builder.append(ntok.getText());
			 npos += ntok.getLength();
			 ntok = (TopASTToken) stream.nextToken();
		 }
		 // Decodes the macro and returns the Define Object prototype
		 DefineObject obj = new DefineObject(tok.getText().substring(1));
		 if (ntok.getType() == SystemVerilogTokenTypes.LPAREN) { // Define Macro
			 ntok = (TopASTToken) stream.nextToken();
			 int open = 0;
			 npos += ntok.getLength();
			 while (open != 0 || (ntok.getType() != SystemVerilogTokenTypes.RPAREN &&
					 			 ntok.getType() != SystemVerilogTokenTypes.EOF)) {
				 if (ntok.getType() == SystemVerilogTokenTypes.LPAREN) open++;
				 if (ntok.getType() == SystemVerilogTokenTypes.RPAREN) open--;
				 builder.append(ntok.getText());
				 npos += ntok.getLength();
				 ntok = (TopASTToken) stream.nextToken();
			 }
			 builder.append(ntok.getText());
			 obj = MacroParser.parseFunction(tok.getText().substring(1), builder);
			 ntok = null;
		 }
		 // Returns the Define Object Call
		 CoreProjectSuite suite = this.getContext().getRefHandler().getGlobalReference().getObject();
		 DefineCall call = suite.getDefineHolder().getDefineCall(obj);
		 if (call == null) {
			 pos = super.handleTickIdentifier(tok, pos, stream);
			 if (ntok != null) this.addToken(ntok, npos, stream, null);
			 return pos;
		 }
		 // Creates the Text associate with the tokens
		 String ret = MacroGenerator.parseObject(call, this.getContext());
		 // Convertes the text to tokens
		 SystemVerilogLexer lex = new SystemVerilogLexer(new StringReader(ret));
		 SystemVerilogPositionStream stream2 = new SystemVerilogPositionStream(this.getContext());
		 stream2.setPreserveSpace(this.isPreserveSpace());
		 stream2.createArrayList(lex, "aa");
		 TopASTToken tok2 = (TopASTToken) stream2.nextToken();
		 // Adds the Tokens to the list
		 TopASTTokenDelegate del = new TopASTTokenDelegate(tok,null);
		 del.setBaseText(ret);
		 while (tok2 != null && tok2.getType() != SystemVerilogTokenTypes.EOF) {
			 TopASTTokenCopy copy = new TopASTTokenCopy(tok2, del);
			 copy.setDefineR(call.getObj().createReferenceItem());
			 
			 this.addToken(copy, npos, stream, null);
			 npos += tok.getLength();
			 tok2 = (TopASTToken)stream2.nextToken();
		 }
		 if (ntok != null) this.addToken(ntok, npos, stream2, null);
		 return npos;
	 }
	 
	
	 
}
