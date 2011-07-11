
<#import "../hdldoc.ftl" as hdldoc/>
<#import "doc_com.ftl" as doc_com/>

<#macro type_table types>
	<@util.table_def title="Types Summary" cols="2">
		<#list types as type>
			<tr>
				<@type_row typeRef=type/>
			</tr>
		</#list>
	</@util.table_def>
</#macro>

<#macro type_row typeRef>
	<#assign type = typeRef.object/>
	<td width="20%" BGCOLOR="#EEEEFF"><@type_type   type=type/></td>
	<td width="80%" BGCOLOR="#EEEEFF">
		<@doc_com.hyper reference=typeRef/><br>
		<#if type.doc?exists>
			<@hdldoc.doc_short doc=type.doc/>
		</#if>
		
	</td>
</#macro>

<#macro type_type type>
	${type.typeString}	
</#macro>

<#macro type_name type>
	${type.name}
</#macro>


<#macro type_detail types>
	<@util.table_def title="Types Detail" cols="1"/>
	<#list types as type>
		 <font size="+1"><b><a name="${type.name}">${type.name}</a></b></font>
		 <#if type.searchType == 8100><@type_array.type_detail type=type/></#if> 
		 <#if type.searchType == 8200><@type_array.type_detail type=type/></#if>
		 <#if type.searchType == 8300><@type_struct.type_detail type=type/></#if>
		 <#if type.searchType == 8400><@type_struct.type_detail type=type/></#if>
		 <#if type.searchType == 8500><@type_sub.type_detail type=type/></#if>
	</#list>
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