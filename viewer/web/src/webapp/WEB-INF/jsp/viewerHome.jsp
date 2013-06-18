<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<head>
    <title><fmt:message key="viewer.home.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
    <div class="homepage" style="width:600px">
        <table><tr>
        <!--Home Banner-->
          <td>
            <div class="homebanner"><img src="${imagePath}/banner_viewer.jpg" width="599" height="140" alt="" /></div>
          </td>
        <!--/Home Banner-->
        <!--Help Link-->
          <td>
          </td>
        <!--/Home Link-->
        </tr></table>
        <h1>CTRP Viewer Home
        </h1>
        <p class="intro">
            This Portal enables you to�generate reports about trials registered with NCI&#39;s Clinical Trials Reporting Program.
        </p>
        <p class="intro">
            To learn more about this application view the 
            <a href="#" onclick="Help.popHelp('');">online help</a>.
        </p>
        <p class="intro">
            Want to learn more about the Reporting Program? Visit the 
            <a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main"  target="new1">NCI Clinical Trials Reporting Program</a>
            web site.  You can also email NCI CBIIT Application Support at <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a> 
            if you have questions or need assistance
        </p>
    </div>
</body>
