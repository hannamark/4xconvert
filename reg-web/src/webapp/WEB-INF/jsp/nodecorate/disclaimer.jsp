<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="disclaimer.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function submitForm(btnSelected){
    if(btnSelected == 'accept') {
        document.forms[0].action = "disClaimerActionaccept.action";
        document.forms[0].submit();
    } else{
        document.forms[0].action="logout.action";
        document.forms[0].submit();
    } 
    
}
</SCRIPT>

<body>
<s:form name="disclaimer" method="POST">
<!-- main content begins-->
<br>
<br>
<div>
<table width="65%" align="center">
<tr>
<td align="left">
<br>
<center><b><fmt:message key="disclaimer.page.ctrp"/></b></center><br> 
<hr><br>
<s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('Disclaimer','Registry')"/>
<br/>
<br/>
<hr>
<p align="right">OMB#: <s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentVersion('OMB','Registry')"/> 
                                EXP. DATE:
                                <s:set var="ombExpDate" value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentExpiration('OMB','Registry')"/>
                                <fmt:formatDate value="${ombExpDate}" dateStyle="SHORT"/> 
                                </p>
<center><b><fmt:message key="disclaimer.page.ctrp.burden.title"/></b></center><br><br>
<s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('OMB','Registry')"/>
<hr>
  
</td>
</tr>
</table>
</div>
 <div class="actionsrow">
 <del class="btnwrapper">
                                        <ul class="btnrow">            
<li><a href="javascript:void(0)" class="btn" onclick="submitForm('accept');" id="acceptDisclaimer"><span class="btn_img"><span class="accept">Accept</span></span></a></li> 
<li><a href="javascript:void(0)" class="btn" onclick="submitForm('decline');" id="rejectDisclaimer"><span class="btn_img"><span class="decline">Reject</span></span></a></li>
                                        </ul>    
                                    </del>
                                </div><div id="newSessionMarker"></div>

</s:form>
</body>
</html>