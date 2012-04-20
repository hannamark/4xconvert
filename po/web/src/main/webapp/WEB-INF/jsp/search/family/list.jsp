<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="family.search.title"/></title>
    <c:set var="topic" scope="request" value="familylist"/>
</head>
<body> 
<po:successMessages />
<s:actionerror/>

<div id="org_family"> 
                            
    <div class="boxouter">     
    <h2><fmt:message key="family.search.results"/></h2>
    <div class="createbtn"> 
        <ul class="btnrow">         
            <li><a href="<c:url value="/protected/family/create/start.action"/>" class="btn" onclick="this.blur();" id="createNewFamily"><span class="btn_img"><span class="add">Create New</span></span></a></li> 
        </ul> 
    </div> 
    <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>