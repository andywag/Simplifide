<#import "../common.ftl" as common/>

<#macro completion_head object refer>
    <@completion_head_dec object=object/> <@common.object>${object.name}</@common.object> : <@common.type>${object.typeReference}</@common.type><#t>
	<@completion_default object=object/><#t>
</#macro>

<#macro completion_head_dec object>
	<@common.keyword>
	<#if object.searchType = 9100>constant<#t>
	<#elseif object.searchType = 9200>variable<#t>
	<#elseif object.searchType = 9300>signal<#t>
	<#else>variable<#t>
	</#if>
    </@common.keyword><#t>
</#macro>

<#macro completion_default object>
	<#if object.searchType = 9100>
		<#if object.default?exists>
			:= ${object.default}<#t>
		</#if>
	</#if>
	
</#macro>