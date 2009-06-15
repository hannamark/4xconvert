<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_reports"/> 
<body>
    <p class="intro">
        To view CTRO Reports select from the menu at left.
    </p>
    <p class="intro">
        To learn more about running CTRO Reports view the 
        <a href="#" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">online help</a>.
    </p>
</body>
