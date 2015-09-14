<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<s:if test="%{isCreate}">
    <title>Create <s:text name="researchOrganization"/></title>
</s:if>
<s:else>
    <c:set var="topic" scope="request" value="editro"/>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="researchOrganization"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="researchOrganization"/></title>
   </c:if>
</s:else>

<%@include file="../../roleStatusOnChange_handleDuplicateOf.jsp" %>
</head>
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <div>
        <p class="directions">
            <s:text name="curation.instructions.role.changerequests">
                <s:param value="getText('researchOrganization')"/>
                <s:param>${fn:length(role.changeRequests)}</s:param>
            </s:text>
        </p>
    </div>
    </c:if>
</s:if>


<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
        <h2><s:text name="researchOrganization"/> Information</h2>
            <%@ include file="../orgInfo.jsp" %>
        <div class="boxouter">
            <s:if test="%{isCreate}">
                <s:set name="formAction" value="'roles/organizational/ResearchOrganization/add.action'" />
            </s:if>
            <s:else>
                <s:set name="formAction" value="'roles/organizational/ResearchOrganization/edit.action'" />
            </s:else>
            <c:choose>
               <c:when test="${isReadonly}">
                 <s:set name="formTheme" value="'css_xhtml_readonly'" />
               </c:when>
               <c:otherwise>
                 <s:set name="formTheme" value="'css_xhtml'" />
               </c:otherwise>
            </c:choose>
            <h2><s:text name="researchOrganization"/> Role Information</h2>
            <div class="box_white">
                <s:actionerror/>
                <s:form action="%{formAction}" theme="%{formTheme}" id="curateRoleForm" onsubmit="submitForm('curateRoleForm');">
                    <s:token/>
                    <s:hidden key="cr"/>
                    <s:hidden key="organization"/>
                    <s:hidden key="rootKey"/>
                   
                    <s:if test="%{isNotCreate}"><s:hidden key="role.id"/></s:if>
                    <script type="text/javascript"><!--
                    /*
                     Toggles the display of a stateOrProvince textfield or a select-box.
                    */
                    function curateRoleForm_displayFundingMechanism(ResearchOrganizationType_id) {
                      var url =  '' + contextPath + '/protected/ajax/roles/organizational/ResearchOrganization/funding/changeResearchOrganizationType.action?researchOrganizationType.id=' + ResearchOrganizationType_id;
                      selectOnlyFundingMechanism = function() {
                        var fundingSel = $('curateRoleForm.role._selectFundingMechanism');
                        if (fundingSel != null) {
                            var opts = fundingSel.options;
                            if (opts.length == 2) {
                               fundingSel.selectedIndex = 1;
                               fundingSel.onchange();
                            }
                        }
                      }
                      loadDiv(url, 'curateRoleForm_displayFundingMechanism', true, selectOnlyFundingMechanism);
                    }

                    --></script>
                    <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                    <s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.ResearchOrganizationType@class"/>
                    <s:set name="researchOrgTypes" value="#genericCodeValueService.list(#codeValueClass, 'description')" />
                    <c:choose>
	                   <c:when test="${isReadonly}">          
	                     <po:field labelKey="researchOrganization.name">${role.name}</po:field>  
	                     <po:field labelKey="researchOrganization.typeCode">${role.typeCode.description}</po:field>  
	                     <po:field labelKey="researchOrganization.fundingMechanism">${role.fundingMechanism.description}</po:field>              
	                   </c:when>
	                   <c:otherwise>      
		                 <s:textfield
	                        id="curateRoleForm.role.name"
	                        label="%{getText('researchOrganization.name')}" name="role.name" value="%{role.name}" maxlength="160" size="50"/>                 
	                     <s:select
                           label="%{getText('researchOrganization.typeCode')}"
                           name="role.typeCode"
                           list="#researchOrgTypes"
                           listKey="id"
                           listValue="description"
                           value="role.typeCode.id"
                           headerKey="" headerValue="--Select a Type--"
                           onchange="return curateRoleForm_displayFundingMechanism(this.value);"/>
                          <s:hidden key="role.fundingMechanism" id="curateRoleForm.role.fundingMechanism"/>
                          <div id="curateRoleForm_displayFundingMechanism" class="inline">
                            <%@ include file="selectFundingMechanism.jsp" %>
                          </div>
	                   </c:otherwise>
	                </c:choose>
	                
	                <c:choose>
                       <c:when test="${isReadonly}">            
                         <po:field labelKey="researchOrganization.status">${role.status}</po:field>           
                       </c:when>
                       <c:otherwise>                       
                         <s:select id="curateRoleForm.role.status"
                           label="%{getText('researchOrganization.status')}"
                           name="role.status"
                           list="availableStatus"
                           listKey="name()"
                           listValue="name()"
                           value="role.status"
                           headerKey="" headerValue="--Select a Role Status--"
                           required="true" cssClass="required"
                           onchange="handleDuplicateOf();" />
                       </c:otherwise>
                    </c:choose>                    
                    
                    <div id="duplicateOfDiv" <s:if test="role.status != @gov.nih.nci.po.data.bo.RoleStatus@NULLIFIED">style="display:none;"</s:if>>
                        <c:if test="${fn:length(availableDuplicateOfs) > 0}">
                            <po:field labelKey="researchOrganization.duplicateOf">
                                <select id="curateRoleForm.duplicateOf" name="duplicateOf">
                                    <option value="">--Select a Duplicate Of Entry (ID - TYPE - FUNDING - STATUS - DATE)--</option>
                                    <c:forEach var="dupEntry" items="${availableDuplicateOfs}">
                                        <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.typeCode.description} - ${dupEntry.fundingMechanism.code} - ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
                                    </c:forEach>
                                </select>
                            </po:field>
                        </c:if>
                    </div>
                    <input id="enableEnterSubmit" type="submit"/>
                </s:form>
                <c:if test="${!empty role.otherIdentifiers}">
                    <label>Other IDs</label><br/>
                    <c:forEach var="otherId" items="${role.otherIdentifiers}">
                        ${otherId.identifierName}: ${otherId.extension}<br/>
                    </c:forEach>
                </c:if>
                <s:if test="isNotCreate">                        
                    <po:createdBy createdByUserName="${role.createdByUserName}"/>    
                    <po:overriddenBy overriddenByUserName="${role.overriddenByUserName}"/>
                    <c:if test="${isReadonly}">
                        <div style="margin-bottom:30px;" id="ro_override_div">
                            <c:url var="overrideUrl" value="/protected/roles/organizational/ResearchOrganization/override.action">
                                <c:param name="organization">${organization.id}</c:param>                                
                                <c:param name="role.id" value="${role.id}"/>
                            </c:url>
                            <po:buttonRow>                              
                                <po:button id="ro_override_button" href="${overrideUrl}" style="entity_override" text="Override"/>                           
                            </po:buttonRow>
                        </div> 
                    </c:if>           
                </s:if>
            </div>
        </div>
        
        <div class="boxouter">
        <h2>Aliases</h2>
            <div class="box_white">                
                <div class="clear"></div>                
                <po:aliases aliasKeyBase="organization" readonly="${isReadonly}"/>
            </div>
        </div>
        
        <div class="boxouter">
            <h2>Address Information</h2>
            <po:addresses readonly="${isReadonly}" usOrCanadaFormatForValidationOnly="true"/>
        </div>

        <div class="boxouter_nobottom">
            <h2>Contact Information</h2>
            <div class="box_white">
                <div class="clear"></div>
                <po:contacts usOrCanadaFormatForValidationOnly="true" contactableKeyBase="role" emailRequired="false" phoneRequired="false" readonly="${isReadonly}"/>
            </div>
        </div>
    </div>
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(role.changeRequests) > 1}">
        <div class="crselect">
            <s:form action="ajax/roles/organizational/ResearchOrganization/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
                <s:hidden key="organization"/>
                <s:hidden key="rootKey"/>
                <s:select
                   name="cr"
                   list="selectChangeRequests"
                   value="cr.id"
                   onchange="$('curateRoleForm_cr').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);"
                   />
            </s:form>
        </div>
    </c:if>
<div id="crinfo">
<%@ include file="roleCrInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>
    <div class="btnwrapper" style="margin-bottom:20px;">
    <%@include file="../../defineMapToShowConfirm.jsp" %>
    <po:buttonRow>
       <c:if test="${!isReadonly}">
       <po:button id="save_button" href="javascript://noop/" onclick="submitForm('curateRoleForm');" style="save" text="Save"/>
       </c:if>
       <c:url var="managePage" value="/protected/roles/organizational/ResearchOrganization/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('researchOrganization.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>
