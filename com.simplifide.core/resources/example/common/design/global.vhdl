--------------------------------------------------------------------------------
--  Module  : global.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 7:35 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

package global is
   
--* 10 Bit Complex Number
--* @param real Real Component
--* @param imag Imaginary Component   
type ComplexSign10 is record
    real : std_logic_vector(9 downto 0);
    imag : std_logic_vector(9 downto 0);
end record;

--* 16 Bit Complex Number
--* @param real Real Component
--* @param imag Imaginary Component
type ComplexSign16 is record
       real : std_logic_vector(15 downto 0);
       imag : std_logic_vector(15 downto 0);
end record;
   
type dc_filter_array is array(7 downto 0) of ComplexSign10;
type rx_filter_array is array(20 downto 0) of ComplexSign10;

--* State used by the global control state machine
--* @param OFF   Modem is in powered down state
--* @param IDLE  Modem is waiting for some event to trigger an action
--* @param TX    Modem is transmitting data
--* @param RX    Modem is receiving data
type main_state is (OFF,IDLE,TX,RX);



end package global;  



package body global is

 
end package body;
