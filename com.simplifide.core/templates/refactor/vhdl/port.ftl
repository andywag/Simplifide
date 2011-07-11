<#-- Completion Html Items -->

<#macro port_list object type>
	<#if object?size != 0>
		${type} (<#rt>
		<#list object as port>
		   <#lt>${port.name?right_pad(24)} : ${port.getVhdlIOString()} ${port.type}<#if port_has_next>;
		      </#if></#list>);
	</#if>
</#macro>

<#macro generic_list object type>
	<#if object?size != 0>
		${type} (<#rt>
		<#list object as port>
		    <#lt>${port.name?right_pad(24)} : ${port.getVhdlIOString()} ${port.type}<#if port.hasInitialValue()> := ${port.initial}</#if><#if port_has_next>;
		         </#if></#list>);
	</#if>
</#macro>