<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
    function submitform(intid)
    {
        
        top.window.loadDiv(intid);
        window.top.hidePopWin(true); 
    }
    
    function callParentSubmit(intid)
    {   
        top.window.loadDiv(intid);
        window.top.hidePopWin(true); 
    }
    
    
    function loadDiv() {        
        var jsName = document.getElementById("searchName").value;
        var jsType = document.getElementById("interventionType").value;
        var url = '/pa/protected/popupIntdisplayList.action?searchName='+jsName+'&searchType='+jsType;
        var div = document.getElementById('getOrgs');
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
        return false;
    }
</SCRIPT>

</head> 
<body>
<div class="box">
<s:form id="poOrganizations" name="poOrganizations" >
<h2>Search Interventions</h2>
<s:label name="interventionErrorMessage"/>
<table  class="form">  
    <s:hidden id="interventionType" name="interventionType"/>
    <tr>    
        <td scope="row" class="label">
            <label for="searchName">Intervention Name: </label>
        </td>
        <td>
            <s:textfield id="searchName" name="searchName"  maxlength="60" size="60"  cssStyle="width:200px" />
        </td>
    </tr>
</table>
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                   </li>
               </ul>
          </del>
    </div>
    <div id="getOrgs" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/displayInterventionList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>