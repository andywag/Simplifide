onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Literal /cntfourbit_testbench/uut/c_cascdpos
add wave -noupdate -divider <NULL>
add wave -noupdate -format Logic /cntfourbit_testbench/uut/cntclk
add wave -noupdate -format Logic /cntfourbit_testbench/uut/cntrst
add wave -noupdate -format Logic /cntfourbit_testbench/uut/cntena
add wave -noupdate -format Literal /cntfourbit_testbench/uut/cntcmbin
add wave -noupdate -format Literal /cntfourbit_testbench/uut/cntcmbtcin
add wave -noupdate -format Literal /cntfourbit_testbench/uut/cntout
add wave -noupdate -format Logic /cntfourbit_testbench/uut/cntcmbtc
add wave -noupdate -format Logic /cntfourbit_testbench/uut/cnttc
TreeUpdate [SetDefaultTree]
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
WaveRestoreZoom {543996 ps} {1550316 ps}
