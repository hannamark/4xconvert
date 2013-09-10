<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
    <div class="box">
        <pa:sucessMessage/>
        <pa:failureMessage/>
        <s:form>
            <s:actionerror/>
            <s:set name="nciCtGovImportLogs" value="nciCtGovImportLogs" scope="request"/>
            <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="nciIdentifier"> <fmt:message key="ctgov.import.logs.details.nciIdentifier"/></label>
                    </td>
                    <td><c:out value="${nciCtGovImportLogs[0].nciID}"/></td>
                </tr>
                <tr>
                    <td scope="row" class="label">
                        <label for="nctIdentifier"> <fmt:message key="ctgov.import.logs.details.nctIdentifier"/></label>
                    </td>
                    <td><c:out value="${nciCtGovImportLogs[0].nctID}"/></td>
                </tr>
                <tr>
                    <td scope="row" class="label">
                        <label for="trialTitle"> <fmt:message key="ctgov.import.logs.details.trialTitle"/></label>
                    </td>
                    <td><c:out value="${nciCtGovImportLogs[0].title}"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <display:table class="data" sort="list" pagesize="10" id="row" defaultorder="descending" 
                        name="nciCtGovImportLogs" export="true" requestURI="ctGovImportLogshowDetailspopup.action">
                            <display:setProperty name="export.xml" value="false" />
                            <display:setProperty name="export.excel.filename" value="ClinicalTrials.GovImportLog.xls" />
                            <display:setProperty name="export.excel.include_header" value="true" />
                            <display:setProperty name="export.csv.filename" value="ClinicalTrials.GovImportLog.csv" />
                            <display:setProperty name="export.csv.include_header" value="true" />
                            <display:column escapeXml="true" title="Action" property="action" sortable="true" />
                            <display:column escapeXml="true" title="Auto/User" property="userCreated" sortable="true" />
                            <display:column title="Date/Time" format="{0,date,MM/dd/yyyy hh:mm aaa}" property="dateCreated" sortable="true" />
                            <display:column escapeXml="true" title="Import Status" property="importStatus" sortable="true" />
                            <display:column escapeXml="true" title="Needs Review" sortable="true">
                                <c:out value="${row.reviewRequired=='true'?'Yes':'No'}"/>
                            </display:column>                    
                        </display:table>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
</body>
</html>
    

