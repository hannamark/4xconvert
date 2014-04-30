<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <script type="text/javascript" language="javascript">
            
            function updateTrialStatus() {
                $('updateTrialStatusForm').submit();
                <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocol"/>
                showPopWin('${reviewProtocol}', 600, 200, '', 'Update Trial Status');
            }
            
            function doSave() {
            	updateTrialStatus();
            }            
       
        </script>
    </head>
    <body>
        <reg-web:failureMessage/>
        <reg-web:actionErrorsAndMessages />
        <s:form name="updateTrialStatusForm" id="updateTrialStatusForm" action="updateTrialStatuspopupupdate"  cssClass="form-horizontal" role="form">
            <s:token/>
            <s:hidden name="trialDTO.identifier"/>
            <s:hidden name="studyProtocolId"/>
            <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>
            <reg-web:saveAndCloseBtn/>        
        </s:form>
    </body>
</html>