<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:textfield label="Responsible Party Name" name="(responsiblePartyContact.getName().getPart()).get(1).getValue()" size="30"  readonly="true" cssStyle="width:200px"/>
<input type="button" value="Look Up" onclick="lookup4loadresponsibleparty();"/>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>ResponsiblePartyNotSelected</s:param>
    </s:fielderror>                            
</span>