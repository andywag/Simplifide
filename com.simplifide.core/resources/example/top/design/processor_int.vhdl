--------------------------------------------------------------------------------
--  Module  : processor_int.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 10:49 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;
use common.registers.all;

entity processor_int is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      procWr     : std_logic;
      procRd     : std_logic;
      procWrBus : std_logic_vector(31 downto 0);
      procRdBus  : out std_logic_vector(31 downto 0);
      regs2rx_top : common.registers.regs2rx_top_record);
    
end processor_int;     
            


architecture synth of processor_int is


begin  
     
   
end synth;








