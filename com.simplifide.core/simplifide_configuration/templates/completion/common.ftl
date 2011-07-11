
<#macro ind value>[[[[${value}]]]]</#macro>

<#macro template_default object default>
    <#assign tvar=object+" default="+default?replace("\"","&&&&&")/>
    <@template>${tvar}</@template><#t>
</#macro>

<#macro html_head_style><font size=4><#nested><br/><br/></font></#macro>

<#-- 
	<#macro type><b><font color="#005500"><#nested></font></b></#macro>
	<#macro keyword><b><font color="#000099"><#nested></font></b></#macro>
	<#macro object><b><#nested></b></#macro>
	<#macro definedoc><i><font color="#990099"><#nested></font></i></#macro>
-->

<#macro type><type><#nested></type></#macro>
<#macro keyword><key><#nested></key></#macro>
<#macro object><object><#nested></object></#macro>
<#macro definedoc><define><#nested></define></#macro>
