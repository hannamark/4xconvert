<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="orgDetailsDiv">
<%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp" %>
</div>
<table class="form">	

               		<tr>
					<td scope="row" class="label"><s:label for="srs">Site Recruitment Status:</s:label><span class="required">*</span></td>
                    <s:set name="recruitmentStatusValues"
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2"><s:select 
                        name="recStatus"
                        list="#recruitmentStatusValues" />
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
                        <s:textfield name="recStatusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>recStatusDate</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
				</tr>

			</table>


