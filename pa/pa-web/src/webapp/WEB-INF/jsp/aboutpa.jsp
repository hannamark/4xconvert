<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@page import ="gov.nih.nci.pa.util.PaRegistry;" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="about.page.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="about.page.header" /></h1>
 <c:set var="topic" scope="request" value=""/> 
    <!--Box-->

    <div class="box" id="filters">
          
            <p class="intro">You can also email NCICB Application Support at ncicb@pop.nci.nih.gov if you have questions or need assistance
               <br><br> Current release: <%=PaRegistry.getLookUpTableService().getPropertyValue("current.release.no") %></p>

    </div>

    <!--/Box-->
</body>
</html>