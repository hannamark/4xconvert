<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<script language="JavaScript">
    function closepopup()
    {
        window.top.hidePopWin(true); 
    }
</script>

</head> 

<body>
    <div class="box">
    <s:form action="studyOverallStatus">
      <h2><fmt:message key="statusHistory.title" /></h2>
      <table class="form">
        <tr><td>
        <s:set name="overallStatusList" value="overallStatusList" scope="request"/>
        <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" 
                name="overallStatusList" export="false">
            <display:column property="statusCode" titleKey="studyOverallStatus.statusCode" sortable="false"/>
            <display:column property="statusDate" titleKey="studyOverallStatus.statusDate" sortable="false"/>
            <display:column property="reason" titleKey = "studyOverallStatus.reason" sortable="false"/>
        </display:table>
        </td></tr>
      </table>
    </s:form>
    </div>
    <div class="actionsrow">
      <del class="btnwrapper">
        <ul class="btnrow">
          <li>
            <s:a href="#" cssClass="btn" onclick="closepopup()"><span class="btn_img"><span class="logout">Close</span></span></s:a>  
          </li>
        </ul>
      </del>
    </div>
</body>
</html>