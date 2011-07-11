<#import "main_module.ftl" as main_module/>

<#macro table entity>
	<table class="signal" border = "1" width = "100%">
	<caption>Signal Interface for ${entity.name}</caption>
		<tr class = "signal_head">
			<td width = "15%">Signal</td>
			<td width = "10%">I/O</td>
			<td width = "15%">To/From</td>
			<td width = "60%">Description</td>
		</tr>
		<#list entity.portList as port>
			<#if port.ioString = "input">
				<@portrow port=port/>
			</#if>
		</#list>
		<#list entity.portList as port>
			<#if port.ioString = "output">
				<@portrow port=port/>
			</#if>
		</#list>
	</table>

</#macro>

<#macro portrow port>
	<tr>
		<td>${port.name}</td>
		<td>${port.ioString}</td>
		<td>${port.source}</td>
		<#if port.doc?exists>
			<td>${port.doc.description}</td>
		<#else>
			<td></td>
		</#if>
	</tr>
</#macro>

