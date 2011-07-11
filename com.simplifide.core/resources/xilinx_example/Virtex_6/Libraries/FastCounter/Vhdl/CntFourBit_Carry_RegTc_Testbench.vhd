---------------------------------------------------------------------------------------------
-- © Copyright 2009, Xilinx, Inc. All rights reserved.
-- This file contains confidential and proprietary information of Xilinx, Inc. and is
-- protected under U.S. and international copyright and other intellectual property laws.
---------------------------------------------------------------------------------------------
--
-- Disclaimer:
--		This disclaimer is not a license and does not grant any rights to the materials
--		distributed herewith. Except as otherwise provided in a valid license issued to you
--		by Xilinx, and to the maximum extent permitted by applicable law: (1) THESE MATERIALS
--		ARE MADE AVAILABLE "AS IS" AND WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL
--		WARRANTIES AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING BUT NOT LIMITED
--		TO WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR
--		PURPOSE; and (2) Xilinx shall not be liable (whether in contract or tort, including
--		negligence, or under any other theory of liability) for any loss or damage of any
--		kind or nature related to, arising under or in connection with these materials,
--		including for any direct, or any indirect, special, incidental, or consequential
--		loss or damage (including loss of data, profits, goodwill, or any type of loss or
--		damage suffered as a result of any action brought by a third party) even if such
--		damage or loss was reasonably foreseeable or Xilinx had been advised of the
--		possibility of the same.
--
-- CRITICAL APPLICATIONS
--		Xilinx products are not designed or intended to be fail-safe, or for use in any
--		application requiring fail-safe performance, such as life-support or safety devices
--		or systems, Class III medical devices, nuclear facilities, applications related to
--		the deployment of airbags, or any other applications that could lead to death,
--		personal injury, or severe property or environmental damage (individually and
--		collectively, "Critical Applications"). Customer assumes the sole risk and
--		liability of any use of Xilinx products in Critical Applications, subject only to
--		applicable laws and regulations governing limitations on product liability.
--
-- THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS PART OF THIS FILE AT ALL TIMES. 
--
--		Contact:    e-mail  hotline@xilinx.com        phone   + 1 800 255 7778
--   ____  ____
--  /   /\/   /
-- /___/  \  /   Vendor: Xilinx
-- \   \   \/    Version: 
--  \   \        Filename:              CntFourBit_Carry_RegTc_Testbench.vhd
--  /   /        Date Last Modified:    Friday, November 27, 2009
-- /___/   /\    Date Created:  		Tuesday, April 21, 2009 
-- \   \  /  \
--  \___\/\___\
-- 
-- Device:		Virtex-6
-- Entity Name: CntFourBit_Carry_RegTc_Testbench
-- Purpose: 	Test Bench for design.
-- Tools:
-- Limitations: TESTBENCH
--                       DON'T USE THIS FILE FOR COMPILATION NOR INTEGRATION.
--
-- Revision History:
--    Rev. 
--
---------------------------------------------------------------------------------------------
--
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_UNSIGNED.all;
use STD.textio.all;
library UNISIM;
use UNISIM.vcomponents.all;
--
entity CntFourBit_Carry_RegTc_Testbench is
     -- generic / port / local declatations
end entity CntFourBit_Carry_RegTc_Testbench;
--
architecture CntFourBit_Carry_RegTc_Testbench_struct of CntFourBit_Carry_RegTc_Testbench is
---------------------------------------------------------------------------------------------
-- Component Instantiation
---------------------------------------------------------------------------------------------
component CntFourBit_Carry_RegTc
	port (
		CntClk	    : in std_logic;
		CntRst	    : in std_logic;
		CntEna	    : in std_logic;
		CntTcEna	: in std_logic;
        CntTcInit   : in std_logic;
		CntTc	 	: out std_logic_vector(3 downto 0);
		CntOut	    : out std_logic_vector(3 downto 0)
	);
end component;

component CntFourBit_Carry_RegTc_Tester
	port (
		CntClk	    : out std_logic;
		CntRst	    : out std_logic;
		CntEna	    : out std_logic;
		CntTcEna	: out std_logic;
        CntTcInit   : out std_logic;
		CntTc	 	: in std_logic_vector(3 downto 0);
		CntOut	    : in std_logic_vector(3 downto 0)
	);
end component;
---------------------------------------------------------------------------------------------
-- Signal Declarations
---------------------------------------------------------------------------------------------
signal Sim_CntClk	    : std_logic;
signal Sim_CntRst	    : std_logic;
signal Sim_CntEna	    : std_logic;
signal Sim_CntTcEna	    : std_logic;
signal Sim_CntTcInit	: std_logic;
signal Sim_CntTc	    : std_logic_vector(3 downto 0);
signal Sim_CntOut	    : std_logic_vector(3 downto 0);
---------------------------------------------------------------------------------------------
--
begin
Uut : CntFourBit_Carry_RegTc
	port map (
		CntClk    => Sim_CntClk,
		CntRst    => Sim_CntRst,
		CntEna    => Sim_CntEna,
		CntTcEna  => Sim_CntTcEna,
        CntTcInit => Sim_CntTcInit,
		CntTc     => Sim_CntTc,
		CntOut    => Sim_CntOut
	);

Dtu : CntFourBit_Carry_RegTc_Tester
	port map (
		CntClk    => Sim_CntClk,
		CntRst    => Sim_CntRst,
		CntEna    => Sim_CntEna,
		CntTcEna  => Sim_CntTcEna,
        CntTcInit => Sim_CntTcInit,
		CntTc     => Sim_CntTc,
		CntOut    => Sim_CntOut
	);
--
-------------------------------------------------------------------------------------------
--
end architecture CntFourBit_Carry_RegTc_Testbench_struct; 