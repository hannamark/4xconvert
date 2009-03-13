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
 <c:set var="topic" scope="request" value="view_result"/>

<!--Help Content-->
   <!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->

  <div class="box">
     <c:if test="${param.trialAction == 'submit'}">
        <div class="confirm_msg">
          <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.trialSummary.assignedIdentifier.extension}</strong>
        </div>
     </c:if>
    <s:form > <s:actionerror/>          
    	
    <table class="form">
    	  <tr>
    	  	<th colspan="2"><fmt:message key="view.trial.trialDetails"/></th>
    	  </tr>
    	  <tr>     
          		<td scope="row" class="label">
          			<label for="Identifier">
          				<fmt:message key="view.trial.identifier"/>                
                  	</label>
                </td>
                <td class="value">
                	<c:out value="${requestScope.trialSummary.assignedIdentifier.extension}"/> 
                </td>
          </tr>
          <tr>     
        		<td scope="row" class="label">
            		<label for="Lead Organization Trial Identifier">
            			<fmt:message key="view.trial.leadOrgTrialIdentifier"/>                
            			</label>
            	</td>
            	<td class="value">
            		<c:out value="${requestScope.studyParticipation.localStudyProtocolIdentifier.value }"/> 
            	</td>
          </tr>
          <c:if test="${requestScope.studyNCTNumber != null}">
	          <tr>     
	                <td scope="row" class="label">
	                    <label for="NCT Number">
	                        <fmt:message key="view.trial.nctNumber"/>                
	                        </label>
	                </td>
	                <td class="value">
	                    <c:out value="${requestScope.studyNCTNumber }"/> 
	                </td>
	          </tr>
          </c:if>
          <tr>     
            	<td scope="row" class="label">
                	<label for="officialTitle">
                    	<fmt:message key="view.trial.title"/>                
                    </label>
                </td>
                <td class="value">
                 	<c:out value="${requestScope.trialSummary.officialTitle.value }"/> 
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Trial Phase">
                        <fmt:message key="view.trial.phase"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialSummary.phaseCode.code }"/> 
                </td>
          </tr>
          <c:if test="${requestScope.trialSummary.phaseOtherText.value != null}">
	          <tr>     
	                <td scope="row" class="label">
	                    <label for="Other Phase Text">
	                        <fmt:message key="view.trial.otherPhaseText"/>                
	                        </label>
	                </td>
	                <td class="value">
	                    <c:out value="${requestScope.trialSummary.phaseOtherText.value }"/> 
	                </td>
	          </tr>
          </c:if> 
          <tr>     
                <td scope="row" class="label">
                    <label for="Trial Type">
                        <fmt:message key="view.trial.type"/>                
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialType }"/>
                </td>
          </tr>
          <tr>     
          		<td scope="row" class="label">
          			<label for="Primary Purpose">
          				<fmt:message key="view.trial.primaryPurpose"/>                
          			</label>
          		</td>
          		<td class="value">
          			<c:out value="${requestScope.trialSummary.primaryPurposeCode.code }"/>
          		</td>
          </tr>
          <c:if test="${requestScope.trialSummary.primaryPurposeOtherText.value != null}">
	          <tr>     
	                <td scope="row" class="label">
	                    <label for="Other Purpose Text">
	                        <fmt:message key="view.trial.otherPurposeText"/>                
	                    </label>
	                </td>
	                <td class="value">
	                    <c:out value="${requestScope.trialSummary.primaryPurposeOtherText.value }"/>
	                </td>
	          </tr>
          </c:if>

         
          <tr>
          	<td colspan="2" class="space">&nbsp;</td>
          </tr>           
          <tr>
          	<th colspan="2"><fmt:message key="view.trial.leadOrgInvestigator"/></th>
          </tr>
          <tr>     
        	<td scope="row" class="label">
            <label for="Lead Organization">
                <fmt:message key="view.trial.leadOrganization"/>                
            </label>
            </td>
           	 <td class="value">
           		<c:out value="${requestScope.studyProtocolIi.leadOrganizationName }"/>
             </td>
       </tr> 
       <tr>     
      		<td scope="row" class="label">
      			<label for="Principal Investigator">
      			<fmt:message key="view.trial.principalInvestigator"/>                
              	</label>
              </td>
         	 <td class="value">
         		<c:out value="${requestScope.studyProtocolIi.piFullName }"/>
         		</td>
       </tr>
       <tr>
            <td colspan="2" class="space">&nbsp;</td>
       </tr> 
       <c:if test="${requestScope.sponsor != null}">          
	       <tr>
	            <th colspan="2"><fmt:message key="view.trial.sponsorResParty"/></th>
	          </tr>
	          <tr>     
	            <td scope="row" class="label">
	            <label for="Sponsor">
	                <fmt:message key="view.trial.sponsor"/>                
	            </label>
	            </td>
	             <td class="value">
	                <c:out value="${requestScope.sponsor }"/>
	             </td>
	       </tr> 
	       <tr>     
	            <td scope="row" class="label">
	            <label for="Responsible Party">
	                <fmt:message key="view.trial.respParty"/>                
	            </label>
	            </td>
	             <td class="value">
	                <c:out value="${requestScope.respParty}"/>
	             </td>
	       </tr> 
	       <c:if test="${requestScope.respPartyContact != null}">  
		       <tr>     
		            <td scope="row" class="label">
		            <label for="Responsible Party Contact">
		                <fmt:message key="view.trial.respPartyContact"/>                
		            </label>
		            </td>
		             <td class="value">
		                <c:out value="${requestScope.respPartyContact}"/>
		             </td>
		       </tr>
	       </c:if>
	       <tr>
                <td scope="row" class="label">
	            <label for="Email Address">
	                <fmt:message key="view.trial.respPartyEmailAddr"/>                
	            </label>
	            </td>
	             <td class="value">
	                <c:out value="${requestScope.respPartyEmailAddr}"/>
	             </td>
	       </tr> 
	              <tr>     
	            <td scope="row" class="label">
	            <label for="Phone">
	                <fmt:message key="view.trial.respPartyPhone"/>                
	            </label>
	            </td>
	             <td class="value">
	                <c:out value="${requestScope.respPartyPhone}"/>
	             </td>
	       </tr>  
	       <tr>
	     		<td colspan="2" class="space">&nbsp;</td>
	       </tr>
       </c:if>
       <c:if test="${requestScope.summaryFourSponsorName != null}">             
	       <tr>
	     		<th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
	       </tr>
	       <tr>     
				<td scope="row" class="label">
					<label for="Summary 4 Funding Category">
					<fmt:message key="view.trial.FundingCategory"/>                
					</label>
				</td>
				<td class="value">
					<c:out value="${requestScope.nihInstitute.typeCode.code }"/>
				</td>
		   </tr>
	  	   <tr>     
				<td scope="row" class="label">
					<label for="Summary 4 Funding Sponsor/Source">
					<fmt:message key="view.trial.FundingSponsor"/>                
					</label>
				</td>
				<td class="value">
					<c:out value="${requestScope.summaryFourSponsorName }"/>
				</td>
		   </tr> 
	      <tr>
	          <td colspan="2" class="space">&nbsp;</td>
	      </tr>
      </c:if>          
      <tr>
          <th colspan="2"><fmt:message key="view.trial.statusDates"/></th>
      </tr>
      <tr>     
    	<td scope="row" class="label">
        <label for="Current Trial Status">
            <fmt:message key="view.trial.currentTrialStatus"/>                
        </label>
       </td>
       	 <td class="value">
       		<c:out value="${requestScope.trialOverallStatus.statusCode.code }"/>
         </td>
      </tr> 
      <c:if test="${requestScope.trialOverallStatus.reasonText.value != null}">
	      <tr>     
	        <td scope="row" class="label">
	        <label for="Trial Status Reason">
	            <fmt:message key="view.trial.trialStatusReason"/>                
	        </label>
	       </td>
	         <td class="value">
	            <c:out value="${requestScope.trialOverallStatus.reasonText.value }"/>
	         </td>
	      </tr>
      </c:if> 
      <tr>     
      	<td scope="row" class="label">
          <label for="Current Trial Status Date">
              <fmt:message key="view.trial.currentTrialStatusDate"/>                
          </label>
         </td>
         <td class="value">
      	   <fmt:formatDate value="${requestScope.trialOverallStatus.statusDate.value }"/>
           </td>
      </tr> 
      <tr>     
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="view.trial.trialStartDate"/>                
              </label>
          </td>
          <td class="value">
          	   <fmt:formatDate value="${requestScope.trialSummary.startDate.value }"/>
               <c:out value="${requestScope.trialSummary.startDateTypeCode.code }"/> 
          </td>
       </tr> 
       <tr>     
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="view.trial.primaryCompletionDate"/>                
            </label>
        </td>
        <td class="value">
        	 <fmt:formatDate value="${requestScope.trialSummary.primaryCompletionDate.value }"/> 
             <c:out value="${requestScope.trialSummary.primaryCompletionDateTypeCode.code }"/>
        </td>
      </tr> 
     </table>
     <c:if test="${requestScope.studyIndIde != null}">
     	<div class="box">     								
     			<h3>FDA IND/IDE Information for applicable trials</h3>  
     			<jsp:include page="/WEB-INF/jsp/searchTrialViewIndIde.jsp"/>     		
		</div>
		</c:if>
		<c:if test="${requestScope.trialFundingList != null}">			
        	<div class="box">
    			<h3>NIH Grant Information (for NIH funded Trials)</h3>  
    			<jsp:include page="/WEB-INF/jsp/searchTrialViewGrants.jsp"/>
			</div>
		</c:if>
		<c:if test="${requestScope.protocolDocument != null}">
			<div class="box">		
				<h3>Trial Related Documents</h3>  
				<jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>
			</div>
		</c:if>

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