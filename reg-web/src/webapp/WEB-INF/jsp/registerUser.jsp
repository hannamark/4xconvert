<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="register.user.page.title"/></title>
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function handleAction(){
    document.forms[0].page.value = "Submit";
    document.forms[0].action="registerUserverifyEmail.action";
    document.forms[0].submit();
}
</SCRIPT>

<body>
<!-- main content begins-->
    <h1><fmt:message key="register.user.page.header"/></h1>
    <c:set var="topic" scope="request" value="createaccount"/>
    <div class="box" id="filters">
    <s:form name="registerUser" validate="true" method="POST" ><s:actionerror />
        <s:hidden name="page" />
    <table class="form">
        <tr>
            <s:url id="existingGridAccountUrl" action="registerUserexistingGridAccount.action" />
            <td colspan="2" class="space">
            <ul>
                <li>If you already have an NIH or NCI account, click <s:a href="%{existingGridAccountUrl}">here</s:a> to proceed.</li>
                <li>If you do not have an NIH or NCI account, enter the email address below and click Next.</li>
            </ul>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="registerUser_registryUserWebDTO_emailAddress">
            <fmt:message
                key="register.user.emailAddress" /><span class="required">*</span></label></td>
            <td><s:textfield id="registerUser_registryUserWebDTO_emailAddress" name="registryUserWebDTO.emailAddress" maxlength="200" size="100"
                cssStyle="width:200px" /> <span class="alert-danger"> <s:fielderror>
                <s:param>registryUserWebDTO.emailAddress</s:param>
            </s:fielderror> </span></td>
        </tr>
    </table>
    <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img">
                        <span class="login">Next</span></span></s:a>
                    </li>
                </ul>
            </del>
        </div>

   </s:form>

 </div>

</body>
</html>
