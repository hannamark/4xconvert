<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://ctrp.nci.nih.gov/taglib/utility-functions" prefix="func" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<%@ tag display-name=" description="Displays the results for a cat of the summ4 report" body-content="empty"%>
<%@ attribute name="name" type="java.lang.Object" required="true" rtexprvalue="true"%>
<%@ attribute name="requestURI" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@ attribute name="uid" type="java.lang.String" required="true" rtexprvalue="true"%>

<display:table class="data" pagesize="20" uid="${uid}" name="${name}" requestURI="${requestURI}" export="true">
    <display:setProperty name="export.excel" value="true" />
    <viewer:displayTagProperties />
    <display:column titleKey="summary4Report.result.sponsor" property="sponsor" />
    <display:column titleKey="summary4Report.result.cancerSite" property="anatomicSites" />
    <display:column titleKey="summary4Report.result.nciIdentifier" property="nciIdentifier" />
    <display:column titleKey="summary4Report.result.nctIdentifier" property="nctIdentifier" />
    <display:column titleKey="summary4Report.result.ctepIdentifier" property="ctepIdentifier" />
    <display:column titleKey="summary4Report.result.protoId" property="protoId" />
    <display:column titleKey="summary4Report.result.pi" property="pi" />
    <display:column titleKey="summary4Report.result.leadOrgName" property="leadOrgName" />
    <display:column titleKey="summary4Report.result.programCode" property="programCode" />
    <display:column titleKey="summary4Report.result.openDate" format="{0,date,MM-dd-yyyy}" property="openDate" />
    <display:column titleKey="summary4Report.result.closedDate" format="{0,date,MM-dd-yyyy}" property="closedDate" />
    <display:column titleKey="summary4Report.result.phase" property="phase" />
    <display:column titleKey="summary4Report.result.type" property="type" />
    <display:column titleKey="summary4Report.result.title"  maxLength= "200" property="title" />
    <display:column titleKey="summary4Report.result.target" property="target" />
    <display:column titleKey="summary4Report.result.accrualCenterLO12m" property="accrualCenterLO12m" />
    <display:column titleKey="summary4Report.result.accrualCenterLOToDate" property="accrualCenterLOToDate" />
    <display:column titleKey="summary4Report.result.accrualCenterTS12m" property="accrualCenterTS12m" />
    <display:column titleKey="summary4Report.result.accrualCenterTSToDate" property="accrualCenterTSToDate" />
</display:table>