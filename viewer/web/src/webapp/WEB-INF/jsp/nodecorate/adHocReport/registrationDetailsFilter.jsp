<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script type="text/javascript">
jQuery(function() {
      jQuery( "#officialTitle" ).autocomplete({delay: 600 ,
          source: function(req, responseFn) {
            var url = viewerApp.contextPath + '/ctro/json/refreshMatchingOfficalTitles.action?officialTitleMatchTerm=' + req.term;
            jQuery.getJSON(url,null,function(data){
            	responseFn(data.officialTitles);
            });
        }
    });
});
</script>

<h2><a href="#"><fmt:message key="adHocReport.details.header" /></a></h2>
<div id="detailsSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="officialTitle" labelKey="studyProtocol.officialTitle">
                <s:textfield id="officialTitle" name="criteria.officialTitle" maxlength="600" size="600" cssStyle="width:600px"/>
            </viewer:valueRow>
            <viewer:valueRow labelFor="primaryPurpose" labelKey="studyProtocol.primaryPurpose">
                <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <s:select headerKey="" id="primaryPurpose" headerValue="All" name="criteria.primaryPurposeCode" 
                          list="#primaryPurposeCodeValues" value="criteria.primaryPurposeCode" cssStyle="width:206px" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="phaseCodes" labelKey="studyProtocol.studyPhase">
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <s:select headerKey="" id="phaseCodes" headerValue="All" name="criteria.phaseCodes" 
                          list="#phaseCodeValues"  value="criteria.phaseCodes" cssStyle="width:206px" multiple="true" size="3" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="identifierType" labelKey="studyProtocol.identifierType">
                <s:set name="identifierSearchTypes" value="@gov.nih.nci.pa.enums.IdentifierType@getDisplayNames()" />
                <s:select id="identifierType" headerKey="" headerValue="--Select--" name="criteria.identifierType"  
                          list="#identifierSearchTypes" value="criteria.identifierType"  cssStyle="width:206px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                        <s:param>criteria.identifierType</s:param>
                    </s:fielderror>                            
                    </span> 
            </viewer:valueRow>
            <tr>
                <td scope="row" class="label">
                    <label for="identifier"><fmt:message key="studyProtocol.identifier"/></label>
                    <br><span class="info">(e.g: NCI-2008-00015; ECOG-1234, etc)</span>
                </td>
                <td class="value">
                    <s:textfield id="identifier" name="identifier" maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                            <s:param>identifier</s:param>
                       </s:fielderror>                            
                    </span>  
                </td>   
            </tr>             
            
            <viewer:titleRow titleKey="adHocReport.leadOrganizations"/>        
           <viewer:valueRow labelKey="adHocReport.byFamily">
              
                <s:select id="familyId" name="criteria.familyId" list="families" listKey="key"
                          listValue="value" headerKey="0" headerValue="--All--"  cssStyle="display:inline"/>
            </viewer:valueRow>
            <viewer:valueRow labelKey="adHocReport.orgsByFamily">
                <div id="organization_choices">                       
                  <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/organizationsByFamily.jsp"/>                   
                </div> 
            </viewer:valueRow>           
        
            <viewer:valueRow labelFor="principalInvestigatorId" labelKey="studyProtocol.principalInvestigator">
                <s:set name="principalInvs" value="@gov.nih.nci.pa.util.PaRegistry@getPAPersonService().getAllPrincipalInvestigators()" />
                <s:select name="criteria.principalInvestigatorId" id="principalInvestigatorId" list="#principalInvs" listKey="id"
                          listValue="fullName" headerKey="" headerValue="All" value="criteria.principalInvestigatorId" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="documentWorkflowStatusCodes" labelKey="studyProtocol.documentWorkflowStatus">
                <s:set name="documentWorkflowStatusCodeValues" value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
                <s:select headerKey="" headerValue="All" id="documentWorkflowStatusCodes" name="criteria.documentWorkflowStatusCodes" list="#documentWorkflowStatusCodeValues"  
                          value="criteria.documentWorkflowStatusCodes" cssStyle="width:206px" multiple="true" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="studyStatusCode" labelKey="studyProtocol.studyStatus">
                <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                <s:select headerKey="" id="studyStatusCode" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  
                          value="criteria.studyStatusCode" cssStyle="width:206px" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="submissionType" labelKey="studyProtocol.submissionTypeSearch">
                <s:set name="submissionTypeValues" value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
                <s:select headerKey="" headerValue="All" id="submissionType" name="criteria.submissionType" list="#submissionTypeValues"  
                          value="criteria.submissionType" cssStyle="width:206px" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="trialCategory" labelKey="studyProtocol.trialCategorySearch">
                <s:select headerKey="" headerValue="Both" id="trialCategory" name="criteria.trialCategory" list="#{'p':'Abbreviated','n':'Complete'}"  
                          value="criteria.trialCategory" cssStyle="width:206px" />
            </viewer:valueRow>
            <viewer:valueRow labelFor="studyMilestone" labelKey="studyProtocol.milestone">
                <s:set name="milestoneCodes" value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
                <s:select headerKey="" headerValue="All" id="studyMilestone" name="criteria.studyMilestone" list="#milestoneCodes"  
                          value="criteria.studyMilestone" cssStyle="width:206px" />
            </viewer:valueRow>
        </tbody>
    </table>
</div>