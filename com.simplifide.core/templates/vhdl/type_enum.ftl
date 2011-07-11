<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>

<#macro completion_head_dec object refer>
    Enumeration<#t>
</#macro>

<#macro completion_doc object refer>
	<#if object.doc?exists>
		<@hdl_doc.doc_description doc = object.doc/>
	</#if>
	<@doc_portlist portlist=object.realSelfList type="param"/>
</#macro>

<#macro doc_portlist portlist type> 

	<@common.keyword>${type}</@common.keyword>
	<#list portlist as portobject> 
		&nbsp;&nbsp;&nbsp;&nbsp;<#t>
		<@common.object>${portobject.name}</@common.object><#t>
		<#if portobject.doc?exists>
			<@hdl_doc.doc_description doc = portobject.doc/>
		<#else>
			<i>undocumented</i>
		</#if>     
	</#list>
</#macro>

<#macro type_detail type>
	<font size="+1"><b>${type.name}</b></font>
	<#--<p><@type_detail_dec type=type/> </p>-->
	<@util.doc object=type/>
	<hr/>
</#macro>

<#macro type_detail_dec type>
	type <b>${type.name}</b> is array ${type.range} of ${type.type}
</#macro>