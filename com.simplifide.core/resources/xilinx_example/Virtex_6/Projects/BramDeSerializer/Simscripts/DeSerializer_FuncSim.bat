REM
REM  ===============================================================
REM  == Be sure to have run the "...._FuncFuse.bat" file prior to ==
REM  == executing this batch file.                                ==
REM  ===============================================================
REM
cd ..\Simulation
DeSerializer_FuncIsim.exe -gui -view ../Simscripts/DeSerializer_FuncWave.wcfg -tclbatch ../Simscripts/SimRun.tcl
REM
REM  ===============================================================
REm  ==                       DONE                                == 
REM  ===============================================================
cd ../Simscripts