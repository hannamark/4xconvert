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

    function handleNextAction() {
        document.getElementsByName("currentAction")[0].value = "next";
        document.getElementsByName("nextTarget")[0].value = "Treatment";
        document.forms[0].action = "nextPriorTherapies.action";
        document.forms[0].submit();
    }

    function handleAddPriorAction() {
        document.getElementsByName("currentAction")[0].value = "addPrior";
        document.forms[0].action = "savePriorTherapies.action";
        document.forms[0].submit();
    }

    function handleDelPriorAction(obj) {
    	document.getElementsByName("delItem")[0].value = obj;
        document.getElementsByName("currentAction")[0].value = "delPrior";
        document.forms[0].action = "savePriorTherapies.action";
        document.forms[0].submit();
    }

    function lookup(obj){
        document.getElementsByName("lookupItem")[0].value = obj;
        showPopWin('${lookupUrl}', 900, 400, '', 'Therapy Types');
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="priorTherapies_detail"/>
      </s:if>
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
<s:form name="detailForm">
<s:hidden name="currentAction"/>
<s:hidden name="nextTarget"/>
<s:hidden name="delItem"/>
<s:hidden name="priorTherapy.type"/>
<s:hidden name="priorTherapy.id"/>
<s:hidden name="newPrior.id"/>
<s:hidden name="lookupItem"/>
<label><fmt:message key="priorTherapy.label.has"/></label> <s:checkbox name="priors.hasPrior" onclick="handleRefreshAction()"/>
<table class="form">
<tr><td scope="row" class="label"><label style="${roClass}"><fmt:message key="priorTherapy.label.total"/></label></td>
<td><s:textfield readonly="%{!priors.hasPrior}" size="10" name="priors.totalRegimenNum" cssStyle="float:left"/>
<s:fielderror cssClass="formErrorMsg"><s:param>priors.totalRegimenNum</s:param></s:fielderror></td></tr>
<tr><td>&nbsp;</td><td>
    <s:set name="priorsList" scope="request" value="priors.list"/>
    <display:table class="data" summary="The list of Prior Therapy Regimens.
            Please use column headers to sort results." length="0" pagesize="0"
            sort="list" uid="row" name="priorsList" requestURI="PriorTherapies.action" export="false">
        <display:column titleKey="priorTherapy.label.list.type" headerClass="left" sortable="true" class="${roClass}">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}"><s:textfield readonly="true" size="50" name="newPrior.type"
               cssStyle="width:280px;float:left" cssClass="readonly"/>
               <s:a href="#" cssClass="btn" onclick="lookup('%{newPrior.id.extension}');"><span class="btn_img"><span class="search">Look Up</span></span></s:a>
             </s:elseif>
             <s:else><s:textfield readonly="true" size="50" name="type_%{#attr.row.id.extension}"
               cssStyle="width:280px;float:left" cssClass="readonly" value="%{#attr.row.type}"/>
               <s:a href="#" cssClass="btn" onclick="lookup('%{#attr.row.id.extension}');"><span class="btn_img"><span class="search">Look Up</span></span></s:a>
             </s:else>
        </display:column>
        <display:column titleKey="priorTherapy.label.list.description" headerClass="left" sortable="true" class="${roClass}">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}"><s:textfield size="50" name="newPrior.description"/>
             </s:elseif>
             <s:else><s:textfield size="50" name="desc_%{#attr.row.id.extension}" value="%{#attr.row.description}"/>
             </s:else>
        </display:column>
       <display:column titleKey="priorTherapy.label.list.action" sortable="true" 
         headerClass="centered" class="centered" headerScope="col">
             <s:if test="%{!priors.hasPrior}">&nbsp;
             </s:if>
             <s:elseif test="%{#attr.row_rowNum == priors.list.size()}"><s:a href="#" cssClass="btn" onclick="handleAddPriorAction()"><span class="btn_img"><span class="add">Add</span></span></s:a>
             </s:elseif>
             <s:else><s:a href="#" cssClass="btn" onclick="handleDelPriorAction('%{#attr.row.id.extension}')"><span class="btn_img"><span class="delete">Delete</span></span></s:a></s:else>
        </display:column>
    </display:table>
</td></tr>
<tr><td scope="row" class="label"><label style="${roClass}"><fmt:message key="priorTherapy.label.chemoNum"/></label></td>
<td class="value"><s:textfield name="priors.chemoRegimenNum" cssStyle="color: #909090; font-weight: normal; border: 0px solid black" readonly="true"/></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleNextAction()"><span class="btn_img"><span class="next">Next</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
