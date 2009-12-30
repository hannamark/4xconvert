<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 

<div class="summarybox">
    <div class="float33_first">
      <div class="row">
          <span class="label"> <fmt:message key="outcomes.summary.physician"/></span> 
          <span class="value"><c:out value="${sessionScope.physicianName}"/></span>
      </div>
      <div class="row">
        <c:if test="${sessionScope.participantId != null}">
          <span class="label"><fmt:message key="outcomes.summary.participantID"/></span> 
          <span class="value"><c:out value="${sessionScope.participantId.value}"/></span>
        </c:if>
      </div>
    </div>
    <div class="float33">
      <div class="row">
          <span class="label"><fmt:message key="outcomes.summary.submittingOrganization"/></span> 
          <span class="value"> <c:out value="${sessionScope.submittingOrganizationName}"/></span>
      </div>
      <div class="row">
          <span class="label"><fmt:message key="outcomes.summary.submittingPerson"/></span> 
          <span class="value"> <c:out value="${sessionScope.submitterName}"/></span>
      </div>
    </div>
    <div class="float33">
      <div class="row">
        <c:if test="${sessionScope.treatmentPlanName != null}">
          <span class="label"><fmt:message key="outcomes.summary.treatmentPlan"/></span> 
          <span class="value"> <c:out value="${sessionScope.treatmentPlanName.value}"/></span>
        </c:if>
      </div><div class="row" />
        <c:if test="${sessionScope.courseName != null}">
          <span class="label"><fmt:message key="outcomes.summary.course"/></span> 
          <span class="value"> <c:out value="${sessionScope.courseName.value}"/></span>
        </c:if>
      </div>
    </div>
    
    <div class="clear"></div>
</div>