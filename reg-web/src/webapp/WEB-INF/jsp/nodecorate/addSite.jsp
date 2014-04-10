<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="spID" scope="page" value="studyProtocolId"/>
<s:set name="ssID" scope="page" value="siteDTO.id"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
<c:url value="/protected/searchTrialview.action?studyProtocolId=${spID}" var="viewTrialUrl"/>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includeextrajs.jsp"%>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript">
	addCalendar("Cal1", "Select Date", "siteDTO.recruitmentStatusDate", "addSiteForm");
	addCalendar("Cal2", "Select Date", "siteDTO.dateOpenedforAccrual",
			"addSiteForm");
	addCalendar("Cal3", "Select Date", "siteDTO.dateClosedforAccrual",
			"addSiteForm");
	setWidth(90, 1, 15, 1);
	setFormat("mm/dd/yyyy");
	
	
	function addSite() {
		$('addSiteForm').submit();
		<c:url value="/protected/addSitepopupshowWaitDialog.action" var="reviewProtocol"/>
		showPopWin('${reviewProtocol}', 600, 200, '', '${not empty ssID?'Update Participating Site':'Add Participating Site'}');
	}

	function doSave() {
		addSite();
	}
	
	function lookupSiteInvestigator() {
		showPopWin('${lookupPersonsUrl}', getViewportWidth()*0.90, getViewportHeight()*0.85, null, 'Persons');        
    }	
	
    function setpersid(id, name) {
    	name = name.replace(/&apos;/g,"'");
        $('investigator.id').value = id;
        $('investigator').value = name;
        
    }	
	
</script>

<s:if test="redirectToSummary">
    <script>
     window.top.location = "${viewTrialUrl}";
    </script>
</s:if>

</head>
<body class="addsite">
    <s:if test="redirectToSummary==false">
	<reg-web:failureMessage/>
	<reg-web:sucessMessage/>
	<reg-web:actionErrorsAndMessages />
	<s:form name="addSiteForm" id="addSiteForm" action="addSitepopupsave">
		<s:token />
		<s:hidden name="studyProtocolId" />		
		<s:hidden name="siteDTO.id" />		
		<table class="form">
		    <c:choose>
		      <c:when test="${not empty ssID}">
		          <reg-web:titleRow titleKey="update.site.title" />
		      </c:when>
		      <c:otherwise>
		          <reg-web:titleRow titleKey="add.site.title" />
		      </c:otherwise>
		    </c:choose>			
			<reg-web:spaceRow />
            <reg-web:valueRow labelKey="add.site.trial.nciIdentifier" noLabelTag="true">
                 <c:out value="${sessionScope.NCI_ID}"/>
            </reg-web:valueRow>
			<reg-web:valueRow labelKey="add.site.trial.localStudyProtocolIdentifier" noLabelTag="true">
			     <c:out value="${sessionScope.LEAD_ORG_ID}"/>
			</reg-web:valueRow>
			<reg-web:valueRow labelKey="add.site.trial.officialTitle" noLabelTag="true">
                 <c:out value="${sessionScope.TITLE}"/>
                 <c:if test="${not empty trialSummary.studyAlternateTitles}">
                     <a href="javascript:void(0)" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                 </c:if>
            </reg-web:valueRow>			
			<reg-web:valueRow labelFor="organizationName"
				labelKey="add.site.orgName" required="true">
				<s:textfield id="organizationName" name="siteDTO.name" cssClass="readonly"
					readonly="true" cssStyle="width:250px; float:left" />
				<span class="alert-danger"> <s:fielderror>
						<s:param>organizationName</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>
			<reg-web:valueRow labelFor="localIdentifier"
				labelKey="add.site.localID" required="true">
				<s:textfield id="localIdentifier" name="siteDTO.siteLocalTrialIdentifier"
					cssStyle="width:250px; float:left" />
				<span class="alert-danger"> <s:fielderror>
						<s:param>localIdentifier</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>
			<reg-web:valueRow labelFor="investigator"
				labelKey="add.site.investigator" required="true">
				<s:textfield id="investigator" name="siteDTO.investigator" cssClass="readonly"
					readonly="true" cssStyle="width:250px; float:left" />

				<ul style="margin-top: -5px;">
					<li style="padding-left: 0"><a href="javascript:void(0);"
						class="btn" onclick="lookupSiteInvestigator();"
						title="Opens a popup form to select Site Principal Investigator">
							<span class="btn_img"><span class="person">Look Up
									Person</span></span>
					</a></li>
				</ul>

				<span class="alert-danger"> <s:fielderror>
						<s:param>investigator</s:param>
					</s:fielderror>
				</span>
				<s:hidden id="investigator.id" name="siteDTO.investigatorId" />
			</reg-web:valueRow>

			<reg-web:valueRow labelFor="programCode"
				labelKey="add.site.programCode">
				<s:textfield id="programCode" name="siteDTO.programCode"
					cssStyle="width:250px; float:left" />
				<span class="alert-danger"> <s:fielderror>
						<s:param>programCode</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>

			<reg-web:valueRow labelFor="statusCode"
				labelKey="add.site.statusCode" required="true">
				<s:set name="statusCodeValues"
					value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
				<s:select headerKey="" headerValue="--Select--" id="statusCode"
					name="siteDTO.recruitmentStatus" list="#statusCodeValues"
					value="siteDTO.recruitmentStatus" />
				<span class="alert-danger"> <s:fielderror>
						<s:param>statusCode</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>

			<reg-web:valueRow labelFor="statusDate" required="true"
				labelKey="add.site.statusDate">
				<s:textfield id="statusDate" name="siteDTO.recruitmentStatusDate"
					maxlength="10" size="10" cssStyle="width:70px;float:left" />
				<a href="javascript:showCal('Cal1')"> <img
					src="${pageContext.request.contextPath}/images/ico_calendar.gif"
					alt="Select a date" class="calendaricon" />
				</a> (mm/dd/yyyy)                     
                <span class="alert-danger"> <s:fielderror>
						<s:param>statusDate</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>

			<reg-web:valueRow labelFor="accrualOpenedDate"
				labelKey="add.site.accrualOpenedDate">
				<s:textfield id="accrualOpenedDate" name="siteDTO.dateOpenedforAccrual"
					maxlength="10" size="10" cssStyle="width:70px;float:left" />
				<a href="javascript:showCal('Cal2')"> <img
					src="${pageContext.request.contextPath}/images/ico_calendar.gif"
					alt="Select a date" class="calendaricon" />
				</a> (mm/dd/yyyy)                     
                <span class="alert-danger"> <s:fielderror>
						<s:param>accrualOpenedDate</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>

			<reg-web:valueRow labelFor="accrualClosedDate"
				labelKey="add.site.accrualClosedDate">
				<s:textfield id="accrualClosedDate" name="siteDTO.dateClosedforAccrual"
					maxlength="10" size="10" cssStyle="width:70px;float:left" />
				<a href="javascript:showCal('Cal3')"> <img
					src="${pageContext.request.contextPath}/images/ico_calendar.gif"
					alt="Select a date" class="calendaricon" />
				</a> (mm/dd/yyyy)
				<span class="info"><fmt:message
						key="error.proprietary.dateOpenReq" /></span>
				<span class="alert-danger"> <s:fielderror>
						<s:param>accrualClosedDate</s:param>
					</s:fielderror>
				</span>
			</reg-web:valueRow>

		</table>
		<reg-web:saveAndCloseBtn />
	</s:form>
	</s:if>
</body>
</html>