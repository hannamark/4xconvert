<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript">
             function callOnloadFunctions(){
                // there are no onload functions to call for this jsp
                // leave this function to prevent 'error on page'
             }
        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title"/></h1>
        <c:set var="topic" scope="request" value="planned_marker"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage/>
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <s:form name="diseaseForm">
                <s:hidden name="selectedRowIdentifier"/>
                <h2><fmt:message key="plannedMarker.details.title"/></h2>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="plannedMarkerList" value="plannedMarkerList" scope="request"/>
                            <display:table name="plannedMarkerList" id="row" class="data" sort="list" pagesize="200" requestURI="plannedMarker.action">
                                <display:column escapeXml="true" property="name" sortable="true" titleKey="plannedMarker.name" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayType" sortable="true" titleKey="plannedMarker.assayType" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayUse" sortable="true" titleKey="plannedMarker.assayUse" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayPurpose" sortable="true" titleKey="plannedMarker.assayPurpose" headerClass="sortable"/>
                                <display:column escapeXml="true" property="tissueCollectionMethod" sortable="true" titleKey="plannedMarker.tissueCollectionMethod" headerClass="sortable"/>
                                <display:column escapeXml="true" property="status" sortable="true" titleKey="plannedMarker.status" headerClass="sortable" />
                                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null 
                                    && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy) 
                                    || (sessionScope.role == 'SuAbstractor')}">
                                    <display:column titleKey="plannedMarker.edit" headerClass="centered" class="action">
                                        <s:url id="editUrl" namespace="/protected" action="plannedMarker" method="edit">
                                            <s:param name="selectedRowIdentifier" value="%{#attr.row.id}"/>
                                        </s:url>
                                        <s:a href="%{editUrl}">
                                            <img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16" />
                                        </s:a>
                                    </display:column>
                                    <display:column titleKey="plannedMarker.delete" headerClass="centered" class="action">
                                        <s:url id="deleteUrl" namespace="/protected" action="plannedMarker" method="delete">
                                            <s:param name="selectedRowIdentifier" value="%{#attr.row.id}"/>
                                        </s:url>
                                        <s:a href="%{deleteUrl}" onclick="return confirm('Click OK to remove the marker from the study. Cancel to abort.');">
                                            <img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16" />
                                        </s:a>
                                    </display:column>
                                </c:if>
                            </display:table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null 
                                && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                                || (sessionScope.role == 'SuAbstractor')}">
                                <li>
                                    <s:url id="addUrl" namespace="/protected" action="plannedMarker" method="create"/>
                                    <s:a href="%{addUrl}" cssClass="btn">
                                        <span class="btn_img"><span class="add">Add</span></span>
                                    </s:a>
                                </li>
                            </c:if>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>