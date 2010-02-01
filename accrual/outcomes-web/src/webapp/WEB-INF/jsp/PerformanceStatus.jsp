<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleSaveAction() {
		if (document.getElementById('performance.performanceSystem').value == 'Karnofsky'){
			document.forms[0].elements["performance.performanceStatus"].value = document.forms[0].elements["performance.karnofskyStatus"].value
		} else if (document.getElementById('performance.performanceSystem').value == 'ECOG'){
			document.forms[0].elements["performance.performanceStatus"].value = document.forms[0].elements["performance.ecogStatus"].value
		} else if (document.getElementById('performance.performanceSystem').value == 'Lansky'){
			document.forms[0].elements["performance.performanceStatus"].value = document.forms[0].elements["performance.lanskyStatus"].value
		} else {
			document.forms[0].elements["performance.performanceStatus"].value ="";
		}
		document.forms[0].action = "savePerformanceStatus.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executePerformanceStatus.action";
        document.forms[0].submit();
    }
    function setPerformanceStatusCodes(ref){	
		if (ref.value == 'Karnofsky') {
			document.getElementById('karnofskyStatusid').style.display = '';
			document.getElementById('ecogStatusid').style.display = 'none';
			document.getElementById('lanskyStatusid').style.display = 'none';
			document.getElementById('performanceStatusid').style.display = 'none';
		} else if (ref.value == 'ECOG') {
			document.getElementById('karnofskyStatusid').style.display = 'none';
			document.getElementById('ecogStatusid').style.display = '';
			document.getElementById('lanskyStatusid').style.display = 'none';
			document.getElementById('performanceStatusid').style.display = 'none';
		} else if (ref.value == 'Lansky') {
			document.getElementById('karnofskyStatusid').style.display = 'none';
			document.getElementById('ecogStatusid').style.display = 'none';
			document.getElementById('lanskyStatusid').style.display = '';
			document.getElementById('performanceStatusid').style.display = 'none';
		} else {
			document.getElementById('karnofskyStatusid').style.display = 'none';
			document.getElementById('ecogStatusid').style.display = 'none';
			document.getElementById('lanskyStatusid').style.display = 'none';
			document.getElementById('performanceStatusid').style.display = '';
		}
	}
	function checkStatus(){
		if (document.getElementById('performance.performanceSystem').value == 'Karnofsky'){
			showRow(document.getElementById('karnofskyStatusid'));
			hideRow(document.getElementById('ecogStatusid'));
			hideRow(document.getElementById('lanskyStatusid'));
			hideRow(document.getElementById('performanceStatusid'));
		} else if (document.getElementById('performance.performanceSystem').value == 'ECOG'){
			hideRow(document.getElementById('karnofskyStatusid'));
			showRow(document.getElementById('ecogStatusid'));
			hideRow(document.getElementById('lanskyStatusid'));
			hideRow(document.getElementById('performanceStatusid'));
		} else if (document.getElementById('performance.performanceSystem').value == 'Lansky'){
			hideRow(document.getElementById('karnofskyStatusid'));
			hideRow(document.getElementById('ecogStatusid'));
			showRow(document.getElementById('lanskyStatusid'));
			hideRow(document.getElementById('performanceStatusid'));
		} else {
			showRow(document.getElementById('performanceStatusid'));
		}
	}
	function hideRow(row){			
			row.style.display = 'none';	
	}
	function showRow(row){
			row.style.display = '';
	}
	window.onload = checkStatus;
	

</script>
<title>
        <c:set var="topic" scope="request" value="performance"/> 
     Performance Status
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Performance Status
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if><s:else><accrual:sucessMessage /></s:else>
<s:form name="detailForm">
<s:hidden name="performance.id" />
<s:hidden name="performance.performanceStatus" />
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="perfStatus.label.system"/><span class="required">*</span></label></td>
<td><s:set name="performanceSystems" value="@gov.nih.nci.pa.enums.PerformanceSystemCode@getDisplayNames()" />
		<s:select id="performance.performanceSystem" name="performance.performanceSystem" headerKey="" headerValue="--Select--"
               list="#performanceSystems" value="performance.performanceSystem.code" onclick="setPerformanceStatusCodes(this);"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>performance.performanceSystem</s:param></s:fielderror></td></tr>
               
<tr><td scope="row" class="label"><label><fmt:message key="perfStatus.label.status"/><span class="required">*</span></label></td>
<td>
		<div id="performanceStatusid" style="display:''">
				<select name="" id="performanceStatusnoneselected" style="width:150px">
    					<option value="-Select-">-Select-</option>
    			</select>
    	</div>
    	<div id="ecogStatusid" style="display:none">
    			<s:set name="ecogStatuses" value="@gov.nih.nci.pa.enums.EcogPerformanceStatusCode@getDisplayNames()" />
				<s:select id="performance.ecogStatus" name="performance.ecogStatus" headerKey="" headerValue="--Select--"
               list="#ecogStatuses" value="performance.performanceStatus.code"/>
        </div> 
		<div id="karnofskyStatusid" style="display:none">
				<s:set name="karnofskyStatuses" value="@gov.nih.nci.pa.enums.KarnoskyPerformanceStatusCode@getDisplayNames()" />
				<s:select id="performance.karnofskyStatus" name="performance.karnofskyStatus" headerKey="" headerValue="--Select--"
	               list="#karnofskyStatuses" value="performance.performanceStatus.code"/>
	     </div>
		<div id="lanskyStatusid" style="display:none">
				<s:set name="lanskyStatuses" value="@gov.nih.nci.pa.enums.LanskyPerformanceStatusCode@getDisplayNames()" />
				<s:select id="performance.lanskyStatus" name="performance.lanskyStatus" headerKey="" headerValue="--Select--"
               list="#lanskyStatuses" value="performance.performanceStatus.code"/>
        </div>
         <s:fielderror cssClass="formErrorMsg"><s:param>performance.performanceStatus</s:param></s:fielderror></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
