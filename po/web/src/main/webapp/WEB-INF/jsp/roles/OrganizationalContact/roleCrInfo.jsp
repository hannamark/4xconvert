<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="setOCType.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <s:if test="%{#ocType == 'organizational'}">
            <%@ include file="../organizational/orgInfo.jsp" %>
        </s:if>
        <s:else>
            <%@ include file="../person/personInfo.jsp" %>
        </s:else>
        <div class="boxouter">
            <h2><s:text name="organizationalContact"/> Information</h2>
            <div class="box_white">
                <s:if test="%{#ocType == 'organizational'}">
                    <po:copyButton id="copy_curateCrForm_role_title"
                                   onclick="copyValueToTextField('${pofn:escapeJavaScript(cr.title)}', 'curateRoleForm_role_title');"
                                   bodyStyle="float:left;" buttonStyle="float:right;">
                        <po:field labelKey="organizationalContact.title">
                            ${cr.title}
                        </po:field>
                    </po:copyButton>
                </s:if>
                <s:else>
                    <po:copyButton id="copy_curateCrForm_role_scoper"
                        onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback(new IdValue('${cr.scoper.id}','${cr.scoper.name}'));"
                        bodyStyle="float:left;" buttonStyle="float:right;">
                        <po:field labelKey="organizationalContact.scoper.id">
                            ${cr.scoper.name} (${cr.scoper.id})
                        </po:field>
                    </po:copyButton>
                </s:else>

            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');"
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('organizationalContact.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>

            <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
            <s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.OrganizationalContactType@class"/>
            <s:set name="orgContactTypes" value="#genericCodeValueService.list(#codeValueClass)" />

            <po:copyButton
             id="copy_curateCrForm_role_type"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.type.id)}', 'curateRoleForm.role.type');"
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:select
                   id="curateRoleForm.role.type"
                   label="%{getText('organizationalContact.type')}"
                   name="cr.type"
                   list="orgContactTypes"
                   listKey="id"
                   listValue="code"
                   value="cr.type.id"
                   headerKey="" headerValue="--Select a Contact Type--"
                   required="true" cssClass="required"
                   />
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../person/crInfoMailable.jsp" %>
        <%@ include file="../../curate/crInfoContactable.jsp" %>
    </div>
</s:form>
