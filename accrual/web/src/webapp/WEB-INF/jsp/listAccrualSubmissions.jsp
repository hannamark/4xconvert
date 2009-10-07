<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="accrual_submissions"/> 
<head>
<title><fmt:message key="accrual.list.accrual.submissions.page.header" /></title>
<s:head />
<script type="text/javascript">
    function accessAdd(rowId){
        input_box=confirm("Click OK to submit.  Cancel to Abort.   ");
        if (input_box==true){
            document.submissionForm.selectedRowIdentifier.value = rowId;
            document.submissionForm.action="accrualSubmissionssubmit.action";
            document.submissionForm.submit();
        }
    }

    function viewDetails(rowId){
            document.submissionForm.selectedRowIdentifier.value = rowId;
            document.submissionForm.action="accrualSubmissionsviewSubmissionDetails.action";
            document.submissionForm.submit();
     }
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<s:form name="submissionForm"><s:hidden name="selectedRowIdentifier"/> 
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="accrual.list.accrual.submissions.page.header"/> </h1>
    <accrual:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <display:table class="data" summary="This table contains list of Accrual Submissions.
          Please use column headers to sort results" decorator="gov.nih.nci.accrual.web.decorator.SubmissionDecorator"
          sort="list" pagesize="10" id="row" name="listOfSubmissions" requestURI="accrualSubmissions.action" export="false"> 

         <display:column titleKey="accrual.list.submissions.label" headerClass="centered" sortable="true" class="action">
             <s:a href="#" onclick="viewDetails(%{#attr.row.identifier.extension})">
                    ${row.label.value}
             </s:a>
        </display:column>
       <display:column titleKey="accrual.list.submissions.description" property="description" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.cutOffDate" property="cutOffDate" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.createdDate" property="createdDate" sortable="true" 
         headerClass="sortable" headerScope="col"/>
         <display:column titleKey="accrual.list.submissions.createUser" property="createUser" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submittedDate" property="submittedDate" sortable="true"
         headerClass="sortable" headerScope="col"/>
         <display:column titleKey="accrual.list.submissions.submitUser" property="submitUser" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.status" property="status" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submit" headerClass="centered" class="action">
                  <s:if test="%{#attr.row.statusCode.code=='Opened'}">
                      <s:a href="#" onclick="accessAdd(%{#attr.row.identifier.extension})">
                    <img src="<%=request.getContextPath()%>/images/ico_upload.gif" alt="Submit" width="16" height="16" />
                    </s:a>
                  </s:if>
        </display:column>
   </display:table>
</s:form>

<%
String overAllStat = "";
%>
   <s:iterator id="sub" value="listOfSubmissions" >  
     <s:set name="stat" value="%{statusCode.code}"/>
   
    <s:if test="%{#stat=='Opened'}">
      <%
       overAllStat = "Opened";
      %>
    </s:if>
   
   </s:iterator>
   
   <%
   if(!overAllStat.equals("Opened")){
   %>
     <div class="actionsrow">
       <del class="btnwrapper">
          <ul class="btnrow">
            <li>
              <s:a href="accrualSubmissionsdisplayNewSubmission.action" cssClass="btn">
                <span class="btn_img"><span class="add">Add New</span></span>
              </s:a>
            </li>
          </ul>
       </del>
    </div>
      
     <%} %>
</body>