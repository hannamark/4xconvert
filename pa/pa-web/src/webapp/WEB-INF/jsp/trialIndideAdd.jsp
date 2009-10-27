<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialIndide.addtitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}
function trim(val) {
	var ret = val.replace(/^\s+/, '');
	ret = ret.replace(/\s+$/, '');
	  return ret;
	}
function checkCode() {
var input="studyIndldeWebDTO.nihInstHolder";
var inputElement = document.forms[0].elements[input];
	
if (inputElement.options[inputElement.selectedIndex].value == "NCI-National Cancer Institute")
	{
 		document.getElementById('programcodenciid').style.display = '';
 		document.getElementById('programcodenihid').style.display = 'none';
   	}else
   	{
   		document.getElementById('programcodenciid').style.display = 'none';
 		document.getElementById('programcodenihid').style.display = '';
   	}
}
function handleAction(){
 var page;
 page=document.forms[0].page.value;
if (page == "Edit"){
 document.forms[0].action="trialIndideupdate.action";
 document.forms[0].submit(); 
} else {
 document.forms[0].action="trialIndidecreate.action";
 document.forms[0].submit();   
 } 
} 

function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
}
function getIndIdeRadioValue(size){
		for(var i=0; i<size; i++) {
			if(document.forms[0].group3[i].checked==true) 
				return(document.forms[0].group3[i].value);
		}
}
function getExpandedAccessRadioValue(size){
	for(var i=0; i<size; i++) {
		if(document.forms[0].group4[i].checked==true) 
			return(document.forms[0].group4[i].value);
	}
}
function setProgramCodes(ref){	
		if (ref.value == 'NCI') {
			document.getElementById('programcodenciid').style.display = '';
			document.getElementById('programcodenihid').style.display = 'none';
			document.getElementById('programcodeid').style.display = 'none';
		} else if (ref.value == 'NIH') {
			document.getElementById('programcodenciid').style.display = 'none';
			document.getElementById('programcodenihid').style.display = '';
			document.getElementById('programcodeid').style.display = 'none';
		} else {
			document.getElementById('programcodenihid').style.display = 'none';
			document.getElementById('programcodenciid').style.display = 'none';
			document.getElementById('programcodeid').style.display = '';
		}
}
function callAddIndIde(){	
		var indIde = getIndIdeRadioValue(document.forms[0].group3.length);
		var number = document.getElementById('indidenumber').value;
		var grantor = document.getElementById('SubCat').value;
		var holdertype = document.getElementById('holderType').value;
		var programcode;
		if( document.getElementById('programcodenihselectedvalue').value != '' ) {
			programcode = document.getElementById('programcodenihselectedvalue').value
		}else {
			programcode = document.getElementById('programcodenciselectedvalue').value;		
		}
		var expandedaccess = getExpandedAccessRadioValue(document.forms[0].group4.length);
		var expandedaccesstype = document.getElementById('expandedStatus').value;
		var  url = '/registry/protected/ajaxSubmitTrialActionaddIdeIndIndicator.action?indIde='+indIde+'&number='+number+'&grantor='+grantor+'&holdertype='+holdertype+'&programcode='+programcode+'&expandedaccess='+expandedaccess+'&expandedaccesstype='+expandedaccesstype;
    	var div = document.getElementById('indidediv');
    	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
    	callAjax(url, div);
    	resetValues();
}
function resetValues(){
		document.getElementById('indidenumber').value='';
		clearRadios('group3');
    	removeAllOptions(document.getElementById('SubCat'));
    	addOption(document.getElementById('SubCat'), "", "--Select--", "");	
    	document.getElementById('holderType').value='';    	
		document.getElementById('programcodenihselectedvalue').value='';	
		document.getElementById('programcodenciselectedvalue').value='';
		document.getElementById('programcodenihid').style.display = 'none';
		document.getElementById('programcodenciid').style.display = 'none';
		document.getElementById('programcodeid').style.display = '';
		document.forms[0].group4[0].checked = false;
		document.forms[0].group4[1].checked = true;
		document.getElementById('expandedStatus').value='';
		document.getElementById('expandedStatus').disabled=true;
		document.getElementById('addbtn').disabled=true;
}
function clearRadios( radioname ){
	   for( i = 0; i < document.forms[0][radioname].length; i++ )
	      document.forms[0][radioname][i].checked = false;
}
function removeAllOptions(selectbox){
		var i;
		for(i=selectbox.options.length-1;i>=0;i--){
			selectbox.remove(i);
		}
}
function addOption(selectbox, value, text ){
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
}	
function SelectSubCat(i){
		removeAllOptions(document.getElementById('SubCat'));
		addOption(document.getElementById('SubCat'), "", "-Select-", "");	
		if(i.value == 'IND'){
			addOption(document.getElementById('SubCat'),"CDER", "CDER");
			addOption(document.getElementById('SubCat'),"CBER", "CBER");
		}
		if(i.value == 'IDE'){
			addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
		}
}
</SCRIPT>

<body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="review_ind"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstract_ind"/>
</c:if>
 <h1><fmt:message key="trialIndide.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialIndide.addtitle" /></h2>
    	<input type="hidden" name="page" value="${page}" />
    	<input type="hidden" name="cbValue" value="${cbValue}" />
    	<s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.NihInstituteCode@getDisplayNames()" />
		<s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
		<s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />
    <table class="form">   
  					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.indldeType"/>:<span class="required">*</span></label>
						</td>
						<td class="value">							
							<input type="radio" id="group3" name="studyIndldeWebDTO.indldeType" value="IND" onclick="SelectSubCat(this);" />IND<br/>
							<input type="radio" id="group3" name="studyIndldeWebDTO.indldeType" value="IDE" onclick="SelectSubCat(this);" />IDE
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.indldeType</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.indideNumber"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<input id="indidenumber" name="studyIndldeWebDTO.indldeNumber"  type="text" size="10" /> 
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.indldeNumber</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.grantor"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<SELECT id="SubCat" name="studyIndldeWebDTO.grantor" cssStyle="width:150px">
								<Option value="">--Select--</option>
							</SELECT>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.grantor</s:param>
                               </s:fielderror>                            
                         </span>
						</td>	
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.holderType"/>:<span class="required">*</span><label>
						</td>
						<td class="value">
							<s:select id="holderType" name="studyIndldeWebDTO.holderType" headerKey="" headerValue="-Select-" cssStyle="width:150px" onclick="setProgramCodes(this);"
							list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.holderType</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.nihnciDivProgHolderCode"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
						<div id="programcodeid" style="display:''">
							<s:select id="programcodenoneselected"  list="#{'-Select-':'-Select-'}"  cssStyle="width:150px"/>
						</div>
						<div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nihInstHolder" list="#phaseCodeValuesNIH" onclick="checkCode();" /></div>
						<div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nciDivProgHolder" list="#phaseCodeValuesNCI" /></div>
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.nihInstHolder</s:param>
                               </s:fielderror>                            
                         </span>
                         <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.nciDivProgHolder</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.expandedAccessIndicator"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<input type="radio" name="studyIndldeWebDTO.expandedAccessIndicator" id="group4" value="true" onclick="document.getElementById('expandedStatus').disabled=false;document.getElementById('addbtn').disabled=true;"  /> Yes<br />
							<input type="radio" name="studyIndldeWebDTO.expandedAccessIndicator" id="group4" value="false" checked="checked" onclick="document.getElementById('expandedStatus').value='';document.getElementById('expandedStatus').disabled=true;" /> No
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.expandedAccessIndicator</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.expandedAccessStatusCode"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<s:select id="expandedStatus" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.expandedAccessStatus" disabled="true" 
							list="#expandedAccessStatusCodeValues"/>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.expandedAccessStatus</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
    </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                    <li><s:a href="trialIndidequery.action" cssClass="btn"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>
                </ul>   
            </del>
        </div>             
    </s:form>
   </div>
 </body>
 </html>