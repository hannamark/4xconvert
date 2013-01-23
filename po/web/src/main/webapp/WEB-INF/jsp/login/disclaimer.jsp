<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="disclaimer.page.title" /></title>
<c:set var="topic" scope="request" value="usersGuide"/>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(btnSelected){
    if(btnSelected == 'accept') {
        document.forms[0].submit();
    } else{
        document.forms[0].action="login/logout.action";
        document.forms[0].submit();
    }

}
</SCRIPT>

</head>

<body>
<s:form name="disclaimer" method="POST" action="disclaimerActionaccept.action">
    <s:token/>
    <!-- main content begins-->
    <br>
    <br>
    <div>
    <table width="65%" align="center">
        <tr>
            <td align="left"><br>
            <center><b><fmt:message key="disclaimer.page.programTitle" /></b></center>
            <br/>
            <hr/>
            <br/>
            <s:property escapeHtml="false" escapeXml="false" 
                value="@gov.nih.nci.po.util.MiscDocumentUtils@getDocumentContent('Disclaimer')"/>
            <br/>
            <br/>
            <hr/>
            </td>
        </tr>
    </table>
    </div>
    <s:hidden name="actionName" id="actionName" />
    <center>
    <div class="actionsrow"><del class="btnwrapper">
        <po:buttonRow>
            <po:button id="accept_disclaimer" href="javascript://noop/" onclick="submitForm('accept');" style="confirm" text="Accept"/>
            <po:button id="reject_disclaimer" href="javascript://noop/" onclick="submitForm('decline');" style="cancel" text="Reject"/>
        </po:buttonRow>
    </del></div>
    </center>
</s:form>
</body>
</html>