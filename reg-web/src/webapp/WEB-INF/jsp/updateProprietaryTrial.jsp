<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Update <fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <script type="text/javascript" src="${scriptPath}/js/subModalcommon.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/subModal.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <script type="text/javascript">
            var bla = <s:property value="trialDTO.participatingSitesList.size"/>;
            jQuery(function() {
                for (var i = 0; i < <s:property value="trialDTO.participatingSitesList.size"/>; i++) {
                    addCalendar("Cal1-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].recruitmentStatusDate", "updateProprietaryTrial");
                    addCalendar("Cal2-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].dateOpenedforAccrual", "updateProprietaryTrial");
                    addCalendar("Cal3-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].dateClosedforAccrual", "updateProprietaryTrial");
                }
                setWidth(90, 1, 15, 1);
                setFormat("mm/dd/yyyy");
            });
            var orgid;
            var chosenname;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;
            function setorgid(orgIdentifier, oname) {
                orgid = orgIdentifier;
                chosenname = oname.replace(/&apos;/g,"'");
            }
            
            function setpersid(persIdentifier, sname, email, phone) {
                persid = persIdentifier;
                chosenname = sname.replace(/&apos;/g,"'");
            }
            
            function lookup4loadleadorg() {
                showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Lead Organization');
            }
            
            function lookup4loadSummary4Sponsor() {
                showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
            }

            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
            }
            
            function loadSummary4SponsorDiv() {
                $("trialDTO.summaryFourOrgName").value = chosenname;
                $('trialDTO.summaryFourOrgIdentifier').value = orgid;
            }

            function reviewProtocol () {
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
                submitFirstForm("review", "updateProprietaryTrialreview.action");
            }
            
            function cancelProtocol() {
                submitFirstForm("cancle", "updateProprietaryTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function toggledisplay (it, box) {
              var vis = (box.checked) ? "block" : "none";
              $(it).style.display = vis;
            }
            
            function toggledisplay2 (it) {
              var vis = $(it).style.display;
              $(it).style.display = (vis == "block") ? "none" : "block";
            }
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="submit.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="updatetrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="updateProprietaryTrial" method="POST" enctype="multipart/form-data">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="error_msg">
                        <s:actionerror/>
                    </div>
                </s:if>
                <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
                <s:hidden name="trialDTO.sitePiIdentifier" id="trialDTO.sitePiIdentifier"/>
                <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
                <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                    <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                </c:if>
                <s:hidden name="page" />
                <table class="form">
                    <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelKey="view.trial.identifier" noLabelTag="true">
                        <s:property value="trialDTO.assignedIdentifier"/>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.leadOrganizationName" labelKey="view.trial.leadOrganization">
                       <s:property value="trialDTO.leadOrganizationName" />
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.leadOrgTrialIdentifier" labelKey="submit.trial.leadOrgidentifier">
                        <s:property value="trialDTO.leadOrgTrialIdentifier" />                        
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.nctIdentifier" labelKey="submit.trial.nctNumber">
                        <s:property value="trialDTO.nctIdentifier"/>
                    </reg-web:valueRow>
                    <reg-web:titleRow titleKey="submit.trial.trialDetails"/>
                    <reg-web:valueRow labelFor="trialDTO.officialTitle" labelKey="submit.trial.title">
                        <s:property value="trialDTO.officialTitle"/>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialType" labelKey="submit.trial.type">
                        <c:out value="${trialDTO.trialType!='NonInterventional'?'Interventional':'Non-interventional'}"></c:out>
                    </reg-web:valueRow>
                    
                    <c:if test="${trialDTO.trialType=='NonInterventional'}">
						<reg-web:valueRow labelFor="trialDTO.studySubtypeCode" labelKey="submit.trial.studySubtypeCode">
						     <s:property value="trialDTO.studySubtypeCode"/>						      
						</reg-web:valueRow>                   
                    </c:if>
        
                    <reg-web:valueRow labelFor="trialDTO.primaryPurposeCode" labelKey="submit.trial.purpose">
                        <s:property value="trialDTO.primaryPurposeCode" />
                    </reg-web:valueRow>
          
		            <c:if test="${trialDTO.primaryPurposeOtherText=='Other'}">	       
		                <reg-web:valueRow labelFor="submit.trial.otherPurposeText" labelKey="submit.trial.otherPurposeText">
		                    <s:property value="trialDTO.primaryPurposeOtherText"/>
		                </reg-web:valueRow>
		            </c:if>
          
		          <c:if test="${trialDTO.trialType!='NonInterventional'}">	    
		                <reg-web:valueRow labelFor="submit.trial.secondaryPurpose" labelKey="submit.trial.secondaryPurpose">
		                    <s:property value="trialDTO.secondaryPurposesAsString" />
		                </reg-web:valueRow>                      
		          </c:if>
          
                 <c:if test="${trialDTO.trialType=='NonInterventional'}">
		             <reg-web:valueRow labelFor="submit.trial.studyModelCode" labelKey="submit.trial.studyModelCode">
		                 <s:property value="trialDTO.studyModelCode"/>
		             </reg-web:valueRow>
	    
		             <c:if test="${trialDTO.studyModelCode=='Other'}">  
			             <reg-web:valueRow labelFor="submit.trial.studyModelOtherText" labelKey="submit.trial.studyModelOtherText">
			                <s:property value="trialDTO.studyModelOtherText"/>
			             </reg-web:valueRow>
			         </c:if>

		             <reg-web:valueRow labelFor="submit.trial.timePerspectiveCode" labelKey="submit.trial.timePerspectiveCode">
		                    <s:property value="trialDTO.timePerspectiveCode"/>
		             </reg-web:valueRow>
      
			        <c:if test="${trialDTO.timePerspectiveCode=='Other'}">
			              <reg-web:valueRow labelFor="submit.trial.timePerspectiveOtherText" labelKey="submit.trial.timePerspectiveOtherText">
			                <s:property value="trialDTO.timePerspectiveOtherText"/>
			              </reg-web:valueRow>
			        </c:if>        
                </c:if>
                    
                <reg-web:valueRow labelFor="submit.trial.phase" labelKey="submit.trial.phase">
                    <s:property value="trialDTO.phaseCode" />
                </reg-web:valueRow>
                    
	            <c:if test="${trialDTO.phaseCode=='NA'}">                    
	               <reg-web:valueRow labelFor="submit.trial.otherPhaseText" labelKey="submit.trial.otherPhaseText">
	                  <s:property value="trialDTO.phaseAdditionalQualifier"/>
	               </reg-web:valueRow>
	            </c:if>
                    
                    
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <!--  summary4 information -->
                    <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="trialDTO.summaryFourFundingCategoryCode" labelKey="update.proprietary.trial.summary4FundingCategory">
                               <s:property value="trialDTO.summaryFourFundingCategoryCode" />                    
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.summaryFourOrgName" labelKey="update.proprietary.trial.summary4Sponsor">
                        <s:property value="trialDTO.summaryFourOrgName" />
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    
                    <table class="data2">
                         <tr>
                            <th colspan="2">Participating Sites</th>
                         </tr>
                         <tr> 
                            <td>
                                <table class="form">
                                    <tbody>
                                        <tr>
                                            <th>Organization/Investigator</th>
                                            <th>Local Trial<br/> Identifier<span class="required">*</span></th>
                                            <th>Program Code</th>
                                            <th>Current Site<br/> Recruitment Status<span class="required">*</span></th>
                                            <th>Current Site<br/> Recruitment <br/>Status Date<span class="required">*</span><br/>(mm/dd/yyyy) </th>
                                            <th>Date Opened <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                                            <th>Date Closed <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                                        </tr>
                                        <s:iterator id="trialDTO.participatingSitesList" value="trialDTO.participatingSitesList" status="psstats">
                                        <tr>
                                            <td>
                                                <label><s:textarea  name="trialDTO.participatingSitesList[%{#psstats.index}].nameInvestigator" value="%{nameInvestigator}" readonly="true" cssStyle="width:200px;border: 1px solid #FFFFFF" rows="2"/></label>
                                                <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].name" value="%{name}"/>
                                                <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].investigator" value="%{investigator}"/>
                                            </td>
                                            <td>
                                                <label><s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].siteLocalTrialIdentifier" value="%{siteLocalTrialIdentifier}"/></label>
                                            </td>
                                            <td>
                                                <label><s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].programCode" value="%{programCode}"/></label>
                                                <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].id" value="%{id}"/>
                                            </td>
                                            <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                            <td class="value">
                                                <label><s:select headerKey="" headerValue="--Select--"
                                                          name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                                          list="#recruitmentStatusValues" cssStyle="text-align:left;"/></label>
                                            </td>
                                            <td nowrap="nowrap">
                                                <label><s:textfield name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}" size="12"/></label>
                                                <a href="javascript:showCal('Cal1-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                            <td nowrap="nowrap">
                                                <label><s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateOpenedforAccrual" value="%{dateOpenedforAccrual}" size="12"/></label>
                                                <a href="javascript:showCal('Cal2-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                            <td nowrap="nowrap">
                                                <label><s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateClosedforAccrual" value="%{dateClosedforAccrual}" size="12"/></label>
                                                <a href="javascript:showCal('Cal3-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                        </tr>
                                        </s:iterator >
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </table>
                
                <div id="existingDocsDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateProprietaryTrialDocumentsSection.jsp" %>
                </div>
                
                <div id="uploadDocDiv">
                    <c:set var="disableDocumentDeletion" value="${true}" scope="request"/>
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                <p align="center" class="info">
                   Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                   <br>Once you submit the trial you will not be able to modify the information.
                </p>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="reviewProtocol()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
                <s:hidden name="uuidhidden"/>
            </s:form>
        </div>
    </body>
</html>
