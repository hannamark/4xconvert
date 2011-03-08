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
    document.forms[0].action="registerUsersendMail.action";
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
        <p>Create account to register trials with NCI Clinical Trials Reporting Program. </p>
        <table class="form">
            <tr>
                <s:url id="existingGridAccountUrl" action="registerUserexistingGridAccount.action"/>
                <td colspan="2" class="space">
                    <h3>If you already have an NIH or caGrid account, click <s:a href="%{existingGridAccountUrl}">here.</s:a></h3>
                </td>
            </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="registerUser_registryUserWebDTO_emailAddress"><fmt:message key="register.user.emailAddress"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="registryUserWebDTO.emailAddress"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>registryUserWebDTO.emailAddress</s:param>
                       </s:fielderror>
                     </span>
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
