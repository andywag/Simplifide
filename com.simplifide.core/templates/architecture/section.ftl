<#import "main_module.ftl" as main_module/>
<#import "interface.ftl" as interface/>

<#macro intro module index>
	<#if module.module.entity.doc?exists>${module.module.entity.doc.description}</#if>
</#macro>

<#macro inter module index>
	<h${index}>Interface</h${index}>
	<@interface.table entity=module.module.entity/>
</#macro>

<#macro detail module index>
	<#--<h${index}>Hierarchy</h${index}>-->
</#macro>

<#macro dft module index>
	<h${index}>DFT</h${index}>
</#macro>

<#macro dft module index>
	<h${index}>Verification</h${index}>
</#macro>

<#macro dft module index>
	<h${index}>Synthesis</h${index}>
</#macro>
