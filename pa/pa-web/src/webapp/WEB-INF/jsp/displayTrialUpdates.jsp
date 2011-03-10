<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm"><s:actionerror/>
<pa:studyUniqueToken/>
<s:set name="records" value="records" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="records" export="false" defaultsort="1" defaultorder="descending">
    <display:column escapeXml="false" titleKey="trialHistoryUpdate.updatedDate" property="updatedDate" format="{0,date,MM/dd/yyyy}" sortable="false" headerClass="sortable"/>
    <display:column escapeXml="true" titleKey="trialHistoryUpdate.updateReason" property="comment" headerClass="sortable" sortable="false" />
    <display:column class="title" titleKey="trialHistoryUpdate.accept" sortable="false" >
        <a href="#" class="title" onclick="javascript:acceptTrialUpdate('${row.id}');">Accept</a>
    </display:column>
</display:table>
</s:form>
