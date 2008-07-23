<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="nciSpecificInfo.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
function resetValues () {
    document.forms.updateNCISpecificInfo.monitorCode.value="";
    document.forms.updateNCISpecificInfo.reportingDataSetMethodCode.value="";
    document.forms.updateNCISpecificInfo.summary4TypeCode.value="";
    document.forms.updateNCISpecificInfo.summary4Name.value="";
 

}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<div id="contentwide">
 <h1><fmt:message key="nciSpecificInfo.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
    <s:form action="updateNCISpecificInfo"><s:actionerror/>
    	<table width="480">
 			<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
			 <input type="hidden" name="nciSpecificInfoData.protocolId" value="${nciSpecificInfoDTO.studySiteID}"/>
			 	<tr>
			  			<th colspan="2"><fmt:message key="nciSpecificInfo.title"/></th>
				</tr>
			    <tr>
			          <td align=right>
			               <label for="monitorCode"> <fmt:message key="nciSpecificInfo.nci.division"/></label>
			          </td>
			          
			          
			          
			        <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
	                <td align=left>
	                    <s:select headerKey="" headerValue="All" name="nciSpecificInformationData.monitorCode" list="#monitorCodeValues"  value="nciSpecificInformationData.monitorCode" />
	                </td>         
			    </tr> 
			    <tr>
			    	<td align=right>
			               <label for="reportingDataSetMethodCode"> <fmt:message key="nciSpecificInfo.report.data.set.method"/></label>
			          </td>
			    	<s:set name="reportingDataSetMethodCodeValues" value="@gov.nih.nci.pa.enums.ReportingDataSetMethodCode@getDisplayNames()" />
	                <td align=left>
	                    <s:select headerKey="" headerValue="All" name="nciSpecificInformationData.reportingDataSetMethodCode" list="#reportingDataSetMethodCodeValues"  value="nciSpecificInformationData.reportingDataSetMethodCode" />
	                </td>   			            
			     </tr>           
			     <tr>
			          <td align=right>
			               <label for="summary4TypeCode"> <fmt:message key="nciSpecificInfo.summary4.funding.category"/></label>
			          </td>
			          <td align=left>
			              <s:textfield name="nciSpecificInformationData.summary4TypeCode" size="30" maxlength="40" />
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
 
