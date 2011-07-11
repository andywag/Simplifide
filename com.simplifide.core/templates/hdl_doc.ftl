<#import "common.ftl" as common/>

<#macro translate doc>
	<@doc_description doc = doc/>
	<#if doc.genericList?exists><@doc_genericlist genericlist = doc.genericList/></#if>
	<#if doc.portList?exists><@doc_portlist portlist = doc.portList/></#if>
</#macro>

<#macro doc_description doc>
	<#if doc.description?exists>${doc.description}</#if>	
</#macro>

<#macro doc_portlist portlist> 

	<@common.keyword>ports</@common.keyword>
	<#list portlist as port> 
		&nbsp&nbsp&nbsp&nbsp<b>${port.index}</b>      ${port.value}
	</#list>
</#macro>

<#macro doc_genericlist portlist> 

	<@common.keyword>generics</@common.keyword>
	<#list portlist as port> 
		&nbsp&nbsp&nbsp&nbsp<b>${port.index}</b>      ${port.value}
	</#list>
</#macro>