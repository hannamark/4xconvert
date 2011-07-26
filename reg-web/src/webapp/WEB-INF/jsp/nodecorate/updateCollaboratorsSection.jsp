<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="collaborators.size > 0" >
    <tr>
        <th colspan="2" >Collaborators</th>
    </tr>
    <tr>
        <td colspan="2" class="space">
            <table class="data2">
                <tr>
                    <td class="space">
                        <table class="form">
                            <tbody>
                                <tr>
                                    <th>Collaborator</th>
                                    <th>Functional Role</th>
                                </tr>
                                <s:iterator value="collaborators" id="collaborators" status="stat" >
                                    <tr>
                                        <td>
                                            <s:hidden name="collaborators[%{#stat.index}].id" />
                                            <s:hidden name="collaborators[%{#stat.index}].name" />
                                            <s:property value="%{name}"/>
                                        </td>
                                        <s:set name="functionalCodeValues" value="@gov.nih.nci.pa.enums.StudySiteFunctionalCode@getCollaboratorDisplayNames()"/>
                                        <td>
                                            <s:hidden  name="collaborators[%{#stat.index}].functionalRole" />
                                            <s:property value="%{functionalRole}"/>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr> 
</s:if>