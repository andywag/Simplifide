<#-- Completion Html Items -->

<#import "port.ftl" as port/>
entity ${object.name} is  
	<@port.generic_list object = object.generics type="generic"/>
	<@port.port_list object = object.ports type = "port"/>
end ${object.name};<#t> 
<#macro ent object>
entity ${object.name} is  
	<@port.generic_list object = object.generics type="generic"/>
	<@port.port_list object = object.ports type = "port"/>
end ${object.name}; </#macro>