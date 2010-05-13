<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/account/initTreatmentSiteLookup.action" var="treatmentSiteLookupUrl" />
<c:url value="/account/initPhysicianLookup.action" var="physicianLookupUrl" />
<head>
    <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>

    <script type="text/javascript">
        function handleAction(){
            <s:if test="!patients">
                if (confirm("Once patients have been entered the associated Treatment Site and Physician can no longer be changed.")){
                    document.forms[0].submit();
                }
            </s:if>
            <s:else>
                document.forms[0].submit();
            </s:else>            
        }
        
        function lookupTreatmentSite(action){
            showPopup('${treatmentSiteLookupUrl}', '', 'Select Treatment Site');
        }
        
        function lookupPhysician(action){
            showPopup('${physicianLookupUrl}', '', 'Select Physician');
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
    
        <s:form name="myAccount" method="POST" action="update.action">
            <s:hidden name="userAccount.identifier"/>
            <s:hidden name="userAccount.identity"/>
            <s:hidden name="userAccount.password"/>
            <s:hidden name="userAccount.retypePassword"/>
            <s:actionerror/>
            <table class="form">
                <tr><th colspan="3">Grid Credential Information</th></tr> 
                <tr><td colspan="2" class="space">&nbsp;</td></tr>    
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.identity.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:property value="userAccount.identity"/>
                    </td>                
                </tr>
                <tr><td class="space" colspan="2">&nbsp;</td></tr>
                <tr><th colspan="3">Your Account Profile</th></tr>
                <tr><td class="space" colspan="2">&nbsp;</td></tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.email.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.email" maxlength="150" size="50" cssStyle="width:150px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.email</s:param></s:fielderror>
                    </td>              
                </tr>
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
                                  list="userAccount.states" listKey="name" listValue="code" value="userAccount.state.value" cssStyle="width:206px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.state</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.zipCode.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.postalCode" maxlength="15" size="8" cssStyle="width:80px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.postalCode</s:param></s:fielderror>
                    </td>               
                </tr>                   
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.country.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:select name="userAccount.country" headerKey="USA" headerValue="United States" 
                                  list="userAccount.countries" listKey="alpha3" listValue="name" value="userAccount.country.value" cssStyle="width:206px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.country</s:param></s:fielderror>
                    </td>               
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.phone.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.phone" maxlength="50" size="15" cssStyle="width:120px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.phone</s:param></s:fielderror>
                    </td>                
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.organization.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield name="userAccount.affiliateOrg" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.affiliateOrg</s:param></s:fielderror>
                    </td>               
                </tr>
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.prsOrganization.label"/></label></td>
                    <td>
                        <s:textfield name="userAccount.prsOrg" maxlength="200" size="100" cssStyle="width:200px"/>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.prsOrg</s:param></s:fielderror>
                    </td>               
                </tr>                
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.treatmentSite.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield readonly="true" size="30" name="userAccount.treatmentSite" cssStyle="float:left; width:250px" cssClass="readonly"/>                       
                        <s:hidden name="userAccount.treatmentSiteIdentifier"/>
                        <s:if test="!patients">
                        <a href="#" class="btn" onclick="lookupTreatmentSite();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
                        </s:if>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.treatmentSite</s:param></s:fielderror>
                    </td>
                </tr>                
                <tr>
                    <td scope="row" class="label"><label><fmt:message key="user.account.physician.label"/><span class="required">*</span></label></td>
                    <td>
                        <s:textfield readonly="true" size="30" name="userAccount.physician" cssStyle="float:left; width:250px" cssClass="readonly"/>
                        <s:hidden name="userAccount.physicianIdentifier"/>
                        <s:if test="!patients">
                        <a href="#" class="btn" onclick="lookupPhysician();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
                        </s:if>
                        <s:fielderror cssClass="formErrorMsg"><s:param>userAccount.physician</s:param></s:fielderror>
                    </td>
                </tr>                               
                <tr>
                    <td colspan="2" align="left">
                        <p><b><I>Contact information is required for internal administrative use only; it is not revealed to the public.</I></b></p>
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