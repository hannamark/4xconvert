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
<script type="text/javascript" src="scripts/js/calendarpopup.js" />  
<script type="text/javascript" src="scripts/js/prototype.js"></script>
<script type="text/javascript" src="scripts/js/scriptaculous.js"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
<c:url value="/protected/participatingOrganizationsnodecorlookup.action" var="lookupUrl"/>

<script type="text/javascript">
            var siteRecruitmentStatusDate = new CalendarPopup();
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
<div class="box"><s:form name="studyOverallStatus">
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

			<table class="form">
				<div id="loadOrgDetails">
					<%@ include file="/WEB-INF/jsp/nodecorate/nodecororgdetails.jsp" %>
				</div>
				<tr>
					<td scope="row" class="label"><label for="srs">Site
					Recruitment Status:</label></td>
                    <s:set name="recruitmentStatusValues"
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2"><s:select headerKey="" headerValue=""
                        name="partOrgData.recruitmentStatus"
                        list="#recruitmentStatusValues" /></td>
                    <td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="srsd">Site
					Recruitment Status Date:</label></td>
					<td class="value" colspan="2">

                    <s:textfield name="partOrgData.recruitmentStatusDate"
                        maxlength="10" size="10" cssStyle="width:70px;float:left"/> 
                           <a href="javascript:;"
                              onclick="cal.select(document.forms[0].partOrgData.recruitmentStatusDate,'calendarbutton','MM/dd/yyyy'); return false;"
                              name="calendarbutton" 
                              id="calendarbutton"> <img
                              src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                           </a>
					</td>
				</tr>
			</table>

			<div class="actionsrow"><del class="btnwrapper">
			<ul class="btnrow">
				<li><a href="participatingOrganizationsfacilitySave.action"  class="btn" onclick="this.blur();"><span
					class="btn_img"><span class="save">Save</span></span></a></li>
			</ul>
			</del></div>


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
</s:form>
</div>
</body>
</html>