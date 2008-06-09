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


	<h1><fmt:message key="trail.detail.title"/></h1>

    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>  
		<jsp:include page="/WEB-INF/jsp/common/trailDetailSummary.jsp"/>  

			<s:form action="trailDetailAction">				
	   		<strong><fmt:message key="trail.detail.title"/></strong>
		     
		   		<s:textfield key="trail.detail.lead.org.id" name="leadTrailIdentifier" />
				<s:textfield key="trail.detail.brief.title" name="breifTitle" />
				<s:textfield key="trail.detail.acronym" name="acronym" />
				<s:textfield key="trail.detail.keywords" name="keywords" />
				<s:textfield key="trail.detail.brief.summary" name="briefSummary" />
				<s:textfield key="trail.detail.detailed.description" name="detailedDesc" />		
							
				<s:textfield key="trail.detail.anatomic.site" name="anatomicSite" />
			
			
				 <s:set name="RepDataSet" value="@gov.nih.nci.pa.enums.ReportDataSetMethodCode@values()" />
                 <s:select key="trail.detail.reporting.data.set.method" name="reportDataSet" multiple="false"
                        list="#RepDataSet" listKey="name()" listValue="name()"
                        value="repDataSet" />
				
				
				<s:set name="ParticipationType" value="@gov.nih.nci.pa.enums.ParticipationTypeCode@values()" />
                <s:select key="trail.detail.participationType" name="ParticipationType" multiple="false"
                        list="#ParticipationType" listKey="name()" listValue="name()"
                        value="ParticipationType" />
				
				<s:set name="INDTrailIndicator" value="@gov.nih.nci.pa.enums.INDTrailIndicatorCode@values()" />
                <s:select key="trail.detail.indTrail" name="INDTrailIndicator" multiple="false"
                        list="#INDTrailIndicator" listKey="name()" listValue="name()"
                        value="INDTrailIndicator" />
                        
                <s:set name="IDETrailIndicator" value="@gov.nih.nci.pa.enums.IDETrailIndicatorCode@values()" />
                <s:select key="trail.detail.ideTrail" name="IDETrailIndicator" multiple="false"
                        list="#IDETrailIndicator" listKey="name()" listValue="name()"
                        value="IDETrailIndicator" />
				
				
	            
	            <s:set name="accrualStatus" value="@gov.nih.nci.pa.enums.AccrualStatus@values()" />
	            <s:select key="trail.detail.accrualStatus" name="accrualStatus" multiple="false"
	               list="#accrualStatus" listKey="name()" listValue="name()"
	               value="accrualStatus" />
	               
	            <s:textfield key="trail.detail.accrualStatusDate" name="accrualStatusDate" />
	            
	            <s:set name="ExpandedAccessIndicator" value="@gov.nih.nci.pa.enums.ExpandedAccessIndicatorCode@values()" />
	            <s:select key="trail.detail.expanded.access.ind" name="ExpandedAccessIndicator" multiple="false"
	               list="#ExpandedAccessIndicator" listKey="name()" listValue="name()"
	               value="ExpandedAccessIndicator" />
	            
	            <s:set name="ExpandedAccessStatus" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@values()" />
	            <s:select key="trail.detail.expanded.access.status" name="ExpandedAccessStatus" multiple="false"
	               list="#ExpandedAccessStatus" listKey="name()" listValue="name()"
	               value="ExpandedAccessStatus" />  
	               
	             <s:submit value="Save" align="center"/>
		 </s:form>

<!--/Content-->

</body>
</html>
