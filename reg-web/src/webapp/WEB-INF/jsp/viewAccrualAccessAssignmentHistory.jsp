<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manage.accrual.access.page.title" /></title>
<s:head />
</head>
<body>
	<!-- main content begins-->
	<c:set var="topic" scope="request" value="assignmentHistory" />
	<h1>
		<fmt:message key="manage.accrual.access.history.header" />
	</h1>

	<div class="box" id="filters">

		<reg-web:failureMessage />
		<reg-web:sucessMessage />

		<s:form name="manageAccrualAccess"
			action="manageAccrualAccesshistoryPaging.action">
			<table class="form">
			    <c:if test="${empty model.history}">
                    <tr height="200">
                        <td align="center" class="info">
                           <fmt:message key="manage.accrual.access.history.norecords" />
                        </td>
                    </tr>  			    
			    </c:if>
				<tr>
					<td><display:table class="data" sort="list" pagesize="50"
							uid="row" name="model.history" export="false"
							requestURI="manageAccrualAccesshistoryPaging.action">
							<display:setProperty name="basic.msg.empty_list"
								value="" />					
							<display:column escapeXml="true" title="Date"
								property="date" headerClass="sortable" sortable="true" />
							<display:column escapeXml="true" title="Assignee"
								property="assignee" headerClass="sortable nowrap" sortable="true" />
							<display:column escapeXml="true" title="Trial ID"
								property="trialNciId" headerClass="sortable nowrap" sortable="true" />
							<display:column escapeXml="true" title="Assignment Action" property="action"
								sortable="true" />							
							<display:column escapeXml="true" title="Comments" property="comments"
								sortable="true" />
							<display:column escapeXml="true" title="Assigner" property="assigner"
								sortable="true" />
						</display:table></td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>
