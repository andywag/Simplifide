
<#macro hyperlink text location>
	<a href ="${location}">${text}</a><#t>
</#macro>




<#macro doc object>
	<#if object.doc?exists>
		<#if object.doc.description?exists>
			${object.doc.description}
		<#else>
			<i>undocumented</i>
		</#if>
	</#if>
</#macro>

<#macro table_def title cols>
	<table width="100%" border="1" bordercolor="black" summary="">
		<tr valign="top">
			<td BGCOLOR="#CCCCFF" colspan="${cols}"><FONT size="+2"><b>${title}</b></font></td>
		</tr>
		<tr valign="top">
			<#nested>
		</tr>
	</table>
</#macro>