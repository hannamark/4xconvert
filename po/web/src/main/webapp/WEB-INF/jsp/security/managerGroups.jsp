<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<title>Manage User Groups</title>
<c:set var="topic" scope="request" value="managegroups" />
<style type="text/css">
#page {
	margin-bottom: 10px;
}

table.dataTable {
	padding-top: 10px;
}

table.dataTable tbody td {
	padding: 2px 2px;
	vertical-align: middle;
	width: 50%;
}

.dataTables_wrapper {
	padding: 10px 10px;
}

.dataTables_length {
	white-space: nowrap;
	padding-right: 20px;
}
</style>
<script type="text/javascript">
	(function($) {
		//******************
		//** On DOM Ready **
		//******************
		$(function() {

			var saveRolesURL = '<c:url value='/security/manage/groups/saveRoles.action'/>';
			displayWaitPanel();

			// Init Select2 boxes.
			$("select")
					.select2()
					.on(
							"change",
							function(e) {
								var userID = $(this).attr('data-user-id');
								var groups = $(this).val();
								displayWaitPanel();
								$
										.post(
												saveRolesURL,
												{
													userID : userID,
													newGroups : groups != null ? groups
															.join(";")
															: ""
												}, function() {
													hideWaitPanel();
												})
										.fail(
												function() {
													hideWaitPanel();
													alert("Unable to save your changes due to a server error.");
												});

							});

			// Prevent opening of the Select2 box upon unselect.
			var ts = 0;
			$(".select2-hidden-accessible").on("select2:unselect", function(e) {
				ts = e.timeStamp;
			}).on("select2:opening", function(e) {
				if (e.timeStamp - ts < 100) {
					e.preventDefault();
				}
			});

			$('#users').DataTable({
				"bFilter" : true,
				"paging" : true,
				"searching" : true,
				"info" : true
			});

			hideWaitPanel();

		});

	}(jQuery));
</script>
</head>
<body>

	<po:successMessages />

	<div id="page" style="margin-top: 10px;">
		<div class="boxouter_nobottom">
			<s:actionerror />
			<s:form action="manage/groups/execute" id="userGroupForm"
				method="POST">
				<table id="users">
					<thead>
						<tr>
							<th>LDAP ID</th>
							<th>Groups</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
							<tr>
								<td><c:out value="${user.loginName}"></c:out></td>
								<td><s:select size="3" data-user-id="%{#attr.user.userId}"
										id="groups_of_user_%{#attr.user.userId}" multiple="true"
										value="#action.getUserGroupsAsString(#attr.user)"
										list="groups"></s:select></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</s:form>
		</div>
	</div>


</body>
</html>
