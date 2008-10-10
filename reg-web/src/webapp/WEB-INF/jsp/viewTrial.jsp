<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="view.trial.page.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript"> 
    function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
	</SCRIPT>
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="view.trial.page.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->

  <div class="box">
    <s:form ><s:actionerror/>
	<h2>Trial Details</h2>

        <table class="form">
         
            <tr>
            <td scope="row" class="label">
                <label for="nciAccessionNumber">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="view.trial.identifier"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.trialPhase }"/> 
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="nct">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">NCT Number:
                </dfn></label>
            </td>
            <td class="value"></td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="localProtocolIdentifer"> 
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                </dfn>
                </label>
            </td>
            <td class="value"></td>
            </tr>  
            <tr>
                <td scope="row" class="label"><label for="sectrialid"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">Secondary Trial Identifier:</dfn></label></td>
                <td class="value"></td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                    <fmt:message key="view.trial.leadOrganization"/>
                </dfn>
                </label>
            </td>
            <td class="value">
            </td>
            </tr> 
            <tr>     
            <td scope="row" class="label">
                <label for="officialTitle">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="view.trial.title"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.trialTitle }"/> 
            </td>
            </tr>       
             <tr>
                <td scope="row" class="label"><label for="acronym"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">Acronym:</dfn></label></td>
                <td class="value"></td>
            </tr>


        </table>  
                  
    </s:form>
   </div>

 </body>
 </html>