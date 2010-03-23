<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">	
    function loadDiv() {
        document.forms[0].action="lookupTreatmentSite.action";
        document.forms[0].submit();
    }
	
	function formReset(){
        document.getElementById("treatmentSiteName").value = '';
        document.getElementById("treatmentSiteCountry").value = 'USA';        
        document.getElementById("treatmentSiteCity").value = '';
        document.getElementById("treatmentSiteState").value = '';
        document.getElementById("treatmentSiteZip").value = '';
    }
    
    function populateTreatmentSiteField(id, name) {
        window.top.document.getElementsByName("userAccount.treatmentSiteIdentifier")[0].value = id;
        window.top.document.getElementsByName("userAccount.treatmentSite")[0].value = name.replace(/&apos;/g,"'");       
        window.top.hidePopWin(false); 
    }
	function setSearchFormVisible(){
		document.getElementById("searchOrgJsp").style.display="";
		document.getElementById("createOrgJsp").style.display="none";
	}

	function setCreateFormVisible(){
		document.getElementById("searchOrgJsp").style.display="none";
		document.getElementById("createOrgJsp").style.display="";
	}	
	function createOrg(){
		var orgName = document.getElementById("orgName").value;
		var stAddress = document.getElementById("orgAddress").value;
		var city = document.getElementById("orgCity").value;
		var state = document.getElementById("orgState").value;
		var zip = document.getElementById("orgZip").value;
		var country = document.getElementById("orgCountry").value;
		var phone = document.getElementById("orgPhone").value;
		var email = document.getElementById("orgEmail").value;
		var orgUrl = document.getElementById("orgUrl").value;
		var tty = document.getElementById("orgTty").value;
		var fax = document.getElementById("orgFax").value;
		var reqProperties = 'orgName='+orgName+'&orgStAddress='+stAddress+'&countryName='+country+'&cityName='+city+'&zipCode='+zip+'&stateName='+state+'&phoneNumber='+phone+'&email='+email+'&tty='+tty+'&url='+orgUrl+'&fax='+fax;
		document.forms[0].action="lookupCreateOrganization.action?" + reqProperties;
        document.forms[0].submit();
	}
</SCRIPT>

</head>

<body onload="window.top.centerPopWin();" class="submodal">
    <div class="box">
        <s:form>
        <accrual:failureMessage/>
            <s:label name="orgErrorMessage"/>
            <div id="searchOrgJsp">
                <table class="form">
                    <tr>
                        <td scope="row" class="label"><label>Name</label></td>
                        <td><s:textfield id="treatmentSiteName" name="treatmentSiteSearchCriteria.name" maxlength="200" size="100" cssStyle="width:200px"/></td>        
                        <td scope="row" class="label"><label>Country</label></td>
                        <td><s:select id="treatmentSiteCountry" name="treatmentSiteSearchCriteria.country" headerKey="USA" headerValue="United States" 
                                      list="treatmentSiteSearchCriteria.countries" listKey="alpha3" listValue="name" cssStyle="width:206px"/>
                        </td>       
                    </tr>
                    <tr>  
                        <td scope="row" class="label"><label>City</label></td>
                        <td><s:textfield id="treatmentSiteCity" name="treatmentSiteSearchCriteria.city" maxlength="200" size="100" cssStyle="width:200px"/></td>
                        <td scope="row" class="label"><label>State</label></td>
                        <td><s:textfield id="treatmentSiteState" name="treatmentSiteSearchCriteria.state" maxlength="75" size="20"/><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font></td>
                    </tr>
                    <tr>
                        <td scope="row" class="label"><label>Zip</label></td>
                        <td><s:textfield id="treatmentSiteZip" name="treatmentSiteSearchCriteria.zipCode" maxlength="75" size="20"/></td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>            
                                <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Org</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            </li>
                        </ul>   
                    </del>
                </div>
            </div>
            <div id="createOrgJsp" style="display:none">
				<jsp:include page="/WEB-INF/jsp/nodecorate/createOrg.jsp"/>
			</div>
            <div align="center">
                <s:if test="treatmentSites != null">
                    <s:set name="treatmentSites" value="treatmentSites" scope="request"/>
                    <display:table class="data" name="treatmentSites" pagesize="10" sort="list" uid="row" export="false" requestURI="lookupTreatmentSite.action">
                        <display:column title="PO-ID" property="id" headerClass="sortable"/>
                        <display:column title="Organization Name" property="name" headerClass="sortable"/> 
                        <display:column title="City" property="city" headerClass="sortable"/> 
                        <display:column title="State" property="state" headerClass="sortable"/>
                        <display:column title="Country" property="country" headerClass="sortable"/> 
                        <display:column title="Zip" property="zipCode" headerClass="sortable"/> 
                        <display:column title="Action" class="action" sortable="false">
                            <a href="#" class="btn" onclick="populateTreatmentSiteField('${row.id}', '${fn:replace(row.name,'\'','&apos;')}')">
                                <span class="btn_img"><span class="add">Select</span></span>
                            </a>  
                        </display:column>
                    </display:table>
                </s:if>        	       
            </div>
        </s:form>
    </div>
</body>
</html>