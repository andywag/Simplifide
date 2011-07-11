/*
 * VerilogPositionStream.java
 *
 * Created on October 18, 2005, 10:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;

import com.simplifide.base.basic.struct.DocPosition;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.ToDoObject;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.define.DefineHolder;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeFolding;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenCopy;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenDelegate;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;



/**
 *  
 * @author Andy Wagner
 */
public class VerilogPositionStream extends PositionStream{
    
    /** Creates a new instance of VerilogPositionStream 
     * @param str 
     */
	
	private static String[] NEWLINE_TOKS = new String[] {
			"uselib"
	};
	
	private static String[] SINGLE_TOKS = new String[] {
			"default_nettype"
	};
	
    private static String[] NULL_TOKS = new String[] {
    		// Cadence Null Toks
    		"accelerate",
    		"noaccelerate",
    		"autoexpand_vectornets",
    		"cell_define",
    		"endcelldefine",
    		"default_decay_time",
    		"default_rswitch_strength",
    		"default_switch_strength",
    		"default_trireg_strength",
    		"delay_mode_distributed",
    		"delay_mode_path",
    		"delay_mode_unit",
    		"delay_mode_zero",
    		"expand_vectornets",
    		"protect",
    		"endprotect",
    		"protected",
    		"unprotected",
    		"remove_gatenames",
    		"noremove_gatenames",
    		"remove_netnames",
    		"noremove_netnames",
    		"resetall",
    		"unconnected_drive",
    		"nounconnected_drive",
    		
    		"celldefine",
    		"disable_portfaults",
    		"suppress_faults",
    		"enable_portfaults",
    		"nosuppress_faults",
    		"reset_timescale"};
	

   
    private ArrayList<TopASTNodeFolding> directiveList = new ArrayList<TopASTNodeFolding>();
    private Stack<TopASTNodeFolding> foldList = new Stack<TopASTNodeFolding>();
    
	public VerilogPositionStream(ParseContext context) {
        super(context);
       
    }
    
	
	
    public void createArrayList(TokenStream stream, String name) {
    	super.createArrayList(stream, name);
    	
    	TopASTToken lastToken = null;
    	
    	boolean smode = false;
    	int stok = -1;
    	
    	for (int i=this.getTokenList().size()-1;i>=0;i--) {
    		TopASTToken tok = this.getTokenList().get(i);
    		if (tok == null) continue;
    		if (lastToken == null) {
    			lastToken = tok;
    			continue;
    		}
    		// I have no idea what the purpose of this is ----
    		if (tok.getType() == SystemVerilogTokenTypes.NUMBER && lastToken.getType() == SystemVerilogTokenTypes.NUMBER) {
    			if (lastToken.getText().startsWith("'")) {
    				this.getTokenList().remove(i);
    				continue;
    			}
    		}
    		// Handles Attributes
    		if (tok.getType() == SystemVerilogTokenTypes.STAR) {
    			if (lastToken.getType() == SystemVerilogTokenTypes.RPAREN) {
    				if (tok.getPosition() == (lastToken.getPosition() -1)) {
    					smode = true;
        				stok = i+1;
    				}
    			}
    		}
    		if (smode) {
    			if (tok.getType() == SystemVerilogTokenTypes.LPAREN) {
    				if (lastToken.getType() == SystemVerilogTokenTypes.STAR &&
    					(tok.getPosition() == (lastToken.getPosition() -1) &&
    							stok != (i+2))) {
    						for (int j=stok;j>=i;j--) {
    							this.getTokenList().remove(j);
    						}
    						smode = false;
    				}
    				else {
    					smode = false;
        				stok = -1;
    				}
    			}
    		}
    		lastToken = tok;
    	}
    }
    
    public List<TopASTNode> getCommentFoldList() {
    	List<TopASTNode> comments = super.getCommentFoldList();
    	comments.addAll(this.directiveList);
    	return comments;
    }
    
  
    
    protected ReferenceItem<DefineObject> getDefineObject(String name) {

    	//ModuleObjectWithList<DefineObject> defines = this.getContext().getRefHandler().getSuperModuleReference().getObject().getDefines();
    	//return defines.findBaseReference(new ReferenceItem(name,ReferenceUtilities.REF_MODULEOBJECT));
    
    	DefineHolder holder = this.getContext().getRefHandler().getGlobalReference().getObject().getDefineHolder();
    	if (holder == null) return null;
    	ReferenceItem ref = holder.findBaseReference(name,ReferenceUtilities.REF_MODULEOBJECT);
    	return ref;
    }
    
    protected int handleTickIdentifierNew(TopASTToken tok, int pos, TokenStream stream, TopASTToken otok) throws TokenStreamException{
    	int npos = pos; 
    	
    	//ReferenceItem<DefineObject> objRef = this.getDefineObject(tok.getText().substring(1));
    	
    	//if (objRef == null) {
    		String utok = tok.getText().substring(1);
    		for (String ntok : NULL_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) return pos + tok.getText().length();
    		}
    		for (String ntok : SINGLE_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) {
    				int npos1 = pos + tok.getText().length();
    				Token newTok = stream.nextToken();
    				while (this.isWhiteSpace(newTok)) {
    		    		newTok = (TopASTToken) stream.nextToken();
    		    		npos1 = npos1 + tok.getText().length();
    				}
    				return npos1 + tok.getText().length();
    			}
    		}
    		for (String ntok : NEWLINE_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) {
    				return this.handleUntilNewLine(tok, pos, stream);
    			}
    		}
    		// Convert the Token to and identifier token
    		TopASTToken atok = new TopASTToken(SystemVerilogTokenTypes.IDENTIFIER,tok.getText());
    		return super.addToken(atok, pos,stream,null);
    }
    
    
    /** Handle the case for a `identifier */
    protected int handleTickIdentifier(TopASTToken tok, int pos, TokenStream stream) throws TokenStreamException{
    	int npos = pos; 
    	
    	//ReferenceItem<DefineObject> objRef = this.getDefineObject(tok.getText().substring(1));
    	
    	//if (objRef == null) {
    		String utok = tok.getText().substring(1);
    		for (String ntok : NULL_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) return pos + tok.getText().length();
    		}
    		for (String ntok : SINGLE_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) {
    				int npos1 = pos + tok.getText().length();
    				Token newTok = stream.nextToken();
    				while (this.isWhiteSpace(newTok)) {
    		    		newTok = (TopASTToken) stream.nextToken();
    		    		npos1 = npos1 + tok.getText().length();
    				}
    				return npos1 + tok.getText().length();
    			}
    		}
    		for (String ntok : NEWLINE_TOKS) {
    			if (utok.equalsIgnoreCase(ntok)) {
    				return this.handleUntilNewLine(tok, pos, stream);
    			}
    		}
    		// Convert the Token to and identifier token
    		TopASTToken atok = new TopASTToken(SystemVerilogTokenTypes.IDENTIFIER,tok.getText());
    		return super.addToken(atok, pos,stream,null);
    	//}
    	/*
    	ArrayList<TopASTToken> tokList = new ArrayList();
    	TopASTToken newTok = (TopASTToken) stream.nextToken();
    	tokList.add(newTok);
    	while (this.isWhiteSpace(newTok)) {
    		newTok = (TopASTToken) stream.nextToken();
    		tokList.add(newTok);
    	}
    	if (newTok.getType() != SystemVerilogTokenTypes.LPAREN) {
    		this.addDefineObject(objRef.getObject(), npos, stream,tok);
    		npos = npos + tok.getText().length();
    	}
    	else {
    		TopASTTokenCopy cop = new TopASTTokenCopy(new TopASTToken(SystemVerilogTokenTypes.IDENTIFIER,objRef.getname()),tok);
    		super.addToken(cop, pos, stream);
    	}
    	for (TopASTToken stok : tokList) {
			super.addToken(stok, pos, stream);
			npos = npos + stok.getText().length();
		}
		return npos;
    	*/
    	
    }
    
    /** Handles a `include (sort of) only removes the values from the token stream */
    private int handleUntilNewLine(TopASTToken tok, int pos, TokenStream stream) throws TokenStreamException{
    	int npos = pos + tok.getText().length();
    	TopASTToken ntok = (TopASTToken) stream.nextToken();
		
    	while (ntok.getType() != SystemVerilogTokenTypes.NEWLINE_ && ntok.getType() != 1) {
    		ntok = (TopASTToken) stream.nextToken();
    		npos +=  ntok.getText().length();
    	}
    	npos +=  ntok.getText().length();
    	return npos;
    }
    
    private DefineObject decodeTick(String name) {
    	CoreProjectSuite suite = this.getContext().getRefHandler().getGlobalReference().getObject();
    	ReferenceItem<DefineObject> objRef = suite.getDefineHolder().findBaseReference(name, ReferenceUtilitiesInterface.REF_MODULEOBJECT );
    	if (objRef == null) return null;
    	return objRef.getObject();
    	
    }
  
    
    protected int handleDefineToken(TopASTToken tok, int pos, TokenStream stream, HdlDoc doc) throws TokenStreamException {
    	return pos;
    } 
    /*
    protected int handleDefineToken(TopASTToken tok, int pos, TokenStream stream) throws TokenStreamException {
    	int npos = pos + tok.getText().length();
    	TopASTToken ntok = (TopASTToken) stream.nextToken();
    	
    	while (this.isWhiteSpace(ntok) && ntok.getType() != 1) {
    		ntok = (TopASTToken) stream.nextToken();
    		npos += ntok.getText().length();
    	}
    	
    	String name = ntok.getText();
    	if (name.startsWith("`")) {
    		name = name.substring(1);
    	}
    	npos += ntok.getText().length();
    	
    	DefineObject obj = new DefineObject(name);
    	CoreProjectSuite suite = this.getContext().getRefHandler().getGlobalReference().getObject();
    	suite.getDefineHolder().addObject(obj.createReferenceItemWithLocation(this.getContext().getBaseLocation()));
    	
    	// Add this define to the module for for the 'ifndef X; `define X
    	SuperModule sup = this.getContext().getRefHandler().getSuperModuleReference().getObject();
    	sup.getDefines().addReferenceItem(obj.createReferenceItem());
    	
    	ntok = (TopASTToken) stream.nextToken();
    	boolean checkParen = false;
    	
    	while (ntok.getType() != SystemVerilogTokenTypes.NEWLINE_ && ntok.getType() != 1) {
    		ntok = (TopASTToken) stream.nextToken();
    		
    		// This case handles dereferencing of defines
    		// ie `define alpha `beta
    		//if (ntok.getType() == SystemVerilogTokenTypes.LPAREN && !checkParen) {
    		//	obj = new DefineObjectFunction(obj.getname(),ntok);
    		//	obj.addToken(ntok);
    		//	checkParen = true;
    		//}
    		if (ntok.getType() ==  SystemVerilogTokenTypes.DEFINE) {
    			DefineObject def = this.decodeTick(ntok.getText().substring(1));
    			obj.addDefineObject(def);
    			checkParen = true;
    		}
    		else if (!this.isWhiteSpace(ntok) && !this.hiddenToken(ntok)) {
    			obj.addToken(ntok);
    			checkParen = true;
    		}
    		npos += ntok.getText().length();
    	}
    	//npos += ntok.getText().length();
    	return npos;
    }
    */
    private int handleUseDef(TopASTToken tok, TokenStream stream, int pos) throws TokenStreamException{
    	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
    	this.foldList.push(foldingNode);
    	int npos = pos +  tok.getText().length();
    	return npos;
    }
    
    private int handleNoUseDef(TopASTToken otok, TokenStream stream, int pos) throws TokenStreamException{
    	int npos = pos;
    	TopASTToken tok = otok;
    	Stack<TopASTNodeFolding> localStack = new Stack<TopASTNodeFolding>();
    	
    	boolean not_first = false;
    	while (tok.getType() != 1) {
    		if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF || tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF) {
    	    	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
    	    	localStack.push(foldingNode);
    		}
    		else if (tok.getType() == SystemVerilogTokenTypes.TICKELSE) {
    	    	//if (not_first) {
    	    		TopASTNodeFolding popNode = localStack.pop();
    	    		popNode.setEndPosition(tok.getPosition()-3);
        	    	this.directiveList.add(popNode);
    	    	//}
    	    	//else {
    	    		
    	    	//}
    	    	not_first = true;
    	    	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
    	    	localStack.push(foldingNode);
    		}
    		else if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF) {
    	    	TopASTNodeFolding popNode = localStack.pop();
    	    	popNode.setEndPosition(tok.getEndPosition());
    	    	this.directiveList.add(popNode);
    		}
    		tok = (TopASTToken) stream.nextToken();
      		npos += tok.getText().length();
      		if (localStack.empty()) break;
     	}
    	return npos;
    }
    
    

    /** Handle the stream associated if the lexer tries to add an `ifdef marker. If this is the case
     *  a folding node is created and this token is removed.  
	 *
	 *  TODO Determine whether the value is defined before throwing out data
     */
    private int handleIfDef(TopASTToken otok, TokenStream stream, int pos, boolean ifdef) throws TokenStreamException{
    	
    	int npos = pos + otok.getText().length();
    	
    	TopASTToken ntok = (TopASTToken) stream.nextToken();	
    	while (this.isWhiteSpace(ntok) && ntok.getType() != 1) {
    		ntok = (TopASTToken) stream.nextToken();
    		npos += ntok.getText().length();
    	}
    	String name = ntok.getText();
    	ReferenceItem<DefineObject> objRef = this.getDefineObject(name);
    	boolean sameFile = false; // This is to handle ifndef in the same file as define
    	if (objRef != null ) {
    		sameFile = true;
    	}
    	 
    	boolean use = true;
    	if (objRef == null && ifdef == true) use = false; 
    	if (objRef != null && ifdef == false && sameFile == false) use = false;
    	
    	TopASTToken tok = ntok;
    	if (use == true) { // Adds a folding node and passes all of the other text except the current
    		TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
        	this.foldList.push(foldingNode);
        	npos = npos +  tok.getText().length();
        	return npos; 
    	}
    	else { // Creates a folding node and removes everything until an 'else or 'endif is found
    		TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText(),true);
        	Stack<TopASTNodeFolding> localStack = new Stack<TopASTNodeFolding>();
        	localStack.push(foldingNode);
        	while (tok != null && tok.getType() != 1) {
        		if (tok.getType() == SystemVerilogTokenTypes.TICKELSE) { // Pop Off the Node and Add
        			TopASTNodeFolding popNode = localStack.pop();
        	    	//popNode.setEndPosition(tok.getEndPosition());
        	    	//this.directiveList.add(popNode);
        	    	if (localStack.empty()) { // If Last Else Condition run the else code
        	    		this.foldList.push(popNode);
        	    		return this.handleElseCondition(tok, stream, npos);
        	    	}
        	    	else { // Create a new Node
        	    		popNode.setEndPosition(tok.getPosition()-3);
            	    	this.directiveList.add(popNode);
        	    		foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
            	    	localStack.push(foldingNode);
        	    	}
        		}
        		else if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF || 
        				tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF) {
        	    	foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
        	    	localStack.push(foldingNode);
        		}
        		else if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF) {
        			TopASTNodeFolding popNode = localStack.pop();
        	    	popNode.setEndPosition(tok.getEndPosition());
        	    	this.directiveList.add(popNode);
        		}
          		if (localStack.empty()) break;

        		tok = (TopASTToken) stream.nextToken();
          		npos += tok.getText().length();
        	}
    		return this.handleNoUseDef(ntok, stream, npos);
    	}
    	/*
    	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
    	this.foldList.push(foldingNode);
    	
    	npos += ntok.getText().length();
 
    	return npos;
    	*/
    }
    
    
   
    /** Handles when a `else is added to the stream. In this case, it closes a folding node adds it 
     *  to the list of folds and removes the rest of the tokens until the end.  
     *  
     *  TODO Determine whether the value is defined before throwing out data
     */
    private int handleElseCondition(TopASTToken tok, TokenStream stream, int pos) throws TokenStreamException {

    	// Close Out the Previous Node
    	TopASTNodeFolding node = this.foldList.pop();
    	node.setEndPosition(tok.getPosition() - 3);
    	this.directiveList.add(node);
    	int npos = pos;
    	if (node.isFolded()) { // If Last If Node Not Used Create New Node
    		TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
        	this.foldList.push(foldingNode);
        	npos = pos +  tok.getText().length();
        	return npos;
    	}
    	else { // Delete Nodes Until an endif is found
        	Stack<TopASTNodeFolding> localStack = new Stack<TopASTNodeFolding>();
        	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
        	localStack.push(foldingNode);
        	tok = (TopASTToken) stream.nextToken();
        	npos = pos + tok.getText().length();
    		while (tok != null && tok.getType() != 1) {
        		if (tok.getType() == SystemVerilogTokenTypes.TICKELSE) { // Pop Off the Node and Add
        			TopASTNodeFolding popNode = localStack.pop();
        	    	popNode.setEndPosition(tok.getEndPosition());
        	    	this.directiveList.add(popNode);
        	    	foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
                	localStack.push(foldingNode);
        		}
        		else if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF || 
        				tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF) {
        	    	foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
        	    	localStack.push(foldingNode);
        		}
        		else if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF) {
        			TopASTNodeFolding popNode = localStack.pop();
        	    	popNode.setEndPosition(tok.getEndPosition());
        	    	this.directiveList.add(popNode);
        		}
        		
        		tok = (TopASTToken) stream.nextToken();
          		npos += tok.getText().length();
          		if (localStack.empty()) break;
        	}
    		return npos;
    	}
    	
    	// Add the ifdef fold to the list
    	/*TopASTNodeFolding popNode = this.foldList.pop();
    	popNode.setEndPosition(pos-3);
    	this.directiveList.add(popNode);
    	TopASTNodeFolding foldingNode = new TopASTNodeFolding(tok.getPosition(), tok.getText());
    	
    	while (ntok.getType() != 1 && 
     		   ntok.getType() != SystemVerilogTokenTypes.TICKENDIF) {
     		ntok = (TopASTToken) stream.nextToken();	
     		npos += ntok.getText().length();
    	}
    	npos += ntok.getText().length();
    	foldingNode.setEndPosition(npos);
    	this.directiveList.add(foldingNode);*/
    	//this.createDirectiveFolds(stream, tok, pos);
    	//return npos;
    }
    /** Handles when a `else is added to the stream. In this case, it closes a folding node adds it 
     *  to the list of folds and removes the rest of the tokens until the end.  
     *  
     *  TODO Determine whether the value is defined before throwing out data
     */
    private int handleEndIf(TopASTToken tok, TokenStream stream, int pos) throws TokenStreamException {
    	TopASTToken ntok = tok;
    	int npos = pos;
    	// Add the ifdef fold to the list
    	TopASTNodeFolding popNode = this.foldList.pop();
    	popNode.setEndPosition(pos);
    	this.directiveList.add(popNode);
    	
    	npos += ntok.getText().length();
    	return npos;
    }
  
    
	 protected int handleUnDefineToken(TopASTToken tok, int pos, TokenStream stream) throws TokenStreamException {
		 int npos = pos;
		 while (tok != null && tok.getType() != SystemVerilogTokenTypes.NEWLINE_) {
			 npos += tok.getLength();
			 tok = (TopASTToken)stream.nextToken();
		 }
		 return npos;
	 }
    
    public int addToken(TopASTToken tok,int pos, TokenStream stream, HdlDoc doc) throws TokenStreamException{
    	if (tok.getType() == SystemVerilogTokenTypes.DEFINE) {
        	return this.handleTickIdentifier(tok, pos, stream);
        } 
    	//else if (tok.getType() == SystemVerilogTokenTypes.TICKINCLUDE) {
    	//	return this.handleUntilNewLine(tok, pos, stream);
    	//}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKTIMESCALE) {
    		return this.handleUntilNewLine(tok, pos, stream);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKDEFINE) {
    		return this.handleDefineToken(tok, pos, stream,doc);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKUNDEF) { 
    		return this.handleUnDefineToken(tok, pos, stream);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF ) {
    		return this.handleIfDef(tok, stream, pos, true);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF ) {
    		return this.handleIfDef(tok, stream, pos, false);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKELSE ) {
    		return this.handleElseCondition(tok, stream, pos);
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF ) {
    		return this.handleEndIf(tok, stream, pos);
    	}
    	else return super.addToken(tok, pos, stream,null);

    }
    
    public boolean scriptToken(Token tok) {
        return false;
    }
     
    public boolean hiddenToken(antlr.Token tok)
    {
            if (tok.getType() == SystemVerilogTokenTypes.ML_COMMENT) return true;
            
            else if (tok.getType() == SystemVerilogTokenTypes.SL_COMMENT) return true;
            
            else if (tok.getType() == SystemVerilogTokenTypes.TICKDEFINE ||
            		 tok.getType() == SystemVerilogTokenTypes.TICKUNDEF) {
            	return true;
            }
            /*else if (tok.getType() == SystemVerilogTokenTypes.TICKINCLUDE) {
            	return true;
            }*/
            else if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF ||
            		 tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF) {
            	return true;
            }
            
            else if (tok.getType() == SystemVerilogTokenTypes.TICKELSE) return true;
            else if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF) return true;
            
            else return false;    
    }
    
    public boolean isWhiteSpace(antlr.Token tok)
    {
        if (tok.getType() == SystemVerilogTokenTypes.WS_) return true;
        else if (tok.getType() == SystemVerilogTokenTypes.NEWLINE_) return true;
        else return false;
    }
    
    public boolean isSingleLineComment(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.SL_COMMENT) return true;
		else return false;
	}
    
    
    
 
   

    public void docText(Token tok, HdlDoc doc) {
    	if (tok.getType() == SystemVerilogTokenTypes.ML_COMMENT)
    		VerilogDocParser.getInstance().parseDoc(doc, tok.getText(),this.getContext());  
    	else if (tok.getType() == SystemVerilogTokenTypes.SL_COMMENT) 
    		VerilogDocParser.getInstance().parseSingle(doc, tok.getText(),this.getContext());  

    }

   
   
    
    private void handleTODOSingle(Token tok) {
    	TopASTToken utok = (TopASTToken) tok;
    	String utext = utok.getText();
    	if (utext.contains("TODO") || utext.contains("todo")) {
    		int loc = utext.indexOf("TODO");
    		if (loc < 0) loc = utext.indexOf("todo");
        	String todo = utext.substring(loc + 4);
        	todo.replace("\r", "");
        	String[] st = todo.split("\n");
        	DocPosition docp = utok.getStartDocPosition();
        	
        	ReferenceLocation rloc = new ReferenceLocation(this.getContext().getURILocation(),
        			utok.getStartDocPosition().getDocp() + loc,
        			st[0].length() + 4,
        			utok.getLine());
        	ToDoObject obj = new ToDoObject(st[0],rloc);
        	this.getTodoList().add(obj);
        }
    }
    
    public boolean docToken(Token tok) {
    	if (tok.getType() == SystemVerilogTokenTypes.ML_COMMENT) {
    		this.handleTODOSingle(tok);
    		return true;
    	}
    	else if (tok.getType() == SystemVerilogTokenTypes.SL_COMMENT) {
    		this.handleTODOSingle(tok);
    		return true;
    	}
    	return false;
    }

	@Override
	public boolean getEndOfGeneratedCode(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.ML_COMMENT) {
			if (tok.getText().startsWith(VerilogTemplateHandler.TEMPLATE_STRING_END)) return true;
		}
		return false;
	}

	@Override
	public boolean getStartOfGeneratedCode(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.ML_COMMENT) {
			if (tok.getText().startsWith(VerilogTemplateHandler.TEMPLATE_STRING_BEGIN)) return true;
		}
		return false;
	}
	


	@Override
	public boolean directiveTokenFinish(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.TICKENDIF ||
				tok.getType() == SystemVerilogTokenTypes.TICKELSE) {
				return true;
			}
			return false;
	}

	@Override
	public boolean directiveTokenStart(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.TICKIFDEF ||
			tok.getType() == SystemVerilogTokenTypes.TICKIFNDEF ||
			tok.getType() == SystemVerilogTokenTypes.TICKELSE) {
			return true;
		}
		return false;
	}



	@Override
	public boolean isNewLineToken(Token tok) {
		if (tok.getType() == SystemVerilogTokenTypes.NEWLINE_) return true;
		return false;
	}

}
