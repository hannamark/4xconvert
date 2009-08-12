<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
<center><b><a>View Warning Notice for information on NIH Policies, Disclaimers, and Rules of Behavior</a> </b></center><br> 
<hr>
<br><br>
This system is for the use of authorized users only. Individuals using this computer system
without authority, or in excess of their authority, are subject to having all of their activities
on this system monitored and recorded by system personnel. In the course of monitoring individuals
improperly using this system, or in the course of system maintenance, the activities of authorized users
may also be monitored. Anyone using this system expressly consents to such monitoring and is advised that
if such monitoring reveals possible evidence of criminal activity, system personnel may provide the evidence of
such monitoring to law enforcement officials. Although decisions concerning the applicability and
implementation of the HIPAA Privacy Rule reside with the researcher and his/her institution, it is important
to note that the CTRP Accrual reporting is permitted without patient authorization under several provisions of
the HIPAA Privacy Rule. For example, CTRP disclosures are expressly permitted under section 164.512(b) for
public health activities, which include reporting to the FDA, NIH, IND sponsors, and others.
CTRP uses or disclosures may also be permitted for health oversight activities under section 164.512(d).<br>

In addition, to assure security and safety of patient information, CTRP uses the latest encryption technology.
All data collected through CTRP are stored on a server that is not accessible from the Internet. Once you have
submitted data to the CTRP , the data can only be retrieved with a user ID and password. You should take every
precaution necessary to keep the pass-code information confidential and to avoid distribution of CDS data to
inappropriate individuals.<br>
<hr>
  
</td>
</tr>
</table>
</div>
<s:hidden name="actionName" id="actionName"/>
 <div class="actionsrow">
 <del class="btnwrapper">
                                        <ul class="btnrow">            
<li><a href="#" class="btn" onclick="submitForm('accept');"><span class="btn_img"><span class="accept">I Agree</span></span></a></li> 
<li><a href="#" class="btn" onclick="submitForm('decline');"><span class="btn_img"><span class="decline">I Decline</span></span></a></li>
                                        </ul>    
                                    </del>
                                </div>

</s:form>
</body>
