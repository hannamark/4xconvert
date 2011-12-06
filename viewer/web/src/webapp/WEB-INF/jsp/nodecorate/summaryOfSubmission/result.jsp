<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="%{resultList != null}">
    <table id="resultTable" width="100%">
        <tr>
            <td colspan="2">
                <h2><fmt:message key="sosReport.title"/></h2>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="sosReport.header.date1"/>
                <s:label name="criteria.intervalStartDate"/>
                <fmt:message key="sosReport.header.date2"/>
                <s:label name="criteria.intervalEndDate"/>
            </td>
            <td>
                <fmt:message key="report.header.user"/>
                <viewer:displayUser />
            </td>
        </tr>
        <tr>
            <td><br/></td>
        </tr>
        <tr>
            <td colspan="2">
                <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                               requestURI="resultsSummaryOfSubmission.action" export="true">
                    <display:setProperty name="export.excel" value="true" />
                    <display:setProperty name="export.excel.filename" value="SummaryOfSubmissionReport.xls" />                    
                    <display:column titleKey="sosReport.result.organization" property="organization"/>
                    <display:column titleKey="sosReport.result.initial" property="initial"/>
                    <display:column titleKey="sosReport.result.amendment" property="amendment"/>
                    <display:column titleKey="sosReport.result.total" property="total"/>
                </display:table>
            </td>
        </tr>
    </table>
</s:if>