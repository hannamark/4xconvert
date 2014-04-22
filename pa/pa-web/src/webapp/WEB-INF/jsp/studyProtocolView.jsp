<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="studyProtocol.view.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/tooltip.js'/>"></script>
        <script type="text/javascript" language="javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
            }
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            function handleAction(action) {
                var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
                var form = document.forms[0];
                if ((action == 'adminCheckIn') || (action == 'scientificCheckIn') || (action == 'adminAndScientificCheckIn')){
                    var bs=new Array(64).join(' ');
                    var comment=prompt(bs+"Enter check-in comment:"+bs,"");
                    if (comment==null){
                        return;
                    }
                    form.elements["checkInReason"].value = comment.substr(0,200);
                }
                form.action="studyProtocol" + action + ".action?studyProtocolId=" + studyProtocolId;
                form.submit();
            }
        </script>
        <style type="text/css">
            
            .labelDupe {
                font-weight: bold;
                font-size: 92%;
                color: #333333;
        }
</style>
    </head>
    <body>
        <c:set var="topic" scope="request" value="trialdetails"/>
        <c:set scope="request" var="suAbs" value="${sessionScope.isSuAbstractor==true}"></c:set>
        <h1>Trial Identification</h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <s:form>
                <s:token/>
                <s:hidden name="checkInReason"/>
                <s:actionerror/>
                <h2>Trial Identification</h2>
                <table class="form" cellspacing="10" cellpadding="10">
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe" width="1%">
                                                
                                <fmt:message key="studyProtocol.studyProtocolType"/>
                                                      
                        </td>
                        <td nowrap="nowrap" >
                            <c:out value="${trialSummary.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
                        </td>
                    </tr>   
                    <c:if test="${trialSummary.studyProtocolType=='NonInterventionalStudyProtocol'}">
	                    <tr>
	                        <td nowrap="nowrap" scope="row" class="labelDupe">                            
	                                <fmt:message key="studyProtocol.nonIntTrialType"/>
	                                                          
	                        </td>
	                        <td nowrap="nowrap" >
	                            <c:out value="${trialSummary.studySubtypeCode}"/>
	                        </td>
	                    </tr>
                    </c:if>             
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                           
                                <fmt:message key="studyProtocol.nciIdentifier"/>
                            
                        </td>
                        <td nowrap="nowrap">
                            <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
                        </td>
                    </tr>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <td nowrap="nowrap" scope="row" class="labelDupe">
                                 ClinicalTrials.gov XML required?
                            </td>
                            <td nowrap="nowrap">
                                <pa:displayBoolean value="${sessionScope.trialSummary.ctgovXmlRequiredIndicator}"/>
                            </td>
                        </tr>
                    </c:if>       
                    <tr><td>&nbsp;</td></tr>
                    <c:forEach var="identifier" items="${studyIdentifiers}">
                        <c:if test="${not empty identifier.value}">
		                    <tr>
		                        <td nowrap="nowrap" scope="row" class="labelDupe" id="td_${identifier.type.name}_name">		                           
		                                <c:out value="${identifier.type.code}"/>
		                        </td>
		                        <td nowrap="nowrap" id="td_${identifier.type.name}_value">
		                            <c:out value="${identifier.value}"/>
		                        </td>
		                    </tr>                        
                        </c:if>
                    </c:forEach>                                 
                     <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                            
                                <fmt:message key="studyProtocol.proprietaryTrial"/>
                            
                        </td>
                        <td nowrap="nowrap" >
                            <pa:displayBoolean value="${sessionScope.trialSummary.proprietaryTrial}"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                            <fmt:message key="studyProtocol.lastVerificationDate"/>       
                        </td>
                        <td nowrap="nowrap">
                            <c:out value="${sessionScope.trialSummary.recordVerificationDate}"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">                          
                                <fmt:message key="studyProtocol.officialTitle"/>   
                        </td>
                        <td>
                             <c:out value="${sessionScope.trialSummary.officialTitle }"/>
                        </td>
                    </tr>
                </table>
                <s:if test="%{!checkoutCommands.isEmpty()}">
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <pa:studyUniqueToken/>
                                <s:set name="checkoutCommands" scope="page" value="%{checkoutCommands}" /> 
                                <c:forEach items="${checkoutCommands}" var="command" varStatus="vstat">
                                    <c:if test="${vstat.index==2}">
                                        <!-- Force the 3rd button onto a separate row: https://tracker.nci.nih.gov/browse/PO-3966 -->
                                        </ul></del><br/><del class="btnwrapper"><ul class="btnrow">                                        
                                    </c:if>
                                    <li>
                                        <a href="javascript:void(0)" class="btn" onclick="handleAction('${command}')">
                                            <span class="btn_img"><span class="save"><fmt:message key="studyProtocolView.button.${command}" /></span></span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </del>
                    </div>
                </s:if>
                <c:if test="${pageContext.request.method=='GET' && fn:contains(header['Referer'],'/studyProtocolquery.action')}">
	                <div class="actionsrow" style="border: none;">
	                  <del class="btnwrapper">
	                      <ul class="btnrow">
	                          <li><a href="javascript:void(0)" class="btn" onclick="window.history.back();"><span class="btn_img"><span class="back">Back to Search Results</span></span></a></li>
	                      </ul>
	                  </del>
	                 </div>     
                 </c:if>  
                 
                 <div class="line"></div>
                 <table class="form">
                         <tr>
                             <td scope="row" class="label"><label for="assignedTo">Assigned To</label></td>
                             <td> 
                                 <s:set name="abstractorsList"
                                     value="@gov.nih.nci.pa.service.util.CSMUserService@getInstance().abstractors" />
                                 <s:select id="assignedTo" name="assignedTo"
                                     list="#abstractorsList" headerKey="" headerValue="Unassigned"
                                     value="#session.trialSummary.assignedUserId" cssStyle="width:206px" />                                        
                             </td>
                         </tr>
                         <tr>
                             <td scope="row" class="label"><label for="newProcessingPriority">Processing Priority</label></td>
                             <td> 
                                 <s:select id="newProcessingPriority"
                                     name="newProcessingPriority"
                                     list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"                                               
                                     value="#session.trialSummary.processingPriority" cssStyle="width:206px" />    
                                 
                                 <c:if test="${!suAbs}">
                                       <script type="text/javascript">
                                             Event.observe(window, "load", function () {
                                                 $('assignedTo').disabled = true;
                                                 $('newProcessingPriority').disabled = true;
                                             });
                                       </script>
                                  </c:if>                                        
                                                                     
                             </td>
                         </tr>  
                         <tr>
                             <td scope="row" class="label" colspan="2"><label for="processingComments">Trial Processing Comments</label></td>
                         </tr>
                         <tr>
                             <td colspan="2">
                                 <s:textarea id="processingComments" name="processingComments"    
                                     maxlength="4000" cssClass="charcounter"                                        
                                     cssStyle="width: 100%;" rows="5">
                                     <s:param name="value">
                                         <s:property value="#session.trialSummary.processingComments"/>
                                     </s:param>                                            
                                 </s:textarea> 
                             </td>                                        
                         </tr>
                         <tr>
                             <td colspan="2" align="center">
                                 <div class="actionsrow">
                                     <del class="btnwrapper">
                                         <ul class="btnrow">
                                             <li><s:a href="javascript:void(0)" cssClass="btn"
                                                     onclick="handleAction('save');">
                                                     <span class="btn_img"><span class="search">Save</span></span>
                                                 </s:a></li>
                                         </ul>
                                     </del>
                                 </div>                                        
                             </td>
                         </tr>  
                 </table>
                          
            </s:form>
        </div>
    </body>
</html>
