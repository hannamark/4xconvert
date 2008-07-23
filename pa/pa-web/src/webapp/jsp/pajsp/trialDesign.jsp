<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trial.trialDesign.title"/></title>
	<s:head />
</head>

<body onload="setFocusToFirstControl();">
<div id="contentwide">
 <h1><fmt:message key="trial.trialDesign.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
 	<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/>
 
<s:actionerror/>
     <tr>    	
		<td scope="row" class="label" nowrap>
			Protocol NCI Identifier is: ${trialDesign.acronym}
		</td>
	</tr>
	<h3><strong>
	          	Study Site ID:
	                    ${trialDesign.studySiteID}
        </strong></h3>
	
	<h3><strong>
	nciIdentifier is:
          ${trialDesign.acronym}
     </strong></h3>
     
     <h3><strong>
     	Official Title:
               ${trialDesign.officialTitle}
     </strong></h3>
    
     
   </div>
 </div>
 </body>
 </html>
 
