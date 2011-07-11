---------------------------------------------------------------------------------------------
-- Copyright © 2009 - 2010, Xilinx, Inc. 
-- This design is confidential and proprietary of Xilinx, Inc. All Rights Reserved.
---------------------------------------------------------------------------------------------
--
--  Disclaimer:
--          LIMITED WARRANTY AND DISCLAIMER.
--          These designs are provided to you "as is". Xilinx and its licensors make and you
--          receive no warranties or conditions, express, implied, statutory or otherwise,
--          and Xilinx specifically disclaims any implied warranties of merchantability,
--			non-infringement, or fitness for a particular purpose. Xilinx does not warrant
--          that the functions contained in these designs will meet your requirements, or
--          that the operation of these designs will be uninterrupted or error free, or that
--          defects in the designs will be corrected.
--          Furthermore, Xilinx does not warrant or make any representations regarding use or
--			the results of the use of the designs in terms of correctness, accuracy,
--			reliability, or otherwise.
--
--          LIMITATION OF LIABILITY.
--          In no event will Xilinx or its licensors be liable for any loss of data, lost
--			profits, cost or procurement of substitute goods or services, or for any special,
--			incidental, consequential, or indirect damages arising from the use or operation
--          of the designs or accompanying documentation, however caused and on any theory of
--			liability. This limitation will apply even if Xilinx has been advised of the
--			possibility of such damage. This limitation shall apply not-withstanding the
--          failure of the essential purpose of any limited remedies herein.
--
--  Contact:    e-mail  hotline@xilinx.com        phone   + 1 800 255 7778
--   ____  ____
--  /   /\/   /
-- /___/  \  / 			Vendor:              Xilinx Inc.
-- \   \   \/ 			Version:              
--  \   \        		Path:                E:\Projects\BramTricks\Virtex_6\Projects\BramDeSerializer\Vhdl
--  /   /        		Filename:            DeSerializer.vhd
-- /___/   /\    		Date Created:        27 November, 2009  
-- \   \  /  \          Date Last Modified:  27 November, 2009
--  \___\/\___\
-- 
-- Device:          Virtex-6
-- Author:          MD
-- Entity Name:     DeSerializer
-- Purpose:         The deserializer presented here is a 32-bit deserializer.
--					The design taken in 32-bit serial data and outputs that a parallel words.
--					The data can be read from the B-port of the Block-RAM.
--					This is the design showed as "Example Two" in the documentation.
-- Tools:           ISE_11.4 or later
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
library FastCounter;
	use FastCounter.all;
library Control;
	use Control.all;
---------------------------------------------------------------------------------------------
-- Entity pin description
---------------------------------------------------------------------------------------------
-- DeSerClkA		: Clock for port_A of the Block-RAM. This is the serial clock.
-- DeSerEnaA		: Enable for the deserializer (serial shift register).
-- DeSerrstA		: Reset all logic controlling Port_A.
-- DeSerPatternA	: Pattern that must be recognized before shifting data in.
-- DeSerFlags		: Four flags controlling Port_A and Port_B.
-- DeSerClkB		: Clock for port_B of the Block-RAM. This is the parallel clock.
-- DeSerEnaB		: Enable of the parallel output port of the Block-RAM.
-- DeSerRstB		: Reset of the parallel read port logic.
-- DeSerDatOutB		: Parallel output. 
-- DeSerWordCntTc	: Flag indicating the number of time the bit counter has reached its
--					: terminal count.
-- DeSerPttrnCntTc	: Flag indicating that the pattern has been seen several times after
--					: the first time it was detected.
-- DeSerDisabled	: Flag indicating that the address counter for the serializer buffer
--					: has reached terminal count and the parallel readout is not enabled.
--					: The serializer is disabled.
---------------------------------------------------------------------------------------------
entity DeSerializer is
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
end DeSerializer;
---------------------------------------------------------------------------------------------
-- Architecture section
---------------------------------------------------------------------------------------------
architecture DeSerializer_struct of DeSerializer is
---------------------------------------------------------------------------------------------
-- Component Instantiation
---------------------------------------------------------------------------------------------
-- This component can be found in the /Vhdl directory in teh sub-project.
-- A sub-project can be found in the /Libraries directory in the project itself.
--
component Control
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
		Stopped		: out std_logic
	);
end component;
---------------------------------------------------------------------------------------------
-- Constants, Signals and Attributes Declarations
---------------------------------------------------------------------------------------------
-- Functions
-- Constants
constant Low  : std_logic_vector(31 downto 0):= X"00000000";
constant High : std_logic_vector(31 downto 0):= X"11111111";
-- Signals
signal IntDataInA		: std_logic_vector(31 downto 0);
signal IntDataOutA		: std_logic_vector(31 downto 0);
signal IntEquA			: std_logic;
signal IntAddrA			: std_logic_vector(15 downto 0);
signal IntAddrB			: std_logic_vector(15 downto 0);
signal IntBitCntEna		: std_logic;
signal IntBitCntRst		: std_logic;
signal IntAddrCntEnaA	: std_logic;
signal IntAddrCntEnaB	: std_logic;
signal IntBitCntTc		: std_logic;
signal IntAddrCntTcA	: std_logic;
signal IntAddrCntTcB	: std_logic;
signal IntEnaPortA		: std_logic;
signal IntEnaPortB		: std_logic;
signal IntFlags			: std_logic_vector(3 downto 0);
signal IntWordCntEna	: std_logic;
signal IntPttrnCntEna   : std_logic;
signal IntMissCntEna    : std_logic;
signal IntEquBitSetA	: std_logic;
signal IntEquBitRegA	: std_logic;
signal IntWordCntTc		: std_logic;
signal IntMissCntTc		: std_logic;
signal IntPttrnCntTc	: std_logic;
signal IntBitCntRstOrReSync : std_logic;
-- Attributes
---------------------------------------------------------------------------------------------
begin
--
DeSerializer_I_Ramb36E1 : RAMB36E1
  generic map (
    INIT_FILE			 => "NONE", 	-- string
    RAM_MODE			 => "TDP", 		-- string
    WRITE_MODE_A         => "WRITE_FIRST", -- string
    WRITE_MODE_B         => "WRITE_FIRST", -- string
    READ_WIDTH_A         => 36,         -- integer
    READ_WIDTH_B         => 36,         -- integer
    WRITE_WIDTH_A        => 36,         -- integer
    WRITE_WIDTH_B        => 36,         -- integer
    DOA_REG              => 0,          -- integer
    DOB_REG              => 0, 			-- integer
	INIT_A               => X"000000000", -- bit_vector
	INIT_B               => X"000000000", -- bit_vector
    SRVAL_A              => X"000000000", -- bit_vector
    SRVAL_B              => X"000000000", -- bit_vector
    RAM_EXTENSION_A      => "NONE",		-- string
    RAM_EXTENSION_B      => "NONE",		-- string
    RSTREG_PRIORITY_A    => "RSTREG",	-- string
    RSTREG_PRIORITY_B    => "RSTREG",	-- string
    SIM_COLLISION_CHECK  => "ALL"		-- string
  )
  port map (
    CASCADEOUTA		=> open, 			-- out
    DOADO			=> IntDataOutA,	-- out [31:0]
    DOPADOP			=> open, 			-- out [3:0]
    DIADI			=> IntDataInA, 	-- in [31:0]
    DIPADIP			=> Low(3 downto 0), -- in [3:0]
    ADDRARDADDR		=> IntAddrA,		-- in [15:0]
    WEA				=> High(3 downto 0),-- in [3:0]
    ENARDEN			=> IntEnaPortA,		-- in
    REGCEAREGCE		=> Low(0), 			-- in
    CLKARDCLK		=> DeSerClkA,		-- in
	RSTRAMARSTRAM	=> Low(0), 			-- in
    RSTREGARSTREG	=> DeSerReSync,	-- in
    CASCADEINA		=> Low(0), 			-- in
    --
    CASCADEOUTB		=> open, 			-- out
    DOBDO			=> DeSerDatOutB,	-- out [31:0]
    DOPBDOP			=> open, 			-- out [3:0] 
    DIBDI			=> Low(31 downto 0),-- in [31:0] 
    DIPBDIP			=> Low(3 downto 0), -- in [3:0]  
    ADDRBWRADDR		=> IntAddrB,		-- in [15:0] 
    WEBWE			=> Low(7 downto 0), -- in [7:0]  
    ENBWREN			=> IntEnaPortB,		-- in
    REGCEB			=> Low(0), 			-- in
    CLKBWRCLK		=> DeSerClkB,		-- in
    RSTRAMB			=> Low(0), 			-- in
    RSTREGB			=> DeSerRstB,		-- in
    CASCADEINB		=> Low(0), 			-- in
    --                   
    ECCPARITY		=> open, 			-- out [7:0]
    RDADDRECC		=> open, 			-- out [8:0]
    SBITERR			=> open, 			-- out 
    DBITERR			=> open, 			-- out
    INJECTDBITERR	=> Low(0), 			-- in
    INJECTSBITERR	=> Low(0) 			-- in
	);
--
IntDataInA <= IntDataOutA(30 downto 0) & DeSerDatInA;
IntBitCntRstOrReSync <= DeSerReSync or IntBitCntRst; 
---------------------------------------------------------------------------------------------
-- Bit Counter, 5-bit counter (from 0 to 31)
DeSerializer_I_CntFivBit : entity FastCounter.CntFiveBit
	generic map (C_CascdPos => 0)			-- Stand alone counter
	port map (
		CntClk		=> DeSerClkA,			-- in
		CntRst		=> IntBitCntRstOrReSync,-- in
		CntEna	    => IntBitCntEna,		-- in
		CntCmbIn 	=> High(0),				-- in
		CntCmbTcIn	=> High(0),				-- in
		CntOut		=> open,				-- out [4:0]
		CntCmbTc	=> open,				-- out
		CntTc    	=> IntBitCntTc			-- out
	);
---------------------------------------------------------------------------------------------
-- Address counters.
-- Both ports have the same data width, and thus the same address width.
-- 32-bit data requires a 10-bit address counter.
DeSerializer_I_CntTenBit_PortA : entity FastCounter.CntTenBit
	port map (
		CntClk		=> DeSerClkA, 				-- in
		CntRst		=> DeSerReSync, 			-- in
		CntEna	    => IntAddrCntEnaA,			-- in
		CntOut		=> IntAddrA(14 downto 5),	-- out [9:0]
		CntTc    	=> IntAddrCntTcA			-- out
	);
IntAddrA(15) <= Low(0);
IntAddrA(4 downto 0) <= Low(4 downto 0);
--
DeSerializer_I_CntTenBit_PortB : entity FastCounter.CntTenBit
	port map (
		CntClk		=> DeSerClkB,	 			-- in
		CntRst		=> DeSerRstB, 				-- in
		CntEna	    => IntAddrCntEnaB,			-- in
		CntOut		=> IntAddrB(14 downto 5),	-- out [9:0]
		CntTc    	=> IntAddrCntTcB			-- out
	);
IntAddrB(15) <= Low(15);
IntAddrB(4 downto 0) <= Low (4 downto 0);
---------------------------------------------------------------------------------------------
-- Port_A output comparator
-- Ouput of port_A must be equal to a given pattern.
IntEquA <= High(0) when (IntDataOutA = DeSerPatternA) else Low(0);
---------------------------------------------------------------------------------------------
-- Flags
-- Only the top two bits of the binary address counter are taken to provide an flag that
-- reports one out of four blocks where the read or write pointer are.
-- Flag(0) = empty (Empty to 1/4)
-- Flag(1) = LoMid (1/4 to 1/2)
-- Flag(2) = HiMid (1/2 to 3/4)
-- Flag(3) = Full (3/4 to full)
-- The flags are synchronized with the parallel output clock.
-- Port A = write port, Port B = read port
IntFlags(3) <=	'1' when (IntAddrA(14 downto 13) = "10" and IntAddrB(14 downto 13) = "11") else
				'1' when (IntAddrA(14 downto 13) = "01" and IntAddrB(14 downto 13) = "10") else
				'1' when (IntAddrA(14 downto 13) = "00" and IntAddrB(14 downto 13) = "01") else
				'1' when (IntAddrA(14 downto 13) = "11" and IntAddrB(14 downto 13) = "00") else
				'0';
IntFlags(2) <=	'1' when (IntAddrA(14 downto 13) = "01" and IntAddrB(14 downto 13) = "11") else
				'1' when (IntAddrA(14 downto 13) = "00" and IntAddrB(14 downto 13) = "10") else
				'1' when (IntAddrA(14 downto 13) = "11" and IntAddrB(14 downto 13) = "01") else
				'1' when (IntAddrA(14 downto 13) = "10" and IntAddrB(14 downto 13) = "00") else
				'0';
IntFlags(1) <=	'1' when (IntAddrA(14 downto 13) = "00" and IntAddrB(14 downto 13) = "11") else
				'1' when (IntAddrA(14 downto 13) = "11" and IntAddrB(14 downto 13) = "10") else
				'1' when (IntAddrA(14 downto 13) = "10" and IntAddrB(14 downto 13) = "01") else
				'1' when (IntAddrA(14 downto 13) = "01" and IntAddrB(14 downto 13) = "00") else
				'0';
IntFlags(0) <=	'1' when (IntAddrA(14 downto 13) = "11" and IntAddrB(14 downto 13) = "11") else
				'1' when (IntAddrA(14 downto 13) = "10" and IntAddrB(14 downto 13) = "10") else
				'1' when (IntAddrA(14 downto 13) = "01" and IntAddrB(14 downto 13) = "01") else
				'1' when (IntAddrA(14 downto 13) = "00" and IntAddrB(14 downto 13) = "00") else
				'0';
--
DeSerializer_Flags_PROCESS : process (DeSerClkB)
begin
    if (DeSerClkB'event and DeSerClkB = '1') then
        if (DeSerRstB = '1') then
            DeSerFlags <= (others => '0');
        else
            DeSerFlags <= IntFlags;
        end if;
    end if;
end process; 
---------------------------------------------------------------------------------------------
-- Control state machine.
DeSerializer_I_Ctrl : Control
	port map (
		ClkA		=> DeSerClkA,		-- in
		ClkB		=> DeSerClkB,		-- in
		EnaA		=> DeSerEnaA,		-- in
		EnaB		=> DeSerEnaB,		-- in
		ReSync		=> DeSerReSync,		-- in
		BitCntTc	=> IntBitCntTc,		-- in
		PttrnEqu	=> IntEquA,			-- in
		AddrCntTcA	=> IntAddrCntTcA,	-- in
		AddrCntTcB	=> IntAddrCntTcB,	-- in
		BitCntEna	=> IntBitCntEna,	-- out
		BitCntRst	=> IntBitCntRst,	-- out
		EnaPortA	=> IntEnaPortA,		-- out
		AddrCntEnaA	=> IntAddrCntEnaA,	-- out
		EnaPortB	=> IntEnaPortB,		-- out
		AddrCntEnaB	=> IntAddrCntEnaB,	-- out
		WordCntEna	=> IntWordCntEna,	-- out
		PttrnCntEna	=> IntPttrnCntEna,	-- out
		Stopped 	=> DeSerStopped		-- out
	);
---------------------------------------------------------------------------------------------
-- Flag counters
-- All flags react after a 16 count.
DeSerializer_I_FourBit_WordCnt : entity FastCounter.CntFourBit
	generic map (C_CascdPos	=> 0)		-- Stand alone counter
	port map (
		CntClk	    => DeSerClkA,			-- in
		CntRst	    => DeSerReSync,			-- in
		CntEna	    => IntWordCntEna,		-- in
		CntCmbIn	=> High(1 downto 0),	-- in
        CntCmbTcIn	=> High(1 downto 0),	-- in
		CntOut	    => open,				-- out [3:0]
		CntCmbTc	=> open,
		CntTc	 	=> IntWordCntTc
	);
DeSerWordCntTc <= IntWordCntTc;
--
DeSerializer_I_FourBit_PttrnCnt : entity FastCounter.CntFourBit
	generic map (C_CascdPos	=> 0)		-- Stand alone counter
	port map (
		CntClk	    => DeSerClkA,			-- in
		CntRst	    => DeSerReSync,			-- in
		CntEna	    => IntPttrnCntEna,		-- in
		CntCmbIn	=> High(1 downto 0),	-- in
        CntCmbTcIn	=> High(1 downto 0),	-- in
        CntOut	    => open,				-- out [3:0]
        CntCmbTc	=> open,				-- out
		CntTc	 	=> IntPttrnCntTc		-- out
	);
DeSerPttrnCntTc <= IntPttrnCntTc;
---------------------------------------------------------------------------------------------
end DeSerializer_struct;
--