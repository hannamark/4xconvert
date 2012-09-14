 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <table class="data2">
<tr>
              <th colspan="2">Regulatory Information</th>
          </tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
<!--  Trial Oversight Authority Country -->
        <tr>
        <td scope="row" class="label">
            <reg-web:displayTooltip tooltip="tooltip.oversight_authority_organization_country">
                <label for="countries" ><fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span> </label>
            </reg-web:displayTooltip>
        </td>
          <td class="value"><s:select id="countries" headerValue="-Select-" headerKey=""
                      name="trialDTO.lst"
                      list="trialDTO.countryList"
                      listKey="id" listValue="name"
                      onchange="loadRegAuthoritiesDiv();"
                      />
                    <span class="formErrorMsg">
                       <s:fielderror>
                       <s:param>trialDTO.lst</s:param>
                      </s:fielderror>
                    </span>
         </td>
       </tr>
       <tr>
         <td scope="row" class="label">
            <reg-web:displayTooltip tooltip="tooltip.oversight_authority_organization_name">
                <label for="submitTrial_trialDTO_selectedRegAuth" ><fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></label>
            </reg-web:displayTooltip>
         </td>
                <td class="value">
                    <div id="loadAuthField">
                          <%@ include file="/WEB-INF/jsp/nodecorate/oversightAuthInfo.jsp" %>
                    </div>
                </td>
     </tr>

 <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
            <reg-web:displayTooltip tooltip="tooltip.fda_regulated_intervention_indicator">
                <label for="trialDTO.fdaRegulatoryInformationIndicator" ><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span></label>
            </reg-web:displayTooltip>
         </td>
         <td class="value"><s:select  id ="trialDTO.fdaRegulatoryInformationIndicator" name="trialDTO.fdaRegulatoryInformationIndicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}" onchange="checkFDADropDown();" value="trialDTO.fdaRegulatoryInformationIndicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.fdaRegulatoryInformationIndicator</s:param></s:fielderror></span>
         </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label">
            <reg-web:displayTooltip tooltip="tooltip.section_801_indicator">
                <label for="trialDTO.section801Indicator" ><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></label>
            </reg-web:displayTooltip>
         </td>
         <td class="value"><s:select id="trialDTO.section801Indicator" name="trialDTO.section801Indicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}" onchange="checkSection108DropDown();" value="trialDTO.section801Indicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.section801Indicator</s:param></s:fielderror></span>
         </td>
     </tr>

     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label">
            <reg-web:displayTooltip tooltip="tooltip.delayed_posting_indicator">
               <label for="trialDTO.delayedPostingIndicator" > <fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></label>
            </reg-web:displayTooltip>
         </td>
         <td class="value"><s:select id="trialDTO.delayedPostingIndicator" name="trialDTO.delayedPostingIndicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}" value="trialDTO.delayedPostingIndicator" />
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.delayedPostingIndicator</s:param></s:fielderror></span>
         </td>
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label">
            <reg-web:displayTooltip tooltip="tooltip.data_monitoring_committee_appointed_indicator">
                <label for="trialDTO.dataMonitoringCommitteeAppointedIndicator" ><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
            </reg-web:displayTooltip>
         </td>
         <td class="value"><s:select id="trialDTO.dataMonitoringCommitteeAppointedIndicator" name="trialDTO.dataMonitoringCommitteeAppointedIndicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}" value="trialDTO.dataMonitoringCommitteeAppointedIndicator" />
         </td>
     </tr>
          </table>