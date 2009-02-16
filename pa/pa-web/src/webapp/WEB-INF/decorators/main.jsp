<!DOCTYPE html PUBLIC 
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
    <head>
        <title><decorator:title default="Protocol Abstraction (PA)"/></title>  	  
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>        
        <link rel="address bar icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
        <link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon" />        
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/Help.js"/>"></script>		
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/pa.js"/>"></script>
        <decorator:head/>
    </head>
    <body onload="setFocusToFirstControl();">
    <div id="wrapper" class="curate">
        <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
        <jsp:include page="/WEB-INF/jsp/common/paheader.jsp"/>
        <div id="main">	
            <div id="contentwrapper">
                <div id="content">
                    <a href="#" class="helpbutton" onclick="Help.popHelp('');">Help</a>
                    <decorator:body/>
                </div>        
                <div class="clear"></div>        
            </div>
            <div id="leftnav">
                <ul class="menu">
                <jsp:include page="/WEB-INF/jsp/common/pamenu.jsp"/> 
                <jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/> 
                </ul>
            </div> 
        </div> 
        <div class="clear"><br /></div>        
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
    </div>
    </body>
</html>
