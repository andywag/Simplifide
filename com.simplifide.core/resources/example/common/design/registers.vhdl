--------------------------------------------------------------------------------
--  Module  : registers.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:52 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;

--* Package which contains a set of types for register definition
package registers is
  
--* Type which contains an array of taps for the rx_filter

--* Programmable taps to the dc filter
--* @param taps Set of taps for dc filter
type regs2dcfilt_record is record
    taps : common.global.dc_filter_array;
end record;

--* Programmable Registers to Downconverter block
--* @param frequency Frequency to downconvert
type regs2downconverter_record is record
    frequency : std_logic_vector(31 downto 0);
end record;

--* Programmable Registers to the filter
--* @params taps Rx Filter Taps
type regs2filter_record is record
    taps : rx_filter_array;
end record; 

--* Combined fields for registers to the receiver
--* @param dcfilt Dc filter registers
--* @param down Downconverter registers
--* @param filter Rx Filter registers
type regs2rx_top_record is record
    dcfilt : regs2dcfilt_record;
    down   : regs2downconverter_record;
    filter : regs2filter_record;
end record;

end package registers;  



package body registers is

 
end package body;
