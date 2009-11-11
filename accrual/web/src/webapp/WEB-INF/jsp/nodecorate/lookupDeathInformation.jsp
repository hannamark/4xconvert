<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<script language="JavaScript">
    function loadDiv() {
        document.forms[0].submit();
    }
    function submitform(ident, name) {
        window.top.document.getElementsByName("selectedSite")[0].value = ident;
        window.top.document.getElementsByName("deathInfo.autopsySite")[0].value = name;
        window.top.hidePopWin(false); 
    }
</script>
</head> 
<body>
<div class="box">
<s:form id="deathInfo" name="deathInfo" action="lookupDeathInformation.action">
<h2>Search Autopsy Site</h2>
<s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<table  class="form">
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.name"/></label></td>
<td><s:textfield size="50" name="searchAutopsySite"
               cssStyle="width:280px;float:left"/></td></tr>
</table>
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li>
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
               </li>
            </ul>
          </del>
    </div>
    <div class="line"></div>
    <div id="getAutopsySite" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupDeathInformationDisplayList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>
