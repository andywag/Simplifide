<#import "../common.ftl" as common/>
<#import "port.ftl" as port/>
<#import "../hdl_doc.ftl" as hdl_doc/>

<#macro completion_head object refer>
    <@common.keyword>function</@common.keyword> <@common.object>${object.name}</@common.object><#t> 
    (<@portlist ports=object.ports/>)<#t>
    <#if object.outputType?exists>
    	return <@common.type>${object.outputType}</@common.type>
    </#if><#t>
</#macro>

<#macro completion_doc object refer>
	<#if object.doc?exists><@hdl_doc.doc_description doc = object.doc/></#if>
	<@doc_portlist portlist=object.ports type="parameters"/>
</#macro>

<#macro portlist ports>
	<#list ports as port> 
		<@common.type>${port.typeReference}</@common.type> <@common.object>${port}</@common.object><#t>
		<#if port_has_next>,</#if><#t>
	</#list>
</#macro>

<#macro doc_portlist portlist type> 

	<@common.keyword>${type}</@common.keyword>
	<#list portlist as portobject> 
		<@port.entity_port object=portobject/>     
	</#list>
</#macro>