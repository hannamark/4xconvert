<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:textfield label="First Name" name="gtdDTO.sponsorName" size="30"  readonly="true" cssStyle="width:200px"/>

<input type="button" value="Look Up" onclick="lookup4sponsor();"/>
<s:hidden name="gtdDTO.sponsorIdentifier"/>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>SponsorNotSelected</s:param>
    </s:fielderror>                            
</span>