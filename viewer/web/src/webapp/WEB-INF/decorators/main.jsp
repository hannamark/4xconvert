<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title><decorator:title default="NCI CTRP Viewer"/></title>    
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <script type="text/javascript">
            jQuery(function() {
                jQuery('#mainmenu').buttonset().ptMenu().show();        
            });
            function submitXsrfForm(action){
                document.xsrfForm.action=action;
                document.xsrfForm.submit();
            }
         </script>  
        <decorator:head/>
    </head>
    <body>
        <a href="#content" id="navskip">Skip to Page Content</a> 
        <div id="wrapper">
            <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/viewerheader.jsp"/>    
            <div id="main2">
                <div id="contentwrapper">
                    <div id="content">
                        <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
                        <decorator:body/>
                    </div> 
                    <div class="clear"></div>
                </div>       
            </div> 
            <div class="clear"><br/></div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
    <s:form id="xsrfForm"><s:token/></s:form>
</html>
