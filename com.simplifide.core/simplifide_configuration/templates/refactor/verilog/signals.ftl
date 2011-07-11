
<#list object as signal>
<@dec object = signal/>
</#list>

<#macro dec object>
<@type object=object/>  ${object.name}; 
</#macro>

<#macro type object><#if object.isInput()>reg <#else>wire </#if><@range object = object/></#macro>
<#macro range object><#if object.width != 0> [${object.width-1}:0] </#if></#macro>