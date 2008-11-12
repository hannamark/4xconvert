<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="view.trial.page.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript"> 
    function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
	</SCRIPT>
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="view.trial.page.title" /></h1>
<reg-web:sucessMessage/>
<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->

  <div class="box">
    <s:form ><s:actionerror/>
    <h1><fmt:message key="submit.trial.success.message"/> <c:out value="${sessionScope.trialSummary.nciAccessionNumber }"/> </h1>
	<h2><fmt:message key="view.trial.trialDetails"/></h2>

        <table class="form">
            <tr>
	            <td scope="row" class="label">
	                <label for="nct">	                
	                   <fmt:message key="view.trial.nciAccessionNumber"/>
	                </label>
	            </td>
                <td class="value">
                    <c:out value="${requestScope.trialSummary.nciAccessionNumber}"/> 
                </td>
            </tr>
            <tr>
            <tr>
                <td scope="row" class="label">
                    <label for="nct">
                        <fmt:message key="view.trial.leadOrgTrialIdentifier"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.studyParticipation.localProtocolIdentifier }"/> 
                </td>
            </tr>
            <tr>     
            <td scope="row" class="label">
                <label for="officialTitle">
                    <fmt:message key="view.trial.title"/>                
                </label>
            </td>
            <td class="value">
                 <c:out value="${requestScope.trialSummary.trialTitle }"/> 
            </td>
            </tr>       
            <tr>            
            <td scope="row" class="label">
                <label for="trialPhase">
                    <fmt:message key="view.trial.phase"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${requestScope.trialSummary.trialPhase }"/> 
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                    <fmt:message key="view.trial.leadOrganization"/>
                </label>
            </td>
            <td class="value">
                Organization A
            </td>
            </tr> 
            <c:if test="${requestScope.trialFundingList != null}">  
		        <tr>
	                <td colspan="2" class="space">&nbsp;</td>
		        </tr>	        
		        <tr>
	                <th colspan="2"><h2><fmt:message key="view.trial.grantInfo"/></h2></th>
		        </tr>
	            <tr>
	                <td>
	                    <table>
	                        <tr>
	                           <td scope="row" class="label">
					                <label for="leadOrg"> 
					                    <fmt:message key="view.trial.fundingMechanism"/>
					                </label>
					            </td>
					            <td scope="row" class="label">
	                                <label for="leadOrg"> 
	                                    <fmt:message key="view.trial.instituteCode"/>
	                                </label>
	                            </td>
	                            <td scope="row" class="label">
	                                <label for="leadOrg"> 
	                                    <fmt:message key="view.trial.serialNumber"/>
	                                </label>
	                            </td>
	                            <td scope="row" class="label">
	                                <label for="leadOrg"> 
	                                    <fmt:message key="view.trial.divProgram"/>
	                                </label>
	                            </td>
	                        
	                        </tr>
	                        <tr>
	                          <td class="value">
	                            <c:out value="${requestScope.trialFundingList.fundingMechanismCode }"/> 
	                          </td>
	                          <td class="value">
	                            <c:out value="${requestScope.trialFundingList.nihInstitutionCode }"/> 
	                          </td>
	                          <td class="value">
	                            <c:out value="${requestScope.trialFundingList.serialNumber }"/> 
	                          </td>
	                          <td class="value">
	                            <c:out value="${requestScope.trialFundingList.nciDivisionProgramCode }"/> 
	                          </td>
	                        </tr>
	                    </table>
	                </td>
	            </tr>
            </c:if>
            <c:if test="${requestScope.trialOverallStatus != null}">  
                <tr>
                    <td colspan="2" class="space">&nbsp;</td>
                </tr>           
                <tr>
                    <th colspan="2"><h2><fmt:message key="view.trial.statusDates"/></h2></th>
                </tr>
                <tr>            
		            <td scope="row" class="label">
		                <label for="currentTrialStatus">
		                    <fmt:message key="view.trial.currentTrialStatus"/>
		                </label>
		            </td>
		            <td class="value">
		                <c:out value="${requestScope.trialOverallStatus.statusCode }"/> 
		            </td>
	            </tr>
	            <tr>            
	                <td scope="row" class="label">
	                    <label for="currentTrialStatusDate">
	                        <fmt:message key="view.trial.currentTrialStatusDate"/>
	                    </label>
	                </td>
	                <td class="value">
	                    <c:out value="${requestScope.trialOverallStatus.statusDate }"/> 
	                </td>
                </tr>
                <c:if test="${requestScope.trialSummary.startDate != null}">
	                <tr>            
		                <td scope="row" class="label">
		                    <label for="studyStartDate">
		                        <fmt:message key="view.trial.studyStartDate"/>
		                    </label>
		                </td>
		                <td class="value">
		                    <c:out value="${requestScope.trialSummary.startDate }"/>
		                    <c:out value="${requestScope.trialSummary.startDateType }"/>  
		                </td>
	                </tr>
                </c:if>
                <c:if test="${requestScope.trialSummary.completionDate != null}">
	                <tr>       
	                    <td scope="row" class="label">
	                        <label for="primaryCompletionDate">
	                            <fmt:message key="view.trial.primaryCompletionDate"/>
	                        </label>
	                    </td>
	                    <td class="value">
	                        <c:out value="${requestScope.trialSummary.completionDate }"/>
	                        <c:out value="${requestScope.trialSummary.completionDateType }"/>  
	                    </td>
	                </tr>
                </c:if>
            </c:if>
                <tr>
                    <td colspan="2" class="space">&nbsp;</td>
                </tr>           
                <tr>
                    <th colspan="2"><h2><fmt:message key="view.trial.documents"/></h2></th>
                </tr>
                <tr>            
                    <td scope="row" class="label">
                        <label for="protocolDocument">
                            <fmt:message key="view.trial.protocolDocument"/>
                        </label>
                    </td>
                    <td class="value">
                        <c:url var="url" value="submitTrialviewDoc.action" >
						  <c:param name="id " value="${requestScope.protocolDocument.id}" />
						</c:url>
                        <a href="<c:out value="${url}"/>"><c:out value="${requestScope.protocolDocument.fileName }"/></a>
                    </td>
                </tr>
                <tr>            
                    <td scope="row" class="label">
                        <label for="irbApproval">
                            <fmt:message key="view.trial.irbApproval"/>
                        </label>
                    </td>
                    <td class="value">
                        <c:url var="url" value="submitTrialviewDoc.action" >
                          <c:param name="id " value="${requestScope.irbApproval.id}" />
                        </c:url>
                        <a href="<c:out value="${url}"/>"><c:out value="${requestScope.irbApproval.fileName }"/></a>
                    </td>
                </tr>
                
                <tr>            
                    <td scope="row" class="label">
                        <label for="informedConsent">
                            Informed Consent Document
                        </label>
                    </td>
                    <td class="value">
                        <c:url var="url" value="submitTrialviewDoc.action" >
                          <c:param name="id " value="${requestScope.informedConsent.id}" />
                        </c:url>
                        <a href="<c:out value="${url}"/>"><c:out value="${requestScope.informedConsent.fileName }"/></a>
                    </td>
                </tr>      
                <tr>            
                    <td scope="row" class="label">
                        <label for="other">
                            Other
                        </label>
                    </td>
                    <td class="value">
                        <c:url var="url" value="submitTrialviewDoc.action" >
                          <c:param name="id " value="${requestScope.other.id}" />
                        </c:url>
                        <a href="<c:out value="${url}"/>"><c:out value="${requestScope.other.fileName }"/></a>
                    </td>
                </tr>                 
        </table>  
                  
    </s:form>
   </div>

 </body>
 </html>