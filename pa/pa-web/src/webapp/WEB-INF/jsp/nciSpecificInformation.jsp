<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="nciSpecificInformation.title"/></title>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/ajaxorglookup.action" var="lookupUrl"/>
<script type="text/javascript">    
    function trim(str) {
	    var newstr;
	    newstr = str.replace(/^\s*/, "").replace(/\s*$/, "");
	    newstr = newstr.replace(/\s{2,}/, " ");
	    return newstr;
    }
	function resetValues () {
	    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_accrualReportingMethodCode.value="";
	    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_summaryFourFundingCategoryCode.value="";
	    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_organizationName.value="";
	    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_monitorCode.value="";
	}
	
	function handleAction(){
	     document.nciSpecificInformationupdate.action="nciSpecificInformationupdate.action";
	     document.nciSpecificInformationupdate.submit();     
	}
	
	function lookup(){	
	    showPopWin('${lookupUrl}', 600, 250, '', 'Choose Organization');
	}
	
	function loadDiv(url) {
	    var div = document.getElementById('loadOrgField');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
	    var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}
</SCRIPT>
</head>
<body onload="setFocusToFirstControl();">

<h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form action="nciSpecificInformationupdate"><s:actionerror/>
    <h2><fmt:message key="nciSpecificInformation.title" /></h2>
    	<table class="form" >
			    <tr>
			    	  <td scope="row" class="label">
			               <label for="accrualReportingMethodCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.accrualReportingMethodCode"/></dfn></label>
			          </td>
			    	  <s:set name="accrualReportingMethodCodeValues" value="@gov.nih.nci.pa.enums.AccrualReportingMethodCode@getDisplayNames()" />
	                  <td class="value">
	                    <s:select headerKey="" headerValue="" 
	                       name="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
	                       list="#accrualReportingMethodCodeValues"  
	                       value="nciSpecificInformationWebDTO.accrualReportingMethodCode" cssStyle="width:206px" />
	                  </td>   			            
			     </tr>           
			     <tr>
			          <td scope="row" class="label">
			               <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></dfn></label>
			          </td>
			          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
                            name="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"  
                            value="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                            cssStyle="width:206px" />
                      </td>  			                    
			     </tr>	  
				<tr>
					<td scope="row" class="label">
						<label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here."> Summary 4 Funding Source:</dfn></label>
					</td>
					<td class="value">
						<div id="loadOrgField">
						<%@ include file="/WEB-INF/jsp/nodecorate/orgField.jsp" %>
						</div>		
					</td>
					
				</tr>
				
			</table> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
					<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="regulatoryInfoquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>

		           
  	</s:form>
   </div>
<!--  </div> -->
 </body>
 </html>