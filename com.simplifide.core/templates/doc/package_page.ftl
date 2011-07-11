<#import "basepage.ftl" as basepage/>
<#import "util.ftl" as util/>
<#import "package.ftl" as entity/>
<#import "types.ftl" as types/>   
  
  
<@basepage.page> 
	<@basepage.head title="{object.name}"> </@basepage.head>
	<@basepage.body> 
		<h1>Package ${object.name}</h1>
		<p><@util.doc object=object/></p>
		<hr/>
		<#if object.packageModule?exists>
			<#assign module = object.packageModule/>
			<@summary module=module/>
			<hr/>
			<@detail  module=module/>
		</#if>
	</@basepage.body>
 	
</@basepage.page>

<#macro summary module>
	<@types.type_table types = module.types/>
	
</#macro>

<#macro detail module>
	<@types.type_detail types = module.types/>
</#macro>