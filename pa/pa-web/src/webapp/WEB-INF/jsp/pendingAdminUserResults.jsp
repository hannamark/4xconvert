<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <s:if test="pendingAdminUsers != null"> 
    <h2>Pending Admin User</h2>  
    <s:form name="spForm"><s:actionerror/>    
        <s:set name="pendingAdminUsers" value="pendingAdminUsers" scope="request"/>
            <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
             name="pendingAdminUsers" requestURI="inboxProcessinggetPendingAdminUserRole.action" export="false">
            <display:column class="title" titleKey="pending.userFirstName" property="firstName" paramId="id" paramProperty="id" 
                 href="inboxProcessingviewPendingUserAdmin.action" sortable="true" headerClass="sortable"/>
            <display:column titleKey="pending.userLastName" maxLength= "200" property="lastName" sortable="true" headerClass="sortable"/>
            <display:column titleKey="pending.affiliateOrg" maxLength= "200" property="affiliateOrg" sortable="true" headerClass="sortable"/>
        </display:table>
    </s:form>
</s:if>
