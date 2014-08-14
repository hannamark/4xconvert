<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<c:url value="/protected/popupDisdisplayDiseaseWidget.action" var="lookupUrl" />
<script type="text/javascript">
	function saveDisease() {
		selectAllListItems();
		document.forms[0].action = "manageTermssaveDisease.action";
		document.forms[0].submit();
	}

	function importDisease() {
		selectAllListItems();
		document.forms[0].action = "manageTermssaveDisease.action";
		document.forms[0].submit();
	}
	
	 function lookupDisease() {
         showPopWin('${lookupUrl}', 1100,500,refresh,'Diseases');
     }

	 function refresh() {
         alert("refresh")
     }
	function selectAllListItems() {
		jQuery('#alterNames option').prop('selected', true);
		jQuery('#parentTerms option').prop('selected', true);
		jQuery('#childTerms option').prop('selected', true);
	}
	/**
	 * Udpates options in the result list
	 * @param from
	 * @param to
	 */
	function addToList(list, source) {
		var value = jQuery('#' + source).val()
		if (value != ''
				&& jQuery('#' + list + ' option[value="' + value + '"]').length == 0) {
			var option = new Option(value, value, 'selected');
			jQuery('#' + list).append(option);
		}
		jQuery('#' + source).val("");
	}

	function removeFromList(list) {
		if (jQuery("#importTerm").val() == 'false') {
			var values = jQuery('#' + list).val();
			for (var i = 0; i < values.length; i++) {
				jQuery('#' + list + ' option[value="' + values[i] + '"]')
						.remove();
			}
		}
	}
</script>
</head>
<div id="box">
	<pa:failureMessage />
	<s:form name="diseaseForm" method="post">
		<s:hidden id="importTerm" name="importTerm" />
		<table class="form">
			<tr>
				<td scope="row" class="label"><label for="ntTermIdentifier">
						NCIt Identifier</label><span class="required">*</span></td>
				<td width="150px"><s:textfield id="ntTermIdentifier"
						name="disease.ntTermIdentifier" maxlength="10" size="10"
						cssStyle="width:150px" readonly="%{importTerm}" /> <span
					class="formErrorMsg"> <s:fielderror>
							<s:param>disease.ntTermIdentifier</s:param>
						</s:fielderror>
				</span></td>

			</tr>
			<tr>
				<td scope="row" class="label"><label for="code">CDR
						Identifier</label><span class="required">*</span></td>
				<td><s:textfield id="code" name="disease.code" maxlength="15"
						size="15" cssStyle="width:150px" /> <span class="formErrorMsg">
						<s:fielderror>
							<s:param>disease.code</s:param>
						</s:fielderror>
				</span></td>

			</tr>
			<tr>
				<td scope="row" class="label"><label for="preferredName">Preferred
						Name</label><span class="required">*</span></td>
				<td><s:textfield id="preferredName"
						name="disease.preferredName" maxlength="200" size="100"
						cssStyle="width:400px" readonly="%{importTerm}" /> <span
					class="formErrorMsg"> <s:fielderror>
							<s:param>disease.preferredName</s:param>
						</s:fielderror>
				</span></td>
			</tr>
			<tr>
				<td scope="row" class="label"><label for="menuDisplayName">Menu
						Display Name</label><span class="required">*</span></td>
				<td><s:textfield id="menuDisplayName"
						name="disease.menuDisplayName" maxlength="200" size="100"
						cssStyle="width:400px" /> <span class="formErrorMsg"> <s:fielderror>
							<s:param>disease.menuDisplayName</s:param>
						</s:fielderror>
				</span></td>
			</tr>
			<tr>
				<td scope="row" class="label"><label for="typeCode">Synonyms
				</label></td>
				<td><s:textfield id="altName" name="altName" maxlength="200"
						size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
						onclick="addToList('alterNames','altName')">
						<span class="btn_img"><span class="add">Add</span></span>
					</s:a></td>
			</tr>
			<tr>
				<td />
				<td><s:select id="alterNames" size="4"
						name="disease.alterNameList" cssStyle="width:400px"
						list="disease.alterNameList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeFromList('alterNames')">
						<span class="btn_img"><span class="delete">Remove</span></span>
					</s:a></td>
			</tr>
			<tr>
				<td scope="row" class="label"><label for="typeCode">Parent
						Term NCIt Ids </label></td>
				<td><s:textfield id="parentId" name="parentId" maxlength="50"
						size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
				onClick="addToList('parentTerms','parentId')">
						<span class="btn_img"><span class="add">Add</span></span>
					</s:a></td>
			</tr>
			<tr>
				<td />
				<td><s:select id="parentTerms" size="4"
						name="disease.parentTermList" cssStyle="width:400px"
						list="disease.parentTermList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeFromList('parentTerms')">
						<span class="btn_img"><span class="delete">Remove</span></span>
					</s:a></td>
			</tr>
			<tr>
				<td scope="row" class="label"><label for="typeCode">Child
						Term NCIt Ids </label></td>
				<td><s:textfield id="childId" name="childId" maxlength="50"
						size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
						onclick="addToList('chlildTerms','childId')">
						<span class="btn_img"><span class="add">Add</span></span>
					</s:a></td>
			</tr>
			<tr>
				<td />
				<td><s:select id="chlildTerms" size="4"
						name="disease.childTermList" cssStyle="width:400px"
						list="disease.childTermList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeFromList('chlildTerms')">
						<span class="btn_img"><span class="delete">Remove</span></span>
					</s:a></td>
			</tr>
		</table>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:if test="%{!importTerm}">
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="saveDisease()">
								<span class="btn_img"><span class="save">Save</span></span>
							</s:a>
						</s:if> <s:else>
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="importDisease()">
								<span class="btn_img"><span class="copy">Import</span></span>
							</s:a>
						</s:else> <s:a href="manageTerms.action" cssClass="btn">
							<span class="btn_img"><span class="cancel">Cancel</span></span>
						</s:a></li>
			</del>
		</div>
	</s:form>
</div>
</html>