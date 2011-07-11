<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "variable.ftl" as variable/>
<#import "types.ftl" as types/>

<#macro completion_head object refer>
	<@variable.completion_head object=object.getLocalVar() refer=refer/>
</#macro>

<#macro dectype object>
    <#if object.generic>
        generic<#t>
    <#else>
        port<#t>
    </#if>
</#macro>

<#macro entity_port object>
	&nbsp;&nbsp;&nbsp;&nbsp;<@common.keyword><@iotype object=object/></@common.keyword> <@common.object>${object}</@common.object><#t>
	<#if object.doc?exists>
		<@hdl_doc.doc_description doc=object.doc/>
	<#else>
		<i>undocumented</i>
	</#if>
</#macro>



<#macro iotype object>
    <#if object.getOperatingVar()?exists>
    	<#assign op=object.getOperatingVar()/>
  		<#if op.type?exists>
        	<#assign type=op.type/>
        </#if>
    <#else>
        <#assign type=0>
    </#if>
    <#if type == 0>input <#t>
    <#elseif type == 1>input <#t>
    <#elseif type == 2>inout<#t>
    <#elseif type == 3>output<#t>
    <#elseif type == 4>buffer<#t>
    </#if>
</#macro>