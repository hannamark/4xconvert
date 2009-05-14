<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<c:set var="topic" scope="request" value="summary_sent"/> 
<head>
<title><fmt:message key="summarySent.header" /></title>
<s:head />
<script type="text/javascript">
function handleAction(){
     document.forms[0].action="summarySentgetReport.action";
     document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="milestones.header"/></h1>
    <s:form>
        <table class="form">    
            <tr> 
                <td class="label">
                    <label><fmt:message key="summarySent.criteria.overdueOnly"/></label>
                </td>
                <td class="value">
                    <s:checkbox name="criteria.overdueOnly" />
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
            <jsp:include page="/WEB-INF/jsp/incl/milestonesResults.jsp"/>
        </s:if>
    </s:form>
</body>
</html>