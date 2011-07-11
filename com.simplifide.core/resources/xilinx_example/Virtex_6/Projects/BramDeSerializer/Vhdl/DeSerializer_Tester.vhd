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
--  \   \        		
--  /   /        		Filename:            DeSerializer_Tester.vhd
-- /___/   /\    		Date Created:        15 February, 2010  
-- \   \  /  \          Date Last Modified:  15 February, 2010
--  \___\/\___\
-- 
-- Device: 
-- Author:       MD
-- Entity Name:  DeSerializer_Tester
-- Purpose:      Stimulus file for testbench
-- Tools:
-- Limitations:  TESTBENCH / STIMULUS;
--               DON'T USE THIS FILE FOR COMPILATION NOR INTERATION.
--
-- Revision History:
--    Rev. 
--
---------------------------------------------------------------------------------------------
library IEEE;
    use IEEE.std_logic_1164.all;
    use IEEE.std_logic_UNSIGNED.all;
    use IEEE.std_logic_textio.all;
    use std.textio.all;
    use IEEE.std_logic_arith.all;
---------------------------------------------------------------------------------------------
entity DeSerializer_Tester is
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
end DeSerializer_Tester;
--
architecture DeSerializer_Tester_flow of DeSerializer_Tester is
---------------------------------------------------------------------------------------------
-- Functions
---------------------------------------------------------------------------------------------
function int_to_chr(int: integer) return character is
    variable temp : character;
begin
	case int is
		when  0 => temp := '0';
		when  1 => temp := '1';
		when  2 => temp := '2';
		when  3 => temp := '3';
		when  4 => temp := '4';
		when  5 => temp := '5';
		when  6 => temp := '6';
		when  7 => temp := '7';
		when  8 => temp := '8';
		when  9 => temp := '9';
		when 10 => temp := 'A';
		when 11 => temp := 'B';
		when 12 => temp := 'C';
		when 13 => temp := 'D';
		when 14 => temp := 'E';
		when 15 => temp := 'F';
		when 16 => temp := 'G';
		when 17 => temp := 'H';
		when 18 => temp := 'I';
		when 19 => temp := 'J';
		when 20 => temp := 'K';
		when 21 => temp := 'L';
		when 22 => temp := 'M';
		when 23 => temp := 'N';
		when 24 => temp := 'O';
		when 25 => temp := 'P';
		when 26 => temp := 'Q';
		when 27 => temp := 'R';
		when 28 => temp := 'S';
		when 29 => temp := 'T';
		when 30 => temp := 'U';
		when 31 => temp := 'V';
		when 32 => temp := 'W';
		when 33 => temp := 'X';
		when 34 => temp := 'Y';
		when 35 => temp := 'Z';
		when others => temp := '?';
	end case;
return temp;
end function int_to_chr;

function int_to_str(int: integer; base: integer) return string is
    variable temp:      string(1 to 10);
    variable num:       integer;
    variable abs_int:   integer;
    variable len:       integer := 1;
    variable power:     integer := 1;
begin
    abs_int := abs(int);	-- Negative numbers
    num     := abs_int;
    
    while num >= base loop                     -- Determine how many
      len := len + 1;                          -- characters required
      num := num / base;                       -- to represent the
    end loop ;                                 -- number.

    for i in len downto 1 loop        			        -- Convert the number to
      temp(i) := int_to_chr(abs_int/power mod base);	-- a string starting
      power := power * base;                   			-- with the right hand
    end loop ;                                	 		-- side.

    -- return result and add sign if required
    if int < 0 then
       return '-'& temp(1 to len);
     else
       return temp(1 to len);
    end if;
end function int_to_str;

function str_to_stdlvec(inp: string) return std_logic_vector is
	variable temp: std_logic_vector(inp'range) := (others => 'X');
begin 
	for i in inp'range loop
		if (inp(i) = '1') then
			temp(i) := '1';
		elsif (inp(i) = '0') then
			temp(i) := '0'; 
		end if;
	end loop;
return temp;
end function str_to_stdlvec;

function stdlvec_to_str(inp: std_logic_vector) return string is
	variable temp: string(inp'left+1 downto 1) := (others => 'X');
begin
	for i in inp'reverse_range loop
		if (inp(i) = '1') then
			temp(i+1) := '1';
		elsif (inp(i) = '0') then
			temp(i+1) := '0'; 
		end if;
	end loop;
return temp;
end function stdlvec_to_str;
---------------------------------------------------------------------------------------------
-- Architecture Declarations
---------------------------------------------------------------------------------------------
constant Pattern : std_logic_vector(31 downto 0):= "11111110000000011111111000000001";
constant ClockPeriod : time := 2.5 ns;
signal IntClockA : std_logic;
signal IntClockB : std_logic;
---------------------------------------------------------------------------------------------
begin
---------------------------------------------------------------------------------------------
-- Processes
---------------------------------------------------------------------------------------------
MainProc : process
	file TextFile : text;
	variable LineLength : line;
	variable TextIn : string (16 downto 1);
	variable IntDeSerDatInA : std_logic_vector(15 downto 0);
	constant NmbrOfBits : integer := 16;
begin
FILE_OPEN (TextFile,
	-- Ser_1_Vec.txt = Shart test pattern file without valid pattern
	"E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Vhdl\Ser_1_Vec.txt", READ_MODE);
	-- Ser_2_Vec.txt = Short test pattern file with valid pattern
--	"E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Vhdl\Ser_2_Vec.txt", READ_MODE);
--
DeSerPatternA <= Pattern;
DeSerReSync <= '1';
DeSerEnaA <= '0';
DeSerEnaB <= '0';
wait for ClockPeriod*25;
DeSerReSync <= '0';
wait for ClockPeriod*20;
DeSerEnaA <= '1';
--
loop
	if endfile(TextFile) then
		assert false
		report "    End of data file; exiting."
		severity note;
		exit;
	end if;
	--
	readline (TextFile, LineLength);  -- read a line
	read (LineLength, TextIn);
	IntDeSerDatInA := str_to_stdlvec(TextIn);
	--
	Shift : for n in NmbrofBits-1 downto 0 loop
		wait until IntClockA'event and IntClockA = '1';
		DeSerDatInA <= IntDeSerDatInA(n) after 1 ns;
	end loop Shift;
end loop;
--
assert false
report "That's All Folks !"
severity warning;
wait;
--
end process MainProc;
---------------------------------------------------------------------------------------------
-- Architecture Concurrent Statements
---------------------------------------------------------------------------------------------
-- Generate Clock
TheClockA : process
begin
	IntClockA <= '0';
	wait for ClockPeriod;
	IntClockA <= '1';
	wait for ClockPeriod;
end process;
--
TheClockB : process
begin
	IntClockB <= '0';
	wait for ClockPeriod/2;
	IntClockB <= '1';
	wait for ClockPeriod/2;
end process;
--
DeSerClkA <= IntClockA;
DeSerClkB <= IntClockB;
--
-------------------------------------------------------------------------------------------
end DeSerializer_Tester_flow;
