<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="participatingOrganizations.subtitle" /></title>
        <s:head/>
        <script type="text/javascript" src="scripts/js/prototype.js"></script>
        <script type="text/javascript" src="scripts/js/scriptaculous.js"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
        <script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
        <c:url value="/protected/participatingOrganizationshistoryPopup.action" var="lookupUrlstatusHistory" />

        <script language="javascript" type="text/javascript">
            addCalendar("Cal1", "Select Date", "recStatusDate", "proprietarySite");
            addCalendar("Cal2", "Select Date", "dateOpenedForAccrual", "proprietarySite");
            addCalendar("Cal3", "Select Date", "dateClosedForAccrual", "proprietarySite");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
            var orgid;

            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();        
            }

            function save() {
                document.proprietarySite.action="participatingOrganizationsproprietarySave.action";
                document.proprietarySite.submit();    
            }    

            function lookup() {
                showPopup('${lookupUrl}', null, 'Organization');
            }   

            function lookupperson() {
                showPopup('${lookupPersonsUrl}', null, 'Persons');
            }   

            function lookupStatusHistory() {
                showPopup('${lookupUrlstatusHistory}?studySiteId='+$('studySiteIdentifier').value, null, 'Status History');
            }   

            function loadDiv(orgid) {
                 var url = '/pa/protected/ajaxptpOrgdisplayOrg.action';
                 var params = { orgId: orgid };
                 var div = document.getElementById('orgDetailsDiv');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, params);
                 return false;      
            }   

            function loadPersDiv(persid, rolecode, func) {
                if (func != "add"){
                    input_box=confirm("Click OK to un-link the Investigator from the Study.  Cancel to Abort.");
                    if (input_box!=true){
                        return;
                    }
                }
                var url = '/pa/protected/ajaxptpOrgdisplayPerson.action';
                var params = { perId: persid };
                var div = document.getElementById('saveAndShowContacts');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            // do not remove these two callback methods!
            function setpersid(persIdentifier, name, email, phone) {
                persid = persIdentifier;
                selectedName = name;
                contactMail = email;
                contactPhone = phone;
            }

            function setorgid(orgid) {
                orgid = orgid;
            }

        </script>
    </head>
    <body>
        <h1><fmt:message key="participatingOrganizations.subtitle" /></h1>
        <c:set var="topic" scope="request" value="abstractsite"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <s:form name="proprietarySite">
            <s:token/>
            <pa:studyUniqueToken/>
            <div class="box">
                <pa:sucessMessage/>
                <pa:failureMessage/>
                 <s:if test="hasActionErrors()">
                    <div class="error_msg"><s:actionerror/></div>
                 </s:if>
                <h2><fmt:message key="participatingOrganizations.subtitle" /></h2>
                <table class="form">
                    <s:hidden name="proprietaryTrialIndicator" id="proprietaryTrialIndicator"></s:hidden>
                    <s:hidden name="studySiteIdentifier" id ="studySiteIdentifier"></s:hidden>
                    <s:hidden name="cbValue" />
                    <s:hidden name="currentAction" id="currentAction"/>
                    <tr>
                      <td colspan="2">
                        <div id="orgDetailsDiv">
                            <%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp" %>
                        </div>
                      </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <s:label for="local Trial Identifier"><fmt:message key="proprietary.siteidentifier"/></s:label>
                            <span class="required">*</span>
                        </td>
                        <td>
                            <s:textfield name="siteLocalTrialIdentifier" id="siteLocalTrialIdentifier" maxlength="20" size="200" cssStyle="width: 200px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>siteLocalTrialIdentifier</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="submitTrial_poLeadPiFullName"><fmt:message key="proprietary.siteInvestigator"/>
                            <span class="required">*</span></label> 
                        </td>
                        <td scope="row">
                            <div id="saveAndShowContacts">
                                <%@ include file = "/WEB-INF/jsp/nodecorate/sitePrincipalInvestigator.jsp" %>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="siteProgramCode"><fmt:message key="proprietary.sitePrgCode"/></label>
                        </td>
                        <td class="value">
                            <s:textfield name="siteProgramCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>siteProgramCodeText</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>    
                    <tr>
                        <td scope="row" class="label">
                            <s:label for="srs">Site Recruitment Status:</s:label><span class="required">*</span>
                        </td>
                        <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                        <td>
                            <table>
                                <tr>
                                    <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--"
                                        name="recStatus" list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                                        <span class="formErrorMsg"> 
                                                  <s:fielderror>
                                                  <s:param>recStatus</s:param>
                                                  </s:fielderror>                            
                                        </span>
                                    </td>
                                    <td>
                                        <ul class="btnrow">         
                                            <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupStatusHistory()"><span class="btn_img"><span class="history">History</span></span></a></li>
                                        </ul>
                                    </td>
                                </tr>
                             </table>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <s:label for="srsd">Site Recruitment Status Date:</s:label><span class="required">*</span>
                        </td>
                        <td class="value" colspan="2">
                            <s:textfield name="recStatusDate" maxlength="10" size="10" readonly="true"  cssStyle="text-align:left;width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                                <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>recStatusDate</s:param>
                                </s:fielderror>
                            </span>
                         </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="startDate"><fmt:message key="proprietary.trial.dateOpenedforAccrual" /></label>
                        </td>
                        <td class="value">
                            <s:textfield name="dateOpenedForAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal2')">
                                <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy) 
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>dateOpenedForAccrual</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="completionDate"><fmt:message key="proprietary.Site.dateClosedforAccrual" /></label>
                        </td>
                        <td class="value">
                            <s:textfield name="dateClosedForAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal3')">
                                <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="info"><fmt:message key="error.proprietary.dateOpenReq" /></span>  
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>dateClosedForAccrual</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li><a href="javascript:void(0)" class="btn" onclick="save();"><span class="btn_img"><span class="add" >Save </span></span></a></li>
                            <li><a href="participatingOrganizations.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Cancel</span></span></a></li>
                        </ul>   
                    </del>
                </div>
            </div>
        </s:form>
    </body>
</html>    