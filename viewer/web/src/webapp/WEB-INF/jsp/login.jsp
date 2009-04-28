<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body><h1>Login</h1>
<c:set var="topic" scope="request" value="login"/> 
<div class="box"> 
    <p style="margin:0; padding:0">Please log in to view reports on CTRP trials.</p>
    <form action="j_security_check" method="post" id="loginForm">   

    <table style="margin:0 auto">
        <c:if test="${not empty param.failedLogin}">
          <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
        </c:if>
        <tr><td class="space" colspan="2">&nbsp;</td></tr>
        <tr>
            <td class="label" scope="row">
                <label for="j_username">Email Address:</label>
            </td>
            <td class="value">
                <input name="j_username" maxlength="100" size="25" type="text">
            </td>
        </tr>
        <tr>
            <td class="label" scope="row">
                <label for="j_password">Password:</label>
            </td>
            <td class="value"><input name="j_password" maxlength="100" size="25" type="password"></td>
        </tr>
    </table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:submit type="image" src="../images/btn_login.gif" cssClass="btn" title="Log In" value="Log In" align="center" />
                </li>  
            </ul>   
        </del>
    </div>
</form>
</div>
</body>
</html>
</page:applyDecorator>