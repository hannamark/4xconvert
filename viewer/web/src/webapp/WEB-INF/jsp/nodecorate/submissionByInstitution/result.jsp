<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
 <s:if test="%{resultList != null}">
   <table id="resultTable" width="100%">
     <tr>
       <td colspan="2">
         <h2><fmt:message key="sbiReport.title"/></h2>
       </td>
     </tr>
     <tr>
       <td colspan="2">
         <fmt:message key="sbiReport.header.institutions"/>
         <s:label name="selectedInstitutions"/>
       </td>
     </tr>
     <tr>
       <td>
         <fmt:message key="sbiReport.header.date1"/>
         <s:label name="criteria.intervalStartDate"/>
         <fmt:message key="sbiReport.header.date2"/>
         <s:label name="criteria.intervalEndDate"/>
       </td>
       <td>
         <fmt:message key="report.header.user"/>
         <viewer:displayUser />
       </td>
     </tr>
     <tr>
       <td><br/>
       </td>
     </tr>
     <tr>
       <td colspan="2">
         <display:table class="data" pagesize="20" id="row" name="sessionScope.displayTagList" 
                    requestURI="resultsSubmissionByInstitution.action" export="true">
           <display:setProperty name="export.excel" value="true" />
           <display:setProperty name="export.excel.filename" value="SubmissionByInstitutionReport.xls" />
           <display:column titleKey="sbiReport.result.assignedIdentifier" property="assignedIdentifier" sortable="true"/>
           <display:column titleKey="sbiReport.result.submissionType" property="submissionType" sortable="true"/>
           <display:column titleKey="sbiReport.result.submitterOrg" property="submitterOrg" sortable="true"/>
           <display:column titleKey="sbiReport.result.leadOrgTrialIdentifier" property="leadOrgTrialIdentifier" sortable="true"/>
           <display:column titleKey="sbiReport.result.leadOrg" property="leadOrg" sortable="true"/>
           <display:column titleKey="sbiReport.result.submissionDate" property="submissionDate" sortable="true"/>
           <display:column titleKey="sbiReport.result.dws" property="dws" sortable="true"/>
           <display:column titleKey="sbiReport.result.dwsDate" property="dwsDate" sortable="true"/>
           <display:column escapeXml="true" titleKey="sbiReport.result.milestone" sortable="true" headerClass="sortable">
             <s:if test="%{#attr.row.adminMilestone == null && #attr.row.scientificMilestone == null}">
               <c:out value="${row.milestone}" />
               <c:out value="${row.milestoneDate}"/>
             </s:if>
           </display:column>
           <display:column escapeXml="true" titleKey="sbiReport.result.adminMilestone" sortable="true" headerClass="sortable">
             <c:out value="${row.adminMilestone}" />
             <c:out value="${row.adminMilestoneDate}"/>
           </display:column>
           <display:column escapeXml="true" titleKey="sbiReport.result.scientificMilestone" sortable="true" headerClass="sortable">
             <c:out value="${row.scientificMilestone}" />
             <c:out value="${row.scientificMilestoneDate}"/>
           </display:column>
         </display:table>
       </td>
     </tr>
   </table>
 </s:if>