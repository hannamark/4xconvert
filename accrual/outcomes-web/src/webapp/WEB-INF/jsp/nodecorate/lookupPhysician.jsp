<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">	
    function loadDiv() {
        document.forms[0].action="lookupPhysician.action";
        document.forms[0].submit();
    }
	
	function formReset(){
        document.getElementById("physicianFirstName").value = '';
        document.getElementById("physicianLastName").value = '';                
        document.getElementById("physicianCity").value = '';
        document.getElementById("physicianState").value = '';
        document.getElementById("physicianCountry").value = 'USA';
        document.getElementById("physicianZip").value = '';
    }
    
    function populatePhysicianField(id, name) {
        window.top.document.getElementsByName("userAccount.physicianIdentifier")[0].value = id;
        window.top.document.getElementsByName("userAccount.physician")[0].value = name.replace(/&apos;/g,"'");       
        window.top.hidePopWin(false); 
    }
	function setSearchFormVisible(){
		document.getElementById("searchPersJsp").style.display="";
		document.getElementById("createPersJsp").style.display="none";
	}

	function setCreateFormVisible(){
		document.getElementById("searchPersJsp").style.display="none";
		document.getElementById("createPersJsp").style.display="";
	}
	function createPerson() {
		var fname = document.getElementById("firstName").value;	
		var lname = document.getElementById("lastName").value;		
		var prefx = document.getElementById("preFix").value;		
		var mname = document.getElementById("middleName").value;		
		var stadd = document.getElementById("streetAddress").value;		
		var city = document.getElementById("city").value;		
		var st = document.getElementById("state").value;		
		var zip = document.getElementById("zip").value;		
		var ct = document.getElementById("country").value;		
		var email = document.getElementById("email").value;		
		var phone = document.getElementById("phone").value;		
		var url = document.getElementById("url").value;		
		var tty = document.getElementById("tty").value;		
		var fax = document.getElementById("fax").value;		
		var suffix = document.getElementById("suffix").value;
		var reqProperties = 'firstName='+fname+'&lastName='+lname+'&preFix='+prefx+'&midName='+mname+'&streetAddr='+stadd+'&city='+city+'&state='+st+'&zip='+zip+'&country='+ct+'&email='+email+'&phone='+phone+'&tty='+tty+'&fax='+fax+'&url='+url+'&suffix='+suffix;
		document.forms[0].action="lookupCreatePerson.action?" + reqProperties;
        document.forms[0].submit();
	}
</SCRIPT>

</head>

<body onload="window.top.centerPopWin();" class="submodal">
    <div class="box">
        <s:form>
        <accrual:failureMessage/>
            <s:label name="orgErrorMessage"/>
            <div id="searchPersJsp">
                <table class="form">
                    <tr>
                        <td scope="row" class="label"><label>First Name</label></td>
                        <td><s:textfield id="physicianFirstName" name="physicianSearchCriteria.firstName" maxlength="200" size="100" cssStyle="width:200px"/></td> 
                        <td scope="row" class="label"><label>Last Name</label></td>
                        <td><s:textfield id="physicianLastName" name="physicianSearchCriteria.lastName" maxlength="200" size="100" cssStyle="width:200px"/></td>                                      
                    </tr>
                    <tr>  
                        <td scope="row" class="label"><label>City</label></td>
                        <td><s:textfield id="physicianCity" name="physicianSearchCriteria.city" maxlength="200" size="100" cssStyle="width:200px"/></td>
                        <td scope="row" class="label"><label>State</label></td>
                        <td><s:textfield id="physicianState" name="physicianSearchCriteria.state" maxlength="200" size="100" cssStyle="width:200px"/><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font></td>
                    </tr>
                    <tr>
                        <td scope="row" class="label"><label>Country</label></td>
                        <td><s:select id="physicianCountry" name="physicianSearchCriteria.country" headerKey="USA" headerValue="United States" 
                                      list="physicianSearchCriteria.countries" listKey="alpha3" listValue="name" cssStyle="width:206px"/>
                        </td>
                        <td scope="row" class="label"><label>Zip</label></td>
                        <td><s:textfield id="physicianZip" name="physicianSearchCriteria.zipCode" maxlength="200" size="100" cssStyle="width:200px"/></td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>            
                                <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Person</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            </li>
                        </ul>   
                    </del>
                </div>
            </div>
            <div id="createPersJsp" style="display:none">
				<jsp:include page="/WEB-INF/jsp/nodecorate/addPerson.jsp"/>
			</div>
            <div align="center">
                <s:if test="physicians != null">
                    <s:set name="physicians" value="physicians" scope="request"/>
                    <display:table class="data" name="physicians" pagesize="10" sort="list" uid="row" export="false" requestURI="lookupPhysician.action">
                        <display:column title="PO-ID" property="id" headerClass="sortable"/>
                        <display:column title="First Name" property="firstName" headerClass="sortable"/> 
                        <display:column title="Middle Name" property="middleName" headerClass="sortable"/>
                        <display:column title="Last Name" property="lastName" headerClass="sortable"/>
                        <display:column title="Address" property="address" headerClass="sortable"/> 
                        <display:column title="Action" class="action" sortable="false">
                            <a href="#" class="btn" onclick="populatePhysicianField('${row.id}', '${fn:replace(row.lastName,'\'','&apos;')}'+', '+'${fn:replace(row.firstName,'\'','&apos;')}')">
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