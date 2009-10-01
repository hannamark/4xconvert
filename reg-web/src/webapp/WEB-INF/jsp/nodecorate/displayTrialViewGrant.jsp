<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(sessionScope.grantList) >0}">          
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
     name="${sessionScope.grantList}" requestURI="searchTrialview.action" export="false">    
     <display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode"   sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode"   sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
     <display:column title="Action" class="action" sortable="false">
        <c:choose>
            <c:when test="${row.id != null}">
            </c:when>
            <c:otherwise>
              <input type="button" value="Delete" onclick="deleteGrantRow('${row.rowId}')"/>
            </c:otherwise>
        </c:choose>
              
    </display:column>
</display:table>
</c:when>
<c:when test="${trialDTO.fundingDtos != null && fn:length(trialDTO.fundingDtos) >0}">          
<div class="box">
<h3>NIH Grant Information (for NIH funded Trials)</h3>  
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
     name="${trialDTO.fundingDtos}" requestURI="searchTrialview.action" export="false">    
    <display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
    </display:table>
</div>
</c:when>
</c:choose>