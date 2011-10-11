<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript">
    function trim(val) {
        var ret = val.replace(/^\s+/, '');
        ret = ret.replace(/\s+$/, '');
          return ret;
        }
    
    function getIndIdeRadioValue(size) {
        for (var i=0; i<size; i++) {
            if (document.forms[0].group3[i].checked == true) {
                return(document.forms[0].group3[i].value);
            }    
        }
    }
    
    function getExpandedAccessRadioValue(size) {
        for (var i=0; i<size; i++) {
            if (document.forms[0].group4[i].checked == true) {
                return(document.forms[0].group4[i].value);
            }        
        }
    }
    
    function setExpandedStatus(selection) {
        if (selection.checked == true) {
            document.getElementById('expanded_status').disabled=false;
        } else {
            document.getElementById('expanded_status').value='';
            document.getElementById('expanded_status').disabled=true;
        }
    }
    
    function setProgramCodes(ref) {
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
    
    function callAddIndIde() {
        var indide = document.getElementById('group3').value;
        var number = document.getElementById('indidenumber').value;
        var grantor = document.getElementById('SubCat').value;
        var holder = document.getElementById('holderType').value;
        number = trim(number);
        if (indide == "") {
            alert("Please choose an IND/IDE Type");
            return false;
        }
        if (number == "") {
            alert("Please enter an IND/IDE number");
            return false;
        } else {
            var numericExpression = /^[A-Za-z0-9,._]+$/;
            if (!numericExpression.test(number)) {
                alert("IND/IDE  Number must be alphanumeric");
                return false;
            }
        }
        var grantor = document.getElementById('SubCat').value;
        if (grantor == "") {
            alert("Please select a Grantor");
            return false;
        }
        var holdertype = document.getElementById('holderType').value;
        if (holdertype == "") {
            alert("Please select an IND Holder Type");
            return false;
        }
        var programcode;
        if ((document.getElementById('programcodenciselectedvalue').value == '') && (holdertype == 'NCI')) {
            alert("Please select a Division/Program Code");
            return false;
        }
        if ((document.getElementById('programcodenihselectedvalue').value == '') && (holdertype == 'NIH')) {
            alert("Please select a NIH Institution");
            return false;
        }
        if (document.getElementById('programcodenihselectedvalue').value != '') {
            programcode = document.getElementById('programcodenihselectedvalue').value
        } else {
            programcode = document.getElementById('programcodenciselectedvalue').value;
        }
        var expandedaccess = document.forms[0].group4;
        if (expandedaccess.checked == true) {
            expandedaccess ='Yes';
        } else {
            expandedaccess ='No';
        }
        var expandedaccesstype = document.getElementById('expanded_status').value;
        if ((expandedaccess == 'Yes') && expandedaccesstype == '') {
            alert("Please select an Expanded Access Type");
            return false;
        }
        var exemptIndicator = document.getElementById('exemptIndicator');
        if (exemptIndicator.checked == true) {
            exemptIndicator ='Yes';
        } else {
            exemptIndicator ='No';
        }
        addIndIde(indide,number,grantor,holdertype,programcode,expandedaccess,expandedaccesstype,exemptIndicator);
    }
    
    function resetValues() {
        document.getElementById('indidenumber').value = '';
        document.getElementById('group3').value = '';
        removeAllOptions(document.getElementById('SubCat'));
        addOption(document.getElementById('SubCat'), "", "--Select--", "");
        document.getElementById('holderType').value = '';
        document.getElementById('programcodenihselectedvalue').value = '';
        document.getElementById('programcodenciselectedvalue').value = '';
        document.getElementById('programcodenihid').style.display = 'none';
        document.getElementById('programcodenciid').style.display = 'none';
        document.getElementById('programcodeid').style.display = '';
        document.getElementById('group4').checked = false;
        document.getElementById('expanded_status').value = '';
        document.getElementById('expanded_status').disabled = true;
        document.getElementById('exemptIndicator').checked = false;
    }
    
    function clearRadios(radioname) {
       for (i = 0; i<document.forms[0][radioname].length; i++) {
           document.forms[0][radioname][i].checked = false;
       }   
    }
    
    function removeAllOptions(selectbox) {
        var i;
        for(var i = selectbox.options.length-1; i>=0; i--) {
            selectbox.remove(i);
        }
    }
    
    function addOption(selectbox, value, text) {
        var optn = document.createElement("OPTION");
        optn.text = text;
        optn.value = value;
        selectbox.options.add(optn);
    }
    
    function SelectSubCat(i) {
        removeAllOptions(document.getElementById('SubCat'));
        addOption(document.getElementById('SubCat'), "", "-Select-", "");
        if (i.value == 'IND') {
            addOption(document.getElementById('SubCat'),"CDER", "CDER");
            addOption(document.getElementById('SubCat'),"CBER", "CBER");
        }
        if (i.value == 'IDE') {
            addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
            addOption(document.getElementById('SubCat'),"CBER", "CBER");
        }
    }
    
    function enableAddButton() {
        var indide = getIndIdeRadioValue(document.forms[0].group3.length);
        var number = document.getElementById('indidenumber').value;
        var grantor = document.getElementById('SubCat').value;
        var holder = document.getElementById('holderType').value;
        if (indide != '' && number != '' && grantor != '' && holder != '') {
            var expandedaccess = getExpandedAccessRadioValue(document.forms[0].group4.length);
            if (expandedaccess == 'Yes') {
                var expandedaccesstype = document.getElementById('expanded_status').value;
                if(expandedaccesstype != '') {
                    document.getElementById('addbtn').disabled = false;
                }
            } else {
                document.getElementById('addbtn').disabled = false;
            }
        }
    }

    function bindNIHToolTipOnLoad() {
        var opts = document.getElementById('programcodenihselectedvalue').options;
        for (var i = 0; i<opts.length; i++) {
            opts[i].title=opts[i].value;
        }
    }
    
    function bindNCIToolTipOnLoad() {
        var opts = document.getElementById('programcodenciselectedvalue').options;
        for (var i = 0; i<opts.length; i++) {
            opts[i].title=opts[i].value;
        }
    }
    
    function enableTooltip(ref) {
        if (ref == 'nih') {
            document.getElementById('programcodenihselectedvalue').title = document.getElementById('programcodenihselectedvalue').value;
        } else {
            document.getElementById('programcodenciselectedvalue').title = document.getElementById('programcodenciselectedvalue').value;
        }    
    }
</script>
<s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.NihInstituteCode@getDisplayNames()" />
<s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
<s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />

<table class="form">
    <tbody>
        <tr>
            <th><reg-web:displayTooltip tooltip="tooltip.ind_ide_type">IND/IDE Types</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.ind_ide_number">IND/IDE Number</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.ind_ide_grantor">IND/IDE Grantor</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.ind_ide_holder_type">IND/IDE Holder Type</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.nih_institution_nci_division_program_code">NIH Institution, NCI Division/Program Code (if applicable)</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.has_expanded_access_indicator">Expanded Access</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.has_expanded_status">Expanded Access Type (if applicable)</reg-web:displayTooltip></th>
            <th><reg-web:displayTooltip tooltip="tooltip.has_exempt_indicator">Exempt? (if applicable)</reg-web:displayTooltip></th>
            <th></th>
        </tr>
        <tr>
            <td style="white-space:nowrap;">
                <s:select id="group3" name="indldeType" headerKey="" headerValue="-Select-" onblur="SelectSubCat(this);" cssStyle="width:75px" onclick="SelectSubCat(this);"
                list="#{'IND':'IND','IDE':'IDE'}"/>

            </td>
            <td>
                <input id="indidenumber" name="indidenumber"  type="text" size="10" />
            </td>
            <td>
                <SELECT id="SubCat" name="SubCat" style="width:75px">
                    <Option value="">-Select-</option>
                </SELECT>
            </td>
            <td>
                <s:select id="holderType" name="holderType" headerKey="" headerValue="-Select-" onblur="setProgramCodes(this);bindNIHToolTipOnLoad();bindNCIToolTipOnLoad();" cssStyle="width:75px" onclick="setProgramCodes(this);"
                list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>

            </td>
            <td>
                <div id="programcodeid" style="display:''">
                    <s:select id="programcodenoneselected" list="#{'-Select-':'-Select-'}"  cssStyle="width:300px"/>
                </div>
                <div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="programcodenihselectedvalue" list="#phaseCodeValuesNIH" onmouseover="enableTooltip('nih');"  cssStyle="width:300px"/></div>
                <div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="programcodenciselectedvalue" list="#phaseCodeValuesNCI" onmouseover="enableTooltip('nci');"  cssStyle="width:300px"/></div>
            </td>
            <td>
                <input type="checkbox" name="group4" id="group4" onclick="setExpandedStatus(this);"/> Yes
            </td>
            <td>
                <s:select id="expanded_status" headerKey="" headerValue="-Select-" name="expanded_status" disabled="true"
                list="#expandedAccessStatusCodeValues"/>

            </td>
            <td>
                <input type="checkbox" name="exemptIndicator" id="exemptIndicator" /> Yes
            </td>
            <td>
                <input type="button" id="addbtn" onclick="callAddIndIde();" value="Add IND/IDE" >
            </td>
        </tr>
    </tbody>
</table>
