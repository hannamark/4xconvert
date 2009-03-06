<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:textfield label="First Name" name="selectedSponsor.name.part[0].value" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
<input type="button" value="Look Up" onclick="lookup4sponsor();" title="Opens a popup form to select Sponsor"/>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>SponsorNotSelected</s:param>
    </s:fielderror>                            
</span>