<#import "basepage.ftl" as basepage/>
<#import "suite.ftl" as suite/>
   
<@basepage.page>  
	<@basepage.head title="Suite Index"> </@basepage.head>
	<@basepage.body>  
		<h1>Suite ${object.name}</h1>
		<hr/>
		<@suite.project_list object=object/>
		<@suite.library_list object=object/>
	</@basepage.body>
 	
</@basepage.page>  