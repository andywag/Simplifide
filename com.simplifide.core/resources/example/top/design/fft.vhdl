--------------------------------------------------------------------------------
--  Module  : rx_fft.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:14 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;

entity fft is 

port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      txFftInput    : ComplexSign16;
      rxFftInput    : ComplexSign16;
      fftOutput : out ComplexSign16);
    
end fft;     
   


architecture synth of fft is


begin  
     
   
end synth;








