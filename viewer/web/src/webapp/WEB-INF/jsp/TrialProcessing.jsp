<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<c:set var="topic" scope="request" value="TrialProcessing"/> 
<head>
<title><fmt:message key="trialProcessing.header" /></title>
<s:head />
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");

function handleAction(){
    document.forms[0].action="resultsTrialProcessing.action";
    document.forms[0].submit();
}
function handleReset(){
    document.forms[0].action="criteriaTrialProcessing.action";
    document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="trialProcessing.header"/></h1>
    <s:form name="criteria">
        <table class="form">    
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="trialProcessing.select"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="trialProcessing.criteria.assignedIdentifier"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="criteria.assignedIdentifier" maxlength="14" size="14" cssStyle="width:128px;float:left"/>
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Run report</span></span></s:a>  
                    </li>
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleReset()"><span class="btn_img"><span class="back">Reset</span></span></s:a>  
                    </li>
                </ul>   
            </del>
        </div>
        <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if> 
        <s:if test="%{header != null}">
            <table width="100%">
            <tr><td colspan="2">
                <h2><fmt:message key="trialProcessing.header"/></h2>
            </td></tr>
            <tr>
                <td>
                    <fmt:message key="report.header.user"/>
                    <%=request.getRemoteUser()%>
                </td>
                <td>
                    <fmt:message key="report.header.date"/>
                    <%=gov.nih.nci.pa.util.PAUtil.today()%>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <fmt:message key="trialProcessing.header.officialTitle"/>
                    <s:label name="header.officialTitle"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="trialProcessing.header.assignedIdentifier"/>
                    <s:label name="header.assignedIdentifier"/>
                </td>
                <td>
                    <fmt:message key="trialProcessing.header.leadOrganization"/>
                    <s:label name="header.leadOrganization"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="trialProcessing.header.statusCode"/>
                    <s:label name="header.statusCode"/>
                </td>
                <td>
                    <fmt:message key="trialProcessing.header.userLastCreated"/>
                    <s:label name="header.userLastCreated"/>
                </td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
            <td colspan="2">
            <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                    requestURI="resultsTrialProcessing.action" export="true">
                <display:setProperty name="export.excel" value="true" />
                <display:column titleKey="report.result.submissionNumber" property="submissionNumber"/>
                <display:column titleKey="trialProcessing.result.statusCode" property="milestoneCode"/>
                <display:column titleKey="trialProcessing.result.milestoneDays" property="milestoneDays"/>
                <display:column titleKey="trialProcessing.result.cumulativeDays" property="cumulativeDays"/>
            </display:table>
            </td>
            </tr>
            </table>
        </s:if>
    </s:form>
</body>
</html>