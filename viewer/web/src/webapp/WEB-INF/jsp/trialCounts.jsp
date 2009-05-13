<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="trial_counts"/> 
<head>
<title><fmt:message key="trialCounts.header" /></title>
<s:head />
<SCRIPT LANGUAGE="JavaScript">
function handleAction(){
     document.forms[0].action="trialCountsgetReport.action";
     document.forms[0].submit();
}
</SCRIPT>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="trialCounts.header"/></h1>
    <s:form>
        <table class="form">    
            <tr> 
                <td scope="row" class="label">
                    <label><fmt:message key="trialCounts.criteria.ctrpOnly"/></label>
                </td>
                <td>
                    <s:checkbox name="criteria.ctrpOnly" />
                </td>
            </tr>
            <tr> 
                <td scope="row" class="label">
                    <label><fmt:message key="trialCounts.criteria.groupByTimeUnit"/></label>
                </td>
            </tr>
            <tr> 
                <td scope="row" class="label">
                    <label><fmt:message key="trialCounts.criteria.intervalStartDate"/></label>
                </td>
            </tr>
            <tr> 
                <td scope="row" class="label">
                    <label><fmt:message key="trialCounts.criteria.intervalEndDate"/></label>
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