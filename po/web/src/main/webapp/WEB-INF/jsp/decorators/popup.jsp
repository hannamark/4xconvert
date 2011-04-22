<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="po.title" var="defaultTitle" /><decorator:title default="${defaultTitle}" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <%@ include file="/WEB-INF/jsp/decorators/headIncludes.jsp" %>
        <link href="<c:url value='/styles/overwritesPopup.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <decorator:head/>
        <script type="text/javascript">
            function initTitle() {
                mainDiv = document.getElementById('main');
                fields = mainDiv.getElementsByTagName('h1');
                if (fields.length > 0) {
                    parent.document.getElementById('popupTitle').innerHTML = fields[0].innerHTML;
                }
            }
        </script>
    </head>
    <body onload="setFocusToFirstControl(); initTitle();">
        <div>
            <div id="main">
                <decorator:body/>
                <div class="clear" />
            </div>
        </div>
    </body>
</html>
