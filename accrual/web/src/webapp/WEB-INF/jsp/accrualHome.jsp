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
        <h1>Accrual Home
        </h1>
<p class="padme2">
This Site enables you to report accrual information for a trial with NCI's Clinical Trials Reporting Program. You can
</p>
        
<ul class="padme10">
  <c:choose>
    <c:when test="${pageContext.request.remoteUser == null}">
      <li><a href="/accrual/common/welcome.action">Log In</a> to your account and</li>
      <menu> 
         <li>Report Accruals on Multiple Trials</li>
         <li>Generate Basic Results report for <a href="http://www.clinicaltrials.gov/">ClinicalTrials.gov</a></li>
      </menu>
    </c:when> 
    <c:otherwise>
      <li>You have logged in to your account and can now</li>
      <menu> 
         <li>Report Accruals on Multiple Trials</li>
         <li>Generate Basic Results report for <a href="http://www.clinicaltrials.gov/">ClinicalTrials.gov</a></li>
      </menu>
    </c:otherwise>
  </c:choose>
</ul>
<p class="padme2">
Want to learn more about the Reporting Program? Visit the <a href="http://www.cancer.gov/clinicaltrials/ctrp"  target="new1">NCI Clinical Trials Reporting Program</a> website
 <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance
 </p>
     
    </div>
</body>
