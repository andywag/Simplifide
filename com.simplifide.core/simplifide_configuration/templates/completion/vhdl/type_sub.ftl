<#import "../common.ftl" as common/>

<#macro completion_head object refer>
    <@common.keyword>subtype</@common.keyword> <@common.object>${object.name}</@common.object> is <@common.type>${object.typeReference.name}</@common.type>
</#macro>

<#macro type_detail type>
	<font size="+1"><b>${type.name}</b></font>
	<#--<p><@type_detail_dec type=type/> </p>-->
	<@util.doc object=type/>
	<hr/>
</#macro>

<#macro type_detail_dec type>
	type <b>${type.name}</b> is array ${type.range} of ${type.type}
</#macro>

