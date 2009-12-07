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
    <ul class="padme10">
        <li><a href="/outcomes/userAccount.action">Create an account</a> in order to submit data on your patients</li>
        <li><a href="/outcomes/common/welcome.action">Log In</a> to your account and</li> 
        <menu> 
            <li>Create a patient</li>
            <li>Update and/or add data to an existing patient</li>
        </menu>                                               
    </ul>
    <p class="padme2">
        Want to learn more about NCI's Patient Outcomes Reporting? Visit the <a href="https://wiki.nci.nih.gov/display/CTMS/Patient+Outcomes">Outcomes Reporting</a> website.
        <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance.
    </p>
</div>
</body>
