--------------------------------------------------------------------------------
--  Module  : modem_top.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 9:40 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;
use common.registers.all;

library receive;
use receive.rx_components.all;
 
library top;
use top.top_components.all;  


--* Top level connection module for the modem
--* @port clk Main Clock for Design
--* @port reset Main <i>asynchronous</i> for Design
--* @port adcIn Analog to Digital Converter Input
--* @port procRd uProcessor Read Strobe
--* @port procWr uProcessor Write Strobe
--* @port procWrBus uProcessor Input Bus
--* @port procRdBus uProcessor Output Bus
--* @port outVld Valid Byte Indicator
--* @port byteOut Output Bytes
entity modem_top is  
port (clk : std_logic;
      reset : std_logic;
      adcIn : ComplexSign16;
      procRd : std_logic;
      procWr : std_logic;
      procWrBus : std_logic_vector(31 downto 0);
      procRdBus : out std_logic_vector(31 downto 0);
      outVld : out std_logic;
      byteOut : out std_logic_vector(7 downto 0));
    
end modem_top;     
            


architecture synth of modem_top is
    
   --* enable used for the design
   signal enable : std_logic;
   --* Register connection to the receive side
   signal regs2rx_top : regs2rx_top_record;
   --* Transmit FFT Input
   signal txFftInput : ComplexSign16;
   --* Receive FFT Input
   signal rxFftInput : ComplexSign16;
  

-- Use of AUTOWIRE will automatically create signals corresponding to the instances
-- assuming that they are not outputs of this block and the input and output of the isntance 
-- have the same name
  
--AUTOWIRE--
-- AUTO GENERATED (DO NOT EDIT MANUALLY)
-- Outputs of ifft
signal fftOutput : ComplexSign16;

-- END AUTOGENERATION
   
begin  

	iprocessor_int : processor_int
		port map(--AUTOPORT--
                    clk                              => clk,
                    enable                           => enable,
                    procRd                           => procRd,
                    procWr                           => procWr,
                    procWrBus                        => procWrBus,
                    regs2rx_top                      => regs2rx_top,
                    reset                            => reset,
                    procRdBus                        => procRdBus
                 
                  );
                  
          
-- The following script will create an instance when (Expand Templates is Selected)        

-- python 
-- import Template.Element.Instance as Instance
-- inst = Instance.Top('receive.rx_components.rx_top','irx_top',{'fftInput' : 'rxFftInput'})
-- writer.writeVhdl(inst)
-- AUTO GENERATED (DO NOT EDIT MANUALLY)

    irx_top : receive.rx_components.rx_top
        port map   (
                    adcIn                            => adcIn,
                    clk                              => clk,
                    enable                           => enable,
                    fftOutput                        => fftOutput,
                    regs2rx_top                      => regs2rx_top,
                    reset                            => reset,
                    byteOut                          => byteOut,
                    fftInput                         => rxFftInput,
                    outVld                           => outVld);
-- END AUTOGENERATION
    
	ifft :fft    
		port    map(--AUTOPORT--
                    clk                              => clk,
                    enable                           => enable,
                    reset                            => reset,
                    rxFftInput                       => rxFftInput,
                    txFftInput                       => txFftInput,
                    fftOutput                        => fftOutput
                   
                 	);             

                  
   
end synth;








