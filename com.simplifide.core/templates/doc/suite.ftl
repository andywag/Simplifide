
<#import "util.ftl" as util>

<#macro project_list object>
	<h2>Projects</h2>	
	<ul>
		<#list object.projectList as project>
			<li><@util.hyperlink location="../projects/${project.name}/doc/project_index.html" text="${project.name}"/></li>
		</#list>
	</ul>
</#macro>



<#macro library_list object>
	<h2>Libraries</h2>
	<ul>
		<#list object.libraryList as project>
			<li><@util.hyperlink location="../libraries/${project.name}/doc/project_index.html" text="${project.name}"/></li>
		</#list>
	</ul>
</#macro>

<#macro hierarchy object> 
	<h2>Chip Hierarchy</h2>
	 
</#macro> 