<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="orgDetailsDiv">
<%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp" %>
</div>
<table class="form">	

               		<tr>
					<td scope="row" class="label"><s:label for="srs">Site Recruitment Status:</s:label><span class="required">*</span></td>
                    <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--"
                        name="recStatus"
                        list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>recStatus</s:param>
                              </s:fielderror>                            
                        </span>
                    <td>
				</tr>
				<tr>
					<td scope="row" class="label"><s:label for="srsd">Site Recruitment Status Date:</s:label><span class="required">*</span></td>
                    <td class="value" colspan="2">
                        <s:textfield name="recStatusDate" maxlength="10" size="10" readonly="true"  cssStyle="text-align:left;width:70px;float:left"/>
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
                    <td class="label"><s:label for="targetAccrualNumber">Target Accrual Number:</s:label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="targetAccrualNumber" maxlength="10" size="10" cssStyle="text-align:left;width:70px;float:left"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>targetAccrualNumber</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
                </tr>
                <tr> 
            		<td/>
            		<td class="info" colspan="2">Mandatory if Treating site/Lead organization is a cancer center</td>
        		</tr>
			</table>


