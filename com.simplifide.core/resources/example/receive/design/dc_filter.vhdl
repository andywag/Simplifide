--------------------------------------------------------------------------------
--  Module  : dc_filter.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 7:32 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library common;
use common.global.all;
use common.registers.all;

--* Dc blocking filter for the receiver
--* @port clk main clock port
--* @port reset main <i>asynchronous</i> reset for the block
--* @port din input data to this block
--* @port dout output data from this block
entity dc_filter is 
    port (clk : std_logic;
          reset : std_logic;
          enable : std_logic;
          din    : ComplexSign16;
          regs2dcfilt : regs2dcfilt_record;
          dout   : out ComplexSign16);
		


			
end dc_filter;     
            


architecture synth of dc_filter is

    signal alpha : std_logic_vector(1 downto 0);

begin  
    
	 alpha <= alpha;


   
end synth;








