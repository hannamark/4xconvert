<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><decorator:title default="NCI Registry"/></title>    
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <decorator:head/>
        <style type="text/css">
            BODY { width:100%; }
        </style>
        <script>
          function submitXsrfForm(action){
            document.xsrfForm.action=action;
            document.xsrfForm.submit();
          }
        </script>
    </head>
    <body>
        <a href="#content" id="navskip">Skip to Page Content</a> 
        <div id="wrapper">
            <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/registryheader.jsp"/>
            <div id="main">    
                <div id="contentwrapper">
                    <div id="content">                        
                        <a href="javascript:void(0)" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a> 
                    <decorator:body/>
                    </div> 
                    <div class="clear"></div>
                </div>
                <div id="leftnav">
                    <ul class="menu">
                        <c:if test="${sessionScope.disclaimerAccepted}">
                            <jsp:include page="/WEB-INF/jsp/common/registrymenu.jsp"/> 
                        </c:if> 
                        <jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/> 
                    </ul>
                </div>
            </div> 
            <div class="clear"><br/></div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
	        <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        </div>
        <s:form id="xsrfForm"><s:token/></s:form>
    </body>
</html>
