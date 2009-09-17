<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="inboxTrial.title"/></title>   
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>

</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
     document.forms[0].action="inboxProcessingquery.action";
     document.forms[0].submit();     
}

function resetValues(){
    document.getElementById("nciIdentifier").value="";
 }
</SCRIPT>
<body>
<!-- main content begins-->
    <c:set var="topic" scope="request" value="search_trial"/>
    <h1><fmt:message key="inboxTrial.search.title"/></h1>
    <div class="filter_checkbox"><input type="checkbox" name="checkbox" checked="true" id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Display Search Fields</label></div>
    <div class="box" id="filters">
    <s:form>
    <pa:failureMessage/>
        <table class="form">    
            <tr>
                <td scope="row" class="label">
                     <label for="nciIdentifier"> <fmt:message key="studyProtocol.nciIdentifier"/></label>
                </td>
                <td>
                    <s:textfield id="nciIdentifier" name="criteria.nciIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
             </tr>                                               
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Submit</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>  
                        </li>
                </ul>   
            </del>

        </div>        
   </s:form>

 </div>
 <div class="line"></div>
 
 <c:if test="${records != null}">                       
        <h2>Search Results</h2>  
        <jsp:include page="/WEB-INF/jsp/inboxProcessingQueryResults.jsp">
            <jsp:param name="listName" value="records" />        
        </jsp:include>
   </c:if>
   
</body>
</html>
