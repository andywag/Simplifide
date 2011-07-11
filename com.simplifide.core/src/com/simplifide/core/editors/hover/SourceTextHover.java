/*******************************************************************************
  * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/

package com.simplifide.core.editors.hover;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.DelegateVar;
import com.simplifide.base.core.var.realvars.SystemParameter;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.hover.GeneralTextHover;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.preference.PreferenceConstants;



public abstract class SourceTextHover extends GeneralTextHover{

    private EditorFindItem item;
    
   
    public SourceTextHover(SourceEditor editor) {
        super(editor);

    }

    public abstract int getHoverType();
    
    
    
    private String convertTags(String inString) {
    	StringBuilder out = new StringBuilder();
    	String[] ustr = inString.split("\n");
    	for (int i=0;i<ustr.length;i++) {
    		String cstr = ustr[i];
    		if (cstr.trim().startsWith("<")) { // Don't Add Spaces to tags
    			out.append(ustr[i].trim());
    		}
    		else {
    			int len = cstr.length();
    			for (int j=0;j<cstr.length()-1;j++) {
    				if (cstr.charAt(j) == ' ') out.append("<space>");
    				else if (cstr.charAt(j) =='\t') out.append("<tab>");
    				else {
    					out.append(cstr.substring(j));
    					break;
    				}
    			}
    		}
    		out.append("\n");
    	}
    	return out.toString();
    }
    
    public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {

    	if (!HardwareChecker.isHoverEnabled()) return null;
        try {
        	if (CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.HOVER_ENABLED)) {
                if (item != null) {
                    ModuleObject obj = item.getItem().getObject();
                    if (obj instanceof InstanceModule) {
                    	obj = ((InstanceModule)obj).getEntity(); 
                    }
                    else if (obj instanceof InstancePackage) {
                    	InstancePackage pack = (InstancePackage) obj;
                    	obj = pack.getPackageModule();
                    }
                    else if (obj instanceof FunctionHolder) {
                    	FunctionHolder hold = (FunctionHolder) obj;
                    	ReferenceItem ref = (ReferenceItem) hold.getObject(0);
                    	obj = ref.getObject();
                    }
                    else if (obj instanceof DelegateVar) {
                    	DelegateVar del = (DelegateVar) obj;
                    	obj= del.getBaseVar().getObject();
                    }
                    int type = this.getHoverType();
                    String objtext = HoverFactory.getTextHover(obj, type);
                    objtext = this.convertTags(objtext);
                    return objtext;
                }
                else {
                	ParseDescriptor desc = this.getSourceEditor().getDesignFile().getParseDescriptor();
                	int pos = hoverRegion.getOffset();
                	SystemParameter par = EditorUtilities.getNumberNode(desc, pos);
                	if (par != null) {
                		return par.getHoverText();
                	}
                }
            }
        }
        catch (Exception e) {
        	HardwareLog.logError(e);
        }
        
        return null;
    }

    public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
    	if (!HardwareChecker.isHoverEnabled()) return null;
    	try {
    		SourceEditor seditor = this.getSourceEditor();
    		this.item = null;
    		if (seditor.getDesignFile() == null) return null;
        	if (seditor.getDesignFile().getParseDescriptor() == null) return null;
        	
            ParseDescriptor desc = seditor.getDesignFile().getParseDescriptor();
            this.item = EditorUtilities.getHoverItem(desc, offset);
    	}
    	catch (Exception e) {
    		HardwareLog.logError(e);
    	}
    	
        
        return new Region(offset,1);
    }

    public SourceEditor getSourceEditor() {
    	return (SourceEditor) getEditor();
    }
    
   

}
