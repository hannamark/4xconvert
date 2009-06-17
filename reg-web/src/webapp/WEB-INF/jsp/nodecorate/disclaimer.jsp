<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="register.user.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function bodyOnLoad() {
    alert('hi');
}
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

<body onload="bodyOnLoad()">
<s:form name="disclaimer" method="POST">
<!-- main content begins-->

<br>
<br>
<br>
<br>
<div>
<table width="65%" align="center">
<tr>
<td align="left">
The NCI's Clinical Trials Reporting Program (CTRP) requests NCI supported sites and awardees to submit <br>
clinical investigator professional contact information.  Professional contact information of clinical investigators <br>
will be made available to designated, appropriate NCI employee and contractor staff for purposes of portfolio<br>
management and compliance with regulatory and administrative reporting obligations.  
</td>
</tr>
</table>
</div>
<s:hidden name="actionName" id="actionName"/>
 <div class="actionsrow">
 <del class="btnwrapper">
                                        <ul class="btnrow">            
<li><a href="#" class="btn" onclick="submitForm('accept');"><span class="btn_img"><span class="accept">Accept</span></span></a></li> 
<li><a href="#" class="btn" onclick="submitForm('decline');"><span class="btn_img"><span class="decline">Reject</span></span></a></li>
                                        </ul>    
                                    </del>
                                </div>

</s:form>
</body>
</html>