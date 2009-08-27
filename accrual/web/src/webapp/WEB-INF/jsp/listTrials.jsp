<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="accrual.list.trials.page.title"/></title>   
    <s:head/>
<SCRIPT LANGUAGE="JavaScript">

function viewParticipationSite(pId) {       
    document.forms[0].action="participationSiteSelection.action?studyProtocolId="+pId;
    document.forms[0].submit(); 
}
</SCRIPT>
</head>
<c:set var="topic" scope="request" value="list_trials"/> 
<body>
 <s:form name="participationSiteSelection" action="participationSiteSelection">
<h1><fmt:message key="accrual.list.trials.page.header"/></h1>
   <p>Click on the Protocol Number to select the Trial Participation Site.</p>
   
   <display:table class="data" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.web.decorator.AccrualDisplayTagDecorator"
      sort="list" pagesize="10" id="row" name="listOfTrials" requestURI="viewTrials.action" export="false"> 
   
       <display:column titleKey="accrual.list.trials.protocolNumber" property="assignedIdentifier" sortable="true"
         href="participationSiteSelection.action" paramId="studyProtocolId" 
         paramProperty="studyProtocolIdentifier.extension" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.protocolTitle" property="officialTitle" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus"  sortable="true" headerClass="sortable"
         headerScope="col"/>
   </display:table>
</body>
</s:form>