<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-4">
    <s:textfield label="Site PI Full Name" name="trialDTO.sitePiName" id="trialDTO.sitePiName" size="30"  readonly="true" cssClass="form-control"/>
    <span class="alert-danger" id="piIdentifierErr"> 
    <s:fielderror>
        <s:param>trialDTO.sitePiIdentifier</s:param>
    </s:fielderror>                            
</span>
</div>
<div class="col-xs-4">
    <button type="button" class="btn btn-icon btn-default" onclick="lookup4loadSitePerson();" title="Opens a popup form to select Principal Investigator"><i class="fa-user"></i>Look Up Person</button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='Enter the name of the individual who is responsible for all aspects of the conduct of a study at a site.'  data-placement="top" data-trigger="hover"></i>
</div>