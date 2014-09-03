<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<c:url value="/protected/popupDisdisplayDiseaseWidget.action?lookUp=true"
	var="lookupUrl" />
<c:url value="/protected/manageTermsajaxGetDiseases.action?diseaseIds="
	var="diseaseAjaxURL" />
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

	function lookupParentDisease() {
		showPopWin('${lookupUrl}', 1100, 500, addToParentList,
				'Lookup Parent Diseases');
	}

	function lookupChildDisease() {
		showPopWin('${lookupUrl}', 1100, 500, addToChildList,
				'Lookup Child Diseases');
	}
	
	function lookupDisplayName() {
        showPopWin('${lookupUrl}', 1100, 500, setDisplayName,
                'Lookup Disease Display Name (Select only one term)');
    }

	function setDisplayName(ret){
        var retValue = ret.value;
        if(retValue != '') {
        	 // select only the first term if more than one term was selected
        	var retValue = retValue.split(',')[0];
        	jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
        		var name = value.split(':')[1];
                jQuery('#menuDisplayName').val(name);
            });
        } 
    }
	
	function addToParentList(ret) {
		var retValue = ret.value;
		if(retValue != '') {
			jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
				addDiseaseToList(value, 'parentTerms')
			});
		}
	}

	function addToChildList(ret) {
		var retValue = ret.value;
		if(retValue != '') {
			jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
				addDiseaseToList(value, 'childTerms')
			});
		}
	}

	function addDiseaseToList(values, listName) {
		values = values.split('\n');
		for (var i = 0; i < values.length; i++) {
			var value = values[i];
			if (value != ''
					&& jQuery('#' + listName + ' option[value="' + value + '"]').length == 0) {
				var option = new Option(value, value, 'selected');
				jQuery('#' + listName).append(option);
			}
		}
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
		var values = jQuery('#' + list).val();
		if (values != null) {
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
				<td scope="row" width="20%"><label for="ntTermIdentifier">
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
				<td scope="row" ><label for="code">CDR
						Identifier</label></td>
				<td><s:textfield id="code" name="disease.code" maxlength="15"
						size="15" cssStyle="width:150px" /> <span class="formErrorMsg">
						<s:fielderror>
							<s:param>disease.code</s:param>
						</s:fielderror>
				</span></td>
			</tr>
			<tr>
				<td scope="row" ><label for="preferredName">Preferred
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
				<td scope="row" ><label for="menuDisplayName">Display 
				Name</label><span class="required">*</span></td>
				<td><s:if test="%{importTerm && disease.menuDisplayName == null}">
				        <s:textfield id="menuDisplayName"
						name="disease.menuDisplayName" maxlength="200" size="100"
						cssStyle="width:400px" value="%{disease.preferredName}"/>
					</s:if>
					<s:else>
							<s:textfield id="menuDisplayName"
	                        name="disease.menuDisplayName" maxlength="200" size="100"
	                        cssStyle="width:400px"/>
					</s:else>
					 <span class="formErrorMsg"> <s:fielderror>
						<s:param>disease.menuDisplayName</s:param>
					</s:fielderror>
				</span></td>
				<td><div style="float: left; width: 100%;">
                        <s:a href="javascript:void(0)" cssClass="btn"
                            onClick="lookupDisplayName()">
                            <span class="btn_img"><span class="add">LookUp</span></span>
                        </s:a></div>
                </td>
			</tr>
			 <% List<String> newAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.alterNameList");
                List<String> currentAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.alterNameList"); 
            %>
			<tr>
				<td scope="row" ><label for="typeCode">Synonyms
				</label></td>
				<td><s:textfield id="altName" name="altName" maxlength="200"
						size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
				<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
						onclick="addToList('alterNames','altName')">
						<span class="btn_img"><span class="add">Add</span></span>
					</s:a></s:if></td>
			</tr>
			<tr>
				<td />
				<td><s:select id="alterNames" size="4"
						name="disease.alterNameList" cssStyle="width:400px"
						list="disease.alterNameList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeFromList('alterNames')">
						<span class="btn_img"><span class="delete">Remove</span></span>
					</s:a></s:if></td>
			</tr>
			<tr>
				<td scope="row" ><label for="typeCode">Parent
						Term NCIt Ids </label></td>
				<td><s:select id="parentTerms" size="6"
						name="disease.parentTermList" cssStyle="width:400px"
						list="disease.parentTermList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onClick="lookupParentDisease()">
							<span class="btn_img"><span class="add">LookUp &
									Add</span></span>
						</s:a>
					</div>
					<br/><br/>
				<div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="removeFromList('parentTerms')">
							<span class="btn_img"><span class="delete">Remove</span></span>
						</s:a>
					</div></td>
			</tr>
			<tr>
				<td scope="row" ><label for="typeCode">Child
						Term NCIt Ids </label></td>
				<td><s:select id="childTerms" size="6"
						name="disease.childTermList" cssStyle="width:400px"
						list="disease.childTermList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="lookupChildDisease()">
							<span class="btn_img"><span class="add">LookUp &
									Add</span></span>
						</s:a>
					</div>
					<br/><br/>
					<div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="removeFromList('childTerms')">
							<span class="btn_img"><span class="delete">Remove</span></span>
						</s:a>
					</div></td>
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