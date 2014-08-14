<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manageterms.page.title" /></title>
<s:head />
<script type="text/javascript" language="javascript">
	function submitForm(btnSelected) {
		var form = document.forms[0];
		if (btnSelected != 'accept') {
			form.action = "logout.action";
		}
		form.submit();
	}
</script>
</head>
<body>
	<c:set var="topic" scope="request" value="mangeterms" />
	<h1>
		<fmt:message key="manageterms.page.title" />
	</h1>
	<pa:sucessMessage />
	<pa:failureMessage />

	<div class="actionsrow">
		<del class="btnwrapper">
			<h4>Intervention</h4>
			<ul class="btnrow">
				<li><a href="manageTermscreateIntervention.action" class="btn">
						<span class="btn_img"><span class="add"><fmt:message
									key="manageTerms.button.create" /></span></span>
				</a></li>
				<li><a href="manageTermssearchIntervention.action?searchStart=true" class="btn">
						<span class="btn_img"><span class="copy"><fmt:message
									key="manageTerms.button.import" /></span></span>
				</a></li>
				<li><a href="http://ncitermform.nci.nih.gov/ncitermform/"
					class="btn" target="_blank"> <span class="btn_img"><span
							class="upload"><fmt:message
									key="manageTerms.button.requestForm" /></span></span>
				</a></li>
			</ul>
			<br /> <br />
			<h4>Disease</h4>
			<ul class="btnrow">
				<li><a href="manageTermscreateDisease.action" class="btn"
					id="newDisease"> <span class="btn_img"><span class="add"><fmt:message
									key="manageTerms.button.create" /></span></span>
				</a></li>
				<li><a href="manageTermssearchDisease.action?searchStart=true" class="btn"
					id="importDisease"> <span class="btn_img"><span
							class="copy"><fmt:message
									key="manageTerms.button.import" /></span></span>
				</a></li>
				<li><a href="http://ncitermform.nci.nih.gov/ncitermform/"
					class="btn" target="_blank"> <span class="btn_img"><span
							class="upload"><fmt:message
									key="manageTerms.button.requestForm" /></span></span>
				</a></li>
			</ul>
		</del>
	</div> 
</body>
</html>