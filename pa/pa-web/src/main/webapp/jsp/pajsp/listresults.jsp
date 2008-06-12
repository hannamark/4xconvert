<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ajax:displayTag id="contentwide" ajaxFlag="true" tableClass="data">
    <display:table class="its" uid="row" name="${param.listName}" >
        <display:column title="NCI Identifier" property="nciIdentifier"/>
        <display:column title="Long Title" property="longTitleText"/>
        <display:column title="Date" property="statusDate"/>
        <display:column title="Study Type Code" property="studyTypeCode"/>
        <display:column title="Lead OrganizationName" property="leadOrganizationName"/> 
        <display:column title="Principal Investigator Full Name" property="principalInvestigatorFullName"/>       
    </display:table>
</ajax:displayTag>
