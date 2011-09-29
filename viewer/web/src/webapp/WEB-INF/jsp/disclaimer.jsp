<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<c:set var="topic" scope="request" value="disclaimer"/> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="disclaimer.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function submitForm(btnSelected){
    if(btnSelected == 'accept') {
        document.forms[0].submit();
    } else{
        document.forms[0].action="<%=request.getContextPath()%>/logout.action";
        document.forms[0].submit();
    } 
    
}
</SCRIPT>

<body>
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
<fmt:message key="disclaimer.page.ctrp.systemmsg"/>
<br/>
<br/>
<hr>
</td>
</tr>
</table>
</div>
<s:hidden name="actionName" id="actionName"/>
 <div class="actionsrow">
 <del class="btnwrapper">
                                        <ul class="btnrow">            
<li><a href="#" class="btn" onclick="submitForm('accept');" id="acceptDisclaimer"><span class="btn_img"><span class="confirm">Accept</span></span></a></li> 
<li><a href="#" class="btn" onclick="submitForm('decline');" id="rejectDisclaimer"><span class="btn_img"><span class="cancel">Reject</span></span></a></li>
                                        </ul>    
                                    </del>
                                </div>

</s:form>
</body>
</html>