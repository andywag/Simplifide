REM
REM  ===================================================================
REM  == Run this batch file in a command window (DOS box) !!!         ==
REM  ==  To start a command window do:                                ==
REM  ==  START --> Run --> type in the box [command]                  ==
REM  ==  Type in the opened command window:                           ==
REM  ==      <drive:> to swap to the drive where the project resides  ==
REM  ==      cd The\path\to\the\"Simscripts"\directory                ==
REM  ==      Type the name of this .bat file and hit enter.           ==
REM  ===================================================================
REM
cd E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Simulation
fuse work.deserializer_testbench -prj ..\Simscripts\DeSerializer_FuncComp.prj -o DeSerializer_FuncIsim.exe
cd ..\Simscripts
REM
REM  ===================================================================
REM  == Execute now the  "...._FuncSim.bat" file.                     ==
REM  ===================================================================
REM