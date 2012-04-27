<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
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
        <c:set var="topic" scope="request" value="abstractmarkers"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <s:form name="diseaseForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                <h2><fmt:message key="plannedMarker.details.title"/></h2>
                <c:if test="${fn:length(requestScope.plannedMarkerList) > 5}">                
                <div class="actionstoprow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:url id="addUrl" namespace="/protected" action="plannedMarker" method="create"/>
                                    <s:a href="%{addUrl}" cssClass="btn">
                                        <span class="btn_img"><span class="add">Add</span></span>
                                    </s:a>
                                </li>
                                <s:if test="%{plannedMarkerList != null && !plannedMarkerList.isEmpty()}">
                                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarker!delete.action');" onkeypress="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarker!delete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                                    <li><pa:toggleDeleteBtn/></li>
                                </s:if>                                
                            </pa:scientificAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
                </c:if>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="plannedMarkerList" value="plannedMarkerList" scope="request"/>
                            <display:table name="plannedMarkerList" htmlId="plannedMarkerTable" id="row" class="data" sort="list" pagesize="200" requestURI="plannedMarker.action">
                                <display:column escapeXml="true" property="name" sortable="true" titleKey="plannedMarker.name" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayType" sortable="true" titleKey="plannedMarker.assayType" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayUse" sortable="true" titleKey="plannedMarker.assayUse" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayPurpose" sortable="true" titleKey="plannedMarker.assayPurpose" headerClass="sortable"/>
                                <display:column escapeXml="true" property="tissueCollectionMethod" sortable="true" titleKey="plannedMarker.tissueCollectionMethod" headerClass="sortable"/>
                                <display:column escapeXml="true" property="status" sortable="true" titleKey="plannedMarker.status" headerClass="sortable" />
                                <pa:scientificAbstractorDisplayWhenCheckedOut>
                                    <display:column titleKey="plannedMarker.edit" headerClass="centered" class="action">
                                        <s:url id="editUrl" namespace="/protected" action="plannedMarker" method="edit">
                                            <s:param name="selectedRowIdentifier" value="%{#attr.row.id}"/>
                                        </s:url>
                                        <s:a href="%{editUrl}">
                                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                                        </s:a>
                                    </display:column>
                                    <display:column titleKey="plannedMarker.delete" headerClass="centered" class="action">
                                        <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                                    </display:column>
                                </pa:scientificAbstractorDisplayWhenCheckedOut>
                            </display:table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:url id="addUrl" namespace="/protected" action="plannedMarker" method="create"/>
                                    <s:a href="%{addUrl}" cssClass="btn">
                                        <span class="btn_img"><span class="add">Add</span></span>
                                    </s:a>
                                </li>
		                        <s:if test="%{plannedMarkerList != null && !plannedMarkerList.isEmpty()}">
		                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarker!delete.action');" onkeypress="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarker!delete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
		                            <li><pa:toggleDeleteBtn/></li>
		                        </s:if>                                
                            </pa:scientificAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
