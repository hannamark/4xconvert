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
        var includeSyn = false;  
        if (document.getElementById("includeSynonym") != null && document.getElementById("includeSynonym").checked==true)
        {
            includeSyn = true;
        } 
        var exactMat = false;
        if (document.getElementById("exactMatch") != null && document.getElementById("exactMatch").checked == true)
        {
           exactMat = true; 
        }
        var url = '/pa/protected/popupIntdisplayList.action?searchName='+jsName+'&includeSynonym='+includeSyn+'&exactMatch='+exactMat;
        var div = document.getElementById('getInterventions');
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
        return false;
    }
    function cancel() {
        window.top.hidePopWin(true);
    } 
</SCRIPT>

</head> 
<body>
<div class="box">
<pa:sucessMessage />
<s:if test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if>
<s:form id="interventions" name="interventions" >
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
        </td> </tr>
    <tr> 
                <td scope="row" class="label">
                   <label  for="includeSynonym"> <fmt:message key="interventions.includeSynonym"/></label>                        
                </td>
                <td>
                    <s:checkbox name="includeSynonym" id="includeSynonym" />
                </td>                
            </tr>
             <tr> 
                <td scope="row" class="label">
                     <label  for="exactMatch"> <fmt:message key="interventions.exactMatch"/></label>                        
                </td>
                <td>
                    <s:checkbox name="exactMatch" id="exactMatch"/>
                </td>                
            </tr>
   
</table>
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li>
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img">
                    <span class="search">Search</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="cancel();">
                    <span class="btn_img"><span class="cancel">Cancel</span></span>
                </s:a>  
               </li>
                
            </ul>
          </del>
    </div>
    <div class="line"></div>
    <div id="getInterventions" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupinterventionsdisplayList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>