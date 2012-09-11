<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <c:url value="/protected/popup.action" var="lookupUrl" />
    <%@ include file="/WEB-INF/jsp/nodecorate/tableTagParameters.jsp" %>
    <head>
        <s:if test="%{currentAction == 'create'}">
            <c:set var="topic" scope="request" value="subjects_adding"/> 
            <fmt:message key="patient.create.title" var="pageTitle" />
        </s:if>
        <s:elseif test="%{currentAction == 'update'}">
            <c:set var="topic" scope="request" value="subjects_update"/> 
            <fmt:message key="patient.update.title" var="pageTitle" />
        </s:elseif>
        <s:elseif test="%{currentAction == 'retrieve'}">
            <c:set var="topic" scope="request" value="subjects_intro"/> 
            <fmt:message key="patient.retrieve.title" var="pageTitle" />
        </s:elseif>
        <title>${pageTitle}</title>        
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
            addCalendar("Cal1", "Select Date", "patient.registrationDate", "detailForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");   
            
            var urlParameters = '<%=StringEscapeUtils.escapeJavaScript(urlParams)%>';
            function handleCancelAction() {
                submitForm("patients.action" + urlParameters);
            }
            
            function handleAddAction() {
                submitForm("patientsadd.action");
            }
            
            function handleEditAction() {
                submitForm("patientsedit.action" + urlParameters);
            }
            
            function submitForm(action) {
                var form = document.forms[0];
                form.action = action;
                form.submit();
            }
            
            function lookup(){
                showPopWin('${lookupUrl}', 900, 400, '', 'Disease');
            }
            
            function loadDiv(intid, type) {
                 var url = '/accrual/protected/ajaxpatientsgetDisplayDisease.action';
                 var params = { diseaseId: intid, dType: type };
                 var div = document.getElementById('loadDetails');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
                 var aj = callAjaxPost(div, url, params);
            }     
        </script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
        <h1>${pageTitle}</h1>
        <div class="box">
            <s:if test="hasActionErrors()">
                <div class="error_msg">
                    <s:actionerror />
                </div>
            </s:if>
            <s:form name="detailForm">
                <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                    <s:token/>
                </s:if>
                <s:hidden name = "patient.patientId" />
                <s:hidden name = "patient.studySubjectId" />
                <s:hidden name = "patient.studyProtocolId" />
                <s:hidden name = "patient.statusCode" />
                <s:hidden name = "patient.performedSubjectMilestoneId" />
                <s:hidden name = "patient.submissionTypeCode" />
                <s:hidden name = "patient.registrationGroupId" />
                <table class="form">
                    <tr>
                        <td class="label">
                            <label for="identifier">
                                <fmt:message key="patient.ID"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="identifier" name="patient.assignedIdentifier" maxlength="400" size="50" 
                                             cssStyle="width:98%;max-width:206px" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.assignedIdentifier" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr> 
                    <tr>
                        <td class="label">
                            <label for="birthDate">
                                <fmt:message key="patient.birthDate"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="birthDate" name="patient.birthDate" maxlength="400" size="50"
                                             cssStyle="width:98%;max-width:128px" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.birthDate" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="genderCode">
                                <fmt:message key="patient.gender"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="genderCodeValues" value="@gov.nih.nci.pa.enums.PatientGenderCode@getDisplayNames()" />
                                <s:select id ="genderCode" name="patient.genderCode" headerKey="" headerValue="--Select--"
                                          list="#genderCodeValues"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.genderCode" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="raceCode">
                                <fmt:message key="patient.race"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="raceCodeValues" value="@gov.nih.nci.pa.enums.PatientRaceCode@getDisplayMap()" />
                                <s:select id ="raceCode" name="patient.raceCode" multiple="true" size="7" list="#raceCodeValues" />
                                <span class="info">To select multiple races, select one race, and then press and hold the CTRL key as you select the other(s).</span>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:iterator id="races" value="patient.raceCode" >
                                    <s:set name="racerx" value="%{code}"/>
                                    <s:label name="races" cssStyle="font-weight:normal"/><br>
                                </s:iterator>
                            </s:elseif>
                        </td>
                    </tr>
                     <tr>
                        <td scope="row" class="label">
                            <label for="ethnicCode">
                                <fmt:message key="patient.ethnicity"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="ethnicCodeValues" value="@gov.nih.nci.pa.enums.PatientEthnicityCode@getDisplayMap()" />
                                <s:select id ="ethnicCode" name="patient.ethnicCode" headerKey="" headerValue="--Select--"
                                          list="#ethnicCodeValues"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.ethnicCode" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="countryIdentifier">
                                <fmt:message key="patient.country"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:select id ="countryIdentifier" name="patient.countryIdentifier" headerValue="-Select-" headerKey=""
                                          list="listOfCountries"  listKey="id" listValue="name"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.countryName" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="zip">
                                <fmt:message key="patient.zipCode"/>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="zip" name="patient.zip" maxlength="400" size="50" cssStyle="width:98%;max-width:96px" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.zip" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="registrationDate">
                                <fmt:message key="patient.registrationDate"/>:
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="registrationDate" name="patient.registrationDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                <a href="javascript:showCal('Cal1')"><img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.registrationDate" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="paymentMethodCode">
                                <fmt:message key="patient.methodOfPayment"/>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="paymentCodeValues" value="@gov.nih.nci.pa.enums.PaymentMethodCode@getDisplayNames()" />
                                <s:select id ="paymentMethodCode" name="patient.paymentMethodCode" headerKey="" headerValue="--Select--"
                                          list="#paymentCodeValues"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.paymentMethodCode" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}"><div class="padme5"></div></s:if>
                            <label for="disease"><fmt:message key="patient.disease"/><span class="required">*</span></label>
                        </td>
                        <td class="value">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <div id="loadDetails" >
                                     <%@ include file="/WEB-INF/jsp/nodecorate/displayDisease.jsp" %>
                                </div>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.diseasePreferredName" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="organizationName">
                                <fmt:message key="patient.organizationName"/>:
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value" colspan="4">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:select id="organizationName" name="patient.studySiteId" list="listOfStudySites" headerKey="" 
                                          listKey="ssIi" listValue="orgName" headerValue="--Select--"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:label name="patient.organizationName" cssStyle="font-weight:normal"/>
                            </s:elseif>
                        </td>
                    </tr>
                    <s:if test="%{currentAction == 'retrieve'}">
                    <tr>
                        <td class="label">
                            <label for="userCreated">
                                <fmt:message key="patient.userCreated"/>:
                            </label>
                        </td>
                        <td class="value" colspan="4">                           
                                <s:label name="patient.userCreated" cssStyle="font-weight:normal"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="dateLastUpdated">
                                <fmt:message key="patient.lastUpdateDateTime"/>:
                            </label>
                        </td>
                        <td class="value" colspan="4">                           
                                <s:label name="patient.dateLastUpdated" cssStyle="font-weight:normal"/>
                        </td>
                    </tr>      
                    </s:if>
                </table>
            </s:form>
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li>
                            <s:if test="%{currentAction == 'create'}">
                                <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="back">Back</span></span></s:a>
                            </s:elseif>
                            <s:elseif test="%{currentAction == 'update'}">
                                <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </s:elseif>
                        </li>
                    </ul>
                </del>
            </div>
        </div>
    </body>
</html>
