<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <display:table class="its" sort="list" pagesize="10" id="row"
    	name="${param.listName}" requestURI="/queryProtocol.action" export="false">    
        <display:column title="NCI Accession Number" property="nciIdentifier" sortable="true" headerClass="sortable"/>
        <display:column title="Title" property="longTitleText"   />
        <display:column title="Study Phase Code" property="studyPhaseCode.name"  />
        <display:column title="Trial Status" property="studyStatusCode.name"  />
        <display:column title="Trial Status Date" property="studyStatusDate"   />
        <display:column title="Lead Organization" property="leadOrganizationName"   />
        <display:column title="Principal Investigator " property="principalInvestigatorFullName"   />       
    </display:table>

