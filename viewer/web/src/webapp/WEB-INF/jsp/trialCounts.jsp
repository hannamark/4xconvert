<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<c:set var="topic" scope="request" value="trial_counts"/> 
<head>
<title><fmt:message key="trialCounts.header" /></title>
<s:head />
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
    addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
    addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");

    function handleAction(){
        document.forms[0].action="trialCountsgetReport.action";
        document.forms[0].submit();
    }
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="trialCounts.header"/></h1>
    <s:form name="criteria">
        <table class="form">    
            <tr> 
                <td class="label">
                    <label><fmt:message key="trialCounts.criteria.ctrpOnly"/></label>
                </td>
                <td class="value">
                    <s:checkbox name="criteria.ctrpOnly" />
                </td>
            </tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="trialCounts.criteria.groupByTimeUnit"/></label>
                </td>
                <td class="value"><s:select
                    name="criteria.groupByTimeUnit"
                    headerKey="-1" headerValue=""
                    list="#{'DAY':'Day', 'MONTH':'Month', 'YEAR':'Year'}"/>
                </td>
            </tr>
            <tr> 
                <td scope="row" class="label">
                    <label><fmt:message key="trialCounts.criteria.intervalStartDate"/></label>
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
                    <label><fmt:message key="trialCounts.criteria.intervalEndDate"/></label>
                </td>
                <td class="value">
                    <s:textfield name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal2')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                    </li>
                </ul>   
            </del>
        </div>
        <s:if test="%{resultList != null}">
            <jsp:include page="/WEB-INF/jsp/incl/trialCountsResults.jsp"/>
        </s:if>
    </s:form>
</body>
</html>