<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.geographicArea.header" /></a></h2>
<div id="geographicAreaSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="country" labelKey="studyProtocol.country">
                <s:set name="countries"  value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getCountries()" />
                <s:select headerKey="" headerValue="All" id="country" name="criteria.countryName"  
                          list="#countries" listKey="alpha3" listValue="name" value="criteria.countryName" cssStyle="width:206px" />
            </viewer:valueRow>
             <viewer:valueRow labelKey="studyProtocol.state">
                <s:set name="stateCodeValues" value="@gov.nih.nci.pa.enums.USStateCode@values()" />
                  <s:select headerKey="" headerValue="All" id="states" name="criteria.states" 
                            list="#stateCodeValues" listKey="name" listValue="code" value="criteria.states" cssStyle="width:206px" multiple="true" size="3"/>
            </viewer:valueRow>
             <viewer:valueRow labelFor="city" labelKey="studyProtocol.city">
                <s:textfield id="city" name="criteria.city" maxlength="200" size="100" cssStyle="width:206px"  />
            </viewer:valueRow>
        </tbody>
    </table> 
</div>