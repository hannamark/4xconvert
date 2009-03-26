<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="view.trial.page.title"/></title>
</head>
<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script language="javascript">
function submit (){   
    var action = "amendTrialamend.action";   
    document.forms[0].page.value = "Submit";
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${saveProtocol}', 600, 200, '', 'Amend Trial');
</script>
<body>
<div id="contentwide"> 
 <h1><fmt:message key="search.trial.view.page.title" /></h1>
 
<div class="box">
    <s:form > <s:actionerror/>          
        
    <table class="form">
          <tr>
            <th colspan="2"><fmt:message key="trial.amendDetails"/></th>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Amendment Number">
                        <fmt:message key="view.trial.amendmentNumber"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.localAmendmentNumber}"/> 
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Amendment Date">
                        <fmt:message key="view.trial.amendmentDate"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.amendmentDate}"/> 
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Assigned NCI Identifier">
                        <fmt:message key="view.trial.nci.id"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.assignedIdentifier}"/> 
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Lead Organization Trial Identifier">
                        <fmt:message key="view.trial.leadOrgTrialIdentifier"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.localProtocolIdentifier}"/> 
                </td>
          </tr>
          <c:if test="${trialDTO.nctIdentifier != null}">
              <tr>     
                    <td scope="row" class="label">
                        <label for="NCT Number">
                            <fmt:message key="view.trial.nctNumber"/>                
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.nctIdentifier }"/> 
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
                    <c:out value="${trialDTO.officialTitle}"/> 
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Trial Phase">
                        <fmt:message key="view.trial.phase"/>                
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.phaseCode}"/> 
                </td>
          </tr>
          <c:if test="${trialDTO.phaseOtherText!= ''}">
              <tr>     
                    <td scope="row" class="label">
                        <label for="Other Phase Text">
                            <fmt:message key="view.trial.otherPhaseText"/>                
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.phaseOtherText}"/> 
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
                    <c:out value="${trialDTO.trialType }"/>
                </td>
          </tr>
          <tr>     
                <td scope="row" class="label">
                    <label for="Primary Purpose">
                        <fmt:message key="view.trial.primaryPurpose"/>                
                    </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.primaryPurposeCode}"/>
                </td>
          </tr>
          <c:if test="${trialDTO.primaryPurposeOtherText != ''}">
              <tr>     
                    <td scope="row" class="label">
                        <label for="Other Purpose Text">
                            <fmt:message key="view.trial.otherPurposeText"/>                
                        </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.primaryPurposeOtherText}"/>
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
                <c:out value="${trialDTO.leadOrganizationName }"/>
             </td>
       </tr> 
       <tr>     
            <td scope="row" class="label">
                <label for="Principal Investigator">
                <fmt:message key="view.trial.principalInvestigator"/>                
                </label>
              </td>
             <td class="value">
                <c:out value="${trialDTO.piName }"/>
                </td>
       </tr>
       <tr>
            <td colspan="2" class="space">&nbsp;</td>
       </tr> 
       <c:if test="${trialDTO.sponsorName != null}">          
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
                    <c:out value="${trialDTO.sponsorName }"/>
                 </td>
           </tr> 
           <tr>     
                <td scope="row" class="label">
                <label for="Responsible Party">
                    <fmt:message key="view.trial.respParty"/>                
                </label>
                </td>
                 <td class="value">
                    <c:out value="${trialDTO.responsiblePartyType}"/>
                 </td>
           </tr> 
           <c:if test="${trialDTO.responsiblePersonName != ''}">  
               <tr>     
                    <td scope="row" class="label">
                    <label for="Responsible Party Contact">
                        <fmt:message key="view.trial.respPartyContact"/>                
                    </label>
                    </td>
                     <td class="value">
                        <c:out value="${trialDTO.responsiblePersonName}"/>
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
                    <c:out value="${trialDTO.contactEmail}"/>
                 </td>
           </tr> 
                  <tr>     
                <td scope="row" class="label">
                <label for="Phone">
                    <fmt:message key="view.trial.respPartyPhone"/>                
                </label>
                </td>
                 <td class="value">
                    <c:out value="${trialDTO.contactPhone}"/>
                 </td>
           </tr>  
           <tr>
                <td colspan="2" class="space">&nbsp;</td>
           </tr>
       </c:if>
       <c:if test="${trialDTO.summaryFourOrgName != ''}">             
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
                    <c:out value="${trialDTO.summaryFourFundingCategoryCode }"/>
                </td>
           </tr>
           <tr>     
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Sponsor/Source">
                    <fmt:message key="view.trial.FundingSponsor"/>                
                    </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.summaryFourOrgName }"/>
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
            <c:out value="${trialDTO.statusCode}"/>
         </td>
      </tr> 
      <c:if test="${trialDTO.reason != ''}">
          <tr>     
            <td scope="row" class="label">
            <label for="Trial Status Reason">
                <fmt:message key="view.trial.trialStatusReason"/>                
            </label>
           </td>
             <td class="value">
                <c:out value="${trialDTO.reason }"/>
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
           <c:out value="${trialDTO.statusDate}"/>
           </td>
      </tr> 
      <tr>     
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="view.trial.trialStartDate"/>                
              </label>
          </td>
          <td class="value">
               <c:out value="${trialDTO.startDate}"/>
               <c:out value="${trialDTO.startDateType }"/> 
          </td>
       </tr> 
       <tr>     
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="view.trial.primaryCompletionDate"/>                
            </label>
        </td>
        <td class="value">
             <c:out value="${trialDTO.completionDate}"/> 
             <c:out value="${trialDTO.completionDateType}"/>
        </td>
      </tr> 
     </table>
     <c:if test="${fn:length(trialDTO.indDtos) > 0}">
        <div class="box">                                   
                <h3>FDA IND/IDE Information for applicable trials</h3>  
                <display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                    name="${trialDTO.indDtos }" requestURI="searchTrialview.action" export="false"> 
                <display:column titleKey="search.trial.view.indldeTypeCode" property="indIde"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.indldeNumber" property="number"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.grantorCode" property="grantor"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.holderTypeCode" property="holderType"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.nciDivProgHolderCode" property="programCode"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.expandedAccessIndicator" property="expandedAccessType"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.expandedAccessStatusCode" property="expandedAccess"   sortable="true" headerClass="sortable"/>
                </display:table>
            
        </div>
        </c:if>
        <c:if test="${fn:length(trialDTO.fundingDtos) >0}">          
            <div class="box">
                <h3>NIH Grant Information (for NIH funded Trials)</h3>  
				<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
				        name="${trialDTO.fundingDtos}" requestURI="searchTrialview.action" export="false">    
				    <display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanism"   sortable="true" headerClass="sortable"/>
				    <display:column titleKey="search.trial.view.nihInstitutionCode" property="instituteCode"   sortable="true" headerClass="sortable"/>
				    <display:column titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
				    <display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
				</display:table>
            </div>
        </c:if>
        <c:if test="${fn:length(trialDTO.docDtos) >0}">          
            <div class="box">
               <display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">    
                <display:column titleKey="search.trial.view.documentTypeCode" property="typeCode"   sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>" 
                </display:table>
            </div>
        </c:if>
        <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li><a href="amendTrialedit.action"                
                    class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Edit</span></span></a></li>
               <li><a href="amendTrialamend.action"                
                    class="btn" onclick="submit();"><span class="btn_img"><span class="back">Submit</span></span></a></li>     
            </ul>   
        </del>
        </div>
    </s:form>
   </div>
   </div>
 </body>
 </html>