
<#import "basepage.ftl" as basepage/>
<#import "util.ftl" as util/>
<#import "entity.ftl" as entity/>
<#import "variable.ftl" as variable/>
<#import "port.ftl" as port/>
   
<@basepage.page> 
	<@basepage.head title="Entity Index"> </@basepage.head>
	<@basepage.body> 
		<#if object.entity?exists>
			<#assign entityObj = object.entity/>
			<h1>Entity ${entityObj.name}</h1>
			<p><@util.doc object=entityObj/></p>
			<hr/>
			<@entity.port_def entity=entityObj/>
			
			
			<#if object.architecture?exists>
				<#assign architecture = object.architecture>
				<#if architecture.types?exists> 
					<@entity.types object=architecture.types/>
				</#if>
				<#if architecture.vars?exists> 
					<@variable.variable_table vars=architecture.vars/>
				</#if>
			</#if>
		
		
		</#if>
	</@basepage.body>
 	
</@basepage.page>