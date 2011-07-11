
<#list object as signal>
<@dec object = signal/>
</#list>

<#macro dec object>
signal ${object.name?right_pad(24)} : ${object.type} <@ass object=object/>; 
</#macro>

<#macro ass object><#if object.defaultValue?exists> := object.defaultString></#if><#lt></#macro>

