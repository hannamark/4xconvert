<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="review.trial.page.title"/></title>
    <s:head/>
</head>

<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
<c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
<script language="javascript">
function submitTrial(){
	var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
	if (assignedId != '') {
		document.forms[0].action="updateTrialupdate.action";
		document.forms[0].submit();
		showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');	
	} 
}
function editTrial() {
	var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
    if (assignedId != '') {
        document.forms[0].action="updateTrialedit.action";
        document.forms[0].submit();
    } 
    
    
}
function printProtocol (){   
	var sOption="toolbar=no,location=no,directories=no,menubar=yes,"; 
    sOption+="scrollbars=yes,width=750,height=600,left=100,top=25"; 
var sWinHTML = document.getElementById('contentprint').innerHTML; 

var winprint=window.open("","",sOption); 
    winprint.document.open(); 
    winprint.document.write('<html><body>'); 
    winprint.document.write(sWinHTML);          
    winprint.document.write('</body></html>'); 
    winprint.document.close(); 
    winprint.focus(); 
}
</script>
<body>
<div id="contentwide"> 
 <h1><fmt:message key="review.trial.view.page.title" /></h1>
 
<div class="box">
    <s:form > <s:actionerror/>
    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>          
    <div id="contentprint">        
    <table class="form">
         <tr>
            <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
          </tr>
          <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
          <tr>     
                <td scope="row" class="label">
                    <label for="Assigned NCI Identifier">
                        <strong><fmt:message key="view.trial.nciAccessionNumber"/></strong>                
                        </label>
                </td>
                <td class="value">
                    <strong><c:out value="${trialDTO.assignedIdentifier}"/></strong> 
                </td>
          </tr>
          </c:if>
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
            <th colspan="2"><fmt:message key="view.trial.trialDetails"/></th>
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
       <c:if test="${trialDTO.sponsorName != null}">          
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
           <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">  
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
           <c:if test="${fn:trim(trialDTO.responsibleGenericContactName) != ''}">  
               <tr>     
                    <td scope="row" class="label">
                    <label for="Responsible Party Contact">
                        <fmt:message key="view.trial.respPartyContact"/>                
                    </label>
                    </td>
                     <td class="value">
                        <c:out value="${trialDTO.responsibleGenericContactName}"/>
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
       <c:if test="${fn:trim(trialDTO.summaryFourOrgName) != ''}">             
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
      </c:if>
        <c:if test="${trialDTO.programCodeText != ''}">   
             <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">             
                <tr>
                    <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
                </tr>
            </c:if>
           <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <c:out value="${trialDTO.programCodeText}"/>
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
     <c:if test="${trialDTO.regulatoryAuthority != null}">
     <h3>Regulatory Information</h3> 
     <table>
     <tr>
        <td scope="row" class="label">
        <fmt:message key="regulatory.oversight.country.name"/></td>
          <td class="value">
           <s:property value="regulatoryAuthority.trialOversgtAuthCountry"/>
         </td>
       </tr>
       <!--  Trial Oversignt Authority Organization Name -->
     <tr>
         <td scope="row" class="label">
           <fmt:message key="regulatory.oversight.auth.name"/></td>
                     <td class="value">
                     <s:property value="regulatoryAuthority.trialOversgtAuthOrgName"/>
                                                         
                     </td>
     </tr>   
     <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
         <fmt:message key="regulatory.FDA.regulated.interv.ind"/></td>
         <td class="value">
              <s:property value="regulatoryAuthority.fdaRegulatedInterventionIndicator"/>
         </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label"><fmt:message key="regulatory.section801.ind"/></td>
         <td class="value">
         <s:property value="regulatoryAuthority.section801Indicator"/>
         </td>
     </tr>
     
     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label"><fmt:message key="regulatory.delayed.posting.ind"/></td>
         <td class="value">
         <s:property value="regulatoryAuthority.delayedPostingIndicator"/>
         </td>       
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
         <td class="value">  
                  <s:property value="regulatoryAuthority.dataMonitoringIndicator"/>    
         </td>       
     </tr>
     </table>
     </c:if>
     <c:if test="${trialDTO.indIdeUpdateDtos != null && fn:length(trialDTO.indIdeUpdateDtos) > 0}">
       <div class="box">
       <h3>Updated FDA IND/IDE </h3> 
       <display:table class="data" sort="list"  uid="row"  name="${trialDTO.indIdeUpdateDtos}">
        <display:column title="IndIde Type" property="indldeType"  headerClass="sortable" style="width:75px" />
        <display:column title="Number" property="indldeNumber"  headerClass="sortable" style="width:75px"/>
        <display:column title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
        <display:column title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
        <display:column title="Program Code" property="programCode"  headerClass="sortable" style="width:75px"/>
        <display:column title="Expanded Access" property="expandedAccessIndicator"  headerClass="sortable" style="width:75px"/>
        <display:column title="Expanded Access Type" property="expandedAccessStatus"  headerClass="sortable" style="width:75px"/>
        </display:table>
      </div>
     </c:if>
      <c:if test="${trialDTO.indIdeAddDtos != null && fn:length(trialDTO.indIdeAddDtos) > 0}">  
       <div class="box">  
         <h3>Added FDA IND/IDE </h3> 
         <display:table class="data" sort="list"  uid="row"  name="${trialDTO.indIdeAddDtos}">
         <display:column title="IndIde Type" property="indIde"  headerClass="sortable" style="width:75px"/>
         <display:column title="Number" property="number"  headerClass="sortable" style="width:75px"/>
         <display:column title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
         <display:column title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
         <display:column title="Program Code" property="programCode"  headerClass="sortable" style="width:75px"/>
         <display:column title="Expanded Access" property="expandedAccess"  headerClass="sortable" style="width:75px"/>
         <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable" style="width:75px" />
         </display:table>
       </div>  
     </c:if>
     <c:if test="${trialDTO.fundingDtos != null && fn:length(trialDTO.fundingDtos) > 0}">  
        <div class="box">                                   
        <h3>Updated Grant Information </h3> 
        <display:table class="data" sort="list"  uid="row"  name="${trialDTO.fundingDtos}" >
        <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
        <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
        <display:column title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>  
        <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
        </display:table>
      </div>  
     </c:if>
     <c:if test="${trialDTO.fundingAddDtos != null && fn:length(trialDTO.fundingAddDtos) > 0}">
     <div class="box">  
       <h3>Added Grant Information </h3> 
        <display:table class="data" sort="list"  uid="row"  name="${trialDTO.fundingAddDtos}" >
        <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
        <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
        <display:column title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>
        <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
        </display:table>
     </div>   
     </c:if>
     <c:if test="${trialDTO.collaborators != null && fn:length(trialDTO.collaborators) > 0}"> 
      <div class="box">        
       <h3>Colaborators </h3>  
       <display:table class="data" sort="list"  uid="row"  name="${trialDTO.collaborators}" >
       <display:column title="Collaborator" property="name"  headerClass="sortable"/>
       <display:column title="Functional Role" property="functionalRole"  headerClass="sortable"/>
        </display:table>
      </div>  
     </c:if>
      <c:if test="${trialDTO.participatingSites != null && fn:length(trialDTO.participatingSites) > 0}">
        <div class="box">
         <h3>Participating sites </h3>  
         <display:table class="data" sort="list"  uid="row"  name="${trialDTO.participatingSites}" >
         <display:column title="Site" property="name"  headerClass="sortable"/>
         <display:column title="Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
         <display:column title="Date" property="recruitmentStatusDate"  headerClass="sortable"/>
         <display:column title="Program Code" property="programCode"  headerClass="sortable"/>
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
        </div>
        <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li><a href="#"                
                    class="btn" onclick="editTrial();"><span class="btn_img"> <span class="edit">Edit</span></span></a></li>
               <li><a href="#"                
                    class="btn" onclick="submitTrial();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
               <li><a href="#"                
                    class="btn" onclick="printProtocol();"><span class="btn_img"><span class="print">Print</span></span></a></li>          
            </ul>   
        </del>
        </div>
    </s:form>
   </div>
   </div>
 </body>
 </html>