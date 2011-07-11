--------------------------------------------------------------------------------
--  Module  : rx_components.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:43 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;
use common.registers.all;
 
package rx_components is 
  
  
-- The following script generates a single component file named rx_top
-- Uncommenting the following code and using right click and Expand Templates will
-- create the code

---- python
---- import Template.Element.Entity as Entity
---- comp = Entity.Component('rx_top')
---- writer.writeVhdl(comp)
---- AUTO GENERATED (DO NOT EDIT MANUALLY)

--component rx_top is
--port    (adcIn                            : in       ComplexSign16;
--         byteOut                          : in       std_logic_vector (7 downto 0);
--         clk                              : in       std_logic;
--         enable                           : in       std_logic;
--         fftOutput                        : in       ComplexSign16;
--         regs2rx_top                      : in       regs2rx_top_record;
--         reset                            : in       std_logic;
--         fftInput                         : out      ComplexSign16;
--         outVld                           : out      std_logic);

--end component;


-- END AUTOGENERATION-- AUTO GENERATED (DO NOT EDIT MANUALLY)

  
-- The Following Script Creates The Components for All of the Entities in the Project  
-- Using right click and Expand Templates will create the code

--
-- python
-- import Template.Util.General as General
-- General.createComponents(writer)
-- AUTO GENERATED (DO NOT EDIT MANUALLY)

component dc_filter is
port    (clk                              : in       std_logic;
         din                              : in       ComplexSign16;
         enable                           : in       std_logic;
         regs2dcfilt                      : in       regs2dcfilt_record;
         reset                            : in       std_logic;
         dout                             : out      ComplexSign16);

end component;

component downconverter is
port    (clk                              : in       std_logic;
         din                              : in       ComplexSign16;
         enable                           : in       std_logic;
         regs2down                        : in       regs2downconverter_record;
         reset                            : in       std_logic;
         dout                             : out      ComplexSign16);

end component;

component equalizer is
port    (clk                              : in       std_logic;
         din                              : in       ComplexSign16;
         dout                             : in       ComplexSign16;
         enable                           : in       std_logic;
         reset                            : in       std_logic);

end component;

component rx_filter is
port    (clk                              : in       std_logic;
         din                              : in       ComplexSign16;
         enable                           : in       std_logic;
         regs2rxfilt                      : in       regs2filter_record;
         reset                            : in       std_logic;
         dout                             : out      ComplexSign16);

end component;

component rx_top is
port    (adcIn                            : in       ComplexSign16;
         clk                              : in       std_logic;
         enable                           : in       std_logic;
         fftOutput                        : in       ComplexSign16;
         regs2rx_top                      : in       regs2rx_top_record;
         reset                            : in       std_logic;
         byteOut                          : out      std_logic_vector (7 downto 0);
         fftInput                         : out      ComplexSign16;
         outVld                           : out      std_logic);

end component;

component viterbi is
port    (clk                              : in       std_logic;
         din                              : in       ComplexSign16;
         enable                           : in       std_logic;
         reset                            : in       std_logic;
         dout                             : out      std_logic_vector (7 downto 0);
         outVld                           : out      std_logic);

end component;


-- END AUTOGENERATION

end package rx_components;  



package body rx_components is



    
 
end package body;
