<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<script language="JavaScript">
	function loadDiv() {	
	var type = document.getElementById("type").value;
		if(type == 'radiationTotalDoseUom') {
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'radiationTotalDoseUom';
		}else if(type == 'radiationDurationUom') {
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'radiationDurationUom';
		}else if(type == 'doseUom'){
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'doseUom';
		}else if(type == 'doseRoa') {
			document.forms[0].action="lookUprouteOfAdministration.action";
			document.getElementById("type").value = 'doseRoa';
		}else if(type == 'doseFrequency') {
			document.forms[0].action="lookUpdoseFrequency.action";
			document.getElementById("type").value = 'doseFrequency';
		}else if(type == 'doseDurationUom') {
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'doseDurationUom';
		}else if(type == 'totalDoseUom') {
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'totalDoseUom';
		}else if(type == 'lesionSite') {
			document.forms[0].action="lookUplesionLocationAnatomicSite.action";
			document.getElementById("type").value = 'lesionSite';
		}else if(type == 'assessmentType') {
			document.forms[0].action="lookUpassessmentType.action";
			document.getElementById("type").value = 'assessmentType';
		}else if(type == 'diseaseAutopsy') {
			document.forms[0].action="lookUpanatomicSites.action";
			document.getElementById("type").value = 'diseaseAutopsy';
		}else if(type == 'tumorMarker') {
			document.forms[0].action="lookUptumorMarker.action";
			document.getElementById("type").value = 'tumorMarker';
		}else if(type == 'tumorMarkeruom') {
            document.forms[0].action="lookUpunitOfMeasurement.action";
            document.getElementById("type").value = 'tumorMarkeruom';
        }else if(type == 'radiationDoseUom') {
			document.forms[0].action="lookUpunitOfMeasurement.action";
			document.getElementById("type").value = 'radiationDoseUom';
		}else if(type == 'radiationFrequency') {
			document.forms[0].action="lookUpdoseFrequency.action";
			document.getElementById("type").value = 'radiationFrequency';
		}
	    document.forms[0].submit();
	}
	function populateFields(name,type,displayName,id) {
		if(type == 'radiationTotalDoseUom') {
			window.top.document.getElementsByName("radiation.totalDose.unit")[0].value = name;
		}else if(type == 'radiationDurationUom') {
			window.top.document.getElementsByName("radiation.duration.unit")[0].value = name;
		}else if(type == 'doseUom'){
			window.top.document.getElementsByName("drugBiologic.dose.unit")[0].value = name;
		}else if(type == 'doseRoa') {
			window.top.document.getElementsByName("drugBiologic.doseRoute")[0].value = name;
		}else if(type == 'doseFrequency') {
			window.top.document.getElementsByName("drugBiologic.doseFreq")[0].value = displayName;
			window.top.document.getElementsByName("drugBiologic.doseFreqId")[0].value = id;
		}else if(type == 'doseDurationUom') {
			window.top.document.getElementsByName("drugBiologic.doseDur.unit")[0].value = name;
		}else if(type == 'totalDoseUom') {
			window.top.document.getElementsByName("drugBiologic.doseTotal.unit")[0].value = name;
		}else if(type == 'lesionSite') {
			window.top.document.getElementsByName("lesionAssessment.lesionSite")[0].value = name;
		}else if(type == 'assessmentType') {
			window.top.document.getElementsByName("targetOutcome.assessmentType")[0].value = name;
		}else if(type == 'diseaseAutopsy') {
			window.top.document.getElementsByName("deathInfo.autopsySite")[0].value = name;
		}else if(type == 'tumorMarker') {
			window.top.document.getElementsByName("tumorMarker.tumorMarker")[0].value = name;
		}else if(type == 'tumorMarkeruom') {
            window.top.document.getElementsByName("tumorMarker.tmvUom.unit")[0].value = name;
        }else if(type == 'radiationDoseUom') {
			window.top.document.getElementsByName("radiation.dose.unit")[0].value = name;
		}else if(type == 'radiationFrequency') {
			window.top.document.getElementsByName("radiation.doseFreq")[0].value = displayName;			
			window.top.document.getElementsByName("radiation.doseFreqId")[0].value = id;
		}
        window.top.hidePopWin(false); 
	}
</script>
</head> 
<body>
<div class="box">
<s:form>
<h2>Search</h2>
<s:hidden id="type" name="type" />
<s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
	<table  class="form">
		<tr><td scope="row" class="label">
		<label>			
			<s:if test="(type == 'radiationTotalDoseUom') || (type == 'radiationDurationUom')
									|| (type == 'doseUom') || (type == 'doseDurationUom')
									|| (type == 'totalDoseUom')	|| (type == 'tumorMarkeruom')
									|| (type == 'radiationDoseUom') ">
			UOM
			</s:if>
			<s:elseif test="type == 'doseRoa'">
			Route Of Administration
			</s:elseif>
			<s:elseif test="(type == 'doseFrequency') || (type == 'radiationFrequency')">
			Frequency
			</s:elseif>
			<s:elseif test="type == 'lesionSite'">
			Lesion Site
			</s:elseif>
			<s:elseif test="type == 'assessmentType'">
			Assessment Type
			</s:elseif>
			<s:elseif test="type == 'diseaseAutopsy'">
			Anatomic Site
			</s:elseif>
			<s:elseif test="type == 'tumorMarker'">
			Tumor Marker
			</s:elseif>
		</label></td>
		<td><s:textfield size="50" name="searchText"
		               cssStyle="width:280px;float:left"/></td></tr>
		</table>

    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li>
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
               </li>
            </ul>
          </del>
    </div>
    <div class="line"></div>
    <div id="getSurgery" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupDisplayList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>