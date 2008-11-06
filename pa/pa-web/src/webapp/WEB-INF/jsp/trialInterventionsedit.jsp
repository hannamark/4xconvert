<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message
    key="interventions.main.title" /></title>
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
        input_box=confirm("Click OK to save changes.  Cancel to Abort.");
        if (input_box==true){
            document.intervention.action="trialInterventionsupdate.action";
            document.intervention.submit();
        }
    }
    function statusChange() {
        newType=document.intervention.interventionType.value;
        if((newType=="Other")||(newType=="Cosmetic")){
          document.intervention.interventionName.disabled=false;
          document.intervention.interventionDescription.disabled=false;
          document.intervention.otherName.disabled=false;
        } else {
          document.intervention.interventionName.disabled=true;
          document.intervention.interventionDescription.disabled=true;
          document.intervention.otherName.disabled=true;
        }
        if(newType=="Drug"){
          document.intervention.leadIndicator.disabled=false;
        } else {
          document.intervention.leadIndicator.disabled=true;
        }
    }
    function lookup(){
        var intType = document.intervention.interventionType.value;
        if(intType.length < 1){
           alert("Please select an intervention type before doing look up.");
           return;
        }
        if((intType=="Other") || (intType=="Cosmetic")){
           alert("Use form to key in details for non-standard types (Cosmetic and Other) of intervention.");
           return;
        }
        showPopWin('${lookupUrl}?interventionType='+intType, 1050, 400, '', intType+' Intervention');
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
</script>

</head>
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
<h2>
    <s:if test="%{currentAction == 'edit'}"><fmt:message key="interventions.edit.title" /></s:if>
    <s:else><fmt:message key="interventions.add.title" /></s:else>
</h2>

<table class="form">
    <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr>
        <td colspan="2"><!--Facility-->
        <h3>Intervention</h3>
        <s:form name="intervention">
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