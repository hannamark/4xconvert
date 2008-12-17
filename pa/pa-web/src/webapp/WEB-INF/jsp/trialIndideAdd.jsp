<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialIndide.addtitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
function trim(val) {
	var ret = val.replace(/^\s+/, '');
	ret = ret.replace(/\s+$/, '');
	  return ret;
	}

function handleAction(){
 var page;
 page=document.forms[0].page.value;

var number = document.getElementById('indidenumber').value;
number = trim(number);
if( number == "") {
	alert("Please enter an IND/IDE number")
	return false;
}
var grantor = document.getElementById('SubCat').value;
if( grantor == "") {
	alert("Please select a Grantor")
	return false;
}
var holdertype = document.getElementById('holderType').value;
if( holdertype == "") {
	alert("Please select an IND Holder Type")
	return false;
}
if ((document.getElementById('programcodenciselectedvalue').value == '') && (holdertype == 'NCI') ) {
	alert("Please select a Division/Program Code");
	return false;
}
if ((document.getElementById('programcodenihselectedvalue').value == '') && (holdertype == 'NIH') ) {
	alert("Please select a NIH Institute");
	return false;
}
var expandedaccess = getExpandedAccessRadioValue(document.forms[0].group4.length);
if ((expandedaccess == 'true') && (document.getElementById('expandedStatus').value == '') ) {
	alert("Please select an Expanded Access Status ");
	return false;
}
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
</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialIndide.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form>
    <h2><fmt:message key="trialIndide.addtitle" /></h2>
    	<input type="hidden" name="page" value="${page}" />
    	<input type="hidden" name="cbValue" value="${cbValue}" />
    <table class="form">   
  		<tr>
  			<td colspan="2" class="space">&nbsp;</td>
    	</tr>
    	<tr><td colspan="2" class="space">
			<%@ include file="/WEB-INF/jsp/nodecorate/indide.jsp" %>
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