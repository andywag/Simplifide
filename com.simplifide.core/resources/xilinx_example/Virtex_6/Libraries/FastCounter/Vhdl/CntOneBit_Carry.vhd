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
-- /___/  \  / 			Vendor:              Xilinx Inc.
-- \   \   \/ 			Version:              
--  \   \        		
--  /   /        		Filename:            CntOneBit_Carry.vhd
-- /___/   /\    		Date Created:        31 March, 2009  
-- \   \  /  \          Date Last Modified:  31 March, 2009
--  \___\/\___\
-- 
-- Device:          
-- Author:          MD
-- Entity Name:     CntOneBit_Carry
-- Purpose:         One bit counter.
--					Counter has a local enable (terminal count enable) decoded in logic.
--					Counter has a system enable connectd to the FFs enable input.
--					Counter has a synchronous reset.
-- Tools:           
-- Limitations:     none
--
-- Revision History:
--    Rev. 
--
---------------------------------------------------------------------------------------------
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
---------------------------------------------------------------------------------------------
-- Entity pin description
---------------------------------------------------------------------------------------------
-- CntClk		: Clock input.
-- CntRst		: Synchronous reset input.
-- CntEna	    : System (FF) enable input
-- CntTcEna	    : Enables the LUT at terminal count, stand lone 4-bit counter = '1'
-- CntTcInit    : initalisation input of the carry chain, needed for the terminal count.
-- CntTc		: Combinatorial Terminal Count Output.
-- CntOut		: Counter outputs.
---------------------------------------------------------------------------------------------
entity CntOneBit_Carry is
	port (
		CntClk	    : in std_logic;
		CntRst	    : in std_logic;
		CntEna	    : in std_logic;
		CntTcEna	: in std_logic;
        CntTcInit   : in std_logic;
		CntTc	 	: out std_logic_vector(0 downto 0);
		CntOut	    : out std_logic_vector(0 downto 0)
	);
end CntOneBit_Carry;
---------------------------------------------------------------------------------------------
-- Architecture section
---------------------------------------------------------------------------------------------
architecture CntOneBit_Carry_struct of CntOneBit_Carry is
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
type InitIncVal is array (1 downto 0) of bit_vector (63 downto 0);
-- Depending the counter size these value might change.
-- Use the "LUT6_2_n_INIT.xls" file in teh /Documents directory to determine
-- the LUT values for the counter.
-- Copy the original XLS file to a file you can use for your initialisation of LUTs.
-- View the "Bin_4_Bit_Cnt LUT6_2_n_INIT.xls" file for a 4-bit counter.
constant IncLutVal : InitIncVal := (
	X"FFFFFFFFFFFFFFFF",	-- Dummy
	X"5555AAAAAAAA0000"		-- Address zero
);
-- Signals
signal IntCntO6	         : std_logic_vector(3 downto 0);
signal IntCntO5	         : std_logic_vector(3 downto 0);
signal IntCnt	         : std_logic_vector(0 downto 0);
signal IntCntCarOut      : std_logic_vector(3 downto 0);
-- Attributes
attribute IOB : string;
    attribute IOB of Cnt_PROCESS : label is "FALSE";
attribute RLOC : string;
	type RlocValue is array (1 downto 0) of string (4 downto 1);
	constant RlocVal : RlocValue := (
		"X0Y0",		-- Dummy
		"X0Y0"		-- Bit 0
	);
    attribute RLOC of Cnt_PROCESS  : label is "X0Y0";
    attribute RLOC of Cnt_I_Carry4 : label is "X0Y0";
--attribute MAXDELAY : string;
--    attribute MAXDELAY of IntCnt : signal is "600 ps";
---------------------------------------------------------------------------------------------
begin
--
Gen_CntLut : for i in 0 to 0 generate
	attribute RLOC of IncrLut : label is RlocVal(i);
begin
	IncrLut : LUT6_2
generic map (INIT => IncLutVal(i))
port map (
	I0 	=> IntCnt(0),
	I1 	=> Low,			-- or High
	I2 	=> Low,			-- or High
	I3 	=> Low,			-- or High
    I4  => CntTcEna,	-- Connected to teh TermCnt of the previous counter.
    I5  => High,
	O6 	=> IntCntO6(i),
    O5  => IntCntO5(i)
);
end generate;
--
Cnt_PROCESS : process (CntClk)
begin
    if (CntClk'EVENT and CntClk = '1') then
        if (CntRst = '1') then
            IntCnt <= (others => '0');
        elsif (CntEna = '1') then
            IntCnt <= IntCntO6(0 downto 0);
        end if; 
   	end if;
end process;
--
IntCntO5(3 downto 1) <= Low & Low & Low;
IntCntO6(3 downto 1) <= High & High & High;
--
Cnt_I_Carry4 : CARRY4
	port map (
		DI		=> IntCntO5,
		S		=> IntCntO6,
		CYINIT	=> CntTcInit,
		CI		=> Low,
		O		=> open,
		CO		=> IntCntCarOut
	);
--
-- For a 4-bit counter the terminal count is bit 3 of CntTc.
-- Another counter can use any of the other bits as terminal count output.
-- Example: A 2-bit counter will use bit 1 as terminal count output.
CntTc <= IntCntCarOut(0 downto 0);
-- This ar ethe four counter bits.
-- When using a small counter do not connect the unused upper bits and they will get
-- optimized by the software
CntOut <= IntCnt;
--
---------------------------------------------------------------------------------------------
end CntOneBit_Carry_struct;