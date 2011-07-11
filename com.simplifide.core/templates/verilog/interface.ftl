<#-- Completion Html Items -->
<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "port.ftl" as port/>
 
<#macro completion_head_dec object refer>
    <@common.keyword>interface</@common.keyword><#t>
</#macro>

<#macro completion_doc object refer>
	<@completion_doc_internal object=object.getEntity() refer=refer/>
</#macro>

<#macro completion_doc_internal object refer>
	<#if object.doc?exists>
		<@hdl_doc.doc_description doc = object.doc/>
	</#if>
	<#if object.connectRef?exists>
		<#if object.genericList?exists>
			<@doc_portlist portlist = object.genericList type="generics"/>
		</#if>
		<#if object.portList?exists>
			<@doc_portlist portlist = object.portList type="ports"/>
		</#if>
	</#if>
</#macro>

<#macro doc_portlist portlist type> 

	<@common.keyword>${type}</@common.keyword>
	<#list portlist as portobject> 
		<@port.entity_port object=portobject/>     
	</#list>
</#macro>

