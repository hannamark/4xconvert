<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<html lang="en">
<c:set var="topic" scope="request" value="accrualcounts"/>
<head>
    <title><fmt:message key="accrual.counts.title"/></title>
    <s:head/>
</head>
<body>

<script>
$(function()
        {
            var export1 = document.getElementById('export');
            if(export1 != null)
            {
                var export2 = export1.cloneNode(true);
                var datagrid = document.getElementById('datagrid');
                datagrid.insertBefore(export2, datagrid.firstChild);                
            }
        });
</script>

 <div class="container">
    <h1 class="heading"><span><fmt:message key="accrual.counts.title"/></span></h1>
    <div id="datagrid">
  <display:table class="table table-striped sortable" summary="This table contains list of counts.  Please use column headers to sort results"
                  sort="list" pagesize="10" id="row" name="displayTagList" requestURI="accrualCounts.action" export="true">                                             
       <display:setProperty name="export.xml" value="false"/>
       <display:setProperty name="export.excel.filename" value="resultsAccrualCounts.xls"/>
       <display:setProperty name="export.excel.include_header" value="true"/>
       <display:setProperty name="export.csv.filename" value="resultsAccrualCounts.csv"/>
       <display:setProperty name="export.csv.include_header" value="true"/>
       <display:column titleKey="accrual.counts.nciNumber" property="nciNumber" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.lead.organization.trialID" property="leadOrgTrialIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.nctTrialNumber" property="nctNumber" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.lead.organization" property="leadOrgName" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.site" property="affiliateOrgCount" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.trials" property="trialCount" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.counts.date" property="date" format="{0,date,yyyy-MM-dd HH.mm.ss}" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table> 
   </div> 
   </div>  
</body>
</html>