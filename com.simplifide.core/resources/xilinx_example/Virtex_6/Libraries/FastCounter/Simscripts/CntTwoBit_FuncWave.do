onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Literal /cnttwobit_testbench/uut/c_cascdpos
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /cnttwobit_testbench/uut/cntclk
add wave -noupdate -format Logic /cnttwobit_testbench/uut/cntrst
add wave -noupdate -format Logic /cnttwobit_testbench/uut/cntena
add wave -noupdate -format Literal /cnttwobit_testbench/uut/cntcmbin
add wave -noupdate -format Literal /cnttwobit_testbench/uut/cntcmbtcin
add wave -noupdate -format Literal /cnttwobit_testbench/uut/cntout
add wave -noupdate -format Logic /cnttwobit_testbench/uut/cntcmbtc
add wave -noupdate -format Logic /cnttwobit_testbench/uut/cnttc
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {9072245 ps} 0} {{Cursor 2} {979082000 ps} 0}
configure wave -namecolwidth 241
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
WaveRestoreZoom {0 ps} {1575 ns}
