<#import "../common.ftl" as common/>

<#macro completion_head object refer>
    <@common.keyword>function</@common.keyword> <@common.object>${object.name}</@common.object><#t> 
    (<@portlist ports=object.ports/>)<#t>
    return <@common.type>${object.outputType}</@common.type>
</#macro>

<#macro portlist ports>
	<#list ports as port> 
		<@common.type>${port.typeReference}</@common.type> <@common.object>${port}</@common.object><#t>
		<#if port_has_next>,</#if><#t>
	</#list>
</#macro>

