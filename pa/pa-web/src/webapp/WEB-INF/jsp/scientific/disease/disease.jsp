<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="disease.details.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <c:url value="/protected/popupDisdisplayDiseaseWidget.action" var="lookupUrl" />
        
        <script language="javascript" type="text/javascript">
            function handleView(diseaseId) {
                var url = "<%=request.getContextPath()%>/protected/popupDiseaseDetails.action?diseaseId=" + diseaseId;
                showPopup(url, null, 'Disease');
            }
            
            function handleEdit(rowId) {
                var form = document.diseaseForm;
                form.selectedRowIdentifier.value = rowId;
                form.action = "diseaseedit.action";
                form.submit();
            }
            
            function handleCreate() {
            	showPopWin('${lookupUrl}', 985,500,refresh,'Diseases');
            }
            
            function refresh() {
                var form = document.diseaseForm;
                form.action = "disease.action";
                form.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="disease.details.title"/></h1>
        <c:set var="topic" scope="request" value="abstractdisease"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <pa:failureMessage/>
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <s:form name="diseaseForm">
                <s:token/>
                <s:hidden name="selectedRowIdentifier"/>
                <h2>
                    <fmt:message key="disease.details.title"/>
                </h2>
                <c:if test="${fn:length(requestScope.diseaseList) > 5}">
                <div class="actionstoprow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();">
                                        <span class="btn_img"><span class="add">Add </span></span>
                                    </a>
                                </li>
                                <s:if test="%{diseaseList != null && !diseaseList.isEmpty()}">
                                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected disease(s) from the study. Cancel to abort.', 'diseasedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected disease(s) from the study. Cancel to abort.', 'diseasedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
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
                            <s:set name="diseaseList" value="diseaseList" scope="request"/>
                            <display:table name="diseaseList" id="row" class="data" sort="list" pagesize="200" requestURI="disease.action">
                                <display:column escapeXml="true" property="preferredName" sortable="true"
                                                titleKey="disease.preferredName" headerClass="sortable"/>
                                <display:column titleKey="disease.view" headerClass="centered" class="action">
                                    <s:a href="javascript:void(0)" onclick="handleView(%{#attr.row.diseaseIdentifier})">
                                        <img src="<c:url value='/images/ico_search.gif'/>" alt="View" width="16" height="16" />
                                    </s:a>
                                </display:column>
                                <display:column escapeXml="true" property="code" sortable="true"
                                                titleKey="disease.code" headerClass="sortable" />
                                <display:column escapeXml="true" property="conceptId" sortable="true"
                                                titleKey="disease.conceptId" headerClass="sortable" />
                                <display:column escapeXml="true" property="menuDisplayName" sortable="true"
                                                titleKey="disease.menuDisplayName" headerClass="sortable" />
                                <display:column escapeXml="true" property="parentPreferredName" sortable="true"
                                                titleKey="disease.parentPreferredName" headerClass="sortable" />
                                <display:column escapeXml="true" property="ctGovXmlIndicator" sortable="true" 
                                                titleKey="disease.includeInXML" headerClass="sortable"/>
                                <pa:scientificAbstractorDisplayWhenCheckedOut>
                                    <display:column titleKey="disease.edit" headerClass="centered" class="action">
                                        <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.studyDiseaseIdentifier})">
                                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                                        </s:a>
                                    </display:column>
                                    <display:column titleKey="disease.delete" headerClass="centered" class="action">
                                        <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.studyDiseaseIdentifier}" value="%{#attr.row.studyDiseaseIdentifier in objectsToDelete}"/>
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
                                    <a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();">
                                        <span class="btn_img"><span class="add">Add </span></span>
                                    </a>
                                </li>
		                        <s:if test="%{diseaseList != null && !diseaseList.isEmpty()}">
		                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected disease(s) from the study. Cancel to abort.', 'diseasedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected disease(s) from the study. Cancel to abort.', 'diseasedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
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