<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="list_patients"/> 
<head>
    <title><fmt:message key="patient.search.title"/></title>   
    <s:head/>
<SCRIPT LANGUAGE="JavaScript">
function handleSearchAction(){
    document.forms[0].action="viewTrials.action";
    document.forms[0].submit();
}
function handleAddAction(){
    document.forms[0].action="viewTrials.action";
    document.forms[0].submit();
}
</SCRIPT>
</head>
<body>

<h1><fmt:message key="patient.search.title"/></h1>
  <s:form name="searchPatient">
    <table class="form">
      <tr>
        <td class="label">
          <s:label><fmt:message key="patient.assignedIdentifier"/></s:label>
        </td>
        <td class="value">
            <s:textfield id ="assignedIdentifier" name="criteria.assignedIdentifier" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
        </td>
      </tr> 
      <tr>
        <td class="label">
          <s:label><fmt:message key="patient.studySite"/></s:label>
        </td>
        <td class="value">
            <s:textfield id ="studySite" name="criteria.studySite" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
        </td>
      </tr> 
      <tr>
        <td class="label">
          <s:label><fmt:message key="patient.birthDate"/></s:label>
        </td>
        <td class="value">
            <s:textfield id ="birthDate" name="criteria.birthDate" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
        </td>
      </tr> 
      <tr>
        <td class="label">
          <s:label><fmt:message key="patient.statusCode"/></s:label>
        </td>
        <td class="value">
            <s:textfield id ="statusCode" name="criteria.statusCode" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
        </td>
      </tr> 
    </table>

    <div class="actionsrow">
        <del class="btnwrapper">
           <ul class="btnrow">
            <li>
            <s:a href="#" cssClass="btn" onclick="handleSearchAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="add">Add New Patient</span></span></s:a>
            </li>
           </ul>
        </del>
     </div>
   </s:form>

   <div class="line"></div>

   <c:if test="${listOfTrials != null}">
         <c:set var="topic" scope="request" value="search_trials"/>
          <jsp:include page="/WEB-INF/jsp/listTrials.jsp">
              <jsp:param name="listOfTrials" value="listOfTrials" />
          </jsp:include>
   </c:if>

</body>
</html>