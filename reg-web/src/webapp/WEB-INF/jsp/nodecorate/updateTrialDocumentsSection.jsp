<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="collaborators.size > 0">
	<tr>
		<th colspan="2">Existing Trial Related Documents</th>
	</tr>
	<tr>
		<td colspan="2" class="space">
			<table class="data2">
				<tr>
					<td class="space">
						<table class="form">
							<tbody>
								<tr>
									<th>Document Type</th>
									<th>File Name</th>
								</tr>
								<s:iterator value="existingDocuments" var="docDto" status="stat">
									<tr>
										<td><s:property value="%{typeCode}" /></td>
										<td><a
											href="searchTrialviewDoc.action?identifier=${docDto.id}"><s:property
													value="%{fileName}" /></a></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</s:if>