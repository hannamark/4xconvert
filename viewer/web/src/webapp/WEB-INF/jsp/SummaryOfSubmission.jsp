<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_summary_submission"/> 
<head>
<title><fmt:message key="summaryOfSubmission.header" /></title>
<s:head />
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");

function handleAction(){
    document.forms[0].action="resultsSummaryOfSubmission.action";
    document.forms[0].submit();
}
function handleReset(){
    document.forms[0].action="criteriaSummaryOfSubmission.action";
    document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="summaryOfSubmission.header"/></h1>
    <s:form name="criteria">
        <table class="form">
            <s:if test="hasActionErrors()"><tr><td colspan="2"><div class="error_msg"><s:actionerror /></div></td></tr></s:if> 
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="report.dates"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalStartDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal1')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalEndDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal2')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr><td class="space"><br/></td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.ctep"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:checkbox name="criteria.ctep" />
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
        <s:if test="%{resultList != null}">
            <table width="100%">
            <tr><td colspan="2">
                <h2><fmt:message key="summaryOfSubmission.header"/></h2>
            </td></tr>
            <tr>
                <td>
                    <fmt:message key="report.header.date1"/>
                    <s:label name="criteria.intervalStartDate"/>
                    <fmt:message key="report.header.date2"/>
                    <s:label name="criteria.intervalEndDate"/>
                </td>
                <td>
                    <fmt:message key="report.header.user"/>
                    <%=request.getRemoteUser()%>
                </td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
            <td colspan="2">
            <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                    requestURI="resultsSummaryOfSubmission.action" export="true">
                <display:setProperty name="export.excel" value="true" />
                <display:column titleKey="report.result.organization" property="organization"/>
                <display:column titleKey="report.result.initial" property="initial"/>
                <display:column titleKey="report.result.amendment" property="amendment"/>
                <display:column titleKey="report.result.total" property="total"/>
            </display:table>
            </td>
            </tr>
            </table>
        </s:if>
    </s:form>
</body>
