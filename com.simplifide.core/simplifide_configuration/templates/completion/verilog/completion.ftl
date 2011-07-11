<#import "../common.ftl" as common/>
<#import "../hdl_doc.ftl" as hdl_doc/>

<#-- Completion Html Items -->
<#macro complete_html object refer>
    <@completion_head object=object refer=refer/>
    <@completion_doc  object=object refer=refer/>
    <#--><@completion_body  object=object refer=refer/>-->
   
</#macro>

<#-- Completion Head Generally refers to 
	 ${completion_head_dec} ${object.name}
	 ie (package "name")
	 -->
<#macro completion_head object refer>
    <#if refer.completion_head?exists>
        <@refer.completion_head object=object refer=refer/><#t>
    <#else>
       	<@completion_head_dec object=object refer=refer/>
    </#if>
</#macro>

<#macro completion_head_dec object refer>
    <#if refer.completion_head_dec?exists>
        <@common.keyword><@refer.completion_head_dec object=object refer=refer/></@common.keyword> <b>${object.name}</b>
    <#else>
        <b>${object.name}</b><#t>
    </#if>
</#macro>

<#macro completion_doc object refer>
    <#if refer.completion_doc?exists>
        <@refer.completion_doc object=object refer=refer/>
    <#elseif object.doc?exists>
    	<@hdl_doc.translate doc = object.doc/>    		
    </#if>
</#macro>

