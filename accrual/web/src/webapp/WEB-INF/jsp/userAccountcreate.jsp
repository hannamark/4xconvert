<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/userAccountinitTreatmentSiteLookup.action" var="treatmentSiteLookupUrl" />
<c:url value="/userAccountinitPhysicianLookup.action" var="physicianLookupUrl" />
<head>
    <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>

    <script type="text/javascript">
        function handleAction(){
            document.forms[0].action="userAccountcreate.action";
            document.forms[0].submit();
        }
        
        function lookupTreatmentSite(action){
            if (action == 'activateAccount') {
                showPopWinOutsideContext('${treatmentSiteLookupUrl}', 900, 400, '', 'Select Treatment Site');
            } else {
                showPopWin('${treatmentSiteLookupUrl}', 900, 400, '', 'Select Treatment Site');
            }
        }
        
        function lookupPhysician(action){
            if (action == 'activateAccount') {
                showPopWinOutsideContext('${physicianLookupUrl}', 900, 400, '', 'Select Physician');
            } else {
                showPopWin('${physicianLookupUrl}', 900, 400, '', 'Select Physician');
            }
        }
    </script>
    
    <title><fmt:message key="user.account.create.page.title"/></title>   
    <s:head/>
</head>

<body>
    <h1><fmt:message key="user.account.create.page.title"/></h1>
    <c:set var="topic" scope="request" value="create_account"/> 
    <div class="box">
    
        <s:if test="userAction == 'updateAccount'">
            <div class="confirm_msg"><strong>Your account was successfully updated</strong></div>
        </s:if>
    
        <s:form name="myAccount" method="POST">
            <s:actionerror/>
            <s:hidden name="userAccount.id" />
            <s:if test="userAccount.id == null">
                <p>To activate your account for NCI Outcomes, please begin by creating your login information.<br>                   
                   Please note: asterisks (<span class="required">*</span>) indicate required fields.<br>
                   Passwords must have a minimum of 8 characters and contain at least one special character and one digit.<br>
                   <b><i>Please provide professional contact information only.</i></b>
                </p>             
            </s:if>
            <s:else>
                <p>You may update your account information. Please note: asterisks (<span class="required">*</span>) indicate required fields.<br>
                   Passwords must have a minimum of 8 characters and contain at least one special character and one digit.<br>
                   <b><i>Please provide professional contact information only.</i></b>
                </p>
            </s:else>
            <table class="form">
                <tr><th colspan="3">Login Information</th></tr> 
                <tr><td colspan="2" class="space">&nbsp;</td></tr>          
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.email.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.loginName" maxlength="100" size="35" readonly="true" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.loginName</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.password.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:password name="userAccount.password" showPassword="true" maxlength="100" size="35" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.password</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.retypePassword.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:password name="userAccount.retypePassword" showPassword="true" maxlength="100" size="35" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.retypePassword</s:param></s:fielderror>
                    </td>               
                </tr>
                <tr><td class="space" colspan="2">&nbsp;</td></tr>
                <tr><th colspan="3">Your Account Profile</th></tr>
                <tr><td class="space" colspan="2">&nbsp;</td></tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.firstName.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.firstName" maxlength="150" size="50" cssStyle="width:150px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.firstName</s:param></s:fielderror>
                    </td>              
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.middleInitial.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.middleName" maxlength="2" size="35" cssStyle="width:20px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.middleName</s:param></s:fielderror>
                    </td>                
                </tr> 
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.lastName.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.lastName" maxlength="150" size="50" cssStyle="width:150px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.lastName</s:param></s:fielderror>
                    </td>              
                </tr>                 
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.address.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.address" maxlength="200" size="50" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.address</s:param></s:fielderror>
                    </td>                
                </tr>                  
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.city.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.city" maxlength="200" size="35" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.city</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.state.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:select name="userAccount.state" headerKey="" headerValue="--Select--" 
                                  list="userAccount.states" listKey="name" listValue="code" value="userAccount.state" cssStyle="width:206px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.state</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.zipCode.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.zipCode" maxlength="15" size="8" cssStyle="width:80px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.zipCode</s:param></s:fielderror>
                    </td>               
                </tr>                   
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.country.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:select name="userAccount.country" headerKey="USA" headerValue="United States" 
                                  list="userAccount.countries" listKey="alpha3" listValue="name" value="userAccount.country" cssStyle="width:206px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.country</s:param></s:fielderror>
                    </td>               
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.phone.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.phoneNumber" maxlength="50" size="15" cssStyle="width:120px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.phoneNumber</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.organization.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.organization" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.organization</s:param></s:fielderror>
                    </td>               
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.prsOrganization.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.prsOrganization" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.prsOrganization</s:param></s:fielderror>
                    </td>               
                </tr>                
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.treatmentSite.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield readonly="true" size="30" name="userAccount.treatmentSite" cssStyle="float:left; width:200px" cssClass="readonly"/>                       
                        <s:hidden name="userAccount.treatmentSiteId"/>
                        <a href="#" class="btn" onclick="lookupTreatmentSite('<s:property value="userAction"/>');"/><span class="btn_img"><span class="search">Look Up</span></span></a>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.treatmentSite</s:param></s:fielderror>
                    </td>
                </tr>                
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.physician.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield readonly="true" size="30" name="userAccount.physician" cssStyle="float:left; width:200px" cssClass="readonly"/>
                        <s:hidden name="userAccount.physicianId"/>
                        <a href="#" class="btn" onclick="lookupPhysician('<s:property value="userAction"/>');"/><span class="btn_img"><span class="search">Look Up</span></span></a>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.physician</s:param></s:fielderror>
                    </td>
                </tr>                               
                <tr>
                    <td colspan="2" align="left">
                        <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
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