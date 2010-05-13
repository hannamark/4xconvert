<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>
        <fmt:message key="user.account.request.page.title"/>
    </title>
<s:head/>
</head>

<body>
    <h1>
        <fmt:message key="user.account.request.page.title"/>
    </h1>  
    <c:set var="topic" scope="request" value="create_account"/> 
    <div class="box">
        <s:actionerror />
        <s:form name="requestAccountForm" validate="true" action="request.action">
            <p>Please provide your email address to request an account for enabling patient's outcome reporting. </p>
            <table class="form"> 
                <tr><td colspan="2" class="space">&nbsp;</td></tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.email.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.email" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.email</s:param></s:fielderror>
                    </td>
                </tr>
            </table>
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">         
                        <li>       
                            <s:a href="#" cssClass="btn" onclick="return document.requestAccountForm.submit();"><span class="btn_img"><span class="login">Submit</span></span></s:a>  
                        </li> 
                    </ul>   
                </del>
            </div>        
        </s:form>
    </div>  
</body>
</html>
