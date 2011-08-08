<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />

<h1>
    <fmt:message key="participatingsite.accrual.count.title" />
</h1>
<accrual:sucessMessage />
<s:if test="hasActionErrors()">
    <div class="error_msg">
        <s:actionerror />
    </div>
</s:if>

<s:form name="countform" action="industrialPatientsupdate">
    <s:hidden name="studyProtocolId" />
    <display:table class="data" sort="list" pagesize="10" uid="row" name="studySiteCounts" export="false"
        decorator="gov.nih.nci.accrual.accweb.decorator.SubjectAccrualCountDecorator">
        <display:column titleKey="participatingsite.accrual.count.checkbox" headerClass="sortable" headerScope="col">
            <s:checkbox name="sitesToSave" fieldValue="%{#attr.row.site.id}" />
        </display:column> 
        <display:column titleKey="participatingsite.accrual.count.siteid" headerClass="sortable" headerScope="col" property="siteId"/>
        <display:column titleKey="participatingsite.accrual.count.sitename" headerClass="sortable" headerScope="col" property="siteName"/>
        <display:column titleKey="participatingsite.accrual.count.numOfSubjectEnrolled" headerClass="sortable" headerScope="col" >
            <s:hidden name="submittedSiteIds" value="%{#attr.row.site.id}" />
            <s:textfield name="submittedCounts" value="%{#attr.row.accrualCount}" size="9" maxlength="9" onfocus="document.countform.sitesToSave[%{#attr.row_rowNum-1}].checked=true;"/>
        </display:column>
        <display:column titleKey="participatingsite.accrual.count.dateLastUpdated" headerClass="sortable"
            property="dateLastUpdated" headerScope="col" />
    </display:table>
    <s:a href="#" cssClass="btn" onclick="document.countform.submit()"><span class="btn_img"><span class="save">Save</span></span></s:a>
    <s:a href="#" cssClass="btn" onclick="document.countform.reset();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
</s:form>
