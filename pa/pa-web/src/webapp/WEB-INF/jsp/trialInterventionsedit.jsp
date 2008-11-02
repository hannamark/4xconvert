<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message
    key="participatingOrganizations.collaborators.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<script type="text/javascript"
    src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript"
    src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/popupInt.action" var="lookupUrl" />


<script type="text/javascript">
function interventionAdd(){
     document.intervention.action="trialInterventionsadd.action";
     document.intervention.submit();     
}
function interventionUpdate(){
     document.intervention.action="trialInterventionsupdate.action";
     document.intervention.submit();     
}
</script>

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
    function lookup(){
        if(document.intervention.interventionType.value.length < 1){
           alert("Please select an intervention type before doing look up.");
        } else {
           var intType = document.intervention.interventionType.value;
           showPopWin('${lookupUrl}?interventionType='+intType, 1050, 400, '', intType+' Intervention');
        }
    }   
    function loadDiv(intid){
         var url = '/pa/protected/ajaxptpInterventiondisplay.action?interventionId='+intid;
         var div = document.getElementById('loadOrgDetails');   
         div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
         });
         return false;
    }   
</SCRIPT>
<body>
<!-- <div id="contentwide"> -->
<h1><fmt:message key="interventions.main.title" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if>
<h2><fmt:message
    key="interventions.add.title" /></h2>



<table class="form">
    <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr>
        <td colspan="2"><!--Facility-->
        <h3>Intervention</h3>
        <s:form name="intervention">
            <div id="loadOrgDetails">
            <div id="orgDetailsDiv">
                <%@ include file="/WEB-INF/jsp/nodecorate/selectedInterventionDetails.jsp"%>
            </div>
            </div>
        </s:form>
        <div class="actionsrow"><del class="btnwrapper">
        <ul class="btnrow">
            <li><s:if test="%{currentAction == 'edit'}">
                <s:a href="#" cssClass="btn" onclick="interventionUpdate();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:if> <s:else>
                <s:a href="#" cssClass="btn" onclick="interventionAdd();">
                    <span class="btn_img"> <span class="save">Save</span></span>
                </s:a>
            </s:else></li>
        </ul>
        </del></div>
        </td>
    </tr>
</table>



<div class="actionsrow"><del class="btnwrapper">
<ul class="btnrow">
    <li><a href="interventionalStudyDesigndetailsQuery.action" class="btn"
        onclick="this.blur();"><span class="btn_img"><span
        class="back">Back</span></span></a></li>
    <li><a href="#" class="btn"
        onclick="this.blur();"><span class="btn_img"><span
        class="next">Next</span></span></a></li>
</ul>
</del></div>
</div>
</body>
</html>