<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="user.account.request.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>

<body>
    <c:set var="topic" scope="request" value="create_account"/>
    <h2><fmt:message key="user.account.request.confirmation"/></h2>
    <div class="box" id="filters">
        <p>An e-mail has been sent to the e-mail address, <strong><s:property value="userAccount.loginName"/></strong>.
           Please click on the link provided in the e-mail to activate your account.</p>
    </div>
   
</body>
</html>