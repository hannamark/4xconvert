<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="list_accural_submissions"/> 
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />

<h1><fmt:message key="accrual.list.accrual.submissions.page.header"/> &nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<a align="right">New Accrual Submission</a></h1>
   <p>Click on the submission dates to view the details of the previous submissions.</p>
   
    <display:table class="data" summary="This table contains list of Accrual Submissions.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.web.decorator.AccrualDisplayTagDecorator"
      sort="list" pagesize="10" id="row" name="listOfSubmissions" requestURI="accrualSubmissions.action" export="false"> 
   
       <display:column titleKey="accrual.list.submissions.submissionDate" property="cutOffDate" sortable="true"
         href="accrualSubmissions.action" paramId="studyProtocolId" 
         paramProperty="studyProtocolIdentifier.extension" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submissionDescription" property="description" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submissionStatus" property="statusCode" sortable="true" 
       headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submissionStatusDate" property="statusDateRange" sortable="true" 
       headerClass="sortable" headerScope="col"/>
       
   </display:table>
   
</body>