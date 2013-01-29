<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="accrualcounts"/>
<head>
    <title><fmt:message key="accrual.counts.title"/></title>
    <s:head/>
</head>
<body>
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="accrual.counts.title"/></h1>
  <display:table class="data" summary="This table contains list of counts.  Please use column headers to sort results"
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
</body>
</html>