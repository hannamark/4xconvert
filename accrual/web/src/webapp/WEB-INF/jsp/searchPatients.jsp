<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="list_patients"/> 
<head>
    <title><fmt:message key="patient.search.title"/></title>   
    <s:head/>
<script LANGUAGE="JavaScript">
function handleSearch(){
    document.forms[0].action="patients.action";
    document.forms[0].submit();
}
function handleCreate(){
    document.forms[0].action="patientscreate.action";
    document.forms[0].submit();
}
function handleRetrieve(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="patientsretrieve.action";
    document.forms[0].submit();
}
function handleUpdate(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="patientsupdate.action";
    document.forms[0].submit();
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the subject from the study.  Cancel to abort.");
    if (input_box==true){
        document.forms[0].selectedRowIdentifier.value = rowId;
        document.forms[0].action="patientsdelete.action";
        document.forms[0].submit();
    }
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="patient.search.title"/></h1>
 <% 
           
    Boolean submissionOpened = (Boolean)session.getAttribute(gov.nih.nci.accrual.web.util.AccrualConstants.SESSION_ATTR_IS_SUBMISSION_OPENED); 
            
 %>
  <s:form name="listForm">
    <s:hidden name="selectedRowIdentifier"/>
    
    <table class="form">
    
   
      <tr>     
        <td class="label">
           <label for="Patient ID">
              <fmt:message key="patient.assignedIdentifier"/>
           </label>
         </td>
         <td class="value">
           <s:textfield id ="assignedIdentifier" name="criteria.assignedIdentifier" maxlength="400" size="50" 
                  cssStyle="width:98%;max-width:206px" />
         </td>
      </tr> 
      <tr>     
        <td class="label">
          <label for="Participating Site">
             <fmt:message key="patient.organizationName"/>
          </label>
         </td>
         <td class="value">
             <s:select id="organizationName" name="criteria.studySiteId" list="listOfStudySites" headerKey="" 
                       listKey="ssIi" listValue="orgName" headerValue="--Select--"/>
         </td>
      </tr> 
      <tr>
          <td class="label">
           <label for="Birth Date">
              <fmt:message key="patient.birthDate"/>
          </label>
          </td>
          <td colspan="4">
          <s:textfield id ="birthDate" name="criteria.birthDate" maxlength="400" size="50"
                cssStyle="width:98%;max-width:128px" />
          </td>
      </tr>
      <tr>
          <td class="label">
           <label for="Record Status">
              <fmt:message key="patient.statusCode"/>
          </label>
          </td>
          <td colspan="4">
           <s:select id ="statusCode" name="criteria.statusCode" 
                         headerKey=""
                         headerValue="--Select--"
                         list="#{'Pending':'Pending','Active':'Active'}"/>
          </td>
      </tr>
    </table>

    <div class="actionsrow">
        <del class="btnwrapper">
           <ul class="btnrow">
            <li>
            <s:a href="#" cssClass="btn" onclick="handleSearch()"><span class="btn_img"><span class="search">Search</span></span></s:a>
            <%
            if(submissionOpened.booleanValue()){
            	
            %>
            
            <s:a href="#" cssClass="btn" onclick="handleCreate()"><span class="btn_img"><span class="add">Add New Study Subject</span></span></s:a>
            
            <%} %>
            </li>
           </ul>
        </del>
    </div>
  </s:form>

   <div class="line"></div>
   <h1><fmt:message key="patient.list.header"/></h1>
   <accrual:sucessMessage /> 
   <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

   <display:table class="data" summary="This table contains your Study Subject search results.  Please use column headers to sort results" 
                  sort="list" pagesize="10" id="row" name="listOfPatients" requestURI="patients.action" export="false"> 
       <display:column titleKey="patient.assignedIdentifier"
         sortable="true" headerClass="sortable" headerScope="col">
            <s:a href="#" onclick="handleRetrieve(%{#attr.row.identifier})">
                   ${row.assignedIdentifier}
            </s:a>
       </display:column>
       <display:column titleKey="patient.registrationDate" property="registrationDate" 
         sortable="true" headerClass="sortable" headerScope="col"/> 
       <display:column titleKey="patient.organizationName" property="organizationName" 
         sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="patient.update" headerClass="centered" class="action">
           <%
            if(submissionOpened.booleanValue()){
                
            %>
            <s:a href="#" onclick="handleUpdate(%{#attr.row.identifier})">
                <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                    alt="Update" width="16" height="16" />
            </s:a>
             <%} %>
       </display:column>
       <display:column titleKey="patient.delete" headerClass="centered" class="action">
           <%
            if(submissionOpened.booleanValue()){
                
            %>
           <s:a href="#" onclick="handleDelete(%{#attr.row.identifier})">
               <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                   alt="Delete" width="16" height="16" />
           </s:a>
            <%} %>
       </display:column>
   </display:table>
</body>
</html>