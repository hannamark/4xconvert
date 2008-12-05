<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="arms.main.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/popupDis.action" var="lookupUrl" />


<script type="text/javascript">
    function diseaseAdd(){
        document.diseaseForm.action="diseaseadd.action";
        document.diseaseForm.submit();
    }
    function diseaseUpdate(){
        input_box=confirm("Click OK to save changes.  Cancel to Abort.");
        if (input_box==true){
            document.diseaseForm.action="diseaseupdate.action";
            document.diseaseForm.submit();
        }
    }
    function lookup(){
        showPopWin('${lookupUrl}', 1050, 400, '', 'Disease');
    }   
    function loadDiv(intid){
         var url = '/pa/protected/ajaxptpDiseasedisplay.action?diseaseId='+intid;
         var div = document.getElementById('loadDetails');   
         div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
         });
    }   
</script>


</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="disease.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <h2>
        <s:if test="%{currentAction == 'edit'}"> 
            <fmt:message key="disease.edit.details.title"/>
        </s:if>
        <s:elseif test="%{currentAction == 'create'}">
            <fmt:message key="disease.add.details.title"/>
        </s:elseif>
    </h2>

    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2"><!--Facility-->
            <h3>Disease/Condition</h3>
            <s:form name="diseaseForm">
               <div id="loadDetails">
                    <%@ include file="/WEB-INF/jsp/nodecorate/selectedDiseaseDetails.jsp"%>
                </div>
            </s:form>
            <div class="actionsrow"><del class="btnwrapper">
            <ul class="btnrow">
                <li><s:if test="%{currentAction == 'edit'}">
                    <s:a href="#" cssClass="btn" onclick="diseaseUpdate();">
                        <span class="btn_img"> <span class="save">Save</span></span>
                    </s:a>
                </s:if> <s:else>
                    <s:a href="#" cssClass="btn" onclick="diseaseAdd();">
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
        <li><a href="#" class="btn"
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