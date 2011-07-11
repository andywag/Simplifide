<#-- Completion Html Items -->
<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>
<#import "port.ftl" as port/>
 
<#macro completion_head_dec object refer>modport </#macro>

<#macro completion_doc object refer>
	<#if object.doc?exists>
		<@hdl_doc.doc_description doc = object.doc/>
	</#if>
	<#if object.portsR?exists>
		<@doc_portlist portlist = object.portsR.getObject().getPorts() type="Ports"/>
	</#if>
</#macro>

<#macro doc_portlist portlist type> 
	
	<#list portlist as portobject> 
		<#if portobject_index == 0>
		<@common.keyword>${type}</@common.keyword>
		</#if>
		<@port.entity_port object=portobject/>     
	</#list>
</#macro>
