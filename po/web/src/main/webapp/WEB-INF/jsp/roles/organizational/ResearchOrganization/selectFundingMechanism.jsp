<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden key="role.fundingMechanism" id="curateRoleForm.role.fundingMechanism"/>
<s:if test="researchOrganizationType.id != null || researchOrganizationType.fundingMechanisms.size() > 0">
<s:select
   id="curateRoleForm.role._selectFundingMechanism"
   label="%{getText('researchOrganization.fundingMechanism')}"
   name="role.fundingMechanismSelect"
   list="researchOrganizationType.fundingMechanisms"
   listKey="code"
   listValue="description"
   value="role.fundingMechanism" 
   headerKey="" headerValue="--Select a Funding Mechanism--" 
   required="true" cssClass="required" 
   onchange="$('curateRoleForm.role.fundingMechanism').value = this.value;"/> 
<script type="text/javascript">
<!--
$('curateRoleForm.role.fundingMechanism').value = $('curateRoleForm.role._selectFundingMechanism').value;
  -->
</script>
</s:if>
<s:else>
<script type="text/javascript">
<!--
$('curateRoleForm.role.fundingMechanism').value = '';
  -->
</script>
</s:else>
