	<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
	<script language="JavaScript">
	function trim(val) {
		var ret = val.replace(/^\s+/, '');
		ret = ret.replace(/\s+$/, '');
		  return ret;
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
		number = trim(number);
		if( number == "") {
			alert("Invalid IND/IDE number")
			return false;
		}
		var grantor = document.getElementById('SubCat').value;
		var holdertype = document.getElementById('holderType').value;
		var programcode;
		if( document.getElementById('programcodenihselectedvalue').value != '' ) {
			programcode = document.getElementById('programcodenihselectedvalue').value
		}else {
			programcode = document.getElementById('programcodenciselectedvalue').value;		
		}
		var expandedaccess = getExpandedAccessRadioValue(document.forms[0].group4.length);
		var expandedaccesstype = document.getElementById('expanded_status').value;
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
		document.getElementById('expanded_status').value='';
		document.getElementById('expanded_status').disabled=true;
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
	function enableAddButton(){
		var indide = getIndIdeRadioValue(document.forms[0].group3.length);
		var number = document.getElementById('indidenumber').value;
		var grantor = document.getElementById('SubCat').value;
		var holder = document.getElementById('holderType').value;

		if(indide != '' && number != '' && grantor != '' && holder != ''){
			var expandedaccess = getExpandedAccessRadioValue(document.forms[0].group4.length);
			if(expandedaccess == 'Yes') {
				var expandedaccesstype = document.getElementById('expanded_status').value;
				if(expandedaccesstype != '') {
					document.getElementById('addbtn').disabled = false;
				}
			} else {
				document.getElementById('addbtn').disabled = false;
			}
		}
	}	
	function deleteIndIde(rowid){
	
		var  url = '/registry/protected/ajaxSubmitTrialActiondeleteIndIde.action?uuid='+rowid;
    	var div = document.getElementById('indidediv');
    	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
    	callAjax(url, div);				
	}
	function bindNIHToolTipOnLoad(){
		var opts = document.getElementById('programcodenihselectedvalue').options;
		 for (var i=0; i<opts.length; i++) {
			opts[i].title=opts[i].value;
	  }
	}
	function bindNCIToolTipOnLoad(){
		var opts = document.getElementById('programcodenciselectedvalue').options;
		 for (var i=0; i<opts.length; i++) {
			opts[i].title=opts[i].value;
	  }
	}	
	function enableTooltip(ref){
	if(ref == 'nih')
		document.getElementById('programcodenihselectedvalue').title = document.getElementById('programcodenihselectedvalue').value;
	else
		document.getElementById('programcodenciselectedvalue').title = document.getElementById('programcodenciselectedvalue').value;
	}
	</script>
	<s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.ProgramCodesForNIH@getDisplayNames()" />
	<s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.ProgramCodesForNCI@getDisplayNames()" />

	<table class="data2">	

		<tbody>
			<tr>
				<th>IND/IDE Types</th>
				<th>IND/IDE Number</th>
				<th>IND/IDE Grantor</th>
				<th>IND/IDE Holder Type</th>
				<th>NIH Institution, NCI Division/Program Code (if applicable)</th>
				<th>Expanded Access</th>
				<th>Expanded Access Type (if applicable)</th>
				<th></th>
			</tr>
		
			<tr>
				<td style="white-space:nowrap;">							
					<input type="radio" name="group3" value="IND" onclick="SelectSubCat(this);" onblur="enableAddButton();"/>IND<br/>
					<input type="radio" name="group3" value="IDE" onclick="SelectSubCat(this);" onblur="enableAddButton();"/>IDE
				</td>
				<td>
					<input id="indidenumber" name="indidenumber" onblur="enableAddButton();" type="text" size="10" /> 
				</td>
				<td>
					<SELECT id="SubCat" name="SubCat" onblur="enableAddButton(); cssStyle="width:150px">
						<Option value="">-Select-</option>
					</SELECT>
				</td>	
				 					
				<td>
					<s:select id="holderType" name="holderType" headerKey="" headerValue="-Select-" onblur="enableAddButton();setProgramCodes(this);bindNIHToolTipOnLoad();bindNCIToolTipOnLoad();" cssStyle="width:75px" onclick="setProgramCodes(this);"
					list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>
					
				</td>
				<td>
				<div id="programcodeid" style="display:''">
					<s:select id="programcodenoneselected" list="#{'-Select-':'-Select-'}" onblur="enableAddButton();" cssStyle="width:300px"/>
					</div>
				<div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="programcodenihselectedvalue" list="#phaseCodeValuesNIH" onmouseover="enableTooltip('nih');" onblur="enableAddButton();" cssStyle="width:300px"/></div>
				<div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="programcodenciselectedvalue" list="#phaseCodeValuesNCI" onmouseover="enableTooltip('nci');" onblur="enableAddButton();" cssStyle="width:300px"/></div>
				</td>
				<td>
					<input type="radio" name="group4" id="group4" value="Yes" onclick="document.getElementById('expanded_status').disabled=false;document.getElementById('addbtn').disabled=true;" onblur="enableAddButton();" /> Yes<br />
					<input type="radio" name="group4" id="group4" value="No" checked="checked" onclick="document.getElementById('expanded_status').value='';document.getElementById('expanded_status').disabled=true;" onblur="enableAddButton();"/> No
				</td>
				<td>
					<s:select id="expanded_status" headerKey="" headerValue="-Select-" name="expanded_status" disabled="true" onblur="enableAddButton();"
					list="#{'Available':'Available','No longer available':'No longer available','No longer available':'No longer available','Approved for marketing':'Approved for marketing'}"/>
					
				</td>
				<td>
					<input type="button" id="addbtn" onclick="callAddIndIde();" value="Add IND/IDE.." disabled="disabled">
				</td>
			</tr>
			<tr>
			</tr>
		</tbody>
	

</table>


