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
<div>
<table width="65%" align="center">
<tr>
<td align="left">
<br>
<center><b>NCI CLINICAL TRIALS REPORTING PROGRAM (CTRP) SYSTEM </b></center><br> 
<hr>
<p align="right">OMB#: 0925-0600 EXP. DATE: 01/31/2010 </p>
<center><b>NOTIFICATION TO RESPONDENT OF ESTIMATED BURDEN </b></center><br><br>
Public reporting burden for this collection of information is estimated to average on (1) to two (2) hours for this questionnaire, <br>
including the time to review instructions, search existing data sources, gather and maintain the data needed, and complete and review 
the collection of information.  An agency may not conduct or sponsor, and a person is not required to respond to, a collection of 
information unless it displays a current, valid OMB control number.  Send comments regarding this burden estimate or any other aspect 
of this collection of information, including suggestions for reducing the burden to <br>
NIH, Project Clearance Branch, 6705 Rockledge Drive, MSC 7974, Bethesda, MD 20892-7974, ATTN: PRA (0925-0600).  <br>
Do not return the completed form to this address.<br>
<hr>
  
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