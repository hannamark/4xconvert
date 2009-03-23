<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:textfield label="Responsible Party Name" name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>

<c:choose>
	<c:when test="${sessionScope.Sponsorselected != null}">
		<input type="button" id="lookupbtn4RP" value="Look Up" onclick="lookup4loadresponsibleparty();" title="Opens a popup form to select Responsible Party Contact"/>
	</c:when>
	<c:otherwise>
		<input type="button" id="lookupbtn4RP" value="Look Up" onclick="lookup4loadresponsibleparty();" title="Opens a popup form to select Responsible Party Contact" disabled="disabled"/>
	</c:otherwise>
</c:choose>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>ResponsiblePartyNotSelected</s:param>
    </s:fielderror>                            
</span>