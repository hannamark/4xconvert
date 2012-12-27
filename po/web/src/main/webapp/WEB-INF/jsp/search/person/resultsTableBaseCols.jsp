<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
        <display:column titleKey="search.person.id" property="id" sortable="true" sortProperty="PERSON_ID"/>
        <display:column title="CTEP ID" sortable="true" property="ctepID" sortProperty="CTEP_ID"/>        
		<display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" maxLength="30"/>
		<display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" maxLength="30"/>
        <display:column titleKey="person.email" sortable="true" property="emailAddresses" sortProperty="EMAIL"/>
        <display:column title="Organization Affiliation" sortable="false" class="orgNameColumn" media="html">
            <c:forEach items="${row.affiliation}" var="e">
                ${e.group} &mdash; <c:out value="${e.orgName}"/>
                <c:if test="${e.pending}">
                    <span class="pending">(P)</span>
                </c:if>
                <br/>
            </c:forEach>        
        </display:column>
        <display:column title="Organization Affiliation" sortable="false" class="orgNameColumn" 
            media="csv excel"><c:forEach items="${row.affiliation}" 
            var="e">${e.group} -- ${e.orgName} <c:if test="${e.pending}">(P)</c:if>
</c:forEach></display:column>
        <display:column title="CRS Role(s)" sortable="false" property="totalCrs" media="csv excel"/>         
        <display:column title="HCP Role(s)" sortable="false" property="totalHcp" media="csv excel"/>
        <display:column title="OC Role(s)" sortable="false" property="totalOc" media="csv excel"/>
        <display:column title="OPI Role(s)" sortable="false" property="totalOpi" media="csv excel"/>
        <display:column title="Total Pending Role(s)" sortable="false" property="totalPending" media="csv excel"/>
        <display:column title="Address 1" sortable="false" property="address1" media="csv excel"/>
        <display:column title="Address 2" sortable="false" property="address2" media="csv excel"/>
        <display:column titleKey="person.postalAddress.cityOrMunicipality" property="city" sortable="true" sortProperty="CITY" maxLength="20"/>
		<display:column titleKey="person.postalAddress.stateOrProvince" property="state" sortable="true" sortProperty="STATE" maxLength="20"/>
		<display:column title="Country" sortable="false" property="country" media="csv excel"/>
		<display:column title="Zip Code" sortable="false" property="zipCode" media="csv excel"/>
		<display:column title="Comments" sortable="false" property="comments" media="csv excel"/>
		<display:column title="Phone(s)" sortable="false" property="phones" media="csv excel"/>
        <display:column titleKey="person.statusCode" sortable="true" sortProperty="STATUS" property="statusCode" />
        <display:column title="Duplicate-of PO ID" sortable="false" property="duplicateOf" media="csv excel"/>