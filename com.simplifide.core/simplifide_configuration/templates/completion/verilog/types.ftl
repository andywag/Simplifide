
<#macro variable_dec type>
	<#if type.range?exists>
		[${type.range.topValue} : ${type.range.bottomValue}] <#t> 
	</#if>
</#macro>