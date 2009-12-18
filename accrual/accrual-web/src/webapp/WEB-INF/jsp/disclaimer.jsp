<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="disclaimer"/> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script type="text/javascript" language="javascript">
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
</script> 

</head>



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
Welcome to the NCI's Clinical Trials Reporting Program (CTRP) Accrual Module pilot website.  As a CTRP pilot cancer 
center, we value your feedback in the initial evaluation of the CTRP Accrual Module and accrual information submission 
process.  Please send your questions, comments, and feedback to <s:a href="mailto:NCICTRP@mail.nih.gov">NCICTRP@mail.nih.gov</s:a>
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
                <s:a href="#" cssClass="btn" onclick="submitForm('accept')"><span class="btn_img"><span class="confirm">Accept</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="submitForm('decline');"><span class="btn_img"><span class="cancel">Reject</span></span></s:a>
                </li>
               </ul>
            </del>
         </div>
</s:form>
</body>
