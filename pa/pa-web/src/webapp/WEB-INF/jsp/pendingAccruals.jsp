 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="pendingAccruals.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
</SCRIPT>
 <body>
 <c:set var="topic" scope="request" value="pendingAccruals"/>
 <h1><fmt:message key="pendingAccruals.title"/></h1>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form name= "pendingAccruals">
        <s:token/>
        <pa:studyUniqueToken/>
        <s:actionerror/>
    <h2><fmt:message key="pendingAccruals.title" /></h2>
    
    <s:set name="pendingAccruals" value="pendingAccruals" scope="request"/>
    <display:table name="pendingAccruals" id="row" class="data" sort="list"  pagesize="200" requestURI="pendingAccrualsexecute.action" export="false">
        <display:column escapeXml="true" titleKey="pendingAccruals.trialIdentifier" property="studyIdentifier" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="pendingAccruals.ctepIdentifier" property="ctepId" sortable="true" headerClass="sortable" />       
        <display:column escapeXml="true" titleKey="pendingAccruals.dcpIdentifier" property="dcpId" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="pendingAccruals.missingSiteIdentifier" property="studySite" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="pendingAccruals.missingSiteName" property="orgName" sortable="true" headerClass="sortable" />
        <display:column title="Delete" class="action">
            <label for="delete" style="display:none">delete</label>
            <s:checkbox id="delete" name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>                
        </display:column>        
    </display:table>
  
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">                                           
                        <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected sites. Cancel to abort.', 'pendingAccrualsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Remove Site From Queue</span></span></s:a></li>
                        <li><s:a href="#" cssClass="btn" onclick="document.pendingAccruals.reset();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a></li>                       
                </ul>
            </del>
        </div>
    </s:form>
   </div>
 </body>
 </html>
