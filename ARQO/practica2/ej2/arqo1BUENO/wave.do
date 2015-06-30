onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate /procesador_tb/UUT/auxPc
add wave -noupdate -radix binary /procesador_tb/UUT/miUC/OPCode
add wave -noupdate /procesador_tb/UUT/auxEq
add wave -noupdate /procesador_tb/UUT/auxJump
add wave -noupdate /procesador_tb/UUT/JumpAddr
add wave -noupdate /procesador_tb/UUT/auxPC4
add wave -noupdate /procesador_tb/UUT/BeqAddr2
add wave -noupdate /procesador_tb/UUT/bubble
add wave -noupdate /procesador_tb/UUT/auxA3
add wave -noupdate /procesador_tb/UUT/muxRegDest_ID_EX
add wave -noupdate /procesador_tb/UUT/muxRegDest_EX_MEM
add wave -noupdate /procesador_tb/UUT/muxRegDest_MEM_WB
add wave -noupdate /procesador_tb/UUT/instruccion_ID
add wave -noupdate /procesador_tb/UUT/RegWrite_EX_MEM
add wave -noupdate /procesador_tb/UUT/RegWrite_ID_EX
add wave -noupdate -divider registros
add wave -noupdate /procesador_tb/UUT/miregs/Clk
add wave -noupdate /procesador_tb/UUT/miregs/Reset
add wave -noupdate /procesador_tb/UUT/miregs/A1
add wave -noupdate /procesador_tb/UUT/miregs/Rd1
add wave -noupdate /procesador_tb/UUT/miregs/A2
add wave -noupdate /procesador_tb/UUT/miregs/Rd2
add wave -noupdate /procesador_tb/UUT/miregs/A3
add wave -noupdate /procesador_tb/UUT/miregs/Wd3
add wave -noupdate /procesador_tb/UUT/miregs/We3
add wave -noupdate -childformat {{/procesador_tb/UUT/miregs/regs(0) -radix decimal} {/procesador_tb/UUT/miregs/regs(1) -radix decimal} {/procesador_tb/UUT/miregs/regs(2) -radix decimal} {/procesador_tb/UUT/miregs/regs(3) -radix decimal} {/procesador_tb/UUT/miregs/regs(4) -radix decimal} {/procesador_tb/UUT/miregs/regs(5) -radix decimal} {/procesador_tb/UUT/miregs/regs(6) -radix decimal} {/procesador_tb/UUT/miregs/regs(7) -radix decimal} {/procesador_tb/UUT/miregs/regs(8) -radix decimal} {/procesador_tb/UUT/miregs/regs(9) -radix decimal} {/procesador_tb/UUT/miregs/regs(10) -radix decimal} {/procesador_tb/UUT/miregs/regs(11) -radix decimal} {/procesador_tb/UUT/miregs/regs(12) -radix decimal} {/procesador_tb/UUT/miregs/regs(13) -radix decimal} {/procesador_tb/UUT/miregs/regs(14) -radix decimal} {/procesador_tb/UUT/miregs/regs(15) -radix decimal} {/procesador_tb/UUT/miregs/regs(16) -radix decimal} {/procesador_tb/UUT/miregs/regs(17) -radix decimal} {/procesador_tb/UUT/miregs/regs(18) -radix decimal} {/procesador_tb/UUT/miregs/regs(19) -radix decimal} {/procesador_tb/UUT/miregs/regs(20) -radix decimal} {/procesador_tb/UUT/miregs/regs(21) -radix decimal} {/procesador_tb/UUT/miregs/regs(22) -radix decimal} {/procesador_tb/UUT/miregs/regs(23) -radix decimal} {/procesador_tb/UUT/miregs/regs(24) -radix decimal} {/procesador_tb/UUT/miregs/regs(25) -radix decimal} {/procesador_tb/UUT/miregs/regs(26) -radix decimal} {/procesador_tb/UUT/miregs/regs(27) -radix decimal} {/procesador_tb/UUT/miregs/regs(28) -radix decimal} {/procesador_tb/UUT/miregs/regs(29) -radix decimal} {/procesador_tb/UUT/miregs/regs(30) -radix decimal} {/procesador_tb/UUT/miregs/regs(31) -radix decimal}} -expand -subitemconfig {/procesador_tb/UUT/miregs/regs(0) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(1) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(2) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(3) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(4) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(5) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(6) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(7) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(8) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(9) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(10) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(11) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(12) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(13) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(14) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(15) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(16) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(17) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(18) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(19) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(20) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(21) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(22) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(23) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(24) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(25) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(26) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(27) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(28) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(29) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(30) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(31) {-height 15 -radix decimal}} /procesador_tb/UUT/miregs/regs
add wave -noupdate -divider alu
add wave -noupdate /procesador_tb/UUT/miALU/Op1
add wave -noupdate /procesador_tb/UUT/miALU/Op2
add wave -noupdate /procesador_tb/UUT/miALU/ALUControl
add wave -noupdate /procesador_tb/UUT/miALU/Res
add wave -noupdate /procesador_tb/UUT/miALU/Z
add wave -noupdate /procesador_tb/UUT/miALU/aux
add wave -noupdate /procesador_tb/UUT/miALU/resta
add wave -noupdate /procesador_tb/UUT/miALU/Resaux
add wave -noupdate -divider Control
add wave -noupdate /procesador_tb/UUT/miUC/Funct
add wave -noupdate /procesador_tb/UUT/miUC/MemToReg
add wave -noupdate /procesador_tb/UUT/miUC/MemWrite
add wave -noupdate /procesador_tb/UUT/miUC/Branch
add wave -noupdate /procesador_tb/UUT/miUC/ALUControlUC
add wave -noupdate /procesador_tb/UUT/miUC/ALUSrc
add wave -noupdate /procesador_tb/UUT/miUC/RegDest
add wave -noupdate /procesador_tb/UUT/miUC/RegWrite
add wave -noupdate /procesador_tb/UUT/miUC/Jump
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {157873 ps} 0}
quietly wave cursor active 1
configure wave -namecolwidth 255
configure wave -valuecolwidth 100
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
configure wave -timelineunits ns
update
WaveRestoreZoom {0 ps} {1090192 ps}
