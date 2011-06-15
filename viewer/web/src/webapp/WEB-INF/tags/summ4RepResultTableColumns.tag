<%@ tag display-name="summ4RepResult"  description="Displays the results for a cat of the summ4 report"  body-content="empty" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>
<%@ attribute name="table_uid" type="gov.nih.nci.pa.viewer.dto.result.Summ4ResultWebDto" required="true" rtexprvalue = "true" %>

<display:setProperty name="export.excel" value="true" />
<viewer:displayTagProperties/>
<display:column titleKey="report.result.sponsor" property="sponsor"/>
<display:column titleKey="report.result.cancerSite" media="html">
    <ol>
        <c:forEach var="anatomicSiteVal" items="${table_uid.anatomicSites}">
            <li>${anatomicSiteVal}</li>
        </c:forEach>
    </ol>
</display:column>
<display:column titleKey="report.result.cancerSite" media="excel">
        <c:forEach var="anatomicSiteVal" items="${table_uid.anatomicSites}" varStatus="status">
            ${anatomicSiteVal}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
</display:column>
<display:column titleKey="report.result.nciIdentifier" property="nciIdentifier"/>
<display:column titleKey="report.result.nctIdentifier" property="nctIdentifier"/>
<display:column titleKey="report.result.ctepIdentifier" property="ctepIdentifier"/>
<display:column titleKey="report.result.protoId" property="protoId"/>
<display:column titleKey="report.result.pi" property="pi"/>
<display:column titleKey="report.result.leadOrgName" property="leadOrgName"/>
<display:column titleKey="report.result.orgMember" property="orgMember"/>
<display:column titleKey="report.result.programCode" property="programCode"/>
<display:column titleKey="report.result.openDate" format="{0,date,dd-MM-yyyy}" property="openDate"/>
<display:column titleKey="report.result.closedDate" format="{0,date,dd-MM-yyyy}" property="closedDate"/>
<display:column titleKey="report.result.phase" property="phase"/>
<display:column titleKey="report.result.type" property="type"/>
<display:column titleKey="report.result.title" property="title"/>
<display:column titleKey="report.result.target" property="target"/>
<display:column titleKey="report.result.accrualCenter12m" property="accrualCenter12m"/>
<display:column titleKey="report.result.accrualCenterToDate" property="accrualCenterToDate"/>
