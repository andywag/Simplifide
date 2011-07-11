<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "../hdldoc.ftl" as hdldoc/>

<#macro completion_head object refer>
    <@common.keyword>record</@common.keyword> <b>${object.name}</b> 
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
	<#--<p><@type_detail_dec type=type/> </p>--> 
	<@hdldoc.doc_detailed object=type/>
	
	  
	<hr/>
</#macro>
