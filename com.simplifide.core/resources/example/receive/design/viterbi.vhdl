--------------------------------------------------------------------------------
--  Module  : viterbi.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:29 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;

--* Standard viterbi module which takes the equalizer input in, and outputs bits 
--* which are valid when outVld is high
--* @port clk Global Chip Clock
--* @port reset Global <i> asynchronous <i> reset
--* @port enable Enable Siganl for this block
--* @port din Input signal to this block 
--* @port outVld Valid signal which dileneates the byte boundaries
--* @port dout Output bytes
entity viterbi is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      din    : ComplexSign16;
      outVld : out std_logic;
      dout   : out std_logic_vector(7 downto 0));
    
end viterbi;     
            


architecture synth of viterbi is


begin  
     
   
end synth;








