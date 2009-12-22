<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT language="JavaScript">

    
    function callParentSubmit(disid,prefName,pId,version)
    {   
        window.top.document.getElementsByName("webDTO.criterionName")[0].value = prefName;
        window.top.document.getElementsByName("webDTO.cdePublicIdentifier")[0].value = pId;
        window.top.document.getElementsByName("webDTO.cdeVersionNumber")[0].value = version;
        var csiName = document.getElementById("csiName").value;
        window.top.document.getElementsByName("webDTO.cdeCategoryCode")[0].value = csiName;
        window.top.hidePopWin(true); 
    }
    function loadDiv() {     
        document.getElementById("searchcaDSR").style.display="";
        document.getElementById("getCadsr").style.display="";
        document.getElementById("cdeRequestEmail").style.display="none";
        
        var jsName = document.getElementById("searchName").value;
        var jcsiName = document.getElementById("csiName").value;
        var url = '/pa/protected/popupCadsrgetClassifiedCDEs.action?searchName='+jsName+'&csiName=' + jcsiName;
        var div = document.getElementById('getCadsr');
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
    }
    
    function openEmail() {
        document.getElementById("searchcaDSR").style.display="none";
        document.getElementById("getCadsr").style.display="none";
        document.getElementById("cdeRequestEmail").style.display="";
        var url = '/pa/protected/popupCadsrrequestToCreateCDE.action';
            var div = document.getElementById('cdeRequestEmail');
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
<s:form id="cadsrlookup" name="cadsrlookup">
<div class="box" id="searchcaDSR">
<h2>Search caDSR</h2>
<table  class="form">  
    <tr>    
        <td scope="row" class="label">
            <label for="searchName"><fmt:message key="cadsr.dataElementName"/>:</label>
        </td>
        <td>
            <s:textfield id="searchName" name="searchName"  maxlength="60" size="60"  cssStyle="width:200px" />
        </td>
       </tr>
       <tr> 
                <td scope="row" class="label">
                   <label  for="Classification Code"> <fmt:message key="cadsr.classificationCode"/>:</label>                        
                </td>
               <td>
                <s:select id ="csiName" name="csiName" list="csisResult" listKey="longName" listValue="longName"/>
              </td>
        </tr>

</table>
</div>
 <div id="cdeRequestEmail" style="display:none">
     <jsp:include page="/WEB-INF/jsp/nodecorate/requestToCreateCDE.jsp"/>
 </div>
  <div class="actionsrow">
    <del class="btnwrapper">
      <ul class="btnrow">
        <li>
           <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="openEmail()"><span class="btn_img"><span class="search">Create CDE Request</span></span>
           </s:a>
        </li>
      </ul>
   </del>
 </div>
   <div class="line"></div>
    <div id="getCadsr" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupcadsrdisplaycadsr.jsp"/>
    </div>   
</s:form>
</body>
</html>