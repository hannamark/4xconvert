<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:studyUniqueToken/>
<s:set name="trialHistoryWebDTO" value="trialHistoryWebDTO" scope="request" />
<display:table name="trialHistoryWebDTO" id="row" class="data" sort="list" defaultsort="1" defaultorder="ascending"
    pagesize="10" requestURI="trialHistory.action">
    <display:column escapeXml="true" property="submissionNumber" sortable="false"
        titleKey="trialHistory.submissionNumber" sortProperty="submissionNumberToSort" />
    <display:column escapeXml="true" property="type" sortable="false" titleKey="trialHistory.type" />
    <display:column escapeXml="true" property="amendmentNumber" sortable="false" titleKey="trialHistory.amendmentNumber" />
    <display:column escapeXml="true" property="amendmentDate" sortable="false" titleKey="trialHistory.amendmentDate" />
    <display:column escapeXml="true" property="submissionDate" sortable="false" titleKey="trialHistory.submissionDate" />
    <display:column escapeXml="true" property="amendmentReasonCode" sortable="false"
        titleKey="trialHistory.amendmentReasonCode" />
    <display:column escapeXml="false" property="documents" sortable="false" style="word-wrap: break-word"
        titleKey="trialHistory.documents" />
    <pa:displayWhenCheckedOut>
        <display:column title="Action" headerClass="centered" class="action">
            <s:if test="%{#attr.row.submissionNumber != 1}">
                <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.identifier})">
                    <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                </s:a>
            </s:if>
        </display:column>
    </pa:displayWhenCheckedOut>
</display:table>