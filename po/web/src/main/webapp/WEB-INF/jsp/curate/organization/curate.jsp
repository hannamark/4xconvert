<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>     
    <c:set var="isReadonly" value="${!organization.isEditableBy(pageContext.request.remoteUser)}"/>
    <s:set name="isCreate" value="organization.id == null"/>
    <s:set name="isNotCreate" value="organization.id != null"/>
    <s:if test="%{isCreate}">
        <title>Create <s:text name="organization"/></title>
        <c:set var="topic" scope="request" value="createorg"/>
    </s:if>
    <s:else>
        <c:if test="${fn:length(organization.changeRequests) > 0}">
           <title><s:text name="organization.details.title"/> - Comparison</title>
        </c:if>
        <c:if test="${fn:length(organization.changeRequests) == 0}">
           <title><s:text name="organization.details.title"/></title>
        </c:if>
        <c:set var="topic" scope="request" value="orgdetails"/>
    </s:else>
    <%@include file="../../confirmThenSubmit.jsp" %>
      <script type="text/javascript">
		function confirmThenSubmit(fieldId, formId){
		    var map = new Array();
		    if ($('curateEntityForm.duplicateOf.id')==null || $('curateEntityForm.duplicateOf.id').value=='') {
		    	 map['NULLIFIED'] = 'Warning: You are about to nullify organization "${func:escapeJavaScript(organization.name)} - ${organization.id}". Changing the status to NULLIFIED will force all roles to go in to the NULLIFIED state. This action is irreversible. Do you really want to mark the record as NULLIFIED?';
		    } else {
		    	 map['NULLIFIED'] = 'Warning: You are about to nullify organization "${func:escapeJavaScript(organization.name)} - ${organization.id}" and merge it into organization "'+dupeOrgName+' - '+$('curateEntityForm.duplicateOf.id').value+'". This action is irreversible. Are you sure you want to merge this organization?';
		    }		   
		    map['INACTIVE'] = '<s:text name="entity.curation.inactive.confirmation"/>';
		    finalConfirmThenSubmit($(fieldId),$(formId),map);
		 }
   </script>
</head>
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(organization.changeRequests) > 0}">
    <div>
    <p class="directions">
    <s:text name="curation.instructions.entity.changerequests">
        <s:param value="getText('organization')"/>
        <s:param>${fn:length(organization.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>

<po:successMessages/>
<s:actionerror/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="organization"/> Information</h2>

    <s:if test="%{isCreate}">
       <s:set name="formAction" value="'create/organization/create.action'"/>
    </s:if>
    <s:else>
       <s:set name="formAction" value="'organization/curate/curate.action'"/>
    </s:else>
    <c:url value="/protected/organization/curate/removeCR.action" var="removeCrUrl" scope="request"/>
    <s:form action="%{formAction}" id="curateEntityForm" onsubmit="$('curateEntityForm.organization.comments').value = $F('curateEntityForm.organization.commentsText'); return isTelecomFieldsBlank() && isAliasFieldBlank() && confirmThenSubmit('curateEntityForm.organization.statusCode',document.forms.curateEntityForm);">
        <s:token/>
        <input id="enableEnterSubmit" type="submit"/>
        <s:hidden key="rootKey"/>
        <s:hidden key="cr.id"/>
        <s:hidden key="organization.id"/>
        <div class="boxouter">
        <h2>Basic Identifying Information</h2>
            <div class="box_white">
            <s:if test="isCreate">
                <s:select
                   label="%{getText('organization.statusCode')}"
                   name="organization.statusCode"
                   list="availableStatus"
                   listKey="name()"
                   listValue="name()"
                   value="organization.statusCode"
                   headerKey="" headerValue="--Select a Status--"
                   required="true" cssClass="required"
                   id="curateEntityForm.organization.statusCode"/>
            </s:if>
            <s:else>
                <po:inputRow>
                <po:inputRowElement><po:field labelFor="organization.id" labelKey="organization.id">${organization.id}</po:field></po:inputRowElement>
                <po:inputRowElement>&nbsp;</po:inputRowElement>
                <po:inputRowElement><po:field labelKey="organization.statusCode">${organization.statusCode}</po:field></po:inputRowElement>
                <po:inputRowElement>&nbsp;</po:inputRowElement>
                <po:inputRowElement><po:field labelFor="organization.statusDate" labelKey="organization.statusDate">
                <s:date name="organization.statusDate"  format="yyyy-MM-dd" /></po:field>
                </po:inputRowElement>
                </po:inputRow>
                <c:if test="${!isReadonly}">
	                <s:select
	                   label="New %{getText('organization.statusCode')}"
	                   name="organization.statusCode"
	                   list="organization.priorEntityStatus.allowedTransitions"
	                   value="organization.statusCode"
	                   headerKey="" headerValue="--Select a Status--"
	                   onchange="handleDuplicateOf();"
	                   required="true" cssClass="required"
	                   id="curateEntityForm.organization.statusCode"/>
	            </c:if>
	            <c:if test="${isReadonly}">
	               <s:hidden name="organization.statusCode" id="curateEntityForm.organization.statusCode" value="%{organization.statusCode}" />
	            </c:if>
                <div id="duplicateOfDiv" <s:if test="organization.statusCode != @gov.nih.nci.po.data.bo.EntityStatus@NULLIFIED">style="display:none;"</s:if>>
                    <script type="text/javascript">
                        var dupeOrgName = '';
                        function handleDuplicateOf() {
                            $('duplicateOfDiv')[$('curateEntityForm.organization.statusCode').value == 'NULLIFIED' ? 'show' : 'hide']();

                            if ($('curateEntityForm.organization.statusCode').value != 'NULLIFIED') {
                                $('curateEntityForm.duplicateOf.id').value = '';
                                $('wwctrl_curateEntityForm_organization_duplicateOf_id').innerHTML = '';
                                dupeOrgName = '';
                            }
                            return true;
                        }
                    </script>
                    <script type="text/javascript">
                        function showPopWinCallback(returnVal) {
                            if(returnVal.id == ${organization.id}) {
                                alert("Please select a different organization instead of selecting the same organization as duplicate.");
                            } else {
                                $('curateEntityForm.duplicateOf.id').value = returnVal.id;
                                $('wwctrl_curateEntityForm_organization_duplicateOf_id').innerHTML = '' + returnVal.value + ' (' + returnVal.id + ')';
                                dupeOrgName = returnVal.value;
                            }
                        }
                    </script>
                    <po:inputRow>
                        <po:inputRowElement>
                            <div class="wwgrp" id="wwgrp_curateEntityForm_organization_duplicateOf_id">
                                <div class="wwlbl" id="wwlbl_curateEntityForm_organization_duplicateOf_id">
                                    <label class="label" for="curateEntityForm_organization_duplicateOf_id">
                                        <s:text name="organization.duplicateOf.id"/>:
                                    </label>
                                </div>
                                <br/>
                                <div class="wwctrl" id="wwctrl_curateEntityForm_organization_duplicateOf_id">
                                   ${organization.duplicateOf.id}
                                </div>
                            </div>
                           <s:hidden key="duplicateOf" id="curateEntityForm.duplicateOf.id"/>
                        </po:inputRowElement>
                        <po:inputRowElement>
                            <c:url value="/protected/selector/organization/start.action" var="duplicatesUrl">
                                <c:param name="source.id" value="${organization.id}"/>
                            </c:url>
                            <po:buttonRow>
                                <po:button id="select_duplicate" href="javascript://noop/" onclick="showPopup('${duplicatesUrl}', showPopWinCallback);" style="search" text="Select Duplicate"/>
                            </po:buttonRow>
                        </po:inputRowElement>
                    </po:inputRow>
                </div>
            </s:else>
                <c:choose>
		          <c:when test="${isReadonly}">
		            <po:inputRow>
                        <po:inputRowElement><po:field labelKey="organization.name"><c:out value="${organization.name}"></c:out></po:field></po:inputRowElement>
                    </po:inputRow>
		          </c:when>
		          <c:otherwise>
		            <s:textfield key="organization.name" required="true" cssClass="required" size="70"/>
		          </c:otherwise>
		        </c:choose> 
                
                <div class="clear"></div>
                <s:if test="isNotCreate">                  	 
                    <po:createdBy createdByUserName="${organization.createdByUserName}"/>    
                    <po:overriddenBy overriddenByUserName="${organization.overriddenByUserName}"/>
                    <c:if test="${isReadonly}">
	                    <div style="margin-bottom:30px;" id="override_div">
		                    <c:url var="overrideUrl" value="/protected/organization/curate/override.action">
	                            <c:param name="organization.id">${organization.id}</c:param>
	                        </c:url>
	                        <po:buttonRow>	                            
	                            <po:button id="override_button" href="${overrideUrl}" style="entity_override" text="Override"/>                           
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
            <div class="box_white">
                <po:addressForm formNameBase="curateEntityForm" addressKeyBase="organization.postalAddress" address="${organization.postalAddress}" required="true" noPhoneFormatSwitch="true" readonly="${isReadonly}"/>
                <div class="clear"></div>
            </div>
        </div>

        <s:hidden key="comments" id="curateEntityForm.organization.comments"/>
    </s:form>

        <div class="boxouter_nobottom">
        <h2>Contact Information</h2>
            <div class="box_white">
                <div class="clear"></div>
                <po:contacts contactableKeyBase="organization" emailRequired="false" usOrCanadaFormatForValidationOnly="true" readonly="${isReadonly}"/>
            </div>
        </div>
<s:if test="%{isNotCreate}">
        <div class="boxouter">
        <h2>Assign Organizational Roles</h2>
            <div class="box_white">
                <c:url var="manageResearchOrgs" value="/protected/roles/organizational/ResearchOrganization/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>
                <c:url var="manageIdentifiedOrgs" value="/protected/roles/organizational/IdentifiedOrganization/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>
                <c:url var="manageOversightComms" value="/protected/roles/organizational/OversightCommittee/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>
                <c:url var="manageHcf" value="/protected/roles/organizational/HealthCareFacility/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>
                <c:url var="manageOrganizationalContacts" value="/protected/roles/organizational/OrganizationalContact/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>               
                <c:url var="manageOrganizationRelationships" value="/protected/roles/organizational/organizationRelationships/start.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>                                
                <ul id="maintabs" class="roletabs">
                     <li><a href="#hcf">HCF (${fn:length(organization.healthCareFacilities)}) <c:if test="${hotHealthCareFacilityCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#ro">RO (${fn:length(organization.researchOrganizations)}) <c:if test="${hotResearchOrganizationCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#oc">Oversight Committee (${fn:length(organization.oversightCommittees)}) <c:if test="${hotOversightCommitteeCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#io">Identified Org (${fn:length(organization.identifiedOrganizations)}) <c:if test="${hotIdentifiedOrganizationCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#octc">Org Contact (${fn:length(organization.organizationalContacts)}) <c:if test="${hotOrganizationalContactCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                </ul>
                <script type="text/javascript">
                                //<![CDATA[
                                Event.observe(window,'load',function() {
                                    $$('.roletabs').each(function(tabs) {
                                        new Control.Tabs(tabs);
                                    });
                                });
                                //]]>
                </script>                
                <div id="tabboxwrapper">
                      <div id="hcf" class="tabbox"> 
	                        <c:set var="results" value="${func:wrapInPaginated(organization.healthCareFacilities)}" scope="request"/>
	                        <%@include file="/WEB-INF/jsp/roles/organizational/HealthCareFacility/list.jsp" %>
				            <c:url var="addUrl" value="/protected/roles/organizational/HealthCareFacility/input.action">
				                <c:param name="organization" value="${organization.id}"/>
				            </c:url>
				            <div class="btnwrapper">
					            <po:buttonRow>
					                <po:button id="add_button_hcf" href="${addUrl}" style="add" text="Add Health Care Facility"/>
					            </po:buttonRow>	                                    
				            </div>               
                      </div>
                      <div id="ro" class="tabbox" style="display:none;">   
                            <c:set var="results" value="${func:wrapInPaginated(organization.researchOrganizations)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/organizational/ResearchOrganization/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/organizational/ResearchOrganization/input.action">
                                <c:param name="organization" value="${organization.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_ro" href="${addUrl}" style="add" text="Add Research Organization"/>
                                </po:buttonRow>                                     
                            </div>       
                      </div>
                      <div id="oc" class="tabbox" style="display:none;">
                            <c:set var="results" value="${func:wrapInPaginated(organization.oversightCommittees)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/organizational/OversightCommittee/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/organizational/OversightCommittee/input.action">
                                <c:param name="organization" value="${organization.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_oc" href="${addUrl}" style="add" text="Add Oversight Committee"/>
                                </po:buttonRow>                                     
                            </div>          
                      </div>
                      <div id="io" class="tabbox" style="display:none;">
                            <c:set var="results" value="${func:wrapInPaginated(organization.identifiedOrganizations)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/organizational/IdentifiedOrganization/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/organizational/IdentifiedOrganization/input.action">
                                <c:param name="organization" value="${organization.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_io" href="${addUrl}" style="add" text="Add Identified Organization"/>
                                </po:buttonRow>                                     
                            </div>          
                      </div>
                      <div id="octc" class="tabbox" style="display:none;">
                            <c:set var="results" value="${func:wrapInPaginated(organization.organizationalContacts)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/OrganizationalContact/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/organizational/OrganizationalContact/input.action">
                                <c:param name="organization" value="${organization.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_octc" href="${addUrl}" style="add" text="Add Organizational Contact"/>
                                </po:buttonRow>                                     
                            </div>          
                      </div>
                </div>
            </div>
        </div>
        <div class="boxouter">
            <h2>Organization Family</h2>
             <div class="box_white">
                    <c:url var="manageFamilies" value="/protected/roles/organizational/familyRelationships/start.action">
	                    <c:param name="organization" value="${organization.id}"/>
	                </c:url>
	                <ul>
	                    <li><a href="${manageFamilies}"><s:text name="familyOrganizationRelationships.manage.title"/></a> (${fn:length(organization.familyOrganizationRelationships)})</li>
	                </ul>
	                <div class="clear"></div>	                
             </div>
        </div>
</s:if>
        <div class="boxouter">
        <h2>Curator Comment(s)</h2>
            <c:forEach items="${organization.comments}" var="comment">
                <div class="comment_box">
                    <div class="comment_hdr">
		                <b><c:out value="${not empty comment.userName?comment.userName:'Unknown user'}"></c:out></b>
		                <fmt:message key="organization.comments.addedBy"/>
		                <b>
		                <c:choose>
		                    <c:when test="${comment.createDate==null}">
		                        unknown date:
		                    </c:when>    
		                    <c:otherwise>
		                        <fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd hh:mm aaa"/>:
		                    </c:otherwise>                
		                </c:choose>
		                </b>
	                </div>
	                <div class="comment_txt"><c:out value="${comment.value}" escapeXml="false"/></div>
                </div>
            </c:forEach>
            <div class="box_white">
                <s:textarea id="curateEntityForm.organization.commentsText" label="%{getText('organization.comments.add')}" cols="50" rows="8" cssStyle="resize: none;" value="%{comments}" />
            </div>
        </div>
    </div>
</div>

<c:if test="${fn:length(organization.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(organization.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/organization/curate/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
        <s:hidden key="rootKey"/>
        <s:select
           name="cr"
           list="selectChangeRequests"
           value="cr.id"
           onchange="document.getElementById('curateEntityForm_cr_id').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);"
           />
    </s:form>
    </div>
    </c:if>
<div id="crinfo">
<%@ include file="crInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;"></div>

<div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
        <c:choose>
          <c:when test="${isReadonly}">
            <po:button id="save_button" href="javascript://noop/" onclick="$('curateEntityForm.organization.comments').value = $F('curateEntityForm.organization.commentsText'); return (confirmThenSubmit('curateEntityForm.organization.statusCode', document.forms.curateEntityForm));" style="save" text="Save"/>
          </c:when>
          <c:otherwise>
            <po:button id="save_button" href="javascript://noop/" onclick="$('curateEntityForm.organization.comments').value = $F('curateEntityForm.organization.commentsText'); return ((isTelecomFieldsBlank()==true && isAliasFieldBlank()==true) ? confirmThenSubmit('curateEntityForm.organization.statusCode', document.forms.curateEntityForm):false);" style="save" text="Save"/>
          </c:otherwise>
        </c:choose>     
        <c:set var="querystring" value="${pageContext.request.queryString}"/>
        <c:choose>
          <c:when test="${fn:contains(querystring, 'organization.id')}">
            <c:url var="resetUrl" value="/protected/organization/curate/start.action">
              <c:param name="organization.id">${organization.id}</c:param>
            </c:url>
          </c:when>
          <c:otherwise>
            <c:url var="resetUrl" value="/protected/create/organization/start.action"/>
          </c:otherwise>
        </c:choose>
        <po:button id="reset_button" href="${resetUrl}" style="reject" text="Reset"/>
    </po:buttonRow>
</div>

</body>
</html>
