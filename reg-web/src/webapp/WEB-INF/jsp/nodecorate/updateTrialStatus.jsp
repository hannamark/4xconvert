<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <link href="${pageContext.request.contextPath}/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
        <link href="${pageContext.request.contextPath}/styles/subModal.css" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/showhide.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
            addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "updateTrialStatusForm");
            addCalendar("Cal2", "Select Date", "trialDTO.startDate", "updateTrialStatusForm");
            addCalendar("Cal3", "Select Date", "trialDTO.primaryCompletionDate", "updateTrialStatusForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
            
            function updateTrialStatus() {
                $('updateTrialStatusForm').submit();
                <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocol"/>
                showPopWin('${reviewProtocol}', 600, 200, '', 'Update Trial Status');
            }
        </script>
    </head>
    <body>
        <reg-web:failureMessage/>
        <s:if test="hasActionMessages()">
            <div class="confirm_msg"><s:actionmessage/></div>
        </s:if>
        <s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
        </s:if>
        <s:else>
            <s:actionerror/>
        </s:else>
        <s:form name="updateTrialStatusForm" id="updateTrialStatusForm" action="updateTrialStatuspopupupdate">
            <s:hidden name="trialDTO.identifier"/>
            <s:hidden name="studyProtocolId"/>
            <table class="form">
                <%@ include file="/WEB-INF/jsp/nodecorate/trialStatus.jsp" %>
            </table>    
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li>
                            <s:a href="#" cssClass="btn" onclick="updateTrialStatus()" ><span class="btn_img"><span class="save">Save</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();" id="search_organization_close_btn">
                                <span class="btn_img"><span class="close">Close</span>
                                </span>
                            </s:a>
                       </li>
                    </ul>
                </del>
            </div>
        </s:form>
    </body>
</html>