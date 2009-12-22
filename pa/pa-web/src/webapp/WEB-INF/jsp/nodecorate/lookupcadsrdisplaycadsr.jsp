<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="cadsrResult != null">
<s:set name="cadsrResult" value="cadsrResult" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row" 
    name="cadsrResult" export="false"  requestURI="/pa/protected/popupCadsrgetClassifiedCDEsDisplayTag.action" >
    <display:column title="Preferred Question Text" property="preferredQuestion"  headerClass="sortable"/>
    <display:column title="Public Id" property="viewPublicId"  headerClass="sortable"/>
    <display:column title="Version" property="version"  headerClass="sortable"/>   
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
           <a href="#" class="btn" onclick="top.window.loadDiv('${row.id}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
    </display:column>
</display:table>
</s:if>