<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
<s:hidden name="trialDTO.programCodeText" id="trialDTO.programCodeText" />
<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a></div>
<div id="section6" class="accordion-body in">
<div class="container">
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label">Data Table 4 Funding Sponsor Type:<span class="required">*</span></label>
    <div class="col-xs-4">
        <s:property value="trialDTO.summaryFourFundingCategoryCode"/>
    </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label">Data Table 4 Funding Sponsor: <span class="required">*</span></label>
    <div class="col-xs-4">    
        <s:iterator value="trialDTO.summaryFourOrgIdentifiers" id="trialDTO.summaryFourOrgIdentifiers" status="stat">
            <s:property value="%{orgName}"/><br/>
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="<c:out value="${orgId}" />"/>
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="<c:out value="${orgName}" />"/>
        </s:iterator>
    </div>
</div>
<div class="form-group" id="programCodeBlock" style="display:none">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
    <div class="col-xs-4">
     <div class="col-xs-16" >
                     <s:select size="2" multiple="true"  name="trialDTO.selectedProgramCodes" id="programCodesValues"  list="#{trialDTO.programCodesMap}"
                        cssStyle="width:206px" />
                      <c:if test="${sessionScope.isSiteAdmin}">
                       &nbsp;&nbsp;<a href="../siteadmin/programCodesexecute.action">Manage Program Codes</a>
                     </c:if>
                 </div>  
    </div>
</div>
</div>
</div>
</div>
 
