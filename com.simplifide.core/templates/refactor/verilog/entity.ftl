<#-- Completion Html Items -->

<#import "port.ftl" as port/>



module ${object.name}   
	<@port.generic_list object = object.generics />
	<@port.port_list object = object.ports />
    
<#macro ent object>
module ${object.name}   
	<@port.generic_list object = object.generics />
	<@port.port_list object = object.ports />
</#macro>