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
        document.forms[0].action="<%=request.getContextPath()%>/logout.action";
        document.forms[0].submit();
    } 
    
}
</SCRIPT>
<s:form name="disclaimer" method="POST">
<div class="row">
	<div class="col-xs-8 col-xs-offset-2">
	  <h3 class="heading align-center"><span>NCI Clinical Trials Reporting Program (CTRP) System</span></h3>
	  <p>This is a U.S. Government computer system, which may be accessed and used only for authorized Government business by authorized personnel. Unauthorized access or use of this computer system may subject violators to criminal, civil, and/or administrative action.</p>
	  <p>All information on this computer system may be intercepted, recorded, read, copied, and disclosed by and to authorized personnel for official purposes, including criminal investigations. Such information includes sensitive data encrypted to comply with confidentiality and privacy requirements. Access or use of this computer system by any person, whether authorized or unauthorized, constitutes consent to these terms. There is no right of privacy in this system. </p>
	  <h3 class="heading align-center"><span>Notification to Respondent of Estimated Burden</span></h3>
	  <small class="omb">OMB#: 0925-0600 EXP. DATE: 5/31/16</small>
	  <p>Public reporting burden for this collection of information is estimated to average sixty (60) minutes for this questionnaire, including the time to review instructions, search existing data sources, gather and maintain the data needed, and complete and review the collection of information. An agency may not conduct or sponsor, and a person is not required to respond to, a collection of information unless it displays a current, valid OMB control number.</p>
	  <p>Send comments regarding this burden estimate or any other aspect of this collection of information, including suggestions for reducing the burden to
	    NIH, Project Clearance Branch, 6705 Rockledge Drive, MSC 7974, Bethesda, MD 20892-7974, ATTN: PRA (0925-0600).
	    Do not return the completed form to this address. </p>
	  <hr>
	  <div class="align-center">
	    <button type="button" class="btn btn-primary btn-icon mr20" data-dismiss="modal" onClick="submitForm('accept');" id="acceptDisclaimer"><i class="fa-check-circle"></i>Accept</button>
	    <button type="button" class="btn btn-default btn-icon" data-dismiss="modal" onclick="submitForm('decline');" id="rejectDisclaimer"><i class="fa-times-circle"></i>Reject</button>
	   </div>
	 </div>
</div>
</s:form>  
 