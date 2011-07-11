/*
 * TransformList.java
 *
 * Created on April 15, 2007, 1:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker.transforms;

import java.util.Map;

import com.simplifide.base.basic.util.StringOps;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.ProjectLocationHandler;
import com.simplifide.core.source.SourceLocationHandler;

import freemarker.template.SimpleNumber;

/**
 *
 * @author Andy
 */
public class TransformList {
    
    /**
     * Class which surrounds the block with ${...}
     */
    public static TransformInterface TEMPLATE = new Template();
    public static TransformInterface MININDENT = new MinIndent();
    public static TransformInterface HYPERLINK = new HyperLink();
    /** Creates a new instance of TransformList */
    
    private static class Template implements TransformInterface{
        public Template() {}
        public String transformString(String inval, Map args) {
            return "${" + inval + "}";
        }
    }
    
    private static class MinIndent implements TransformInterface{
        public MinIndent() {}
        public String transformString(String inval, Map args) {
            SimpleNumber number = (SimpleNumber) args.get("value");
            int intValue = number.getAsNumber().intValue();
            String uval = StringOps.trimPreWhiteSpace(inval);
            String outval = StringOps.spaceString(intValue) + uval;
            return outval;
        }
    }
    
    private static class HyperLink implements TransformInterface {
    	private HyperLink() {}
    	public String transformString(String inval, Map args) {
            SimpleNumber projNum = (SimpleNumber) args.get("proj");
            SimpleNumber fileNum = (SimpleNumber) args.get("file");
            
            
            int projLoc = projNum.getAsNumber().intValue();
            int fileLoc = fileNum.getAsNumber().intValue();
            
            EclipseBaseProject proj = ProjectLocationHandler.getInstance().getLocation(projLoc);
            SourceFile sfile = SourceLocationHandler.getInstance().getLocation(fileLoc);
            
            String loc = StringOps.addQuote("../../" + proj.getname() + "/doc/" + sfile.getRealName() + ".html#" + inval);
            String outval = "<a href=" + loc + ">" + inval + "</a>";
            
            return outval;
        }
    }
    
    
}
