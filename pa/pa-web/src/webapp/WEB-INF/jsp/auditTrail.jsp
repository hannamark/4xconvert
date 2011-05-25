<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="auditTrail.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    </head>
    <body>
        <h1><fmt:message key="auditTrail.title"/></h1>
        <c:set var="topic" scope="request" value="auditTrail"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />        
        
        <div class="box">
            <table class="form">
                <s:form action="auditTrailview.action" id="auditTrailForm">
                    <tr>
                        <td style="width: 250px">
                            <s:set name="auditTrailOptions" value="@gov.nih.nci.pa.util.AuditTrailCode@getSortedValues()" />
                            <s:select list="auditTrailOptions" name="auditTrailCode" listValue="name"/>
                        </td>
                        <td>
                            <ul style="margin-top: -6px;">
                                <li style="padding-left: 0">
                                    <s:a href="#" cssClass="btn" onclick="$('auditTrailForm').submit();">
                                        <span class="btn_img"><span class="confirm">View Audit Trail</span></span>
                                    </s:a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </s:form>
            </table>
        </div>
        <div class="box">
            <s:set name="auditTrail" value="auditTrail" scope="request"/>
            <c:if test="${auditTrail != null}">
                <display:table class="data" id="row" name="auditTrail" export="true" sort="list"  pagesize="20" requestURI="auditTrailview.action"
                    decorator="gov.nih.nci.pa.decorator.AuditTrailTagDecorator">
                    <pa:displayTagProperties/>
                    <display:column property="changeDate" escapeXml="true" titleKey="auditTrail.changeDate" sortable="true" headerClass="sortable" />
                    <display:column property="userName" escapeXml="true" titleKey="auditTrail.userName" sortable="true" headerClass="sortable" />
                    <display:column escapeXml="true" titleKey="auditTrail.dataElement" sortable="true" headerClass="sortable" >
                        <fmt:message key="auditTrail.${row.attribute}" bundle="${auditTrailResources}"/>
                    </display:column>
                    <display:column property="oldValue" escapeXml="true" titleKey="auditTrail.oldValue" sortable="true" headerClass="sortable" />
                    <display:column property="newValue" escapeXml="true" titleKey="auditTrail.newValue" sortable="true" headerClass="sortable" />
                    <display:column property="record.type" escapeXml="true" titleKey="auditTrail.changeType" sortable="true" headerClass="sortable" />
                </display:table>
            </c:if>
        </div>
    </body>
</html>