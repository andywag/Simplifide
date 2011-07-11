<#import "entity.ftl" as entity>

${HEADER}

library ieee;
use ieee.std_logic_1164.all;

<@entity.ent object=object/>
        

architecture synth of ${object.name} is
               
begin  

end synth;








