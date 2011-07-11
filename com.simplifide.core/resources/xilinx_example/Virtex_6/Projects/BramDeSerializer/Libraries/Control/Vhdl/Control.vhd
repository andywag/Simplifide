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
--  \   \        		Path:                E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Libraries\Control\Vhdl
--  /   /        		Filename:            Control.vhd
-- /___/   /\    		Date Created:        06 January, 2010  
-- \   \  /  \          Date Last Modified:  06 January, 2010
--  \___\/\___\
-- 
-- Device:          
-- Author:          MD
-- Entity Name:     Control
-- Purpose:         <purpose of this file>
-- Tools:           
-- Limitations:     none
--
-- Revision History:
--    Rev. 
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
---------------------------------------------------------------------------------------------
-- Entity pin description
---------------------------------------------------------------------------------------------
-- Clk				-- Clock
-- EnaA				-- Enable for the deserilaizer.
-- EnaB				-- Enable for the parallel read storage memory.
-- ReSync			-- Local reset, provokes a resync of the circuit.
-- BitCntTc			-- Bit counter terminal count.
-- EquA				-- Output of the deserializer and given pattern match.
-- AddrCntTcA		-- Address counter of the Port A of the Block-RAM (WRITE). 
-- AddrCntTcB		-- Address counter of the Port B of the Block-RAM (READ).
-- BitCntEna		-- Enable for the bit counter.
-- BitCntRst		-- Reset for the bit counter.
-- EnaPortA			-- Enable for port_A of the Block-RAM.
-- AddrCntEnaA		-- Enable for the Port_A address counter.
-- EnaPortB			-- Enable for port_B of the Block-RAM.
-- AddrCntEnaB		-- Enable for port_B address counter.
-- WordCntEna		-- Enable for the number of word without a match counter.
-- PttrnCntEna		-- Enable for the nu,ber of detected patterns after a match counter.
---------------------------------------------------------------------------------------------
entity Control is
	port (
		ClkA		: in std_logic;
		ClkB		: in std_logic;
		EnaA		: in std_logic;
		EnaB		: in std_logic;
		ReSync		: in std_logic;
		BitCntTc	: in std_logic;
		PttrnEqu	: in std_logic;
		AddrCntTcA	: in std_logic;
		AddrCntTcB	: in std_logic;
		BitCntEna	: out std_logic;
		BitCntRst	: out std_logic;
		EnaPortA	: out std_logic;
		AddrCntEnaA	: out std_logic;
		EnaPortB	: out std_logic;
		AddrCntEnaB	: out std_logic;
		WordCntEna	: out std_logic;
		PttrnCntEna	: out std_logic;
		Stopped 	: out std_logic
	);
end Control;
---------------------------------------------------------------------------------------------
-- Architecture section
---------------------------------------------------------------------------------------------
architecture Control_struct of Control is
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
signal IntProceed		: std_logic;
signal IntProceed_n		: std_logic;
signal IntReset			: std_logic;
signal IntPttrnEquEna	: std_logic;
signal IntPttrnEqu_or	: std_logic;
signal IntPttrnEqu_d	: std_logic;
signal IntStopped		: std_logic;
signal IntAddrCntTcA_d	: std_logic;
-- Attributes
---------------------------------------------------------------------------------------------
--
begin
    ---------------------------------------------------------------------------------------------
IntReset <= ReSync or IntStopped;
---------------------------------------------------------------------------------------------
-- Proceed signal that runs synchronously with the counters.
Intproceed_n <= IntProceed;
-- Start "Proceed" at a HIGH level.
Ctrl_I_StrtHig_Fdpe : FDPE
generic map (INIT => '1')
port map (D => IntProceed_n, CE => EnaA, C => ClkA, PRE => IntReset, Q => IntProceed);
-- Start "Proceed" at a LOW level.
-- Ctrl_I_StrtLow_Fdce : FDCE
-- generic map (INIT => '0')
-- port map (D => IntProceed, CE => EnaA, C => ClkA, CLR => IntReset, Q => IntProceed);
---------------------------------------------------------------------------------------------
-- Enable bit counter, Port_A, and Pattern Equality bit storage.
-- Bits can be shifted in and counted by the bit counter.
-- Tne "EnaA" signal must be kept high as long as the serializer must be enabled. When
-- "EnaA" is low the serializer and storage is disabled.
-- A small circuit can be added so that only a pulse on "EnaA" is needed to start and stop
-- the desierializer. 
Cntrl_SideA_PROCESS : process (ClkA)
begin
    if (ClkA'event and ClkA = '1') then
        if (IntReset = '1') then
            BitCntEna <= '0';
    		EnaPortA <= '0';
    	else -- (IntReset = '0') then
    		BitCntEna <= EnaA;
    		EnaPortA <= EnaA;
    		IntPttrnEquEna <= EnaA;
    	end if;
    end if;
end process;
Cntrl_PttrnEna_PROCESS : process (ClkA, IntPttrnEqu_d)
begin
    if (ClkA'event and ClkA = '1') then
        if (IntReset = '1') then
    		IntPttrnEquEna <= '0';
    	elsif (EnaA = '1') then
    	    IntPttrnEquEna <= '1';
    	elsif (IntPttrnEqu_d = '1') then
    	    IntPttrnEquEna <= '0';
    	end if;
    end if;
end process;            
---------------------------------------------------------------------------------------------
-- When the wanted pattern, given on a higher hierarchical level, is recognised register
-- the fact that a pattern has been detected.
Cntrl_PttrnReg_PROCESS : process (ClkA)
begin
    if (ClkA'event and ClkA = '1') then
        if (IntReset = '1') then
            IntPttrnEqu_or <= '0';
        elsif (IntPttrnEquEna = '1') then
            IntPttrnEqu_or <= PttrnEqu;
        end if;
	end if;
end process;
IntPttrnEqu_d <= IntPttrnEqu_or or (PttrnEqu and IntPttrnEquEna);
---------------------------------------------------------------------------------------------
-- When a pattern has been detected reset the bit counter. 
-- From then on the counter will only count full words.
BitCntRst <= PttrnEqu;
---------------------------------------------------------------------------------------------
-- Counter the received words without recognising a pattern in the received.
-- This can be useful when the received bits come in frames. When the number of words in a
-- frame is reached without finding a pattern, some action can be enabled.
WordCntEna <= BitCntTc when (IntPttrnEqu_d = '0') else '0';
---------------------------------------------------------------------------------------------
-- Count the number of detected patterns in a incomming stream when a first pattern is
-- already detected. This can be useful to check if resyncronisation is necessary.
PttrnCntEna <= PttrnEqu when (IntPttrnEqu_d = '1') else '0';
---------------------------------------------------------------------------------------------
-- When a pattern has been found and when the bit counter reaches a terminal count, then
-- increment "Port_A" address counter to enable storage of new serial captured data.
AddrCntEnaA <= BitCntTc when (IntPttrnEqu_d = '1') else '0';
---------------------------------------------------------------------------------------------
-- Enable Port_B and it's address counter.
Cntrl_SideB_PROCESS : process (ClkB)
begin
    if (ClkB'event and ClkB = '1') then
        if (IntReset = '1') then
            AddrCntEnaB <= '0';
            EnaPortB <= '0';
        else
            AddrCntEnaB <= EnaB;
            EnaPortB <= EnaB;
        end if;
	end if;
end process;
---------------------------------------------------------------------------------------------
-- When the serial-to-parallel function arrived at the end of the storage, check if the parallel
-- read read is enabled. If not stop.
Cntrl_AddrCntTcA_PROCESS : process (ClkA)
begin
    if (ClkA'event and ClkA = '1') then
        if (IntReset = '1' and EnaB = '1') then
            IntAddrCntTcA_d <= '0';
      	elsif (AddrCntTcA = '1') then
      	    IntAddrCntTcA_d <= '1';
      	end if;
    end if;
end process;
--
IntStopped <=  IntAddrCntTcA_d or AddrCntTcA; 
---------------------------------------------------------------------------------------------
end Control_struct;
