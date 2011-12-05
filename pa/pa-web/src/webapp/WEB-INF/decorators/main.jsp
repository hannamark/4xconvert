<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><decorator:title default="Protocol Abstraction (PA)"/></title>
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <script type="text/javascript" language="javascript">
            Help.url = '<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getPaHelpUrl()" />';
            var contextPath = '<%=request.getContextPath()%>';
            function mainOnLoadHandler() {
                if (window.callOnloadFunctions) {
                    callOnloadFunctions();
                }
            }
        </script>
        <fmt:setBundle basename="AuditTrailResources" var="auditTrailResources" scope="session"/>
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <decorator:head/>
    </head>
    <body onload="mainOnLoadHandler();">
    <div id="wrapper" class="curate">
        <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
        <jsp:include page="/WEB-INF/jsp/common/paheader.jsp"/>
        <div id="main">
            <div id="contentwrapper">
                <div id="content">
                   <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
                    <decorator:body/>
                </div>
                <div class="clear"></div>
            </div>
            <div id="leftnav">
                <ul class="menu">
                <c:if test="${sessionScope.disclaimerAccepted}">
                <jsp:include page="/WEB-INF/jsp/common/pamenu.jsp"/>
                </c:if>
                <jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/>
                </ul>
            </div>
        </div>
        <div class="clear"><br /></div>
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
    </div>
    </body>
</html>
