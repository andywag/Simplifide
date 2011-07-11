--------------------------------------------------------------------------------
--  Module  : downconverter.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 7:51 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library common;
use common.global.all;
use common.registers.all;

--* Digital Downconversion block to bring signal down to baseband
--* @port clk System Clock
--* @port reset Global <i>asynchronous</i> for this block
--* @port din  input signal to this block
--* @port dout output signal from this block 
entity downconverter is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      regs2down : regs2downconverter_record;
      din    : ComplexSign16;
      dout   : out ComplexSign16);
    
end downconverter;     
            


architecture synth of downconverter is


begin  
     




end synth;








