<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section8_1">Trial Status<span class="required">*</span></a></div>
    <div id="section8_1" class="accordion-body in">
        <div class="container">
            <div class="form-group">
                <label for="trialDTO_statusCode" class="col-xs-4 control-label"><fmt:message key="update.trial.currentTrialStatus"/><span class="required">*</span></label>
                <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
                <div class="col-xs-4">
                    <s:select headerKey="" headerValue="--Select--" id="trialDTO_statusCode" name="trialDTO.statusCode" list="#statusCodeValues" 
                        value="trialDTO.statusCode" onchange="displayTrialStatusDefinition('trialDTO_statusCode');" 
                        cssClass="form-control"/> 
                    <span class="alert-danger"> 
                        <s:fielderror>
                            <s:param>trialDTO.statusCode</s:param>
                        </s:fielderror>
                    </span>
                </div>
                <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.current_trial_status" />"  data-placement="top" data-trigger="hover"></i>
            </div>
            <div class="form-group">
            	  <label class="col-xs-4 control-label">&nbsp;</label>
                  <div class="col-xs-4">
                  <em><%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp"%></em>
                  </div>
            </div>
            <div class="form-group">
               <label for="trialDTO_reason" class="col-xs-4 control-label"><fmt:message key="update.trial.trialStatusReason"/><span class="required">*</span><em>Required for Administratively Complete, Withdrawn<br>and Temporarily Closed statuses only</em></label>
               <div class="col-xs-4">
                  <s:textarea id="trialDTO_reason" name="trialDTO.reason" cols="50" rows="3" maxlength="160" 
                      cssClass="form-control charcounter" cssStyle="width: 156px; height: 55px;"/>
                  <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.reason</s:param>
                      </s:fielderror> 
                  </span>
                  <span>
                  <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.why_study_stopped" />"  data-placement="top" data-trigger="hover"></i>
                  </span>
               </div>
            </div>
            
            <div class="form-group">
                <label for="trialDTO_statusDate" class="col-xs-4 control-label"><fmt:message key="update.trial.currentTrialStatusDate"/><span class="required">*</span></label>
                <div class="col-xs-2">
                  <div id="datetimepicker" class="datetimepicker input-append">                    
                    <s:textfield id="trialDTO_statusDate" name="trialDTO.statusDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                    <span class="alert-danger">
                        <s:fielderror>
                            <s:param>trialDTO.statusDate</s:param>
                        </s:fielderror>
                    </span>
                  </div>
                </div>                                
                <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.current_trial_status_date" />"  data-placement="top" data-trigger="hover"></i></div>
             </div>
        </div>
    </div>
</div>


<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section8_2">Trial Dates<span class="required">*</span></a></div>
    <div id="section8_2" class="accordion-body in">
        <div class="container">       
             <div class="form-group">
                <label for="trialDTO_startDate" class="col-xs-4 control-label"><fmt:message key="update.trial.trialStartDate"/><span class="required">*</span></label>
                <s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />
                <div class="col-xs-2">
                  <div id="datetimepicker" class="datetimepicker input-append">
                    <s:textfield id="trialDTO_startDate" name="trialDTO.startDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />                    
                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                    <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.startDate</s:param>
                      </s:fielderror>
                  </span>
                 </div>                 
                </div>
                <div class="col-xs-5">
                  <s:radio cssClass="radio-inline" id="trialDTO_startDateType" name="trialDTO.startDateType" list="#dateTypeList" />                 
                  <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.trial_start_date"/>"  data-placement="top" data-trigger="hover"></i>
                  <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.startDateType</s:param>
                      </s:fielderror>
                  </span>   
             </div>
          </div>
          <div class="form-group">
              <label for="trialDTO_primaryCompletionDate" class="col-xs-4 control-label"><fmt:message key="update.trial.primaryCompletionDate"/>
              <c:set var="asterix" value="true"/>
              <c:if test="${trialDTO.trialType == 'NonInterventional' && trialDTO.xmlRequired == false}">
                  <c:set var="asterix" value="false"/>
              </c:if>
              <c:if test="${asterix}"><span class="required" id="primaryCompletionDateId">*</span></c:if>
              </label>
               <div class="col-xs-2">
                   <div id="datetimepicker" class="datetimepicker input-append">
                       <s:textfield id="trialDTO_primaryCompletionDate" name="trialDTO.primaryCompletionDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
                       <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                       <span class="alert-danger">
                          <s:fielderror>
                             <s:param>trialDTO.primaryCompletionDate</s:param>
                         </s:fielderror>
                      </span>
                   </div>               
               </div>               
               <div class="col-xs-5">
                   <s:radio cssClass="radio-inline" id="trialDTO_primaryCompletionDateType" name="trialDTO.primaryCompletionDateType" list="#dateTypeList" />                   
                   <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.primary_completion_date"/>"  data-placement="top" data-trigger="hover"></i> 
                    <span class="alert-danger">
                       <s:fielderror>
                           <s:param>trialDTO.primaryCompletionDateType</s:param>
                       </s:fielderror>
                   </span>
               </div>
          </div>
          <div class="form-group">
              <label for="trialDTO_completionDate" class="col-xs-4 control-label"><fmt:message key="update.trial.completionDate"/></label>
              <div class="col-xs-2">
                  <div id="datetimepicker" class="datetimepicker input-append">
                    <s:textfield id="trialDTO_completionDate" name="trialDTO.completionDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>                 
                    <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.completionDate</s:param>
                      </s:fielderror>
                    </span>
                  </div>
              </div>
              <div class="col-xs-5">
                  <s:radio  cssClass="radio-inline" id="trialDTO_completionDateType" name="trialDTO.completionDateType" list="#dateTypeList" />
                  <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.completion_date" />"  data-placement="top" data-trigger="hover"></i>
                  <span class="alert-danger">
                     <s:fielderror>
                         <s:param>trialDTO.completionDateType</s:param>
                     </s:fielderror>
                  </span>
                  </div>
           </div>
          <span class="info">Please refer to <a href="https://wiki.nci.nih.gov/x/l4CNC" target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
        </div>
    </div>
</div>