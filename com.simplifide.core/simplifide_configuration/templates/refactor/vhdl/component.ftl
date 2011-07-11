<#-- Completion Html Items -->

<#import "port.ftl" as port/>
component ${object.name} is  
	<@port.generic_list object = object.generics type="generic"/>
	<@port.port_list object = object.ports type = "port"/>
end component;<#t>