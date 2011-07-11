<#import "main_module.ftl" as main_module/>



<html>
	<head>
		<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
		<title>${object.name} Micro Architecture Specification</title>
	</head>
	<body>
		<h1>${object.name} Micro Architecture Specification</h1>
		<@main_module.module object = object.connections index=1/>
	</body>
	
</html>