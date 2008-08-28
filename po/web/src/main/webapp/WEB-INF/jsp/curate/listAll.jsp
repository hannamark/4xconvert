<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="curate.search.title"/></title>
</head>
<body>
    <po:successMessages />

    <div class="boxouter"> 
    <h2><fmt:message key="curate.search.results.person.title"/></h2>
    <%@ include file="listPersons.jsp" %>
    </div>

    <div class="boxouter">
    <h2><fmt:message key="curate.search.results.organization.title"/></h2>
    <%@ include file="listOrgs.jsp" %>
    </div>
</body>
</html>