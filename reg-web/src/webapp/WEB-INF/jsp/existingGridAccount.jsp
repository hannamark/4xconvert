<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Register with existing NIH account</title>
        <s:head/>
    </head>

    <SCRIPT LANGUAGE="JavaScript">
        function handleAction(){
            document.forms[0].page.value = "Submit";
            document.forms[0].action="registerUserregisterExistingGridAccount.action";
            if (validate()) {
                document.forms[0].submit()
             }
        }
        function validate() {
            var box = document.forms[0].username;
            
            re = /^[0-9a-zA-Z\_\.\-]*$/;

            if (!re.exec(box.value)) {
                alert("Invalid Entry:\nOnly Alphanumeric, _ or . are allowed.");
                return false;
            }
            return true;
        }
    </SCRIPT>
    <body>
        <c:set var="topic" scope="request" value="register"/>
        <h1>Register for a CTRP Account</h1>
        <div class="box">
            <s:form name="existingAccount" method="POST">
                <s:actionerror/>
                <s:hidden name="page" />
                <table style="margin: 0 auto">
                    <tr>
                        <td class="space" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="label" scope="row">
                            <label for="username"><fmt:message key="register.user.username"/></label>
                        </td>
                        <td class="value"><s:textfield name="userWebDTO.username" maxlength="15" size="20" cssStyle="width:200px" id="username"/></td>
                    </tr>
                    <tr>
                        <td class="label" scope="row">
                            <label for="password"> <fmt:message key="register.user.password"/></label>
                        </td>
                        <td class="value">
                            <s:password  name="userWebDTO.password"  showPassword="true" maxlength="100" size="35"  cssStyle="width:200px"  id="password"/>
                        </td>
                    </tr>
                    <tr>
                        <s:if test="%{identityProviders.size == 1}">
                            <s:iterator var="idp" value="identityProviders">
                                <s:hidden name="selectedIdentityProvider"/>
                            </s:iterator>
                        </s:if>
                        <s:else>
                            <td class="label" scope="row"><label for="selectedIdentityProvider">Account Source:</label></td>
                            <td class="value">
                                <s:select name="selectedIdentityProvider" list="identityProviders" listKey="value" listValue="key" id="selectedIdentityProvider"/>
                            </td>
                        </s:else>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="login">Next</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
