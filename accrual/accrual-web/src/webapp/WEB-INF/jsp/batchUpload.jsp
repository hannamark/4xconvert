<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <c:set var="topic" scope="request" value="batch_upload"/>
    <head>
        <title><fmt:message key="accrual.batchUpload.title"/></title>
        <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/overlib.js"/>"></script>
    </head>
    <body>
        <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
        <s:if test="hasActionMessages()"><div class="confirm_msg"><s:actionmessage /></div></s:if>
        <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
        <h1><fmt:message key="accrual.batchUpload.title"/></h1>
        <s:form action="batchUploaddoUpload" method="POST" enctype="multipart/form-data">
            <s:token/>
            <table class="form">
                <tr>
                   <td class="value" style="width:250px">
                    <accrual:displayTooltip tooltip="tooltip.browse">
                        <s:file name="upload" label="File"/>
                    </accrual:displayTooltip>
                   </td>
                </tr>
            </table>
            <div class="actionsrow">
                <div class="btnwrapper">
                    <ul class="btnrow">
                        <accrual:button labelKey="button.submit" href="#" style="save" onclick="document.forms[0].submit();"/>
                    </ul>
                </div>
            </div>
        </s:form>
    </body>
</html>