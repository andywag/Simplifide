<#import "../common.ftl" as common/>
<#import "types.ftl" as types/>

<#macro completion_head object refer>
    <@completion_head_dec object=object/> <@types.variable_dec type=object.typeReference.object/><#t>
    <@common.object>${object.name}</@common.object>  <@completion_default object=object/><#t>
</#macro>


<#macro completion_head_dec object>
	<@common.keyword>
	<#if object.searchType = 9100>constant<#t>
	<#elseif object.searchType = 9200>wire<#t>
	<#elseif object.searchType = 9300>reg<#t>
	<#else>reg<#t>
	</#if>
    </@common.keyword><#t>
</#macro>

<#macro completion_default object>
	<#if object.searchType = 9100>
		<#if object.default?exists>
			:= ${object.default}
		</#if>
	</#if>
</#macro>