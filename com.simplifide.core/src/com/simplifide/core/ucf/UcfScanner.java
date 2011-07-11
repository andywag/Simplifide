/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ucf;


import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;

public class UcfScanner extends SourceScanner{

	public static String[] keywords = new String[] {
		"AFTER",
		"AREA_GROUP",
		"ASYNC_REG",
		"BEL",
		"BEFORE",
		"BLKNM",
		"CLOCK_DEDICATED_ROUTE",
		"COMPGRP",
		"CONFIG_MODE",
		"DEFAULT",
		"DCI_CASCADE",
		"DCI_VALUE",
		"DIRECTED_ROUTING",
		"DISABLE",
		"DRIVE",
		"DROP_SPEC",
		"ENABLE",
		"ENABLE_SUSPEND",
		"FALLING",
		"FAST",
		"FEEDBACK",
		"FFS",
		"FILE",
		"FLOAT",
		"FROM",
		"HBLKNM",
		"HIGH",
		"HLUTNM",
		"HU_SET",
		"IBUF_DELAY_VALUE",
		"IFD_DELAY_VALUE",
		"IN",
		"INREG",
		"IOB",
		"IOBDELAY",
		"IODELAY_GROUP",
		"IOSTANDARD",
		"KEEP",
		"KEEPER",
		"KEEP_HIERARCHY",
		"LOC",
		"LOCATE",
		"LOCK_PINS",
		"LUTNM",
		"MAP",
		"MIODELAY_GROUP",
		"MAXDELAY",
		"MAX_FANOUT",
		"MAXSKEW",
		"NET",
		"NODELAY",
		"NS",
		"OFFSET",
		"OPT_EFFORT",
		"OPTIMIZE",
		"OUT",
		"PERIOD",
		"PHASE",
		"PIN",
		"POST_CRC",
		"POST_CRC_ACTION",
		"POST_CRC_FREQ",
		"POST_CRC_SIGNAL",
		"PRIORITY",
		"PROHIBIT",
		"PS",
		"PULLDOWN",
		"PULLUP",
		"REFERENCE_PIN",
		"RISING",
		"RLOC",
		"RLOC_ORIGIN",
		"RLOC_RANGE",
		"SAVE",
		"SLEW",
		"SLOW",
		"STEPPING",
		"SUSPEND",
		"SYSTEM_JITTER",
		"TEMPERATURE",
		"TIG",
		"TIMEGRP",
		"TIMESPEC",
		"TNM",
		"TNM_NET",
		"TO",
		"TPSYNC",
		"TPTHRU",
		"TS",
		"TS_",
		"THRU",
		"U_SET",
		"USE_RLOC",
		"VALID",
		"VCCAUX",
		"VOLTAGE",
		"VREF",
		"XBLKNM"};
	
    public static String[] totalkeywords;

	
	public UcfScanner(ColorManager provider) {
		super(provider);
	}
	
	@Override
	protected String[] getKeywords() {
            if (totalkeywords == null) {
                totalkeywords = new String[keywords.length*2];
                for (int i=0;i<keywords.length;i++) {
                    totalkeywords[2*i] = keywords[i];
                    totalkeywords[2*i+1] = keywords[i].toLowerCase();
                }
            }
		return totalkeywords;
	}
	
	

	@Override
	public IPredicateRule getSingleLineCommentRule(IToken tok) {
		return new EndOfLineRule("#", tok);
	}

	@Override
	public IPredicateRule getStringRule(IToken tok) {
		return new SingleLineRule("\"", "\"", tok, '\\');
	}
	
	public IPredicateRule getStringRule2(IToken tok) {
		//return new SingleLineRule("'", "'", tok, '\\');
		return null;
	}

	

	@Override
	public IPredicateRule getSingleHdlRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IPredicateRule getDefinedRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IRule getAltNumberRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
