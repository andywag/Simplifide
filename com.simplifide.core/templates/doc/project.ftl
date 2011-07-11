<#import "util.ftl" as util>


<#macro package_list object>
	<h2>Packages</h2>	
	<ul>
		<#list object.packageList as package>
			<li><@util.hyperlink location="${package.name}.html" text="${package.name}"/></li>
		</#list>
	</ul>
</#macro>



<#macro entity_list object>
	<h2>Entities</h2>
	<ul>
		<#list object.moduleList as module>
			<li><@util.hyperlink location="${module.name}.html" text="${module.name}"/></li>
		</#list>
	</ul>
</#macro>

