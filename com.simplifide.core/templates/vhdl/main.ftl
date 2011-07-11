<#import "completion.ftl" as completion/>
<#import "moduleobject.ftl" as moduleobject/>
<#import "variable.ftl" as variable/>
<#import "component.ftl" as component/>
<#import "port.ftl" as port/>
<#import "function.ftl" as function/>
<#--<#import "type.ftl" as type/>-->
<#import "type_sub.ftl" as type_sub/>
<#import "type_struct.ftl" as type_struct/>
<#import "type_array.ftl" as type_array/>
<#import "type_enum.ftl" as type_enum/>
<#import "entity.ftl" as entity/>
<#import "package.ftl" as package/>

<#-- MAIN HTML BODY FOR COMPLETION -->
<#assign refer = .namespace[name]/>
<#if type==0> 
    <#if refer.complete_html?exists>
        <@refer.complete_html object = object refer=refer/>
    <#else>
        <@completion.complete_html object = object refer=refer/>
    </#if>
</#if>