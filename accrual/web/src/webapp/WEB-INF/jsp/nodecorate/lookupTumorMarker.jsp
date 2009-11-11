<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<script language="JavaScript">
	function loadDiv(lookupType) {	
	    document.getElementById("lookupType").value = lookupType;
	    document.getElementById("tumorMarker").submit();
	}
	function populateTumorMarkerField(id, name) {
		window.top.document.getElementsByName("tumorMarker.id")[0].value = id;
        window.top.document.getElementsByName("tumorMarker.tumorMarker")[0].value = name;		
        window.top.hidePopWin(false); 
	}
	function populateTmvUomField(id, name) {
        window.top.document.getElementsByName("tumorMarker.id")[0].value = id;
        window.top.document.getElementsByName("tumorMarker.tmvUom")[0].value = name;   
        window.top.hidePopWin(false); 
    }
</script>
</head> 
<body>
<div class="box">
<s:form id="tumorMarker" name="tumorMarker" action="lookupTumorMarker.action">
<s:hidden id="lookupType" name="lookupType"/>
<s:if test="lookupType == 'tumorMarker'">
    <h2>Search Tumor Marker</h2>
</s:if>
<s:elseif test="lookupType == 'tmvUom'">
    <h2>Search Tumor Marker Value UOM</h2>
</s:elseif>
<s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<table class="form">
    <tr>        
        <s:if test="lookupType == 'tumorMarker'">
            <td scope="row" class="label"><label><fmt:message key="tumor.marker.label"/></label></td>
            <td><s:textfield size="50" name="searchTumorMarker" cssStyle="width:280px;float:left"/></td>
        </s:if>
        <s:elseif test="lookupType == 'tmvUom'">
            <td scope="row" class="label"><label><fmt:message key="tumor.marker.value.uom.label"/></label></td>
            <td><s:textfield size="50" name="searchTmvUom" cssStyle="width:280px;float:left"/></td>
        </s:elseif>
    </tr>
</table>
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li>
                   <s:if test="lookupType == 'tumorMarker'">
                       <s:a href="#" cssClass="btn" onclick="loadDiv('tumorMarker')"><span class="btn_img"><span class="search">Search</span></span></s:a>
                   </s:if>
                   <s:elseif test="lookupType == 'tmvUom'">
                       <s:a href="#" cssClass="btn" onclick="loadDiv('tmvUom')"><span class="btn_img"><span class="search">Search</span></span></s:a>
                   </s:elseif>                                        
               </li>
            </ul>
          </del>
    </div>
    <div class="line"></div>
    <div id="getTumorMarker" align="center">        
        <s:if test="lookupType == 'tumorMarker'">
            <jsp:include page="/WEB-INF/jsp/nodecorate/lookupTumorMarkerDisplayList.jsp"/>
        </s:if>
        <s:elseif test="lookupType == 'tmvUom'">
            <jsp:include page="/WEB-INF/jsp/nodecorate/lookupTmvUomDisplayList.jsp"/>
        </s:elseif>
    </div>
</s:form>
</div>


</body>
</html>