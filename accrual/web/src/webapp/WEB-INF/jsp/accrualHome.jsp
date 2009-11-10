<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:set var="topic" scope="request" value="home"/> 
<head>
    <title><fmt:message key="accrual.home.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
<div class="homepage" style="width:600px">
    <table><tr>
    <!--Home Banner-->
      <td>
        <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_accrual.jpg" width="600" height="140" alt="" /></div>
      </td>
    <!--/Home Banner-->
    <!--Help Link-->
      <td>
      </td>
    <!--/Home Link-->
    </tr></table>
    <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
    <h1>Outcomes Home
    </h1>
    <p class="padme2">
        This Site enables you to report outcomes information on patient outcomes.
    </p>
    <p class="padme2">
        <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance
    </p>
</div>
</body>
