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

<!--Help Content-->
    <!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
                <!--Content-->

		<div id="content" class="homepage">

                    <!--Home Banner-->

                    <div class="homebanner"><img src="images/banner_cactus.jpg" width="700" height="200" alt="" /></div>

                    <!--/Home Banner-->

                    <!--ADD CONTENT HERE-->

                    <h1>Welcome to NCI Clinical Trials Portal - Powered by (caCTUS)</h1>

                    <p class="intro">
                        <strong>NCI Clinical Trials Portal - Powered by (caCTUS)</strong> is a registry system for cancer clinical trial protocols that gives you the tools to:
                    </p>
                    <ul>
                        <li><strong>Search for NCI clinical trial protocols</strong></a> submitted by members of the <a href="https://cabig.nci.nih.gov" class="external" target="new">caBIG&trade; community. You can view detailed protocol information such as the title, NCI and Lead Organization Protocol Id, phase, status, principal investigators, and more.</li>
                        <li><strong>Submit your NCI clinical trial protocols</strong> and join our community of contributing scientists.</li>
                    </ul>

                    <!--/ADD CONTENT HERE-->

		</div>

		<!--/Content-->
 <!-- </div> -->
</body>
</html>