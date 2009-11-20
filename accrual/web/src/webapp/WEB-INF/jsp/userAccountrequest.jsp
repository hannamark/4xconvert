<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <script type="text/javascript">
        function handleAction() {
            document.forms[0].action="userAccountrequest.action";
            document.forms[0].submit();  
        }
    </script>

    <title>
        <s:if test="userAction == 'resetPassword'">
            <fmt:message key="reset.password.page.title"/>
        </s:if>
        <s:else>
            <fmt:message key="user.account.request.page.title"/>
        </s:else>
    </title>
    
    <s:head/>
</head>

<body>
    <h1>
        <s:if test="userAction == 'resetPassword'">
            <fmt:message key="reset.password.page.title"/>
        </s:if>
        <s:else>
            <fmt:message key="user.account.request.page.title"/>
        </s:else>
    </h1>  
    <c:set var="topic" scope="request" value="create_account"/> 
    <div class="box">
        <s:actionerror />
        <s:form name="requestAccountForm">
            <s:hidden name="userAction" />
            <s:if test="userAction == 'resetPassword'">
                <p>To reset your password, please begin by entering your login name and new password. </p>
            </s:if>
            <s:else>
                <p>Create account to report outcomes information on patient outcomes.</p>
            </s:else>            
            <table class="form"> 
                <tr><td colspan="2" class="space">&nbsp;</td></tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.email.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.loginName" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.loginName</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.password.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:password name="userAccount.password" showPassword="true" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.password</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.retypePassword.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:password name="userAccount.retypePassword" showPassword="true" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.retypePassword</s:param></s:fielderror>
                    </td>                
                </tr>
            </table>
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">         
                        <li>       
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="login">Submit</span></span></s:a>  
                        </li> 
                    </ul>   
                </del>
            </div>        
        </s:form>
    </div>  
</body>
</html>
