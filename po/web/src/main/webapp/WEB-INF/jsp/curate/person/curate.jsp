<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <s:set name="isCreate" value="person.id == null"/>
    <s:set name="isNotCreate" value="person.id != null"/>
    <s:if test="%{isCreate}">
        <title>Create <s:text name="person"/></title>
        <c:set var="topic" scope="request" value="createperson"/>
    </s:if>
    <s:else>
        <c:if test="${fn:length(person.changeRequests) > 0}">
           <title><s:text name="person.details.title"/> - Comparison</title>
        </c:if>
        <c:if test="${fn:length(person.changeRequests) == 0}">
           <title><s:text name="person.details.title"/></title>
        </c:if>
        <c:set var="topic" scope="request" value="persondetails"/>
    </s:else>
    <%@include file="../../confirmThenSubmit.jsp" %>
    <%@include file="../defineMapToShowConfirm.jsp" %>
</head>
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(person.changeRequests) > 0}">
    <div>
    <p class="directions">
    <s:text name="curation.instructions.entity.changerequests">
        <s:param value="getText('person')"/>
        <s:param>${fn:length(person.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>

<po:successMessages/>
<s:actionerror/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="person"/> Information</h2>

    <s:if test="%{isCreate}">
       <s:set name="formAction" value="'create/person/create.action'"/>
    </s:if>
    <s:else>
       <s:set name="formAction" value="'person/curate/curate.action'"/>
    </s:else>
    <s:form action="%{formAction}" id="curateEntityForm" onsubmit="$('curateEntityForm.person.comments').value = $F('curateEntityForm.person.commentsText'); return isTelecomFieldsBlank() && confirmThenSubmit('curateEntityForm.person.statusCode', document.forms.curateEntityForm);">
        <s:token/>
        <input id="enableEnterSubmit" type="submit"/>
        <s:hidden key="rootKey"/>
        <s:hidden key="cr.id"/>
        <s:hidden key="person.id"/>
        <div class="boxouter">
        <h2>Basic Identifying Information</h2>
            <div class="box_white">
            <s:if test="isCreate">
                <s:select
                   label="%{getText('person.statusCode')}"
                   name="person.statusCode"
                   list="availableStatus"
                   listKey="name()"
                   listValue="name()"
                   value="person.statusCode"
                   headerKey="" headerValue="--Select a Status--"
                   required="true" cssClass="required"
                   id="curateEntityForm.person.statusCode"/>
            </s:if>
            <s:else>
                <po:inputRow>
                <po:inputRowElement><po:field labelKey="person.id">${person.id}</po:field></po:inputRowElement>
                <po:inputRowElement>&nbsp;</po:inputRowElement>
                <po:inputRowElement><po:field labelKey="person.statusCode">${person.statusCode}</po:field></po:inputRowElement>
                <po:inputRowElement>&nbsp;</po:inputRowElement>
                <po:inputRowElement><po:field labelKey="person.statusDate"><s:date name="person.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
                </po:inputRow>
                <s:select
                   label="New %{getText('person.statusCode')}"
                   name="person.statusCode"
                   list="person.priorEntityStatus.allowedTransitions"
                   value="person.statusCode"
                   headerKey="" headerValue="--Select a Status--"
                   onchange="handleDuplicateOf();"
                   required="true" cssClass="required"
                   id="curateEntityForm.person.statusCode"/>
                <div id="duplicateOfDiv" <s:if test="person.statusCode != @gov.nih.nci.po.data.bo.EntityStatus@NULLIFIED">style="display:none;"</s:if>>
                    <script type="text/javascript">
                        function handleDuplicateOf() {
                            $('duplicateOfDiv')[$('curateEntityForm.person.statusCode').value == 'NULLIFIED' ? 'show' : 'hide']();

                            if ($('curateEntityForm.person.statusCode').value != 'NULLIFIED') {
                                $('curateEntityForm.duplicateOf.id').value = '';
                                $('wwctrl_curateEntityForm_person_duplicateOf_id').innerHTML = '';
                            }
                            return true;
                        }
                    </script>
                    <script type="text/javascript">
                        function showPopWinCallback(returnVal) {
                            $('curateEntityForm.duplicateOf.id').value = returnVal.id;
                            $('wwctrl_curateEntityForm_person_duplicateOf_id').innerHTML = '' + returnVal.value + ' (' + returnVal.id + ')';
                        }
                    </script>
                    <po:inputRow>
                    <po:inputRowElement>
                        <div class="wwgrp" id="wwgrp_curateEntityForm_person_duplicateOf_id">
                            <div class="wwlbl" id="wwlbl_curateEntityForm_person_duplicateOf_id">
                                <label class="label" for="curateEntityForm_person_duplicateOf_id">
                                    <s:text name="person.duplicateOf.id"/>:
                                </label>
                            </div>
                            <br/>
                            <div class="wwctrl" id="wwctrl_curateEntityForm_person_duplicateOf_id">
                                ${person.duplicateOf.id}
                            </div>
                        </div>
                        <s:hidden key="duplicateOf" id="curateEntityForm.duplicateOf.id"/>
                    </po:inputRowElement>
                    <po:inputRowElement>
                        <c:url value="/protected/selector/person/start.action" var="duplicatesUrl">
                            <c:param name="source.id" value="${person.id}"/>
                        </c:url>
                        <po:buttonRow>
                        <po:button id="select_duplicate" href="javascript://noop/" onclick="showPopup('${duplicatesUrl}', showPopWinCallback);" style="search" text="Select Duplicate"/>
                        </po:buttonRow>
                    </po:inputRowElement>
                    </po:inputRow>
                </div>
            </s:else>
                <s:textfield key="person.prefix" size="10"/>
                <s:textfield key="person.firstName" required="true" cssClass="required" size="50"/>
                <s:textfield key="person.middleName" size="50"/>
                <s:textfield key="person.lastName" required="true" cssClass="required" size="50"/>
                <s:textfield key="person.suffix" size="10"/>
                <div class="clear"></div>
                <s:if test="isNotCreate">                        
                    <po:createdBy createdByUserName="${person.createdByUserName}"/>               
                </s:if>
            </div>
        </div>

        <div class="boxouter">
        <h2>Address Information</h2>
            <div class="box_white">
                <po:addressForm formNameBase="curateEntityForm" addressKeyBase="person.postalAddress" address="${person.postalAddress}" 
                   noPhoneFormatSwitch="true" required="true"/>
                <div class="clear"></div>
            </div>
        </div>
        <s:hidden key="comments" id="curateEntityForm.person.comments"/>
    </s:form>

        <div class="boxouter_nobottom">
        <h2>Contact Information</h2>
            <div class="box_white">
                <div class="clear"></div>
                <po:contacts emailOrPhoneRequired="true" emailRequired="false" phoneRequired="false" contactableKeyBase="person" usOrCanadaFormatForValidationOnly="true"/>
            </div>
        </div>
<s:if test="%{isNotCreate}">
        <div class="boxouter">
        <h2>Assign Organizational Roles</h2>
            <div class="box_white">
                <c:url var="manageClinicalResearchStaff" value="/protected/roles/person/ClinicalResearchStaff/start.action">
                    <c:param name="person" value="${person.id}"/>
                </c:url>
                <c:url var="manageHealthCareProvider" value="/protected/roles/person/HealthCareProvider/start.action">
                    <c:param name="person" value="${person.id}"/>
                </c:url>
                <c:url var="manageOrganizationalContact" value="/protected/roles/person/OrganizationalContact/start.action">
                    <c:param name="person" value="${person.id}"/>
                </c:url>
                <c:url var="manageIdentifiedPerson" value="/protected/roles/person/IdentifiedPerson/start.action">
                    <c:param name="person" value="${person.id}"/>
                </c:url>      
                
                <ul id="maintabs" class="roletabs">
                     <li><a href="#crs">CRS (${fn:length(person.clinicalResearchStaff)}) <c:if test="${hotClinicalResearchStaffCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#hcp">HCP (${fn:length(person.healthCareProviders)}) <c:if test="${hotHealthCareProviderCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#oc">OC (${fn:length(person.organizationalContacts)}) <c:if test="${hotOrganizationalContactCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>
                     <li><a href="#opi">OPI (${fn:length(person.identifiedPersons)}) <c:if test="${hotIdentifiedPersonCount > 0}"><span class='required'><i>P</i></span></c:if></a></li>                     
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
                      <div id="crs" class="tabbox"> 
                            <c:set var="results" value="${func:wrapInPaginated(person.clinicalResearchStaff)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/person/ClinicalResearchStaff/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/person/ClinicalResearchStaff/input.action">
                                <c:param name="person" value="${person.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_hcf" href="${addUrl}" style="add" text="Add Clinical Research Staff"/>
                                </po:buttonRow>                                     
                            </div>               
                      </div>
                      <div id="hcp" class="tabbox" style="display:none;">   
                            <c:set var="results" value="${func:wrapInPaginated(person.healthCareProviders)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/person/HealthCareProvider/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/person/HealthCareProvider/input.action">
                                <c:param name="person" value="${person.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_ro" href="${addUrl}" style="add" text="Add Health Care Provider"/>
                                </po:buttonRow>                                     
                            </div>       
                      </div>
                      <div id="oc" class="tabbox" style="display:none;">
                            <c:set var="results" value="${func:wrapInPaginated(person.organizationalContacts)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/OrganizationalContact/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/person/OrganizationalContact/input.action">
                                <c:param name="person" value="${person.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_oc" href="${addUrl}" style="add" text="Add Organizational Contact"/>
                                </po:buttonRow>                                     
                            </div>          
                      </div>
                      <div id="opi" class="tabbox" style="display:none;">
                            <c:set var="results" value="${func:wrapInPaginated(person.identifiedPersons)}" scope="request"/>
                            <%@include file="/WEB-INF/jsp/roles/person/IdentifiedPerson/list.jsp" %>
                            <c:url var="addUrl" value="/protected/roles/person/IdentifiedPerson/input.action">
                                <c:param name="person" value="${person.id}"/>
                            </c:url>
                            <div class="btnwrapper">
                                <po:buttonRow>
                                    <po:button id="add_button_io" href="${addUrl}" style="add" text="Add Other Person Identifier"/>
                                </po:buttonRow>                                     
                            </div>          
                      </div>                      
                </div>
            </div>
        </div>
</s:if>
        <div class="boxouter">
        <h2>Curator Comment(s)</h2>
            <c:forEach items="${person.comments}" var="comment">
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
                <s:textarea id="curateEntityForm.person.commentsText" label="%{getText('person.comments.add')}" cols="50" rows="8" cssStyle="resize: none;" value="%{comments}" />
            </div>
        </div>
    </div>
</div>

<c:if test="${fn:length(person.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(person.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/person/curate/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
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
        <po:button id="save_button" href="javascript://noop/" onclick="$('curateEntityForm.person.comments').value = $F('curateEntityForm.person.commentsText'); return ((isTelecomFieldsBlank()==true) ? confirmThenSubmit('curateEntityForm.person.statusCode', document.forms.curateEntityForm):false);" style="save" text="Save"/>
        <c:set var="querystring" value="${pageContext.request.queryString}"/>
        <c:choose>
          <c:when test="${fn:contains(querystring, 'person.id')}">
            <c:url var="resetUrl" value="/protected/person/curate/start.action">
              <c:param name="person.id">${person.id}</c:param>
            </c:url>
          </c:when>
          <c:otherwise>
            <c:url var="resetUrl" value="/protected/create/person/start.action"/>
          </c:otherwise>
        </c:choose>
        <po:button id="reset_button" href="${resetUrl}" style="reject" text="Reset"/>
    </po:buttonRow>
</div>

</body>
</html>
