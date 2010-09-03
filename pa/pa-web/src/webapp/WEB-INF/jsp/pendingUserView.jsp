<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<c:url value="/protected/ajaxcollectRejectReason.action" var="collectReason"/>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();         
}
var rejectReason;
function handleAction(action){
     var userId = document.getElementById("user_id").value;
     if(action == 'reject') {
       collectRejectReason();
     } else {
           document.forms[0].action="inboxProcessingprocessUserRole.action?action="+action+"&id="+userId;
           document.forms[0].submit();
     }
}

function collectRejectReason(){
    showPopWin('${collectReason}', 900, 400, collectReason, 'Collect Reason')
}
function collectReason() {
    var userId = document.getElementById("user_id").value;
    document.forms[0].action="inboxProcessingprocessUserRole.action?action=reject&id="+userId+"&rejectReason="
       +rejectReason;
    document.forms[0].submit();
}
function setRejectReason(reason){
	rejectReason = reason;
	rejectReason = rejectReason.replace(/&apos;/g,"'");
}
</SCRIPT>
<body>
    <s:form>
        <table class="form">
            <input type="hidden" name="user_id" id="user_id" value="${requestScope.user.id}"/>
            <tr><td scope="row" class="label">
               <label for="first Name"> <fmt:message key="pending.userFirstName"/></label></td>
               <td><c:out value ="${requestScope.user.firstName}"/></td></tr>
            <tr><td scope="row" class="label">
               <label for="first Name"> <fmt:message key="pending.userLastName"/></label></td>
               <td><c:out value ="${requestScope.user.lastName}"/></td></tr>
            <tr><td scope="row" class="label">
               <label for="first Name"> <fmt:message key="pending.affiliateOrg"/></label></td>
               <td><c:out value ="${requestScope.user.affiliateOrg}"/></td></tr>
            </table>
            <c:if test= "${requestScope.user.affiliateOrg != 'No Org Found'}">
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">         
                            <li>
                                <s:a href="#" cssClass="btn" onclick="handleAction('accept')"><span class="btn_img"><span class="save">Accept</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="handleAction('reject')"><span class="btn_img"><span class="cancel">Reject</span></span></s:a>  
                            </li>
                        </ul>   
                    </del>
                </div>      
            </c:if>
    </s:form>
</body>
</html>