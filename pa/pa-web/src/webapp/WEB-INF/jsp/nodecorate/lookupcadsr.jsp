<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script language="javascript" type="text/javascript">
        
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
                $("searchcaDSR").style.display="";
                $("getCadsr").style.display="";
                $("cdeRequestEmail").style.display="none";
                var url = '/pa/protected/popupCadsrgetClassifiedCDEs.action';
                var params = {
                    csiName: $("csiName").value,
                    searchName: $("searchName").value
                };        
                var div = $('getCadsr');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function openEmail() {
                $("searchcaDSR").style.display="none";
                $("getCadsr").style.display="none";
                $("cdeRequestEmail").style.display="";
                var url = '/pa/protected/popupCadsrrequestToCreateCDE.action';
                var div = $('cdeRequestEmail');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, {});
            }
        </script>
    </head> 
    <body>
        <s:form id="cadsrlookup" name="cadsrlookup">
            <div class="box" id="searchcaDSR">
                <h2><fmt:message key="cadsr.header"/></h2>
                <table  class="form">  
                    <tr>    
                        <td scope="row" class="label">
                            <label for="searchName"><fmt:message key="cadsr.dataElementName"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchName" name="searchName"  maxlength="60" size="60" cssStyle="width:200px" />
                        </td>
                    </tr>
                     <tr> 
                         <td scope="row" class="label">
                             <label for="csiName"><fmt:message key="cadsr.classificationCode"/>:</label>
                         </td>
                         <td>
                             <s:select id="csiName" name="csiName" list="csisResult" listKey="longName" listValue="longName"/>
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
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search"><fmt:message key="cadsr.button.search"/></span></span></s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="openEmail()"><span class="btn_img"><span class="search"><fmt:message key="cadsr.button.create"/></span></span></s:a>
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