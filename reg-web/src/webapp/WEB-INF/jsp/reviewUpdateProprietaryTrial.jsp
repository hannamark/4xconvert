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
		document.forms[0].action="updateProprietaryTrialupdate.action";
		document.forms[0].submit();
		showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');	
	} 
}
function editTrial() {
	var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
    if (assignedId != '') {
        document.forms[0].action="updateProprietaryTrialedit.action";
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
	<s:hidden name="pageFrom" id="pageFrom"/>         
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
          <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
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
      </table>
      <c:if test="${trialDTO.participatingSitesList != null && fn:length(trialDTO.participatingSitesList) > 0}">
        <div class="box">
         <h3>Participating sites </h3>  
         <display:table class="data" sort="list"  uid="row"  name="${trialDTO.participatingSitesList}" >
	         <display:column title="Organization Name" property="name"  headerClass="sortable"/>
	         <display:column title="Site Principal Investigator" property="investigator"  headerClass="sortable"/>
	         <display:column title="Local Trial<br/> Identifier" property="siteLocalTrialIdentifier"  headerClass="sortable"/>
	         <display:column title="Program Code" property="programCode"  headerClass="sortable"/>
	         <display:column title="Current Site<br/> Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
	         <display:column title="Current Site<br/> Recruitment Status Date" property="recruitmentStatusDate"  headerClass="sortable"/>
	         <display:column title="Date Opened <br/>for Accrual" property="dateOpenedforAccrual"  headerClass="sortable"/>
	         <display:column title="Date Closed <br/>for Accrual" property="dateClosedforAccrual"  headerClass="sortable"/>
         </display:table>
       </div>  
    </c:if>
    <c:if test="${fn:length(trialDTO.docDtos) >0}">          
            <div class="box">
               <display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">    
                <display:column titleKey="search.trial.view.documentTypeCode" property="proprietaryTypeCode"   sortable="true" headerClass="sortable"/>
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