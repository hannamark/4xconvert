<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="list_patients"/> 
<SCRIPT LANGUAGE="JavaScript">
function handleSearchAction(){
    document.forms[0].action="patients.action";
    document.forms[0].submit();
}
function handleAddAction(){
    document.forms[0].action="patientsadd.action";
    document.forms[0].submit();
}
</SCRIPT>
<head>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="patient.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="patient.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="patient.update.title" /></s:elseif>
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="patient.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="patient.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="patient.update.title" /></s:elseif>
    <s:head/>
</h1>
<s:form name="listForm">
<table class="form">
    
    <tr>     
        <td scope="row" class="label">
          <label for="Study Subject ID">
              <fmt:message key="patient.ID"/>                
          </label>
         </td>
         <td class="value">
            <s:textfield id ="identifier" name="patient.identifier" maxlength="400" size="50" 
                  cssStyle="width:98%;max-width:206px" />
           </td>
      </tr> 
            <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Birth Date">
                    <fmt:message key="patient.birthDate"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="birthDate" name="patient.birthDate" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
             <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Gender">
                    <fmt:message key="patient.birthDate"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="birthDate" name="criteria.birthDate" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
             <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Race">
                    <fmt:message key="patient.race"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="raceCode" name="patient.raceCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
             <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Ethnicity">
                    <fmt:message key="patient.ethnicity"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="ethnicCode" name="patient.ethnicCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Country">
                    <fmt:message key="patient.country"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="ethnicCode" name="patient.ethnicCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Study Subject Zip Code">
                    <fmt:message key="patient.zipCode"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="ethnicCode" name="patient.ethnicCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Registration Date">
                    <fmt:message key="patient.registrationDate"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="registrationDate" name="patient.registrationDate" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Study Subject method of payment">
                    <fmt:message key="patient.methodOfPayment"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="paymentMethodCode" name="patient.paymentMethodCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Registering Institution/Group Name">
                    <fmt:message key="patient.regInstitution"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="ethnicCode" name="patient.ethnicCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            
            <tr>
                <td scope="row" class="label">
                 <label for="Disease">
                    <fmt:message key="patient.disease"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="ethnicCode" name="patient.ethnicCode" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            <tr>
                <td scope="row" class="label">
                 <label for="Participating Site">
                    <fmt:message key="patient.organizationName"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="organizationName" name="patient.organizationName" maxlength="400" size="50"
                      cssStyle="width:98%;max-width:206px" />
                </td>
            </tr>
            
            
            <tr>
                <td scope="row" class="label">
                 <label for="Record Status">
                    <fmt:message key="patient.statusCode"/>                
                </label>
                </td>
                <td colspan="4">
                                      
                 <s:select id ="statusCode" name="patient.statusCode" 
                               headerKey="1"
                               headerValue="--Select--"
                               list="#{'Pending':'Pending','Active':'Active'}" 
                               cssStyle="width:206px" />
                 
                 
                </td>
            </tr>
    
            
           
    </table>

         <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">
                <li>
                <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="cancel()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                </li>
               </ul>
            </del>
         </div>
    
</s:form>

<div class="line"></div>
</body>
</html>
