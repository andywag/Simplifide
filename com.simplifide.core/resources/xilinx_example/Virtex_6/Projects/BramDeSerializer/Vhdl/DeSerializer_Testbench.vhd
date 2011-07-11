---------------------------------------------------------------------------------------------
-- © Copyright 2009 - 2009, Xilinx, Inc. All rights reserved.
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
--  \   \        		Path:                E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Vhdl
--  /   /        		Filename:            DeSerializer_Testbench.vhd
-- /___/   /\    		Date Created:        15 February, 2010  
-- \   \  /  \          Date Last Modified:  15 February, 2010
--  \___\/\___\
-- 
-- Device: 
-- Author:      MD
-- Entity Name: DeSerializer_Testbench
-- Purpose: 	Test Bench for design.
-- Tools:		
-- Limitations: TESTBENCH
--                       DON'T USE THIS FILE FOR COMPILATION NOR INTEGRATION.
--
-- Revision History:
--    Rev. 
--
---------------------------------------------------------------------------------------------
library IEEE;
    use IEEE.std_logic_1164.all;
    use IEEE.std_logic_UNSIGNED.all;
    use STD.textio.all;
library UNISIM;
    use UNISIM.vcomponents.all;
--
entity DeSerializer_Testbench is
-- Declarations
end DeSerializer_Testbench;
--
architecture DeSerializer_Testbench_struct of DeSerializer_Testbench is
---------------------------------------------------------------------------------------------
-- Component Instantiation
---------------------------------------------------------------------------------------------
component DeSerializer
    port (
		DeSerClkA		: in std_logic;
		DeSerEnaA		: in std_logic;
		DeSerDatInA		: in std_logic;
		DeSerPatternA	: in std_logic_vector(31 downto 0);
		DeSerReSync		: in std_logic;
		DeSerFlags		: out std_logic_vector(3 downto 0);
		DeSerClkB		: in std_logic;
		DeSerEnaB		: in std_logic;
		DeSerRstB		: in std_logic;
		DeSerDatOutB	: out std_logic_vector(31 downto 0);
		DeSerWordCntTc	: out std_logic;
		DeSerPttrnCntTc	: out std_logic;
		DeSerStopped	: out std_logic
	);
end component DeSerializer;
--
component DeSerializer_Tester
    port (
		DeSerClkA		: out std_logic;
		DeSerEnaA		: out std_logic;
		DeSerDatInA		: out std_logic;
		DeSerPatternA	: out std_logic_vector(31 downto 0);
		DeSerReSync		: out std_logic;
		DeSerFlags		: in std_logic_vector(3 downto 0);
		DeSerClkB		: out std_logic;
		DeSerEnaB		: out std_logic;
		DeSerRstB		: out std_logic;
		DeSerDatOutB	: in std_logic_vector(31 downto 0);
		DeSerWordCntTc	: in std_logic;
		DeSerPttrnCntTc	: in std_logic;
		DeSerStopped	: in std_logic
	);
end component DeSerializer_Tester;
---------------------------------------------------------------------------------------------
-- Signal Declarations
---------------------------------------------------------------------------------------------
signal Sim_DeSerClkA		: std_logic;
signal Sim_DeSerEnaA		: std_logic;
signal Sim_DeSerDatInA		: std_logic;
signal Sim_DeSerPatternA	: std_logic_vector(31 downto 0);
signal Sim_DeSerReSync		: std_logic;
signal Sim_DeSerFlags		: std_logic_vector(3 downto 0); 
signal Sim_DeSerClkB		: std_logic;
signal Sim_DeSerEnaB		: std_logic;
signal Sim_DeSerRstB		: std_logic;
signal Sim_DeSerDatOutB		: std_logic_vector(31 downto 0);
signal Sim_DeSerWordCntTc	: std_logic;
signal Sim_DeSerPttrnCntTc	: std_logic;
signal Sim_DeSerStopped		: std_logic;
---------------------------------------------------------------------------------------------
begin
--
UUT : DeSerializer
    port map (
		DeSerClkA		=> Sim_DeSerClkA,		-- in
		DeSerEnaA		=> Sim_DeSerEnaA,		-- in
		DeSerDatInA		=> Sim_DeSerDatInA,		-- in
		DeSerPatternA	=> Sim_DeSerPatternA,	-- in
		DeSerReSync		=> Sim_DeSerReSync,		-- in
		DeSerFlags		=> Sim_DeSerFlags,		-- out
		DeSerClkB		=> Sim_DeSerClkB,		-- in
		DeSerEnaB		=> Sim_DeSerEnaB,		-- in
		DeSerRstB		=> Sim_DeSerRstB,		-- in
		DeSerDatOutB	=> Sim_DeSerDatOutB,	-- out
		DeSerWordCntTc	=> Sim_DeSerWordCntTc,	-- out
		DeSerPttrnCntTc	=> Sim_DeSerPttrnCntTc, -- out
		DeSerStopped	=> Sim_DeSerStopped		-- out
	);
--
DTU : DeSerializer_Tester
    port map (
		DeSerClkA		=> Sim_DeSerClkA,		-- out
		DeSerEnaA		=> Sim_DeSerEnaA,		-- out
		DeSerDatInA		=> Sim_DeSerDatInA,		-- out
		DeSerPatternA	=> Sim_DeSerPatternA,	-- out
		DeSerReSync		=> Sim_DeSerReSync,		-- out
		DeSerFlags		=> Sim_DeSerFlags,		-- in
		DeSerClkB		=> Sim_DeSerClkB,		-- out
		DeSerEnaB		=> Sim_DeSerEnaB,		-- out
		DeSerRstB		=> Sim_DeSerRstB,		-- out
		DeSerDatOutB	=> Sim_DeSerDatOutB,	-- in
		DeSerWordCntTc	=> Sim_DeSerWordCntTc,	-- in
		DeSerPttrnCntTc	=> Sim_DeSerPttrnCntTc, -- in
		DeSerStopped	=> Sim_DeSerStopped		-- in
	);
--
-------------------------------------------------------------------------------------------
end DeSerializer_Testbench_struct;
