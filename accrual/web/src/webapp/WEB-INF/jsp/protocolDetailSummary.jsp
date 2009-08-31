<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>


<div class="summarybox">
           
              <div>
                    <c:out value="${sessionScope.trialSummary.assignedIdentifier.value}"/>:
                    
              </div>        
            <div class="float33_first">
                <div class="row">
                    <span class="label"> <fmt:message key="accrual.trialSummary.lead.organization.trialID"/></span> 
                    <span class="value"><c:out value="${sessionScope.trialSummary.leadOrgTrialIdentifier.value}"/></span>
                </div>
                <div class="row">
                    <span class="label"><fmt:message key="accrual.trialSummary.lead.organization"/></span> 
                    <span class="value"><c:out value="${sessionScope.trialSummary.leadOrgName.value}"/></span>
                </div>
               
            </div>
                            
            <div class="float33">
                <div class="row">
                    <span class="label"><fmt:message key="accrual.trialSummary.principal.investigator"/></span> 
                    <span class="value"> <c:out value="${sessionScope.trialSummary.principalInvestigator.value}"/></span>
                </div>
                
                 <div class="row">
                    <span class="label"><fmt:message key="accrual.trialSummary.participation.site"/></span> 
                    <span class="value"> <c:out value="${sessionScope.trialSummary.principalInvestigator.value}"/></span>
                </div>
               
            </div>
                            
            
                
            <div class="clear"></div>
                            
   </div>