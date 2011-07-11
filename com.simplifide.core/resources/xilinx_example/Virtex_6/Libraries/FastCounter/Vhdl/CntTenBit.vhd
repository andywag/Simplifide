------------------------------7 dwonto0---------------------------------------------------------------
-- © Copyright 2010, Xilinx, Inc. All rights reserved.
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
-- /___/  \  / 			Vendor:              Xilinx Inc.
-- \   \   \/ 			Version:              
--  \   \        		Filename:            CntTenBit.vhd
--  /   /        		Date Created:        28 April, 2010
-- /___/   /\    		Date Last Modified:  28 April, 2010  
-- \   \  /  \          
--  \___\/\___\
-- 
-- Device:          Virtex-6
-- Author:          MD
-- Entity Name:     CntTenBit
-- Purpose:         Ten bit counter made from three smaller counters.
--					get optimal speed. LSB = 2-bit, Mid = 4-bit, and MSB = 4-bit.
-- Tools:           ISE_11.5
-- Limitations:     none
--
-- Revision History:
--	Rev. 
--
------------------------------------------------------------------------------
-- Naming Conventions:
--   active low signals:                    "*_n"
--   clock signals:                         "clk", "clk_div#", "clk_#x"
--   reset signals:                         "rst", "rst_n"
--   generics:                              "C_*"
--   user defined types:                    "*_TYPE"
--   state machine next state:              "*_ns"
--   state machine current state:           "*_cs"
--   combinatorial signals:                 "*_com"
--   pipelined or register delay signals:   "*_d#"
--   counter signals:                       "*cnt*"
--   clock enable signals:                  "*_ce"
--   internal version of output port:       "*_i"
--   device pins:                           "*_pin"
--   ports:                                 "- Names begin with Uppercase"
--   processes:                             "*_PROCESS"
--   component instantiations:              "<ENTITY_>I_<#|FUNC>"
---------------------------------------------------------------------------------------------
library IEEE;
	use IEEE.std_logic_1164.all;
	use IEEE.std_logic_UNSIGNED.all;
library UNISIM;
	use UNISIM.vcomponents.all;
library FastCounter;
	use FastCounter.all;
---------------------------------------------------------------------------------------------
-- Entity pin description
---------------------------------------------------------------------------------------------
--
---------------------------------------------------------------------------------------------
entity CntTenBit is
	port (
		CntClk		: in std_logic;
		CntRst		: in std_logic;
		CntEna		: in std_logic;
		CntOut		: out std_logic_vector(9 downto 0);
		CntTc		: out std_logic
	);
end CntTenBit;
---------------------------------------------------------------------------------------------
-- Architecture section
---------------------------------------------------------------------------------------------
architecture CntTenBit_struct of CntTenBit is
---------------------------------------------------------------------------------------------
-- Component Instantiation
---------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------
-- Constants, Signals and Attributes Declarations
---------------------------------------------------------------------------------------------
-- Functions
-- Constants
constant Low  : std_logic	:= '0';
constant High : std_logic	:= '1';
-- Signals
signal IntCntCmbIn_Msb		: std_logic_vector(1 downto 0);
signal IntCntCmbTcIn_Msb	: std_logic_vector(1 downto 0);
signal IntCntCmbIn_Mid		: std_logic_vector(1 downto 0);
signal IntCntCmbTcIn_Mid	: std_logic_vector(1 downto 0);
signal IntCntCmbTc_Mid		: std_logic;
signal IntCntTc_Mid			: std_logic;
signal IntCntCmbTc_Lsb		: std_logic;
signal IntCntTc_Lsb			: std_logic;
-- Attributes
---------------------------------------------------------------------------------------------
--
begin
--
CntTenBit_I_CntTwoBit_Msb : entity FastCounter.CntTwoBit
    generic map (C_CascdPos => 2) 
	port map (
		CntClk		=> CntClk,              -- in
		CntRst		=> CntRst,              -- in
		CntEna		=> CntEna,              -- in
		CntCmbIn	=> IntCntCmbIn_Msb,     -- in [1:0]
		CntCmbTcIn  => IntCntCmbTcIn_Msb,   -- in [1:0]
		CntOut		=> CntOut(9 downto 8),  -- out [1:0]
		CntCmbTc	=> open,                -- out	
		CntTc		=> CntTc                -- out
	);
--
IntCntCmbIn_Msb <= IntCntTc_Mid & IntCntTc_Lsb;
IntCntCmbTcIn_Msb <= IntCntCmbTc_Mid & IntCntCmbTc_Lsb;
--
CntTenBit_I_CntFourBit_Mid : entity FastCounter.CntFourBit
    generic map (C_CascdPos => 1) 
	port map (
		CntClk		=> CntClk,              -- in
		CntRst		=> CntRst,              -- in
		CntEna		=> CntEna,              -- in
		CntCmbIn	=> IntCntCmbIn_Mid,     -- in [1:0]
		CntCmbTcIn  => IntCntCmbTcIn_Mid,   -- in [1:0]
		CntOut		=> CntOut(7 downto 4),  -- out [3:0]
		CntCmbTc	=> IntCntCmbTc_Mid,     -- out	
		CntTc		=> IntCntTc_Mid         -- out
	);
--
IntCntCmbIn_Mid <= High & IntCntTc_Lsb;
IntCntCmbTcIn_Mid <= High & IntCntCmbTc_Lsb;
--
CntTenBit_I_CntFourBit_Lsb : entity FastCounter.CntFourBit
    generic map (C_CascdPos => 0) 
	port map (
		CntClk		=> CntClk,              -- in
		CntRst		=> CntRst,              -- in
		CntEna		=> CntEna,              -- in
		CntCmbIn	=> "11",                -- in [1:0]
		CntCmbTcIn  => "11",                -- in [1:0]
		CntOut		=> CntOut(3 downto 0),  -- out [3:0]
		CntCmbTc	=> IntCntCmbTc_Lsb,     -- out	
		CntTc		=> IntCntTc_Lsb         -- out
	);
--
---------------------------------------------------------------------------------------------
end CntTenBit_struct;