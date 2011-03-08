<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.general.title"/></title>
    <s:head />
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>

     <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
     <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
     <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
<!-- /po integration -->
 <script type="text/javascript">
    var orgid;
    var persid;
    var contactMail;
    var contactPhone;
    var selectedName;

    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();
    }

    function handleAction(){
        document.forms[0].action="generalTrialDesignupdate.action";
        document.forms[0].submit();
    }
    //function which handles the remove of the Central contact.
    function handleRemove(){
        document.forms[0].action="generalTrialDesignremoveCentralContact.action";
        document.forms[0].submit();
    }
    function lookup4loadresponsibleparty(){
       var orgid = document.getElementById('sponsorIdentifier').value;
       showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
    }

    function setorgid(orgIdentifier){
        orgid = orgIdentifier;
    }
    function setpersid(persIdentifier,name,email,phone){
        persid = persIdentifier;
        selectedName = name;
        contactMail = email;
        contactPhone = phone;
    }
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn");
    }
    function lookupCentralContact(){
    	showPopup('${lookupPersUrl}', loadCentralContactDiv, 'Select Central Contact');
    }

    function loadCentralContactDiv() {
        var url = 'ajaxTrialValidationdisplayCentralContact.action?persId='+persid;
        document.getElementById('gtdDTO.centralContactTitle').value = '';
        document.getElementById('gtdDTO.centralContactIdentifier').value =  persid;
        document.getElementById("gtdDTO.centralContactEmail").value = '';
        document.getElementById("gtdDTO.centralContactPhone").value = '';
        var div = document.getElementById('loadCentralContactDiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        callAjax(url, div);
    }
    function lookupGenericCentralContact(){
        var orgid = document.getElementById('gtdDTO.leadOrganizationIdentifier').value;
        showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Site', createGenericCentralContactDiv, 'Select Generic Contact');
    }
    function createGenericCentralContactDiv() {
       document.getElementById('gtdDTO.centralContactName').value = '';
       document.getElementById('gtdDTO.centralContactTitle').value = selectedName;
       document.getElementById('gtdDTO.centralContactIdentifier').value =  persid;
       document.getElementById("gtdDTO.centralContactEmail").value = contactMail;
       document.getElementById("gtdDTO.centralContactPhone").value = contactPhone;
    }

    function loadDiv(orgid){
    	document.getElementById('gtdDTO.centralContactName').value = "";
        document.getElementById('gtdDTO.centralContactTitle').value = "";
        document.getElementById('gtdDTO.centralContactIdentifier').value =  "";
        document.getElementById("gtdDTO.centralContactEmail").value = "";
        document.getElementById("gtdDTO.centralContactPhone").value = "";
    }
    function loadPersDiv(persid, func) {
    }
    function callAjax(url, div){
        var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
        return false;
    }
    
    
    function deleteOtherIdentifierRow(rowid){ 
        var  url = 'ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action?uuid='+rowid;
        var div = document.getElementById('otherIdentifierdiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
        callAjax(url, div);             
    }

    function addOtherIdentifier() {
        var orgValue=document.getElementById("otherIdentifierOrg").value;
        if (orgValue != null && orgValue != '') {
            var  url = 'ajaxManageOtherIdentifiersActionaddOtherIdentifier.action?otherIdentifier='+orgValue;    
            var div = document.getElementById('otherIdentifierdiv');   
            div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
            callAjax(url, div);
            document.getElementById("otherIdentifierOrg").value="";
        } else {
            alert("Please enter a valid Other identifier.");
        }
    }

    
</script>

</head>

<body>
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="studyProtocol.general.title" /></h1>
<c:set var="topic" scope="request" value="abstractgeneral"/>
<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>

  <div class="box">
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form >
    <s:actionerror/>
    <table class="form">
    <h2>General Trial Details</h2>
    <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
     <s:hidden name="gtdDTO.nonOtherIdentifiers.extension" id="gtdDTO.nonOtherIdentifiers.extension"/>
     <s:hidden name="gtdDTO.nonOtherIdentifiers.root" id="gtdDTO.nonOtherIdentifiers.root"/>
     <s:hidden name="gtdDTO.nonOtherIdentifiers.identifierName" id="gtdDTO.nonOtherIdentifiers.identifierName"/>
    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
        <s:hidden name="gtdDTO.phaseCode" id= "gtdDTO.phaseCode"></s:hidden>
        <s:hidden name="gtdDTO.phaseAdditionalQualifierCode" id= "gtdDTO.phaseAdditionalQualifierCode"></s:hidden>
        <s:hidden name="gtdDTO.primaryPurposeCode" id= "gtdDTO.primaryPurposeCode"></s:hidden>
        <s:hidden name="gtdDTO.primaryPurposeAdditionalQualifierCode" id= "gtdDTO.primaryPurposeAdditionalQualifierCode"></s:hidden>
        <s:hidden name="gtdDTO.primaryPurposeOtherText" id= "gtdDTO.primaryPurposeOtherText"></s:hidden>        
        <tr>
        <td scope="row" class="label">
          <a href="http://ClinicalTrials.gov">ClinicalTrials.gov</a> XML required?
        </td>
        <td>
          <s:radio name="gtdDTO.ctGovXmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
       </td>
       </tr>
    </c:if>
    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
        <s:hidden name="gtdDTO.centralContactName" id="gtdDTO.centralContactName"></s:hidden>
        <s:hidden name="gtdDTO.centralContactTitle" id="gtdDTO.centralContactTitle"></s:hidden>
        <s:hidden name="gtdDTO.centralContactIdentifier" id="gtdDTO.centralContactIdentifier"></s:hidden>
        <s:hidden name="gtdDTO.centralContactEmail" id="gtdDTO.centralContactEmail"></s:hidden>
        <s:hidden name="gtdDTO.centralContactPhone" id="gtdDTO.centralContactPhone"></s:hidden>
    </c:if>
    <tr>
        <td scope="row" class="label"><label for="LocalProtocolIdentifier">
             <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/><span class="required">*</span></label> </td>
        <td><s:textfield name="gtdDTO.localProtocolIdentifier" id="gtdDTO.localProtocolIdentifier"/>
        <span class="formErrorMsg">
             <s:fielderror>
               <s:param>gtdDTO.localProtocolIdentifier</s:param>
             </s:fielderror>
          </span> </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="Nct Identifier">
             <fmt:message key="studyProtocol.nctNumber"/></label> </td>
        <td><s:textfield name="gtdDTO.nctIdentifier" id="gtdDTO.nctIdentifier"/>
        <span class="formErrorMsg">
             <s:fielderror>
               <s:param>gtdDTO.nctIdentifier</s:param>
             </s:fielderror>
          </span> </td>
    </tr>
    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
    <tr>
        <td scope="row" class="label"><label for="CtepIdentifier">
             <fmt:message key="studyProtocol.ctepId"/></label> </td>
        <td><s:textfield name="gtdDTO.ctepIdentifier" id="gtdDTO.ctepIdentifier"/>
        <span class="formErrorMsg">
             <s:fielderror>
               <s:param>gtdDTO.ctepIdentifier</s:param>
             </s:fielderror>
          </span> </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="dcpIdentifier">
             <fmt:message key="studyProtocol.dcpId"/></label> </td>
        <td><s:textfield name="gtdDTO.dcpIdentifier" id="gtdDTO.dcpIdentifier"/>
        <span class="formErrorMsg">
             <s:fielderror>
               <s:param>gtdDTO.dcpIdentifier</s:param>
             </s:fielderror>
          </span> </td>
    </tr>
    </c:if>

    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
        <%@ include file="/WEB-INF/jsp/nodecorate/phaseAndPurpose.jsp" %>
    </c:if>
    <tr>
        <th colspan="2"> Title</th>
    </tr>

    <tr>
        <td scope="row" class="label">
           <label for="acronym">
                    <fmt:message key="studyProtocol.acronym"/>
           </label>
         </td>
         <td class="value">
        <s:textfield name="gtdDTO.acronym" cssStyle="width:86px" maxlength="14"/>
        </td>
    </tr>
     <tr>
       <td scope="row" class="label">
          <label for="officialTitle">
                   <fmt:message key="studyProtocol.officialTitle"/>
              <span class="required">*</span>
          </label>
       </td>
       <td class="value">
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4"/>
            <span class="formErrorMsg">
                    <s:fielderror>
                    <s:param>gtdDTO.officialTitle</s:param>
                   </s:fielderror>
                 </span>
       </td>
    </tr>
    <tr>
        <th colspan="2"> <fmt:message key="studyProtocol.trialDescription"/></th>
    </tr>
    <tr>
        <td scope="row" class="label">
           <label for=keywordText><fmt:message key="studyProtocol.keywordText"/>
           </label>
         </td>
         <td class="value">
             <s:textarea name="gtdDTO.keywordText" cssStyle="width:606px" rows="4"/>
        </td>

    </tr>
    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
    <tr>
        <th colspan="2">Other Identifiers</th>
    </tr>
    <tr>
        <td scope="row" class="label">
            <label for="submitTrial_protocolWebDTO_otherIdentifiers">Other Identifier</label>
        </td>
        <td>
            <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" />&nbsp; 
            <input type="button" id="otherIdbtnid" value="Add Other Identifier" onclick="addOtherIdentifier();" />
        </td>
    </tr>
    <tr>
        <td colspan="2" class="space">
            <div id="otherIdentifierdiv">
                <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp"%>
            </div>
        </td>
    </tr>
    </c:if>
    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>
    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
    <tr>
        <th colspan="2"> Central Contact</th>
    </tr>
    <tr>
            <td/>
            <td class="info" colspan="2">Assign values to all fields below if central contact information is recorded; otherwise leave these fields empty.</td>
     </tr>
    <tr >
        <td scope="row" class="label" >
            <label for="nciIdentifier"> Personal Contact </label>
        </td>
        <td class="value">
            <div id="loadCentralContactDiv">
            <%@ include file="/WEB-INF/jsp/nodecorate/centralContact.jsp" %>
            </div>
        </td>
      </tr>
      <tr>
    <td> OR    </td>
    </tr>
    <tr>
    <td scope="row" class="label"><s:label for="Generic Contact">Generic Contact:</s:label></td>
    <td>
    <table>
        <tr>
        <td><s:textfield label="Central Contact title" name="gtdDTO.centralContactTitle" id="gtdDTO.centralContactTitle" size="30"
            readonly="true" cssClass="readonly" cssStyle="width:200px"/>
        </td>
        <td>
             <ul style="margin-top:-1px;"><li style="padding-left:0">
            <a href="#" class="btn" onclick="lookupGenericCentralContact();" title="Opens a popup form to select Central Contact"/>
            <span class="btn_img"><span class="person">Look Up Generic Contact</span></span></a></li></ul>
        </td>
      </tr>
    </table>
    </td>
    </tr>
        <tr>
            <td scope="row" class="label">
               Email Address:
            </td>
            <td class="value">
                <s:textfield name="gtdDTO.centralContactEmail" id="gtdDTO.centralContactEmail" maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg">
                    <s:fielderror>
                    <s:param>gtdDTO.centralContactEmail</s:param>
                   </s:fielderror>
                 </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">Phone Number:</td>
            <td class="value">
                <s:textfield name="gtdDTO.centralContactPhone" id="gtdDTO.centralContactPhone" maxlength="200" size="15"  cssStyle="width:100px" />
                Extn:<s:textfield name="gtdDTO.centralContactPhoneExtn" id="gtdDTO.centralContactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" />
                <span class="formErrorMsg">
                    <s:fielderror>
                    <s:param>gtdDTO.centralContactPhone</s:param>
                   </s:fielderror>
                 </span>

            </td>
        </tr>
    </c:if>
    </table>
         <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                					|| (sessionScope.role == 'SuAbstractor')}">
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                 </c:if>
                 <!--
                   <li><a href="manageAccrualAccess.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                   <li><a href="nciSpecificInformationquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
                    -->
                </ul>
            </del>

        </div>
    </s:form>
   </div>

 </body>
 </html>