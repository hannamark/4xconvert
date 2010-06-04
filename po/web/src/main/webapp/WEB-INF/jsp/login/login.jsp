<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<page:applyDecorator name="main">
<html>
<head>
    <title>Login</title>
</head>
<body onload="setFocusToFirstControl();">
    <div class="po_form">
        <script type="text/javascript">
            function startLogin() {
                $('login_progress').show();
                <c:choose>
                    <c:when test="${param.fromAjax == 'true'}">
                new Ajax.Request('<c:url value="/protected/home.action"/>', { onSuccess: completeLogin });
                    </c:when>
                    <c:otherwise>
                completeLogin();
                    </c:otherwise>
                </c:choose>
            }

            function completeLogin() {
                $('loginForm').submit();
            }
        </script>
        <div id="login_progress" style="display: none; margin: 3px 3px">
           <img alt="Indicator" align="absmiddle" src="<c:url value="/images/loading.gif"/>" /> Logging in
        </div>
        <form action="j_security_check" method="post" id="loginForm" onsubmit="startLogin(); return false;">
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
            <div class="fieldrow">
                <c:set var="authMap" scope="page" value="${requestScope['AUTHENTICATION_SOURCE_MAP']}"/>
                <c:if test="${!empty requestScope['AUTHENTICATION_SOURCE_MAP']}">
                    <%
                        java.util.Map myMap = (java.util.Map)request.getAttribute("AUTHENTICATION_SOURCE_MAP");
                        if (myMap.size() == 1) {
                    %>
                    <c:forEach var="item" items="${requestScope.AUTHENTICATION_SOURCE_MAP}">
                        <input type="hidden" name="authenticationServiceURL" value="<c:out value="${item.value}"/>" />
                    </c:forEach>
                    <% } else { %>
                        <label for="authenticationServiceURL">Account Source:</label>
                        <div class="fieldbox_m">
                            <select name="authenticationServiceURL" size="1">
                                <c:forEach var="item" items="${requestScope.AUTHENTICATION_SOURCE_MAP}">
                                    <option value="<c:out value="${item.value}" />">
                                        <c:out value="${item.key}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    <% } %>
                </c:if>
            </div>
            <div class="clearfloat"></div>
            <div class="btnwrapper">
                <po:buttonRow><po:button href="#" onclick="startLogin();" style="continue" text="Login"/></po:buttonRow>
            </div>
        </form>
    </div>
</body>
</html>
</page:applyDecorator>
