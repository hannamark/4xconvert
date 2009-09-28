<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="list_patients"/> 
<head>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "patient.registrationDate", "detailForm");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");
function handleCancelAction(){
    document.forms[0].action="patients.action";
    document.forms[0].submit();
}
function handleAddAction(){
    document.forms[0].action="patientsadd.action";
    document.forms[0].submit();
}
function handleEditAction(){
    document.forms[0].action="patientsedit.action";
    document.forms[0].submit();
}
</script>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="patient.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="patient.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'retrieve'}">
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
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">
    <tr>
        <td class="label">
          <label>
              <fmt:message key="patient.ID"/>
              <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="identifier" name="patient.identifier" maxlength="400" size="50" 
                  cssStyle="width:98%;max-width:206px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.assignedIdentifier" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr> 
    <tr>
        <td class="label">
          <label>
            <fmt:message key="patient.birthDate"/>
            <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="birthDate" name="patient.birthDate" maxlength="400" size="50"
                         cssStyle="width:98%;max-width:128px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.birthDate" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>
    <tr>
        <td class="label">
          <label>
            <fmt:message key="patient.gender"/>
            <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="genderCode" name="patient.genderCode" 
                      headerKey=""
                      headerValue="--Select--"
                      list="#{'Male':'Male','Female':'Female','Unknown':'Unknown'}"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.genderCode" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>
    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.race"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="raceCode" name="patient.raceCode"  
                      headerKey=""
                      headerValue="--Select--"
                      list="#{'American Indian or Alaska Native':'American Indian or Alaska Native'
                             ,'Asian':'Asian','Black or African American':'Black or African American'
                             ,'Native Hawaiian or Other Pacific Islander':'Native Hawaiian or Other Pacific Islander'
                             ,'Not Reported':'Not Reported','Unknown':'Unknown','White':'White'}"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.raceCode" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

     <tr>
        <td scope="row" class="label">
         <label>
            <fmt:message key="patient.ethnicity"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="ethnicCode" name="patient.ethnicCode"
                      headerKey=""
                      headerValue="--Select--"
                      list="#{'Hispanic or Latino':'Hispanic or Latino','Not Hispanic or Latino':'Not Hispanic or Latino'
                             ,'Not Reported':'Not Reported','Unknown':'Unknown'}"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.ethnicCode" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.country"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="countryIdentifier" name="patient.countryIdentifier" headerValue="-Select-" headerKey=""
                     list="listOfCountries"
                     listKey="id" listValue="name"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.countryIdentifier" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.zipCode"/>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="zip" name="patient.zip" maxlength="400" size="50" cssStyle="width:98%;max-width:96px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.zip" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.registrationDate"/>
        </label>
        </td>
        <td class="value">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="registrationDate" name="patient.registrationDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
            <a href="javascript:showCal('Cal1')"><img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.registrationDate" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.methodOfPayment"/>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="paymentMethodCode" name="patient.paymentMethodCode"
               headerKey=""
               headerValue="--Select--"
               list="#{'Private Insurance':'Private Insurance','Medicare':'Medicate'
                      ,'Medicare and Private Insurance':'Medicare and Private Insurance'
                      ,'Medicaid':'Medicaid','Medicaid and Medicare':'Medicaid and Medicare'
                      ,'Military or Veterans Sponsored, Not Otherwise Specified (NOS)':'Military or Veterans Sponsored, Not Otherwise Specified (NOS)'
                      ,'Military Sponsored (including CHAMPUS or TRICARE)':'Military Sponsored (including CHAMPUS or TRICARE)'
                      ,'Veterans Sponsored':'Veterans Sponsored','Self pay (no insurance)':'Self pay (no insurance)'
                      ,'No means of payment (no insurance)':'No means of payment (no insurance)'
                      ,'Other':'Other','Unknown':'Unknown'}"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.paymentMethodCode" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.disease"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="disease" name="patient.disease" maxlength="400" size="50" cssStyle="width:98%;max-width:206px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.disease" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="patient.organizationName"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
             <s:select id="organizationName" name="patient.organizationName" list="listOfStudySites" headerKey="" 
                       listKey="ssIi" listValue="orgName" headerValue="--Select--"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="patient.organizationName" cssStyle="font-weight:normal"/>
          </s:elseif>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
          <fmt:message key="patient.statusCode"/>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:label name="patient.statusCode" cssStyle="font-weight:normal"/>
        </td>
    </tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>
        <s:if test="%{currentAction == 'create'}">
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </s:if>
        <s:elseif test="%{currentAction == 'retrieve'}">
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="back">Back</span></span></s:a>
        </s:elseif>
        <s:elseif test="%{currentAction == 'update'}">
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </s:elseif>
       </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
