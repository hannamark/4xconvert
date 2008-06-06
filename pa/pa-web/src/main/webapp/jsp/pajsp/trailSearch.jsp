<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>	
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set name="menuPage" value="%{'QueryProtocol'}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="protocol.search.title"/></title>
	<s:head />
</head>
<body>
	<!-- main content begins-->

<!--Content-->

<div id="contentwide">

    <!--Search Box-->
   <div id="searchbox">
   
	<h1><fmt:message key="protocol.search.header"/></h1>

    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>


		<s:form action="protocolSearchCriteria">
           	<s:textfield key="protocol.search.label.nciNumber" name="nciNumber" /><br>
			<s:textfield key="protocol.search.label.officialTitle" name="officialTitle" /><br>                    	                                                             
            
            <s:textfield key="protocol.search.label.leadOrganization" name="leadOrganization" /><br>
            <s:textfield key="protocol.search.label.leadOrganizationProtocolID" name="leadOrganizationProtocolID" /><br>
           <s:set name="sPhase" value="@gov.nih.nci.pa.enums.StudyPhaseCode@values()" />
         	<s:select key="protocol.search.label.studyPhase" name="reportDataSet" multiple="false"
                list="#sPhase" listKey="name()" listValue="name()"
                value="studyPhase" />                                        

					
			 <s:set name="sStatus" value="@gov.nih.nci.pa.enums.StudyStatusCode@values()" />
	         <s:select key="protocol.search.label.studyStatus" name="reportDataSet" multiple="false"
	              list="#sStatus" listKey="name()" listValue="name()"
	               value="studyStatus" />
                      
             <s:set name="sStatus" value="@gov.nih.nci.pa.enums.StudyStatusCode@values()" />
	         <s:select key="protocol.search.label.processingStatus" name="reportDataSet" multiple="false"
	              list="#sStatus" listKey="name()" listValue="name()"
	               value="studyStatus" />   
	         
	         <s:set name="abStatus" value="@gov.nih.nci.pa.enums.AbstractionStatus@values()" />
	         <s:select key="protocol.search.label.abstractionStatus" name="reportDataSet" multiple="false"
	              list="#abStatus" listKey="name()" listValue="name()"
	               value="abstractionStatus" />       
	                   
            <s:submit value="Search" align="center"/>  
            <s:submit value="Reset" align="center"/>                                        
       </s:form>
    </div>
    <!--/Search Box-->
</div>
<!--/Content-->

</body>
</html>
