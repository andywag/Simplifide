<#-- Completion Html Items -->


${object.name} : ${object.connectName}  
	<@port_map portlist = object.generics type="generic" es=")"/>     
	<@port_map portlist = object.ports type = "port   " es =");"/>      

<#macro generic_map portlist>

</#macro>

<#macro port_map portlist type es>
	<#if portlist?size != 0>
		${type} map (<#list portlist as port>${port.name?right_pad(24)} => ${port.externVar.getCompositeName()}<#if port_has_next>,
		             </#if></#list>${es}
	</#if>
</#macro>