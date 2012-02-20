<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <script type="text/javascript" src="${scriptPath}/pages/loginValidation.js"></script>
    </head>
    <body>
        <h1>Login</h1>
        <c:set var="topic" scope="request" value="login"/> 
        <div> 
            <p style="margin:0; padding:0">Please log in to view reports on CTRP trials.</p>
            <s:form action="j_security_check" method="post" id="loginForm">
                <s:token/>
                <table style="margin:0 auto">
                    <c:if test="${not empty param.failedLogin}">
                        <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
                    </c:if>
                    <tr>
                        <td class="space" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="label" scope="row">
                            <label for="j_username">Login Id:</label>
                        </td>
                        <td class="value">
                            <input name="j_username" maxlength="100" size="25" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td class="label" scope="row">
                            <label for="j_password">Password:</label>
                        </td>
                        <td class="value">
                            <input name="j_password" maxlength="100" size="25" type="password" autocomplete="off"/>
                        </td>
                    </tr>                
                        <c:if test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
                           <c:choose>
                           <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1 }">
                              <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                                <input type="hidden" name="authenticationServiceURL" value="<c:out value="${item.value}"/>" />
                              </c:forEach>
                            </c:when>
                            <c:otherwise>
                            <tr>
                               <td class="label">Account Source: </td>
                                <td>
                                 <select name="authenticationServiceURL" id="authenticationServiceURL" size="1">
                                    <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                                    <option value="<c:out value="${item.value}" />">
                                        <c:out value="${item.key}" />
                                    </option>
                                    </c:forEach>
                                </select>
                                </td>                    
                             </tr>
                            </c:otherwise>
                          </c:choose>
                       </c:if>
                </table>
                <viewer:buttonBar>
                    <viewer:button labelKey="login.button.login" id="loginButton" imgClass="login" />
                </viewer:buttonBar>
            </s:form>
        </div>
    </body>
</page:applyDecorator>