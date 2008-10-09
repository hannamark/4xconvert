<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head/>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" src="scripts/js/prototype.js"></script>
<script type="text/javascript" src="scripts/js/scriptaculous.js"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/protected/participatingOrganizationsnodecorlookup.action" var="lookupUrl"/>

<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "recStatusDate", "facility");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
        
function facilitySave(){
     document.facility.action="participatingOrganizationsfacilitySave.action#investigators";
     document.facility.submit();     
}
function facilityUpdate(){
     document.facility.action="participatingOrganizationsfacilityUpdate.action#investigators";
     document.facility.submit();     
}
</script>
     
</head>
<SCRIPT LANGUAGE="JavaScript">
	function lookup(){
	    showPopWin('${lookupUrl}', 1050, 400, '', 'Organization');
	}	
	function loadDiv(orgid){
		 
		 //document.location.href="/pa/protected/participatingOrganizationsdisplayOrg.action?orgId="+orgid;
		 var url = '/pa/protected/ajaxptpOrgdisplayOrg.action?orgId='+orgid;
	     var div = document.getElementById('loadOrgDetails');   
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
	     var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	     });
	     return false;
	}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
<h1><fmt:message key="participatingOrganizations.title" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
    <pa:sucessMessage/>
    <s:actionerror />
<h2><fmt:message key="participatingOrganizations.title" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr><td colspan="2"><!--Tabs -->

			<ul id="maintabs" class="tabs">
				<li><a href="#facility" class="active">Facility</a></li>
				<li><a href="#investigators">Investigators</a></li>
				<li><a href="#contacts">Contacts</a></li>
			</ul>

			<!--/Tabs --> <!-- 
                    directions on http://livepipe.net/control/tabs 
                    - make sure you add control.tabs.js to your scripts directory! 
                    - Matt 
                --> <script type="text/javascript">             
                    //<![CDATA[
                    Event.observe(window,'load',function(){
                        $$('.tabs').each(function(tabs){
                            new Control.Tabs(tabs);
                        });
                    });
                    //]]>
                </script>


			<div id="tabboxwrapper"><!--Facility-->

			<div id="facility" class="box">

			<h3>Facility</h3>
            <s:form name="facility">
			<table class="form">
			    <s:if test="%{newParticipation}">
                    <div id="loadOrgDetails">
                        <%@ include file="/WEB-INF/jsp/nodecorate/nodecororgdetails.jsp" %>
                    </div>
                </s:if>
			    <s:else>
					<tr>
					    <td scope="row" class="label"><s:label for="editOrg.name">Organization Name:</s:label></td>
					    <td class="value" colspan="2">
					        <s:textfield name="editOrg.name" maxlength="200" size="100"
					        disabled="disabled" cssStyle="width: 250px" readonly="true"/>
					    </td>
					</tr>
					<tr>
					    <td scope="row" class="label"><s:label for="editOrg.city">City:</s:label></td>
					    <td class="value" colspan="2">
					        <s:textfield name="editOrg.city" maxlength="200" size="200" 
					        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
					    </td>
					</tr>
					<tr>
					    <td scope="row" class="label"><s:label for="editOrg.countryName">Country:</s:label></td>
					    <td class="value" colspan="2">
					        <s:textfield name="editOrg.countryName" maxlength="200" size="200" 
					        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
					</tr>
					<tr>
					    <td scope="row" class="label"><s:label for="editOrg.postalCode">Zip/Postal Code:</s:label></td>
					    <td class="value" colspan="2">
					        <s:textfield name="editOrg.postalCode" maxlength="200" size="200" 
					        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
					    </td>
					</tr>
                </s:else>
				<tr>
					<td scope="row" class="label"><s:label for="srs">Site Recruitment Status:</s:label></td>
                    <s:set name="recruitmentStatusValues"
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2"><s:select headerKey="" headerValue=""
                        name="recStatus"
                        list="#recruitmentStatusValues" /></td>
                    <td>
				</tr>
				<tr>
					<td scope="row" class="label"><s:label for="srsd">Site Recruitment Status Date:</s:label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="recStatusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    </td>               
				</tr>
			</table>

			<div class="actionsrow"><del class="btnwrapper">
			<ul class="btnrow">
				<li>
                    <s:if test="%{newParticipation}">
				        <s:a href="#" cssClass="btn" onclick="facilitySave()"><span class="btn_img">
				            <span class="save">Save</span></span></s:a>
				    </s:if>
				    <s:else>
                        <s:a href="#" cssClass="btn" onclick="facilityUpdate()"><span class="btn_img">
                            <span class="save">Save</span></span></s:a>
				    </s:else>
				</li>
			</ul>
			</del></div>
            </s:form>


			</div>
			<!--/Facility--></div>

			</td></tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <li><a href=" trialFundingquery.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            <li><a href="nciSpecificInformationquery.action" 
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </ul>   
    </del>
</div>
</div>
</body>
</html>