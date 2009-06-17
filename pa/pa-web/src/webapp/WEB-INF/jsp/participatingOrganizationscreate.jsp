<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.subtitle" /></title>
<s:head/>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" src="scripts/js/prototype.js"></script>
<script type="text/javascript" src="scripts/js/scriptaculous.js"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/protected/popuplookuporgs.action" var="lookupUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
<c:url value="/protected/popuplookupcontactpersons.action" var="lookupContactPersonsUrl"/>
<style type="text/css"> 
.disabled 
{ 
 background-color: #CCC; 
} 
</style> 

<script type="text/javascript">

    addCalendar("Cal1", "Select Date", "recStatusDate", "facility");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");
	function facilitySave(){		
		document.facility.action="participatingOrganizationsfacilitySave.action";
		document.facility.submit();
	}
	function facilityUpdate(){
		var recStatus;
		var recStatusDate;

        recStatus = document.facility.recStatus.value;		 
        recStatusDate = document.facility.recStatusDate.value;
        targetAccrualNumber = document.facility.targetAccrualNumber.value;
		
	     var div = document.getElementById('loadOrgDetails'); 
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	     var url;
	     url = '/pa/protected/ajaxptpOrgfacilityUpdate.action?recStatus='+recStatus
            +'&recStatusDate='+recStatusDate+'&targetAccrualNumber='+targetAccrualNumber;
      	 var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	     });
	}	
	function lookup(){
	    showPopWin('${lookupUrl}', 900, 400, '', 'Organization');
	}	
	function lookupperson(){
	    showPopWin('${lookupPersonsUrl}', 900, 400, '', 'Persons');
	}	
	function lookupcontactperson(){
		var tel = document.getElementById('personContactWebDTO_telephone').value;
		var email = document.getElementById('personContactWebDTO_email').value;
		var url = '${lookupContactPersonsUrl}?tel='+tel+'&email='+email;
		
	    showPopWin(url, 900, 400, '', 'Persons');
	}
	function loadDiv(orgid){
			 var url = '/pa/protected/ajaxptpOrgdisplayOrg.action?orgId='+orgid;
	     var div = document.getElementById('orgDetailsDiv');   
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
	     var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	     });
	     return false;		
	}	
	
	function loadPersDiv(persid, rolecode, func){
		 
         if(func!="add"){
            input_box=confirm("Click OK to un-link the Investigator from the Study.  Cancel to Abort.");
            if (input_box!=true){
                return;
            }
         }
         
	     var div = document.getElementById('saveAndShowContacts');   
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	     var url;
	     if(func == "add"){
		     	url = '/pa/protected/ajaxptpOrgsaveStudyParticipationContact.action?persid='+persid+'&rolecode='+rolecode;
		    	 var aj = new Ajax.Updater(div, url, {
			        asynchronous: true,
			        method: 'get',
			        evalScripts: false
			     });		     	
	     } else {
		     	url = '/pa/protected/ajaxptpOrgdeleteStudyPartContact.action?persid='+persid;
		    	 var aj = new Ajax.Updater(div, url, {
			        asynchronous: true,
			        method: 'get',
			        evalScripts: false
			     });
			     //setTimeout("refreshPrimaryContact();",1000);	     
		 }
	     return false;
	}
	
	/**
	 * This function is called when a primary contact is chosen from the person search pop up.
	 */
	function loadContactPersDiv(persid){	
	    var div = document.getElementById('showPrimaryContacts');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	    var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action?contactpersid='+persid;
	    	var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}
	/**
	 * Not sure if this one below is called ever!
	 */
	function setAsPrimaryContact(persId) {	
	    var div = document.getElementById('saveAndShowPersons');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	    var url;
	    var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action?contactpersid='+persId;
	    var aj = new Ajax.Updater(div, url, {
	       asynchronous: true,
	       method: 'get',
	       evalScripts: false
	    });
	    return false;
	}
	function loadContactPersDivEditMode(persid) {		
	    var div = document.getElementById('showPrimaryContacts');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	    var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action?contactpersid='+persid+"&editmode=yes";
	    	var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;	
	}
	function refreshPrimaryContact(){	
	 	var contactpersid = document.getElementById('personContactWebDTO_selectedPersId').value;
 		 var div = document.getElementById('showPrimaryContacts');   
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	     var url = '/pa/protected/ajaxptpOrgrefreshPrimaryContact.action?contactpersid='+contactpersid;
	    	var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	     });
	    return false;		
	}
	function savePrimaryContact(){	
		 var contactpersid = document.getElementById('personContactWebDTO_selectedPersId').value;
		 var email = document.getElementById('personContactWebDTO_email').value;
		 var tel = document.getElementById('personContactWebDTO_telephone').value;
 		 var div = document.getElementById('showPrimaryContacts');   
	     div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
	     var url = '/pa/protected/ajaxptpOrgsaveStudyParticipationContact.action?contactpersid='+contactpersid+"&tel="+tel+"&email="+email;
	    	var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	     });
	    return false;
	}
	// do not remove these two callback methods!
	function setpersid(persid){
	}
	function setorgid(orgid){
	}

</script>      
</head>
<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
<h1><fmt:message key="participatingOrganizations.subtitle" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<pa:failureMessage/>
 <div class="error_msg"><s:actionerror/></div>
  
<h2><fmt:message key="participatingOrganizations.subtitle" /></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
    <tr><td colspan="2"><!--Tabs -->

			<ul id="maintabs" class="tabs">
			<li><a href="#facility"><fmt:message key="participatingOrganizations.subtitle2" /></a></li>
			
			
			         <s:if test="%{currentAction == 'edit'}">
							<li><a href="#investigators">Investigators</a></li>
							<li><a href="#contacts">Contact</a></li>
				    </s:if>
				    <s:else>
							<li><a class="disabled">Investigators</a></li>
							<li><a class="disabled">Contact</a></li>				    
				    </s:else>
			
			
			

			</ul>
			<!--/Tabs --> <!-- 
                    directions on http://livepipe.net/control/tabs 
                    - make sure you add control.tabs.js to your scripts directory! 
                    - Matt 
                --> <script type="text/javascript">             
                    //<![CDATA[
                    Event.observe(window,'load',function(){
                        $$('.tabs').each(function(tabs){
                            new Control.Tabs(tabs);
                        });
                    });
                    //]]>
                </script>
			<div id="tabboxwrapper"><!--Facility-->
			<div id="facility" class="box">
            <s:form name="facility">
                <div id="loadOrgDetails">
                     <%@ include file="/WEB-INF/jsp/nodecorate/nodecororgdetails.jsp" %>
                </div>
			<div class="actionsrow"><del class="btnwrapper">
			<ul class="btnrow">
				<li>
                    <s:if test="%{currentAction == 'edit'}">
				        <s:a href="#" cssClass="btn" onclick="facilityUpdate();"><span class="btn_img">
				            <span class="save">Save</span></span></s:a>
				    </s:if>
				    <s:else>
                        <s:a href="#" cssClass="btn" onclick="facilitySave();"><span class="btn_img">
                            <span class="save">Save</span></span></s:a>
				    </s:else>
				</li>
			</ul>
			</del></div>
            </s:form>
			</div>
			<!--/Facility-->
			<!-----------------------------------------------Begin Investigators Tab------------------->
	   		<div id="investigators" class="box" style="display:none;">
	   		
				<h3>Participating Site Investigators <c:out value="${organizationName}"/></h3>
					<div id="saveAndShowContacts">
						<%@ include file="/WEB-INF/jsp/nodecorate/displaySPContactsTable.jsp" %>
					</div>	
				<div class="actionsrow"><del class="btnwrapper">
					<ul class="btnrow">
						<li><a href="#" class="btn" onclick="lookupperson();"><span
							class="btn_img"><span class="add">Add</span></span></a></li>
					</ul>
				</del>
				</div>
			</div>
	  <!-----------------------------------------------End Investigators Tab------------------->
	  <!-----------------------------------------------Begin Contact Tab------------------->
	  	   <div id="contacts" class="box" style="display:none;">						
				<h3>Primary Contact <c:out value="${organizationName}"/></h3>				
			
					<div id="showPrimaryContacts">
						<%@ include file="/WEB-INF/jsp/nodecorate/displayPrimaryContact.jsp" %>
					</div>	
			<div class="actionsrow"><del class="btnwrapper">
			<ul class="btnrow">
				<li>                   
 						<s:a href="#" cssClass="btn" onclick="savePrimaryContact();"><span class="btn_img">
                            <span class="save">Save</span></span></s:a>						  
			</ul>
			</del></div>
			</div>
	  <!-----------------------------------------------End Contact Tab------------------->
			</div>
			</td></tr>
    </table>
</div>
</body>
</html>