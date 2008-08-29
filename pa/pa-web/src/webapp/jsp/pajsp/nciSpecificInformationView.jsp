<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="nciSpecificInfo.title"/></title>
	<s:head />
</head>

<body onload="setFocusToFirstControl();">
<div id="contentwide">
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
    <s:form action="nciSpecificInformation"><s:actionerror/>
    	<table width="480">
 			<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
			 	<tr>
			  			<th colspan="2"><fmt:message key="nciSpecificInformation.title"/></th>
				</tr>
			    <tr>
			          <td align=right>
			                <label for="monitorCode"> <fmt:message key="studyProtocol.monitorCode"/></label>
			          </td>		
	                <td align=left>
	                    ${nciSpecificInformationWebDTO.monitorCode}
	                </td>         
			    </tr> 
			    <tr>
			    	<td align=right>
			               <label for="accrualReportingMethodCode"> <fmt:message key="studyProtocol.accrualReportingMethodCode"/></label>
			          </td>			    	
	                <td align=left>
	                	${nciSpecificInformationWebDTO.accrualReportingMethodCode}
	                </td>   			            
			     </tr>           
			     <tr>
			          <td align=right>
			                <label for="summaryFourFundingCategoryCode"> 
                           <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></label>
			          </td>
			          <td align=left>
			            ${nciSpecificInformationWebDTO.summaryFourFundingCategoryCode} 
			          </td>          
			     </tr>
				<tr>
			          <td align=right>
			               <label for="organizationName"> 
                            <fmt:message key="summaryFourFundingSource.organizationName"/></label>
			          </td>
			          <td align=left>
			              ${nciSpecificInformationWebDTO.organizationName} 
			          </td>          
			     </tr> 
			     <tr> 
			         <td colspan="2">                        
                       <INPUT TYPE="submit" NAME="submit"  value="Update" class="button"/>          
                     </td>
                 </tr> 			     
		</table>            
  	</s:form>
   </div>
 </div>
 </body>
 </html>