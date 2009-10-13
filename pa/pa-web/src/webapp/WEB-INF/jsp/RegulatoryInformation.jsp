<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<sx:head />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><fmt:message key="regulatory.title" /></title>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript">	
	window.onload=checkAllonLoad;
	
	function checkAllonLoad(){
		if (document.getElementById('fdaindid').value == '' | document.getElementById('fdaindid').value == 'false'){
				document.getElementById('sec801id').value ='';
				document.getElementById('delpostindid').value ='';
				hideRow(document.getElementById('sec801row'));
		} else {
			showRow(document.getElementById('sec801row'));
		}
			
		if (document.getElementById('sec801id').value == '' | document.getElementById('sec801id').value == 'false') {	
			hideRow(document.getElementById('delpostindrow'));
			document.getElementById('indideid').value ='';
			document.getElementById('delpostindid').value ='';
		} else {
			showRow(document.getElementById('delpostindrow'));
		}			
	}


	function checkFDADropDown(){
		if (document.getElementById('fdaindid').value == '' | document.getElementById('fdaindid').value == 'false'){			
			input_box=confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
			if (input_box==true){
				document.getElementById('sec801id').value ='';
				document.getElementById('delpostindid').value ='';
				hideRow(document.getElementById('sec801row'));
			} else {
				document.getElementById('fdaindid').value = 'true';
			}
		} else {
			showRow(document.getElementById('sec801row'));
		}
	}

    function checkSection108DropDown(){
		if (document.getElementById('sec801id').value == '' | document.getElementById('sec801id').value == 'false') {	
			input_box=confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
			if (input_box==true){
				hideRow(document.getElementById('delpostindrow'));
				document.getElementById('indideid').value ='';
				document.getElementById('delpostindid').value ='';
			} else {
				document.getElementById('sec801id').value = 'true';
			}
		} else {
			showRow(document.getElementById('delpostindrow'));
		}    
    }
	
	/**/
	function checkAll(){
		if (document.getElementById('fdaindid').value == '' | document.getElementById('fdaindid').value == 'false'){			
			input_box=confirm("Section 801 and Delayed posting indicators will be NULLIFIED? \nPlease Click OK to continue or Cancel");
			if (input_box==true){
				document.getElementById('sec801id').value ='';
				document.getElementById('delpostindid').value ='';
				hideRow(document.getElementById('sec801row'));
				hideRow(document.getElementById('delpostindid'));
			} else {
				document.getElementById('fdaindid').value = 'true';
			}
		} else {
			showRow(document.getElementById('sec801row'));
		}
			
		if (document.getElementById('sec801id').value == '' | document.getElementById('sec801id').value == 'false') {	
			input_box=confirm("Delayed posting indicators will be NULLIFIED? \nPlease Click OK to continue or Cancel");
			if (input_box==true){
				hideRow(document.getElementById('delpostindrow'));
				document.getElementById('indideid').value ='';
				document.getElementById('delpostindid').value ='';
			} else {
				document.getElementById('sec801id').value = 'true';
			}
		} else {
			showRow(document.getElementById('delpostindrow'));
		}			
	}
	/**/
	function hideRow(row){			
		row.style.display = 'none';	
	}
	function showRow(row){
		row.style.display = '';
	}
	/*
	Ajax calls to populate authority
	*/
	function show_details() {
		dojo.event.topic.publish("show_detail");
	}
	function handleAction(){
		if(!checkReqFields()) {
			document.regulatoryInfoupdate.action="regulatoryInfoupdate.action";
			document.regulatoryInfoupdate.submit();
		}
		
	}
	function loadDiv() {		
		var url = '/pa/protected/ajaxgetAuthOrgsgetRegAuthoritiesList.action?countryid='+document.getElementById('countries').value;
	    var div = document.getElementById('loadAuthField');
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';	       
	    var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}
	function checkReqFields(){
		if(document.getElementById('fdaindid').value == 'true'){
			if(document.getElementById('sec801id').value == ''){
				alert("The Section801 Indicator cannot be empty");
				return true;
			}
		}
		if(document.getElementById('sec801id').value == 'true'){
			if(document.getElementById('delpostindid').value == ''){
			 	alert("The Delayed posting Indicator cannot be empty");
				return true;
			}
		}	
		return false;
	}
</script>
</head>
<body>
<!-- main content begins-->
<!-- <div id="contentwide"> -->
<h1><fmt:message key="regulatory.title" /></h1>
<c:set var="topic" scope="request" value="abstract_regulatory"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>

<s:form action="regulatoryInfoupdate" id="saveRegAuthority" theme="simple">
<s:actionerror/>
<h2><fmt:message key="regulatory.title" /></h2>
<!--Help Content--> 
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a> -->
<table class="form">
<%-- <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>

	<!-- <tr>
		<th colspan="2">Regulatory Information</th>
	</tr> -->
	<!--  Trial Oversight Authority Country -->
	<tr>
		<td scope="row" class="label"><label><fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span></td>
		<td class="value"><s:select id="countries" headerValue="-Select-" headerKey=""
					 name="lst"                   
                     list="countryList"  
                     listKey="id" listValue="name"                    
                     onchange="loadDiv();"                
                     />
                   <span class="formErrorMsg"> 
                      <s:fielderror>
                      <s:param>lst</s:param>
                     </s:fielderror>                            
                   </span>
         </td>      
	</tr>
	<!--  Trial Oversignt Authority Organization Name -->
	<tr>
		<td scope="row" class="label"><label><fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></td>
					<td class="value">
						<div id="loadAuthField">
						<%@ include file="/WEB-INF/jsp/nodecorate/authorityname.jsp" %>
						</div>
														
					</td>
	</tr>	
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row"  class="label"><label><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span> </td>
		<td class="value"><s:select  id="fdaindid" name="webDTO.fdaRegulatedInterventionIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkFDADropDown();"/>
		<span class="formErrorMsg"><s:fielderror><s:param>webDTO.fdaRegulatedInterventionIndicator</s:param></s:fielderror></span>
		</td>
	</tr>
	<!--   Section 801 Indicator-->
	<tr id="sec801row">
		<td scope="row" class="label"><label><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></td>
		<td class="value"><s:select id="sec801id" name="webDTO.section801Indicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkSection108DropDown();"/>
		<span class="formErrorMsg"><s:fielderror><s:param>webDTO.section801Indicator</s:param></s:fielderror></span>
		</td>
	</tr>
	
	<!--   Delayed Posting Indicator-->
	<tr id="delpostindrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></td>
		<td class="value"><s:select id="delpostindid" name="webDTO.delayedPostingIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" />
		<span class="formErrorMsg"><s:fielderror><s:param>webDTO.delayedPostingIndicator</s:param></s:fielderror></span>
		</td>		
	</tr>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr id="datamonrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
		<td class="value"><s:select id="datamonid" name="webDTO.dataMonitoringIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}"/>		
		</td>		
	</tr>
	<tr>
		<td colspan="2" class="pad10">
			<div class="line"></div>
		</td>
	</tr>
	<!--  <tr>
		<td colspan="2" class="centered"><input type="submit" value="Save" class="button"></td>
	</tr>-->
	
</table>
	
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">			
			<c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
								|| (sessionScope.role == 'SuAbstractor')}">
			<li><sx:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></sx:a></li>
			</c:if>
			<li><a href="nciSpecificInformationquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
			<li><a href="irb.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
		</ul>	
	</del>
</div>
</s:form>
</div>
<!-- </div> -->
</body>
</html>
