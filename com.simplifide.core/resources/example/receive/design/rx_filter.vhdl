--------------------------------------------------------------------------------
--  Module  : rx_filter.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 7:53 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library common;
use common.global.all;
use common.registers.all;

--* Band limiting filter for this receiver
--* @port clk Main Clock for this Chip
--* @port reset Global <i>asynchronous</i> Reset for this block
--* @port din  Input to this block
--* @port dout Output from this block
entity rx_filter is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      din    : ComplexSign16;
      regs2rxfilt : regs2filter_record;
      dout   : out ComplexSign16);
     
end rx_filter;     
            


architecture synth of rx_filter is


begin  
     
   
end synth;








