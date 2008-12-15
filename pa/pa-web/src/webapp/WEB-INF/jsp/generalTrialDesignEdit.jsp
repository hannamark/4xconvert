<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.general.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>

     <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
     <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
     <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>

<!-- /po integration -->    
 <script type="text/javascript"> 
    var orgid;
    var persid;
    function handleAction(){
        document.forms[0].action="generalTrialDesignupdate.action";
        document.forms[0].submit(); 
    }    
    function lookup4loadresponsibleparty(){
        orgid = document.getElementById('generalTrialDesignquery_gtdDTO_sponsorIdentifier').value;  
        showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 1050, 400, createOrgContactDiv, 'Select Responsible contact');
    }

    function setorgid(orgIdentifier){
        orgid = orgIdentifier;
    }
    function setpersid(persIdentifier){
        persid = persIdentifier;
    }
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn"); 
    }
    function lookupCentralContact(){
        showPopWin('${lookupPersUrl}', 1050, 400, loadCentralContactDiv, 'Select Central Contact');
    }
    
    function loadCentralContactDiv() {
        var url = 'ajaxTrialValidationdisplayCentralContact.action?persId='+persid;
        var div = document.getElementById('loadCentralContactDiv');   
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        callAjax(url, div);    
    }

    function loadDiv(orgid){
    }
    function loadPersDiv(persid, func) {
    }
    function callAjax(url, div){
        var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
        return false;
    }
function manageRespPartyLookUp(){
	//alert(document.getElementById('trialValidationquery_gtdDTO_responsiblePartyTypepi').checked == true);
/*	if(document.getElementById('generalTrialDesignquery_gtdDTO_responsiblePartyTypepi').checked==true) {							
			document.getElementById('rpcid').style.display='none';
	}
	if(document.getElementById('generalTrialDesignquery_gtdDTO_responsiblePartyTypesponsor').checked==true) {	
			document.getElementById('rpcid').style.display='';
	}
	*/
}    
</script>
	
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="studyProtocol.general.title" /></h1>

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

    <tr>
    	<td scope="row" class="label">
           <label for="nciIdentifier">
                    <fmt:message key="studyProtocol.nciIdentifier"/>
           </label>
         </td>
         <td class="value">
    		<c:out value="${sessionScope.trialSummary.nciIdentifier}"/>
    	</td>
    </tr>
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    NCT Number
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.nctIdentifier" maxlength="30" cssStyle="width:106px" /> 
        </td>
    </tr>

    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    Lead Organization Trial Identifier <span class="required">*</span>
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.localProtocolIdentifier" cssStyle="width:206px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.localProtocolIdentifier</s:param>
                   </s:fielderror>                            
                 </span>
        </td>
    </tr>
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
        <s:textfield name="gtdDTO.acronym" cssStyle="width:86px" maxlength="12"/> 
        </td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for=briefTitle>
                    <fmt:message key="studyProtocol.briefTitle"/>
                 <span class="required">*</span>
           </label>
         </td>
         <td class="value">
    		<s:textarea name="gtdDTO.publicTitle" cssStyle="width:606px" rows="4"/> 
    		<span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.publicTitle</s:param>
                   </s:fielderror>                            
                 </span>
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
           <label for=briefSummary>
                    <fmt:message key="studyProtocol.briefSummary"/><span class="required">*</span>
           </label>
         </td>
         <td class="value">
    		<s:textarea name="gtdDTO.publicDescription" cssStyle="width:606px" rows="4"/>
    		 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.publicDescription</s:param>
                   </s:fielderror>                            
                 </span>  
    	</td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for=detailedDescription>
                    <fmt:message key="studyProtocol.detailedDescription"/>
           </label>
         </td>
         <td class="value">
   			 <s:textarea name="gtdDTO.scientificDescription" cssStyle="width:606px" rows="4"/> 
   		</td>
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
    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>
    <tr>
        <th colspan="2"> Central Contact</th>
    </tr>    
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    Central Contact
                 <span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadCentralContactDiv">
            <%@ include file="/WEB-INF/jsp/nodecorate/centralContact.jsp" %>
            </div>      
        </td>
    </tr>
        <tr>
            <td scope="row" class="label">
               Email Address:<span class="required">*</span>
            </td>
            <td class="value">
                <s:textfield name="gtdDTO.centralContactEmail"  maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.centralContactEmail</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
            <td class="value">
                <s:textfield name="gtdDTO.centralContactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.centralContactPhone</s:param>
                   </s:fielderror>                            
                 </span>
            </td>           
        </tr>             
    
    </table>  
         <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                    <li><a href="#" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="#" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
                </ul>   
            </del>

        </div>          
    </s:form>
   </div>

 </body>
 </html>