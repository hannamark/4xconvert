<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

          <!--  summary4 information -->
          <tr>
                <th colspan="2">Summary 4 Information (for trials at NCI-designated cancer centers)</th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
        <table>
          <tr>
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_sponsor_type">
                        <label for="submitTrial_summary4FundingCategory">Summary 4 Funding Sponsor Type:</label>
                        <span class="required">*</span>
                    </reg-web:displayTooltip>
                </td>
                <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <td class="value">
                    <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                         <s:select headerKey="" headerValue="--Select--" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            value ="trialDTO.summaryFourFundingCategoryCode"
                            cssStyle="width:206px" 
                            disabled="true"/>
                     </c:if>
                     <c:if test="${empty trialDTO.summaryFourFundingCategoryCode}">
                        <s:select headerKey="" headerValue="--Select--" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            value ="trialDTO.summaryFourFundingCategoryCode"
                            cssStyle="width:206px" />
                     </c:if>
                     <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                       </s:fielderror>
                     </span>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_source">
                        <label for="submitTrial_selectedSummary4Sponsor_name_part_0__value"> Summary 4 Funding Sponsor: </label>
                        <span class="required">*</span>
                    </reg-web:displayTooltip>
                </td>
                <td class="value">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                        <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.summaryFourOrgIdentifier</s:param>
                       </s:fielderror>
                     </span>
                </td>
            </tr>
            <tr>
             <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.summary_4_program_code">
                    <label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
                <s:textfield name="trialDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg">
                    <s:fielderror>
                            <s:param>trialDTO.programCodeText</s:param>
                    </s:fielderror>
                 </span>
               </td>
            </tr>
            
</table>