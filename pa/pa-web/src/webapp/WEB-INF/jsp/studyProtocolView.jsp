<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.view.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/tooltip.js'/>"></script>
    <script type="text/javascript">
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();
    }
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn");
    }
    function handleAction(){
    var studyProtocolId;
    studyProtocolId='${sessionScope.trialSummary.studyProtocolId }';
    input_box=confirm("Click OK to save changes or Cancel to Abort.");
    if (input_box==true){
             document.forms[0].action="studyProtocolcheckout.action?studyProtocolId="+studyProtocolId;
             document.forms[0].submit();
     }
}
    </SCRIPT>
</head>

<body>
 <c:set var="topic" scope="request" value="trialdetails"/>
 <h1>Trial Identification</h1>

   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>

  <div class="box">
  <pa:sucessMessage/>
    <s:form ><s:actionerror/>
    <h2>Trial Identification</h2>

        <table class="form">
            <tr>
            <td scope="row" class="label">
                <label for="nciAccessionNumber">
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="localProtocolIdentifier">
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                </label>
            </td>
            <td class="value">
             <c:out value="${sessionScope.trialSummary.localStudyProtocolIdentifier }"/>
             <span class="formErrorMsg"> <s:fielderror><s:param>localTrialIdentifier</s:param></s:fielderror></span>
            </td>
            </tr>
            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                <tr>
                    <td scope="row" class="label">
                        <label for="secondaryIdentifiers">Other Trial Identifiers</label>
                    </td>
                    <td class="value">
                        <c:forEach items="${sessionScope.trialSummary.otherIdentifiers}" var="extension" varStatus="status">
                            <c:out value="${extension}"/>
                            <c:if test="${not status.last}">,&nbsp;</c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td scope="row" class="label"> <label for="nciIdentifier"> NCT Number </label></td>
                <td class="value">
                <c:out value="${sessionScope.nctIdentifier }"/>
            </td>
            </tr>
            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
            <tr>
                <td scope="row" class="label"> <label for="ctepIdentifier"> CTEP Trial Identifier</label></td>
                <td class="value">
                <c:out value="${sessionScope.ctepIdentifier }"/>
            </td>
            </tr>
            <tr>
                <td scope="row" class="label"> <label for="dcpIdentifier"> DCP Trial Identifier</label></td>
                <td class="value">
                <c:out value="${sessionScope.dcpIdentifier }"/>
            </td>
            </tr>
            <tr>
                <td scope="row" class="label"> <label for="ctGovXml"> ClinicalTrials.gov XML required?</label></td>
                <td class="value">
                    <pa:displayBoolean value="${sessionScope.trialSummary.ctgovXmlRequiredIndicator}"/>
                </td>
            </tr>
            </c:if>
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg">
                    <fmt:message key="studyProtocol.proprietaryTrial"/>
                </label>
            </td>
            <td class="value">
                <pa:displayBoolean value="${sessionScope.trialSummary.proprietaryTrial}"/>
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="officialTitle">
                    <fmt:message key="studyProtocol.officialTitle"/>
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.officialTitle }"/>
            </td>
            </tr>
            </table>
        <c:if test="${(sessionScope.trialSummary.studyCheckoutBy == null)
                            || (sessionScope.trialSummary.studyCheckoutBy == sessionScope.loggedUserName)
                            || (sessionScope.role == 'SuAbstractor')}">
 <div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <pa:studyUniqueToken/>
            <c:choose>
                <c:when test="${(sessionScope.trialSummary.studyCheckoutBy == null)}">
                    <c:set var="saveButtonName" value="Check Out"/>
                    <s:hidden name="checkoutStatus" value="true" />
                </c:when>
                <c:otherwise>
                    <c:set var="saveButtonName" value="Check In"/>
                    <pa:studyUniqueToken/>
                </c:otherwise>
            </c:choose>
            <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">${saveButtonName}</span></span></s:a></li>
        </ul>
    </del>
</div>
</c:if>

    </s:form>
   </div>

 </body>
 </html>
