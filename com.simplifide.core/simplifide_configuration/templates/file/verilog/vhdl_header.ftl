
<#macro header object>
-- @module : ${object.name}
-- @author : ${object.user}
-- @date   : ${object.date}
<#list object.comment as c>-- ${c}</#list>
</#macro>