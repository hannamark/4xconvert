<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.participatingSites.header" /></a></h2>
<div id="participatingSitesSection">
    <table class="form-table">
        <tbody>            
             <viewer:titleRow titleKey="studyProtocol.participatingSites"/>        
             <viewer:valueRow labelKey="adHocReport.byFamily">              
                <s:select id="participatingSiteFamilyId" name="criteria.participatingSiteFamilyId" list="families" listKey="key"
                          listValue="value" headerKey="0" headerValue="--All--"  cssStyle="display:inline"/>
            </viewer:valueRow>
            <viewer:valueRow labelKey="adHocReport.orgsByFamily">
                <div id="sites_choices">                       
                  <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/participatingSitesByFamily.jsp"/>                   
                </div> 
            </viewer:valueRow>           
        </tbody>
    </table> 
</div>

   


