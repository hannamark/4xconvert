<!DOCTYPE html PUBLIC  
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="nciSpecificInformation.title"/></title>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
        <script type="text/javascript" language="javascript">    
            
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();        
            }
            
            function handleAction() {
                 document.nciSpecificInformationupdate.action="nciSpecificInformationupdate.action";
                 document.nciSpecificInformationupdate.submit();     
            }
             
            function lookup() {
                showPopup('${lookupUrl}', null, 'Organization');
            }    
            
            function loadDiv(orgid) {
                var url = '/pa/protected/ajaxorgdisplayOrg.action';
                var params = { orgId: orgid };
                var div = document.getElementById('loadOrgField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }
            
            // do not remove these two callback methods!
            function setpersid(persid) {
            }
            
            function setorgid(orgid) {
            }
                
        </script>
    </head>
    <body>
        <h1><fmt:message key="nciSpecificInformation.title" /></h1>
        <c:set var="topic" scope="request" value="abstractnci"/>
        <c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">  
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form action="nciSpecificInformationupdate">
                <s:token/>
                <pa:studyUniqueToken/>
                <h2><fmt:message key="nciSpecificInformation.title" /></h2>
                <table class="form" >
                    
                        <tr>
                            <td scope="row" class="label">
                                <label for="accrualReportingMethodCode"><fmt:message key="studyProtocol.accrualReportingMethodCode"/><span class="required">${asterisk}</span></label>
                            </td>
                            <s:set name="accrualReportingMethodCodeValues" value="@gov.nih.nci.pa.enums.AccrualReportingMethodCode@getDisplayNames()" />
                            <td class="value">
                                <s:select id="accrualReportingMethodCode" headerKey="" headerValue="" 
                                   name="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
                                   list="#accrualReportingMethodCodeValues"  
                                   value="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
                                   cssStyle="width:206px" />
                                <span class="formErrorMsg"> 
                                      <s:fielderror>
                                          <s:param>nciSpecificInformationWebDTO.accrualReportingMethodCode</s:param>
                                      </s:fielderror>                            
                                </span>
                            </td>                           
                        </tr> 
                                  
                    <tr>
                        <td scope="row" class="label">
                            <label for="summary4TypeCode"><fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></label>
                        </td>
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                        <td class="value">
                            <s:select id="summary4TypeCode" headerKey="" headerValue="" 
                                      name="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                                      list="#summaryFourFundingCategoryCodeValues"  
                                      value="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                                      cssStyle="width:206px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.summaryFourFundingCategoryCode</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>                                  
                    </tr>      
                    <tr>
                        <td scope="row" class="label">
                            <label for="summary4FundingSponsor">Summary 4 Funding Sponsor/Source:</label>
                        </td>
                        <td class="value">
                            <div id="loadOrgField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/orgField.jsp" %>
                            </div>        
                        </td>
                        
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                        </td>
                        <td class="value">
                            <s:textfield id="summary4ProgramCode" name="nciSpecificInformationWebDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.programCodeText</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="ctroOverride"><fmt:message key="studyProtocol.ctroOverride"/></label>
                        </td>
                        <td class="value">
                            <s:checkbox id="ctroOverride" name="nciSpecificInformationWebDTO.ctroOverride" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.ctroOverride</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>                    
                </table> 
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>    
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>