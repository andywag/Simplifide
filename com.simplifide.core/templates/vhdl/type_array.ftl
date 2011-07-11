<#import "../common.ftl" as common/>

<#macro completion_head object refer>
    <@common.keyword>type</@common.keyword> <b>${object.name}</b> is array <@range object=object/> of <@common.type>${object.typeReference}</@common.type>
</#macro>

<#macro completion_head_dec object refer>

</#macro>

<#macro range object>
    <#if object.rangeReference?exists>
        <a href="rangeReference">${object.rangeReference}</a><#t>
    <#else>
        (NATURAL range)<#t>
    </#if>
</#macro>

<#macro type_detail type>
	
	<p><@type_detail_dec type=type/> </p>
	<@util.doc object=type/>
	<hr/>
</#macro>

<#macro type_detail_dec type>
	type <b>${type.name}</b> is array ${type.range} of ${type.type}
</#macro>