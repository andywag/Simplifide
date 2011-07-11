###############################################################################################
# © Copyright 2010, Xilinx, Inc. All rights reserved.
# This file contains confidential and proprietary information of Xilinx, Inc. and is
# protected under U.S. and international copyright and other intellectual property laws.
###############################################################################################
#
# Disclaimer:
#		This disclaimer is not a license and does not grant any rights to the materials
#		distributed herewith. Except as otherwise provided in a valid license issued to you
#		by Xilinx, and to the maximum extent permitted by applicable law: (1) THESE MATERIALS
#		ARE MADE AVAILABLE "AS IS" AND WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL
#		WARRANTIES AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING BUT NOT LIMITED
#		TO WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR
#		PURPOSE; and (2) Xilinx shall not be liable (whether in contract or tort, including
#		negligence, or under any other theory of liability) for any loss or damage of any
#		kind or nature related to, arising under or in connection with these materials,
#		including for any direct, or any indirect, special, incidental, or consequential
#		loss or damage (including loss of data, profits, goodwill, or any type of loss or
#		damage suffered as a result of any action brought by a third party) even if such
#		damage or loss was reasonably foreseeable or Xilinx had been advised of the
#		possibility of the same.
#
# CRITICAL APPLICATIONS
#		Xilinx products are not designed or intended to be fail-safe, or for use in any
#		application requiring fail-safe performance, such as life-support or safety devices
#		or systems, Class III medical devices, nuclear facilities, applications related to
#		the deployment of airbags, or any other applications that could lead to death,
#		personal injury, or severe property or environmental damage (individually and
#		collectively, "Critical Applications"). Customer assumes the sole risk and
#		liability of any use of Xilinx products in Critical Applications, subject only to
#		applicable laws and regulations governing limitations on product liability.
#
# THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS PART OF THIS FILE AT ALL TIMES. 
#
#		Contact:    e-mail  hotline@xilinx.com        phone   + 1 800 255 7778
#   ____  ____
#  /   /\/   /
# /___/  \  /   Vendor: Xilinx
# \   \   \/    Version: 
#  \   \        Filename: DeSerial_FuncComp.do
#  /   /        Date Last Modified: 15 Feb 2010
# /___/   /\    Date Created:  15 Feb 2010 
# \   \  /  \
#  \___\/\___\
# 
# Device:		Virtex-6
# Author:		MD
# Entity Name: 	DeSerial_FuncComp
# Purpose: 		Functional test of the deserializer made from Block-RAM
# Tools:		Modelsim SE6.6
# Limitations: none
#
# Revision History:
#    Rev. 
#
###############################################################################################
#
cd E:/Projects/BramTricks/Virtex_6/Projects/BramDeSerializer/Simulation
#
if {![file exists work]} {
    vlib work
}
#
# The files that need to be compiled by Modelsim.
# Place the files in the correct order.
#
Vlib FastCounter
vcom -novopt -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntTwoBit.vhd
vcom -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntThreeBit.vhd
vcom -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntFourBit.vhd
vcom -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntFiveBit.vhd
vcom -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntSixBit.vhd
vcom -reportprogress 300 -work FastCounter ../../../Libraries/FastCounter/Vhdl/CntTenBit.vhd
Vlib Control
vcom -reportprogress 300 -work Control ../Libraries/Control/Vhdl/Control.vhd
--
vcom -novopt -reportprogress 300 -work work ../Vhdl/DeSerializer.vhd
vcom -novopt -reportprogress 300 -work work ../Vhdl/DeSerializer_Tester.vhd
vcom -novopt -reportprogress 300 -work work ../Vhdl/DeSerializer_Testbench.vhd

# Functional Simulate
vsim -t ps -novopt work.deserializer_testbench(deserializer_testbench_struct)
#
# This calls the waveform script.
do ../SimScripts/DeSerializer_FuncWave.do
#
# Run 

# The End.