<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookupPriorTherapies.action" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
	function handleRefreshAction() {
		var prior = document.getElementsByName("priors.hasPrior")[0];
		if (!prior.checked) {
			if (!confirm("All Prior Therapies will be deleted when Save is pressed, continue?")) {
				prior.checked = true;
				return;
			}
		}
        document.getElementsByName("currentAction")[0].value = "reset";
        document.forms[0].action = "executePriorTherapies.action";
        document.forms[0].submit();
	}

	function handleEditAction() {
        document.getElementsByName("currentAction")[0].value = "save";
        document.forms[0].action = "savePriorTherapies.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.getElementsByName("currentAction")[0].value = "cancel";
        document.forms[0].action = "cancelPriorTherapies.action";
        document.forms[0].submit();
    }

    function handleAddPriorAction() {
        document.getElementsByName("currentAction")[0].value = "addPrior";
        document.forms[0].action = "addPriorTherapies.action";
        document.forms[0].submit();
    }

    function handleDelPriorAction(obj) {
    	document.getElementsByName("delItem")[0].value = obj;
        document.getElementsByName("currentAction")[0].value = "delPrior";
        document.forms[0].action = "deletePriorTherapies.action";
        document.forms[0].submit();
    }

    function lookup(obj){
        document.getElementsByName("lookupItem")[0].value = obj;
        showPopWin('${lookupUrl}', 900, 400, '', 'Therapy Types');
    }
</script>
<title>
    <c:set var="topic" scope="request" value="prior_therapies"/>
     Prior Therapies
</title>
<s:head />
</head>
<body>
<s:if test="%{priors.hasPrior}">
<c:set var="roClass" scope="request" value="" />
</s:if>
<s:else>
<c:set var="roClass" scope="request">color:#909090</c:set>
</s:else>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
      Prior Therapies
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <accrual:sucessMessage />
<s:form name="detailForm">
<s:hidden name="currentAction"/>
<s:hidden name="delItem"/>
<s:hidden name="newPrior.identifier"/>
<s:hidden name="priors.identifier" id="priors.identifier"/>
<label><fmt:message key="priorTherapy.label.has"/></label> <s:checkbox name="priors.hasPrior" onclick="handleRefreshAction()"/>
<table class="form">
<tr><td scope="row" class="label"><label style="${roClass}"><fmt:message key="priorTherapy.label.total"/></label></td>
<td><s:textfield readonly="true" size="10" name="priors.totalRegimenNum" cssStyle="color: #909090; font-weight: normal; border: 0px solid black"/>
<s:fielderror cssClass="formErrorMsg"><s:param>priors.totalRegimenNum</s:param></s:fielderror></td></tr>
<tr><td>&nbsp;</td><td>
    <s:set name="priorsList" scope="request" value="priors.list"/>    
    <s:set name="priorTherapyTypeCodeValues" value="@gov.nih.nci.pa.enums.PriorTherapyTypeCode@getDisplayNames()" />   
    <display:table class="data" summary="The list of Prior Therapy Regimens." length="0" pagesize="0" uid="row" name="priorsList" export="false">
        <display:column titleKey="priorTherapy.label.list.type" headerClass="left" class="${roClass}">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}">
			     <s:select id="newPrior.type" name="newPrior.type" headerKey="" headerValue="--Select--"
                    list="#priorTherapyTypeCodeValues"/>
                </s:elseif>
             <s:else>
			 <s:select id="type_%{#attr.row.identifier.extension}" name="type_%{#attr.row.identifier.extension}" headerKey="" headerValue="--Select--"
                    list="#priorTherapyTypeCodeValues" value="%{#attr.row.type.code}"/>
             </s:else>
        </display:column>
        <display:column titleKey="priorTherapy.label.list.description" headerClass="left" class="${roClass}">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}"><s:textfield size="50" name="newPrior.description"/>
             </s:elseif>
             <s:else><s:textfield size="50" name="desc_%{#attr.row.identifier.extension}" value="%{#attr.row.description}"/>
             </s:else>
        </display:column>
       <display:column titleKey="priorTherapy.label.list.action"  
         headerClass="centered" class="centered" headerScope="col">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}"><s:a href="#" cssClass="btn" onclick="handleAddPriorAction()"><span class="btn_img"><span class="add">Add</span></span></s:a>
             </s:elseif>
             <s:else><s:a href="#" cssClass="btn" onclick="handleDelPriorAction('%{#attr.row.identifier.extension}')"><span class="btn_img"><span class="delete">Delete</span></span></s:a></s:else>
        </display:column>
    </display:table>
</td></tr>
<tr><td scope="row" class="label"><label style="${roClass}"><fmt:message key="priorTherapy.label.chemoNum"/></label></td>
<td class="value"><s:textfield name="priors.chemoRegimenNum" cssStyle="color: #909090; font-weight: normal; border: 0px solid black" readonly="true"/></td></tr>
</table>
<fmt:message key="priorTherapy.saveMsg"/>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
