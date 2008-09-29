<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/prototype.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/scriptaculous.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
<script type="text/javascript">
            var siteRecruitmentStatusDate = new CalendarPopup();
</script>
     
</head>
<SCRIPT LANGUAGE="JavaScript">

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
   
			<ul id="maintabs" class="tabs">
				<li><a href="#facility" class="active">Facility</a></li>
				<li><a href="#investigators">Investigators</a></li>
				<li><a href="#contacts">Contacts</a></li>
			</ul>

			<script type="text/javascript">             
                    //<![CDATA[
                    Event.observe(window,'load',function(){
                        $$('.tabs').each(function(tabs){
                            new Control.Tabs(tabs);
                        });
                    });
                    //]]>
                </script>


	<div id="tabboxwrapper">
		<!--/Facility-->
	 	<div id="facility" class="box">
				<h3>Facility</h3>

			<table class="form">
				<tr>
					<td scope="row" class="label"><label for="name">Organization
					Name:</label></td>
					<td class="value" style="width: 250px"><input type="text"
						name="name" maxlength="200" size="100" value=""
						style="width: 250px" /> <span class="info">Enter
					organization name and click <strong>Look Up</strong>.</span> <span
						class="formErrorMsg"></span></td>
					<td class="value">
					<ul style="margin-top: -6px;">
						<li style="padding-left: 0"><a href="#" class="btn"
							onclick="showPopWin('submodal/modal_search.html', 600, 400, null);" /><span
							class="btn_img"><span class="search">Look Up</span></span></a></li>
					</ul>
					</td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="ncinum">NCI
					number:</label></td>
					<td class="value" colspan="2"><input type="text" name="ncinum"
						id="ncinum" maxlength="200" size="10" value=""
						style="width: 100px" disabled="disabled" class="readonly" /></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="city">City:</label>
					</td>
					<td class="value" colspan="2"><input type="text" name="city"
						id="city" maxlength="200" size="200" value="" style="width: 200px"
						disabled="disabled" class="readonly" /> <span class="formErrorMsg"></span>
					</td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="state">State:</label>
					</td>
					<td class="value" colspan="2"><input type="text" name="state"
						name="state" maxlength="200" size="200" value=""
						style="width: 200px" disabled="disabled" class="readonly" /> <span
						class="formErrorMsg"></span></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="zip">Zip/Postal
					Code:</label></td>
					<td class="value" colspan="2"><input type="text" name="zip"
						id="zip" maxlength="200" size="200" value="" style="width: 200px"
						disabled="disabled" class="readonly" /> <span class="formErrorMsg"></span>
					</td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="country">Country:</label>
					</td>
					<td class="value" colspan="2"><input type="text"
						name="country" id="country" maxlength="200" size="200" value=""
						style="width: 200px" disabled="disabled" class="readonly" /> <span
						class="formErrorMsg"></span></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="leadorg">Lead
					Organization:</label></td>
					<td class="value"><input type="checkbox" name="group2"
						id="leadorg" value="Lead Org" onclick="SetGenericValues1()" />
					(check box if yes)</td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="srs">Site
					Recruitment Status:</label></td>
					<td class="value" colspan="2"><select name="status" id="srs"
						style="width: 200px">
						<option value="CTEP">Not yet recruiting</option>
						<option value="CCR">Recruiting</option>
						<option value="CCR">Enrolling by invitation</option>
						<option value="CCR">Active , not recruiting</option>
						<option value="CCR">Completed</option>
						<option value="CCR">Suspended : recruiting</option>
						<option value="CCR">Terminated : recruiting</option>
						<option value="CCR">Withdrawn</option>
					</select></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="srsd">Site
					Recruitment Status Date:</label></td>
					<td class="value" colspan="2"><input type="text"
						name="statusDate1" id="srsd" maxlength="10" size="10" value=" "
						style="width: 70px; float: left"> <a
						href="javascript:showCal('Calendar1')"><img
						src="images/ico_calendar.gif" alt="select date"
						class="calendaricon" /></a> (mm/dd/yyyy) <span class="formErrorMsg"></span>
					</td>
				</tr>
			</table>

			<div class="actionsrow"><del class="btnwrapper">
			<ul class="btnrow">
				<li><a href="#" class="btn" onclick="this.blur();"><span
					class="btn_img"><span class="save">Save</span></span></a></li>
			</ul>
			</del></div>


			</div><!--/Facility-->
			<!--Investigators-->
			<div id="investigators" class="box" style="display:none;">
				<h3>Facility Investigators </h3>
						<table class="data">
						</table>
			</div><!--/Investigators-->
			<!--Contacts-->
			<div id="contacts" class="box" style="display:none;">
				<h3>Contacts</h3>
					<table class="form">
					</table>
			</div><!--/Contacts-->
		</div>

<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <li><a href="trialFunding.action"                
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