
<#macro port_list object>
	<#if object?size != 0>
		 (<#rt>
		<#list object as port>
		   <#lt>${port.getVerilogIOString()?right_pad(10)} ${port.type?right_pad(10)} ${port.name}<#if port_has_next>,
		  </#if></#list>);
	</#if>
</#macro>

<#macro generic_list object>
	<#if object?size != 0>
		#(<#rt>
		<#list object as port>
		  <#lt>${"parameter"?right_pad(11)} ${port.type?right_pad(10)} ${port.name?right_pad(24)}<#if port.hasInitialValue()> = ${port.initial}</#if><#if port_has_next>,
		  </#if></#list>)
	</#if>
</#macro>