<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set name="menuPage" value="%{'other'}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Hello World</title>
	<s:head />
</head>
<body>
	Hello <s:property value="name"/>, today is <s:property value="dateNow" /><br/>
	
	<ul>
			<li><a href="searchProtocol.action">Search Protocol</a></li>
	</ul>	
	<ul>
			<li><a href="trailDetailEdit.action">Trail Detail Edit</a></li>
	</ul>	

	<ul>
			<li><a href="login.action">Login</a></li>
	</ul>	
</body>
</html>
