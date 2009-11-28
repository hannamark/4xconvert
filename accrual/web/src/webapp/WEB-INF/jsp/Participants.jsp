<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="subjects_intro"/> 
<head>
    <title><fmt:message key="participant.search.title"/></title>   
    <s:head/>
<script LANGUAGE="JavaScript">
function handleSearch(){
    document.forms[0].action="executeParticipants.action";
    document.forms[0].submit();
}
function handleCreate(){
    document.forms[0].action="createParticipants.action";
    document.forms[0].submit();
}
function handleRetrieve(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="retrieveParticipants.action";
    document.forms[0].submit();
}
function handleUpdate(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="updateParticipants.action";
    document.forms[0].submit();
}
function handleDelete(rowId){
    input_box=confirm("Click OK to remove the patient from the study.  Cancel to abort.");
    if (input_box==true){
        document.forms[0].selectedRowIdentifier.value = rowId;
        document.forms[0].action="deleteParticipants.action";
        document.forms[0].submit();
    }
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="participant.search.title"/></h1>
    <s:form name="listForm">
    <s:hidden name="selectedRowIdentifier"/>
    
    <table class="form">
       <tr>     
        <td class="label">
           <label for="Participant ID">
              <fmt:message key="participant.assignedIdentifier"/>
           </label>
         </td>
         <td class="value">
           <s:textfield id ="assignedIdentifier" name="criteria.assignedIdentifier" maxlength="400" size="50" 
                  cssStyle="width:98%;max-width:206px" />
         </td>
      </tr> 
      <tr>
          <td class="label">
           <label for="Birth Date">
              <fmt:message key="participant.birthDate"/>
          </label>
          </td>
          <td colspan="4">
          <s:textfield id ="birthDate" name="criteria.birthDate" maxlength="400" size="50"
                cssStyle="width:98%;max-width:128px" />
          </td>
      </tr>
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
   <h1><fmt:message key="participant.list.header"/></h1>
   <accrual:sucessMessage /> 
   <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

   <display:table class="data" summary="This table contains your Study Subject search results.  Please use column headers to sort results" 
                  sort="list" pagesize="10" id="row" name="displayTagList" requestURI="executeParticipants.action" export="false"> 
       <display:column titleKey="participant.assignedIdentifierColumnHeader"
         sortable="true" headerClass="sortable" headerScope="col">
            <s:a href="#" onclick="handleRetrieve(%{#attr.row.identifier})">
                   ${row.assignedIdentifier.value}
            </s:a>
       </display:column>
       <display:column titleKey="participant.update" headerClass="centered" class="action">
            <s:a href="#" onclick="handleUpdate(%{#attr.row.identifier})">
                <img src="<%=request.getContextPath()%>/images/ico_edit.gif"
                    alt="Update" width="16" height="16" />
            </s:a>
       </display:column>
       <display:column titleKey="participant.delete" headerClass="centered" class="action">
           <s:a href="#" onclick="handleDelete(%{#attr.row.identifier})">
               <img src="<%=request.getContextPath()%>/images/ico_delete.gif"
                   alt="Delete" width="16" height="16" />
           </s:a>
       </display:column>
   </display:table>
</body>
</html>