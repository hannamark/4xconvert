<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-4">
    <s:textfield label="Organization Name" name="trialDTO.leadOrganizationName" id="trialDTO.leadOrganizationName" size="30" readonly="true" cssClass="form-control"  />
    <span class="formErrorMsg">
    <s:fielderror>
        <s:param>trialDTO.leadOrganizationIdentifier</s:param>
    </s:fielderror>
</span> 
</div>
<div class="col-xs-4">
    <button type="button" onclick="lookup4loadleadorg();" title="Opens a popup form to select Lead Organization" class="btn btn-icon btn-default"><i class="fa-sitemap"></i>Look Up Org </button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Look Up Org, and select the principal administrative organization responsible for the research protocol from the list of search results."  data-placement="top" data-trigger="hover"></i>
</div>
