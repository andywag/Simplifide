<#-- Completion Html Items -->
 
<#if object.head>
	<@head object=object/>
<#else>
	<@body object=object/>
</#if>

<#macro portlist object>
	()
</#macro>



<#macro returns object>
	<#if object.getReturnString()?exists/>
	 returns ${object.getReturnString()}
	</#if>
</#macro>

<#macro head object>
    ${object.getVhdlFunctionType()} ${object.name} <@portlist object=object/> <@returns object = object/>
</#macro>

<#macro body object>

</#macro>