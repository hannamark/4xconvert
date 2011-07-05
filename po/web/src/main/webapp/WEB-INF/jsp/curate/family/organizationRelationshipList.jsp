<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="box_outer">
    <div class="box">
        <h2><fmt:message key="organizationRelationship.list.title"/></h2>
        <ajax:displayTag id="organizationRelationships" tableClass="data">
            <display:table class="data" sort="list" uid="row" name="organizationRelationships" >
                <po:displayTagProperties />
                <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
                <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
                <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
            
                <fmt:message key="organizationRelationship.list.relationship.header" var="relationshipTitle">
                    <fmt:param value="${familyOrgRelationship.organization.name}"/>
                </fmt:message>
                <display:column title="${relationshipTitle}" sortable="false">
                    <c:choose>
                        <c:when test="${row.hierarchicalType == null}">
                            <fmt:message key="organizationRelationship.list.noRelationship">
                                <fmt:param value="${row.relatedOrganization.name}"/>
                            </fmt:message>
                            <c:url value="/protected/popup/organization/relationship/create/input.action" var="changeUrl">
                                <c:param name="familyOrgId" value="${familyOrgRelationship.id}"/>
                                <c:param name="orgRelationship.organization.id" value="${row.relatedOrganization.id}"/>
                            </c:url>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="organizationRelationship.list.relationship">
                                <fmt:param value="${row.relatedOrganization.name}"/>
                                <fmt:param value="${row.hierarchicalType}"/>
                            </fmt:message>
                            <c:url value="/protected/popup/organization/relationship/curate/input.action" var="changeUrl">
                                <c:param name="orgRelationship.id" value="${row.id}"/>
                            </c:url>
                        </c:otherwise>
                    </c:choose>
                    <a href="javascript://noop/" onclick="showPopWin('${changeUrl}', 800, 400);">(change)</a>
                    <c:if test="${row.id != null}">
                        <a href="javascript://noop/" onclick="removeOrgRelationship('${row.id}');">(remove)</a>
                    </c:if>
                </display:column>
                <display:column titleKey="organizationRelationship.list.startDate" property="startDate" sortable="false" format="{0, date, MM-dd-yyyy}"/>
            </display:table>
        </ajax:displayTag>
    </div>
</div>