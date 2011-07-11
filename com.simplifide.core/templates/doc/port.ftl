 <#import "../hdldoc.ftl" as hdldoc/>


<#macro port_table type ports>
	<@util.table_def title="${type} Summary" cols="3">
		<#list ports as port>
			<#if port.ioString = "input">
			<tr>
				<@port_row port=port/>
			</tr>
			</#if>
		</#list>
		<#list ports as port>
			<#if port.ioString != "input">
			<tr>
				<@port_row port=port/>
			</tr>
			</#if>
		</#list>
	</@util.table_def>
</#macro>



<#macro port_row port>
	<td width="10%" BGCOLOR="#EEEEFF"><@port_iotype port=port/></td>
	<td width="25%" BGCOLOR="#EEEEFF"><@port_type   var=port.localVar/></td>
	<td width="65%" BGCOLOR="#EEEEFF"><@port_name   port=port/></td>
</#macro>

<#macro port_type var>
	<#if var.typeVar?exists><i>${var.typeVar} </i> </#if>	
</#macro>

<#macro port_iotype port>
	${port.ioString}
</#macro>

<#macro port_name port>
		<a href=".."> ${port.name}</a></br>
		<#if port.doc?exists>
			<@hdldoc.doc_short doc=port.doc/>
		</#if>
</#macro>



<#macro port_list type ports>
	<#if ports?exists>
		<h2>${type}</h2>
		<ul>
			<#list ports as port>
				<li><@port_declaration var=port.localVar/></li>
			</#list>
		</ul>
	</#if>
</#macro>

<#macro port_declaration var>
	${var.name} : <#if var.typeVar?exists><i>${var.typeVar} </i> </#if><#t>
	  
</#macro>