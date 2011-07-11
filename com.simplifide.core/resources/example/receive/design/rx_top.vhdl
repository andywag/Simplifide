--------------------------------------------------------------------------------
--  Module  : rx_top.vhdl
--  Author  : Andy
--  Created : April 17, 2007, 11:29 PM
--------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library common;
use common.global.all;
use common.registers.all;

library receive;
use receive.rx_components.all;

--* Top level for the receive block. This is just a connector module which contains the sublocks for the design
--* @port clk Main clock for the design
--* @port reset Main <i>asynchronous</i> for the design
--* @port enable Main enable for the design
--* @port regs2rx_top Programmable Register Input 
--* @port adcIn Output from the A/D converter
--* @port fftOutput Output from the fft block
--* @port fftInput Output from the receive filter to the fft block
--* @port outVld Valid signal delineating the correct byte boundaries
--* @port byteOut Output bytes from the receiver
entity rx_top is 
port (clk : std_logic;
      reset : std_logic;
      enable : std_logic;
      regs2rx_top : regs2rx_top_record;
      adcIn  : ComplexSign16;
      fftOutput : ComplexSign16;
      
      fftInput : out ComplexSign16;
      outVld   : out std_logic;
      byteOut  : out std_logic_vector(7 downto 0));
    
end rx_top;     
            


architecture synth of rx_top is

    --* Dc Filter Output
    signal dcFilterOut : ComplexSign16;
    --* Down Converter Output
    signal downOut     : ComplexSign16;
    --* Rx Filter Output 
    signal rxFilterOut : ComplexSign16;
    --* Equalizer Output
    signal equalizerOutput : ComplexSign16;
    
begin   


    fftInput <= rxFilterOut;

    idc_filter : dc_filter 
         port map(clk                    =>     clk,
                  din                    =>     adcIn,
                  dout                   =>     dcFilterOut,
                  enable                 =>     enable,
                  regs2dcfilt            =>     regs2rx_top.dcfilt,
                  reset                  =>     reset);
                  
    idownconverter : downconverter 
         port map(clk                    =>     clk,
                  din                    =>     dcFilterOut,
                  dout                   =>     downOut,
                  enable                 =>     enable,
                  regs2down              =>     regs2rx_top.down,
                  reset                  =>     reset);
                  
   irx_filter : rx_filter 
         port map(clk                    =>     clk,
                  din                    =>     downOut,
                  dout                   =>     rxFilterOut,
                  enable                 =>     enable,
                  regs2rxfilt            =>     regs2rx_top.filter,
                  reset                  =>     reset);

   iequalizer : equalizer 
         port map(clk                    =>     clk,
                  din                    =>     fftOutput,
                  dout                   =>     equalizerOutput,
                  enable                 =>     enable,
                  reset                  =>     reset);

   iviterbi : viterbi 
         port map(clk                    =>     clk,
                  din                    =>     equalizerOutput,
                  dout                   =>     byteOut,
                  enable                 =>     enable,
                  outVld                 =>     outVld,
                  reset                  =>     reset);





 

   
end synth;








