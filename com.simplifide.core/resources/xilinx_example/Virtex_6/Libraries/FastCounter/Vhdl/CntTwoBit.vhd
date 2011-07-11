---------------------------------------------------------------------------------------------
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
--  \   \        		
--  /   /        		Filename:            CntTwoBit.vhd
-- /___/   /\    		Date Created:        24 March, 2009  
-- \   \  /  \          Date Last Modified:  27 April, 2010
--  \___\/\___\
-- 
-- Device:          Virtex-6
-- Author:          MD
-- Entity Name:     CntTwoBit
-- Purpose:         This is a Two bit counter.
--					The counter has two extra inputs for counter cascading functions.
--					When both inputs are 'High' the counter is able to count, otherwise
--					the counter stalls.
--					The counter output bits and the terminal count (TC) output have each
--					an enable input. In normal conditions both are joined together as one
--					enbale signal. When TC is not needed the enable for the TC flipflop
--					can be tied to 'Low' and then that FF will be optimized at inplementation.  
-- Tools:           ISE_11.5
-- Limitations:     none
--
-- Revision History:
--	Rev. 27 Apr 2010
--		tested with Virtex-6 and ISE_11.5
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
--
library IEEE;
	use IEEE.std_logic_1164.all;
	use IEEE.std_logic_UNSIGNED.all;
library UNISIM;
	use UNISIM.vcomponents.all;
---------------------------------------------------------------------------------------------
-- Entity pin description
---------------------------------------------------------------------------------------------
-- Generics
--		C_CascdPos	: Position of the counter in a cascade chain.
--					: When the counter is used stand alone then this must be '0'.
--					: When the counter represents the LSB bits in a counter cascade this is '0'.
--					: When the counter is part of a cascade chain, indicate the position.
--					: example, View 10-bit counter.
-- Ports 
--		CntClk		: Clock for the counter.
--		CntRst		: Reset of the counter.
--		CntEna		: Enable input. Enables the FFs of the counter
--		CntCmbIn	: Input from the clocked TC output of the previous counter.
--		CntCmbTcIn	: input from the combinatorial TC output of the previous counter.
--					: The two ablove inputs are not used wnhen C_CascdPos = 0, Tie High.
--		CntOut		: Counter output.
--		CntCmbTc	: Combinatorial TC output.
--		CntTc		: Registered TC output.
---------------------------------------------------------------------------------------------
entity CntTwoBit is
    generic (C_CascdPos : integer := 0); 
	port(
		CntClk		: in std_logic;
		CntRst		: in std_logic;
		CntEna		: in std_logic;
		CntCmbIn	: in std_logic_vector(1 downto 0);
		CntCmbTcIn  : in std_logic_vector(1 downto 0);
		CntOut		: out std_logic_vector(1 downto 0);
		CntCmbTc	: out std_logic;	
		CntTc		: out std_logic
	);
end CntTwoBit;
---------------------------------------------------------------------------------------------
-- Architecture section
---------------------------------------------------------------------------------------------
architecture CntTwoBit_struct of CntTwoBit is
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
signal IntCntCmbIn		: std_logic_vector(1 downto 0);
signal IntCntCmbTcIn	: std_logic_vector(1 downto 0);	
signal IntCnt			: std_logic_vector(1 downto 0);
signal IntCntLog		: std_logic_vector(1 downto 0);
signal IntCntTcLog		: std_logic;
signal IntCntTc			: std_logic;
-- Attributes
---------------------------------------------------------------------------------------------
--
begin
-- The FFs of the counter and its logic are written as separate processes.
-- The FFs use now their dedicated inputs for reset and enable.
-- The counter process is described as a ROM kind of logic.
-- This could eventually be replaced by instantiating LUT primitives.
-- The counter runs now at a speed of: 708 MHz.
--
CntTwoBit_PROCESS : process (CntClk)
begin
    if (CntClk'event and CntClk = '1') then
        if (CntRst = '1') then
            IntCnt <= (others => '0');
            IntCntTc <= '0';
        elsif (CntEna = '1') then
            IntCnt <= IntCntLog;
            IntCntTc <= IntCntTcLog;
        end if; 
   	end if;
end process;
--
CntOut <= IntCnt;
CntTc <= IntCntTc;
CntCmbTc <= IntCntTcLog;
IntCntCmbIn <= "11" when (C_CascdPos = 0) else CntCmbIn;
IntCntCmbTcIn <= "11" when (C_CascdPos = 0) else CntCmbTcIn;
--
-- This is the description of a 6-input Look-Up-Table.
-- Only used for two + two inputs.
CntCmb_PROCESS : process (IntCnt, IntCntCmbIn)
subtype CaseSel is std_logic_vector(3 downto 0);
begin
    CntLog : case CaseSel'(IntCntCmbIn(1 downto 0) & IntCnt(1 downto 0)) is
        when "0000" => IntCntLog <= "00";
        when "0001" => IntCntLog <= "01";
        when "0010" => IntCntLog <= "10";
        when "0011" => IntCntLog <= "11";
        --
        when "0100" => IntCntLog <= "00";
        when "0101" => IntCntLog <= "01";
        when "0110" => IntCntLog <= "10";
        when "0111" => IntCntLog <= "11";
		--
        when "1000" => IntCntLog <= "00";
        when "1001" => IntCntLog <= "01";
        when "1010" => IntCntLog <= "10";
        when "1011" => IntCntLog <= "11";
		--
        when "1100" => IntCntLog <= "01";
        when "1101" => IntCntLog <= "10";
        when "1110" => IntCntLog <= "11";
        -- when "1111" => IntCntLog <= "00";
        when others => IntCntLog <= "00";
    end case;
end process;
--
-- This is the terminal count functionality.
-- When treaded correctly this function fits in a 6-input LUT function.
Gen_1 : if (C_CascdPos = 0) generate
    CntCmbTc_PROCESS : process (IntCnt, IntCntCmbTcIn)
    subtype CaseSel is std_logic_vector(3 downto 0);
    begin
        CntLog : case CaseSel'(IntCntCmbTcIn(1 downto 0) & IntCnt(1 downto 0)) is
            when "1110" => IntCntTcLog <= '1';
            when "0011" => IntCntTcLog <= '1';
            when "0111" => IntCntTcLog <= '1';
            when "1011" => IntCntTcLog <= '1';
            when others => IntCntTcLog <= '0';
        end case;
    end process;
end generate Gen_1;
--
Gen_2 : if (C_CascdPos /= 0) generate 
    CntCmbTc_PROCESS : process (IntCnt, IntCntCmbTcIn)
    subtype CaseSel is std_logic_vector(3 downto 0);
    begin
        CntLog : case CaseSel'(IntCntCmbTcIn(1 downto 0) & IntCnt(1 downto 0)) is
            when "1111" => IntCntTcLog <= '1';
            when others => IntCntTcLog <= '0';
        end case;
    end process;
end generate Gen_2;
--
---------------------------------------------------------------------------------------------
end CntTwoBit_struct;
--