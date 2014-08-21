<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
	function saveIntrv() {
		jQuery('#intervAltNames option').prop('selected', true);
		document.forms[0].action = "manageTermssaveIntervention.action";
		document.forms[0].submit();
	}

	function importIntrv() {
        jQuery('#intervAltNames option').prop('selected', true);
		document.forms[0].action = "manageTermssaveIntervention.action";
		document.forms[0].submit();
	}

	/**
	 * Udpates options in the result list
	 * @param from
	 * @param to
	 */
	function addSyn() {
		var value = jQuery("#altName").val()
		if (value != ''
				&& jQuery('#intervAltNames option[value="' + value + '"]').length == 0) {
			var option = new Option(value, value, 'selected');
			jQuery("#intervAltNames").append(option);
		}
		jQuery("#altName").val("");
	}

	function removeSyn(value) {
		if (jQuery("#importTerm").val() == 'false') {
			var values = jQuery("#intervAltNames").val();
			if(values != null) {
			 for (var i = 0; i < values.length; i++) {
				jQuery("#intervAltNames option[value='" + values[i] + "']")
						.remove();
			 }
			}
		}

	}
</script>
</head>
<div id="box">
	<pa:failureMessage />
	<s:form name="interventionForm" method="post">
		<s:hidden id="importTerm" name="importTerm" />
		<table class="form">
			<tr>
				<td scope="row" width="20%"><label for="ntTermIdentifier">
						NCIt Identifier</label><span class="required">*</span></td>
				<td width="150px"><s:textfield id="ntTermIdentifier"
						name="intervention.ntTermIdentifier" maxlength="10" size="10"
						cssStyle="width:150px" readonly="%{importTerm}" />
						<span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>intervention.ntTermIdentifier</s:param>
                    </s:fielderror>
                    </span>
                    </td>
			        
			</tr>
			<tr>
				<td scope="row" ><label for="pdqTermIdentifier">CDR
						Identifier</label></td>
				<td><s:textfield id="pdqTermIdentifier"
						name="intervention.identifier" maxlength="15" size="15"
						cssStyle="width:150px" />
						 <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>intervention.identifier</s:param>
                    </s:fielderror>
                    </span>
						</td>
			       
			</tr>
			<tr>
				<td scope="row" ><label for="name">Preferred
						Name</label><span class="required">*</span></td>
				<td><s:textfield id="name" name="intervention.name"
						maxlength="200" size="100" cssStyle="width:400px"
						readonly="%{importTerm}" />
						 <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>intervention.name</s:param>
                    </s:fielderror>
                    </span>
                    </td>
			</tr>
			<tr>
				<td scope="row" ><label for="typeCode">Synonyms
				</label></td>
				<td><s:textfield id="altName" name="altName" maxlength="200"
						size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
				<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
						onclick="addSyn()">
						<span class="btn_img"><span class="add">Add</span></span>
					</s:a></s:if></td>
			</tr>
			<tr>
				<td />
				<td><s:select id="intervAltNames" size="4"
						name="intervention.alterNames" cssStyle="width:400px"
						list="intervention.alterNames" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeSyn()">
						<span class="btn_img"><span class="delete">Remove</span></span>
					</s:a></s:if></td>
			</tr>
			<tr>
				<td scope="row" ><label for="typeCode">Cancer.gov Type</label></td>
				<td><s:set name="pdqTypeCodes"
						value="@gov.nih.nci.pa.enums.InterventionPDQTypeCode@getDisplayNames()" />
					<s:select id="typeCode" name="intervention.type" headerKey=""
						headerValue="-Select a Type Code-" list="#pdqTypeCodes" />
                </td>
			</tr>
			<tr>
				<td scope="row" ><label for="typeCode">ClinicalTrials.gov
						Type</label></td>
				<td>
				 <s:set name="typeCodes"
                        value="@gov.nih.nci.pa.enums.InterventionTypeCode@getDisplayNames()" />
                 <s:select id="typeCode" name="intervention.ctGovType"
						headerKey="" headerValue="-Select a Type Code-" list="#typeCodes" />
		         </td>
			</tr>
		</table>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:if test="%{!importTerm}">
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="saveIntrv()">
								<span class="btn_img"><span class="save">Save</span></span>
							</s:a>
						</s:if> <s:else>
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="importIntrv()">
								<span class="btn_img"><span class="copy">Import</span></span>
							</s:a>
						</s:else> <s:a href="manageTerms.action" cssClass="btn">
							<span class="btn_img"><span class="cancel">Cancel</span></span>
						</s:a></li>
				</ul>
			</del>
		</div>
	</s:form>
</div>
</html>