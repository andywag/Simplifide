/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.project.define;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class DefineObject extends ModuleObjectNew implements TemplateCompletionInterface{

	
	private String text;	
	private NodePosition position;
	
	private String realText;
	private String totalText;
	
	public DefineObject(String name) {
		super(name);
	}

	public EditorFindItem getFindItem(ParseContext context, int offset) {
		if (offset < position.getStartPos() + name.length()) { // Position of Name
			this.realText = text;
			EditorFindItem item = new EditorFindItem(this.createReferenceItem(),this.createReferenceItem(),
					position.getStartPos(),position.getStartPos() + name.length());
			return item;
		}
		else {
			int off = offset - position.getStartPos();
			char uchar = totalText.charAt(off);
			while (!Character.isWhitespace(uchar) && off > 0) {
				off--;
				uchar = totalText.charAt(off);
			}
			if (totalText.charAt(off + 1) == '`') {
				StringBuilder builder = new StringBuilder();
				off+=2;
				uchar = totalText.charAt(off);
				while (Character.isJavaIdentifierPart(uchar) && off < totalText.length()) {
					builder.append(uchar);
					off++;
					uchar = totalText.charAt(off);
				}
				DefineHolder holder = context.getRefHandler().getGlobalReference().getObject().getDefineHolder();
				DefineObject obj = holder.getDefineObject(builder.toString());
				EditorFindItem item = new EditorFindItem(obj.createReferenceItem(),obj.createReferenceItem(),
						off-builder.length() + position.getStartPos()-2,off + position.getStartPos()-2);
				return item;
			}
		}
		return null;
	}
	
	
	
	public boolean containsPosition(int offset) {
		if (offset > position.getStartPos() && offset < position.getEndPos()) return true;
		return false;
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_DEFINE_OBJECT;
	}
	
	public DefineCall getDefineCall(DefineObject obj) {
	
		return new DefineCall(this);
	}
	
	public void setRealText(String text) {
		this.realText = text;
	}
	public String getRealText() {
		return this.realText;
	}
	
	

	public void setPosition(NodePosition position) {
		this.position = position;
	}

	public NodePosition getPosition() {
		return position;
	}

	
	

	public String getTemplateDescription() {
		return "";
	}

	public String getTemplateDisplayName() {
		return this.getDisplayName();
	}

	public String getTemplatePattern() {
		return "`" + this.getname();
	}

	public boolean isAutoComplete() {
		return false;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setTotalText(String totalText) {
		this.totalText = totalText;
	}

	public String getTotalText() {
		return totalText;
	}
	
	
	
}
