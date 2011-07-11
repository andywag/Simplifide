/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.NodePosition;

/**
 * Class which contains a list of templates used in the hdl files.
 * @author andy
 *
 */

public class TemplateList {

	private List<TemplateContents> contentList = new ArrayList<TemplateContents>();
	private List<NodePosition> deleteList = new ArrayList<NodePosition>();
	
	public TemplateList() {}
	
	public TemplateList(List contentList, List deleteList) {
		this.contentList = contentList;
		this.deleteList = deleteList;
	}

	public void setContentList(List<TemplateContents> contentList) {
		this.contentList = contentList;
	}

	public List<TemplateContents> getContentList() {
		return contentList;
	}

	public void setDeleteList(List<NodePosition> deleteList) {
		this.deleteList = deleteList;
	}

	public List<NodePosition> getDeleteList() {
		return deleteList;
	}
	
	
}
