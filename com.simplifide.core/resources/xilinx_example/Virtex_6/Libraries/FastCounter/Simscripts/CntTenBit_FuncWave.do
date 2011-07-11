onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -divider {10-bit Counter}
add wave -noupdate -format Logic /cnttenbit_testbench/uut/cntclk
add wave -noupdate -format Logic /cnttenbit_testbench/uut/cntrst
add wave -noupdate -format Logic /cnttenbit_testbench/uut/cntena
add wave -noupdate -format Literal /cnttenbit_testbench/uut/cntout
add wave -noupdate -format Logic /cnttenbit_testbench/uut/cnttc
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {3129500 ps} 0}
configure wave -namecolwidth 379
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
WaveRestoreZoom {1043558 ps} {3182209 ps}
