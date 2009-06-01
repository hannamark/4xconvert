<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="irb.main.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/popuplookuporgs.action" var="lookupUrl" />
<script type="text/javascript">
function irbSave(){
     document.irbForm.action="irbsave.action";
     document.irbForm.submit();     
}
function statusChange() {
    var newStatus=document.irbForm.approvalStatus.value;
    var rads = document.irbForm.siteRelated;
    if(newStatus=="Submission not required"){
        document.irbForm.approvalNumber.disabled=true;
        document.getElementById('name').disabled=true;
        setContactDisabled(true);
        document.irbForm.contactAffiliation.disabled=true;
    }else if(newStatus=="Submitted, approved"){
        document.irbForm.approvalNumber.disabled=false;
        document.getElementById('name').disabled=false;
        if(document.irbForm.boardChanged.value!="true"){
            setContactDisabled(false);
        }
        document.irbForm.contactAffiliation.disabled=false;    
    }else if(newStatus=="Submitted, exempt"){
        document.irbForm.approvalNumber.disabled=false;
        document.getElementById('name').disabled=false;
        if(document.irbForm.boardChanged.value!="true"){
            setContactDisabled(false);
        }
        document.irbForm.contactAffiliation.disabled=false;    
    }else{
        document.irbForm.approvalNumber.disabled=true;
        document.getElementById('name').disabled=true;
        setContactDisabled(true);
        document.irbForm.contactAffiliation.disabled=true;    
    }
    if(document.irbForm.siteRelated[1].checked){
        document.irbForm.contactAffiliation.disabled=true;
    }
}
function setContactDisabled(value){
    document.getElementById('address').disabled=value;
    document.getElementById('city').disabled=value;
    document.getElementById('state').disabled=value;
    document.getElementById('zip').disabled=value;
    document.getElementById('country').disabled=value;
    document.getElementById('phone').disabled=value;
    document.getElementById('email').disabled=value;
}
function changeName(){
    var orgid = document.irbForm.id.value;
    document.irbForm.action='irbfromPO.action?orgId='+orgid;
    document.irbForm.submit();
}
// do not remove these two callback methods!
function setpersid(persid){}
function setorgid(orgid){}
function lookup(){
    if(document.getElementById('name').disabled!=true){
        showPopWin('${lookupUrl}', 900, 400, '', 'Organization');
    }
}   
function loadDiv(orgid){
    document.irbForm.action='irbfromPO.action?orgId='+orgid;
    document.irbForm.submit();
    return false;
}
</script>

</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="irb.main.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if> 
    <h2>
        <fmt:message key="irb.details.title" />
    </h2>
    
    <s:form name="irbForm">
     <table class="form"><tr>
        <s:hidden name="selectedArmIdentifier"/>
        <s:hidden name="boardChanged"/> 
        <s:hidden name="newOrgId"/>
        <s:hidden name="newOrgName"/> 
         <s:hidden name="ct.id"/> 
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td class="label">Board Approval Status:<span class="required">*</span></td>
            <s:set name="approvalStatusValues"
                value="@gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode@getDisplayNames()" />
            <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--" 
                name="approvalStatus" list="#approvalStatusValues" 
                onchange="statusChange()" onfocus="statusChange()"/></td>
        </tr>
        <tr>
            <td class="label">Board Approval Number:</td>
            <td class="value" colspan="2"><s:textfield name="approvalNumber" maxlength="30" size="30" cssStyle="width:200px;float:left"/></td>
        </tr>
        <tr>
             <td class="label">Board Name:</td>
             <td class="value"> 
               <s:textfield name="ct.name" id="name" size="30"  readonly="true" cssClass="readonly" onchange="changeName()"/></td>
              <td>  <ul style="margin-top:-3px;">
                    <li style="padding-left:0">
                        <a href="#" class="btn" onclick="lookup();"><span class="btn_img"><span class="search">Look Up</span></span></a>
                    </li>
                </ul>
             </td>
        </tr>
        <tr>
            <td class="label">Board Contact Mailing Address:</td>
            <td class="value" colspan="2">
            <s:textfield id="address" name="ct.address" cssStyle="width:280px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
         <tr>
            <td class="label">Board Contact City:</td>
            <td class="value" colspan="2">
            <s:textfield id="city" name="ct.city" cssStyle="width:140px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
         <tr>
            <td class="label">Board Contact State/Province:</td>
            <td class="value" colspan="2"><s:textfield id="state" name="ct.state" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
         <tr>
            <td class="label">Board Contact Zip/Postal Code:</td>
            <td class="value" colspan="2">
            <s:textfield id="zip" name="ct.zip" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
         <tr>
            <td class="label">Board Contact Country:</td>
            <td class="value" colspan="2">
            <s:textfield id="country" name="ct.country" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
        <tr>
            <td class="label">Board Contact Phone:</td>
            <td class="value" colspan="2">
            <s:textfield id="phone" name="ct.phone" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
        <tr>
            <td class="label">Board Contact Email Address:</td>
            <td class="value" colspan="2">
            <s:textfield id="email" name="ct.email" cssStyle="width:140px;float:left" readonly="true" cssClass="readonly"/></td>
        </tr>
        <tr>
            <td class="label">Board Affiliation:</td>
             <td colspan="2" class="value">
            <s:textarea name="contactAffiliation" cssStyle="width:606px" rows="4"/></td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <li><s:a href="#" cssClass="btn" onclick="irbSave();"><span class="btn_img"> <span class="save">Save</span></span></s:a></li>
        <li><a href="regulatoryInfoquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
        <li><a href="trialIndidequery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>