<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="cadsrResult != null">
<s:set name="cadsrResult" value="cadsrResult" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" 
    name="cadsrResult" export="false">
    <display:column title="Preferred Question Text" property="dataElementConcept.longName"  headerClass="sortable"/>
    <display:column title="Public Id" property="publicID"  headerClass="sortable"/>
    <display:column title="Version" property="version"  headerClass="sortable"/>   
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
           <a href="#" class="btn" onclick="callParentSubmit('${row.id}','${row.dataElementConcept.longName}','${row.publicID}','${row.version}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
    </display:column>
</display:table>
</s:if>