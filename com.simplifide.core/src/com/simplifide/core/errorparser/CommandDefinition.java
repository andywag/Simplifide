package com.simplifide.core.errorparser;

public class CommandDefinition {

	
	public static int TYPE_PROJECT = 0;
	public static int TYPE_SUITE   = 1;
	//public static int TYPE_COMMAND = 2;
	//public static int TYPE_SUITE_COMMAND = 3;
	
	public static String TYPE_PROJECT_MAKEFILE = "Project Makefile";
	public static String TYPE_SUITE_MAKEFILE = "Suite Makefile";
	//public static String TYPE_COMMAND_LABEL = "Project Command";
	//public static String TYPE_COMMAND_SUITE = "Suite Command";

	public static String[] TYPES = {TYPE_PROJECT_MAKEFILE,TYPE_SUITE_MAKEFILE};
	
	public static String SAVE_YES = "Yes";
	public static String SAVE_NO = "Noe";
	
	public static String[] SAVES = {SAVE_YES,SAVE_NO};

	
	private String command;
	private int type;
	private int parserType;
	private boolean onSave;
	
	
	public CommandDefinition(String command, int type, int parser, boolean onSave) {
		this.command = command;
		this.type = type;
		this.setParserType(parser);
		this.onSave = onSave;
	}
	
	public String getTextTypeDefinition() {
		return TYPES[this.type];
	}
	
	public String getTextTypeSave() {
		if (onSave) return SAVE_YES;
		return SAVE_NO;
	}
	
	public IErrorParser getParser() {
		return ErrorParserManager.getErrorParserCopy(this.getActualParserType());
	}
	
	public String getTextParserType() {
		String type = this.getActualParserType(); 
		String[] ut = type.split("\\.");
		return ut[ut.length - 1];
	}
	
	public String getActualParserType() {
		String[] str = ErrorParserExtensionManager.getErrorParserAvailableIds();
		if (parserType < 0 || parserType > str.length) {
			return "Error";
		}
		return str[parserType];
	}
	
	public static String[] getErrorTypes() {
		String[] ids = ErrorParserExtensionManager.getErrorParserAvailableIds();
		String[] types = new String[ids.length];
		int index = 0;
		for (String id : ids) {
			String type = ErrorParserExtensionManager.getErrorParserAvailableIds()[index];
			String[] ut = type.split("\\.");
			types[index] = ut[ut.length - 1];
			index++;
		}
		return types;
		
	}
	
	public static CommandDefinition dummyCommand() {
		return new CommandDefinition("target",
				TYPE_PROJECT,
				0,
				false);
	}
	
	public boolean isProject() {
		if (this.type == TYPE_PROJECT) return true;
		return false;
	}
	
	public boolean isSuite() {
		if (this.type == TYPE_SUITE ) return true;
		return false;
	}
	
	public boolean isMakeAction() {
		if (this.type == TYPE_PROJECT || this.type == TYPE_SUITE) return true;
		return false;
	}
	
	
	public void setCommand(String command) {
		this.command = command;
	}
	public String getCommand() {
		return command;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	public void setOnSave(boolean onSave) {
		this.onSave = onSave;
	}
	public boolean isOnSave() {
		return onSave;
	}

	public void setParserType(int parserType) {
		this.parserType = parserType;
	}

	public int getParserType() {
		return parserType;
	}
	
}
