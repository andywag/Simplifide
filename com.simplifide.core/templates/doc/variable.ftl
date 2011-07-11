<#import "util.ftl" as util/>


<#macro variable_table vars>
	<@util.table_def title="Signal Summary" cols="2">
			<#list vars as var>
				<@variable_row var=var/>
			</#list>
	</@util.table_def>
</#macro>

<#macro variable_row var>
	<td width="25%" BGCOLOR="#EEEEFF"><@vartype var=var/></td>
	<td width="75%" BGCOLOR="#EEEEFF"><@varname var=var/></td>
</#macro>

<#macro vartype var>
	<#if var.typeVar?exists><i>${var.typeVar} </i> </#if>
</#macro>

<#macro varname var>
	${var.name}
</#macro>

<#macro variable_list vars>
	<h2>Signals</h2>
	<ul>
	<#list vars as var>
		<li><@var_declaration var=var/></li>
	</#list> 
	</ul>
</#macro>

<#macro var_declaration var>
	${var.name} : <#if var.typeVar?exists><i>${var.typeVar} </i> </#if><#t>
	  
</#macro>