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
	<h1 class="heading"><span><fmt:message key="manage.accrual.access.byTrial.header" /></span></h1>

	<div class="box" id="filters">
		<reg-web:failureMessage />
		<reg-web:sucessMessage />
		<s:form name="manageAccrualAccess"
			action="manageAccrualAccessbyTrialPaging.action">
			    <s:set var="byTrial" scope="request" value="%{model.byTrial}" />			    			
                <c:forEach var="entry" items="${requestScope['byTrial']}">
                	<div class="mb20">
	                	<h3 class="table-title"><c:out value="${entry.key.code}"/></h3>
	                </div>
	                <display:table class="data table table-striped b1px mb40 sortable" sort="list" pagesize="20"
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
                     </display:table>
                </c:forEach>
                <c:if test="${empty byTrial}">
                           <fmt:message key="manage.accrual.access.byTrial.norecords" />
                </c:if>
		</s:form>
	</div>
</body>
</html>
