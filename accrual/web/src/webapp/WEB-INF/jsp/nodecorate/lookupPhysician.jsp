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
        document.getElementById("physicianCountry").value = 'United States';
        document.getElementById("physicianZip").value = '';
    }
    
    function populatePhysicianField(id, name) {
        window.top.document.getElementsByName("userAccount.physicianId")[0].value = id;
        window.top.document.getElementsByName("userAccount.physician")[0].value = name;       
        window.top.hidePopWin(false); 
    }
</SCRIPT>

</head>

<body onload="window.top.centerPopWin();" class="submodal">
    <div class="box">
        <s:form>
            <s:label name="orgErrorMessage"/>
            <div>
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
                                <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            </li>
                        </ul>   
                    </del>
                </div>
            </div>
            <div align="center">
                <s:if test="physicians != null">
                    <s:set name="physicians" value="physicians" scope="request"/>
                    <display:table class="data" name="physicians" pagesize="10" sort="list" uid="row" export="false">
                        <display:column title="PO-ID" property="id" headerClass="sortable"/>
                        <display:column title="First Name" property="firstName" headerClass="sortable"/> 
                        <display:column title="Middle Name" property="middleName" headerClass="sortable"/>
                        <display:column title="Last Name" property="lastName" headerClass="sortable"/>
                        <display:column title="Address" property="address" headerClass="sortable"/> 
                        <display:column title="Action" class="action" sortable="false">
                            <a href="#" class="btn" onclick="populatePhysicianField('${row.id}', '${fn:replace(row.lastName,'\'','')}'+', '+'${fn:replace(row.firstName,'\'','')}')">
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