<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm"><s:actionerror/>
<pa:studyUniqueToken/>
<s:set name="records" value="records" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="records" export="false" defaultsort="1" defaultorder="descending" requestURI="trialHistory.action#updates">
    <display:column escapeXml="false" titleKey="trialHistoryUpdate.updatedDate" property="updatedDate" format="{0,date,MM/dd/yyyy}" sortable="false" headerClass="sortable"/>
    <display:column titleKey="trialHistoryUpdate.updateReason" headerClass="sortable" sortable="false">
        <s:property escapeHtml="false" escapeXml="false" 
            value="@org.apache.commons.lang.StringUtils@replace(#attr.row.comment, @gov.nih.nci.pa.util.TrialUpdatesRecorder@SEPARATOR,'<br/>')"/>
    </display:column>
    <display:column class="title" titleKey="trialHistoryUpdate.accept" sortable="false" >
        <a href="javascript:void(0)" class="title" onclick="javascript:acceptTrialUpdate('${row.id}');">Acknowledge</a>
    </display:column>
</display:table>
</s:form>
