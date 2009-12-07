<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/protected/popup.action" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "participant.registrationDate", "detailForm");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");
function handleCancelAction(){
    document.forms[0].action="executeParticipants.action";
    document.forms[0].submit();
}
function handleAddAction(){
    document.forms[0].action="addParticipants.action";
    document.forms[0].submit();
}
function handleEditAction(){
    document.forms[0].action="editParticipants.action";
    document.forms[0].submit();
}
</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="add_patient"/> 
        <fmt:message key="participant.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="update_patient"/> 
        <fmt:message key="participant.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <c:set var="topic" scope="request" value="view_patient"/> 
        <fmt:message key="participant.update.title" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    <s:if test="%{currentAction == 'create'}">
        <fmt:message key="participant.create.title" /></s:if>
    <s:elseif test="%{currentAction == 'retrieve'}">
        <fmt:message key="participant.retrieve.title" /></s:elseif>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="participant.update.title" /></s:elseif>
    <s:head/>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
    <s:hidden name = "currentAction"/>
    <s:hidden name = "participant.patientId" />
    <s:hidden name = "participant.studySubjectIi" />
    <s:hidden name = "participant.statusCode" />
    <s:hidden name = "participant.poIdentifier" />
    <s:hidden name = "participant.studyProtocolIi" />
<table class="form">
    <tr>
        <td class="label">
          <label>
              <fmt:message key="participant.assignedIdentifier"/>
              <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="identifier" name="participant.assignedIdentifier" maxlength="400" size="50" 
                  cssStyle="width:98%;max-width:206px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.assignedIdentifier" cssStyle="font-weight:normal"/>
          </s:elseif>
           <s:fielderror cssClass="formErrorMsg"><s:param>participant.assignedIdentifier</s:param></s:fielderror>
        </td>
    </tr> 
    <tr>
        <td class="label">
          <label>
            <fmt:message key="participant.birthDate"/>
            <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:textfield id ="birthDate" name="participant.birthDate" maxlength="400" size="50"
                         cssStyle="width:98%;max-width:128px" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.birthDate" cssStyle="font-weight:normal"/>
          </s:elseif>
           <s:fielderror cssClass="formErrorMsg"><s:param>participant.birthDate</s:param></s:fielderror>
        </td>
    </tr>
    <tr>
        <td class="label">
          <label>
            <fmt:message key="participant.gender"/>
            <span class="required">*</span>
          </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:set name="genderCodeValues" value="@gov.nih.nci.pa.enums.PatientGenderCode@getDisplayNames()" />
            <s:select id ="genderCode" name="participant.genderCode.code" headerKey="" headerValue="--Select--"
                      list="#genderCodeValues"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.genderCode.code" cssStyle="font-weight:normal"/>
          </s:elseif>
          <s:fielderror cssClass="formErrorMsg"><s:param>participant.genderCode</s:param></s:fielderror>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="participant.race"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:set name="raceCodeValues" value="@gov.nih.nci.pa.enums.PatientRaceCode@getDisplayMap()" />
            <s:select id ="raceCode" name="participant.raceCode" value="%{participant.raceCode}" multiple="true" size="7" list="#raceCodeValues" />
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:iterator id="races" value="participant.raceCode" status="stat" >
                <s:set name="racerx" value="%{code}"/>
                <s:label name="races" cssStyle="font-weight:normal"/><br>
            </s:iterator>
          </s:elseif>          
        </td>
    </tr>

     <tr>
        <td scope="row" class="label">
         <label>
            <fmt:message key="participant.ethnicity"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:set name="ethnicCodeValues" value="@gov.nih.nci.pa.enums.PatientEthnicityCode@getDisplayMap()" />
            <s:select id ="ethnicCode" name="participant.ethnicCode.code" headerKey="" headerValue="--Select--"
                      list="#ethnicCodeValues"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.ethnicCode.code" cssStyle="font-weight:normal"/>
          </s:elseif>
          <s:fielderror cssClass="formErrorMsg"><s:param>participant.ethnicCode</s:param></s:fielderror>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="participant.country"/>
            <span class="required">*</span>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:select id ="countryIdentifier" name="participant.countryIdentifier" headerValue="-Select-" headerKey=""
                     list="listOfCountries"  value="participant.countryIdentifier.extension"
                     listKey="id" listValue="name"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.countryName" cssStyle="font-weight:normal"/>
          </s:elseif>
          <s:fielderror cssClass="formErrorMsg"><s:param>participant.countryIdentifier</s:param></s:fielderror>
        </td>
    </tr>

    <tr>
        <td class="label">
         <label>
            <fmt:message key="participant.methodOfPayment"/>
        </label>
        </td>
        <td class="value" colspan="4">
          <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
            <s:set name="paymentCodeValues" value="@gov.nih.nci.pa.enums.PaymentMethodCode@getDisplayNames()" />
            <s:select id ="paymentMethodCode" name="participant.paymentMethodCode.code" headerKey="" headerValue="--Select--"
               list="#paymentCodeValues"/>
          </s:if>
          <s:elseif test="%{currentAction == 'retrieve'}">
            <s:label name="participant.paymentMethodCode.code" cssStyle="font-weight:normal"/>
          </s:elseif>
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
