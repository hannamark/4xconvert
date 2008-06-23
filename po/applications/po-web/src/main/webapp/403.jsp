<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<page:applyDecorator name="main">
<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>
<p>
    <fmt:message key="403.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
</page:applyDecorator>