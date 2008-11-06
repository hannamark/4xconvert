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
<div id="contentwide"> 
 <h1><fmt:message key="search.trial.view.page.title" /></h1>

<!--Help Content-->
   <!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->

  <div class="box">
    <s:form > <s:actionerror/>          
    	
    <table class="form">
    	  <tr>
    	  	<th colspan="2"><h2><fmt:message key="view.trial.trialDetails"/></h2></th>
    	  </tr>
          <tr>     
            <td scope="row" class="label">
                <label for="officialTitle">
                    <fmt:message key="view.trial.title"/>                
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.officialTitle.value }"/> 
            </td>
          </tr>
          <tr>     
            <td scope="row" class="label">
                <label for="Identifier">
                    <fmt:message key="view.trial.identifier"/>                
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.identifier.extension }"/> 
            </td>
          </tr>
          <tr>     
          	<td scope="row" class="label">
              		<label for="Lead Organization Trial Identifier">
                  <fmt:message key="view.trial.leadOrgTrialIdentifier"/>                
                  </label>
              </td>
              <td class="value">
              	<c:out value="${sessionScope.studyParticipation.localStudyProtocolIdentifier.value }"/> 
              </td>
          </tr>
         <tr>     
            <td scope="row" class="label">
                <label for="Trial Phase">
                    <fmt:message key="view.trial.phase"/>                
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.phaseCode.code }"/> 
            </td>
          </tr> 
          <tr>     
            <td scope="row" class="label">
                <label for="Primary Purpose">
                    <fmt:message key="view.trial.primaryPurpose"/>                
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.primaryPurposeCode.code }"/>
                 <c:out value="${sessionScope.trialSummary.primaryPurposeOtherText.value }"/>
            </td>
          </tr>
          <tr>
          	<td colspan="2" class="space">&nbsp;</td>
          </tr>           
          <tr>
          	<th colspan="2"><h2><fmt:message key="view.trial.leadOrgInvestigator"/></h2></th>
          </tr>
          <tr>     
        	<td scope="row" class="label">
            <label for="Lead Organization">
                <fmt:message key="view.trial.leadOrganization"/>                
            </label>
            </td>
           	 <td class="value">
           		<c:out value="${sessionScope.studyProtocolIi.leadOrganizationName }"/>
             </td>
       </tr> 
       <tr>     
      		<td scope="row" class="label">
      			<label for="Principal Investigator">
      			<fmt:message key="view.trial.principalInvestigator"/>                
              	</label>
              </td>
         	 <td class="value">
         		<c:out value="${sessionScope.studyProtocolIi.piFullName }"/>
         		</td>
       </tr> 
       <tr>
     		<td colspan="2" class="space">&nbsp;</td>
       </tr>           
       <tr>
     		<th colspan="2"><h2><fmt:message key="view.trial.Summary4Information"/></h2></th>
       </tr>
       <tr>     
			<td scope="row" class="label">
				<label for="Summary 4 Funding Category">
				<fmt:message key="view.trial.FundingCategory"/>                
				</label>
			</td>
			<td class="value">
				<c:out value="${sessionScope.nihInstitute.typeCode.code }"/>
			</td>
	   </tr>
  	   <tr>     
			<td scope="row" class="label">
				<label for="Summary 4 Funding Sponsor/Source">
				<fmt:message key="view.trial.FundingSponsor"/>                
				</label>
			</td>
			<td class="value">
				<c:out value="${sessionScope.nihInstitute.organizationIdentifier.extension }"/>
			</td>
	   </tr> 
      <tr>
          <td colspan="2" class="space">&nbsp;</td>
      </tr>           
      <tr>
          <th colspan="2"><h2><fmt:message key="view.trial.statusDates"/></h2></th>
      </tr>
      <tr>     
    	<td scope="row" class="label">
        <label for="Current Trial Status">
            <fmt:message key="view.trial.currentTrialStatus"/>                
        </label>
       </td>
       	 <td class="value">
       		<c:out value="${sessionScope.trialOverallStatus.statusCode.code }"/>
         </td>
      </tr> 
      <tr>     
      	<td scope="row" class="label">
          <label for="Current Trial Status Date">
              <fmt:message key="view.trial.currentTrialStatusDate"/>                
          </label>
         </td>
         <td class="value">
      	   <fmt:formatDate value="${sessionScope.trialOverallStatus.statusDate.value }"/>
           </td>
      </tr> 
      <tr>     
          <td scope="row" class="label">
              <label for="Study Start Date">
                  <fmt:message key="view.trial.studyStartDate"/>                
              </label>
          </td>
          <td class="value">
          	   <fmt:formatDate value="${sessionScope.trialSummary.startDate.value }"/>
               <c:out value="${sessionScope.trialSummary.startDateTypeCode.code }"/> 
          </td>
       </tr> 
       <tr>     
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="view.trial.primaryCompletionDate"/>                
            </label>
        </td>
        <td class="value">
        	 <fmt:formatDate value="${sessionScope.trialSummary.primaryCompletionDate.value }"/> 
             <c:out value="${sessionScope.trialSummary.primaryCompletionDateTypeCode.code }"/>
        </td>
      </tr> 
     </table>
     	<div class="box">
     		<c:if test="${requestScope.studyIndIde != null}">						
     		<h2>IND/IDE Information</h2>  
     		<jsp:include page="/WEB-INF/jsp/searchTrialViewIndIde.jsp"/>	
     		</c:if>
		</div>
        <div class="box">
    		<c:if test="${requestScope.trialFundingList != null}">						
    		<h2>NIH Grant Information</h2>  
    		<jsp:include page="/WEB-INF/jsp/searchTrialViewGrants.jsp"/>	
    		</c:if>
		</div>
		
		<div class="box">
			<c:if test="${requestScope.protocolDocument != null}">						
			<h2>Trial Related Documents</h2>  
			<jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>	
			</c:if>
		</div>

	
		<div class="actionsrow">
		<del class="btnwrapper">
        	<ul class="btnrow">
            	<li><a href="searchTrialquery.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            </ul>   
        </del>
        </div>
    </s:form>
   </div>
   </div>
 </body>
 </html>