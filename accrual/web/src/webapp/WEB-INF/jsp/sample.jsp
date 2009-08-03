<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_sample"/> 
<head>
<title><fmt:message key="sample.header" /></title>
<s:head />
<script type="text/javascript">

function handleAction(){
    document.forms[0].action="samplegetResult.action";
    document.forms[0].submit();
}
</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="sample.header"/></h1>
    <s:form name="criteria">
        <table class="form">
            <s:if test="hasActionErrors()"><tr><td colspan="2"><div class="error_msg"><s:actionerror /></div></td></tr></s:if> 
            <tr>
                <td class="label"><p style="margin:0; padding:20"><fmt:message key="sample.criteria"/></p></td>
                <td class="value"><s:textfield name="enteredValue" maxlength="14" size="14" cssStyle="width:128px;float:left"/></td>
            </tr>
            <tr>
                <td class="label"><p style="margin:0; padding:20"><fmt:message key="sample.result"/></p></td>
                <td class="value"><s:textfield name="resultValue" readonly="true" maxlength="14" size="14" cssStyle="width:128px;float:left"/></td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Get square</span></span></s:a>  
                    </li>
                </ul>
            </del>
        </div>
    </s:form>
</body>
