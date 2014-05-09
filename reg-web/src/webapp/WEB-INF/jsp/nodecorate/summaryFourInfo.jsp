<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<!--  summary4 information -->
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a>
    </div>
    <div id="section6" class="accordion-body in">
        <div class="container">
            <div class="form-group">
                <label for="trialDTO.summaryFourFundingCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.trial.summary4FundingCategory"/><span class="required">*</span></label>
                <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <div class="col-xs-4">
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                   <s:select headerKey="" headerValue="--Select--" id="trialDTO.summaryFourFundingCategoryCode" 
                       name="trialDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" 
                       cssClass="form-control" disabled="true"/>
                </c:if> 
                <c:if test="${empty trialDTO.summaryFourFundingCategoryCode}">
                   <s:select headerKey="" headerValue="--Select--" id="trialDTO.summaryFourFundingCategoryCode" 
                       name="trialDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" 
                       cssClass="form-control" />
                </c:if>       
                   <span class="alert-danger">
                       <s:fielderror>
                           <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                       </s:fielderror>
                   </span>
                </div>
                <div class="col-xs-4">
                <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Trial category you selected for trial submission."  data-placement="top" data-trigger="hover"></i>
                </div>
            </div>
            <div class="form-group">
                <label id="trialDTO.summaryFourOrgNameTR" for="trialDTO.summaryFourOrgName" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4Sponsor"/><span class="required">*</span></label>
                <div id="loadSummary4FundingSponsorField" >
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                </div>                                    
            </div>
            <div class="form-group">                               
                <label for="trialDTO.programCodeText" class="col-xs-4 control-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                <div class="col-xs-4">
                    <s:textfield id="trialDTO.programCodeText" name="trialDTO.programCodeText"  maxlength="100"  cssClass="form-control"/>
                    <span class="alert-danger">
                        <s:fielderror>
                            <s:param>trialDTO.programCodeText</s:param>
                        </s:fielderror>
                    </span>
                </div>
                <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the Program Code that includes the study."  data-placement="top" data-trigger="hover"></i>
            </div>
        </div>
    </div>
</div>
