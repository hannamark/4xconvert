<%@ tag display-name="aliases" description="Renders the alias form" body-content="empty" %>
<%@ attribute name="aliasKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="Display read-only view, if not provied readonly is false"%>
<%@ attribute name="defaultAliases" type="java.lang.Boolean" required="false" description="Use player's aliases to pre-populate with."%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ctrp.nci.nih.gov/taglib/utility-functions" prefix="func" %>
<%@ taglib uri="/struts-tags" prefix="s" %>


<s:if test="%{#attr.readonly == true}">
<s:set name="readonlyBool" value="true"/>
</s:if>
<s:else>
<s:set name="readonlyBool" value="false" />
</s:else>
<script type="text/javascript">
<!--
function clearErrorMessages() {
	$$('span.errorMessage').invoke('remove');
	
}

function isAliasFieldBlank() {
    //creating a few similar objects
    var aliasMsg = {field: '<s:text name="alias.value"/>'};    

    //the template  
    var templ = new Template('#{field} contains a value that has not been added. Do you want to submit the form and lose this data?');

    //let's format each object
    [aliasMsg].each( function(conv){
        templ.evaluate(conv);
    });
 
    var continueResult = true; 
    if( ($F('alias_value').length != 0)) {    	
        continueResult = continueResult && confirm(templ.evaluate(aliasMsg));
    }    
    return continueResult;
}

//-->
</script>
   
<fieldset>    
    
   <s:fielderror>
        <s:param value="%{#attr.aliasKeyBase + '.alias'}"/>
   </s:fielderror>
    <c:url value="alias/edit.action" var="viewAliasAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
        <c:param name="usePlayerAliasesAsDefault" value="${defaultAliases}"/>
    </c:url>
    <div id="alias-list">Loading...</div>
    <script>
        loadDiv('${viewAliasAction}', 'alias-list');
    </script>
</fieldset>