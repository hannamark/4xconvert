<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="addSites.title" /></title>
<s:head />
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl" />
<c:url value="/protected/addSitesvalidateSiteData.action"
	var="validateUrl" />

<style type="text/css">
.no-border {
	border: 0px solid #DDDDDD !important;
}

.no-border-row td {
	border: 0px solid #DDDDDD !important;
}

.bottom-border-only {
	border-bottom: 1px solid #DDDDDD !important;
}

.popover {
    max-width: 150px !important;
    font-size: 80%;
}

</style>

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>

<script type="text/javascript" language="javascript">

	function resetSearch() {
		$('identifier').value = '';
		$('officialTitle').value = '';
	}
	
	function cancel() {       
        $('addSitesForm').action = 'addSites.action';
        $('addSitesForm').submit();
    }

	function search() {
		displayWaitPanel();
		$('addSitesForm').action = 'addSitessearch.action';
		$('addSitesForm').submit();
	}
	
	function beginAddingSites(spID) {
		disableSorting();
		$('plussign_'+spID).hide();
		$('saveCancelDiv').show();
		prepareSiteEntryRow(spID);
		prepareSiteInformationFormControls(spID);
		showPopover(spID);
	}
	
	var popoverShownOnce = false;
	function showPopover(spID) {
		if (!popoverShownOnce) {
			  popoverShownOnce = true;			 
			  jQuery("#trial_"+spID+"_info").popover({
                  'animation' : true,
                  'trigger': 'manual' 
              });
			  jQuery("#trial_"+spID+"_info").popover('show');
		}
	}
	
	function prepareSiteInformationFormControls(spID) {
		var div = $('addSitesDiv_'+spID);
		var col = $('addSitesCol_'+spID);
		col.appendChild(div.parentNode.removeChild(div));	
		jQuery(div).show("blind");
	}
	
	function prepareSiteEntryRow(spID) {
		var trialRow = $('trialrow_'+spID);
		trialRow.addClassName('no-border-row');
		
		var addSiteRow = document.createElement('tr');
		addSiteRow.setAttribute('id','addSitesRow_'+spID);
		addSiteRow.setAttribute('class',trialRow.getAttribute('class'));
		$(addSiteRow).addClassName('bottom-border-only');
		
		var addSiteCol = document.createElement('td');
		addSiteCol.setAttribute('id','addSitesCol_'+spID);
		addSiteCol.setAttribute('colspan','3');
		addSiteRow.appendChild(addSiteCol);
		
		trialRow.parentNode.insertBefore(addSiteRow, trialRow.nextSibling);
	}
	
	var persid;
	var chosenname;
	function setpersid(persIdentifier, sname, email, phone) {
        persid = persIdentifier;
        chosenname = sname.replace(/&apos;/g,"'").replace(/,/g,", ");
    }
	function lookupPI(spID, index) {
		 showPopup('${lookupPersUrl}', function () {
			 if (persid!=null) {
				 $("trial_"+spID+"_site_"+index+"_pi_poid").value = persid;
				 $("trial_"+spID+"_site_"+index+"_pi_name").innerHTML = chosenname;		
				 persid = null;
				 chosenname = null;
			 }
		 }, 'Select Principal Investigator');		
	}
	
	function addAnotherSiteRow(spID, index) {
		$('trial_'+spID+'_site_'+index+'_btnrow').hide();
		index++;
		
		jQuery('#trial_'+spID+'_site_'+index+'_row').show('highlight');
		
		if ($('trial_'+spID+'_site_'+index+'_btnrow')!=null) {
			$('trial_'+spID+'_site_'+index+'_btnrow').show();
		}
		
		preSelectSiteOrgToBeDifferentFromPreviousRows(spID, index);
		
		
	}
	
	function preSelectSiteOrgToBeDifferentFromPreviousRows(spID, index) {
	    var orgDD = $('trial_'+spID+'_site_'+index+'_org_poid');
	    for (var i = 0; i < orgDD.length; i++) {
	    	var poOrgID = orgDD.options[i].value;
	    	if (isPoOrgIdNotSelectedInPreviousRows(poOrgID, spID, index)) {
	    		orgDD.setValue(poOrgID);
	    		return;
	    	}
	    }	    
	}
	
	function isPoOrgIdNotSelectedInPreviousRows(poOrgID, spID, index) {
		for (var i = index-1; i >= 0; i--) {
			 if ($F('trial_'+spID+'_site_'+i+'_org_poid')==poOrgID) {
				 return false;
			 }
		}
		return true;
	}
	
	function copySiteDataFromRowAbove(spID, index) {
		if (index <= 0) {
			return;
		}
		$('trial_'+spID+'_site_'+index+'_pi_name').innerHTML = $('trial_'+spID+'_site_'+(index-1)+'_pi_name').innerHTML;
		$('trial_'+spID+'_site_'+index+'_pi_poid').setValue($('trial_'+spID+'_site_'+(index-1)+'_pi_poid').getValue());
		$('trial_'+spID+'_site_'+index+'_localID').setValue($('trial_'+spID+'_site_'+(index-1)+'_localID').getValue());
		$('trial_'+spID+'_site_'+index+'_pgcode').setValue($('trial_'+spID+'_site_'+(index-1)+'_pgcode').getValue());
		$('trial_'+spID+'_site_'+index+'_status').setValue($('trial_'+spID+'_site_'+(index-1)+'_status').getValue());
		$('trial_'+spID+'_site_'+index+'_statusDate').setValue($('trial_'+spID+'_site_'+(index-1)+'_statusDate').getValue());
		$('trial_'+spID+'_site_'+index+'_dateOpen').setValue($('trial_'+spID+'_site_'+(index-1)+'_dateOpen').getValue());
		$('trial_'+spID+'_site_'+index+'_dateClosed').setValue($('trial_'+spID+'_site_'+(index-1)+'_dateClosed').getValue());
	}
	
	function save() {
		displayWaitPanel();
		hideErrorRows();
		
		 var ajaxReq = new Ajax.Request('${validateUrl}', {
             method: 'post',
             parameters: $('addSitesForm').serialize(),
             onSuccess: function(transport) {
                 hideWaitPanel();   
                 var errors = transport.responseJSON;
                 if (errors.errors.length > 0) {
                	 firstError = false;
                	 $A(errors.errors).each(function(errObj) {                		 
                		 displayErrorsRow(errObj.spID, errObj.index, errObj.errors);
                	 });
                 } else {
                	 displayWaitPanel();
                	 $('addSitesForm').action = 'addSitessave.action';
                     $('addSitesForm').submit();
                 }
             },
             onFailure: function(transport) {
                 hideWaitPanel();  
                 alert('Error when communicating with the server. Please try again.');                 
             },
             onException: function(requesterObj, exceptionObj) {
                 ajaxReq.options.onFailure(null);
             },
             on0: function(transport) {
                 ajaxReq.options.onFailure(transport);
             }
         });
	}
	
	function hideErrorRows() {
		$$('.errors').each(Element.hide);
	}
	
	var firstError = false;
	function displayErrorsRow(spID, index, errors) {
		$('trial_'+spID+'_site_'+index+'_errorDiv').innerHTML = errors;		
		$('trial_'+spID+'_site_'+index+'_errorRow').show();		
		
		if (!firstError) {
			firstError = true;
			$('trial_'+spID+'_site_'+index+'_errorRow').scrollTo();
		}
		
		jQuery('#trial_'+spID+'_site_'+index+'_errorMainDiv').show('blind');
	}
	
	function disableSorting() {
	    jQuery("#th1").unbind('click');		
	    jQuery("#th2").unbind('click');
	}
	
	var dataTable;
	
	(function($) {
		$(document).ready(function() {
			
			$('[data-toggle="tooltip"]').tooltip({
				'placement' : 'top'
			});

			dataTable = $('#trialsTable').dataTable({
				"bPaginate" : false,
				"bLengthChange" : false,
				"bFilter" : false,
				"bSort" : true,
				"bInfo" : false,
				"bAutoWidth" : false,

				"aoColumnDefs" : [ {
					'bSortable' : false,
					'aTargets' : [ 0 ]
				} ]
			});
		});
	}(jQuery));
	
	
	
</script>
</head>
<body>
	<!-- main content begins-->
	<div class="container">
		<c:set var="topic" scope="request" value="addsites" />

		<s:form name="addSites" action="addSites.action" id="addSitesForm"
			cssClass="form-horizontal" role="form">
			<s:token />

			<h3 class="heading">
				<span>Search Trials</span>
			</h3>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<p class="mb20">NOTE: Only eligible Abbreviated Trials, on
						which your affiliated organization or its family members are not
						participating sites yet, are searched using this option.</p>
				</div>

			</div>

			<reg-web:failureMessage />
			<reg-web:sucessMessage />
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger">
					<s:actionerror />
				</div>
			</s:if>

			<div id="filters">
				<div class="form-group">
					<label for="identifier" class="col-md-3 control-label">NCI,
						Lead Org, or Other Trial Identifier: </label>
					<div class="col-md-3">
						<s:textfield id="identifier" name="criteria.identifier"
							placeholder="Examples: NCI-2008-00015; ECOG-1234" maxlength="50"
							size="100" cssClass="form-control" />
					</div>
					<label for="officialTitle" class="col-md-1 control-label">
						Trial Title: </label>
					<div class="col-md-5">
						<s:textfield id="officialTitle" name="criteria.officialTitle"
							placeholder="Enter all or part of title" maxlength="400"
							size="100" cssClass="form-control" />
					</div>
				</div>
				<div class="bottom no-border">
					<button type="button" class="btn btn-icon btn-primary" id="runSearchBtn"
						onclick="search();">
						<i class="fa-search"></i>
						<fmt:message key="siteadministration.buttons.search" />
					</button>
					<button type="button" class="btn btn-icon btn-default" id="resetBtn"
						onclick="resetSearch();">
						<i class="fa-repeat"></i>
						<fmt:message key="siteadministration.buttons.reset" />
					</button>
				</div>
			</div>

			<c:if test="${not empty records}">
				<h3 class="heading">
					<span>Trials</span>
				</h3>

				<div class="row">
					<div class="col-md-10 col-md-offset-1">
						<p class="mb20">
							Here are the trials to which you can add sites. You can sort by
							trial identifier or title by clicking on the corresponding column
							header (note: sorting becomes disabled once you start entering
							sites). You can begin entering sites for a trial by clicking on
							the <b>Plus</b> sign. Once you are done entering site
							information, click the <b>Save</b> button at the bottom of the
							page to save all changes at once.
						</p>
					</div>

				</div>

				<div class="table-wrapper">

					<table class="table table-bordered" id="trialsTable">
						<thead class="sortable">
							<tr>
								<th id="th0"></th>
								<th id="th1" nowrap="nowrap"><a>Trial Identifier<i
										class="fa-sort"></i></a></th>
								<th id="th2"><a>Trial Title<i class="fa-sort"></i></a></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${records}" var="trial" varStatus="stat">
								<tr id="trialrow_${trial.studyProtocolId}">
									<td><i class="fa fa-plus-square" data-toggle="tooltip"
										onclick="beginAddingSites(${trial.studyProtocolId})"
										id="plussign_${trial.studyProtocolId}"
										title="Click here to add sites to ${trial.nciIdentifier}"></i></td>
									<td nowrap="nowrap"><c:out value="${trial.nciIdentifier}" /></td>
									<td><c:out value="${trial.officialTitle}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<div class="bottom no-border" id="saveCancelDiv"
					style="display: none;">
					<button type="button" class="btn btn-icon btn-primary" id="saveBtn"
						onclick="save();">
						<i class="fa-save"></i> Save
					</button>
					<button type="button" class="btn btn-icon btn-default" id="cancelBtn"
						onclick="cancel();">
						<i class="fa-times-circle"></i> Cancel
					</button>
				</div>
			</c:if>

		</s:form>
	</div>
	<s:set name="statusCodeValues"
		value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
	<c:forEach items="${records}" var="trial">
		<div id="addSitesDiv_${trial.studyProtocolId}" class="container-fluid"
			style="display: none;">
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-11">
					<div data-toggle="popover" data-placement="left"
						data-content="If you change your mind about entering a particular site, simply leave the row empty and it will be ignored."
						class="container-fluid" id="trial_${trial.studyProtocolId}_info">
						<table class="table no-border">
							<tr class="bottom-border-only">
								<td><b>Site<span class="required">*</span></b></td>
								<td><b>Principal Investigator<span class="required">*</span></b></td>
								<td></td>
								<td><b>Local Trial Identifier<span class="required">*</span></b></td>
								<td><b>Program Code</b></td>
								<td><b>Current Site Recruitment Status<span
										class="required">*</span></b></td>
								<td><b>Current Site Recruitment Status Date<span
										class="required">*</span></b></td>
								<td><b>Date Opened for Accrual</b></td>
								<td><b>Date Closed for Accrual</b></td>
								<td></td>
							</tr>
							<c:forEach items="${trial.orgsThatCanBeAddedAsSite}" var="org"
								varStatus="stat">

								<tr
									id="trial_${trial.studyProtocolId}_site_${stat.index}_errorRow"
									style="display: none;" class="errors">
									<td colspan="10">
										<div class="container-fluid errors" style="display: none;"
											id="trial_${trial.studyProtocolId}_site_${stat.index}_errorMainDiv">
											<div class="row alert alert-danger">
												<div class="col-md-1">
													<span
														class="glyphicon glyphicon-large glyphicon-exclamation-sign"></span>
												</div>
												<div
													id="trial_${trial.studyProtocolId}_site_${stat.index}_errorDiv"
													class="col-md-11"></div>
											</div>
										</div>
									</td>
								</tr>

								<c:set var="rowVisibility" value="${stat.first?'':'none'}" />
								<tr id="trial_${trial.studyProtocolId}_site_${stat.index}_row"
									style="display: ${rowVisibility};">
									<td><select class="form-control" style="min-width: 200px;"
										name="trial_${trial.studyProtocolId}_site_${stat.index}_org_poid"
										id="trial_${trial.studyProtocolId}_site_${stat.index}_org_poid">
											<c:forEach items="${trial.orgsThatCanBeAddedAsSite}"
												var="innerOrg">
												<option value="${innerOrg.identifier.extension}">
													<c:out value="${innerOrg.name.part[0].value}" />
												</option>
											</c:forEach>
									</select></td>
									<td valign="middle"><span
										id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_name">
											<i style="font-style: italic;">Not Selected</i>
									</span> <input type="hidden"
										id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_poid"
										name="trial_${trial.studyProtocolId}_site_${stat.index}_pi_poid" />
									</td>
									<td nowrap="nowrap">
										<button title="Select Principal Investigator for this site"
											data-toggle="tooltip"
											id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_lookupBtn"
											onclick="lookupPI(${trial.studyProtocolId}, ${stat.index});"
											class="btn btn-default" type="button">
											<i class="fa-user"></i>&nbsp;
										</button>
									</td>
									<td><input type="text"
										id="trial_${trial.studyProtocolId}_site_${stat.index}_localID"
										name="trial_${trial.studyProtocolId}_site_${stat.index}_localID"
										placeholder="Enter Local Trial ID" maxlength="50"
										class="form-control" /></td>
									<td><input type="text"
										id="trial_${trial.studyProtocolId}_site_${stat.index}_pgcode"
										name="trial_${trial.studyProtocolId}_site_${stat.index}_pgcode"
										placeholder="Enter Program Code" maxlength="50"
										class="form-control" /></td>
									<td><s:select headerKey="" headerValue="--Select--"
											id="trial_%{#attr.trial.studyProtocolId}_site_%{#attr.stat.index}_status"
											name="trial_%{#attr.trial.studyProtocolId}_site_%{#attr.stat.index}_status"
											list="#statusCodeValues" cssClass="form-control" /></td>
									<td nowrap="nowrap">
										<div class="datetimepicker input-append" id="datetimepicker">
											<input type="text"
												id="trial_${trial.studyProtocolId}_site_${stat.index}_statusDate"
												name="trial_${trial.studyProtocolId}_site_${stat.index}_statusDate"
												placeholder="mm/dd/yyyy" maxlength="10" size="10"
												data-format="MM/dd/yyyy" class="form-control" /> <span
												class="add-on btn-default"><i
												class="fa-calendar icon-calendar"></i></span>
										</div>
									</td>
									<td nowrap="nowrap">
										<div class="datetimepicker input-append" id="datetimepicker">
											<input type="text"
												id="trial_${trial.studyProtocolId}_site_${stat.index}_dateOpen"
												name="trial_${trial.studyProtocolId}_site_${stat.index}_dateOpen"
												placeholder="mm/dd/yyyy" maxlength="10" size="10"
												data-format="MM/dd/yyyy" class="form-control" /> <span
												class="add-on btn-default"><i
												class="fa-calendar icon-calendar"></i></span>
										</div>
									</td>
									<td nowrap="nowrap">
										<div class="datetimepicker input-append" id="datetimepicker">
											<input type="text"
												id="trial_${trial.studyProtocolId}_site_${stat.index}_dateClosed"
												name="trial_${trial.studyProtocolId}_site_${stat.index}_dateClosed"
												placeholder="mm/dd/yyyy" maxlength="10" size="10"
												data-format="MM/dd/yyyy" class="form-control" /> <span
												class="add-on btn-default"><i
												class="fa-calendar icon-calendar"></i></span>
										</div>
									</td>
									<c:if test="${not stat.first}">
										<td><div class="input-append" style="padding-left: 10px;"
												onclick="copySiteDataFromRowAbove(${trial.studyProtocolId},${stat.index});"
												title="Click here to quickly copy site information from the row above"
												data-toggle="tooltip">
												<span class="add-on"><i class="fa-hand-o-left"></i></span>
											</div></td>
									</c:if>
								</tr>
								<c:if test="${not stat.last}">
									<tr
										id="trial_${trial.studyProtocolId}_site_${stat.index}_btnrow"
										style="display: ${rowVisibility};">
										<td colspan="10">
											<button type="button" class="btn btn-icon btn-primary"
												onclick="addAnotherSiteRow(${trial.studyProtocolId},${stat.index});">
												<i class="fa-plus-square"></i> Add Another Site
											</button>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>

</body>
</html>
