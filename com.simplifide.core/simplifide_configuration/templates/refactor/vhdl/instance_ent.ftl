<#-- Completion Html Items -->

<#import "../../completion/hdl_doc.ftl" as hdl_doc/>
XXXX${object.name}YYYY : entity XXXXworkYYYY.${object.connectName} 
	<@port_map portlist = object.generics type="generic" es=")
"/>     
	<@port_map portlist = object.ports type = "port   " es =");"/><#t>      
<#macro generic_map portlist></#macro>
<#macro port_map portlist type es> 
	<#if portlist?size != 0>
		${type} map (<#list portlist as port>
		<#assign val>XXXX${port.externVar.getCompositeName()}YYYY<#if port_has_next>,<#else>${es}</#if></#assign>
		${port.name?right_pad(16)} => ${val?right_pad(16)}<@doc object=port/></#list><#rt>
	</#if></#macro>

<#macro doc object><#if object.doc?exists> -- <@hdl_doc.doc_description doc=object.doc/></#if></#macro>