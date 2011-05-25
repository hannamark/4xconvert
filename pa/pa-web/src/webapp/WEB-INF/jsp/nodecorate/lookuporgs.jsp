<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script language="javascript" type="text/javascript">
            function submitform(orgid) {
                top.window.setorgid(orgid);
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }
            
            function callParentSubmit(orgid) {    
                top.window.setorgid(orgid);
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {
                var url = '/pa/protected/popupdisplayOrgList.action';
                var params = {
                    orgName: $('orgNameSearch').value,
                    familyName: $('orgFamilyNameSearch').value,
                    countryName: $('orgCountrySearch').value,
                    cityName: $('orgCitySearch').value,
                    zipCode: $('orgZipSearch').value,
                    stateName: $('orgStateSearch').value
                };
                var div = $('getOrgs');          
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function formReset() {
                document.forms[0].reset();
                $("orgNameSearch").value = '';
                $("orgFamilyNameSearch").value = '';
                $("orgCitySearch").value = '';
                $("orgStateSearch").value = '';
                $("orgCountrySearch").value = 'USA';
                $("orgZipSearch").value = '';
            }
            
        </script>
    
    </head> 
    <body>
        <div class="box">
        <s:form id="poOrganizations" name="poOrganizations" >
            <h2><fmt:message key="popUpOrg.header"/></h2>
            <s:label name="orgErrorMessage"/>
            <table  class="form">  
                   <tr>     
                     <td scope="row" class="label">
                        <label for="name"><fmt:message key="popUpOrg.name"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgNameSearch" name="orgSearchCriteria.name" maxlength="200" size="100" cssStyle="width:200px" />
                     </td>
            
                      <td scope="row" class="label">
                        <label for="city"><fmt:message key="popUpOrg.city"/></label>
                    </td>
                     <td>             
                         <s:textfield id="orgCitySearch" name="orgSearchCriteria.city" maxlength="200" size="100" cssStyle="width:200px" />
                    </td>
                     
                </tr>
                <tr>  
                     <td scope="row" class="label">
                        <label for="familyName"><fmt:message key="popUpOrg.familyName"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgFamilyNameSearch" name="orgSearchCriteria.familyName" maxlength="200" size="100" cssStyle="width:200px" />
                     </td>
                     <td scope="row" class="label">
                        <label for="country"><fmt:message key="popUpOrg.country"/></label>
                    </td>
                    <td>
                        <s:select id="orgCountrySearch" name="orgSearchCriteria.orgCountry" list="countryList"  
                                  listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
                    </td>        
                </tr>
                <tr>
                     <td scope="row" class="label">
                        <label for="state"><fmt:message key="popUpOrg.state"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgStateSearch" name="orgSearchCriteria.state" maxlength="75" size="20"/><br><font size="1"><span class="info"><fmt:message key="popUpOrg.stateInfo"/></span></font>
                    </td>
                      <td scope="row" class="label">
                        <label for="zip"><fmt:message key="popUpOrg.zip"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgZipSearch" name="orgSearchCriteria.zip" maxlength="75" size="20"/>
                    </td>    
                </tr>
            </table>
            <div class="actionsrow">
                 <del class="btnwrapper">
                     <ul class="btnrow">
                         <li>           
                             <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search"><fmt:message key="popUpOrg.button.search"/></span></span></s:a>
                             <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel"><fmt:message key="popUpOrg.button.reset"/></span></span></s:a>  
                         </li>
                     </ul>   
                 </del>
            </div>
            <div id="getOrgs" align="center">    
                   <jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>
               </div>
        </s:form>
        </div>
    </body>
</html>