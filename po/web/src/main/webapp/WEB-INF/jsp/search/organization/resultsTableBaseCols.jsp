<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

    <display:column titleKey="search.organization.id" property="id"
        sortable="true" sortProperty="ID"/>
    <display:column titleKey="organization.name" property="name"
        sortable="true" sortProperty="NAME" />
    <display:column titleKey="organization.statusCode"
        property="statusCode" media="excel csv"/>       
    <display:column titleKey="organization.statusDate" property="statusDate" media="excel csv" format="{0,date,MM/dd/yyyy}"/>        
    <display:column titleKey="family.name" property="familyName"
        sortable="true" sortProperty="FAMILY" />
    <display:column titleKey="organization.changeRequests" media="excel csv">
            ${row.changeRequests==0?'':row.changeRequests}
    </display:column>
    <display:column titleKey="organization.totalROs" media="excel csv">
            ${row.totalROs==0?'':row.totalROs}
    </display:column>       
    <display:column titleKey="organization.totalHCFs" media="excel csv">
            ${row.totalHCFs==0?'':row.totalHCFs}
    </display:column>       
    <display:column titleKey="organization.totalIdOrgs" media="excel csv">
            ${row.totalIdOrgs==0?'':row.totalIdOrgs}
    </display:column>
    <display:column titleKey="organization.totalOversightCommitees" media="excel csv">
            ${row.totalOversightCommitees==0?'':row.totalOversightCommitees}
    </display:column>       
    <display:column titleKey="organization.totalOrgContacts" media="excel csv">
            ${row.totalOrgContacts==0?'':row.totalOrgContacts}
    </display:column>    
    <display:column titleKey="organization.pendingHCFs" media="excel csv">
           ${row.pendingHCFs==0?'':row.pendingHCFs}
    </display:column>
    <display:column titleKey="organization.pendingROs" media="excel csv">
             ${row.pendingROs==0?'':row.pendingROs}
    </display:column>

    <display:column titleKey="organization.hcfCtepId" property="hcfCtepId"
        sortable="true" sortProperty="HCF_CTEP_ID" />           
    <display:column titleKey="organization.roCtepId" property="roCtepId"
        sortable="true" sortProperty="RO_CTEP_ID" />
    <display:column titleKey="organization.ioCtepId" property="ioCtepId"
        sortable="true" sortProperty="IO_CTEP_ID" />    
        
    <display:column titleKey="organization.address1" property="address1" media="excel csv"/>       
    <display:column titleKey="organization.address2" property="address2" media="excel csv"/>
    <display:column titleKey="organization.city" property="city" media="excel csv"/>
    <display:column titleKey="organization.state" property="state" media="excel csv"/>
    <display:column titleKey="organization.country" property="country" media="excel csv"/>
    <display:column titleKey="organization.zipCode" property="zipCode" media="excel csv"/>
    <display:column titleKey="organization.comments" property="comments" media="excel csv"/>
    <display:column titleKey="organization.emailAddresses" property="emailAddresses" media="excel csv"/>
    <display:column titleKey="organization.phones" property="phones" media="excel csv"/>
    <display:column titleKey="organization.duplicateOfPoid" property="duplicateOf" media="excel csv"/>

    <display:column titleKey="organization.changeRequests" sortable="true" media="html"
        sortProperty="CR">
            ${row.changeRequests==0?'':row.changeRequests}
    </display:column>
    <display:column titleKey="organization.pendingHCFs" sortable="true" media="html"
        sortProperty="PENDING_HCF">
           ${row.pendingHCFs==0?'':row.pendingHCFs}
    </display:column>    
    <display:column titleKey="organization.pendingROs" sortable="true" media="html"
        sortProperty="PENDING_RO">
             ${row.pendingROs==0?'':row.pendingROs}
    </display:column>
    <display:column titleKey="organization.statusCode"
        property="statusCode" sortable="true" sortProperty="STATUS" media="html"/>
        