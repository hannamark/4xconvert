<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<c:set var="topic" scope="request" value="login"/> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<c:url value="@application.url@/registerUser.action" var="createAccountUrl"/>
<c:url value="@application.url@/registerUser.action?resetPassword=true" var="resetPasswordUrl"/>
<body><h1>Login</h1>
<div class="box"> 
    <p style="margin:0; padding:0">Please log in to manage Accrual Submissions. 
             If you do not have an account, you may <a title="To Create an Account" href="${createAccountUrl}">create an account</a>. 
         </p>
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