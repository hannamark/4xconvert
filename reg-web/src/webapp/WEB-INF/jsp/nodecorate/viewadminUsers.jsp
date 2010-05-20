<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function cancel() {
    var action = "registerUsershowMyAccount.action";   
    document.forms[0].action=action;
    document.forms[0].submit();
}
</SCRIPT>
<body>
<s:form name="adminUser" method="POST" >
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" pagesize="10" id="row"
             name="${sessionScope.adminUsers}" export="false">
            <display:column titleKey="admin.userFirstName" property="firstName" headerClass="sortable"/>
            <display:column titleKey="admin.userLastName" property="lastName" headerClass="sortable"/>
</display:table>
<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li>       
                        <s:a href="#" cssClass="btn" onclick="cancel()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                    </li> 
                </ul>   
            </del>
</div>
</s:form>
</body>  