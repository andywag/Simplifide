/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.completion;


import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;

public class NewReplaceValue {
	public static int TYPE_NONE = 0;
    public static int TYPE_DOT = 1;
    public static int TYPE_PAR = 2;
    public static int TYPE_DONE = 3;
    public static int TYPE_TICK = 4;
    public static int TYPE_VERILOG_TICK = 5;
    
    private static char TICK = "'".toCharArray()[0];
    
    private int type;
    
    private int spos;
    private int dpos;
    private int epos;
    
    private String prefix;
    private String postfix;
    private FunctionContext funtionContext;
    private ReferenceItem enclosingContext;
    
    
    /** Creates a new instance of NewReplaceValue */
    public NewReplaceValue() {}
    public NewReplaceValue(int spos, int dpos, int epos, String prefix, String postfix, int type) {
        this.spos = spos;
        this.dpos = dpos;
        this.epos = epos;
        this.prefix = prefix;
        this.postfix = postfix;
        this.type = type;
    }
    
    public int getLength() {return this.epos - spos;}
    
    public String getReplacePrefix() {
    	String uprefix;
    	if (this.getType() == TYPE_DOT) uprefix = prefix + ".";
    	else uprefix = prefix + "(";
    	
        if (uprefix.startsWith("this.")) return uprefix.substring(5,uprefix.length());
        if (uprefix.startsWith("root.")) return uprefix.substring(5,uprefix.length());
        return uprefix;
    }
    

    public String getVerilogReplacePrefix() {
    	String uprefix ="";
    	if (this.getEnclosingContext() != null && 
    		this.getEnclosingContext().getObject() instanceof ClassModule &&
        	this.getType() == TYPE_DOT) {
        		return prefix + ".";
        	}
    	uprefix =  this.getReplacePrefix();
    	if (uprefix.startsWith("(")) return "";
    	return uprefix;
    }
    
    /** Convenience Operation to get the Character */
    private static char getChar(IDocument doc, int position) {
      
            try {
				return doc.getChar(position);
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}  
			return ' ';
         
    }
    
    public static boolean checkWhiteSpace(char uv) {
    	return Character.isWhitespace(uv);
    }
    
    private static boolean checkValidPostfix(char uv) {
        //return !uv.equalsIgnoreCase(".") && !uv.equalsIgnoreCase("(") && !uv.equalsIgnoreCase(" ") && !uv.equalsIgnoreCase("\n");
    	boolean bool = Character.isJavaIdentifierPart(uv);
    	if (!bool) return false;
        return (uv != '.') && (uv != '(') && (uv != ' ') && (uv != '\n') && (uv != '\t') && (uv != TICK);
    }
    
    private static boolean checkValidPrefix(char uv) {
       // return !uv.equalsIgnoreCase(" ") && !uv.equalsIgnoreCase("\n");
    	boolean bool = Character.isJavaIdentifierPart(uv) | uv == '.' | uv == '(';
    	if (!bool) return false;
    	return (uv != ' ') && (uv != '\n') && (uv != '\t') ;
    }
    
    private static boolean checkValidPrefixNoParen(char uv) {
        // return !uv.equalsIgnoreCase(" ") && !uv.equalsIgnoreCase("\n");
     	boolean bool = Character.isJavaIdentifierPart(uv) | uv == '.';
     	if (!bool) return false;
     	return (uv != ' ') && (uv != '\n') && (uv != '\t') ;
     }
    
    private static boolean checkWhiteCommaParen(char uv) {
        // return !uv.equalsIgnoreCase(" ") && !uv.equalsIgnoreCase("\n");
     	return  Character.isWhitespace(uv) || uv == ',' || uv == '(';
     	
     }
   
    
    /** @deprecated */
    public static NewReplaceValue createReplaceValue(IDocument doc, int caretOffset) {

        int endLocation = caretOffset;
        int startLocation;
        int dotLocation;
        
        
        
        int location = endLocation-1;
        
        char uv = ' ';
        String postfix = "";
        String prefix = "";
        
        int repType = TYPE_NONE;
        // First character
        uv = getChar(doc,location);
        if (uv == ')') return new NewReplaceValue(0,0,0,"","",TYPE_DONE); 
        // First Create the postfix value
        while (checkValidPostfix(uv) && location > 0) {
            postfix = uv + postfix;
            location--;
            uv = getChar(doc,location);
        }
        dotLocation = location;
        if (uv == TICK) {
        	return new NewReplaceValue(location+1, location+1, endLocation,"",postfix,TYPE_TICK);
        }
        if (!checkValidPrefix(uv)) { // Case where there isn't a prefix the code stops here
            //prefix = postfix;
            //postfix = postfix;
            startLocation = location+1;
            dotLocation = location+1;
            return new NewReplaceValue(startLocation,dotLocation,endLocation,prefix,postfix,repType);
        }
        if (uv == '.') repType = TYPE_DOT;
        else if (uv == '(') repType = TYPE_PAR;
        dotLocation = location+1;
        location--;
        uv = getChar(doc,location);
        while (checkValidPrefix(uv) && location > 0) {
            prefix = uv + prefix;
            location--;
            uv = getChar(doc,location);
        }
        if (prefix.equalsIgnoreCase("")) {
        	while (uv != ' ' && uv != '\t' && uv != '\n') {
        		prefix = uv + prefix;
                location--;
                uv = getChar(doc,location);
        	}
        }
        
        startLocation = location+1;
        if (prefix.startsWith("(")) {
        	prefix = prefix.substring(1);
        	startLocation = startLocation + 1;
        }
        // Case to handle Dot inside a function or process statement
        if (repType == TYPE_DOT) {
        	int parens = parenCount(prefix);
        	int ppos = prefix.lastIndexOf("(");
        	if (ppos > 0 && parens != 0) {
        		startLocation = startLocation + ppos+1;
        		prefix = prefix.substring(ppos+1);
        	}
        }
        return new NewReplaceValue(startLocation,dotLocation,endLocation,prefix,postfix,repType);
        
    }
    
    /** Finds a valid prefix until the opening paren. Called from a prefix with a closed paren */
    private static String checkUntilOpenParen(IDocument doc, int position) {
    	int upos = position;
    	char uv = getChar(doc, position);
    	StringBuilder prefix = new StringBuilder();
    	while (uv != '(' && upos > 0) {
    		prefix.append(uv);
    		upos = upos - 1;
    		uv = getChar(doc, upos);
    	}
    	prefix.append(uv);
    	upos = upos-1;
    	String pre = checkTickOrDotPrefix(doc, upos);
    	prefix = prefix.reverse();
    	return pre + prefix.toString() ;
    }
    
    /** Finds the prefix if there is a tick or dot */
    private static String checkTickOrDotPrefix(IDocument doc, int position) {
        int upos = position;
    	char uv = getChar(doc,upos);
    	while ((Character.isWhitespace(uv) || uv == '(') && upos >= 0) { // trim the whitespace and parens
    		upos = upos - 1;
    		uv = getChar(doc,upos);
    	}
    	
    	StringBuilder prefix = new StringBuilder();
        String pre = "";
    	while (checkValidPrefixNoParen(uv) && upos > 0) {
        	prefix.append(uv);
        	upos = upos-1;
        	uv = getChar(doc,upos);
        }
    	if (uv == ')') {
    		pre = checkUntilOpenParen(doc, upos-1) + ")";
    	}
    	return pre + prefix.reverse().toString();
    }
    
    /** Check for a Tick or Dot Replace Value */
    public static NewReplaceValue checkTickOrDot(IDocument doc, int caretOffset) {
    	int repType = TYPE_NONE;
    	int endLocation = caretOffset;
        
    	int location = endLocation-1;
    	StringBuilder postfix = new StringBuilder();
        
        char uv = getChar(doc,caretOffset-1);
        while (checkValidPostfix(uv) && location > 0) {
            postfix.append(uv);
            location--;
            uv = getChar(doc,location);
        }
        
        if (uv == '.') repType = TYPE_DOT;
        if (uv == TICK) repType = TYPE_TICK;
        if (uv == '`') repType = TYPE_VERILOG_TICK;
        if (repType == TYPE_NONE) return null; // Only Continue for TICK or DOT
    	
        int dotLocation = location;
        String prefix = checkTickOrDotPrefix(doc, location-1);
        int startLocation = dotLocation - prefix.length();
        NewReplaceValue rep = new NewReplaceValue(startLocation,dotLocation,endLocation,prefix,postfix.reverse().toString(),repType);
        return rep;
    }
    
   
    
    /** Check for a Tick or Dot Replace Value */
    public static NewReplaceValue checkNoTickDot(IDocument doc, int caretOffset) {
    	int repType = TYPE_NONE;
    	int endLocation = caretOffset;
        
    	int location = endLocation-1;
    	StringBuilder postfix = new StringBuilder();
        
        char uv = getChar(doc,caretOffset-1);
        while (checkValidPostfix(uv) && location > 0) {
            postfix.append(uv);
            location--;
            uv = getChar(doc,location);
        }
        
        int dotLocation = location+1;
        String prefix = "";
        int startLocation = dotLocation - prefix.length();
        NewReplaceValue rep = new NewReplaceValue(startLocation,dotLocation,endLocation,prefix,postfix.reverse().toString(),TYPE_NONE);
        return rep;
    }
    
    public static FunctionContext findCommaFunctionContext(IDocument doc, int caretOffset) {
    	int location = caretOffset;
        char uv = getChar(doc,location-1);
        int position = 1;
        int parenCount = 0;
        
        while (location > 0) {
        	
            if (uv == ',') {
            	position++;
            }
            else if (uv == '(') {
            	if (parenCount == 0) {
            		String pre = checkTickOrDotPrefix(doc, location-1);
                	return new FunctionContext(pre,0);
            	}
            	else {
            		parenCount--;
            	}
            }
            else if (uv == ')') {
            	parenCount++;
            }
            location = location - 1;
            uv = getChar(doc,location-1);
        }
        return null;
        
    }
    
    /** Check for a Tick or Dot Replace Value */
    public static FunctionContext findFunctionContext(IDocument doc, int caretOffset) {
    	
        
    	int location = caretOffset-1;
        
        char uv = getChar(doc,caretOffset-1);
        while (Character.isWhitespace(uv) && location > 0) { // Remove White Space before looking for comma or paren
            uv = getChar(doc,location);
            location = location -1;
        }
        if (uv == '(') {
        	String pre = checkTickOrDotPrefix(doc, location);
        	return new FunctionContext(pre,0);
        }
        else if (uv == ',') {
        	return findCommaFunctionContext(doc, location);
        }
        return null;
        
       
    }
    
    public static NewReplaceValue createReplaceValue2(IDocument doc, int caretOffset) {

        int location = caretOffset;
        char uv = getChar(doc,location-1);
        
       
        
        if (uv == ')') return new NewReplaceValue(0,0,0,"","",TYPE_DONE); // Done Condition with End of Par
        NewReplaceValue value = checkTickOrDot(doc, location); // Dot Or Tick Replace Search
        if (value == null) value = checkNoTickDot(doc, location);
        if (value != null) {
            FunctionContext context = findFunctionContext(doc, value.getSpos());
            value.setFuntionContext(context);
        }
        return value;     
    }
    
   
    
    
    private static int parenCount(String prefix) {
    	int count = 0;
    	for (int i=0;i<prefix.length();i++) {
    		char uc = prefix.charAt(i);
    		if (uc == '(') count = count + 1;
    		else if (uc == ')') count = count - 1;
    	}
    	return count;
    }

    public int getSpos() {
        return spos;
    }

    public void setSpos(int spos) {
        this.spos = spos;
    }

    public int getDpos() {
        return dpos;
    }

    public void setDpos(int dpos) {
        this.dpos = dpos;
    }

    public int getEpos() {
        return epos;
    }

    public void setEpos(int epos) {
        this.epos = epos;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void setFuntionContext(FunctionContext funtionContext) {
		this.funtionContext = funtionContext;
	}
	public FunctionContext getFuntionContext() {
		return funtionContext;
	}

	public void setEnclosingContext(ReferenceItem enclosingContext) {
		this.enclosingContext = enclosingContext;
	}
	public ReferenceItem getEnclosingContext() {
		return enclosingContext;
	}

	public static class FunctionContext {
    	public String prefix;
    	public int loc;
    	public FunctionContext(String prefix, int loc) {
    		this.prefix = prefix;
    		this.loc = loc;
    	}
    }
    
}
