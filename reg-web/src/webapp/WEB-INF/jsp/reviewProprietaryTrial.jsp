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
    document.forms[0].action="submitProprietaryTrialcreate.action";
    document.forms[0].submit();
    showPopWin('${amendProtocol}', 600, 200, '', 'Submit Register Trial');    
}
function editTrial() {
        document.forms[0].action = "submitProprietaryTrialedit.action";
        document.forms[0].submit();
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
    <c:if test="${requestScope.protocolId != null}">
        <div class="confirm_msg">
          <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.protocolId}</strong>
        </div>
     </c:if>          
   <div id="contentprint">        
    <table class="form">
         <tr>
            <th colspan="2"><fmt:message key="submit.proprietary.trial.trialIdentification"/></th>
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
                <label for="Lead Organization Trial Identifier">
                    <fmt:message key="view.trial.leadOrgTrialIdentifier"/>                
                </label>
            </td>
            <td class="value">
                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/> 
            </td>
          </tr>
          <tr>     
            <td scope="row" class="label">
                <label for="Participating Organization Trial Identifier">
                    <fmt:message key="submit.proprietary.trial.siteOrganization"/>                
                </label>
            </td>
            <td class="value">
                <c:out value="${trialDTO.siteOrganizationName}"/> 
            </td>
          </tr>
          <tr>     
            <td scope="row" class="label">
                <label for="Local Trial Identifier">
                    <fmt:message key="submit.proprietary.trial.siteidentifier"/>                
                </label>
            </td>
            <td class="value">
                <c:out value="${trialDTO.localSiteIdentifier}"/> 
            </td>
          </tr>
          <c:if test="${fn:trim(trialDTO.nctIdentifier) != ''}">
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
           <tr>     
            <td scope="row" class="label">
                <label for="Principal Investigator">
                <fmt:message key="view.trial.principalInvestigator"/>                
                </label>
              </td>
             <td class="value">
                <c:out value="${trialDTO.sitePiName}"/>
                </td>
       </tr>
       <tr>
            <td colspan="2" class="space">&nbsp;</td>
       </tr> 
       <tr>
                <td colspan="2" class="space">&nbsp;</td>
       </tr>
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
        <c:if test="${fn:trim(trialDTO.siteProgramCodeText) != ''}">   
             <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">             
                <tr>
                    <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
                </tr>
            </c:if>
           <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <c:out value="${trialDTO.siteProgramCodeText}"/>
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
            <c:out value="${trialDTO.siteStatusCode}"/>
         </td>
      </tr> 
      <tr>     
        <td scope="row" class="label">
          <label for="Current Trial Status Date">
              <fmt:message key="view.trial.currentTrialStatusDate"/>                
          </label>
         </td>
         <td class="value">
           <c:out value="${trialDTO.siteStatusDate}"/>
           </td>
      </tr>
      <c:if test="${fn:trim(trialDTO.dateOpenedforAccrual) != ''}"> 
      <tr>     
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="submit.proprietary.trial.dateOpenedforAccrual"/>                
              </label>
          </td>
          <td class="value">
               <c:out value="${trialDTO.dateOpenedforAccrual}"/>
          </td>
       </tr> 
       </c:if>
       <c:if test="${fn:trim(trialDTO.dateClosedforAccrual) != ''}">
       <tr>     
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="submit.proprietary.trial.dateClosedforAccrual"/>                
            </label>
        </td>
        <td class="value">
             <c:out value="${trialDTO.dateClosedforAccrual}"/> 
        </td>
      </tr> 
      </c:if>
     </table>
     <c:if test="${fn:length(trialDTO.indIdeDtos) > 0}">
        <div class="box">                                   
        <h3>FDA IND/IDE Information for applicable trials</h3>  
        <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
        </div>
     </c:if>
     <c:if test="${trialDTO.fundingDtos != null}">  
        <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
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
          <c:if test="${requestScope.protocolId == null}">
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
        </c:if>
    </s:form>
   </div>
   </div>
 </body>
 </html>