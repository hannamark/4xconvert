<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
    function populateFields(id,name,type) {
		if(type == 'Surgery') {
			window.top.document.getElementsByName("surgery.name")[0].value = name;
			window.top.document.getElementsByName("surgery.interventionId")[0].value = id;
		}else if(type == 'drugName'){
			window.top.document.getElementsByName("drugBiologic.drugName")[0].value = name;			
			window.top.document.getElementsByName("drugBiologic.interventionId")[0].value = id;
		}
        window.top.hidePopWin(false); 
    }
    
    function loadDiv() {     
        
        var jsName = document.getElementById("searchName").value;
        var includeSyn = false;  
        if (document.getElementById("includeSynonym") != null && document.getElementById("includeSynonym").checked==true)
        {
            includeSyn = true;
        } 
        var exactMat = false;
        if (document.getElementById("exactMatch") != null && document.getElementById("exactMatch").checked==true)
        {  
           exactMat = true; 
        }
        var type = document.getElementById("type").value; 
        
        var url = '/outcomes/outcomes/popupInterventiondisplayList.action?searchName='+jsName+'&includeSynonym='+includeSyn+'&exactMatch='+exactMat+'&type='+type;
        var div = document.getElementById('getInterventions');
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
<s:form>
<s:hidden id="type" name="type" />
<h2>Search </h2>
<table  class="form">  
    <tr>    
        <td scope="row" class="label">
            <label for="searchName">
	            <s:if test="type == 'Surgery'">
					<fmt:message key="surgery.name"/>
				</s:if>
				<s:if test="type == 'drugName'">
					<fmt:message key="drugBiologic.name"/>
				</s:if>
			</label>
        </td>
        <td>
            <s:textfield id="searchName" name="searchName"  maxlength="60" size="60"  cssStyle="width:200px" />
        </td>
       </tr>
       <tr> 
                <td scope="row" class="label">
                   <label  for="includeSynonym"> <fmt:message key="disease.includeSynonym"/></label>                        
                </td>
                <td>
                    <s:checkbox name="includeSynonym" id="includeSynonym" />
                </td>                
            </tr>
             <tr> 
                <td scope="row" class="label">
                     <label  for="exactMatch"> <fmt:message key="disease.exactMatch"/></label>                        
                </td>
                <td>
                    <s:checkbox name="exactMatch" id="exactMatch" />
                </td>                
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
    <div id="getInterventions" align="center">   
        <jsp:include page="/WEB-INF/jsp/nodecorate/lookupinterventionsdisplayList.jsp"/>
    </div>
</s:form>
</div>


</body>
</html>