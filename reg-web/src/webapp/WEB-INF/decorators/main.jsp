<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><decorator:title default="NCI Registry"/></title>	
    <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
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
<div id="wrapper">
    	<jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
    	<jsp:include page="/WEB-INF/jsp/common/registryheader.jsp"/>
	     
	     <div id="main">	
			<div id="contentwrapper">
					 <div id="content">
						<a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol');">Help</a> 
						
							<decorator:body/>
						
				 	</div> 
							
					<div class="clear"></div>
					
			</div>
           	<div id="leftnav">
           		<ul class="menu">
              	 	<jsp:include page="/WEB-INF/jsp/common/registrymenu.jsp"/> 
					<jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/> 
				</ul>
			</div> 
         </div> 
         <div class="clear"><br /></div>
         
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</div>
</body>
</html>
