<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="curate.search.entity.title"/> - <fmt:message key="curate.search.entity.organization.title"/></title>
</head>
<body>
    <div class="boxouter">
    <h2><fmt:message key="curate.search.results.organization.title"/></h2>
    <%@ include file="organization/list.jsp" %>
    </div>
</body>
</html>