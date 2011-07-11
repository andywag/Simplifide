
<#macro page><html><#nested/></html></#macro>

<#macro head title>
	<head>
		<title>${title}</title>
		<#nested/>
	</head>
</#macro>

<#macro body><body><#nested> </body> </#macro>



