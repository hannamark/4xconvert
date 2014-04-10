 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section13">Regulatory Information <span class="required">*</span></a></div>
  <div id="section13" class="accordion-body in">
    <div class="container">
        <div class="form-group">
           <label for="countries"  class="col-xs-4 control-label"><fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span> </label>
           <div class="col-xs-4">
           		<s:select id="countries" headerValue="-Select-" headerKey=""
                      name="trialDTO.lst"
                      list="trialDTO.countryList"
                      listKey="id" listValue="name"
                      onchange="loadRegAuthoritiesDiv();"
                      cssClass="form-control"
                      />
                    <span class="alert-danger">
                       <s:fielderror>
                       <s:param>trialDTO.lst</s:param>
                      </s:fielderror>
                    </span>
           </div>
           <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the country in which the Trial Oversight Authority is located. After you select the country, you will be able to select the Trial Oversight Authority Organization name." data-placement="top" data-trigger="hover"></i>
        </div>
       <div class="form-group">
           <label for="trialDTO.selectedRegAuth"  class="col-xs-4 control-label"><fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></label>
            <%@ include file="/WEB-INF/jsp/nodecorate/oversightAuthInfo.jsp" %>
     	</div>

 <!--   FDA Regulated Intervention Indicator-->
     <div class="form-group">
          <label for="trialDTO.fdaRegulatoryInformationIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span></label>
          <div class="col-xs-4">
          	<s:radio cssClass="radio-inline" id ="trialDTO.fdaRegulatoryInformationIndicator" name="trialDTO.fdaRegulatoryInformationIndicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkFDADropDown();" value="trialDTO.fdaRegulatoryInformationIndicator"/>
       		<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Indicate whether this trial includes an intervention subject to US Food and Drug Administration regulation under section 351 of the Public Health Service Act or any of the following sections of the Federal Food, Drug and Cosmetic Act: 505, 510(k), 515, 52." data-placement="top" data-trigger="hover"></i> 
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.fdaRegulatoryInformationIndicator</s:param></s:fielderror></span>
     	</div>
      </div>
     <!--   Section 801 Indicator-->
     <div class="form-group">
        <label for="trialDTO.section801Indicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></label>
        <div class="col-xs-4">
            <s:radio cssClass="radio-inline" id="trialDTO.section801Indicator" name="trialDTO.section801Indicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkSection108DropDown();" value="trialDTO.section801Indicator"/>
         	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Indicate whether this is an ''applicable clinical trial'' as defined in US Public Law 110-85, Title VIII, Section 801." data-placement="top" data-trigger="hover"></i>
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.section801Indicator</s:param></s:fielderror></span>
     	</div>
     </div>

     <!--   Delayed Posting Indicator-->
     <div class="form-group">
          <label for="trialDTO.delayedPostingIndicator"  class="col-xs-4 control-label"> <fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></label>
          <div class="col-xs-4">
            <s:radio cssClass="radio-inline" id="trialDTO.delayedPostingIndicator" name="trialDTO.delayedPostingIndicator" list="#{'No':'No', 'Yes':'Yes'}" value="trialDTO.delayedPostingIndicator" />
         	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Indicate whether this trial includes a device NOT previously approved or cleared by the US FDA for any use, as specified in US Public Law 110-85, Title VIII, Section 801." data-placement="top" data-trigger="hover"></i>
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.delayedPostingIndicator</s:param></s:fielderror></span>
     	</div>
     </div>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <div class="form-group" id="datamonrow">
             <label for="trialDTO.dataMonitoringCommitteeAppointedIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
         <div class="col-xs-4">
            <s:radio cssClass="radio-inline" id="trialDTO.dataMonitoringCommitteeAppointedIndicator" name="trialDTO.dataMonitoringCommitteeAppointedIndicator" list="#{'No':'No', 'Yes':'Yes'}" value="trialDTO.dataMonitoringCommitteeAppointedIndicator" />
        	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Indicate whether a data monitoring committee has been appointed for this study." data-placement="top" data-trigger="hover"></i>
        </div>
      </div>
	</div>
  </div>
</div>