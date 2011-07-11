
<#macro doc_detailed object>
	<#if object.doc?exists>
		<@doc_description_detailed doc=object.doc/>	
		<@doc_parameters doc=object.doc/>
	</#if>
</#macro>

<#macro doc_description_detailed doc>
	<#if doc.description?exists>
		<p>${doc.description}</p>
	</#if>
</#macro>

<#macro doc_short doc>
	<#if doc.shortDescription?exists>&nbsp;&nbsp;&nbsp;&nbsp;${doc.shortDescription}</#if>
</#macro>

<#macro doc_parameters doc>
	<#if doc.paramList?exists>
		<dl>
			<dt><b>parameters</b>
			<#list doc.paramList as param>
				<@param_list param = param/>				
			</#list>
			</dt>
		</dl>
	</#if>
</#macro>

<#macro param_list param>
	<dd><code>${param.index}</code> -  ${param.value}</dd> 
</#macro>