<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="studyProtocol.view.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/tooltip.js'/>"></script>
        <script type="text/javascript" language="javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
            }
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            function handleAction(action) {
                var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
                var form = document.forms[0];
                if ((action == 'adminCheckIn') || (action == 'scientificCheckIn') || (action == 'adminAndScientificCheckIn')){
                    var bs=new Array(64).join(' ');
                    var comment=prompt(bs+"Enter check-in comment:"+bs,"");
                    if (comment==null){
                        return;
                    }
                    form.elements["checkInReason"].value = comment.substr(0,200);
                }
                form.action="studyProtocol" + action + ".action?studyProtocolId=" + studyProtocolId;
                form.submit();
            }
        </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="trialdetails"/>
        <h1>Trial Identification</h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <s:form>
                <s:token/>
                <s:hidden name="checkInReason"/>
                <s:actionerror/>
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
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>localTrialIdentifier</s:param>
                                </s:fielderror>
                            </span>
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
                        <td scope="row" class="label">
                            <label for="nciIdentifier"> NCT Number </label>
                        </td>
                        <td class="value">
                            <c:out value="${sessionScope.nctIdentifier }"/>
                        </td>
                    </tr>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <td scope="row" class="label">
                                <label for="ctepIdentifier"> CTEP Trial Identifier</label>
                            </td>
                            <td class="value">
                                <c:out value="${sessionScope.ctepIdentifier }"/>
                            </td>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <label for="dcpIdentifier"> DCP Trial Identifier</label>
                            </td>
                            <td class="value">
                                <c:out value="${sessionScope.dcpIdentifier }"/>
                            </td>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <label for="ctGovXml"> ClinicalTrials.gov XML required?</label>
                            </td>
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
                <s:if test="%{!checkoutCommands.isEmpty()}">
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <pa:studyUniqueToken/>
                                <s:set name="checkoutCommands" scope="page" value="%{checkoutCommands}" /> 
                                <c:forEach items="${checkoutCommands}" var="command" varStatus="vstat">
                                    <c:if test="${vstat.index==2}">
                                        <!-- Force the 3rd button onto a separate row: https://tracker.nci.nih.gov/browse/PO-3966 -->
                                        </ul></del><br/><del class="btnwrapper"><ul class="btnrow">                                        
                                    </c:if>
                                    <li>
                                        <a href="javascript:void(0)" class="btn" onclick="handleAction('${command}')">
                                            <span class="btn_img"><span class="save"><fmt:message key="studyProtocolView.button.${command}" /></span></span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </del>
                    </div>
                </s:if>
                <c:if test="${pageContext.request.method=='GET' && fn:contains(header['Referer'],'/studyProtocolquery.action')}">
	                <div class="actionsrow" style="border: none;">
	                  <del class="btnwrapper">
	                      <ul class="btnrow">
	                          <li><a href="javascript:void(0)" class="btn" onclick="window.history.back();"><span class="btn_img"><span class="back">Back to Search Results</span></span></a></li>
	                      </ul>
	                  </del>
	                 </div>     
                 </c:if>           
            </s:form>
        </div>
    </body>
</html>
