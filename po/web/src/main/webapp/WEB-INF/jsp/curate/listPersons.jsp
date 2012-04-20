<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="curate.search.entity.title"/> - <fmt:message key="curate.search.entity.person.title"/></title>
    <c:set var="topic" scope="request" value="curateperson"/>
</head>
<body>
    <po:successMessages />

    <div class="boxouter"> 
    <h2><fmt:message key="curate.search.results.person.title"/></h2>
    <%@ include file="person/list.jsp" %>
    </div>

</body>
</html>