<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>	
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set name="menuPage" value="%{'other'}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="protocol.search.title"/></title>
</head>
<body>

  <script type="text/javascript" src="/scripts/js/calendarpopup.js"></script>


  <script type="text/javascript">
      var cal = new CalendarPopup();
  </script>
  <div id="contentwide">
	<h1><fmt:message key="trail.detail.title"/></h1>

    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>  
		<jsp:include page="/WEB-INF/jsp/common/trailDetailSummary.jsp"/>  


			<div class="dotted_line"></div>	
	   		<strong><fmt:message key="trail.detail.title"/></strong><br>
		     
		   		  
		   		  
		   		<s:textfield key="trail.detail.lead.org.id" name="leadTrailIdentifier" />
				<s:textfield key="trail.detail.brief.title" name="breifTitle" /><br>
				<s:textfield key="trail.detail.acronym" name="acronym" />
				<s:textfield key="trail.detail.keywords" name="keywords" /><br>
				<s:textfield key="trail.detail.brief.summary" name="briefSummary" />
				<s:textfield key="trail.detail.detailed.description" name="detailedDesc" /><br>			
							
				<s:textfield key="trail.detail.anatomic.site" name="anatomicSite" />
			
			
				 <s:set name="RepDataSet" value="@gov.nih.nci.pa.enums.ReportDataSetMethod@values()" />
                 <s:select key="trail.detail.reporting.data.set.method" name="reportDataSet" multiple="false"
                        list="#RepDataSet" listKey="name()" listValue="name()"
                        value="repDataSet" /><br>
				
				
				<s:set name="PartType" value="@gov.nih.nci.pa.enums.ParticipationType@values()" />
                <s:select key="trail.detail.participationType" name="PartType" multiple="false"
                        list="#PartType" listKey="name()" listValue="name()"
                        value="PartType" />
				
				<s:set name="INDTrail" value="@gov.nih.nci.pa.enums.IndIdeTrailInd@values()" />
                <s:select key="trail.detail.participationType" name="INDTrail" multiple="false"
                        list="#INDTrail" listKey="name()" listValue="name()"
                        value="INDTrail" /><br>
                        
                <s:set name="IDETrail" value="@gov.nih.nci.pa.enums.IndIdeTrailInd@values()" />
                <s:select key="trail.detail.ideTrail" name="IDETrail" multiple="false"
                        list="#IDETrail" listKey="name()" listValue="name()"
                        value="INETrail" />
				
				
	            
	            <s:set name="accrualStatus" value="@gov.nih.nci.pa.enums.AccrualStatus@values()" />
	            <s:select key="trail.detail.accrualStatus" name="accrualStatus" multiple="false"
	               list="#accrualStatus" listKey="name()" listValue="name()"
	               value="accrualStatus" /><br>
	               
	            <s:textfield key="trail.detail.accrualStatusDate" name="accrualStatusDate" />
	            <a href="javascript:;" onclick="cal.select(document.forms[0].accrualStatusDate,'calendarbutton','MM/dd/yyyy'); return false;" name="calendarbutton" id="calendarbutton"><img src="images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
	            	  
	            
	            <s:set name="ExpandExcessInd" value="@gov.nih.nci.pa.enums.Indicator@values()" />
	            <s:select key="trail.detail.expanded.excess.ind" name="ExpandExcessInd" multiple="false"
	               list="#ExpandExcessInd" listKey="name()" listValue="name()"
	               value="ExpandExcessInd" /><br>
	            
	            <s:set name="ExpandExcessStatus" value="@gov.nih.nci.pa.enums.ExpandExcessStatus@values()" />
	            <s:select key="trail.detail.expanded.excess.status" name="ExpandExcessStatus" multiple="false"
	               list="#ExpandExcessStatus" listKey="name()" listValue="name()"
	               value="ExpandExcessStatus" />  <br>
	               
	             <s:submit value="Save" align="center"/>


  </div>
<!--/Content-->

</body>
</html>
