<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:set var="topic" scope="request" value="run_reports"/> 
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
        <h1>Accrual Home
        </h1>
        <p class="intro">
            This Portal enables you to enter accrual information about trials registered with NCI&#39;s Clinical Trials Reporting Program.
        </p>
        <p class="intro">
            Want to learn more about the Accrual Program? Visit the 
            <a href="http://www.cancer.gov/clinicaltrials/ctrp"  target="new1">NCI Clinical Trials Reporting Program</a>
            web site.  You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> 
            if you have questions or need assistance
        </p>
    </div>
</body>
