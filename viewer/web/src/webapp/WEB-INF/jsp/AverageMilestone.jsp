<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<c:set var="topic" scope="request" value="AverageMilestone"/> 
<head>
<title><fmt:message key="averageMilestone.header" /></title>
<s:head />
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");

function handleAction(){
    document.forms[0].action="resultsAverageMilestone.action";
    document.forms[0].submit();
}
function handleReset(){
    document.forms[0].action="criteriaAverageMilestone.action";
    document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="averageMilestone.header"/></h1>
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
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="averageMilestone.type"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="averageMilestone.criteria.submissionType"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value"><s:select
                    name="criteria.submissionType"
                    list="#{'ORIGINAL':'Original', 'AMENDMENT':'Amendment', 'BOTH':'Both'}" />
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
                <h2><fmt:message key="averageMilestone.header"/></h2>
            </td></tr>
            <tr>
                <td colspan="2">
                    <fmt:message key="report.header.date1"/>
                    <s:label name="criteria.intervalStartDate"/>
                    <fmt:message key="report.header.date2"/>
                    <s:label name="criteria.intervalEndDate"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <fmt:message key="averageMilestone.header.submissionType"/>
                    <s:label name="criteria.submissionType"/>
                </td>
            </tr>
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
            <tr><td><br/></td></tr>
            <tr>
            <td colspan="2">
            <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                    requestURI="resultsAverageMilestone.action" export="true">
                <display:setProperty name="export.excel" value="true" />
                <display:column titleKey="report.result.milestoneCode" property="milestoneCode"/>
                <display:column titleKey="averageMilestone.result.order" property="order"/>
                <display:column titleKey="averageMilestone.result.day01" property="day01"/>
                <display:column titleKey="averageMilestone.result.day02" property="day02"/>
                <display:column titleKey="averageMilestone.result.day03" property="day03"/>
                <display:column titleKey="averageMilestone.result.day04" property="day04"/>
                <display:column titleKey="averageMilestone.result.day05" property="day05"/>
                <display:column titleKey="averageMilestone.result.day06" property="day06"/>
                <display:column titleKey="averageMilestone.result.day07" property="day07"/>
                <display:column titleKey="averageMilestone.result.day08" property="day08"/>
                <display:column titleKey="averageMilestone.result.day09" property="day09"/>
                <display:column titleKey="averageMilestone.result.day10" property="day10"/>
                <display:column titleKey="averageMilestone.result.gtTenDays" property="gtTenDays"/>
                <display:column titleKey="averageMilestone.result.average" property="average"/>
                <display:column titleKey="averageMilestone.result.low" property="low"/>
                <display:column titleKey="averageMilestone.result.high" property="high"/>
            </display:table>
            </td>
            </tr>
            </table>
        </s:if>
    </s:form>
</body>
</html>