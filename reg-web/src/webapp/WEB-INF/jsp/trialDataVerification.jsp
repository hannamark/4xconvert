<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trial.data.verification.page.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
     <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
     <script type="text/javascript">
        function addAction(url){
                if (confirm("<fmt:message key='trial.data.verification.Confirm' />")) {
                    var pid = getUrlVars()["studyProtocolId"];
                    document.forms[0].setAttribute("action", url+"?studyProtocolId="+pid);
                    document.forms[0].submit();    
                }
            }
            
            function getUrlVars() {
                var vars = {};
                var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
                    vars[key] = value;
                });
                return vars;
            }
            
            function cancelAction(url){                
                    document.forms[0].setAttribute("action", url);
                    document.forms[0].submit();      
            }
            </script>
</head>
    <body>
        <h1><fmt:message key="trial.data.verification.title" /></h1> 
        <c:set var="topic" scope="request" value="dataverification"/>
        <div class="box">
                <reg-web:sucessMessage/>
                <table class="form">
                <s:token/>
                
                <reg-web:valueRow labelKey="view.trial.identifier" strong="true" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.assignedIdentifier}"/>
                            </c:if>
                </reg-web:valueRow>
                 <reg-web:valueRow labelKey="view.trial.nctNumber" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.nctIdentifier }"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.nctIdentifier }"/>
                                </c:if>
                 </reg-web:valueRow>
                <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.leadOrgTrialIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.leadOrgTrialIdentifier}"/>
                            </c:if>
                </reg-web:valueRow>

                 <reg-web:valueRow labelKey="view.trial.title" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.officialTitle.value}"/>
                            <c:if test="${not empty trialSummary.studyAlternateTitles}">
                               <a href="javascript:void(0)" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                            </c:if>
                 </reg-web:valueRow>
                 
                </table>
               <table class="form">
                    
                    <tr>
                        <td colspan="2">
                            <s:set name="webDTOList" value="webDTOList" scope="request"/>
               <display:table name="webDTOList" htmlId="webDTOList" id="row" class="data" sort="list" pagesize="200" requestURI="trialDataVerification.action">
                      <display:column escapeXml="true" property="updatedDate" sortable="false" titleKey="trial.data.verification.date" headerClass="sortable"/>
                      <display:column escapeXml="true" property="verificationMethod" sortable="false" titleKey="trial.data.verification.verificationMethod" headerClass="sortable"/>
                      <display:column escapeXml="true" property="userLastUpdated" sortable="false" titleKey="trial.data.verification.verifiedBy" headerClass="sortable"/>        
               </display:table>
               </td>
                    </tr>
                </table>
               <br/>
               <br/>
               <h3>Add Data Verification Record</h3>
               <br/>
               <br/>
               <fmt:message key="trial.data.verification.review" />                     
               
           <div class="actionsrow">
              <del class="btnwrapper">
                  <ul class="btnrow">
                       <li>    
                           <s:url id="addUrl" namespace="/protected" action="trialDataVerificationAction" method="save"/>
                           <s:a cssClass="btn" href="javascript:void(0)" onclick="javascript:addAction('%{addUrl}');">
                                <span class="btn_img"><span class="add">Save Verification Record</span></span>
                           </s:a>
                           <s:url id="cancelUrl" namespace="/protected" action="searchTrial"/>
                           <s:a onclick="javascript:cancelAction('%{cancelUrl}');" href="javascript:void(0)" cssClass="btn">
                                <span class="btn_img"><span class="cancel">Cancel</span></span>
                           </s:a>
                       </li>
                   </ul>
                </del>
            </div>
        </div> 
    </body>
</html>
