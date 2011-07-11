<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>

<#macro completion_head object refer>
    <@common.keyword><@iotype object=object/></@common.keyword><#t>
    <@common.object>${object}</@common.object> : <@common.type>${object.typeReference}</@common.type><#t>
    <@completion_default object=object/>
</#macro>

<#macro completion_default object>	
		<#if object.default?exists>
			:= ${object.default}<#t>
		</#if>
	
</#macro>

<#macro dectype object>
    <#if object.generic>
        generic<#t>
    <#else>
        port<#t>
    </#if>
</#macro>

<#macro entity_port object>
	&nbsp;&nbsp;&nbsp;&nbsp;<@common.keyword><@iotype object=object/></@common.keyword> <@common.object>${object}  </@common.object><#t>
	<#if object.doc?exists>
		<@hdl_doc.doc_description doc=object.doc/>
		
	<#else>
		<i>undocumented</i>
	</#if>
</#macro>



<#macro iotype object>
    <#assign var=object.localVarReference.object/>
    <#if var.opTypeVar?exists>
        <#assign type=var.opTypeVar.type/>
    <#else>
        <#assign type=0>
    </#if>
    <#if type == 0>input <#t>
    <#elseif type == 1>input <#t>
    <#elseif type == 2>inout <#t>
    <#elseif type == 3>output <#t>
    <#elseif type == 4>buffer <#t>
    </#if>
</#macro>