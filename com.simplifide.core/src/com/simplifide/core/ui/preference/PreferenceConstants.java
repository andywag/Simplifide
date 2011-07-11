/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import com.simplifide.core.CoreActivator;

public class PreferenceConstants {
	
	public static final String POSTFIX_VERILOG = ".verilog";
	public static final String POSTFIX_VHDL    = ".vhdl";
	
	public static final String ERROR_SYNTAX = CoreActivator.PLUGIN_ID + ".errors.syntax";
	public static final String ERROR_NOT_DEFINED = CoreActivator.PLUGIN_ID + ".errors.notdefined";
	public static final String ERROR_TYPE_MISMATCH = CoreActivator.PLUGIN_ID + ".errors.typemismatch";
    public static final String WARNING_LATCH = CoreActivator.PLUGIN_ID + ".warnings.latch";
    public static final String WARNING_NOT_ASSIGNED = CoreActivator.PLUGIN_ID + ".warnings.not_assigned";
    public static final String WARNING_NOT_USED = CoreActivator.PLUGIN_ID + ".warnings.not_used";
        
        

    public static final String COLOR_BASE = CoreActivator.PLUGIN_ID + ".color.";
    public static final String BOLD_BASE  = CoreActivator.PLUGIN_ID + ".bold.";
    public static final String ITALIC_BASE = CoreActivator.PLUGIN_ID + ".italic.";
    
    public static final String COLOR_BASE_DEFAULT = "default";
    public static final String COLOR_BASE_KEY = "key";
    public static final String COLOR_BASE_COMMENT = "comment";
    public static final String COLOR_BASE_STRING = "string";
    public static final String COLOR_BASE_HDLDOC = "hdldoc";
    public static final String COLOR_BASE_NUMBER = "number";   
    public static final String COLOR_BASE_PUNCTUATION = "seperator";
    public static final String COLOR_BASE_DEFINE = "define";
    public static final String COLOR_BASE_TYPE = "type";
    
    public static final String COLOR_SCRIPT_BACKGROUND   = "script";
    public static final String COLOR_GENERATE_BACKGROUND = "generate";
    
    public static final String[] COLOR_LIST = new String[] {"Default","Keyword","String","Number","Comment","HdlDoc","Punctuation","Define","Type",
    	"Script Background","Generate Background"};
    public static final String[] COLOR_LIST_KEY = new String[] 
           {COLOR_BASE_DEFAULT, COLOR_BASE_KEY, COLOR_BASE_STRING,
    		COLOR_BASE_NUMBER, COLOR_BASE_COMMENT,  COLOR_BASE_HDLDOC, 
    		COLOR_BASE_PUNCTUATION,COLOR_BASE_DEFINE,COLOR_BASE_TYPE, COLOR_SCRIPT_BACKGROUND,
    		COLOR_GENERATE_BACKGROUND};
    	
    
	public static final String INDENT_LENGTH = CoreActivator.PLUGIN_ID + ".indent.length";
	public static final String INDENT_TAB    = CoreActivator.PLUGIN_ID + ".indent.tab";
	public static final String INDENT_ENABLE    = CoreActivator.PLUGIN_ID + ".indent.enable";
	
	public static final String COLOR_DEFAULT = COLOR_BASE + COLOR_BASE_DEFAULT;
	public static final String COLOR_KEY     = COLOR_BASE + COLOR_BASE_KEY;
	public static final String COLOR_COMMENT = COLOR_BASE + COLOR_BASE_COMMENT;
	public static final String COLOR_STRING  = COLOR_BASE + COLOR_BASE_STRING;
	public static final String COLOR_HDL_DOC = COLOR_BASE + COLOR_BASE_HDLDOC;
	public static final String COLOR_NUMBER = COLOR_BASE + COLOR_BASE_NUMBER;
	public static final String COLOR_PUNCTUATION = COLOR_BASE + COLOR_BASE_PUNCTUATION;
	public static final String COLOR_DEFINE = COLOR_BASE + COLOR_BASE_DEFINE;
	public static final String COLOR_TYPE = COLOR_BASE + COLOR_BASE_TYPE;
	public static final String COLOR_SCRIPT = COLOR_BASE + COLOR_SCRIPT_BACKGROUND;
	public static final String COLOR_GENERATE = COLOR_BASE + COLOR_GENERATE_BACKGROUND;
	
	public static final String BOLD_DEFAULT  = BOLD_BASE + COLOR_BASE_DEFAULT;
	public static final String BOLD_KEY     = BOLD_BASE + COLOR_BASE_KEY;
	public static final String BOLD_COMMENT = BOLD_BASE + COLOR_BASE_COMMENT;
	public static final String BOLD_STRING  = BOLD_BASE + COLOR_BASE_STRING;
	public static final String BOLD_HDL_DOC = BOLD_BASE + COLOR_BASE_HDLDOC;
	public static final String BOLD_NUMBER =   BOLD_BASE + COLOR_BASE_NUMBER;
	public static final String BOLD_PUNCTUATION = BOLD_BASE + COLOR_BASE_PUNCTUATION;
	public static final String BOLD_DEFINE = BOLD_BASE + COLOR_BASE_DEFINE;
	public static final String BOLD_TYPE = BOLD_BASE + COLOR_BASE_TYPE;
	public static final String BOLD_SCRIPT = BOLD_BASE + COLOR_SCRIPT_BACKGROUND;
	public static final String BOLD_GENERATE = BOLD_BASE + COLOR_GENERATE_BACKGROUND;
	
	
	public static final String ITALIC_DEFAULT = ITALIC_BASE + COLOR_BASE_DEFAULT;
	public static final String ITALIC_KEY     = ITALIC_BASE + COLOR_BASE_KEY;
	public static final String ITALIC_COMMENT = ITALIC_BASE + COLOR_BASE_COMMENT;
	public static final String ITALIC_STRING  = ITALIC_BASE + COLOR_BASE_STRING;
	public static final String ITALIC_HDL_DOC = ITALIC_BASE + COLOR_BASE_HDLDOC;
	public static final String ITALIC_NUMBER  = ITALIC_BASE + COLOR_BASE_NUMBER;
	public static final String ITALIC_PUNCTUATION = ITALIC_BASE + COLOR_BASE_PUNCTUATION;
	public static final String ITALIC_DEFINE = ITALIC_BASE + COLOR_BASE_DEFINE;
	public static final String ITALIC_TYPE = ITALIC_BASE + COLOR_BASE_TYPE;
	public static final String ITALIC_SCRIPT = ITALIC_BASE + COLOR_SCRIPT_BACKGROUND;
	public static final String ITALIC_GENERATE = ITALIC_BASE + COLOR_GENERATE_BACKGROUND;
	
	public static final String COMPLETE_AUTO_COMMA = CoreActivator.PLUGIN_ID + ".complete.comma";
	public static final String COMPLETE_AUTO_ACTIVATION = CoreActivator.PLUGIN_ID + ".complete.autoactivation";
	public static final String COMPLETE_AUTO_INSERT = CoreActivator.PLUGIN_ID + ".complete.autoinsert";
	public static final String COMPLETE_AUTO_TIME = CoreActivator.PLUGIN_ID + ".complete.autotime";
	public static final String COMPLETE_FOREGROUND = CoreActivator.PLUGIN_ID + ".complete.foreground";
	public static final String COMPLETE_BACKGROUND = CoreActivator.PLUGIN_ID + ".complete.background";
	
	public static final String HOVER_ENABLED = CoreActivator.PLUGIN_ID + ".hover.enabled";
	public static final String HOVER_HTML_ENABLED = CoreActivator.PLUGIN_ID + ".hover.html";
	public static final String HOVER_NATURAL = CoreActivator.PLUGIN_ID + ".hover.natural";

	
	public static final String LICENSE_STRING     = CoreActivator.PLUGIN_ID + ".license.string";
	public static final String LICENSE_TYPE       = CoreActivator.PLUGIN_ID + ".license.string";
	public static final String LICENSE_EXPIRATION = CoreActivator.PLUGIN_ID + ".license.string";

	
	
	public static final String LICENSE_DOWNLOAD = CoreActivator.PLUGIN_ID + ".license.time";
        
        public static final String LICENSE_DOWNLOAD_ONE_SHOT = CoreActivator.PLUGIN_ID + ".license.download";
        public static final String LICENSE_FTP_ONE_SHOT = CoreActivator.PLUGIN_ID + ".license.ftp";
        
        public static final String BUILD_ENABLED = CoreActivator.PLUGIN_ID + ".build.enabled";
        //public static final String BUILD_LIBRARY_STORE = CoreActivator.PLUGIN_ID + ".build.librarystore";
        public static final String DEBUG_INFO = CoreActivator.PLUGIN_ID + ".debug.info";
        public static final String PARSE_DEBUG_INFO = CoreActivator.PLUGIN_ID + ".debug.parse.info";
        
        public static final String SUITE_DIR_BUILD = CoreActivator.PLUGIN_ID + ".suite.build";
        public static final String SUITE_DIR_DOC   = CoreActivator.PLUGIN_ID + ".suite.doc";
        public static final String SUITE_DIR_ROUTE = CoreActivator.PLUGIN_ID + ".suite.route";
        public static final String SUITE_DIR_SCRIPT  = CoreActivator.PLUGIN_ID + ".suite.script";
        public static final String SUITE_DIR_SYNTHESIS  = CoreActivator.PLUGIN_ID + ".suite.synthesis";
        public static final String SUITE_DIR_TEST  = CoreActivator.PLUGIN_ID + ".suite.test";
       
        public static final String PROJECT_DIR_BUILD = CoreActivator.PLUGIN_ID + ".project.build";
        public static final String PROJECT_DIR_DOC   = CoreActivator.PLUGIN_ID + ".project.doc";
        public static final String PROJECT_DIR_ROUTE = CoreActivator.PLUGIN_ID + ".project.route";
        public static final String PROJECT_DIR_SCRIPT  = CoreActivator.PLUGIN_ID + ".project.script";
        public static final String PROJECT_DIR_SYNTHESIS  = CoreActivator.PLUGIN_ID + ".project.synthesis";
        public static final String PROJECT_DIR_TEST  = CoreActivator.PLUGIN_ID + ".project.test";
        public static final String PROJECT_DIR_SUB  = CoreActivator.PLUGIN_ID + ".project.sub";
        
        public static final String LIBRARY_DIR_BUILD = CoreActivator.PLUGIN_ID + ".library.build";
        public static final String LIBRARY_DIR_DOC   = CoreActivator.PLUGIN_ID + ".library.doc";
        public static final String LIBRARY_DIR_ROUTE = CoreActivator.PLUGIN_ID + ".library.route";
        public static final String LIBRARY_DIR_SCRIPT  = CoreActivator.PLUGIN_ID + ".library.script";
        public static final String LIBRARY_DIR_SYNTHESIS  = CoreActivator.PLUGIN_ID + ".library.synthesis";
        public static final String LIBRARY_DIR_TEST  = CoreActivator.PLUGIN_ID + ".library.test";
        public static final String LIBRARY_DIR_SUB  = CoreActivator.PLUGIN_ID + ".library.sub";

        public static final String SUBPROJECT_DIR_BUILD = CoreActivator.PLUGIN_ID + ".subproject.build";
        public static final String SUBPROJECT_DIR_DOC   = CoreActivator.PLUGIN_ID + ".subproject.doc";
        public static final String SUBPROJECT_DIR_ROUTE = CoreActivator.PLUGIN_ID + ".subproject.route";
        public static final String SUBPROJECT_DIR_SCRIPT  = CoreActivator.PLUGIN_ID + ".subproject.script";
        public static final String SUBPROJECT_DIR_SYNTHESIS  = CoreActivator.PLUGIN_ID + ".subproject.synthesis";
        public static final String SUBPROJECT_DIR_TEST  = CoreActivator.PLUGIN_ID + ".subproject.test";
        
        public static final String SUITE_DIR_CONFIG = CoreActivator.PLUGIN_ID + ".suite.dirconfig";
        public static final String PROJECT_DIR_CONFIG = CoreActivator.PLUGIN_ID + ".project.dirconfig";
        public static final String LIBRARY_DIR_CONFIG = CoreActivator.PLUGIN_ID + ".library.dirconfig";
        public static final String SUBPROJECT_DIR_CONFIG = CoreActivator.PLUGIN_ID + ".subproject.dirconfig";
        
        public static final String WORKSPACE_DIR_CONFIG = CoreActivator.PLUGIN_ID + ".workspace.dirconfig";

        
        public static final String FILE_HEADER = CoreActivator.PLUGIN_ID + ".file.header";
       
       
        public static final String SCRIPT_RELEASE      = CoreActivator.PLUGIN_ID + ".script.release";
        public static final String SCRIPT_RELEASE_PATH = CoreActivator.PLUGIN_ID + ".script.release_path";
        public static final String SCRIPT_EXTRA_PATH1  = CoreActivator.PLUGIN_ID + ".script.extra_path1";
        public static final String SCRIPT_EXTRA_PATH2  = CoreActivator.PLUGIN_ID + ".script.extra_path2";
        
        public static final String MAXIMUM_FILE_LENGTH = CoreActivator.PLUGIN_ID + ".file.length";

        public static final String FILEWIZARD_VERILOG_HEADERDIR = CoreActivator.PLUGIN_ID + ".filewizard.verilogheaderdir";
        public static final String FILEWIZARD_VERILOG_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.verilogtemplate";
        public static final String FILEWIZARD_VERILOG_CLASS = CoreActivator.PLUGIN_ID + ".filewizard.class";
        public static final String FILEWIZARD_VERILOG_INTERFACE = CoreActivator.PLUGIN_ID + ".filewizard.interface";
        public static final String FILEWIZARD_VERILOG_PROGRAM = CoreActivator.PLUGIN_ID + ".filewizard.program";
        public static final String FILEWIZARD_VERILOG_PACKAGE = CoreActivator.PLUGIN_ID + ".filewizard.package";
        
        public static final String FILEWIZARD_VHDL_HEADERDIR = CoreActivator.PLUGIN_ID + ".filewizard.vhdlheaderdir";
        public static final String FILEWIZARD_VHDL_ENTITY_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.vhdlentitytemplate";
        public static final String FILEWIZARD_VHDL_ARCHITECTURE_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.vhdlarchitecturetemplate";
        public static final String FILEWIZARD_VHDL_ENTITYARCHITECTURE_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.vhdlentityarchitecturetemplate";
        public static final String FILEWIZARD_VHDL_LIBRARY_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.vhdllibrarytemplate";
        public static final String FILEWIZARD_VHDL_TESTBENCH = CoreActivator.PLUGIN_ID + ".filewizard.vhdltestbenchtemplate";
        public static final String FILEWIZARD_VHDL_TESTER = CoreActivator.PLUGIN_ID + ".filewizard.vhdltestertemplate";

        public static final String FILEWIZARD_VHDL_PACKAGE_TEMPLATE = CoreActivator.PLUGIN_ID + ".filewizard.vhdlpackagetemplate";

        public static final String EDITOR_MARK_OCCURENCE = CoreActivator.PLUGIN_ID + ".editor.mark.occurence";
        public static final String EDITOR_PAREN_MATCH = CoreActivator.PLUGIN_ID + ".editor.paren.match";
        public static final String EDITOR_MARK_COLOR = CoreActivator.PLUGIN_ID + ".editor.mark.color";
        public static final String EDITOR_PAREN_COLOR = CoreActivator.PLUGIN_ID + ".editor.paren.color";

        public static final String UPDATE_DISABLE = CoreActivator.PLUGIN_ID + ".update.disable";
        
        public static final String VHDL_CAP_KEYWORDS = CoreActivator.PLUGIN_ID + ".keyword.cap";
        public static final String CONFIG_DIRECTORY = CoreActivator.PLUGIN_ID + ".config.directory2";
        public static final String NEW_CONFIG_DIRECTORY = CoreActivator.PLUGIN_ID + ".base.config.file";
        
        public static final String VHDL_ENABLE = CoreActivator.PLUGIN_ID + ".vhdl.enable";
        public static final String VERILOG_ENABLE = CoreActivator.PLUGIN_ID + ".verilog.enable";
        public static final String SYSTEM_VERILOG_ENABLE = CoreActivator.PLUGIN_ID + ".systemverilog.enable";
        
        public static final String SCRIPT_PATH_NAME = CoreActivator.PLUGIN_ID + ".script.path.name";
        public static final String SCRIPT_STARTUP_NAME = CoreActivator.PLUGIN_ID + ".script.startup.name";
        public static final String EDITOR_ENABLE_AUTO_EDITS = CoreActivator.PLUGIN_ID + ".editor.autoedits";
        

        public static final String EMACS_PREFIX = CoreActivator.PLUGIN_ID + ".emacs.prefix";
        public static final String EMACS_VHDL_BEAUTY = CoreActivator.PLUGIN_ID + ".emacs.vhdl.beautify";
        public static final String EMACS_VERILOG_AUTO = CoreActivator.PLUGIN_ID + ".emacs.auto";
        public static final String EMACS_VERILOG_INJECT_AUTO = CoreActivator.PLUGIN_ID + ".emacs.inject";
        public static final String EMACS_VERILOG_DELETE_AUTO = CoreActivator.PLUGIN_ID + ".emacs.delete";
        public static final String EMACS_VERILOG_INDENT = CoreActivator.PLUGIN_ID + ".emacs.indent";
        public static final String EMACS_CUSTOM1 = CoreActivator.PLUGIN_ID + ".emacs.custom1";
        public static final String EMACS_CUSTOM2 = CoreActivator.PLUGIN_ID + ".emacs.custom2";
        
        public static final String FORMAT_INDENT_NORMAL = CoreActivator.PLUGIN_ID + ".format.indent.normal";
        public static final String FORMAT_INDENT_MODULE = CoreActivator.PLUGIN_ID + ".format.indent.module";

        public static final String REFACTOR_REPLACE_EXPR = CoreActivator.PLUGIN_ID + ".refactor.instance.reg";
        public static final String REFACTOR_REPLACE_DEST = CoreActivator.PLUGIN_ID + ".refactor.instance.dest";

        
}
