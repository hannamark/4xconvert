<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialHistory.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"></script>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)

function callOnloadFunctions() {
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleEdit(rowId) {
    document.listForm.selectedRowIdentifier.value = rowId;
    document.listForm.action="trialHistoryedit.action";
    document.listForm.submit();
}

function handlePopup(a,b,c) { // write corresponding content to the popup window
    document.listForm.action="trialHistoryopen.action?studyProtocolii="+a+"&docii="+b+"&docFileName="+c;
    document.listForm.submit();
}

function acceptTrialUpdate(id) {
    input_remove_box = confirm("Do you want to accept this update and remove it from the list?");
    if (input_remove_box==true) {
        //document.forms[0].action="trialHistoryremove.action?studyInboxId="+id;
       // document.forms[0].submit();
        var div = document.getElementById('updates');
        var url = "/pa/protected/ajaxTrialHistoryacceptUpdate.action?studyInboxId="+id;
        var aj = new Ajax.Updater(div, url, {
           asynchronous: true,
           method: 'get',
           evalScripts: false
        });
    }
}

</SCRIPT>
</head>
<body>
    <h1><fmt:message key="trialHistory.title"/></h1>
    <c:set var="topic" scope="request" value="trialhistory"/>
    <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
    <div class="box">
        <pa:sucessMessage />
        <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
        <s:form name="listForm">
            <pa:studyUniqueToken/>
            <s:hidden name="selectedRowIdentifier"/>
            <h2><fmt:message key="trialHistory.title"/></h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <ul id="maintabs" class="tabs">
                            <li><a href="#submissions">Submissions</a></li>
                            <li><a href="#updates">Updates</a></li>
                        </ul>
                        <!--/Tabs -->
                        <!--
                            directions on http://livepipe.net/control/tabs
                            - make sure you add control.tabs.js to your scripts directory!
                            - Matt
                        -->
                        <script type="text/javascript">
                            //<![CDATA[
                            Event.observe(window,'load',function() {
                                $$('.tabs').each(function(tabs) {
                                    new Control.Tabs(tabs);
                                });
                            });
                            //]]>
                        </script>
                        <div id="tabboxwrapper">
                            <div id="submissions" class="box">
                                <s:set name="trialHistoryWebDTO" value="trialHistoryWebDTO" scope="request"/>
                                <display:table name="trialHistoryWebDTO" id="row" class="data" sort="list" defaultsort="1" defaultorder="ascending" pagesize="10" requestURI="trialHistory.action">
                                    <display:column escapeXml="true" property="submissionNumber" sortable="false" titleKey="trialHistory.submissionNumber" sortProperty="submissionNumberToSort"/>
                                    <display:column escapeXml="true" property="type" sortable="false" titleKey="trialHistory.type"/>
                                    <display:column escapeXml="true" property="amendmentNumber" sortable="false" titleKey="trialHistory.amendmentNumber"/>
                                    <display:column escapeXml="true" property="amendmentDate" sortable="false" titleKey="trialHistory.amendmentDate" />
                                    <display:column escapeXml="true" property="submissionDate" sortable="false" titleKey="trialHistory.submissionDate" />
                                    <display:column escapeXml="true" property="amendmentReasonCode" sortable="false" titleKey="trialHistory.amendmentReasonCode"/>
                                    <display:column escapeXml="false" property="documents" sortable="false" style="word-wrap: break-word"  titleKey="trialHistory.documents"/>
                                    <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                                                        || (sessionScope.role == 'SuAbstractor')}">
                                    <display:column title="Action" headerClass="centered" class="action">
                                    <s:if test="%{#attr.row.submissionNumber != 1}">
                                         <s:a href="#" onclick="handleEdit(%{#attr.row.identifier})">
                                            <img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16" />
                                        </s:a>
                                    </s:if>
                                    </display:column>
                                    </c:if>
                                 </display:table>
                            </div>
                            <div id="updates" class="box" style="display:none;">
                                <jsp:include page="/WEB-INF/jsp/displayTrialUpdates.jsp"/>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
</body>
</html>