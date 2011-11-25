<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="%{resultList != null}">
    <table id="resultTable" width="100%">
        <tr>
            <td colspan="2">
                <h2><fmt:message key="summary4Report.title"/></h2>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="summary4Report.header.date1"/>
                <s:label name="criteria.intervalStartDate"/>
                <fmt:message key="summary4Report.header.date2"/>
                <s:label name="criteria.intervalEndDate"/>
            </td>
            <td>
                <fmt:message key="report.header.user"/>
                <viewer:displayUser />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div id="resultui">
                    <ul id="resulttabs" class="clearfix">
                        <li><a href="#agent_device"><fmt:message key="summary4Report.resultTab.agent" /></a></li>
                        <li><a href="#other_intervention"><fmt:message key="summary4Report.resultTab.intervention" /></a></li>
                        <li><a href="#epid_outcome"><fmt:message key="summary4Report.resultTab.outcome" /></a></li>
                        <li><a href="#ancillary_correlative"><fmt:message key="summary4Report.resultTab.ancillary" /></a></li>
                    </ul>
                    <div id="agent_device">
                        <div id="loadAgentDeviceResults">
                            <%@ include file="/WEB-INF/jsp/nodecorate/summary4Report/agencyDeviceResult.jsp" %>
                        </div>
                    </div>
                    <div id="other_intervention" >
                        <div id="loadOtherInterventionResults">
                            <%@ include file="/WEB-INF/jsp/nodecorate/summary4Report/otherInterventionResult.jsp" %>      
                        </div>
                    </div>
                    <div id="epid_outcome">                        
                        <div id="loadEpidOutcomeResults">
                          <%@ include file="/WEB-INF/jsp/nodecorate/summary4Report/epidemOutcomeResult.jsp" %>
                        </div>
                    </div>
                    <div id="ancillary_correlative" >                        
                        <div id="loadAncillaryCorrelativeResults">
                            <%@ include file="/WEB-INF/jsp/nodecorate/summary4Report/ancillaryCorrelativeResult.jsp" %>                        
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</s:if>