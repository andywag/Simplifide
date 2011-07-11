<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "types.ftl" as types/>

<#macro completion_head object refer>
    <@common.keyword><@iotype object=object/></@common.keyword> <@types.variable_dec type=object.typeReference.object/> <@common.object>${object}</@common.object>  
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
    <#assign var=object.localVarReference.object/>
    <#if var.opTypeVar?exists>
    	<#if var.opTypeVar.type?exists>
        	<#assign type=var.opTypeVar.type/>
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