<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.view.title"/></title>
    <s:head />
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="studyProtocol.view.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/jsp/pajsp/protocolDetailSummary.jsp"/>

  <div class="box">
    <s:form ><s:actionerror/>
	<h2>Trial Identification</h2>

        <table class="form">
         
            <tr>
            <td scope="row" class="label">
                <label for="nciAccessionNumber">
                <dfn title="Context sensitive help text or tooltip here."> 
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.nciIdentifier }"/> 
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="nct">
                <dfn title="Context sensitive help text or tooltip here.">NCT Number:
                </dfn></label>
            </td>
            <td class="value"></td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="localProtocolIdentifer"> 
                <dfn title="Context sensitive help text or tooltip here.">
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                </dfn>
                </label>
            </td>
            <td class="value"></td>
            </tr>       
            <tr>
            <tr>
                <td scope="row" class="label"><label for="sectrialid"><dfn title="Context sensitive help text or tooltip here.">Secondary Trial Identifier:</dfn></label></td>
                <td class="value"></td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                <dfn title="Context sensitive help text or tooltip here.">
                    <fmt:message key="studyProtocol.leadOrganization"/>
                </dfn>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.leadOrganizationName }"/> 
            </td>
            </tr>      
            <td scope="row" class="label">
                <label for="officialTitle">
                <dfn title="Context sensitive help text or tooltip here."> 
                    <fmt:message key="studyProtocol.officialTitle"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.officialTitle }"/> 
            </td>
            </tr>       
             <tr>
                <td scope="row" class="label"><label for="acronym"><dfn title="Context sensitive help text or tooltip here.">Acronym:</dfn></label></td>
                <td class="value"></td>
            </tr>


        </table>  
<!--        
 <div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><a href="studyProtocolEdit.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="edit">Edit</span></span></a></li>
		</ul>	
	</del>
</div>
-->
                  
    </s:form>
   </div>

 </body>
 </html>