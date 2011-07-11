onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cntclk
add wave -noupdate -format Logic -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cntrst
add wave -noupdate -format Logic -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cntena
add wave -noupdate -format Logic -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cnttcena
add wave -noupdate -format Logic -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cnttcinit
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cnttc
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/cntout
add wave -noupdate -divider Internals
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/intcnto6
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/intcnto5
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/intcntcarout
add wave -noupdate -format Literal -radix hexadecimal /CntFourBit_Carry_RegTc_testbench/uut/intcnt
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {0 ps} 0}
configure wave -namecolwidth 120
configure wave -valuecolwidth 40
configure wave -justifyvalue left
configure wave -signalnamewidth 1
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ps
update
WaveRestoreZoom {974084 ps} {999764 ps}
