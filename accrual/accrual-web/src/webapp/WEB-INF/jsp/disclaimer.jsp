<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="disclaimer"/> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><fmt:message key="disclaimer.page.title"/></title>   

<script type="text/javascript" language="javascript">
function submitForm(btnSelected){

	 if(btnSelected == 'accept') {
        document.forms[0].submit();
    } else{
        document.forms[0].action="<%=request.getContextPath()%>/logout.action";
        document.forms[0].submit();
    } 
    
}
</script> 

</head>



<body onload="bodyOnLoad()">
<s:form name="disclaimer" method="POST" action="disClaimerActionaccept.action">

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
<%
  gov.nih.nci.pa.util.PaHibernateUtil.getHibernateHelper().openAndBindSession();
%>
<s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('Disclaimer','Accrual')"/>
<br/>
<br/>
<hr>
<p align="right">OMB#: <s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentVersion('OMB','Accrual')"/> 
                                EXP. DATE:
                                <s:set var="ombExpDate" value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentExpiration('OMB','Accrual')"/>
                                <fmt:formatDate value="${ombExpDate}" dateStyle="SHORT"/></p>
<center><b><fmt:message key="disclaimer.page.ctrp.burden.title"/></b></center><br><br>
<s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('OMB','Accrual')"/>
<%
  gov.nih.nci.pa.util.PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
%>
<br>
<hr>
</td>
</tr>
</table>
</div>

<s:hidden name="actionName" id="actionName"/>
        <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">
                <li>
                <s:a href="#" cssClass="btn" onclick="submitForm('accept')" id="acceptDisclaimer"><span class="btn_img"><span class="confirm">Accept</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="submitForm('decline');" id="rejectDisclaimer"><span class="btn_img"><span class="cancel">Reject</span></span></s:a>
                </li>
               </ul>
            </del>
         </div>
</s:form>
</body>
