<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2">Regulatory Information</th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<!--  Trial Oversight Authority Country -->
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.lst"/>
        <s:property value="trialDTO.regAuthorityCountry.name"/>
     </td>
</tr>
<tr>
    <td scope="row" class="label-noinput">
        <fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.selectedRegAuth"/>
        <s:property value="trialDTO.regAuthorityOrg.name"/>
    </td>
</tr>
<!--   FDA Regulated Intervention Indicator-->
<tr>
    <td scope="row"  class="label-noinput">
        <fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.fdaRegulatoryInformationIndicator"/>
        <s:property value="trialDTO.fdaRegulatoryInformationIndicator"/>
    </td>
 </tr>
<!--   Section 801 Indicator-->
<tr id="sec801row">
    <td scope="row" class="label-noinput">
        <fmt:message key="regulatory.section801.ind"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.section801Indicator"/>
        <s:property value="trialDTO.section801Indicator"/>
    </td>
</tr>

<!--   Delayed Posting Indicator-->
<tr id="delpostindrow">
    <td scope="row" class="label-noinput">
        <fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.delayedPostingIndicator"/>
        <s:property value="trialDTO.delayedPostingIndicator"/>
    </td>
</tr>
<!--   Data Monitoring Committee Appointed Indicator -->
<tr id="datamonrow">
    <td scope="row" class="label-noinput">
        <fmt:message key="regulatory.data.monitoring.committee.ind"/>
    </td>
    <td class="value">
        <s:hidden name="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
        <s:property value="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
    </td>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>