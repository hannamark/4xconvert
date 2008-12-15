<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:textfield label="Responsible Party Name" name="gtdDTO.responsiblePersonName" size="30"  readonly="true" cssStyle="width:200px"/>
<input type="button" value="Look Up" onclick="lookup4loadresponsibleparty();"/>

<span class="formErrorMsg"> 
<s:fielderror>
<s:param>gtdDTO.responsiblePersonName</s:param>
</s:fielderror>                            
</span>
<s:hidden name="gtdDTO.responsiblePersonIdentifier"/>
