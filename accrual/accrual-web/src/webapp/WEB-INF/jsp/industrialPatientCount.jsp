<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<script language="javascript">
function setCheckbox(index) {
    if (document.countform.sitesToSave[index] == undefined) {
        document.countform.sitesToSave.checked=true;
    } else {
        document.countform.sitesToSave[index].checked=true;
    }
}
function handleDelete(){
	document.forms[0].action = "industrialPatientsdelete.action";
    document.forms[0].submit();	
}
</script>
<c:set var="topic" scope="request" value="accrualcount"/>

<c:if test="${sessionScope.trialSummary.trialType.value == sessionScope.nonInterTrial && sessionScope.trialSummary.accrualSubmissionLevel.value == sessionScope.both}">
    <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{studyProtocolId}" /></s:url>
    <s:a cssClass="btn" href="%{url}"><span class="btn_img"><span class="save">Switch to Subject Level Accrual</span></span></s:a><br/><br/>
</c:if>

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
    <s:token/>
    <display:table class="data" sort="list" pagesize="10" uid="row" name="studySiteCounts" export="false"
        decorator="gov.nih.nci.accrual.accweb.decorator.SubjectAccrualCountDecorator" requestURI="industrialPatients.action">
        <display:column titleKey="participatingsite.accrual.count.checkbox" headerClass="sortable" headerScope="col">
        <s:if test="%{#attr.row.studySite.id in sitesToSave}">
	       <s:checkbox name="sitesToSave" fieldValue="%{#attr.row.studySite.id}" value="true" />
	    </s:if>
	    <s:else>
	       <s:checkbox name="sitesToSave" fieldValue="%{#attr.row.studySite.id}" value="false"/>
	    </s:else>       
        </display:column> 
        <display:column titleKey="participatingsite.accrual.count.siteid" headerClass="sortable" headerScope="col" property="siteId"/>
        <display:column titleKey="participatingsite.accrual.count.sitename" headerClass="sortable" headerScope="col" property="siteName"/>
        <display:column titleKey="participatingsite.accrual.count.numOfSubjectEnrolled" headerClass="sortable" headerScope="col" >
            <s:hidden name="submittedSiteIds" value="%{#attr.row.studySite.id}" />
            <s:textfield name="submittedCounts" value="%{#attr.row.accrualCount}" size="9" maxlength="9" onfocus="setCheckbox(%{#attr.row_rowNum-1});"/>
        </display:column>
        <display:column titleKey="participatingsite.accrual.count.dateLastUpdated" headerClass="sortable"
            property="dateLastUpdated" headerScope="col" />
        <display:column titleKey="participatingsite.accrual.count.delete.checkbox" headerClass="sortable" headerScope="col">        
           <s:checkbox name="sitesToDelete" fieldValue="%{#attr.row.studySite.id}" value="%{#attr.row.studySite.id in sitesToDelete}" />
        </display:column> 
    </display:table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="#" cssClass="btn" onclick="document.countform.submit()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                    <s:a href="#" cssClass="btn" onclick="document.countform.reset();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                    <s:a href="#" cssClass="btn" onclick="handleDelete()"><span class="btn_img"><span class="delete">Delete</span></span></s:a>
                </li>
            </ul>
        </del>
    </div>
</s:form>
