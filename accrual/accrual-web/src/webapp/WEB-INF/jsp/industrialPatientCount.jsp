<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script language="javascript">
function handleUpdate(rowId) {
	document.countform.selectedRowIdentifier.value = rowId;
	document.countform.submittedCounts.value = document.getElementById("submittedCounts[" +  rowId+ "]").value;
	document.countform.action = "industrialPatientsupdate.action";
    document.countform.submit(); 
}
function handleDelete(rowId){
	document.countform.selectedRowIdentifier.value = rowId;
    var msg = 'Click OK to remove selected site(s) accrual counts. Cancel to abort.';
    var result = confirm(msg);
    if (result == true) {
        document.countform.action = "industrialPatientsdelete.action";
        document.countform.submit(); 
        return true;
    } else {
        document.countform.reset();
        return false;
    }
}
function handleSwitchUrl(spId) {
	document.forms[0].action = "patients.action?studyProtocolId=" + spId;
    document.forms[0].submit(); 	
} 
</script>
<c:set var="topic" scope="request" value="accrualcount" />
<div class="container">
	<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
	<h3 class="heading mt20">
		<span> <fmt:message key="participatingsite.accrual.count.title" />
		</span>
		<c:if
			test="${sessionScope.trialSummary.trialType.value == sessionScope.nonInterTrial && sessionScope.trialSummary.accrualSubmissionLevel.value == sessionScope.both}">
			<button type="button"
				class="btn btn-icon-alt btn-sm btn-light pull-right"
				onclick="handleSwitchUrl('<c:out value='${studyProtocolId}'/>')">
				Switch to Subject Level Accrual<i class="fa-angle-right"></i>
			</button>
		</c:if>
	</h3>
	<accrual:sucessMessage />
	<s:if test="hasActionErrors()">
		<div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong>
			<s:actionerror />.
		</div>
	</s:if>

	<s:form name="countform" cssClass="form-horizontal" role="form">
		<s:token />
		<s:hidden name="selectedRowIdentifier" />
		<s:hidden name="submittedCounts" />
		<display:table class="table table-striped" sort="list" pagesize="10"
			uid="row" name="studySiteCounts" export="false"
			decorator="gov.nih.nci.accrual.accweb.decorator.SubjectAccrualCountDecorator"
			requestURI="industrialPatients.action">
			<display:column titleKey="participatingsite.accrual.count.siteid"
				headerClass="sortable" headerScope="col" property="siteId" />
			<display:column titleKey="participatingsite.accrual.count.sitename"
				headerClass="sortable" headerScope="col" property="siteName" />
			<display:column
				titleKey="participatingsite.accrual.count.numOfSubjectEnrolled"
				headerClass="sortable" headerScope="col">
				<s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">
					<s:textfield name="submittedCounts[%{#attr.row.studySite.id}]"
					    id="submittedCounts[%{#attr.row.studySite.id}]"
						value="%{#attr.row.accrualCount}" cssClass="form-control"
						cssStyle="width:20%" />
				</s:if>
				<s:else>
					<s:property value="%{#attr.row.accrualCount}" />
				</s:else>
			</display:column>
			<display:column
				titleKey="participatingsite.accrual.count.dateLastUpdated"
				headerClass="sortable" property="dateLastUpdated" headerScope="col" />
			<display:column title="Actions" headerClass="align-center"
				class="actions">
				<s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">

					<s:a href="#" data-placement="top" rel="tooltip"
						data-original-title="Save"
						onclick="handleUpdate(%{#attr.row.studySite.id})">
						<i class="fa-floppy-o"></i>
					</s:a>
					<s:if test="%{#attr.row.dateLastUpdated != null}">
						<s:a href="#" data-placement="top" rel="tooltip"
							data-original-title="Delete"
							onclick="handleDelete(%{#attr.row.studySite.id})">
							<i class="fa-trash-o"></i>
						</s:a>
					</s:if>
				</s:if>
			</display:column>
		</display:table>
		<div class="align-center mb20">
			<button class="btn btn-icon btn-default" type="button"
				onclick="document.countform.reset();return false">
				<i class="fa-repeat"></i>Reset
			</button>
		</div>
	</s:form>
</div>
