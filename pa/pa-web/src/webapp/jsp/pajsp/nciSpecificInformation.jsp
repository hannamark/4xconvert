<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="nciSpecificInformation.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
function resetValues () {
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_accrualReportingMethodCode.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_summaryFourFundingCategoryCode.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_organizationName.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_monitorCode.value="";
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <div class="summarybox">
						
			<div class="summarytitle">
				<span class="value"><strong> <c:out value="${sessionScope.trialSummary.nciIdentifier }"/></strong>:
				  <c:out value="${sessionScope.trialSummary.officialTitle }"/>
				 </span>
			</div>
							
			<div class="float33_first">
				<div class="row">
					<span class="label"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>:</span> 
					<span class="value"></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.leadOrganization"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.leadOrganizationName }"/></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.principalInvestigator"/>:</span> 
					<span class="value"> <c:out value="${sessionScope.trialSummary.piFullName }"/></span>
				</div>
				<div class="row">
					<span class="label">Trial Submitter:</span> 
					<span class="value"></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"> <fmt:message key="studyProtocol.studyStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.documentWorkflowStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/></span>
				</div>
			</div>
				
			<div class="clear"></div>
							
   </div>
  <div class="box">  
    <s:form action="updateNCISpecificInformation"><s:actionerror/>
    <h2><fmt:message key="nciSpecificInformation.title" /></h2>
    	<table class="form">
 			<%--  <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> --%>

			 <input type="hidden" name="nciSpecificInformationData.studyProtocolID" value="${nciSpecificInformationDTO.studyProtocolID}"/>
			 	<!--  <tr>
			  			<th colspan="2"><fmt:message key="nciSpecificInfo.title"/></th>
				</tr>	-->			  
			    <tr>
			          <td scope="row" class="label">
			               <label for="monitorCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.monitorCode"/></dfn></label>
			          </td>			    			          			          			          			         
			          <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="All" name="nciSpecificInformationData.monitorCode" list="#monitorCodeValues"  value="nciSpecificInformationData.monitorCode" cssStyle="width:206px"/>
	                  </td>         
			    </tr> 
			    <tr>
			    	  <td scope="row" class="label">
			               <label for="reportingDataSetMethodCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.accrualReportingMethodCode"/></dfn></label>
			          </td>
			    	  <s:set name="reportingDataSetMethodCodeValues" value="@gov.nih.nci.pa.enums.ReportingDataSetMethodCode@getDisplayNames()" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="All" name="nciSpecificInformationData.reportingDataSetMethodCode" list="#reportingDataSetMethodCodeValues"  value="nciSpecificInformationData.reportingDataSetMethodCode" cssStyle="width:206px" />
	                  </td>   			            
			     </tr>           
			     <tr>
			          <td scope="row" class="label">
			               <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></dfn></label>
			          </td>
			          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="All" name="nciSpecificInformationData.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues"  value="nciSpecificInformationData.summaryFourFundingCategoryCode" cssStyle="width:206px" />
                      </td>  			                    
			     </tr>
				<tr>
			          <td scope="row" class="label">
			               <label for="summary4Name"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="summaryFourFundingSource.organizationName"/></dfn></label>
			          </td>
			          <td class="value">
			              <s:textfield name="nciSpecificInformationData.summary4Name" size="30" maxlength="40" />
			          </td>          
			     </tr>  
			     <!-- <td colspan="2">                        
                    <INPUT TYPE="submit" NAME="submit"  value="Save" class="button"/>          
                    <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                </td>  -->
		</table> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><a href="updateNCISpecificInformation.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="save">Save</span></span></a></li>
					<li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="regulatoryInfo.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
<!--  </div> -->
 </body>
 </html>