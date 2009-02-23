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
    <link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="<%=request.getContextPath()%>/styles/displayStyles.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="<%=request.getContextPath()%>/struts/niftycorners/niftyCorners.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/struts/niftycorners/niftyPrint.css" rel="stylesheet" type="text/css" media="print"/>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags_controls.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags_parser.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>        
    
    <link rel="address bar icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
    <link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
    <script language="JavaScript" type="text/javascript">
        window.onload = function(){
            if(!NiftyCheck()) {
                return;
            }
            // perform niftycorners rounding
            // eg.
            // Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
        }
    </script>
    
    <decorator:head/>
</head>
<body> 
   <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
   <decorator:body/>
</html>
