<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message
    key="interventions.details.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet"
    type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
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
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();         
    }

    function interventionAdd(){
        document.interventionForm.action="trialInterventionsadd.action";
        document.interventionForm.submit();     
    }
    function interventionUpdate(){
        input_box=confirm("Click OK to save changes.  Cancel to Abort.");
        if (input_box==true){
            document.interventionForm.action="trialInterventionsupdate.action";
            document.interventionForm.submit();
        }
    }
    function statusChange() {
        newType=document.interventionForm.interventionType.value;
        if(newType=="Drug"){
          document.interventionForm.interventionLeadIndicator.disabled=false;
          document.interventionForm.leadIndicatorLabel.disabled=false;
        } else {
          document.interventionForm.interventionLeadIndicator.disabled=true;
          document.interventionForm.leadIndicatorLabel.disabled=true;
        }
    }
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Intervention');
    }   
    function loadDiv(intid){
         var url = '/pa/protected/ajaxptpInterventiondisplay.action?interventionId='+intid;
         var div = document.getElementById('loadDetails');   
         div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
         });
         return false;
    }   
    function cancel(){
        document.interventionForm.action="trialInterventions.action";
        document.interventionForm.submit();
    }
</script>

</head>
<body>
<!-- <div id="contentwide"> -->
<h1><fmt:message key="interventions.details.title" /></h1>
<c:set var="topic" scope="request" value="abstract_interventions"/>
<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if>
<h2>
    <s:if test="%{currentAction == 'edit'}"><fmt:message key="interventions.edit.title" /></s:if>
    <s:else><fmt:message key="interventions.add.title" /></s:else>
</h2>

<table class="form">
    <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr>
        <td colspan="2">
        <s:form name="interventionForm">
            <div id="loadDetails">
                <%@ include file="/WEB-INF/jsp/nodecorate/selectedInterventionDetails.jsp"%>
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
            <li>
                <s:a href="#" cssClass="btn" onclick="cancel();">
                    <span class="btn_img"><span class="cancel">Cancel</span></span>
                </s:a>
            </li>
        </ul>
        </del></div>
        </td>
    </tr>
</table>

</div>
</body>
</html>