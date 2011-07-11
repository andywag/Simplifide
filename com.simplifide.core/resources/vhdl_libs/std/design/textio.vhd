package TEXTIO is

-- Type definitions for text I/O:

type LINE is access STRING;  -- A LINE is a pointer to a STRING value

type TEXT is file of STRING; -- A file of variable-length ASCII records.

type SIDE is (RIGHT, LEFT);   -- For justifying output data within fields.

subtype WIDTH is NATURAL;     -- For specifying widths of  output fields.

-- Standard text files:

file INPUT:   TEXT  open READ_MODE is "STD_INPUT";

file OUTPUT:  TEXT  open WRITE_MODE is "STD_OUTPUT";

-- Input routines for standard types:

procedure READLINE (file F: TEXT; L: out LINE);

procedure READ (L: inout LINE;        VALUE: out BIT; GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out BIT);

procedure READ (L: inout LINE;        VALUE: out BIT_VECTOR;
              GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out BIT_VECTOR);


procedure READ (L: inout LINE;        VALUE: out BOOLEAN;
      GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out BOOLEAN);

procedure READ (L: inout LINE;        VALUE: out CHARACTER;
      GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out CHARACTER);

procedure READ (L: inout LINE;        VALUE: out INTEGER;
      GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out INTEGER);

procedure READ (L: inout LINE;        VALUE: out REAL;
     GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out REAL);

procedure READ (L: inout LINE;        VALUE: out STRING;
      GOOD: out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out STRING);

procedure READ (L: inout LINE;        VALUE: out TIME; GOOD:
              out BOOLEAN);
procedure READ (L: inout LINE;        VALUE: out TIME);

-- Output routines for standard types:
       
procedure WRITELINE (file F:TEXT; L:inout  LINE);
       
procedure WRITE (L: inout LINE;       VALUE: in BIT;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);
       
procedure WRITE (L: inout LINE;       VALUE: in BIT_VECTOR;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);
       
procedure WRITE (L: inout LINE;       VALUE: in BOOLEAN;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);
       
procedure WRITE (L: inout LINE;       VALUE: in CHARACTER;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);
       
procedure WRITE (L: inout LINE;       VALUE: in INTEGER;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

procedure WRITE (L: inout LINE;       VALUE: in REAL;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0;
              DIGITS: in NATURAL:= 0);

procedure WRITE (L: inout LINE;       VALUE: in STRING;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

procedure WRITE (L: inout LINE;       VALUE: in TIME;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0; UNIT:in TIME:= ns);

attribute foreign of TEXTIO: package is "NO C code generation";

-- File position  Predicates predicate 

--    function ENDFILE (file F: TEXT) return  BOOLEAN;
attribute foreign of readline:procedure is "std_textio_readline";
attribute foreign of read[LINE,BIT,BOOLEAN]  :procedure is "std_textio_read1";
attribute foreign of read[LINE,BIT]:procedure is "std_textio_read2";
attribute foreign of read[LINE,BIT_VECTOR, BOOLEAN]:procedure is "std_textio_read3";
attribute foreign of read[LINE,BIT_VECTOR]:procedure is "std_textio_read4";
attribute foreign of read[LINE,BOOLEAN, BOOLEAN]:procedure is "std_textio_read5";
attribute foreign of read[LINE,BOOLEAN]:procedure is "std_textio_read6";
attribute foreign of read[LINE,CHARACTER, BOOLEAN]:procedure is "std_textio_read7";
attribute foreign of read[LINE,CHARACTER]:procedure is "std_textio_read8";
attribute foreign of read[LINE,INTEGER, BOOLEAN]:procedure is "std_textio_read9";
attribute foreign of read[LINE,INTEGER]:procedure is "std_textio_read10";
attribute foreign of read[LINE,REAL, BOOLEAN]:procedure is "std_textio_read11";
attribute foreign of read[LINE,REAL]:procedure is "std_textio_read12";
attribute foreign of read[LINE,STRING, BOOLEAN]:procedure is "std_textio_read13";
attribute foreign of read[LINE,STRING]:procedure is "std_textio_read14";
attribute foreign of read[LINE,TIME, BOOLEAN]:procedure is "std_textio_read15";
attribute foreign of read[LINE,TIME]:procedure is "std_textio_read16";

attribute foreign of writeline:procedure is "std_textio_writeline";
attribute foreign of write[LINE, BIT, SIDE, WIDTH]:procedure is "std_textio_write1";
attribute foreign of write[LINE, BIT_VECTOR, SIDE, WIDTH]:procedure is "std_textio_write2";
attribute foreign of write[LINE, BOOLEAN, SIDE, WIDTH]:procedure is "std_textio_write3";
attribute foreign of write[LINE, CHARACTER, SIDE, WIDTH]:procedure is "std_textio_write4";
attribute foreign of write[LINE, INTEGER, SIDE, WIDTH]:procedure is "std_textio_write5";
attribute foreign of write[LINE, REAL, SIDE, WIDTH, NATURAL]:procedure is "std_textio_write6";
attribute foreign of write[LINE, STRING, SIDE, WIDTH]:procedure is "std_textio_write7";
attribute foreign of write[LINE, TIME, SIDE, WIDTH, TIME]:procedure is "std_textio_write8";


end TEXTIO;

package body textio is


  procedure readline(file F: TEXT; L: out LINE) is
  begin
  end readline;
  
  procedure read(L:inout LINE; VALUE:out BIT; GOOD: out BOOLEAN) is
  begin end;
  
  procedure read(L:inout LINE; VALUE:out BIT) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out BIT_VECTOR;
              GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out BIT_VECTOR) is
  begin end;


  procedure READ (L: inout LINE;        VALUE: out BOOLEAN;
      GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out BOOLEAN) is
  begin end;

  procedure READ (L: inout LINE;        VALUE: out CHARACTER;
      GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out CHARACTER) is
  begin end;

  procedure READ (L: inout LINE;        VALUE: out INTEGER;
      GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out INTEGER) is
  begin end;

  procedure READ (L: inout LINE;        VALUE: out REAL;
     GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out REAL) is
  begin end;

  procedure READ (L: inout LINE;        VALUE: out STRING;
      GOOD: out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out STRING) is
  begin end;

  procedure READ (L: inout LINE;        VALUE: out TIME; GOOD:
              out BOOLEAN) is
  begin end;
  
  procedure READ (L: inout LINE;        VALUE: out TIME) is
  begin end;
  
  procedure WRITELINE (file F:TEXT; L:inout  LINE) is
  begin end;  
       
  procedure WRITE (L: inout LINE;       VALUE: in BIT;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;
  
  procedure WRITE (L: inout LINE;       VALUE: in BIT_VECTOR;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;
       
  procedure WRITE (L: inout LINE;       VALUE: in BOOLEAN;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;
       
  procedure WRITE (L: inout LINE;       VALUE: in CHARACTER;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;
       
  procedure WRITE (L: inout LINE;       VALUE: in INTEGER;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;

  procedure WRITE (L: inout LINE;       VALUE: in REAL;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0;
              DIGITS: in NATURAL:= 0) is
  begin end;
  procedure WRITE (L: inout LINE;       VALUE: in STRING;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0) is
  begin end;

  procedure WRITE (L: inout LINE;       VALUE: in TIME;
      JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0; UNIT:
              in TIME:= ns) is
  begin end;


end textio;
