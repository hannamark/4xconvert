<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

          <!--  summary4 information -->
          <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
        <table>
          <tr>
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_sponsor_type">
                        <label for="trialDTO.summaryFourFundingCategoryCode">Summary 4 Funding Sponsor Type:</label>
                        <span class="required">*</span>
                    </reg-web:displayTooltip>
                </td>
                <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <td class="value">
                    <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                         <s:select headerKey="" headerValue="--Select--" 
                            id="trialDTO.summaryFourFundingCategoryCode"
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            value ="trialDTO.summaryFourFundingCategoryCode"
                            cssStyle="width:206px" 
                            disabled="true"/>
                     </c:if>
                     <c:if test="${empty trialDTO.summaryFourFundingCategoryCode}">
                        <s:select headerKey="" headerValue="--Select--" 
                            id="trialDTO.summaryFourFundingCategoryCode" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            value ="trialDTO.summaryFourFundingCategoryCode"
                            cssStyle="width:206px" />
                     </c:if>
                     <span class="alert-danger">
                        <s:fielderror>
                        <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                       </s:fielderror>
                     </span>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_source">
                        <label for="trialDTO.summaryFourOrgName"> Summary 4 Funding Sponsor: </label>
                        <span class="required">*</span>
                    </reg-web:displayTooltip>
                </td>
                <td class="value">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                        <span class="alert-danger">
                        <s:fielderror>
                        <s:param>trialDTO.summaryFourOrgIdentifier</s:param>
                       </s:fielderror>
                     </span>
                </td>
            </tr>
            <tr>
             <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.summary_4_program_code">
                    <label for="trialDTO.programCodeText"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
                <s:textfield id="trialDTO.programCodeText" name="trialDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                <span class="alert-danger">
                    <s:fielderror>
                            <s:param>trialDTO.programCodeText</s:param>
                    </s:fielderror>
                 </span>
               </td>
            </tr>
            
</table>