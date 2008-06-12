<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="Struts Starter"/></title>  	  
    <link href="<s:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/layout.css'/>" rel="stylesheet" type="text/css" media="all"/>    
    <link href="<s:url value='/styles/displayStyles.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css'/>" rel="stylesheet" type="text/css" media="print"/>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags.js"/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags_controls.js"/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value="/scripts/ajax/ajaxtags_parser.js"/>"></script>
    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js'/>"></script>
	<script type="text/javascript" src="/scripts/js/calendarpopup.js"></script>	  	
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
    	<jsp:include page="/WEB-INF/jsp/common/paheader.jsp"/>
	     
	     <!-- main content begins-->  	
		<div id="mainwide">
            <div id="leftnav">
               <jsp:include page="/WEB-INF/jsp/common/pamenu.jsp"/>     
            	
			 <!-- main menu ends -->
	
	         <!-- submenu begins -->
	         <!--Quicklinks Menu-->
				<jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/> 
								
			  <!--/Quicklinks Menu-->
				
			<!-- submenu ends -->
			</div>                  		
       
		
            
            	<decorator:body/>
        </div> 
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</body>
</html>
