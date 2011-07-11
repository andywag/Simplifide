<#import "vhdl/completion.ftl" as completion/>
<#import "vhdl/moduleobject.ftl" as moduleobject/>
<#import "vhdl/variable.ftl" as variable/>
<#import "vhdl/component.ftl" as component/>
<#import "vhdl/port.ftl" as port/>
<#import "vhdl/function.ftl" as function/>
<#import "vhdl/type_sub.ftl" as type_sub/>
<#import "vhdl/type_struct.ftl" as type_struct/>
<#import "vhdl/type_array.ftl" as type_array/>
<#import "vhdl/type_enum.ftl" as type_enum/>
<#import "vhdl/entity.ftl" as entity/>
<#import "vhdl/package.ftl" as package/>

<#-- MAIN HTML BODY FOR COMPLETION -->
<#assign refer = .namespace[name]/>
<#if type==0> 
    <#if refer.complete_html?exists>
        <@refer.complete_html object = object refer=refer/>
    <#else>
    	<#if object?exists>
        	<@completion.complete_html object = object refer=refer/>
        </#if>
    </#if>
</#if>