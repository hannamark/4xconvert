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
    <c:set var="topic" scope="request" value="register"/> 
    <div class="box" id="filters">
    <s:form name="registerUser" validate="true" method="POST" ><s:actionerror />
        <input type="hidden" name="page" />
        <c:choose>
            <c:when test="${param.resetPassword == true}">
                  <p>To reset your password, please begin by entering your login name and new password. </p>
            </c:when>
            <c:otherwise>
                  <p>To register for NCI Clinical Trials Reporting Portal, please begin by creating your login information. </p>
            </c:otherwise>
        </c:choose>
        <table class="form"> 
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
  
          <tr>
                <td scope="row" class="label">
                    <label for="emailAddress"> <fmt:message key="register.user.emailAddress"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="registryUserWebDTO.loginName"  maxlength="200" size="100"  cssStyle="width:200px" />
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
                    <s:password  name="registryUserWebDTO.password"  maxlength="200" size="100"  cssStyle="width:200px"  />
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
                    <s:password  name="registryUserWebDTO.retypePassword"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>registryUserWebDTO.retypePassword</s:param>
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
