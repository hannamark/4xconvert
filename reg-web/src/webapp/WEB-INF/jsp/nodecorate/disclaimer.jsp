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
        The website for the CTRP (Clinical Trials Reporting Program) asks NCI supported sites and awardees to
  submit clinical investigator professional contact information,personal information may be provided voluntarily 
  but will be considered business information by the National Cancer Institute.
        The primary use <br>of this application is as an online mechanism for associating an investigator with a protocol to facilitate
  National Cancer Institute protocol portfolio management.<br> CTRP stores information securely between submissions 
  (i.e., subsequently you will only need to associate your person record with a protocol). 
  <br> Collection of this information is authorized under Section 405 of the Public Health Service Act, 42 U.S.C. 284(b)(1) and <br>
  Privacy Act System Notice 09-25-0200 (http://oma.od.nih.gov/ms/privacy/pa-files/0200.htm). This information may be disclosed
  to researchers for research purposes,<br> contractors responsible  for the maintenance of the repository and to other registered 
  repository users for non-commercial, scientific and educational purposes. <br>Submission of this information is voluntary,  
  but it is required for registration with the National Cancer Institute to conduct a clinical trial.
  <br><br>
    The CTRP website also records IP addresses and aggregated user query information. However, the IP address is not associated 
  with any user registration<br>  information. The user registration data, IP addresses and aggregated user query information are 
  used for NCI internal reporting purposes only to allow for <br> improvement of the CTRP website based on users' needs.
    For additional information, please refer to the link to our Privacy Policy [http://www.nih.gov/about/privacy.htm].<br>
</div>
<s:hidden name="actionName" id="actionName"/>
 <div class="actionsrow">
                                    <del class="btnwrapper">
                                        <ul class="btnrow">            
<li><a href="#" class="btn" onclick="submitForm('accept');"><span class="btn_img"><span class="accept">I Accept</span></span></a></li> 
<li><a href="#" class="btn" onclick="submitForm('decline');"><span class="btn_img"><span class="decline">I Decline</span></span></a></li>
                                        </ul>    
                                    </del>
                                </div>

</s:form>
</body>
</html>