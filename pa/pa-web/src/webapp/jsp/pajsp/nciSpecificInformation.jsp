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
<div id="contentwide">
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
    <s:form action="updateNCISpecificInformation">
    	<table width="480">
 			<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
			 	<tr>
			  			<th colspan="2"><fmt:message key="nciSpecificInformation.title"/></th>
				</tr>	
                <tr>
                          <td class=formErrorMsg colspan="2"> <s:actionerror/> </td>
                </tr>   
							  
			    <tr>
			          <td align=right>
			               <label for="monitorCode"> <fmt:message key="studyProtocol.monitorCode"/></label>
			          </td>			    			          			          			          			         
			          <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
	                  <td align=left>
	                    <s:select headerKey="" headerValue="" 
    	                    name="nciSpecificInformationWebDTO.monitorCode" 
    	                    list="#monitorCodeValues"  
    	                    value="nciSpecificInformationWebDTO.monitorCode" />
	                  </td>         
			    </tr> 
			    <tr>
			    	  <td align=right>
			               <label for="accrualReportingMethodCode"> <fmt:message key="studyProtocol.accrualReportingMethodCode"/></label>
			          </td>
			    	  <s:set name="accrualReportingMethodCodeValues" value="@gov.nih.nci.pa.enums.AccrualReportingMethodCode@getDisplayNames()" />
	                  <td align=left>
	                    <s:select headerKey="" headerValue="" 
	                       name="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
	                       list="#accrualReportingMethodCodeValues"  
	                       value="nciSpecificInformationWebDTO.accrualReportingMethodCode" />
	                  </td>   			            
			     </tr>           
			     <tr>
			          <td align=right>
			               <label for="summaryFourFundingCategoryCode"> 
			               <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></label>
			          </td>
			          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                      <td align=left>
                        <s:select headerKey="" headerValue="" 
                            name="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"  
                            value="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" />
                      </td>  			                    
			     </tr>
				<tr>
			          <td align=right>
			               <label for="organizationName"> 
			               <fmt:message key="summaryFourFundingSource.organizationName"/></label>
			          </td>
			          <td align=left>
			              <s:textfield name="nciSpecificInformationWebDTO.organizationName" size="30" maxlength="40" />
			          </td>          
			     </tr>  
			     <td colspan="2">                        
                    <INPUT TYPE="submit" NAME="submit"  value="Save" class="button"/>          
                    <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                </td> 
		</table>            
  	</s:form>
   </div>
 </div>
 </body>
 </html>
 
