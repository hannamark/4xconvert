<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://ctrp.nci.nih.gov/taglib/utility-functions" prefix="func" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<%@ tag display-name="summ4RepResultTable" description="Displays the results for a cat of the summ4 report" body-content="empty"%>
<%@ attribute name="name" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="requestURI" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="uid" type="java.lang.String" required="true" rtexprvalue="true"%>

<display:table class="data" pagesize="20" uid="${uid}" name="${name}" requestURI="${requestURI}" export="true">
    <display:setProperty name="export.excel" value="true" />
    <viewer:displayTagProperties />
    <display:column titleKey="report.result.sponsor" property="sponsor" />
    <display:column titleKey="report.result.cancerSite" property="anatomicSites" />
    <display:column titleKey="report.result.nciIdentifier" property="nciIdentifier" />
    <display:column titleKey="report.result.nctIdentifier" property="nctIdentifier" />
    <display:column titleKey="report.result.ctepIdentifier" property="ctepIdentifier" />
    <display:column titleKey="report.result.protoId" property="protoId" />
    <display:column titleKey="report.result.pi" property="pi" />
    <display:column titleKey="report.result.leadOrgName" property="leadOrgName" />
    <display:column titleKey="report.result.programCode" property="programCode" />
    <display:column titleKey="report.result.openDate" format="{0,date,MM-dd-yyyy}" property="openDate" />
    <display:column titleKey="report.result.closedDate" format="{0,date,MM-dd-yyyy}" property="closedDate" />
    <display:column titleKey="report.result.phase" property="phase" />
    <display:column titleKey="report.result.type" property="type" />
    <display:column titleKey="report.result.title"  maxLength= "200" property="title" />
    <display:column titleKey="report.result.target" property="target" />
    <display:column titleKey="report.result.accrualCenterLO12m" property="accrualCenterLO12m" />
    <display:column titleKey="report.result.accrualCenterLOToDate" property="accrualCenterLOToDate" />
    <display:column titleKey="report.result.accrualCenterTS12m" property="accrualCenterTS12m" />
    <display:column titleKey="report.result.accrualCenterTSToDate" property="accrualCenterTSToDate" />
</display:table>