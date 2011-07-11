
<#import "section.ftl" as section/>

<#macro module object index>
	
	<@section.intro module=object index=index/>
	<@section.inter module=object index=index/>
	<@section.detail module=object index=index/>
	
	<#list object.getRealSelfList() as mod>
		<h${index}>${mod.name}</h${index}>
		<@module object = mod index = index+1/>
	</#list>
</#macro>

