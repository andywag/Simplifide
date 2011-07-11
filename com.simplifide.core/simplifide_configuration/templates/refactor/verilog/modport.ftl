<#-- Completion Html Items -->


modport ${object.name} 
	<@port_map portlist = object.ports/> 
	
<#macro generic_map portlist>
	<#if portlist?size != 0>
#(<#list portlist as port>.${port.name?right_pad(24)}(${port.externVar.getCompositeName()})<#if port_has_next>,
		</#if></#list>)<#rt>
	</#if>
</#macro>

<#macro port_map portlist>
	<#if portlist?size != 0>
		(<#rt>
		 <#lt><#list portlist as port>${port.getVerilogIOString()?right_pad(24)}${port.externVar.getCompositeName()}<#if port_has_next>,
		</#if></#list>);
	</#if>
</#macro>