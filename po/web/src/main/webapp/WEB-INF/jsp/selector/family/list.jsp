<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<body> 
<div class="po_wrapper">
    <div class="po_inner">
        <h1><fmt:message key="family.search.title"/></h1>
    </div>
</div>
<div id="org_family"> 
    <div class="boxouter">     
    <h2><fmt:message key="family.search.results"/></h2>
    <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>