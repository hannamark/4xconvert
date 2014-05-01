 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <!-- Fixed navbar -->
  <div class="navbar navbar-custom navbar-inverse navbar-static-top" id="nav">
    <div class="container">
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li class="active dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Search <b class="caret"></b></a>  
            <ul class="dropdown-menu">
              <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/searchTrial.action');" >Clinical Trials</a></li>
              <li><a id="personSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/personsSearchexecute.action');">Persons</a></li>
              <li><a id="organizationSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/organizationsSearchexecute.action');">Organizations</a></li>
            </ul>
          </li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Register Trial <b class="caret"></b></a>
            <ul class="dropdown-menu register-trial">
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=National">National<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="NCI National Clinical Trials Network (NCTN) and other NIH-supported National Trial Networks." data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=Externally Peer-Reviewed">Externally Peer-Reviewed<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="R01s, SPORES, U01s, U10s, P01s, CTEP, or any other clinical research study mechanism supported by the NIH or an approved peer-reviewed funding organization." data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=Institutional">Institutional<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="In-house clinical research studies authored or co-authored by Cancer Center investigators and undergoing scientific peer-review solely by the Protocol Review and Monitoring System of the Cancer Center. The Cancer Center investigator has primary responsibility for conceptualizing, designing and implementing the clinical research study and reporting results." data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitProprietaryTrialinputNct.action?sum4FundingCatCode=Industrial">Industrial/Other<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Design and implementation of the study is controlled by the pharmaceutical company or by another organization." data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="#" data-toggle="modal" data-target="#myModal">View Category Definitions</a></li>
              <li><a href="${pageContext.request.contextPath}/protected/addSites.action">Add Sites</a></li>
              <li class="batch">
                <button type="button" class="btn btn-icon btn-sm btn-default" id="popover" rel="popover" data-content="Use this option to register multiple trials." data-placement="bottom" data-trigger="hover" data-toggle="modal" data-target="#batchUpload"><i class="fa-upload"></i> Batch Upload</button>
              </li>
            </ul>
          </li>
          <c:if test="${sessionScope.isSiteAdmin}">
	          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Administration <b class="caret"></b></a>
	            <ul class="dropdown-menu">
		           <li><a id="siteAdministrationMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/siteAdministrationsearch.action');">Site Administration</a></li>
	              	<li class="dropdown-submenu"><a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/displayTrialOwnershipsearch.action');">Trial Ownership</a>
	                <ul class="dropdown-menu">
	                  <li><a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/displayTrialOwnershipsearch.action');">View</a></li>
	                  <li><a id="manageTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageTrialOwnershipsearch.action');">Manage</a></li>
	                  <li><a id="manageSiteOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageSiteOwnershipsearch.action');">Manage Site</a></li>
	                </ul>
	              </li>
	              <li class="dropdown-submenu"><a href="#">Accrual Access</a>
	                <ul class="dropdown-menu">
	                  <li><a id="viewAccrualAccessAssignmentByTrialMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccessassignmentByTrial.action');">View</a></li>
	                  <li><a id="manageAccrualAccessMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccess.action');">Manage</a></li>
	                  <li><a id="viewAccrualAccessAssignmentHistoryMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccessassignmentHistory.action');" >Assignment History</a></li>
	                </ul>
	              </li>
	            </ul>
	          </li>
          </c:if>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Quick Links <b class="caret"></b></a>
            <ul class="dropdown-menu external-links">
              <li><a target="_blank" href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main">Clinical Trials Reporting Program (CTRP)</a></li>
              <li><a target="_blank" href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/resources">Useful Templates and Documentation</a></li>
              <li><a target="_blank" href="http://www.cancer.gov/">National Cancer Institute (NCI)</a></li>
              <li><a target="_blank" href="http://cbiit.nci.nih.gov/">NCI Center for Biomedical Informatics and Information Technology (CBIIT)</a></li>
              <li><a target="_blank" href="https://cabig.nci.nih.gov/">caBIG&#0153; - Cancer Biomedical Informatics Grid&#0153; </a></li>
            </ul>
          </li>
          <li><a href="#" data-toggle="modal" data-target="#contactUs">Contact Us</a></li>
        </ul>
        <div class="pull-right text-sizer"><a href="#" id="incfont">A+</a><a href="#" id="decfont">A-</a></div>
      </div>
      <!--/.nav-collapse --> 
    </div>
    <!--/.container --> 
  </div>
  <!--/.navbar --> 
  <div id="stickyalias"></div>