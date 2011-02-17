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
        <br>
        <p class="padme2">
        This site allows you to upload non-CTEP/DCP supported trial accrual data for a specific NCI-supported trial. If you are part of a CTEP/DCP supported trial, please go to the CDUS/CDS web interface to upload your data (these data will be transferred internally to CTRP.
        </p>
        <p class="padme2">
        Want to learn more about the Reporting Program? Visit the <a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main"  target="new1">NCI Clinical Trials Reporting Program</a> website
        <br>You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance
        </p>
    </div>
</body>
