onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format logic -radix hexadecimal /cntfourbit_carry_testbench/uut/cntclk
add wave -noupdate -format logic -radix hexadecimal /cntfourbit_carry_testbench/uut/cntrst
add wave -noupdate -format logic -radix hexadecimal /cntfourbit_carry_testbench/uut/cntena
add wave -noupdate -format logic -radix hexadecimal /cntfourbit_carry_testbench/uut/cnttcena
add wave -noupdate -divider {counter internal logic}
add wave -noupdate -format literal -radix hexadecimal /cntfourbit_carry_testbench/uut/intcnto6
add wave -noupdate -format literal -radix hexadecimal /cntfourbit_carry_testbench/uut/intcnto5
add wave -noupdate -divider {counter internal carry as term _count}
add wave -noupdate -format literal -radix hexadecimal /cntfourbit_carry_testbench/uut/intcntcarout
add wave -noupdate -divider {counter internal ff.q outputs}
add wave -noupdate -format literal -radix hexadecimal /cntfourbit_carry_testbench/uut/intcnt
add wave -noupdate -divider outputs
add wave -noupdate -format literal -radix hexadecimal /cntfourbit_carry_testbench/uut/cntout
add wave -noupdate -format logic -radix hexadecimal /cntfourbit_carry_testbench/uut/cnttc
treeupdate [setdefaulttree]
WaveRestoreCursors {{Cursor 1} {0 ps} 0}
configure wave -namecolwidth 245
configure wave -valuecolwidth 40
configure wave -justifyvalue left
configure wave -signalnamewidth 0
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
WaveRestoreZoom {1207069 ps} {1264641 ps}
