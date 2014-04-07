<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-4">
    <s:textfield label="First Name" name="trialDTO.sponsorName" id="trialDTO.sponsorName" size="30" readonly="true" cssClass="form-control" />
    <span class="formErrorMsg" id="sponsorErr"> 
     <s:fielderror>
        <s:param>trialDTO.sponsorIdentifier</s:param>
    </s:fielderror>                            
</span>
</div>
<div class="col-xs-4">
    <button type="button" class="btn btn-icon btn-default" onclick="lookup4sponsor();" title="Opens a popup form to select Sponsor"><i class="fa-sitemap"></i>Look Up Org Sponsor</button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Look Up Sponsor, and select the name of the primary organization that oversees the implementation of the study and is responsible for data analysis as defined in 21 CFR 50.3."  data-placement="top" data-trigger="hover"></i>
</div>