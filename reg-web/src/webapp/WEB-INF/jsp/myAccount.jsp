<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="register.user.myaccount.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function handleAction(){   
    document.forms[0].page.value = "Submit";
    document.forms[0].action="registerUserupdateAccount.action";
    document.forms[0].submit();  
}
</SCRIPT>

<body>
<!-- main content begins-->
    <h1><fmt:message key="register.user.myaccount.header"/></h1>
    <c:set var="topic" scope="request" value="my_account"/> 
    <div class="box" id="filters">
    <reg-web:sucessMessage/>
    
    <s:form name="myAccount" method="POST" >
        
        <s:actionerror/>
        <s:hidden name="registryUserWebDTO.id" />
        <s:hidden name="registryUserWebDTO.csmUserId" />
        <s:hidden name="registryUserWebDTO.treatmentSiteId" />
        <s:hidden name="registryUserWebDTO.physicianId" />
        <s:hidden name="page" />
        <s:if test="%{registryUserWebDTO.id == null}">
            <p>To register for NCI Clinical Trials Reporting Program, please begin by creating your login information. <br>
            Please note: asterisks (<span class="required">*</span>) indicate required fields.<br>
            <b><i> Please provide professional contact information only.</i></b></p>
        </s:if>
        <s:else>
            <p>You may update your account information. Please note: asterisks (<span class="required">*</span>)
            indicate required fields.<br>
            <b><i> Please provide professional contact information only. </i></b></p>
        </s:else>
    <table class="form">
            <tbody>
                  <tr>
                      <th colspan="3">
                          Login Information
                      </th>
                  </tr> 
                  <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_emailAddress"><fmt:message key="register.user.emailAddress"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.emailAddress"  maxlength="100" size="35" cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.emailAddress</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                    <td scope="row" class="label">&nbsp;</td>
                    <td><fmt:message key="register.user.password.rules" /></td>
                  </tr>
                  <s:if test="%{registryUserWebDTO.id != null && registryUserWebDTO.id != ' '}">
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_password"> Old <fmt:message key="register.user.password"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:password  name="registryUserWebDTO.oldPassword"  showPassword="true" maxlength="100" size="35"  cssStyle="width:200px"  />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.oldPassword</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  </s:if>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_password">
                              <s:if test="%{registryUserWebDTO.id != null && registryUserWebDTO.id != ' '}">
                             		New <fmt:message key="register.user.password"/>
                             </s:if>
                             <s:else>
                             		<fmt:message key="register.user.password"/>
                             </s:else>
                             <span class="required">*</span></label>
                        </td>
                        <td>
                            <s:password  name="registryUserWebDTO.password"  showPassword="true" maxlength="100" size="35"  cssStyle="width:200px"  />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.password</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_retypePassword"> 
                            <s:if test="%{registryUserWebDTO.id != null && registryUserWebDTO.id != ' '}">
                             		Retype New Password
                             </s:if>
                             <s:else>
                            	<fmt:message key="register.user.retypePassword"/>
							</s:else>                            	
                            <span class="required">*</span></label>
                        </td>
                        <td>
                            <s:password  name="registryUserWebDTO.retypePassword"  showPassword="true" maxlength="100" size="35"  cssStyle="width:200px"  />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.retypePassword</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                      <td class="space" colspan="2">
                          &nbsp;
                      </td>
                  </tr>
                  <tr>
                      <th colspan="3">
                          Your Account Profile
                      </th>
                  </tr>
                  <tr>
                      <td class="space" colspan="2">
                          &nbsp;
                      </td>
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_username"><fmt:message key="register.user.username"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:if test="%{registryUserWebDTO.id == null}">
                                <s:textfield name="registryUserWebDTO.username" maxlength="15" size="20" cssStyle="width:200px" />
                            </s:if>
                            <s:else>
                                <s:textfield name="registryUserWebDTO.username" maxlength="15" size="20" cssStyle="width:200px" readonly="true" />
                            </s:else>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.username</s:param>
                               </s:fielderror>                            
                            </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_firstName"> <fmt:message key="register.user.firstName"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.firstName"  maxlength="150" size="50"  cssStyle="width:150px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.firstName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_middleName"> <fmt:message key="register.user.middleInitial"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.middleName"  maxlength="2" size="35"  cssStyle="width:20px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.middleName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr> 
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_lastName"> <fmt:message key="register.user.lastName"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.lastName"  maxlength="150" size="50"  cssStyle="width:150px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.lastName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                 
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_addressLine"><fmt:message key="register.user.streetAddress"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.addressLine"  maxlength="200" size="50"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.addressLine</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>                  
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_city"> <fmt:message key="register.user.city"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.city"  maxlength="200" size="35"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.city</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_state"><fmt:message key="register.user.state"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:set name="stateCodeValues" 
                                    value="@gov.nih.nci.pa.enums.USStateCode@getDisplayNames()" />                                           
                            <s:select headerKey="" headerValue="--Select--" 
                                name="registryUserWebDTO.state" 
                                list="#stateCodeValues"  
                                value="registryUserWebDTO.state" 
                                cssStyle="width:206px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.state</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_postalCode"><fmt:message key="register.user.zipCode"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.postalCode"  maxlength="15" size="8"  cssStyle="width:80px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.postalCode</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>                   
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_country"> <fmt:message key="register.user.country"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:set name="countries" 
                                    value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().
                                    getCountries()" />
                            <s:select headerKey="United States" headerValue="United States" 
                                     name="registryUserWebDTO.country" 
                                     list="#countries"
                                     listKey="name" 
                                     listValue="name"   
                                     value="registryUserWebDTO.country" 
                                     cssStyle="width:206px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.country</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>

                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_phone"> <fmt:message key="register.user.phone"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.phone"  maxlength="50" size="15"  cssStyle="width:120px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.phone</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_affiliateOrg"> <fmt:message key="register.user.affiliateOrg"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.affiliateOrg"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.affiliateOrg</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="registerUsershowMyAccount_registryUserWebDTO_prsOrgName"> <fmt:message key="register.user.prsOrgName"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.prsOrgName"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.prsOrgName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>  
                  <tr>
                  <td colspan="2" align="left">
                   <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
                  </td>
                  </tr>
              </tbody>
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
