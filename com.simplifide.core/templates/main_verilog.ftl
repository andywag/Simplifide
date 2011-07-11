<#import "verilog/completion.ftl" as completion/>
<#import "verilog/moduleobject.ftl" as moduleobject/>
<#import "verilog/variable.ftl" as variable/>
<#import "verilog/component.ftl" as component/>
<#import "verilog/port.ftl" as port/>
<#import "verilog/function.ftl" as function/>
<#import "verilog/type_sub.ftl" as type_sub/>
<#import "verilog/type_struct.ftl" as type_struct/>
<#import "verilog/type_array.ftl" as type_array/>
<#import "verilog/type_enum.ftl" as type_enum/>
<#import "verilog/entity.ftl" as entity/>
<#import "verilog/package.ftl" as package/>
<#import "verilog/interface.ftl" as interface/>
<#import "verilog/class.ftl" as class/>
<#--<#import "verilog/program.ftl" as program/>-->

<#-- MAIN HTML BODY FOR COMPLETION -->
<#assign refer = .namespace[name]/>
<#if type==0> 
    <#if refer.complete_html?exists>
        <@refer.complete_html object = object refer=refer/>
    <#else>
        <@completion.complete_html object = object refer=refer/>
    </#if>
</#if>