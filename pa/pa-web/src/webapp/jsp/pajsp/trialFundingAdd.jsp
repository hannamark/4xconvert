<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialFunding.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">


function handleAction(){
 document.forms[0].action="trialFundingUpdate.action";
 document.forms[0].submit();    
}

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/jsp/pajsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form><s:actionerror/>
    <h2>NIH Grant Information</h2>
    <input type="hidden" id="pageValue" name="page" value="${page}" />
    <input type="hidden" name="cbValue" value="${cbValue}" />
    <table class="form">
			    <tr>
			    	<td scope="row" class="label">
			    	 	<label for="fundingMechanism">	    				
	        				<fmt:message key="trialFunding.funding.mechanism"/>
	   				 	</label>
	   				 </td>
	   				 <s:set name="fundingMechanism" value="%{fundingMechanism}" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="All" 
	                       name="studyResourcingWebDTO.fundingMechanismCode" 
	                       list="#fundingMechanism"  
	                       cssStyle="width:206px"/>
	                  </td>         
			    </tr>
			    <tr> 
	   				 <td scope="row" class="label">
	   				      <label for="institutionCode">
	        				<fmt:message key="trialFunding.institution.code"/>
	   				 	  </label>
	   				 </td>	   			
	   				 <s:set name="institutionCodeValues" value="@gov.nih.nci.pa.enums.InstitutionCode@getDisplayNames()" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="All" 
	                       name="studyResourcingWebDTO.institutionCode" 
	                       list="#institutionCodeValues"  
	                       cssStyle="width:206px"/>
	                  </td>         
			    </tr> 
			    <tr>
	   				 <td scope="row" class="label">
				     <label for="serialNumber">
	        				<fmt:message key="trialFunding.serial.number"/>
	   				 </label>
	   				 </td>
	   				 <td class="value">
	                    <s:textfield name="studyResourcingWebDTO.serialNumber" maxlength="200" size="35" cssStyle="width:200px"/>
	                  </td> 
	   			</tr>
	   			<tr>
	   				 <td scope="row" class="label">
				     <label for="monitorCode">
	        				<fmt:message key="studyProtocol.monitorCode"/>
	   				 </label>
					</td>
					<s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="All" 
	                       name="studyResourcingWebDTO.monitorCode" 
	                       list="#monitorCodeValues"  
	                       cssStyle="width:206px"/>
	                  </td>         
			    </tr> 
			    <tr>
	   				 <td scope="row" class="label">
				     <label for="fundingTypeCode">
	        				FundingTypeCode
	   				 </label>
					</td>
					<td class="value">
	                    <s:textfield name="studyResourcingWebDTO.fundingTypeCode" maxlength="200" size="35" cssStyle="width:200px"/>
	                  </td>         
			    </tr> 		  		    
			    <tr>
	   				 <td scope="row" class="label">
				     <label for="grantYear">
	        				Grant Year
	   				 </label>
					</td>
					<td class="value">
	                    <s:textfield name="studyResourcingWebDTO.suffixgrantYear" maxlength="200" size="35" cssStyle="width:200px"/>
	                  </td>         
			    </tr> 
			    <tr>
	   				 <td scope="row" class="label">
				     <label for="suffix">
	        				Suffix
	   				 </label>
					</td>
					<td class="value">
	                    <s:textfield name="studyResourcingWebDTO.suffixOther" maxlength="200" size="35" cssStyle="width:200px"/>
	                  </td>         
			    </tr> 		  		  
		</table>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>				
				</ul>	
			</del>
		</div>

		           
  	</s:form>
   </div>
 </body>
 </html>