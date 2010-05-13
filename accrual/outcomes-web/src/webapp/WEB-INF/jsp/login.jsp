<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<c:set var="topic" scope="request" value="login"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<c:url value="/userAccount.action" var="createAccountUrl" />
<c:url value="/userAccount.action?userAction=resetPassword" var="resetPasswordUrl" />
<body>
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>Login</h1>
<div class="box">

    <c:choose>
        <c:when test="${param.userAction == '<%=gov.nih.nci.accrual.outweb.action.AccountActions.CREATE.name()%>'}">
            <div class="confirm_msg">
                <strong>Your User Account has been successfully created. Please log in using your username and password.</strong>
            </div>
        </c:when>
        <c:when test="${param.userAction == '<%=gov.nih.nci.accrual.outweb.action.AccountActions.REQUEST_LOGIN_MISMATCH.name()%>'}">
            <div class="confirm_msg">
                <strong>You authenticated using a different username and password than provided during your initial registration request. Please attempt to create your account again. </strong>
            </div>
        </c:when>
        <c:otherwise>
            <p style="margin:0; padding:0">Log in to record patient outcome information.
                If you do not have an account, you may <a href="/outcomes/register/start.action">create an account</a>.
            </p>
        </c:otherwise>
    </c:choose>
    <form action="j_security_check" method="post" id="loginForm">

    <table style="margin:0 auto">
        <c:if test="${not empty param.failedLogin}">
          <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
        </c:if>


        <tr><td class="space" colspan="2">&nbsp;</td></tr>
        <tr>
            <td class="label" scope="row">
                <label for="j_username">Username:</label>
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
        <c:set var="authMap" scope="page" value="${requestScope['AUTHENTICATION_SOURCE_MAP']}"/>
        <c:if test="${!empty requestScope['AUTHENTICATION_SOURCE_MAP']}">
            <%
              java.util.Map myMap = (java.util.Map)request.getAttribute("AUTHENTICATION_SOURCE_MAP");
              if (myMap.size() == 1) {
            %>
                <c:forEach var="item" items="${requestScope.AUTHENTICATION_SOURCE_MAP}">
                  <input type="hidden" name="authenticationServiceURL"
                       value="<c:out value="${item.value}"/>" />
                </c:forEach>
            <% } else { %>
                <td class="label" scope="row">Authentication Source:</td>
                <td class="value">
                    <select name="authenticationServiceURL" size="1">
                       <c:forEach var="item" items="${requestScope.AUTHENTICATION_SOURCE_MAP}">
                       <option value="<c:out value="${item.value}" />">
                           <c:out value="${item.key}" />
                       </option>
                       </c:forEach>
                   </select>
                </td>
            <% } %>
        </c:if>
        <tr>
			<td>&nbsp;</td>
			<td>
				<span class="small"><a title="To Reset Password" href="${resetPasswordUrl}">(Forgot Your Password?)</a></span>
			</td>
			</tr>
			<tr>
                <td>&nbsp;</td>
                <td>
                    <span class="small"><a title="To Create an Account" href="${createAccountUrl}">(Create an Account)</a></span>
                </td>
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
</page:applyDecorator>