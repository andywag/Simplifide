<#import "entity.ftl" as entity/>

<#macro completion_head_dec object refer>
    component<#t>
</#macro>

<#macro completion_doc object refer>
	<@entity.completion_doc object=object refer=refer/>
</#macro>