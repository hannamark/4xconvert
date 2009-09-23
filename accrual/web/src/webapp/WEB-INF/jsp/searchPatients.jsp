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
    input_box=confirm("Click OK to remove the patient from the study.  Cancel to abort.");
    if (input_box==true){
        document.forms[0].selectedRowIdentifier.value = rowId;
        document.forms[0].action="patientsdelete.action";
        document.forms[0].submit();
    }
}
</SCRIPT>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1><fmt:message key="patient.search.title"/></h1>
  <s:form name="listForm">
    <s:hidden name="selectedRowIdentifier"/>
    <table class="form">
    </table>

    <div class="actionsrow">
        <del class="btnwrapper">
           <ul class="btnrow">
            <li>
            <s:a href="#" cssClass="btn" onclick="handleSearch()"><span class="btn_img"><span class="search">Search</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCreate()"><span class="btn_img"><span class="add">Add New Patient</span></span></s:a>
            </li>
           </ul>
        </del>
    </div>
  </s:form>

   <div class="line"></div>

   <h1><fmt:message key="patient.list.header"/></h1>
   <display:table class="data" summary="This table contains your patient search results.  Please use column headers to sort results" 
                  sort="list" pagesize="10" id="row" name="listOfPatients" requestURI="patients.action" export="false"> 
       <display:column titleKey="patient.assignedIdentifier" property="assignedIdentifier"
         sortable="true" headerClass="sortable" headerScope="col"
         href="patientsretrieve.action" paramId="studySubjectId" paramProperty="assignedIdentifier"/> 
<%--
       <display:column titleKey="patient.registrationDate" property="registrationDate" 
         sortable="true" headerClass="sortable" headerScope="col"/> 
--%>
       <display:column titleKey="patient.organizationName" property="organizationName" 
         sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="patient.update" headerClass="centered" class="action">
            <s:a href="#" onclick="handleUpdate(%{#attr.row.identifier})">
                <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                    alt="Update" width="16" height="16" />
            </s:a>
       </display:column>
       <display:column titleKey="patient.delete" headerClass="centered" class="action">
           <s:a href="#" onclick="handleDelete(%{#attr.row.identifier})">
               <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                   alt="Delete" width="16" height="16" />
           </s:a>
       </display:column>
   </display:table>
</body>
</html>