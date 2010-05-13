<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:set var="topic" scope="request" value=""/> 
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
        This Site enables you to submit and manage clinical outcomes data for your patients. You can:
    </p>
    <c:set var="REQUEST_LOGIN_MISMATCH">
    <%=gov.nih.nci.accrual.outweb.action.AccountActions.REQUEST_LOGIN_MISMATCH%>
    </c:set>
    <c:set var="CREATE">
    <%=gov.nih.nci.accrual.outweb.action.AccountActions.CREATE%>
    </c:set>
    <c:choose>
        <c:when test="${param.userAction eq CREATE}">
            <div class="confirm_msg">
                <strong>Your User Account has been successfully created. Please log in using your username and password.</strong>
            </div>
        </c:when>
        <c:when test="${param.userAction eq REQUEST_LOGIN_MISMATCH}">
            <div class="confirm_msg">
                <strong>You authenticated using a different username and password than provided during your initial registration request. Please attempt to create your account again. </strong>
            </div>
        </c:when>
        <c:otherwise>
            <p style="margin:0; padding:0">Log in to record patient outcome information.
                If you do not have an account, you may <a href="/outcomes/register/start.action">create an account</a>.
            </p>
        </c:otherwise>
    </c:choose>
    <ul class="padme10">
        <li><a href="/outcomes/register/start.action">Create an account</a> in order to submit data on your patients</li>
        <li><a href="/outcomes/common/welcome.action">Log In</a> to your account and</li> 
        <menu> 
            <li>Create a patient</li>
            <li>Update and/or add data to an existing patient</li>
        </menu>                                               
    </ul>
    <p class="padme2">
        Want to learn more about NCI's Patient Outcomes Reporting? Visit the <a href="https://wiki.nci.nih.gov/display/CTMS/Patient+Outcomes+Service">Outcomes Reporting</a> website.
        <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance.
    </p>
</div>
</body>
