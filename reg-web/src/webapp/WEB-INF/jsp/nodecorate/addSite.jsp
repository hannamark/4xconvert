<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="spID" scope="page" value="studyProtocolId"/>
<s:set name="ssID" scope="page" value="siteDTO.id"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
<c:url value="/protected/searchTrialview.action?studyProtocolId=${spID}" var="viewTrialUrl"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<script type="text/javascript" language="javascript">
	
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
	<div class="modal-body">
	<s:form name="addSiteForm" id="addSiteForm" action="addSitepopupsave" cssClass="form-horizontal" role="form" >
		<s:token />
		<s:hidden name="studyProtocolId" />		
		<s:hidden name="siteDTO.id" />
			<div class="form-group">
				<label class="col-xs-4  control-label"> <fmt:message key="add.site.trial.nciIdentifier"/></label> 
				 <div class="col-xs-4">
				 <c:out value="${sessionScope.NCI_ID}"/>
				 </div>
			</div>
            <div class="form-group">
				<label class="col-xs-4  control-label"> <fmt:message key="add.site.trial.localStudyProtocolIdentifier"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 <c:out value="${sessionScope.LEAD_ORG_ID}"/>
				 </div>
			</div>
			<div class="form-group">	
			    <fmt:message key="studyAlternateTitles.text" var="title" />					    
				<label class="col-xs-4  control-label"> <fmt:message key="add.site.trial.officialTitle"/></label> 
				 <div class="col-xs-4">
				 	<c:out value="${sessionScope.TITLE}"/>
				   	<c:if test="${not empty trialSummary.studyAlternateTitles}">
                    	<a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                 	</c:if>
				 </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label" for="organizationName"> <fmt:message key="add.site.orgName"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 	<s:textfield id="organizationName" name="siteDTO.name"
					readonly="true" cssClass="form-control readonly" />
					<span class="alert-danger"> <s:fielderror>
							<s:param>organizationName</s:param>
						</s:fielderror>
					</span>
				 </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label" for="localIdentifier"> <fmt:message key="add.site.localID"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 	<s:textfield id="localIdentifier" name="siteDTO.siteLocalTrialIdentifier"
					cssClass="form-control" />
					<span class="alert-danger"> <s:fielderror>
							<s:param>localIdentifier</s:param>
						</s:fielderror>
					</span>
				 </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label" for="investigator"> <fmt:message key="add.site.investigator"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 	<s:textfield id="investigator" name="siteDTO.investigator" 
					readonly="true" cssClass="form-control readonly" />
					<span class="alert-danger"> <s:fielderror>
						<s:param>investigator</s:param>
					</s:fielderror>
				 </div>
				 <div class="col-xs-2">
					<button type="button" class="btn btn-icon btn-default" onclick="lookupSiteInvestigator();"><i class="fa-search"></i>Look Up</button>
				 </div>
				 <s:hidden id="investigator.id" name="siteDTO.investigatorId" />
			</div>
		
			<div class="form-group">
				<label class="col-xs-4 control-label" for="programCode"> <fmt:message key="add.site.programCode"/></label> 
				 <div class="col-xs-4">
				 	<s:textfield id="programCode" name="siteDTO.programCode"
					cssClass="form-control" />
					<span class="alert-danger"> <s:fielderror>
							<s:param>programCode</s:param>
						</s:fielderror>
					</span>
				 </div>
			</div>
			
			<div class="form-group">
				<label class="col-xs-4 control-label" for="statusCode"> <fmt:message key="add.site.statusCode"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 	<s:set name="statusCodeValues"
					value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
					<s:select headerKey="" headerValue="--Select--" id="statusCode"
						name="siteDTO.recruitmentStatus" list="#statusCodeValues"
						value="siteDTO.recruitmentStatus" cssClass="form-control" />
					<span class="alert-danger"> <s:fielderror>
							<s:param>statusCode</s:param>
						</s:fielderror>
					</span>
				 </div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label" for="statusDate"><fmt:message key="add.site.statusDate"/><span class="required">*</span></label> 
				 <div class="col-xs-4">
				 	<div class="datetimepicker input-append">
					<s:textfield id="statusDate" name="siteDTO.recruitmentStatusDate"
						maxlength="10" size="10" cssClass="form-control" data-format="MM/dd/yyyy" placeholder="mm/dd/yyyy"/>
					<span class="add-on btn-default"><i class="fa-calendar"></i></span>	</div>
	                <span class="alert-danger"> <s:fielderror>
							<s:param>statusDate</s:param>
						</s:fielderror>
					</span>
					
				 </div>
			</div>	

			<div class="form-group">
				<label class="col-xs-4 control-label" for="accrualOpenedDate"><fmt:message key="add.site.accrualOpenedDate"/></label> 
				 <div class="col-xs-4">
				 	<div  class="datetimepicker input-append">
					<s:textfield id="accrualOpenedDate" name="siteDTO.dateOpenedforAccrual"
						maxlength="10" size="10" cssClass="form-control" data-format="MM/dd/yyyy" placeholder="mm/dd/yyyy"/>
					<span class="add-on btn-default"><i class="fa-calendar"></i></span>	
	                <span class="alert-danger"> <s:fielderror>
							<s:param>accrualOpenedDate</s:param>
						</s:fielderror>
					</span>
					</div>
				 </div>
			</div>	

			<div class="form-group">
				<label class="col-xs-4 control-label" for="accrualClosedDate"><fmt:message key="add.site.accrualClosedDate"/></label> 
				 <div class="col-xs-4">
				 	<div  class="datetimepicker input-append">
						<s:textfield id="accrualClosedDate" name="siteDTO.dateClosedforAccrual"
						maxlength="10" size="10" cssClass="form-control" data-format="MM/dd/yyyy" placeholder="mm/dd/yyyy"/>
						<span class="add-on btn-default"><i class="fa-calendar"></i></span>	
						<span class="info"><br/><fmt:message
								key="error.proprietary.dateOpenReq" /></span>
						<span class="alert-danger"> <s:fielderror>
								<s:param>accrualClosedDate</s:param>
							</s:fielderror>
						</span>
					</div>
				 </div>
			</div>	
		<reg-web:saveAndCloseBtn />
	</s:form>
	</div>
	</s:if>
</body>
</html>