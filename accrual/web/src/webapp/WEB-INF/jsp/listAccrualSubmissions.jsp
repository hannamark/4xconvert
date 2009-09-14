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
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.web.decorator.SubmissionDecorator"
      sort="list" pagesize="10" id="row" name="listOfSubmissions" requestURI="accrualSubmissions.action" export="false"> 
   
       <display:column titleKey="accrual.list.submissions.label" property="label" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.description" property="description" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.cutOffDate" property="cutOffDate" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.createdDate" property="createdDate" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submittedDate" property="submittedDate" sortable="true"
         href="accrualSubmissions.action" paramId="submissionId" 
         paramProperty="identifier" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.status" property="status" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.submissions.submit" headerClass="centeredsmall" class="action">
                <s:a href="#" onclick="callHandleSubmit(%{#attr.row.identifier})">
                    <img src="<%=request.getContextPath()%>/images/ico_upload.gif"
                        alt="Submit" width="16" height="16" />
                </s:a>
       </display:column>
   </display:table>
   
</body>