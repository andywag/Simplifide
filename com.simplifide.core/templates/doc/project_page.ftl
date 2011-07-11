<#import "basepage.ftl" as basepage/>
<#import "project.ftl" as project/>
   
<@basepage.page> 
	<@basepage.head title="Project Index"> </@basepage.head>
	<@basepage.body> 
		<h1>Project ${object.name}</h1>
		<hr/>
		<@project.package_list object=object/>
		<@project.entity_list object=object/>
	</@basepage.body>
 	
</@basepage.page>