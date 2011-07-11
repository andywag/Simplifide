<#import "basepage.ftl" as basepage/>
<#import "util.ftl" as util/>
<#import "variable.ftl" as variable/>
<#import "port.ftl" as port/>

<#macro port_def entity>
	<#if entity.genericList?exists>
		<@port.port_table type="Generic" ports=entity.genericList/>
	</#if>
	<#if entity.portList?exists>
		<@port.port_table type="Port" ports=entity.portList/>
	</#if>
</#macro>


<#macro port_list entity>
	
	<#if entity.genericList?exists>
		<@port.port_list type="Generics" ports=entity.genericList/>
		
	</#if>
	<#if entity.portList?exists>
		<@port.port_list type="Ports" ports=entity.portList/>
	</#if>
	
</#macro>

<#macro ports object> 
	
</#macro>

<#macro types object> 
	<h2>Types</h2>
	<ul>
	<#list object as port>
		<li><@portdef port = port/></li>
	</#list>
	</ul>
</#macro>

<#macro vars object> 
	<h2>Signals</h2>
	<ul>
	<#list object as var>
		<li>${var.name}</li>
	</#list>
	</ul>
</#macro>


<#macro portdef port>
	${port.name} <@util.doc object=port/>
</#macro>



