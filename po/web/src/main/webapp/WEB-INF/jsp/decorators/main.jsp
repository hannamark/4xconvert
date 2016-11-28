<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text-html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title><fmt:message key="po.title" /> - <decorator:title default="Welcome" /></title>
        <%@ include file="/WEB-INF/jsp/decorators/headIncludes.jsp" %>
        <link rel="address bar icon" href="<c:url value="/images/favicon.ico"/>" />
        <link rel="icon" href="<c:url value="/images/favicon.ico"/>" type="image/x-icon" />
        <link rel="shortcut icon" href="<c:url value="/images/favicon.ico"/>" type="image/x-icon" />
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <decorator:head/>
    </head>
    <body onload="setFocusToFirstControl();">
        <a href="#content" id="navskip">Skip to Page Content</a>
        <div id="wrapper" class="curate">
            <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
            <div id="main">
                <div id="contentwrapper">
                    <%-- Content --%>
                    <div id="content">
	                    <%--Page Help--%>
	                    <po:helpPrint/>
                        <fmt:message key="po.title" var="defaultTitle" />
                        <h1><decorator:title default="${defaultTitle}" /></h1>
                        <div class="padme8">
                            <decorator:body/>
                        </div>
                    </div>
                </div>
                <div id="leftnav">
                    <jsp:include page="/WEB-INF/jsp/common/leftnav.jsp"/>
                    <ul class="quicklinks">
                        <li class="liheader">Quick Links</li>
                        <li><a href="http://www.cancer.gov/" class="external" target="new1">National Cancer Institute (NCI)</a></li>
                        <li><a href="http://cbiit.nci.nih.gov/" class="external" target="new2">NCI Center for Biomedical Informatics and Information Technology (CBIIT)</a></li>
                    </ul>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        </div>
    </body>
</html>
