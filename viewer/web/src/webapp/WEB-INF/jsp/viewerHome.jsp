<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="viewer.home.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
    <c:set var="topic" scope="request" value=""/> 
    <div class="homepage" style="width:600px">

        <!--Home Banner-->
            <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_viewer.jpg" width="599" height="140" alt="" /></div>
        <!--/Home Banner-->

        <h1>CTRP Viewer Home</h1>
        <p class="intro">
            This Portal enables you to generate reports about trials registered with NCI&#39;s Clinical Trials Reporting Program.
        </p>
    </div>
</body>
</html>