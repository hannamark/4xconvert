<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="orgDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
<h3><fmt:message key="participatingOrganizations.subtitle2" /></h3>

<%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp" %>
</div>
<table class="form">
				    <tr>
				        <td scope="row" class="label"><label for="siteLocalTrialIdentifier"><fmt:message key="proprietary.siteidentifier"/></label></td>
				    <td>
				        <s:textfield name="siteLocalTrialIdentifier" id="siteLocalTrialIdentifier" maxlength="20" size="200" cssStyle="width: 200px" />
				        <span class="formErrorMsg">
				        <s:fielderror>
				            <s:param>siteLocalTrialIdentifier</s:param>
				        </s:fielderror>                            
				        </span>
				    </td>
				    </tr>	
               		<tr>
					<td scope="row" class="label"><label for="recStatus">Site Recruitment Status:</label><span class="required">*</span></td>
                    <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--"
                        name="recStatus" id="recStatus"
                        list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>recStatus</s:param>
                              </s:fielderror>                            
                        </span>
                    <td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="recStatusDate">Site Recruitment Status Date:</label><span class="required">*</span></td>
                    <td class="value" colspan="2">
                        <s:textfield name="recStatusDate" id="recStatusDate" maxlength="10" size="10" readonly="true"  cssStyle="text-align:left;width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>recStatusDate</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
				</tr>
				<tr>
                    <td class="label"><label for="programCode">Program Code:</label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="programCode" id="programCode" maxlength="10" size="10" cssStyle="text-align:left;width:70px;float:left"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>programCode</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
                </tr>
                <tr>
                    <td class="label"><label for="targetAccrualNumber">Target Accrual Number:</label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="targetAccrualNumber" id="targetAccrualNumber" maxlength="10" size="10" cssStyle="text-align:left;width:70px;float:left"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>targetAccrualNumber</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
                </tr>
                <tr> 
                    <td/>
                    <td class="info" colspan="2">Mandatory if Participating site/Lead organization is a cancer center</td>
                </tr>
                <tr>
                    <td class="label"><label for="statusCode">Status:</label></td>
                    <td class="value" colspan="2">
                        <s:textfield id="statusCode" name="statusCode" readonly="true" cssClass="readonly" maxlength="80" size="80" cssStyle="width: 200px"/>
                    </td>               
                </tr>
                
			</table>


