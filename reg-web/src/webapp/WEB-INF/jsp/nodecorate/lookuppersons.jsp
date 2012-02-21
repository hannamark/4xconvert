<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script type="text/javascript" language="javascript">
            function submitform(persid, name) {
            	var winObj = window.top;
            	if (winObj.setpersid==undefined) {
            		winObj = window.parent;
            	}            	
            	winObj.setpersid(persid, name);
            	winObj.hidePopWin(true); 
            }
             
            function callCreatePerson(persid, rolecode) {        
                top.window.loadPersDiv(persid, rolecode, 'add');
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {
                var url = '/registry/protected/popupdisplayPersonsList.action';
                var params = {
                    city: $("city").value,
                    country: $("country").value,
                    ctepId: $('poOrganizations').ctepId.value,
                    email: $("email").value,
                    firstName: $("firstName").value,
                    lastName: $("lastName").value,
                    state: $("state").value,
                    zip: $("zip").value
                };
                var div = $('getPersons');          
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
            }
            
            function setSearchFormVisible() {
                $("searchPersJsp").style.display="";
                $("createPersJsp").style.display="none";
            }
        
            function setCreateFormVisible() {
                $("searchPersJsp").style.display="none";
                $("createPersJsp").style.display="";
            }
            
            function createPerson() {
                var url = '/registry/protected/popupcreatePerson.action';
                var country = $("poOrganizations_country").value;
                var params = {
                    city: $("poOrganizations_city").value,
                    country: country,
                    email: $("poOrganizations_email").value,
                    fax: $("poOrganizations_fax").value,
                    firstName: $("poOrganizations_firstName").value,
                    lastName: $("poOrganizations_lastName").value,
                    midName: $("poOrganizations_middleName").value,
                    phone: $("poOrganizations_phone").value,
                    preFix: $("poOrganizations_preFix").value,
                    state: ((country == "USA") ? $("poOrganizations_orgStateSelect").value : $("poOrganizations_orgStateText").value),
                    streetAddr: $("poOrganizations_streetAddress").value,
                    suffix: $("poOrganizations_suffix").value,
                    tty: $("poOrganizations_tty").value,
                    url: $("poOrganizations_url").value,
                    zip: $("poOrganizations_zip").value
                };
                var div = $('getPersons');          
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            function setFocusToFirstControl() {
                for (var f=0; f < document.forms.length; f++) {
                    for(var i=0; i < document.forms[f].length; i++) {
                        var elt = document.forms[f][i];
                        if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
                            try {
                                elt.focus();
                                return;
                            } catch(er) {
                            }
                        }
                    }
                }
            }
        </script> 
    </head> 
    <body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
        <div class="box">
            <s:form id="poOrganizations" name="poOrganizations" >
                <div id="searchPersJsp">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/searchPerson.jsp"/>
                </div>
                <div id="createPersJsp" style="display:none">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/addPerson.jsp"/>
                </div>
                <div class="line"></div>
                <div class="box" align="center">
                    <div id="getPersons">    
                         <jsp:include page="/WEB-INF/jsp/nodecorate/displayPersonsList.jsp"/>
                   </div>
                </div>
            </s:form>
        </div>
    </body>
</html>