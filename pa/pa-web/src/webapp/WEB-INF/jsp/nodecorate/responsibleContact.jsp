<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:textfield label="Responsible Party Name" name="gtdDTO.responsiblePersonName" size="30"  readonly="true" cssStyle="width:200px"/>

<input type="button" value="Look Up" onclick="lookup4loadresponsibleparty();"/>
<s:hidden name="gtdDTO.responsiblePersonIdentifier"/>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>ResponsiblePartyNotSelected</s:param>
    </s:fielderror>                            
</span>