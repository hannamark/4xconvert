<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<title><s:text name="organization.details.title"/> : Error</title>
</head>
<body>

<p>
An error occurred when auto-merging this Organization's associated correlations into the selected duplicate of Organization. Please examine correlations associated to both the Organization and the duplicate of Organization and resolve any conflicts.
</p>

<p>
This typically indicates malformed phone, fax, and/or tty numbers in the associated correlations.  <s:property value="@gov.nih.nci.po.util.UsOrCanadaPhoneHelper@getPhoneFormatErrorMessage()" />
This can also indicate that auto-merging correlations results in an incompatible entity/role status combination, i.e.
the selected duplicate is <i>Pending</i> and one or more scoped roles being merged are <i>Active.</i>  
</p>

<po:successMessages/>
<s:actionerror/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="organization"/> Information (${organization.id})</h2>
        <div class="boxouter">
        <h2>Associated Roles</h2>
            <div class="box_white">
                <table class="data">
                <c:forEach items="${associatedPlayedRolesForOrganization}" var="item">
                    <tr>
                    <td>
                        <fmt:message key="${item.class.simpleName}"/> (${item.id})
                    </td>
                    <td>
                        <c:url var="editUrl" value="/protected/roles/organizational/${item.class.simpleName}/input.action">
                            <c:param name="organization" value="${organization.id}"/>
                            <c:param name="role.id" value="${item.id}"/>
                        </c:url>
                        <po:buttonRow>
                        <po:button href="${editUrl}" style="edit" text="Edit" id="edit_orgRole_id_${item.id}" target="_blank"/>
                        </po:buttonRow>
                    </td>
                    </tr>
                </c:forEach>
                <c:forEach items="${associatedScopedRolesForOrganization}" var="item">
                    <c:url var="editUrl" value="/protected/roles/person/${item.class.simpleName}/input.action">
                        <c:param name="person" value="${item.player.id}"/>
                        <c:param name="role.id" value="${item.id}"/>
                    </c:url>
                    <c:url var="curatePlayerUrl" value="/protected/person/curate/start.action">
                        <c:param name="person.id" value="${item.player.id}"/>
                    </c:url>
                    <tr>
                    <td>
                        <fmt:message key="${item.class.simpleName}"/> (${item.id}) for Person ID (${item.player.id})
                    </td>
                    <td>
                        <po:buttonRow>
                        <po:button href="${editUrl}" style="edit" text="Edit" id="edit_personRole_id_${item.id}" target="_blank"/>
                        <po:button href="${curatePlayerUrl}" style="edit" text="Edit Person" id="edit_player_id_${item.id}" target="_blank"/>
                        </po:buttonRow>
                    </td>
                    </tr>
                </c:forEach>
                </table>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2>Duplicate Of <s:text name="organization"/> Information (${duplicateOf.id})</h2>
        <div class="boxouter">
        <h2>Associated Roles</h2>
            <div class="box_white">
                <table class="data">
                <c:forEach items="${associatedPlayedRolesForDuplicateOfOrganization}" var="item">
                    <tr>
                    <td>
                        <fmt:message key="${item.class.simpleName}"/> (${item.id})
                    </td>
                    <td>
                        <c:url var="editUrl" value="/protected/roles/organizational/${item.class.simpleName}/input.action">
                            <c:param name="organization" value="${duplicateOf.id}"/>
                            <c:param name="role.id" value="${item.id}"/>
                        </c:url>
                        <po:buttonRow>
                        <po:button href="${editUrl}" style="edit" text="Edit" id="edit_orgRole_id_${item.id}" target="_blank"/>
                        </po:buttonRow>
                    </td>
                    </tr>
                </c:forEach>
                <c:forEach items="${associatedScopedRolesForDuplicateOfOrganization}" var="item">
                    <c:url var="editUrl" value="/protected/roles/person/${item.class.simpleName}/input.action">
                        <c:param name="person" value="${item.player.id}"/>
                        <c:param name="role.id" value="${item.id}"/>
                    </c:url>
                    <c:url var="curatePlayerUrl" value="/protected/person/curate/start.action">
                        <c:param name="person.id" value="${item.player.id}"/>
                    </c:url>
                    <tr>
                    <td>
                        <fmt:message key="${item.class.simpleName}"/> (${item.id}) for Person ID (${item.player.id})
                    </td>
                    <td>
                        <po:buttonRow>
                        <po:button href="${editUrl}" style="edit" text="Edit" id="edit_personRole_id_${item.id}" target="_blank"/>
                        <po:button href="${curatePlayerUrl}" style="edit" text="Edit Person" id="edit_player_id_${item.id}" target="_blank"/>
                        </po:buttonRow>
                    </td>
                    </tr>
                </c:forEach>
                </table>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
<div style="clear:left;"></div>

<div class="btnwrapper" style="margin-bottom:20px;">
    <c:url var="curateUrl" value="/protected/organization/curate/start.action">
        <c:param name="organization.id" value="${organization.id}"/>
    </c:url>
    <po:buttonRow>
        <po:button href="${curateUrl}" style="select_person" text="Back to Organization" id="org_id_${row.id}"/>
    </po:buttonRow>
</div>

</body>
</html>
