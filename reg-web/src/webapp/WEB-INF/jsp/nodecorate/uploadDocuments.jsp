<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript">
    function deleteDocument(typeCode) {
        var url = '/registry/protected/ajaxUploaddeleteDocument.action';
        var params = {
                pageFrom: document.forms[0].pageFrom.value,
                typeCode: typeCode
                };
        var div = document.getElementById('uploadDocDiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
        return false;
    }
</script>
<reg-web:failureMessage/>
<s:hidden name="pageFrom" id="pageFrom"/>
<s:if test="%{pageFrom == 'submitTrial'}">
  <%@ include file="/WEB-INF/jsp/nodecorate/submitTrialDocuments.jsp" %>
</s:if>

<s:if test="%{pageFrom == 'updateTrial'}">
  <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialDocuments.jsp" %>
</s:if>

<s:if test="%{pageFrom == 'proprietaryTrial' || pageFrom == 'updateProprietaryTrial'}">
  <%@ include file="/WEB-INF/jsp/nodecorate/submitProprietaryTrialDocuments.jsp" %>
</s:if>

<s:if test="%{pageFrom == 'amendTrial'}">
  <%@ include file="/WEB-INF/jsp/nodecorate/amendTrialDocuments.jsp" %>
</s:if>