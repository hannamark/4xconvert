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
    <s:form name="myAccount" method="POST" ><s:actionerror/>
        <s:hidden name="registryUserWebDTO.id" />
        <s:hidden name="registryUserWebDTO.csmUserId" />
        <input type="hidden" name="page" />
        <c:choose>
	        <c:when test="${requestScope.isNewUser  == true}">
	            <p>To register for NCI Clinical Trials Reporting Program, 
	            please begin by creating your login information. <br>
	            Please note: asterisks (<span class="required">*</span>) indicate required fields.</p>             
	        </c:when>
	        <c:otherwise >
                <p>You may update your account information. 
                Please note: asterisks (<span class="required">*</span>) indicate required fields.  </p>
            </c:otherwise>
        </c:choose>
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
		                    <label for="emailAddress"> <fmt:message key="register.user.emailAddress"/><span class="required">*</span></label>
		                </td>
		                <td>
		                    <s:textfield name="registryUserWebDTO.loginName"  maxlength="200" size="100" readonly="true"  cssStyle="width:200px" />
		                    <span class="formErrorMsg"> 
		                        <s:fielderror>
		                        <s:param>registryUserWebDTO.loginName</s:param>
		                       </s:fielderror>                            
		                     </span>
		                </td>                
		          </tr>
		          <tr>
		                <td scope="row" class="label">
		                    <label for="password"> <fmt:message key="register.user.password"/><span class="required">*</span></label>
		                </td>
		                <td>
		                    <s:password  name="registryUserWebDTO.password"  showPassword="true" maxlength="200" size="100"  cssStyle="width:200px"  />
		                    <span class="formErrorMsg"> 
		                        <s:fielderror>
		                        <s:param>registryUserWebDTO.password</s:param>
		                       </s:fielderror>                            
		                     </span>
		                </td>                
		          </tr>
		          <tr>
		                <td scope="row" class="label">
		                    <label for="retypePassword"> <fmt:message key="register.user.retypePassword"/><span class="required">*</span></label>
		                </td>
		                <td>
		                    <s:password  name="registryUserWebDTO.retypePassword"  showPassword="true" maxlength="200" size="100"  cssStyle="width:200px"  />
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
                            <label for="firstName"> <fmt:message key="register.user.firstName"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.firstName"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.firstName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="lastName"> <fmt:message key="register.user.lastName"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.lastName"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.lastName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="middleInitial"> <fmt:message key="register.user.middleInitial"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.middleName"  maxlength="5" size="5"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.middleName</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>                  
                  <tr>
                        <td scope="row" class="label">
                            <label for="streetAddress"> <fmt:message key="register.user.streetAddress"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.addressLine"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.addressLine</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>                  
                  <tr>
                        <td scope="row" class="label">
                            <label for="city"> <fmt:message key="register.user.city"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.city"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.city</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="state"> <fmt:message key="register.user.state"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:set name="stateCodeValues" 
                                    value="@gov.nih.nci.pa.enums.USStateCode@getDisplayNames()" />                                           
                            <s:select headerKey="" headerValue="--Select--" 
                                name="registryUserWebDTO.state" 
                                list="#stateCodeValues"  
                                value="registryUserWebDTO.state" 
                                cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.state</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>                  
                  <tr>
                        <td scope="row" class="label">
                            <label for="country"> <fmt:message key="register.user.country"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:set name="countries" 
                                    value="@gov.nih.nci.registry.util.
                                    RegistryServiceLocator@getLookUpTableService().
                                    getCountries()" />
                            <s:select headerKey="United States" headerValue="United States" 
                                     name="registryUserWebDTO.country" 
                                     list="#countries"
                                     listKey="name" 
                                     listValue="name"   
                                     value="registryUserWebDTO.country" 
                                     cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.country</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="zipCode"> <fmt:message key="register.user.zipCode"/></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.postalCode"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.postalCode</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr> 
                  <tr>
                        <td scope="row" class="label">
                            <label for="phone"> <fmt:message key="register.user.phone"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield name="registryUserWebDTO.phone"  maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>registryUserWebDTO.phone</s:param>
                               </s:fielderror>                            
                             </span>
                        </td>                
                  </tr>
                  <tr>
                        <td scope="row" class="label">
                            <label for="affiliateOrg"> <fmt:message key="register.user.affiliateOrg"/><span class="required">*</span></label>
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
