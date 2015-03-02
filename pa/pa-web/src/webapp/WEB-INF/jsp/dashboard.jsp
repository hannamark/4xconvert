<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><c:out value="${dashboardTitle}" escapeXml="false"/></title>
<s:head />

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/showhide.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/control.tabs.js"/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl" />
<c:set scope="request" var="suAbs" value="${sessionScope.isSuAbstractor==true}"></c:set>
<c:set scope="request" var="admAbs" value="${sessionScope.isAdminAbstractor==true || suAbs}"></c:set>
<c:set scope="request" var="sciAbs" value="${sessionScope.isScientificAbstractor==true || suAbs}"></c:set>

<script type="text/javascript" language="javascript">
    
	function handleAction(action) {
		document.forms[0].action = "dashboard" + action + ".action";
		document.forms[0].submit();
	}
	
	function handleCheckoutAction(action) {
        var studyProtocolId = '${sessionScope.summaryDTO.studyProtocolId}';
        if (!allowAction(action)) {
            return;
        }
        if ((action == 'adminCheckIn') || (action == 'scientificCheckIn') || (action == 'adminAndScientificCheckIn')
                || (action == 'checkInSciAndCheckOutToSuperAbs')) {
        	 showCommentsBox(action);
        } else {
        	var form = $('checkoutForm');
        	form.action="studyProtocol" + action + ".action?studyProtocolId=" + studyProtocolId;
            form.submit();
        }       
    }
	
	 function saveCheckin(action) {		 
         var form = $('dashboardForm');
         var commandVal = document.getElementById('commentCommand').value;
         var comment = document.getElementById('comments').value;
         if (comment==null){
        	    return;
         }
         form.elements["checkInReason"].value = comment;
         handleAction(commandVal);
     }
	
	 var eltDims = null;
     function showCommentsBox(action) {
         document.getElementById('commentCommand').value=action;
         // retrieve required dimensions
         if (eltDims == null) {
             eltDims = $('comment-dialog').getDimensions();
         }
         var browserDims = $(document).viewport.getDimensions();

         // calculate the center of the page using the browser and element dimensions
         var y  = (browserDims.height - eltDims.height) / 2;
         var x = (browserDims.width - eltDims.width) / 2;    
         
         $('comment-dialog').absolutize(); 
         $('comment-dialog').style.left = x + 'px';
         $('comment-dialog').style.top = y + 'px';
         $('comment-dialog').show();
     }    
	
	function allowAction(action) {
        if (((action == 'adminCheckIn' || action == 'adminAndScientificCheckIn') 
                && (trialHasStatusErrors || trialHasStatusWarnings)) || 
                (action == 'scientificCheckIn' && suAbs && !checkedOutForAdmin && (trialHasStatusErrors || trialHasStatusWarnings)) ){
            displayStatusTransitionMessages(action);
            return false;
        } 
        if (action == 'scientificCheckIn' && sciAbs && !checkedOutForAdmin && trialHasStatusErrors) {
            displayStatusTransitionMessageAndPickSuperAbstractor(action);
            return false;
        }
        return true;
    }
	
	 function displayStatusTransitionMessageAndPickSuperAbstractor(action) {
         var dialogID = '#pickSuperAbstractor';                
         jQuery(dialogID).dialog('open');  
         jQuery(dialogID).attr('act', action);               
     }
	 
	 function displayStatusTransitionMessages(action) {
         var dialogID = '';
         if (trialHasStatusErrors && !trialHasStatusWarnings) {
             dialogID = '#transitionErrors';
         } else if (!trialHasStatusErrors && trialHasStatusWarnings) {
             dialogID = '#transitionWarnings';
         } else if (trialHasStatusErrors && trialHasStatusWarnings) {
             dialogID = '#transitionErrorsAndWarnings';
         }
         jQuery(dialogID).dialog('open');  
         jQuery(dialogID).attr('act', action);               
     }

	function viewProtocol(pid) {
		$('studyProtocolId').value = pid;
		handleAction('view');
		return false;
	}

	function resetValues() {
		$('dashboardForm').getElements().each(function(el) {
			if (el.type=='text') {
				   el.clear();				  
			}
			if (el.type=='checkbox' || el.type=='radio') {               
                el.checked = false;
            }
		});
		$$('option').each(function(optEl) {
			optEl.selected = false;
		});
		$('submittingOrgId').value = '';
		if ($('error_msg_div')!=null) {
			$('error_msg_div').hide();
		}
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction();
			return false;
		}
	}

	function lookupSubmittingOrg() {
		showPopup('${lookupOrgUrl}', function() {
		}, 'Select Organization');
	}

	function setorgid(orgId, orgName) {
		$('submittingOrgId').value = orgId;
		$('submittedBy').value = orgName;
	}

	function loadDiv(orgId) {
	}

	(function($) {    
        //******************
        //** On DOM Ready **
        //******************
        $(function () {
        	$( "#transitionErrors" ).dialog({
                modal: true,
                autoOpen : false,                         
                buttons: {
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#transitionErrorsAndWarnings" ).dialog({
                modal: true,
                autoOpen : false,                         
                buttons: {
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#transitionWarnings" ).dialog({
                modal: true,
                autoOpen : false,         
                width : 450,
                buttons: {
                  "Proceed with Check-in": function() {
                        $(this).dialog("close");
                        trialHasStatusErrors = false;
                        trialHasStatusWarnings = false;
                        handleCheckoutAction($(this).attr('act'));
                        
                  },
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#pickSuperAbstractor" ).dialog({
                modal: true,
                autoOpen : false,         
                width : 450,
                buttons: {
                  "Proceed with Check-in": function() {
                      if ($.isNumeric($('#supAbsId').val())) {
                          document.forms[0].elements["superAbstractorId"].value = $('#supAbsId').val();
                          $(this).dialog("close");                                
                          handleCheckoutAction('checkInSciAndCheckOutToSuperAbs'); 
                      }                                                                 
                  },                           
                  "Cancel": function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        });
	}(jQuery));
        
        var trialStatusHistoryURL = '<c:url value='/protected/studyOverallStatus.action'/>';
        
        function goToTrialStatusHistory() {
             var form = document.forms[0];
             form.action=trialStatusHistoryURL;
             form.submit();
        }
        
        var trialHasStatusErrors = ${sessionScope.trialHasStatusErrors==true};
        var trialHasStatusWarnings = ${sessionScope.trialHasStatusWarnings==true};
        
        var adminAbs = ${sessionScope.isAdminAbstractor==true};
        var sciAbs =    ${sessionScope.isScientificAbstractor==true};
        var suAbs = ${sessionScope.isSuAbstractor==true};
        
        var checkedOutForAdmin = ${sessionScope.trialSummary.adminCheckout.checkoutBy != null};
        var checkedOutForSci = ${sessionScope.trialSummary.scientificCheckout.checkoutBy != null};
       
	
	Event.observe(window, 'load', function() {
		
		addCalendar("Cal1", "Select Date", "submittedOnOrAfter",
				"dashboardForm");
		addCalendar("Cal2", "Select Date", "submittedOnOrBefore",
				"dashboardForm");
		setWidth(90, 1, 15, 1);
		setFormat("mm/dd/yyyy");
		var tabs = new Control.Tabs($('maintabs'));
		<c:if test="${toggleResultsTab==true}">
		tabs.setActiveTab('results');
		updateHelpTopic('results');
		</c:if>
		<c:if test="${toggleDetailsTab==true}">
		tabs.setActiveTab('details');
		updateHelpTopic('details');
		</c:if>
		
		if ($('resultsid')!=null) {
		    Event.observe($('resultsid'), "click", function() {
		             updateHelpTopic('results');
		    });
		}
	    
	    if($('detailsid') !=null) {
	    	 Event.observe($('detailsid'), "click", function() {
	             updateHelpTopic('details');
	         });
	    }
	    
	    if ($('searchid')!=null) {
		    Event.observe($('searchid'), "click", function() {
		             updateHelpTopic('search');
		    }); 
	    }
	});
	
	function updateHelpTopic(tabType) {
		if(tabType =='results') {
		       if("${sessionScope.isAdminAbstractor}" == "true") {
		    	   document.getElementById('pageHelpid').onclick = function() {
                       Help.popHelp('dashboardadmin');
                   } 
	            }
	              if("${sessionScope.isScientificAbstractor}"  == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardsci');
	                    }
	                }
	              if("${sessionScope.isAdminAbstractor}"  == "true" && "${sessionScope.isScientificAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardadmin-sci');
	                    }
	                }
	              if("${sessionScope.isSuAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardresults');
	                    }
	                }
		} 
		if(tabType =='search') {
			if("${sessionScope.isSuAbstractor}" == "true") {
                document.getElementById('pageHelpid').onclick = function() {
                    Help.popHelp('dashboardsuper');
                }
            }
		}
		if (tabType =='details') {
		     if("${sessionScope.isAdminAbstractor}" == "true") {
		            document.getElementById('pageHelpid').onclick = function() {
		                Help.popHelp('admindetails');
		            }
		        }
		          if("${sessionScope.isScientificAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('scidetails');
		                }
		            }
		          if("${sessionScope.isAdminAbstractor}"  == "true"&& "${sessionScope.isScientificAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('sciadmindetails');
		                }
		            }
		          if("${sessionScope.isSuAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('dashboarddetails');
		                }
		            }
		}
		return true;
	}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
	    <c:out value="${dashboardTitle}" escapeXml="false"/>		
	</h1>
    <c:if test="${sessionScope.isAdminAbstractor==true}">
    <c:set var="topic" scope="request" value="dashboardadmin" />
    </c:if>
    <c:if test="${sessionScope.isScientificAbstractor==true}">
    <c:set var="topic" scope="request" value="dashboardsci" />
    </c:if>
    <c:if test="${sessionScope.isAdminAbstractor==true && sessionScope.isScientificAbstractor==true}">
    <c:set var="topic" scope="request" value="dashboardadmin-sci" />
    </c:if>
    <c:if test="${sessionScope.isSuAbstractor==true}">
    <c:set var="topic" scope="request" value="dashboardsuper" />
    </c:if>
	<jsp:useBean id="currentDate" class="java.util.Date" scope="request"></jsp:useBean>
	<div class="box" id="filters">
		<s:form id="dashboardForm">
			<s:token />
			<pa:sucessMessage />
			<pa:failureMessage />
			<s:hidden id="studyProtocolId" name="studyProtocolId" />
			<s:hidden name="checkInReason" id="checkInReason"/>
			 <s:hidden name="superAbstractorId"/>
			<table class="form">
				<c:if test="${dashboardSearchResults!=null}">
					<tr>
						<td align="right" nowrap="nowrap" style="padding: 0; margin: 0;">Results
							as of <fmt:formatDate value="${currentDate}"
								pattern="MM/dd/yyyy -- hh:mm:ss aaa" /> &nbsp;&nbsp; <input
							type="button" value="Refresh" onclick="handleAction('${suAbs?'search':'execute'}');" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<ul id="maintabs" class="tabs">
						    <c:if test="${suAbs}">
							     <li><a id="searchid" href="#search">Search Criteria</a></li>
							</c:if>
							<c:if test="${dashboardSearchResults!=null}">
								<li><a id="resultsid" href="#results" >${suAbs?'Results':'Trial List'}</a></li>
							</c:if>
							<c:if test="${queryDTO!=null}">
								<li><a id="detailsid" href="#details">Details</a></li>
							</c:if>
						</ul>
						<div id="tabboxwrapper">
						    <c:if test="${suAbs}">						    
							<div id="search" class="box"
								style="${toggleResultsTab || toggleDetailsTab?'display:none;':''}">
								<table class="form">
                                    <tr>
                                        <td scope="row" class="label"><label for="assignee"><fmt:message
                                                    key="dashboard.assignedTo" /></label></td>
                                        <td><s:select id="assignee" name="assignee"
                                                list="assigneeList" headerKey="" headerValue=""
                                                value="assignee" cssStyle="width:206px" /></td>
                                    </tr>								
									<tr>
										<td scope="row" class="label"><label for="checkedOutBy"><fmt:message
													key="dashboard.checkedOutBy" /></label></td>
										<td><s:select id="checkedOutBy" name="checkedOutBy"
												list="checkedOutByList" headerKey="" headerValue=""
												value="checkedOutBy" cssStyle="width:206px" /></td>
										<td scope="row" class="label"><label
											for="processingPriority"><fmt:message
													key="dashboard.processingPriority" /></label></td>
										<td rowspan="2"><s:select id="processingPriority"
												name="processingPriority"
												list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"
												headerKey="" headerValue="All" multiple="true"
												value="processingPriority" cssStyle="width:206px" /></td>

									</tr>
									<tr>
										<td scope="row" class="label"><label
											for="submittedOnOrBefore"><fmt:message
													key="dashboard.submittedBetween" /></label></td>
										<label for="submittedOnOrAfter" style="display:none">Do not show </label>
										<td nowrap="nowrap"><s:textfield id="submittedOnOrAfter"
												name="submittedOnOrAfter" maxlength="10" size="10" /> <a
											href="javascript:showCal('Cal1')"> <img
												src="${pageContext.request.contextPath}/images/ico_calendar.gif"
												alt="Select Date" class="calendaricon"/></a> &nbsp;and&nbsp; <s:textfield
												id="submittedOnOrBefore" name="submittedOnOrBefore"
												maxlength="10" size="10" /> <a
											href="javascript:showCal('Cal2')"> <img
												src="${pageContext.request.contextPath}/images/ico_calendar.gif"
												alt="Select Date" class="calendaricon" />
										</a></td>
									</tr>
									<tr>
									<tr>
										<td scope="row" class="label"><label for="submittedBy"><fmt:message
													key="dashboard.submittedBy" /></label></td>
										<td nowrap="nowrap"><s:textfield id="submittedBy"
												name="submittedBy" cssStyle="width:100%;" readonly="true" /></td>
										<td align="left" colspan="2">
											<ul style="margin-top: -1px;">
												<li style="padding-left: 0"><a
													href="javascript:void(0)" class="btn"
													onclick="lookupSubmittingOrg();"><span class="btn_img"><span
															class="organization">Look Up</span></span></a></li>
											</ul> <s:hidden name="submittingOrgId" id="submittingOrgId" />
										</td>
									</tr>
									<tr>
										<td colspan="4"><div class="line"></div></td>
									</tr>
									<tr>
										<s:set name="submissionTypeValues"
											value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
										<td scope="row" class="label"><label for="submissionType"><fmt:message
													key="dashboard.submissionType" /></label></td>
										<td><s:select headerKey="" headerValue="All"
												multiple="true" id="submissionType" name="submissionType"
												list="#submissionTypeValues" value="submissionType"
												cssStyle="width:206px" /></td>
										<td scope="row" class="label"><label for="nciSponsored"><fmt:message
													key="dashboard.nciSponsored" /></label></td>
										<td><s:select id="nciSponsored" name="nciSponsored"
												list="#{'true':'NCI Sponsored','false':'Not NCI Sponsored'}"
												headerKey="" headerValue="All" size="4" value="nciSponsored"
												cssStyle="width:206px" /></td>

									</tr>

									<tr>
										<td scope="row" class="label"><label for="onHoldStatus"><fmt:message
													key="dashboard.onHoldStatus" /></label></td>
										<td><s:select headerKey="" headerValue="All"
												multiple="true" id="onHoldStatus" name="onHoldStatus"
												list="#{'onhold':'On-Hold (now)','notonhold':'Not On-Hold (now)','onholdanytime':'On-Hold (at anytime)'}"
												value="onHoldStatus" cssStyle="width:206px" /></td>
										<td scope="row" class="label"><label
											for="ctepDcpCategory"><fmt:message
													key="dashboard.ctepDcpCategory" /></label></td>
										<td><s:select id="ctepDcpCategory" name="ctepDcpCategory"
												list="#{'ctepdcp':'CTEP and DCP PIO Trials Only','ctep':'CTEP PIO Trials Only','dcp':'DCP PIO Trials Only','exclude':'Exclude CTEP and DCP Trials'}"
												headerKey="" headerValue="All" size="5"
												value="ctepDcpCategory" cssStyle="width:206px" /></td>
									</tr>

									<tr>
										<s:set name="OnholdReasonCodes"
											value="@gov.nih.nci.pa.enums.OnholdReasonCode@getDisplayNames()" />
										<td scope="row" class="label"><label for="onHoldReason"><fmt:message
													key="dashboard.onHoldReason" /></label></td>
										<td><s:select headerKey="" headerValue="All" size="5"
												multiple="true" id="onHoldReason" name="onHoldReason"
												list="#OnholdReasonCodes" value="onHoldReason"
												cssStyle="width:206px" /></td>
										<td></td>
										<s:set name="milestoneCodes"
											value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
										<td nowrap="nowrap">
											<fieldset style="margin-left: 0px;">
												<legend>
													<fmt:message key="dashboard.milestones" />
												</legend>
												<s:radio name="milestoneType" id="milestoneType"
													value="milestoneType" list="#{'any':'Has milestone'}"></s:radio>
												<br />
												<s:radio name="milestoneType" id="milestoneType"
													value="milestoneType"
													list="#{'last':'Has a last milestone'}"></s:radio>
												<br />
												<label for="milestone" style="display:none">milestone </label>
												<s:select headerKey="" headerValue="" id="milestone"
													name="milestone" list="#milestoneCodes" value="milestone"
													cssStyle="width:206px" />
											</fieldset>
										</td>
									</tr>
									<tr>
										<s:set name="documentWorkflowStatusCodeValues"
											value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
										<td scope="row" class="label"><label
											for="processingStatus"><fmt:message
													key="dashboard.processingStatus" /></label></td>
										<td><s:select headerKey="" headerValue="All" size="5"
												multiple="true" id="processingStatus"
												name="processingStatus"
												list="#documentWorkflowStatusCodeValues"
												value="processingStatus" cssStyle="width:206px" /></td>
										<td scope="row" class="label" style="vertical-align: middle;"><label
											for="showTrialsReadyFor"><fmt:message
													key="dashboard.showTrialsReadyFor" /></label></td>
										<td>
											<table class="milestone_matrix" border="1">
												<tr>
													<td class="noborder">&nbsp;</td>
													<td class="label"><fmt:message
															key="dashboard.abstraction" /></td>
													<td class="label"><fmt:message key="dashboard.qc" /></td>
												</tr>
												<tr>
													<td class="label"><fmt:message
															key="dashboard.administrative" /></td>
													<td><s:checkbox name="adminAbstraction" id="showTrialsReadyFor"/></td>
													<td><s:checkbox name="adminQC" id="showTrialsReadyFor"/></td>
												</tr>
												<tr>
													<td class="label"><fmt:message
															key="dashboard.scientific" /></td>
													<td><s:checkbox name="scientificAbstraction" id="showTrialsReadyFor"/></td>
													<td><s:checkbox name="scientificQC" id="showTrialsReadyFor"/></td>
												</tr>
											</table>
										</td>
									</tr>

								</table>
								<div class="actionsrow">
									<del class="btnwrapper">
										<ul class="btnrow">
											<li><s:a href="javascript:void(0)" cssClass="btn"
													onclick="handleAction('search');">
													<span class="btn_img"><span class="search">Search</span></span>
												</s:a> <s:a href="javascript:void(0)" cssClass="btn"
													onclick="resetValues();return false">
													<span class="btn_img"><span class="cancel">Reset</span></span>
												</s:a></li>
										</ul>
									</del>
								</div>
							</div>
                            </c:if>
                            
							<div id="results" class="box"
								style="${toggleResultsTab?'':'display:none;'}">
								<c:set var="requestURI" value="dashboardloopback.action"
									scope="request" />
								<display:table class="data" sort="list" uid="results" pagesize="2147483647"
									defaultsort="9"
									decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
									name="${dashboardSearchResults}" requestURI="${requestURI}"
									export="true">									
                                    <display:setProperty name="paging.banner.item_name" value="trial"/>
                                    <display:setProperty name="paging.banner.items_name" value="trials"/> 
                                    <display:setProperty name="paging.banner.group_size" value="0"/>  									
									<display:setProperty name="export.xml" value="false" />
									<display:setProperty name="export.excel.filename"
										value="dashboardSearchResults.xls" />
									<display:setProperty name="export.excel.include_header"
										value="true" />
									<display:setProperty name="export.csv.filename"
										value="dashboardSearchResults.csv" />
									<display:setProperty name="export.csv.include_header"
										value="true" />

									<display:column  class="title"
										titleKey="studyProtocol.nciIdentifier" sortable="true"
										media="html"
										headerClass="sortable">
										<!--  ${results.nciIdentifierTruncated} -->
										<a href="javascript:void(0);"
											onclick="viewProtocol('${results.studyProtocolId}')"> <c:out
												value="${results.nciIdentifierTruncated}"></c:out>
										</a>
									</display:column>
                                    <display:column  class="title"
                                        titleKey="studyProtocol.nciIdentifier" sortable="true"
                                        media="excel csv xml" property="nciIdentifierTruncated"
                                        headerClass="sortable">                                        
                                    </display:column>									

                                    <display:column  title="ClinicalTrials.gov Identifier" property="nctIdentifier"
                                        media="excel csv xml"/>									
						            <display:column  titleKey="studyProtocol.ctepIdentifier" property="ctepId"
						                media="excel csv xml"/>
						            <display:column  titleKey="studyProtocol.dcpIdentifier" property="dcpId"
						                media="excel csv xml"/>									
                                    <display:column  title="CDR ID" property="cdrId"
                                        media="excel csv xml"/>
                                    <display:column  title="Amendment #" property="amendmentNumber"
                                        media="excel csv xml"/>
                                    <display:column  title="Trial Category" property="trialCategory"
                                        media="excel csv xml"/>
                                    <display:column  title="Summary 4 Funding" property="summary4FundingSponsorType"
                                        media="excel csv xml"/>
                                        
                                    <display:column  title="On Hold Date" property="recentOnHoldDate" format="{0,date,MM/dd/yyyy}"
                                        media="excel csv xml"/>
                                    <display:column  title="Off Hold Date" property="recentOffHoldDate" format="{0,date,MM/dd/yyyy}"
                                        media="excel csv xml"/>
                                    <display:column  title="On Hold Reason" property="recentHoldReason"
                                        media="excel csv xml"/>
                                    <display:column  title="On Hold Description" property="recentHoldDescription"
                                        media="excel csv xml"/>

                                        
						            <display:column titleKey="studyProtocol.trialtype" media="excel csv xml">
						                <c:out value="${results.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
						            </display:column>
                                    <display:column  title="NCI Sponsored" media="excel csv xml">
                                        <c:out value="${results.sponsorName=='National Cancer Institute'?'Yes':'No'}"/>
                                    </display:column>
                                    <display:column  title="Processing Status" property="documentWorkflowStatusCode.code" media="excel csv xml"/>                                    
                                    <display:column  title="Processing Status Date" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>
                                    <display:column  title="Admin Check out Name" property="adminCheckout.checkoutByUsername" media="excel csv xml"/>
                                    <display:column  title="Admin Check out Date" property="adminCheckout.checkoutDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>
                                    <display:column  title="Scientific Check out Name" property="scientificCheckout.checkoutByUsername" media="excel csv xml"/>
                                    <display:column  title="Scientific Check out Date" property="scientificCheckout.checkoutDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>
                                    						            															
									<display:column escapeXml="true" sortable="true"
										titleKey="studyProtocol.submissionType"
										property="submissionTypeCode.code" headerClass="sortable" />
									<display:column title="Trial Category" property="trialCategory"
										sortable="true" headerClass="sortable" />
									<display:column title="CTEP/DCP" property="ctepOrDcp"
										sortable="true" headerClass="sortable" />
									<display:column title="Submitting Organization"
										property="submitterOrgName" sortable="true"
										headerClass="sortable" />
									<display:column  title="Submission Date"
										property="lastCreated.dateLastCreated"
										format="{0,date,MM/dd/yyyy}" sortable="true"
										headerClass="sortable" />
									<display:column  title="Last Milestone"
										property="milestones.lastMilestone.milestone.code"
										sortable="true" headerClass="sortable" />
									<display:column  title="Last Milestone Date"
										property="milestones.lastMilestone.milestoneDate"
										format="{0,date,MM/dd/yyyy}" sortable="true"
										headerClass="sortable" />
									<display:column title="Submission Source"
										property="studySource" sortable="true"
										headerClass="sortable" />
									<display:column title="Processing Priority"
										property="processingPriority" sortable="true"
										headerClass="sortable" />
                                    <display:column  title="Comments" property="processingComments" media="excel csv xml"/>
                                    										
                                    <display:column title="This Trial is" 
                                        class="this_trial_is" sortable="true" headerClass="sortable"><c:out escapeXml="false"
                                            value="${results.checkedOutByMe?'Checked out by me
':''}" /><c:out escapeXml="false"
                                            value="${results.readyForAdminProcessing && admAbs?'Ready for Admin Processing
':''}" /><c:out escapeXml="false"
                                            value="${results.readyForAdminQC && admAbs?'Ready for Admin QC
':''}" /><c:out escapeXml="false"
                                            value="${results.readyForTSRSubmission?'Ready for TSR Submission
':''}" /><c:out escapeXml="false"
                                            value="${results.submittedNotAccepted?'Submitted -- not accepted
':''}" /><c:out escapeXml="false"
                                            value="${results.readyForScientificProcessing && sciAbs?'Ready for Scientific Processing
':''}" /><c:out escapeXml="false"
                                            value="${results.readyForScientificQC && sciAbs?'Ready for Scientific QC
':''}" /><c:if
                                            test="${results.documentWorkflowStatusCode.code=='On-Hold'}">On hold since ${results.onHoldDate!=null?results.onHoldDate:'N/A'},
reason: ${not empty results.onHoldReasons?results.onHoldReasons:'N/A'}
                                        </c:if>
                                    </display:column>
                                    
                                    <s:set var="milestoneCodesForReporting" scope="request"
                                           value="@gov.nih.nci.pa.enums.MilestoneCode@getMilestoneCodesForReporting()" />
                                    <c:forEach items="${milestoneCodesForReporting}" var="milestone">
                                        <display:column title="${milestone.code}" media="excel csv xml">
                                            <s:set scope="request" var="milestoneDate" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).milestoneDate}"/>                                            
                                            <fmt:formatDate value="${requestScope.milestoneDate}" pattern="MM/dd/yyyy" />
                                        </display:column>
                                        <display:column title="Added By" media="excel csv xml">
                                            <s:set scope="request" var="milestoneCreator" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).creator}"/>
                                            <c:out value="${requestScope.milestoneCreator}" escapeXml="false"/>
                                        </display:column>
                                        <display:column title="Added On" media="excel csv xml">
                                            <s:set scope="request" var="milestoneCreateDate" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).createDate}"/>
                                            <fmt:formatDate value="${requestScope.milestoneCreateDate}" pattern="MM/dd/yyyy" />
                                        </display:column>                                        
                                    </c:forEach>                                    
								</display:table>
							</div>
							
							<div id="details" class="box"
								style="${toggleDetailsTab?'':'display:none;'}">
								<table class="form">
								    <tr>
								        <td colspan="4" nowrap="nowrap" class="label">
								            <s:if test="%{!checkoutCommands.isEmpty()}">
				                                <s:set name="checkoutCommands" scope="page" value="%{checkoutCommands}" /> 
				                                <c:forEach items="${checkoutCommands}" var="command" varStatus="vstat">			                                    
			                                        <a href="javascript:void(0)" onclick="handleCheckoutAction('${command}')">
			                                            <fmt:message key="studyProtocolView.button.${command}" />
			                                        </a>&nbsp;&nbsp; 		                                    
				                                </c:forEach>
			                                </s:if>	
                                            <a href="checkOutHistory.action">
                                                Check out History
                                            </a>&nbsp;&nbsp; 	
                                            
                                            <c:choose>
                                                <c:when test="${queryDTO.documentWorkflowStatusCode.code=='Submitted' || queryDTO.documentWorkflowStatusCode.code=='Amendment Submitted'}">
                                                    <a href="studyProtocolview.action?studyProtocolId=${summaryDTO.studyProtocolId}">
                                                        Validate
                                                    </a>&nbsp;&nbsp;                                                 
                                                </c:when>
                                                <c:when test="${queryDTO.documentWorkflowStatusCode.code=='Rejected'}">
                                                </c:when>
                                                <c:otherwise>
	                                                <a href="studyProtocolview.action?studyProtocolId=${summaryDTO.studyProtocolId}">
	                                                    Abstract
	                                                </a>&nbsp;&nbsp;                                                 
                                                </c:otherwise>                                                
                                            </c:choose>   
                                            <a href="ajaxAbstractionCompletionviewTSR.action">
                                                       View TSR
                                            </a>&nbsp;                                          
								        </td>
								    </tr>
									<tr>
										<td scope="row" class="label">Submission Type</td>
										<td>${queryDTO.submissionTypeCode.code}</td>
										<td scope="row" class="label">Processing Status</td>
										<td>${queryDTO.documentWorkflowStatusCode.code}</td>
									</tr>
									<tr>
										<td scope="row" class="label">NCI ID</td>
										<td>${queryDTO.nciIdentifierTruncated}</td>
										<td scope="row" class="label">Processing Status Date</td>
										<td><fmt:formatDate
												value="${queryDTO.documentWorkflowStatusDate}"
												pattern="MM/dd/yyyy" />

										</td>
									</tr>
									<tr>
										<td scope="row" class="label">ClinicalTrials.gov Identifier</td>
										<td><c:out value="${nctIdentifier}"></c:out></td>
										<td scope="row" class="label">Admin Check-out</td>
										<td><c:out value="${queryDTO.adminCheckout.fullName}" />
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">CTEP ID</td>
										<td><c:out value="${queryDTO.ctepId}"></c:out></td>
										<td scope="row" class="label">Scientific Check-out</td>
										<td><c:out
												value="${queryDTO.scientificCheckout.fullName}" /></td>
									</tr>
									<tr>
										<td scope="row" class="label">DCP ID</td>
										<td><c:out value="${queryDTO.dcpId}"></c:out></td>										
									</tr>
                                    <tr>
                                        <td scope="row" class="label">CDR ID</td>
                                        <td><c:out value="${not empty summaryDTO.cdrId?summaryDTO.cdrId:'N/A'}"/></td>
                                        <td colspan="2" rowspan="16">
                                            <display:table class="data" sort="list" uid="milestones"                                                                                             
                                                name="${summaryDTO.milestoneHistory}"
                                                export="false">                                             
                                                <display:column title="Milestone"
                                                    property="milestone.code" sortable="false"
                                                    headerClass="sortable" />
                                                <display:column title="Date"
                                                    property="milestoneDate"
                                                    format="{0,date,MM/dd/yyyy}" sortable="false"
                                                    headerClass="sortable" />                                               
                                                <display:column title="Creator"
                                                    property="creator" sortable="false"
                                                    headerClass="sortable" />                                               
                                            </display:table>                                        
                                        </td>
                                    </tr>									
									<tr>
										<td scope="row" class="label">Submitting Organization</td>
										<td><c:out value="${queryDTO.submitterOrgName}"></c:out>
										</td>										
									</tr>
									<tr>
										<td scope="row" class="label">Submission Date</td>
										<td><fmt:formatDate
												value="${queryDTO.lastCreated.dateLastCreated}"
												pattern="MM/dd/yyyy" />
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Amendment #</td>
										<td><c:out value="${not empty summaryDTO.amendmentNumber?summaryDTO.amendmentNumber:'N/A'}"></c:out>
										</td>
									</tr>
                                    <tr>
                                        <td scope="row" class="label">Trial Category</td>
                                        <td><c:out value="${queryDTO.trialCategory}"></c:out>
                                        </td>
                                    </tr>	
                                    <tr>
                                        <td scope="row" class="label">Trial Type</td>
                                        <td>
                                            <c:out value="${queryDTO.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
                                        </td>
                                    </tr>   
                                    <tr>
                                        <td scope="row" class="label">Summary 4 Funding</td>
                                        <td><c:out value="${func:capitalizeFully(fn:replace(queryDTO.summary4FundingSponsorType, '_',' '))}"></c:out>
                                        </td>
                                    </tr>  
                                    <tr>
                                        <td scope="row" class="label">On Hold Date</td>
                                        <td><c:out value="${not empty queryDTO.recentOnHoldDate?func:format(queryDTO.recentOnHoldDate,'MM/dd/yyyy'):'N/A'}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td scope="row" class="label">Off Hold Date</td>
                                        <td><c:out value="${not empty queryDTO.recentOffHoldDate?func:format(queryDTO.recentOffHoldDate,'MM/dd/yyyy'):'N/A'}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td scope="row" class="label">On Hold Reason</td>
                                        <td><c:out value="${not empty queryDTO.recentHoldReason?queryDTO.recentHoldReason:'N/A'}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td scope="row" class="label">NCI Sponsored?</td>
                                        <td><c:out value="${queryDTO.sponsorName=='National Cancer Institute'?'Yes':'No'}"></c:out>
                                        </td>
                                    </tr>
                                    <tr><td>&nbsp;</td></tr>
                                    <tr>
                                        <td scope="row" class="label"><label for="assignedTo">Assigned To</label></td>
                                        <td> 
                                            <s:set name="abstractorsList"
                                                value="@gov.nih.nci.pa.service.util.CSMUserService@getInstance().abstractors" />
                                            <s:select id="assignedTo" name="assignedTo"
                                                list="#abstractorsList" headerKey="" headerValue="Unassigned"
                                                value="#session.summaryDTO.assignedUserId" cssStyle="width:206px" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td scope="row" class="label">Submission Source</td>
                                        <td><c:out value="${queryDTO.studySource}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td scope="row" class="label"><label for="newProcessingPriority">Processing Priority</label></td>
                                        <td> 
                                            <s:select id="newProcessingPriority"
                                                name="newProcessingPriority"
                                                list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"                                               
                                                value="#session.summaryDTO.processingPriority" cssStyle="width:206px" />
                                            
                                            <c:if test="${!suAbs}">
                                                <script type="text/javascript">
                                                      Event.observe(window, "load", function () {
                                                          $('assignedTo').disabled = true;
                                                          $('newProcessingPriority').disabled = true;
                                                      });
                                                </script>
                                            </c:if>                                        
                                                                                        
                                        </td>
                                    </tr>  
                                    <tr>
                                        <td scope="row" class="label" colspan="2"><label for="processingComments">Trial Processing Comments</label></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
	                                        <s:textarea id="processingComments" name="processingComments"    
	                                            maxlength="4000" cssClass="charcounter"                                        
	                                            cssStyle="width: 100%;" rows="5">
										        <s:param name="value">
										            <s:property value="#session.summaryDTO.processingComments"/>
										        </s:param>                                            
	                                        </s:textarea> 
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td colspan="4" align="center">
			                                <div class="actionsrow">
			                                    <del class="btnwrapper">
			                                        <ul class="btnrow">
			                                            <li><s:a href="javascript:void(0)" cssClass="btn"
			                                                    onclick="handleAction('save');">
			                                                    <span class="btn_img"><span class="search">Save</span></span>
			                                                </s:a></li>
			                                        </ul>
			                                    </del>
			                                </div>                                        
                                        </td>
                                    </tr>                                                                                   								
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<pa:trialCheckInWarnings/>
		</s:form>
		<s:form action="studyProtocol" id="checkoutForm">
		      <s:token></s:token>		      
		</s:form>
	</div>
</body>
</html>
