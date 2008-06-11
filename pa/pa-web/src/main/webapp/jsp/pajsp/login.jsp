<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>	
<%@taglib prefix="s" uri="/struts-tags" %>

<s:set name="menuPage" value="%{'login'}"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trail.login.title"/></title>
	<s:head />
</head>

<body onload="setFocusToFirstControl();">
 <h1><fmt:message key="trail.login.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>

        <!--Box-->

<div id="box">

<s:form action="authenticate">

  <s:actionerror />

  <s:textfield key="trail.login.username" name="username" />
  <s:password key="trail.login.password" name="password" />
 <s:submit value="Login" align="center"/>
</s:form>

</div>

</body>
</html>