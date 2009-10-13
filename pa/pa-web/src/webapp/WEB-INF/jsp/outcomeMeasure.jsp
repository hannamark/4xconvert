<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>
	<c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose>
</title> 
	<s:head />
</head>

<body onload="setFocusToFirstControl();">
<c:set var="topic" scope="request" value="abstract_outcome"/>
 <h1><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
  <pa:sucessMessage/>
    <s:form><s:actionerror/>
    <h2>Outcome Measures</h2>
    <s:if test="outcomeList != null">
    <input type="hidden" name="page" />
    <input type="hidden" name="id" />
	<s:set name="outcomeList" value="outcomeList" scope="request"/>
	<display:table name="outcomeList" id="row" class="data" sort="list"  pagesize="200" requestURI="interventionalStudyDesignoutcomeQuery.action" export="false">    
	    <display:column titleKey="osdesign.outcome.primary" property="primaryIndicator" sortable="true" headerClass="sortable" />
	    <display:column titleKey="osdesign.outcome.description" property="name" sortable="true" headerClass="sortable" />
	    <display:column titleKey="osdesign.outcome.timeFrame" property="timeFrame"  sortable="true" headerClass="sortable" />
	    <display:column titleKey="osdesign.outcome.safety" property="safetyIndicator" sortable="true" headerClass="sortable" />
	    <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
	    					|| (sessionScope.role == 'SuAbstractor')}">
	    <display:column title="Edit" class="action">
    		<s:url id="url" action="interventionalStudyDesignoutcomeedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
    	</display:column>    	
    	<display:column title="Delete" class="action">
			<s:url id="url" action="interventionalStudyDesignoutcomedelete"><s:param name="id" value="%{#attr.row.id}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
		</display:column>  
		</c:if>
	</display:table>
  </s:if> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
										|| (sessionScope.role == 'SuAbstractor')}">
					<li><s:a href="interventionalStudyDesignoutcomeinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					</c:if>
					<c:choose>
                    <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
           				<li><a href="interventionalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        			</c:when>
        			<c:otherwise>
          				<li><a href="observationalStudyDesigndetailsQuery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        			</c:otherwise>
        			</c:choose>				
					<li><a href="eligibilityCriteriaquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
 </body>
 </html>