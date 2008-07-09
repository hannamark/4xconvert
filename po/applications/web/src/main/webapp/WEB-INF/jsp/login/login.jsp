<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<html>
<head>
    <title>Login</title>
</head>
<body onload="setFocusToFirstControl();">
    <div class="po_form">
        <form action="j_security_check" method="post" id="loginForm">
            <input id="enableEnterSubmit" type="submit"/>
            <c:if test="${not empty param.failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <div class="fieldrow">
                <label for="j_username">Username:</label>
                <div class="fieldbox_m required"><input name="j_username" maxlength="100" size="15" type="text"></div>
            </div>
            <div class="fieldrow">
                <label for="j_password">Password:</label>
                <div class="fieldbox_m required"><input name="j_password" maxlength="100" size="15" type="password"></div>
            </div>
            <div class="clearfloat"></div>
            <div class="btnwrapper">
                <po:buttonRow><po:button href="#" onclick="document.forms.loginForm.submit();" style="continue" text="Login"/></po:buttonRow>
            </div>
        </form>
    </div>
</body>
</html>
</page:applyDecorator>