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
        <fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span></td>
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
           <fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></td>
                <td class="value">
                    <div id="loadAuthField">
                          <%@ include file="/WEB-INF/jsp/nodecorate/oversightAuthInfo.jsp" %>
                    </div>
                </td>
     </tr>   
    
 <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
         <fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span> </td>
         <td class="value"><s:select  name="trialDTO.fdaRegulatoryInformationIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkFDADropDown();" value="trialDTO.fdaRegulatoryInformationIndicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.fdaRegulatoryInformationIndicator</s:param></s:fielderror></span>
         </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></td>
         <td class="value"><s:select name="trialDTO.section801Indicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkSection108DropDown();" value="trialDTO.section801Indicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.section801Indicator</s:param></s:fielderror></span>
         </td>
     </tr>
     
     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label"><fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></td>
         <td class="value"><s:select name="trialDTO.delayedPostingIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" value="trialDTO.delayedPostingIndicator" />
         <span class="formErrorMsg"><s:fielderror><s:param>trialDTO.delayedPostingIndicator</s:param></s:fielderror></span>
         </td>       
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
         <td class="value"><s:select name="trialDTO.dataMonitoringCommitteeAppointedIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" value="trialDTO.dataMonitoringCommitteeAppointedIndicator" />      
         </td>       
     </tr>
          </table>