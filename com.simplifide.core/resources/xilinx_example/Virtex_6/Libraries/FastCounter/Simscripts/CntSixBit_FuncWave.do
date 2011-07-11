onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic /cntsixbit_testbench/uut/cntclk
add wave -noupdate -format Logic /cntsixbit_testbench/uut/cntrst
add wave -noupdate -format Logic /cntsixbit_testbench/uut/cntena
add wave -noupdate -format Literal /cntsixbit_testbench/uut/cntout
add wave -noupdate -format Logic /cntsixbit_testbench/uut/cntcmbtc
add wave -noupdate -format Logic /cntsixbit_testbench/uut/cnttc
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {1123605 ps} 0}
configure wave -namecolwidth 224
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
WaveRestoreZoom {1117931 ps} {1248453 ps}
