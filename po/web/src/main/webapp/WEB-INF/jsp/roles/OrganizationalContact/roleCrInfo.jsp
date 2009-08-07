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
            <script type="text/javascript">
                var crRoleTypesArray = new Array();
                <c:forEach var="crRoleType" items="${cr.types}" varStatus="crRoleTypeStatus">
                    crRoleTypesArray[${crRoleTypeStatus.index}] = '${crRoleType.id}';
                </c:forEach>
            </script>
            <po:copyButton
             id="copy_curateCrForm_role_types"
             onclick="selectValuesInMultiSelectField(crRoleTypesArray, 'curateRoleForm.role.types');"
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:select
                   id="curateRoleForm.role.types"
                   label="%{getText('organizationalContact.types')}"
                   name="cr.types"
                   list="cr.types"
                   listKey="id"
                   listValue="code"
                   value="%{cr.types.{id}}"
                   multiple="true"
                   />
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../person/crInfoMailable.jsp" %>
        <%@ include file="../../curate/crInfoContactable.jsp" %>
    </div>
</s:form>
