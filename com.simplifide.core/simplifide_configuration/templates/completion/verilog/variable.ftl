<#import "../common.ftl" as common/>
<#import "types.ftl" as types/>
<#import "../hdl_doc.ftl" as hdl_doc/>

<#-- <#macro completion_head object refer>
    <@completion_head_dec object=object/> <@types.variable_dec type=object.typeReference.object/><#t>
    <@common.object>${object.name}</@common.object>  <@completion_default object=object/><#t>
</#macro> -->

<#macro completion_interface object refer>
    <@completion_head_dec object=object/> ${object.name} <@completion_head_type object=object/><#t>
    <#if object.doc?exists>
    	<@hdl_doc.doc_description doc=object.doc/><#t>
	<#else>
		<i> undocumented</i><#t>
	</#if>
</#macro>

<#macro completion_head object refer>
    <@completion_head_dec object=object/> ${object.name} <@completion_head_type object=object/>  <@completion_default object=object/><#t>
</#macro>

<#macro completion_head_dec object>
	<#if object.getTypeString?exists><@common.keyword>${object.getTypeString()}</@common.keyword></#if><#t>
</#macro>

<#macro completion_head_type object> 
	<#if object.type?exists>:<@common.type> ${object.type.getVerilogDisplayName()}</@common.type></#if><#t>
</#macro>

<#macro completion_default object>
	<#if object.default?exists>
		:= ${object.default}
	<#else>
	
	
	</#if>
</#macro>

