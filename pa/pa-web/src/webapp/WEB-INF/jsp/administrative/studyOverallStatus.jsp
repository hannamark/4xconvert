<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="trialStatus.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
        <c:url value="/protected/studyOverallStatusHistorypopup.action" var="lookupUrl" />
        
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "statusDate", "studyoverallstatus");
            addCalendar("Cal2", "Select Date", "startDate", "studyoverallstatus");
            addCalendar("Cal3", "Select Date", "primaryCompletionDate", "studyoverallstatus");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            
            function lookup() {
                showPopup('${lookupUrl}', null, 'Status History');
            } 
            
            function statusChange() {
                var newStatus = $('currentTrialStatus').value;
                if((newStatus == "Administratively Complete")
                   || (newStatus == "Withdrawn")
                   || (newStatus == "Temporarily Closed to Accrual")
                   || (newStatus == "Temporarily Closed to Accrual and Intervention")) {
                  $('statusReason').disabled=false;
                  $('statusReason').readonly=false;
                } else {
                  $('statusReason').disabled=true;
                  $('statusReason').readonly=true;
                }
            }
            
            function handleAction() {
                document.forms[0].action="studyOverallStatusupdate.action";
                document.forms[0].submit();
            }
            
            function displayTrialStatusDefinition(selectBoxId) {
                $('allTrialStatusDefinitions').childElements().invoke('hide');
                var selectedValue = $(selectBoxId).value;
                $(selectedValue).show();
            }
            
            document.observe("dom:loaded", function() {
                displayTrialStatusDefinition('currentTrialStatus');
                });
        </script>
    </head>
    <body>
        <h1><fmt:message key="trialStatus.title" /></h1>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <c:set var="topic" scope="request" value="reviewstatus"/>
        </c:if>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
            <c:set var="topic" scope="request" value="abstractstatus"/>
        </c:if>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <s:form name="studyoverallstatus">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:if test="hasActionErrors()">
                    <div class="error_msg"><s:actionerror/></div>
                </s:if>
                <h2><fmt:message key="trialStatus.title" /></h2>
                <table class="form">
                    <tr>
                        <td width="0">
                            <table>
                                <tr>
                                    <pa:valueRow cellOnly="true" labelFor="currentTrialStatus" labelKey="trialStatus.current.trial.status" required="true">
                                        <s:set name="currentTrialStatusValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                                        <s:select onchange="statusChange();displayTrialStatusDefinition('currentTrialStatus');" 
                                                  onfocus="statusChange()" id="currentTrialStatus" name="currentTrialStatus" list="#currentTrialStatusValues" />
                                    </pa:valueRow>
                                    <td>
                                        <ul class="btnrow">            
                                            <li style="padding-left:0">
                                                <a href="javascript:void(0)" class="btn" onclick="lookup()"><span class="btn_img"><span class="history">History</span></span></a>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="info"><%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp" %></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <pa:valueRow labelFor="statusDate" labelKey="trialStatus.current.trial.status.date" required="true">
                                    <s:textfield name="statusDate" id="statusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal1')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a>
                                </pa:valueRow>
                                <tr> 
                                    <td>&nbsp;</td>
                                    <td class="info" colspan="2">Administratively Complete, Withdrawn, and Temporarily Closed statuses only</td>
                                </tr>
                                <pa:valueRow labelFor="statusReasonLabel" labelKey="trialStatus.current.trial.status.reason">
                                    <s:textarea name="statusReason" id="statusReason" rows="3" cssStyle="width:280px;" maxlength="160" cssClass="charcounter"/>
                                </pa:valueRow>
                                <pa:spaceRow/>
                                <pa:valueRow labelFor="startDate" labelKey="trialStatus.trial.start.date" required="true">
                                    <s:textfield name="startDate" id="startDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal2')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="startDateType" id="startDateType" list="dateTypeList" />
                                </pa:valueRow>
                                <pa:valueRow labelFor="primaryCompletionDate" labelKey="trialStatus.primary.completion.date" required="true">
                                    <s:textfield name="primaryCompletionDate" id="primaryCompletionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal3')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="primaryCompletionDateType" id="primaryCompletionDateType" list="dateTypeList" />
                                </pa:valueRow>
                                <pa:valueRow labelFor="completionDate" labelKey="trialStatus.completionDate">
                                    <s:textfield name="completionDate" id="completionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal3')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="completionDateType" id="completionDateType" list="dateTypeList" />
                                </pa:valueRow>
								<pa:valueRow labelKey="blank.label">
								    <span class="info">Please refer to the <a href='<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getStateTransitionDiagramUrl()" />' target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
								</pa:valueRow>
                            </table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                </li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>   
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>