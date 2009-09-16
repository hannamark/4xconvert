<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<h1><fmt:message key="accrual.list.trials.page.header"/></h1>
   <display:table class="data" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.web.decorator.SearchTrialResultDecorator"
      sort="list" pagesize="10" id="row" name="listOfTrials" requestURI="viewTrials.action" export="false"> 
   
       <display:column titleKey="accrual.list.trials.protocolNumber" property="assignedIdentifier" sortable="true"
         href="accrualSubmissions.action" paramId="studyProtocolId" 
         paramProperty="studyProtocolIdentifier.extension" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.protocolTitle" property="officialTitle" sortable="true" 
         headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus" property="studyStatusCode" sortable="true" headerClass="sortable"
         headerScope="col"/>
   </display:table>
