--------------------------------------------------------------------------------
--  Module  : equalizer.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:27 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;

entity equalizer is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      din    : ComplexSign16;
      dout   : ComplexSign16);
    
end equalizer;     
            


architecture synth of equalizer is


begin  
     
   
end synth;








