<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title><fmt:message key="diseaseCondition.title"/></title>
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <s:head/>
</head>
<body>
<!-- main content begins-->
   <div id="contentwide">
    <h1><fmt:message key="diseaseCondition.title"/></h1>
    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a> 
    <s:form action="queryProtocol">
    <s:actionerror/>
        <table cellspacing="2" >    
            <tr><td align=right>NCI accession number:</td><td align=left>NCI-2008-0002</td></tr>
            <tr><td align=right>Trial Title:</td><td align=left>A Phase I Trial of Taxol in refractory leukemia in children</td></tr>
            <tr><td align=right>Abstraction Status:</td><td align=left>Accepted</td></tr>
            <tr><td align=right>Trial Submitter:</td><td align=left>Joe Smith</td></tr>
            <tr><td align=right>Principal Investigator:</td><td align=left>Mike Sholch</td></tr>
        </table>        
   </s:form>
   <br>
   <c:if test="${records != null}">
	   <display:table class="its" sort="list" pagesize="10" id="row" name="${records}" requestURI="/queryProtocol.action" export="false">
		   <display:column style="width: 1%"><input type="checkbox" name="select" /></display:column>
		   <display:column>Edit</display:column>
		   <display:column  titleKey="diseaseCondition.code" property="diseaseName" sortable="true" headerClass="sortable"/>
		   <display:column  titleKey="diseaseCondition.parentCode" property="parentCode" sortable="true" headerClass="sortable"/>
		   <display:column  titleKey="diseaseCondition.name" property="diseaseName" sortable="true" headerClass="sortable"/>
		   <display:column  titleKey="diseaseCondition.lead" property="leadIndicator" sortable="true" headerClass="sortable"/>
	   </display:table>
   </c:if>
   <button onclick="showPopWin('<c:url value='jsp/pajsp/nodecorate/addDiseaseCondition.jsp'/>', 600, 250, null, 'Select Disease Condition');">Add</button>   
   <input type="button" onclick="location.href='users-edit.jsp'" class="button" value="Delete" />
 </div>
</body>
</html>