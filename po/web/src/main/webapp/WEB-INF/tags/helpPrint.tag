<%@ tag display-name="helpPrint" description="Renders the help and print buttons at the top of each page" body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="pagehelp">
    <a href="javascript:void(0)" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');" class="help">Help</a>
</div>
