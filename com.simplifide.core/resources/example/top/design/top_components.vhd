
library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;
use common.registers.all;


package top_components is
   
-- The Following Script Creates The Components for All of the Entities in the Project  
-- Using right click and Expand Templates will create the code

--
-- python
-- import Template.Util.General as General
-- General.createComponents(writer)
-- AUTO GENERATED (DO NOT EDIT MANUALLY)

component fft is
port    (clk                              : in       std_logic;
         enable                           : in       std_logic;
         reset                            : in       std_logic;
         rxFftInput                       : in       ComplexSign16;
         txFftInput                       : in       ComplexSign16;
         fftOutput                        : out      ComplexSign16);

end component;

component modem_top is
port    (adcIn                            : in       ComplexSign16;
         clk                              : in       std_logic;
         procRd                           : in       std_logic;
         procWr                           : in       std_logic;
         procWrBus                        : in       std_logic_vector (31 downto 0);
         reset                            : in       std_logic;
         byteOut                          : out      std_logic_vector (7 downto 0);
         outVld                           : out      std_logic;
         procRdBus                        : out      std_logic_vector (31 downto 0));

end component;

component processor_int is
port    (clk                              : in       std_logic;
         enable                           : in       std_logic;
         procRd                           : in       std_logic;
         procWr                           : in       std_logic;
         procWrBus                        : in       std_logic_vector (31 downto 0);
         regs2rx_top                      : in       regs2rx_top_record;
         reset                            : in       std_logic;
         procRdBus                        : out      std_logic_vector (31 downto 0));

end component;


-- END AUTOGENERATION


end package top_components;  
 







