<#-- Completion Html Items -->

<#import "../../completion/hdl_doc.ftl" as hdl_doc/>

<#macro port_list object type>
	<#if object?size != 0>
		${type} (<#rt>
		<#list object as port>
		<#assign val> : ${port.getVhdlIOString()} ${port.type}<#if port_has_next>;</#if></#assign>
		   <#lt>${port.name?right_pad(16)}${val?right_pad(28)}<@doc object=port/>
		   </#list>);</#if>
</#macro>

<#macro generic_list object type>
	<#if object?size != 0>
		${type} (<#rt>
		<#list object as port>
		<#assign val> : ${port.getVhdlIOString()} ${port.type}</#assign>
			<#lt>${port.name?right_pad(16)}${val}<#if port.initial?exists> := ${port.initial}</#if><#if port_has_next>;</#if><@doc object=port/>
		    </#list>);
	</#if>
</#macro>


<#macro doc object><#if object.doc?exists> -- <@hdl_doc.doc_description doc=object.doc/></#if></#macro>