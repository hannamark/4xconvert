	<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
	<script language="JavaScript">
	window.onload=checkAll;
	function checkAll(){
		if (document.getElementById('group4').value == 'Yes'){
			showRow(document.getElementById('expandedStatus'));
		} else {
			hideRow(document.getElementById('expandedStatus'));
		}
		if (document.getElementById('group3').value == 'IND'){
			addOption(document.getElementById('SubCat'),"CDER", "CDER");
			addOption(document.getElementById('SubCat'),"CBER", "CBER");
			showRow(document.getElementById('SubCat'));
		} else {
			addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
			showRow(document.getElementById('SubCat'));
		}
		if (document.getElementById('holderType').value == 'NIH'){
			showRow(document.getElementById('programcodenihid'));
			hideRow(document.getElementById('programcodenciid'));
		}
		if (document.getElementById('holderType').value == 'NCI'){
			hideRow(document.getElementById('programcodenihid'));
			showRow(document.getElementById('programcodenciid'));
		} 
		else {
			showRow(document.getElementById('programcodeid'));
		}
	}
	/**/
	function hideRow(row){			
		row.style.display = 'none';	
	}
	function showRow(row){
		row.style.display = '';
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
	</script>
	<s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.ProgramCodesForNIH@getDisplayNames()" />
	<s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.ProgramCodesForNCI@getDisplayNames()" />
	<s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />
	
	<table class="data2">	
			<tbody>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.indldeType"/>
						</td>
						<td class="value"><s:select id="group3" name="holder.group3" list="#{'IND':'IND', 'IDE':'IDE'}" onclick="SelectSubCat(this);" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.indideNumber"/>
						</td>
						<td>
							<s:textfield  name="holder.indidenumber" maxlength="10" cssStyle="width:150px"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.grantor"/>
						</td>
						<td>
							<s:if test="%{holder.group3 == 'IND'}">
								<SELECT id="SubCat" name="holder.subCat" list="#{'CDER':'CDER', 'CBER':'CBER'}" cssStyle="width:150px"></SELECT>
							</s:if><s:else>
								<SELECT id="SubCat" name="holder.subCat" list="#{'CDRH':'CDRH'}" cssStyle="width:150px"></SELECT>
							</s:else>
						</td>		
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.holderType"/>
						</td>
						<td>
							<s:select id="holderType" name="holder.holderType" headerKey="" headerValue="-Select-" onblur="enableAddButton();setProgramCodes(this);" cssStyle="width:150px" onclick="setProgramCodes(this);"
							list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.nihnciDivProgHolderCode"/>
						</td>
						<td>
							<div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="holder.programcodenihselectedvalue" list="#phaseCodeValuesNIH" onblur="enableAddButton();" cssStyle="width:150px"/></div>
							<div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="holder.programcodenciselectedvalue" list="#phaseCodeValuesNCI" onblur="enableAddButton();" cssStyle="width:150px"/></div>
						</td>
					</tr>
					<tr id="expandedAccessIndicator">
						<td colspan="2">
							<fmt:message key="trialIndide.expandedAccessIndicator"/>
						</td>
						<td class="value"><s:select id="group4" name="holder.group4" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkAll();"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.expandedAccessStatusCode"/>
						</td>
						<td>
							<s:select id="expandedStatus" headerKey="" headerValue="-Select-" name="holder.expandedStatus"
							list="#expandedAccessStatusCodeValues"/>
						</td>
					</tr>
				</tbody>
</table>


