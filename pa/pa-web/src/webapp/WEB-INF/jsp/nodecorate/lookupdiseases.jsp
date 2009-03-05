<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
    function submitform(disid)
    {
        top.window.loadDiv(disid);
        window.top.hidePopWin(true); 
    }
    
    function callParentSubmit(disid)
    {   
        top.window.loadDiv(disid);
        window.top.hidePopWin(true); 
    }
    function loadDiv() {        
        var jsName = document.getElementById("searchName").value;
        var url = '/pa/protected/popupDisdisplayList.action?searchName='+jsName;
        var div = document.getElementById('getDiseases');
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
    }
</SCRIPT>

</head> 
<body>
<div class="box">
<s:form id="diseases" name="diseases" >
<h2>Search Diseases</h2>
<table  class="form">  
    <tr>    
        <td scope="row" class="label">
            <label for="searchName">Disease Name: </label>
        </td>
        <td>
            <s:textfield id="searchName" name="searchName"  maxlength="60" size="60"  cssStyle="width:200px" />
        </td>
    <tr><td/><td class="info">Use * as wildcard when entering search string</td></tr>
    </tr>
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
    <div id="getDiseases" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupdiseasesdisplayList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>