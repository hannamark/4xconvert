<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="registry.home.title"/></title>
        <s:head />
    </head>
    <body onload="setFocusToFirstControl();">
    <!-- <div id="contentwide"> -->
     <h1><fmt:message key="registry.home.title" /></h1>
     <c:set var="topic" scope="request" value=""/> 
    
    	<div class="homepage" style="width:600px">
            <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_ctrp.jpg" width="599" height="140" alt="" /></div>
            <h1>CTRP Home</h1>
            <p class="padme2">
                This site enables you to register a trial with NCI&#39;s Clinical Trials Reporting Program. You can
            </p>
            <ul class="padme10">
                <li><a href="/registry/registerUser.action">Create an account</strong></a> to register your clinical trials </li>
                <li><a href="/registry/protected/searchTrial.action">Log In</a> to your account and</li> 
                	<menu> 
                		<li>Register clinical trials</li>
                		<li>Register multiple trials at one time using a <a href="http://ctrp.nci.nih.gov/docs/CTRP_Registry_Batch_Upload_Template.xls"> batch upload template</a></li>
                		<li>Search registered trials by Title, Phase, Trial Identifiers and Organizations</li>
                        </menu>                                               
            </ul>                    
    	    <p class="padme2">
    		    Want to learn more about the Reporting Program? Visit the <a href="http://www.cancer.gov/clinicaltrials/ctrp"  target="new1">NCI Clinical Trials Reporting Program</a> website
    		    <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance
            </p>
    	</div>
    </body>
</html>