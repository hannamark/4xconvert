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

<SCRIPT LANGUAGE="JavaScript">
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        // there are no onload functions to call for this jsp
        // leave this function to prevent 'error on page'
    }
    
        
    
</SCRIPT>

<body>
<c:set var="topic" scope="request" value="abstractoutcome"/>
 <h1><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">
  <pa:sucessMessage/>
  <pa:failureMessage/>
    <s:form>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2>Outcome Measures</h2>
    <s:if test="outcomeList != null">
    <s:hidden name="page" />
    <s:hidden name="id" />
	<s:set name="outcomeList" value="outcomeList" scope="request"/>
	<display:table name="outcomeList" id="row" class="data" sort="list"  pagesize="200" requestURI="interventionalStudyDesignoutcomeQuery.action" export="false">
	    <display:column escapeXml="true" titleKey="osdesign.outcome.primary" sortable="true" headerClass="sortable">
            <pa:displayBoolean value="${row.outcomeMeasure.primaryIndicator}"/>
        </display:column>
	    <display:column escapeXml="true" titleKey="osdesign.outcome.name" property="outcomeMeasure.name" sortable="true" headerClass="sortable" />
	    <display:column escapeXml="true" titleKey="osdesign.outcome.timeFrame" property="outcomeMeasure.timeFrame"  sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="osdesign.outcome.description" property="outcomeMeasure.description"  sortable="true" headerClass="sortable" />
	    <display:column escapeXml="true" titleKey="osdesign.outcome.safety" sortable="true" headerClass="sortable">
            <pa:displayBoolean value="${row.outcomeMeasure.safetyIndicator}"/>
        </display:column>
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <display:column title="Edit" class="action">
                <s:url id="url" action="interventionalStudyDesignoutcomeedit"><s:param name="id" value="%{#attr.row.outcomeMeasure.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
                <s:a href="%{url}"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
            </display:column>
            <display:column title="Delete" class="action">
                <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.outcomeMeasure.id}" value="%{#attr.row.outcomeMeasure.id in objectsToDelete}"/>
            </display:column>
        </pa:scientificAbstractorDisplayWhenCheckedOut>
	</display:table>
  </s:if>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>
					   <li><s:a href="interventionalStudyDesignoutcomeinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{outcomeList != null && !outcomeList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>					   
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
				</ul>
			</del>
		</div>
  	</s:form>
   </div>
 </body>
 </html>