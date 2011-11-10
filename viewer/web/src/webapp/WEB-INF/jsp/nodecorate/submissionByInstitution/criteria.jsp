<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>   

<div class="criteriaSection">

    <table class="form-table">
        <tbody>
        
           <viewer:titleRow titleKey="sbiReport.criteria.dates"/>
           
            <viewer:valueRow labelFor="intervalStartDate" labelKey="sbiReport.criteria.intervalStartDate" required="true">
                <s:textfield id="intervalStartDate" name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
            </viewer:valueRow>
        
            <viewer:valueRow labelFor="intervalEndDate" labelKey="sbiReport.criteria.intervalEndDate" required="true">
                <s:textfield id="intervalEndDate" name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
            </viewer:valueRow>
        
           <viewer:titleRow titleKey="sbiReport.institutions"/>
           
            <viewer:valueRow labelFor="institutions" labelKey="sbiReport.criteria.institutions" required="true">
                <s:select id="institutions" name="criteria.institutions" multiple="true" list="submitterOrganizations" size="12" headerKey="1" headerValue="All"/>
            </viewer:valueRow>  
            
            <viewer:titleRow titleKey="sbiReport.submissionType"/>  
            
            <viewer:valueRow labelFor="submissionType" labelKey="sbiReport.criteria.submissionType" required="true">
               <s:select id="submissionType" name="criteria.submissionType" list="#{'ORIGINAL':'Original', 'AMENDMENT':'Amendment', 'BOTH':'Both'}" />
            </viewer:valueRow> 
            
            <viewer:valueRow labelFor="ctep" labelKey="sbiReport.criteria.ctep" required="true">
               <s:checkbox id="ctep" name="criteria.ctep" />
            </viewer:valueRow>           
       
        </tbody>
    </table>
</div>