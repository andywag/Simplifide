/*
 * TemplateGenerator.java
 *
 * Created on April 15, 2007, 12:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.freemarker.transforms.FreeFormatter;
import com.simplifide.core.util.FileUtility;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;




/**
 *
 * @author Andy
 */
public class TemplateGenerator {

    public static int TYPE_COMPLETION_HTML = 0;
    
    public static int TEMPLATE_VHDL = 0;
    public static int TEMPLATE_VERILOG = 1;
    
    private static String COMPLETIONBASE = "completion/";
    private static String COMPLETIONVERILOG = "completion/main_verilog";
    private static String COMPLETIONVHDL = "completion/main_vhdl";
    
    /** Creates a new instance of TemplateGenerator */
    public TemplateGenerator() {}
    
    public static String generateTemplateFromString(String templateStr, HashMap map) {
    	try {
    		String tstr = FileUtility.readFileContents(templateStr);
			Template template = new Template("name", new StringReader(tstr),new Configuration());
			StringWriter writer = new java.io.StringWriter();
            template.process(map, writer);
            return writer.toString();
		} catch (IOException e) {
			HardwareLog.logError(e);
		} catch (TemplateException e) {
			HardwareLog.logError(e);
		}
		return "Template Failed";
    }
    
    private static String getTabString() {
    	boolean ntabs = EditorsUI.getPreferenceStore().getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
    	int wid = EditorsUI.getPreferenceStore().getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
    	
    	if (ntabs) {
    		StringBuilder build = new StringBuilder();
    		for (int i=0;i<wid;i++) {
    			build.append(' ');
    		}
    		return build.toString();
    	}
    	return "\t";
    	
    }
    
    private static String convertDelimiters(String instr) {
    	String outstr = instr;
    	String str = System.getProperty("line.separator");
    	if (instr.contains("\r\n")) {
    		if (!str.contains("\r\n")) {
    			outstr.replace("\r\n", "\n");
    		}
    	}
    	else if (instr.contains("\n")) {
    		if (str.contains("\r\n")) {
    			outstr.replace("\n", "\r\n");
    		}
    	}
    	outstr = outstr.replace("\t", getTabString());
    	//return removeTrailingNewLine(outstr);
    	return outstr;
    }
    
    private static String removeTrailingNewLine(String instr) {
    	String outstr = instr;
    	while (outstr.length() > 0) {
    		String a = outstr.substring(outstr.length()-1);
    		if (a.equals("\r") || a.equals("\n")) {
    			outstr = outstr.substring(0,outstr.length()-2);
    		}
    		else break;
    	}
    	if (instr.endsWith("\n")) {
    		outstr = outstr + System.getProperty("line.separator");
    	}
    	return outstr;
    	
    }
    
    /**
     * Convenience Method for calling the template with only the object
     * @param templateName 
     * @param obj 
     * @return 
     */
    public static String generateTemplate(String templateName, Object obj) {
        HashMap map = new HashMap();
        map.put("object", obj);
        String ustr = generateTemplate(templateName,map);
        String tstr = new FreeFormatter(ustr).getString();
        
        return convertDelimiters(tstr);
    }
    
    /** Method to generate the hover output from a template. */
    public static String generateHoverTemplate(String templateName, Object object, int index, int type) {
        HashMap map = new HashMap();
        map.put("object",object);
        map.put("type", index);
        map.put("name", templateName);
        if (type == TEMPLATE_VHDL)
        	return generateTemplate(COMPLETIONVHDL,map);
        else
        	return generateTemplate(COMPLETIONVERILOG,map);
    }
    
    public static String generateTemplatewithHash(String templateName,HashMap map) {
    	return generateTemplate(templateName, map);
    }
    
    /**
     * Generates the expanded template for this structure
     * @param templateName
     * @param map
     * @return
     */
    private static String generateTemplate(String templateName, HashMap map) {
        try     {
            Template template = MainConfiguration.getTemplate(templateName +".ftl");
            
            StringWriter writer = new java.io.StringWriter();
            template.process(map, writer);
            return writer.toString();
        } catch (TemplateException ex) {
            HardwareLog.logError(ex);
        } catch (IOException ex) {
            HardwareLog.logError(ex);
        
        }catch (Exception ex) {
            HardwareLog.logError(ex);
        }
        
        return "";
    }
    
}
