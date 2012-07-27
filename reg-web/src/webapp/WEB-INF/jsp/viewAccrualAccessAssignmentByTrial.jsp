<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="manage.accrual.access.page.title" /></title>
    <s:head/>
</head>
<body>
	<!-- main content begins-->
	<c:set var="topic" scope="request" value="assignmentByTrial" />
	<h1>
		<fmt:message key="manage.accrual.access.byTrial.header" />
	</h1>

	<div class="box" id="filters">

		<reg-web:failureMessage />
		<reg-web:sucessMessage />

		<s:form name="manageAccrualAccess"
			action="manageAccrualAccessbyTrialPaging.action">
			<table class="form">
			    <s:set var="byTrial" scope="request" value="%{model.byTrial}" />			    			
                <c:forEach var="entry" items="${requestScope['byTrial']}">
	                <tr>
	                    <td align="left" class="accrual_trial_cat_title" nowrap="nowrap">
	                        <c:out value="${entry.key.code}"/>
	                    </td>
	                </tr>
                     <tr>
                        <td><display:table class="data" sort="list" pagesize="20"
                            uid="${entry.key.code}" name="${entry.value}" export="false"
                            requestURI="manageAccrualAccessbyTrialPaging.action">
                            <display:setProperty name="basic.msg.empty_list"
                                value="No Accrual Access Assignment records available." />   
                            <c:set scope="page" var="row" value="${pageScope[entry.key.code]}"/>                
                            <display:column title="NCI Trial Identifier"
                                headerClass="sortable nowrap" sortable="true" >
                                <c:out value="${row.trialNciId}"></c:out>
                            </display:column>
                            <display:column title="Title"
                                headerClass="sortable" sortable="true" >
                                <c:out value="${func:abbreviate(row.trialTitle, 200)}"></c:out>
                            </display:column>
                            <display:column title="Accrual Submitter"
                                headerClass="sortable nowrap" sortable="true">
                                <c:forEach var="submitter" items="${row.accrualSubmitters}">
                                    <c:out value="${submitter}"></c:out><br/>
                                </c:forEach>
                            </display:column>                            
                        </display:table></td>
                     </tr>
                     <tr>
                        <td>&nbsp;</td>
                     </tr>
                </c:forEach>
                <c:if test="${empty byTrial}">
                    <tr height="200">
                        <td align="center" class="info">
                           <fmt:message key="manage.accrual.access.byTrial.norecords" />
                        </td>
                    </tr>                
                </c:if>
			</table>
		</s:form>
	</div>
</body>
</html>
