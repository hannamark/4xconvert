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
 <h1><fmt:message key="nciSpecificInfo.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
    <s:form action="viewNCISpecificInformation"><s:actionerror/>
    	<table width="480">
 			<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
			 <input type="hidden" name="nciSpecificInformationData.studyProtocolID" value="${nciSpecificInformationData.studyProtocolID}"/>
			 	<tr>
			  			<th colspan="2"><fmt:message key="nciSpecificInfo.title"/></th>
				</tr>
			    <tr>
			          <td align=right>
			               <label for="monitorCode"> <fmt:message key="nciSpecificInfo.nci.division"/></label>
			          </td>		
	                <td align=left>
	                    ${nciSpecificInformationData.monitorCode}
	                </td>         
			    </tr> 
			    <tr>
			    	<td align=right>
			               <label for="reportingDataSetMethodCode"> <fmt:message key="nciSpecificInfo.report.data.set.method"/></label>
			          </td>			    	
	                <td align=left>
	                	${nciSpecificInformationData.reportingDataSetMethodCode}
	                </td>   			            
			     </tr>           
			     <tr>
			          <td align=right>
			               <label for="summary4TypeCode"> <fmt:message key="nciSpecificInfo.summary4.funding.category"/></label>
			          </td>
			          <td align=left>
			            ${nciSpecificInformationData.summaryFourFundingCategoryCode} 
			          </td>          
			     </tr>
				<tr>
			          <td align=right>
			               <label for="summary4Name"> <fmt:message key="nciSpecificInfo.summary4.funding.source"/></label>
			          </td>
			          <td align=left>
			              <s:textfield name="nciSpecificInformationData.summary4Name" size="30" maxlength="40" />
			          </td>          
			     </tr>  			     
		</table>            
  	</s:form>
   </div>
 </div>
 </body>
 </html>