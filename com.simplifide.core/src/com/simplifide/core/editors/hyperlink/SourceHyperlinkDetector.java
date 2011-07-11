/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.hyperlink.GeneralHyperlinkDetector;
import com.simplifide.core.editors.SourceEditor;



public class SourceHyperlinkDetector extends GeneralHyperlinkDetector{

	
	public SourceHyperlinkDetector(GeneralEditor editor) {
		super(editor);
	}
	
	private SourceEditor getSourceEditor() {
		return (SourceEditor) this.getEditor();
	}
	
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        // Check to see if licensed software    
		
		if (!HardwareChecker.isHyperLinkEnabled()) return null;
		
		ParseDescriptor desc = this.getSourceEditor().getDesignFile().getParseDescriptor();
		
		
		List<EditorFindItem> itemList = EditorUtilities.getEditorFindItemList(desc, region.getOffset());
		if (itemList.size() == 0) {
			return this.handleHttp(region);
			
		}
		
		int arrSize = (canShowMultipleHyperlinks) ? itemList.size() : 1;
		IHyperlink[] nlink = new IHyperlink[arrSize];
		
		ArrayList<IHyperlink> links = new ArrayList<IHyperlink>();
		ArrayList<IHyperlink> elinks = new ArrayList<IHyperlink>();
		
		int[] us = itemList.get(0).getPosition();
		Region areg = new Region(us[0],us[1]-us[0]);
		for (int i=0;i<arrSize;i++) {
			EditorFindItem item = itemList.get(i);
			links.add(new SourceHyperlink(item.getItem(),areg));
			if (item.getItem().getObject() instanceof Entity) {
				Entity ent = (Entity) item.getItem().getObject();
				InstanceModule inst = (InstanceModule)ent.getInstanceModRef().getObject();
				/*
				ConnectorModule cmod = (ConnectorModule) inst.getConnectReference().getObject();
				for (Object ref : cmod.getConnectionList().getGenericSelfList()) {
					elinks.add(new SourceHyperlink((ReferenceItem)ref,areg,editor.getDesignFile()));
				}*/
				if (HardwareChecker.isHyperLinkInstanceEnabled()) {
					List<ModInstanceConnect> cons = inst.getConnectionList();
					for (ModInstanceConnect con : cons) {
						elinks.add(new SourceHyperlink(con.createReferenceItem(),areg));
					}
				}
				
			}
		}
		links.addAll(elinks);
		return links.toArray(new IHyperlink[links.size()]);
		
	}

}
