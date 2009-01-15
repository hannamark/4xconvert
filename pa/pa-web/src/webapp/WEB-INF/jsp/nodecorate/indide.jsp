	<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
	<script language="JavaScript">
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
						<td style="white-space:nowrap;">							
							<input type="radio" id="group3" name="holder.group3" value="IND" onclick="SelectSubCat(this);" onblur="enableAddButton();"/>IND<br/>
							<input type="radio" id="group3" name="holder.group3" value="IDE" onclick="SelectSubCat(this);" onblur="enableAddButton();"/>IDE
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.indideNumber"/>
						</td>
						<td>
							<input id="indidenumber" name="holder.indidenumber" onblur="enableAddButton();" type="text" size="10" /> 
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.grantor"/>
						</td>
						<td>
							<SELECT id="SubCat" name="holder.SubCat"onblur="enableAddButton(); cssStyle="width:150px">
								<Option value="">--Select--</option>
							</SELECT>
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
						<div id="programcodeid" style="display:''">
							<s:select id="programcodenoneselected" name="holder.programCodes" list="#{'-Select-':'-Select-'}" onblur="enableAddButton();" cssStyle="width:150px"/>
						</div>
						<div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="holder.programcodenihselectedvalue" list="#phaseCodeValuesNIH" onblur="enableAddButton();" /></div>
						<div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="holder.programcodenciselectedvalue" list="#phaseCodeValuesNCI" onblur="enableAddButton();"/></div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.expandedAccessIndicator"/>
						</td>
						<td>
							<input type="radio" name="holder.group4" id="group4" value="true" onclick="document.getElementById('expandedStatus').disabled=false;document.getElementById('addbtn').disabled=true;" onblur="enableAddButton();" /> Yes<br />
							<input type="radio" name="holder.group4" id="group4" value="false" checked="checked" onclick="document.getElementById('expandedStatus').value='';document.getElementById('expandedStatus').disabled=true;" onblur="enableAddButton();"/> No
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<fmt:message key="trialIndide.expandedAccessStatusCode"/>
						</td>
						<td>
							<s:select id="expandedStatus" headerKey="" headerValue="-Select-" name="holder.expandedStatus" disabled="true" onblur="enableAddButton();"
							list="#expandedAccessStatusCodeValues"/>
						</td>
					</tr>
				</tbody>
</table>


