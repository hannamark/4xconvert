<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body onload="setFocusToFirstControl();">

<h2>Login</h2>
<div class="box"> 
<form action="j_security_check" method="post" id="loginForm">   

 <table class="form">                 
            <c:if test="${not empty param.failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <div class="clearfloat"></div>
             <div class="fieldrow">
                <label for="j_username">Username:</label>
                <div class="fieldbox_m required"><input name="j_username" maxlength="100" size="15" type="text"></div>
            </div>
            <div class="fieldrow">
                <label for="j_password">Password:</label>
                <div class="fieldbox_m required"><input name="j_password" maxlength="100" size="15" type="password"></div>
            </div>
            <div class="clearfloat"></div>
           <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li>       
                           <s:a href="#" cssClass="btn" onclick="document.forms.loginForm.submit();"><span class="btn_img"><span class="login">Login</span></span></s:a>  
                        </li>
                </ul>   
            </del>

        </div>
        
   
</table></form>
</div>
</body>
</html>
</page:applyDecorator>