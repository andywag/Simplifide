

<#macro hyper reference>
	<#if reference.location?exists>
		<@hyp proj=reference.location.projectIndex file=reference.location.modIndex>${reference.name}</@>	
	<#else>
		${reference.name}
	</#if>
	
</#macro>