onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -divider {DeSerializer ports}
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserenaa
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserdatina
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/deserpatterna
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/deserflags
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclkb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserenab
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserrstb
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/deserdatoutb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserwordcnttc
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserstopped
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserpttrncnttc
add wave -noupdate -divider BlockRAM
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intdataouta
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intdataina
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intaddra
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intenaporta
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/deserdatoutb
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intaddrb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intenaportb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserrstb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclkb
add wave -noupdate -divider CntFivBit
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcntrstorresync
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcntena
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcnttc
add wave -noupdate -format Literal /deserializer_testbench/uut/deserializer_i_cntfivbit/cntout
add wave -noupdate -divider CntTenBit_PortA
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcntenaa
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcnttca
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intaddra
add wave -noupdate -divider CntTenBit_PortB
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclkb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserrstb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcnttcb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcntenab
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intaddrb
add wave -noupdate -divider {Comparator PortA}
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intequa
add wave -noupdate -divider Flags
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intflags
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/deserflags
add wave -noupdate -divider Control
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclkb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserenaa
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserenab
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcnttc
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intequa
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcnttca
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcnttcb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcntena
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intbitcntrst
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intenaporta
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcntenaa
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intenaportb
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intaddrcntenab
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intwordcntena
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intpttrncntena
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserstopped
add wave -noupdate -divider WordCnt
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intwordcntena
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intwordcnttc
add wave -noupdate -divider PttrnCnt
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserclka
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/deserresync
add wave -noupdate -format Logic -radix hexadecimal /deserializer_testbench/uut/intpttrncntena
add wave -noupdate -format Literal -radix hexadecimal /deserializer_testbench/uut/intpttrncnttc
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {67841 ps} 0}
configure wave -namecolwidth 296
configure wave -valuecolwidth 66
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
WaveRestoreZoom {0 ps} {1050 ns}
