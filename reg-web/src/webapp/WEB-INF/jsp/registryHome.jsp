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

<!--Help Content-->
    <!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
                <!--Content-->



		<div class="homepage" style="width:600px">

                    <!--Home Banner-->

                    <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_ctrp.jpg" width="599" height="140" alt="" /></div>

                    <!--/Home Banner-->

                    <!--ADD CONTENT HERE-->
                    <h1>CTRP Home</h1>
                    <p class="intro">
                        This site enables you to register a trial with NCI&#39;s Clinical Trials Reporting Program. You can
                    </p>
                    <ul>
                        <li><a href="/registry/registerUser.action"><strong>Create an account</strong></a> to register your clinical trials </li>
                        <li><a href="/registry/protected/searchTrial.action"><strong>Log In</strong></a> to your account and</li> 
                        	<menu> 
                        		<li>Register clinical trials</li>
                        		<li>Search registered trials by Title, Phase, Trial Identifiers and Organizations</li>
                                </menu>                                               
                    </ul>                    
		    <p class="padme10">
			    Want to learn more about the Reporting Program? Visit the <a href="http://www.cancer.gov/clinicaltrials/ctrp" class="external" target="new1">NCI Clinical Trials Reporting Program website</a>
			    <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance
            </p>

                    <!--/ADD CONTENT HERE-->

		</div>

		<!--/Content-->
 <!-- </div> -->
</body>
</html>