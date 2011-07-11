<#-- Completion Html Items -->
<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "port.ftl" as port/>
 <#import "variable.ftl" as variable/>
 
<#macro completion_head_dec object refer>
    interface<#t>
</#macro>

<#macro completion_doc object refer>
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
		<@doc_signals signals = object.getAllSignals()/>
	</#if>
</#macro>

<#macro doc_portlist portlist type> 
	<#list portlist as portobject> 
		<#if portobject_index == 0>
		<@common.keyword>${type}</@common.keyword>
		</#if>
		<@port.entity_port object=portobject/>     
	</#list>
</#macro> 

<#macro doc_signals signals> 
	<#list signals as signal> <#t>
		<#if signal_index == 0><@common.keyword>Signals</@common.keyword></#if>
		&nbsp;&nbsp;&nbsp;&nbsp;<@variable.completion_interface object=signal refer=""/><#t></#list>
</#macro>


