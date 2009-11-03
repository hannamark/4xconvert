<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
    function submitLookUp(id,divName,className)
    {
        top.window.loadDetails(id,divName,className);
        window.top.hidePopWin(true); 
    }
    
    function callParentSubmit(id,divName,className)
    {   
        top.window.loadDetails(id,divName,className);
        window.top.hidePopWin(true); 
    }
    
    
    function loadLookUpDiv() {
        var code = document.getElementById("codeSearch").value;
        var publicId = document.getElementById("publicIdSearch").value;
        var description = document.getElementById("descriptionSearch").value;
        var displayName = document.getElementById("displayNameSearch").value;
        var className = document.getElementById("className").value;
        var divName = document.getElementById("divName").value;
        var url = '/pa/protected/popupTypeInterventiondisplayLookUpList.action?code='+code+'&publicId='+publicId+'&description='+description+'&displayName='+displayName+'&className='+className+'&divName='+divName;
        
        var div = document.getElementById('getLists');         
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
        return false;
    }
    
    function formMethodCodeReset(){
    document.forms[0].reset();
    document.getElementById("codeSearch").value = '';
    document.getElementById("publicIdSearch").value = '';
    document.getElementById("descriptionSearch").value = '';
    document.getElementById("displayNameSearch").value = '';
   }
    
</SCRIPT>

</head> 
<body>
<div class="box">
<s:form id="lookUp" name="lookUp" >
<h2>Search </h2>
<s:label name="lookUpErrorMessage"/>

<table  class="form">
 <s:hidden id="className" name="lookupSearchCriteria.type"/>  
 <s:hidden id="divName" name="lookupSearchCriteria.divName"/>
    <tr>    
        <td scope="row" class="label">
            <label for="code"> <fmt:message key="lookUp.code"/></label>
        </td>
        <td>
            <s:textfield id="codeSearch" name="lookupSearchCriteria.code"  maxlength="200" size="100"  cssStyle="width:200px" />
        </td>
       <td scope="row" class="label">
            <label for="publicId"> <fmt:message key="lookUp.publicId"/></label>
        </td>
        <td>
            <s:textfield id="publicIdSearch" name="lookupSearchCriteria.publicId"  maxlength="200" size="100"  cssStyle="width:200px" />
        </td>
       <tr> 
        <td scope="row" class="label">
            <label for="displayName"> <fmt:message key="lookUp.displayName"/></label>
        </td>
        <td>            
            <s:textfield id="displayNameSearch" name="lookupSearchCriteria.displayName"  maxlength="200" size="100"  cssStyle="width:200px" />
        </td> 
         <td scope="row" class="label">
            <label for="description"> <fmt:message key="lookUp.description"/></label>
        </td>
        <td>            
            <s:textfield id="descriptionSearch" name="lookupSearchCriteria.description"  maxlength="200" size="100"  cssStyle="width:200px" />
        </td>
</tr>
    </table>
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadLookUpDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>
                    <s:a href="#" cssClass="btn" onclick="lookUpFormReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>  
                   </li>
               </ul>   
          </del>
    </div>
    <div id="getLists" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/displayLookUpList.jsp"/>                 
    </div>
      
</s:form>
</div>


</body>
</html>