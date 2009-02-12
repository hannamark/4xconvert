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
 <c:choose>
     <c:when test="${param.userAction == 'create'}">
		<div class="confirm_msg">
		  <strong>Your User Account has been successfully created. Please log in using your email address and password.</strong>
		</div>
     </c:when>
     <c:when test="${param.userAction == 'reset'}">
        <div class="confirm_msg">
          <strong>Your password has been successfully reset. Please log in using your email address and password.</strong>
        </div>
     </c:when>
     <c:otherwise>
           <p style="margin:0; padding:0">Please log in to search, view and register clinical trial details. 
             If you do not have an account, you may <a title="To Create an Account" href="/registry/registerUser.action">create an account</a>. </p>
     </c:otherwise>
 </c:choose>
<form action="j_security_check" method="post" id="loginForm">   

 <table style="margin:0 auto">                 
            <c:if test="${not empty param.failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <tr>
			<td class="space" colspan="2">
					&nbsp;
			</td>
			</tr>
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
				<td class="value"><input name="j_password" maxlength="100" size="15" type="password"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<span class="small"><a title="To Reset Password" href="/registry/registerUser.action?resetPassword=true">(Forgot Your Password?)</a></span> 
				</td>
			</tr>
			<tr>
                <td>&nbsp;</td>
                <td>
                    <span class="small"><a title="To Create an Account" href="/registry/registerUser.action">(Create an Account)</a></span> 
                </td>
            </tr>   
</table>

           <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li>       
                        <s:a href="#" cssClass="btn" onclick="document.forms.loginForm.submit();"><span class="btn_img"><span class="login">Login</span></span></s:a>  
                    </li>  
 
                </ul>   
            </del>

        </div>
        </form>
</div>
</body>
</html>
</page:applyDecorator>