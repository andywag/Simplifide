// $ANTLR 2.7.7 (2006-11-01): "expandedsystemverilog.g" -> "SystemVerilogParser.java"$

    package com.simplifide.base.verilog.parse.grammar.system;
    import com.simplifide.base.sourcefile.antlr.tok.*;


public interface SystemVerilogLexerTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int ROOT = 4;
	int DESIGNUNIT = 5;
	int CONTEXTCLAUSE = 6;
	int CONTEXTITEM = 7;
	int DEFINEDIRECTIVE = 8;
	int INCLUDEDIRECTIVE = 9;
	int MODULE = 10;
	int MODULEHEAD = 11;
	int MODULEBODY = 12;
	int MODULEITEM = 13;
	int MODULEDEC = 14;
	int MODULENAME = 15;
	int PORTCLAUSE = 16;
	int PORTLIST = 17;
	int PORTELEMENT = 18;
	int PORTTOP = 19;
	int PORT = 20;
	int PORTNODOT = 21;
	int PORTDOT = 22;
	int PORTEXPRESSION = 23;
	int VARDEC = 24;
	int VARTYPE = 25;
	int VARRANGE = 26;
	int VARLIST = 27;
	int VARNAME = 28;
	int REGMEM = 29;
	int REGMEMARRAY = 30;
	int PARDEC = 31;
	int PARASSIGN = 32;
	int CONTINUOUS_ASSIGN = 33;
	int ASSIGNLIST = 34;
	int ASSIGNMENT = 35;
	int IDENTRANGE = 36;
	int SEGMENTRANGE = 37;
	int QUESTTOP = 38;
	int QUESTIONSEGMENT = 39;
	int PAROP = 40;
	int UNOP = 41;
	int BINOP = 42;
	int BINOPSMALL = 43;
	int CONCAT = 44;
	int ALWAYSTOP = 45;
	int ALWAYSHEAD = 46;
	int EVENTCONTROL = 47;
	int EVENTEXPRESSION = 48;
	int EVENTEXPRESSIONSUB = 49;
	int TOTALASSIGN = 50;
	int CONDITIONTOP = 51;
	int CONDITIONHEAD = 52;
	int CONDITIONELSE = 53;
	int CASETOP = 54;
	int CASEHEAD = 55;
	int CASELIST = 56;
	int CASEEXPRESSION = 57;
	int CASENORMAL = 58;
	int CASEDEFAULT = 59;
	int BEGENDBLOCK = 60;
	int SEQBLOCKNAMEQ = 61;
	int STATELIST = 62;
	int MODITEM = 63;
	int MODINSTANCETOP = 64;
	int MODINSTANCE = 65;
	int INSTANCENAME = 66;
	int INSTANCEPORTLIST = 67;
	int INSTANCEPORT = 68;
	int PORTCON = 69;
	int IDENTIFIERPATH = 70;
	int NAME = 71;
	int NAMERANGE = 72;
	int NAMEDOT = 73;
	int NAMEEXPRESSION = 74;
	int NAMEPOUNDEXPRESSION = 75;
	int DRIVESTRENGTHQ = 76;
	int DELAYQ = 77;
	int DELAYOREVENTCONTROLQ = 78;
	int TASK = 79;
	int FUNCTION = 80;
	int TASKVARDECLARATION = 81;
	int FUNCTIONVARDECLARATION = 82;
	int RANGEORTYPE = 83;
	int PARAMETEROVERRIDE = 84;
	int IFDEFDIRECTIVE = 85;
	int IFDEFHEAD = 86;
	int IFDEFCOND = 87;
	int TIMESCALEDIRECTIVE = 88;
	int TICKIFDEF = 89;
	int TICKIFNDEF = 90;
	int TICKELSE = 91;
	int TICKENDIF = 92;
	int TICKDEFINE = 93;
	int TICKINCLUDE = 94;
	int TICKTIMESCALE = 95;
	int TICKUNDEF = 96;
	int GENERATEITEM = 97;
	int RANGEPARAM = 98;
	int PRIMITIVEDEFINITION = 99;
	int TABLEDEFINITION = 100;
	int SPECIFYBLOCK = 101;
	int SPECIFYITEM = 102;
	int SPECPARAMDECLARATION = 103;
	int PATHDECLARATION = 104;
	int EDGESENSITIVEPATHDECLARATION = 105;
	int SDPD = 106;
	int SIMPLEPATHDECLARATION = 107;
	int PARALLELPATHDECLARATION = 108;
	int FULLPATHDESCRIPTOR = 109;
	int LEVELSENSITIVEPATHDECLARATION = 110;
	int RANGEORTYPEQ = 111;
	int AUTOMATICQ = 112;
	int PREBLOCKNAMEQ = 113;
	int UNIQUEPRIORITY = 114;
	int MATCHES = 115;
	int NAMECOLON = 116;
	int INITIALORFINAL = 117;
	int WAITSTATEMENT = 118;
	int DISABLESTATEMENT = 119;
	int LITERAL_endmodule = 120;
	int LITERAL_module = 121;
	int LITERAL_macromodule = 122;
	int SEMI = 123;
	int LPAREN = 124;
	int RPAREN = 125;
	int COMMA = 126;
	int LBRACE = 127;
	int RBRACE = 128;
	int LBRACK = 129;
	int COLON = 130;
	int RBRACK = 131;
	int LITERAL_generate = 132;
	int LITERAL_endgenerate = 133;
	// "1'b0" = 134
	// "1'b1" = 135
	// "1'bx" = 136
	int NUMBER = 137;
	int LITERAL_primitive = 138;
	int LITERAL_endprimitive = 139;
	int LITERAL_table = 140;
	int LITERAL_endtable = 141;
	int LITERAL_task = 142;
	int LITERAL_endtask = 143;
	int LITERAL_function = 144;
	int LITERAL_endfunction = 145;
	int LITERAL_automatic = 146;
	int LITERAL_integer = 147;
	int LITERAL_real = 148;
	int LITERAL_QQQQQDDDDDEEEEEEEFFFFFFFf = 149;
	int LITERAL_PPPPPQQDDDDDEEEEEEEFFFFFFFf = 150;
	int ASSIGN = 151;
	int LITERAL_tri = 152;
	// "tri1" = 153
	// "supply0" = 154
	int LITERAL_wand = 155;
	int LITERAL_triand = 156;
	// "tri0" = 157
	// "supply1" = 158
	int LITERAL_wor = 159;
	int LITERAL_trior = 160;
	int LITERAL_trireg = 161;
	int LITERAL_scalared = 162;
	int LITERAL_vectored = 163;
	int LITERAL_assign = 164;
	int LITERAL_defparam = 165;
	int LITERAL_small = 166;
	int LITERAL_medium = 167;
	int LITERAL_large = 168;
	// "strong0" = 169
	// "pull0" = 170
	// "weak0" = 171
	// "highz0" = 172
	// "strong1" = 173
	// "pull1" = 174
	// "weak1" = 175
	// "highz1" = 176
	int DOLLAR = 177;
	int PLUS = 178;
	int MINUS = 179;
	int LITERAL_and = 180;
	int LITERAL_nand = 181;
	int LITERAL_or = 182;
	int LITERAL_nor = 183;
	int LITERAL_xor = 184;
	int LITERAL_xnor = 185;
	int LITERAL_buf = 186;
	// "bufif0" = 187
	// "bufif1" = 188
	int LITERAL_not = 189;
	// "notif0" = 190
	// "notif1" = 191
	int LITERAL_pulldown = 192;
	int LITERAL_pullup = 193;
	int LITERAL_nmos = 194;
	int LITERAL_rnmos = 195;
	int LITERAL_pmos = 196;
	int LITERAL_rpmos = 197;
	int LITERAL_cmos = 198;
	int LITERAL_rcmos = 199;
	int LITERAL_tran = 200;
	int LITERAL_rtran = 201;
	// "rtranif0" = 202
	// "tranif1" = 203
	// "rtranif1" = 204
	int POUND = 205;
	int DOT = 206;
	int LITERAL_initial = 207;
	int LITERAL_final = 208;
	int LITERAL_always = 209;
	int LITERAL_always_latch = 210;
	int LITERAL_always_comb = 211;
	int LITERAL_always_ff = 212;
	int LITERAL_if = 213;
	int LITERAL_else = 214;
	int LITERAL_unique = 215;
	int LITERAL_priority = 216;
	int LITERAL_matches = 217;
	int LITERAL_endcase = 218;
	int LITERAL_default = 219;
	int LITERAL_case = 220;
	int LITERAL_casez = 221;
	int LITERAL_casex = 222;
	int LITERAL_forever = 223;
	int LITERAL_repeat = 224;
	int LITERAL_while = 225;
	int LITERAL_for = 226;
	int LITERAL_wait = 227;
	int TRIGGER = 228;
	int LITERAL_disable = 229;
	int LITERAL_begin = 230;
	int LITERAL_end = 231;
	int LITERAL_fork = 232;
	int LITERAL_join = 233;
	int LITERAL_deassign = 234;
	int LITERAL_force = 235;
	int LITERAL_release = 236;
	int LITERAL_specify = 237;
	int LITERAL_endspecify = 238;
	int LITERAL_specparam = 239;
	int PPATH = 240;
	int FPATH = 241;
	// "$setup" = 242
	// "$hold" = 243
	// "$period" = 244
	// "$width" = 245
	// "$skew" = 246
	// "$recovery" = 247
	// "$setuphold" = 248
	// "&&&" = 249
	int LITERAL_posedge = 250;
	int LITERAL_negedge = 251;
	int LITERAL_edge = 252;
	// "0x" = 253
	// "1x" = 254
	int IDENTIFIER = 255;
	int LCURLY = 256;
	int RCURLY = 257;
	int STRING = 258;
	int QUESTION = 259;
	// "asdfaslkdjfa;sljdf;alskjfd;aslkjdf;ajlsdf" = 260
	int LNOT = 261;
	int BNOT = 262;
	int BAND = 263;
	int RNAND = 264;
	int BOR = 265;
	int RNOR = 266;
	int BXOR = 267;
	int RXNOR = 268;
	int STAR = 269;
	int DIV = 270;
	int MOD = 271;
	int EQUAL = 272;
	int NOT_EQ = 273;
	int EQ_CASE = 274;
	int NOT_EQ_CASE = 275;
	int LAND = 276;
	int LOR = 277;
	int LT_ = 278;
	int LE = 279;
	int GT = 280;
	int GE = 281;
	int BXNOR = 282;
	int SR = 283;
	int SL = 284;
	int SRS = 285;
	int SLS = 286;
	int POW = 287;
	int PLUSPLUS = 288;
	int MINMIN = 289;
	int DOUBLECOLON = 290;
	int LITERAL_int = 291;
	int ESCAPED_IDENTIFIER = 292;
	int DOLLAR_IDENTIFIER = 293;
	int AT = 294;
	int LITERAL_iff = 295;
	int LITERAL_s = 296;
	int LITERAL_ms = 297;
	int LITERAL_us = 298;
	int LITERAL_ns = 299;
	int LITERAL_ps = 300;
	int LITERAL_fs = 301;
	int LITERAL_step = 302;
	int TICKTICK = 303;
	int TICKQUOTE = 304;
	int CASTPAREN = 305;
	int CASTCURLY = 306;
	int ESCAPE_NEWLINE = 307;
	int ESC = 308;
	int HEX_DIGIT = 309;
	int DEFINE = 310;
	int VOCAB = 311;
	int SIZED_NUMBER = 312;
	int SIZE = 313;
	int BASE = 314;
	int SIZED_DIGIT = 315;
	int UNSIZED_NUMBER = 316;
	int DIGIT = 317;
	int HEXDIGIT = 318;
	int EXPONENT = 319;
	int WS_ = 320;
	int NEWLINE_ = 321;
	int SL_COMMENT = 322;
	int ML_COMMENT = 323;
	int OTHER_SPECIAL_CHARACTER = 324;
	int IMPORTCLAUSE = 325;
	int SYSVARDEC = 326;
	int SYSVARTYPE = 327;
	int SIGNTYPE = 328;
	int IOPORTDEC = 329;
	int IODEC = 330;
	int SYSNODEC = 331;
	int STRUCTDEF = 332;
	int PACKTYPE = 333;
	int STRUCTTYPE = 334;
	int TOPDEC = 335;
	int CONSTDEC = 336;
	int INPUTOUTPUTDEC = 337;
	int SYSDEC = 338;
	int DECTYPE = 339;
	int DECNOTYPE = 340;
	int PACKAGE = 341;
	int IMPORTSTATE = 342;
	int VARIDENT = 343;
	int FIRSTIDENT = 344;
	int LOCALIDENT = 345;
	int MODULEDECLARATION = 346;
	int MODULEINSTANTIATION = 347;
	int MODULEINSTANCE = 348;
	int NAMEOFINSTANCE = 349;
	int LISTOFPORTORDERED = 350;
	int LISTOFPORTNAMED = 351;
	int NAMEPORTSTAR = 352;
	int NAMEPORTEXPR = 353;
	int NAMEPORTIDENT = 354;
	int PARAMETERVALUEASSIGNMENT = 355;
	int ORDEREDPARAMETERLIST = 356;
	int NAMEDPARAMETERLIST = 357;
	int ORDEREDPARAMETERASSIGNMENT = 358;
	int NAMEDPARAMETERASSIGNMENT = 359;
	int PARAMETERPORTLIST = 360;
	int ANSIPORTLIST = 361;
	int NOANSIPORTLIST = 362;
	int DATATYPEORNULL = 363;
	int PARAMETERDIMENSIONNODE = 364;
	int TYPEDECINTEGERVECTOR = 365;
	int TYPEDECINTEGERATOM = 366;
	int TYPEDECIDENTIFIER = 367;
	int TYPEDECNONINTEGER = 368;
	int TYPEDECIO = 369;
	int TYPEDECVIRTUAL = 370;
	int SIGNING = 371;
	int PACKEDDIMENSION = 372;
	int PORTTYPENET = 373;
	int PORTTYPETRIREG = 374;
	int PORTTYPEEMPTY = 375;
	int RANGEQ = 376;
	int TYPEDEF = 377;
	int TYPEDEFDATATYPE = 378;
	int LIFETIME = 379;
	int VARIABLEDECLARATION = 380;
	int LISTOFVARIABLEIDENTIFIERS = 381;
	int VARIABLEIDENT = 382;
	int IODECLARATIONTOP = 383;
	int IODECLARATION = 384;
	int NETDECLARATION = 385;
	int LISTOFNETDECLASSIGNMENTS = 386;
	int NETDECLARATIONASSIGNMENT = 387;
	int PARAMETERDECLARATION = 388;
	int PARAMETERDECLARATIONSEMI = 389;
	int LISTOFPARAMASSIGNMENTS = 390;
	int PARAMASSIGNMENT = 391;
	int ANSIPORTLISTQ = 392;
	int GENERATEIF = 393;
	int GENERATECASE = 394;
	int GENERATEFOR = 395;
	int GENERATEMODULEBLOCK = 396;
	int GENERATEMODULEBLOCKIDENT = 397;
	int GENERATEIDENTIFIER = 398;
	int GENERATEFORHEAD = 399;
	int COLONIDENTIFIERQ = 400;
	int GENVARASSIGNMENT = 401;
	int GENVARDECLASSIGNMENT = 402;
	int GENERATEMODULECASEITEMNORMAL = 403;
	int GENERATEMODULECASEITEMDEFAULT = 404;
	int PACKEDQ = 405;
	int TAGGEDQ = 406;
	int TASKFUNCTIONPREFIX = 407;
	int TASKFUNCTIONPORTLIST = 408;
	int TASKBODYDECLARATION = 409;
	int TASKHEADDECLARATION = 410;
	int FUNCTIONBODYDECLARATION = 411;
	int TFPORTITEM = 412;
	int TFPORTLIST = 413;
	int TFPORTDIRECTION = 414;
	int FUNCTIONDATATYPE = 415;
	int STRUCTUNION = 416;
	int STRUCTUNIONMEMBERLIST = 417;
	int STRUCTUNIONMEMBER = 418;
	int ENUMTYPE = 419;
	int ENUMDECTYPE = 420;
	int ENUMDECTYPEIDENT = 421;
	int ENUMLIST = 422;
	int ENUMNAMEDECLARATION = 423;
	int ASSIGNEXPRESSIONQ = 424;
	int EXTERNQ = 425;
	int CONSTQ = 426;
	int PARBLOCK = 427;
	int CLASSDECLARATION = 428;
	int CLASSBODY = 429;
	int CLASSBODYLIST = 430;
	int CLASSHEAD = 431;
	int CLASSMETHOD = 432;
	int CLASSPROPERTY = 433;
	int CLASSPROPERTYLIST = 434;
	int CLASSMETHODNORMAL = 435;
	int CLASSMETHODPROTO = 436;
	int CLASSMETHODQUALIFIERLIST = 437;
	int CLASSEXTENDS = 438;
	int CLASSEXTENDSARGS = 439;
	int INTERFACEDECLARATION = 440;
	int INTERFACEDECLARATIONNORMAL = 441;
	int INTERFACEDECLARATIONEXTERNAL = 442;
	int INTERFACEDECLARATIONNOANSIHEADER = 443;
	int INTERFACEHEADERDOTSTAR = 444;
	int INTERFACEHEADERNORMAL = 445;
	int INTERFACEBODY = 446;
	int PROGRAMDECLARATION = 447;
	int PROGRAMDECLARATIONNORMAL = 448;
	int PROGRAMDECLARATIONEXTERNAL = 449;
	int PROGRAMDECLARATIONNOANSIHEADER = 450;
	int PROGRAMBODY = 451;
	int PACKAGEDECLARATION = 452;
	int PACKAGEBODY = 453;
	int MODPORTDECLARATION = 454;
	int MODPORTITEM = 455;
	int MODPORTDECLARATIONITEM = 456;
	int MODPORTDECLARATIONPREFIX = 457;
	int MODPORTHIER = 458;
	int MODPORTDOT = 459;
	int TIMEUNITSDECLARATIONQ = 460;
	int SUBROUTINECALLSTATEMENT = 461;
	int LOOPSTATEMENT = 462;
	int CAST = 463;
	int VARIABLEDIMENSION = 464;
	int DPIIMPORTEXPORT = 465;
	int FUNCTIONPROTOTYPE = 466;
	int TASKPROTOTYPE = 467;
	int FUNCTIONHEADDECLARATIONRETURN = 468;
	int FUNCTIONHEADDECLARATIONNORETURN = 469;
	int COVERGROUPDECLARATION = 470;
	int PROPERTYDECLARATION = 471;
	int VIRTUALQ = 472;
	int SEQUENCEDECLARATION = 473;
	int CLOCKINGDECLARATION = 474;
	int CONCURRENTASSERTIONITEM = 475;
	int ASSERTPROPERTYSTATEMENT = 476;
	int ASSUMEPROPERTYSTATEMENT = 477;
	int COVERPROPERTYSTATEMENT = 478;
	int EXPECTPROPERTYSTATEMENT = 479;
	int STATEMENTNAMEPREFIX = 480;
	int FUNCTIONBODYDECLARATIONLIST = 481;
	int TASKBODYDECLARATIONLIST = 482;
	int CONSTRAINTDECLARATION = 483;
	int CONSTRAINTSET = 484;
	int CONSTRAINTEXPRESSIONTRIGGER = 485;
	int CONSTRAINTEXPRESSIONIF = 486;
	int CONSTRAINTEXPRESSIONFOR = 487;
	int STATICQ = 488;
	int ANSIPORTHEADERQ = 489;
	int ANSIPORTDECLARATION = 490;
	int ANSIPORTDECLARATIONNORMAL = 491;
	int ANSIPORTDECLARATIONDOT = 492;
	int ANSIPORTHEADERNORMAL = 493;
	int ANSIPORTHEADERINTERFACE = 494;
	int PORTREFERENCE = 495;
	int PORTDIRECTIONQ = 496;
	int ASSOCIATIVEDIMENSIONQ = 497;
	int CLASSARRAYNEW = 498;
	int CLASSNEW = 499;
	int DYNAMICARRAYNEW = 500;
	int PACKAGEIMPORTDECLARATION = 501;
	int PACKAGEIMPORTITEM = 502;
	int STATEMENT = 503;
	int LITERAL_extern = 504;
	int LITERAL_interface = 505;
	int LITERAL_program = 506;
	int LITERAL_bind = 507;
	int LITERAL_cover = 508;
	int LITERAL_assume = 509;
	int LITERAL_assert = 510;
	int LITERAL_property = 511;
	int LITERAL_clocking = 512;
	int LITERAL_forkjoin = 513;
	int LITERAL_input = 514;
	int LITERAL_inout = 515;
	int LITERAL_output = 516;
	int LITERAL_ref = 517;
	int LITERAL_const = 518;
	int LITERAL_parameter = 519;
	int LITERAL_localparam = 520;
	int LITERAL_type = 521;
	int LITERAL_typedef = 522;
	int LITERAL_logic = 523;
	int LITERAL_time = 524;
	int LITERAL_shortreal = 525;
	int LITERAL_realtime = 526;
	int LITERAL_class = 527;
	int LITERAL_virtual = 528;
	int LITERAL_shortint = 529;
	int LITERAL_longint = 530;
	int LITERAL_reg = 531;
	int LITERAL_wire = 532;
	int LITERAL_genvar = 533;
	int LITERAL_struct = 534;
	int LITERAL_union = 535;
	int LITERAL_void = 536;
	int LITERAL_enum = 537;
	int LITERAL_signed = 538;
	int LITERAL_unsigned = 539;
	int LITERAL_packed = 540;
	int LITERAL_tagged = 541;
	int LITERAL_import = 542;
	int LITERAL_static = 543;
	int INDENTIFIER = 544;
	// "++" = 545
	// "--" = 546
	int LITERAL_package = 547;
	int LITERAL_endpackage = 548;
	int LITERAL_endprogram = 549;
	int TBD = 550;
	int LITERAL_export = 551;
	int LITERAL_context = 552;
	int LITERAL_pure = 553;
	int LITERAL_endinterface = 554;
	int LITERAL_modport = 555;
	int LITERAL_extends = 556;
	int LITERAL_endclass = 557;
	int LITERAL_constraint = 558;
	int LITERAL_new = 559;
	int LITERAL_protected = 560;
	int LITERAL_local = 561;
	int LITERAL_rand = 562;
	int LITERAL_randc = 563;
	// "asdlfja;slkdfja;lskjdf;alskjdf" = 564
	int LITERAL_timeunit = 565;
	int LITERAL_timeprecision = 566;
	int PLUSEQ = 567;
	int MINUSEQ = 568;
	int MULTEQ = 569;
	int DIVEQ = 570;
	int MODEQ = 571;
	int ANDEQ = 572;
	int OREQ = 573;
	int XOREQ = 574;
	int LELEEQ = 575;
	int GRGREQ = 576;
	int LELELEEQ = 577;
	int GEGEGEEQ = 578;
	int LITERAL_alias = 579;
	int LITERAL_expect = 580;
	int LITERAL_endproperty = 581;
	int POINTDASH = 582;
	int POINTEQ = 583;
	int LITERAL_dist = 584;
	int LITERAL_sequence = 585;
	int LITERAL_endsequence = 586;
	int LITERAL_throughout = 587;
	int LITERAL_first_match = 588;
	int RPAEN = 589;
	int LITERAL_intersect = 590;
	int LITERAL_within = 591;
	int POUNDPOUND = 592;
	int LITERAL_solve = 593;
	int LITERAL_before = 594;
	int LITERAL_foreach = 595;
	int COLONEQUALS = 596;
	int COLONDIV = 597;
	int LITERAL_endclocking = 598;
	int LITERAL_randsequence = 599;
	int LITERAL_covergroup = 600;
	int LITERAL_endgroup = 601;
	int LITERAL_do = 602;
	int LITERAL_randcase = 603;
	int TRIGGER2 = 604;
	int LITERAL_return = 605;
	int LITERAL_break = 606;
	int LITERAL_continue = 607;
	int LITERAL_join_any = 608;
	int LITERAL_join_none = 609;
	int LITERAL_randomize = 610;
	int LITERAL_with = 611;
	int LITERAL_null = 612;
	int EQQUEQ = 613;
	int NOTQUEQ = 614;
	int LITERAL_inside = 615;
	int LITERAL_super = 616;
	int LITERAL_string = 617;
	int CRAP = 618;
}
