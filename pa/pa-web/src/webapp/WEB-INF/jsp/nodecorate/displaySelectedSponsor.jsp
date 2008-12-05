<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Sponsor Name" name="selectedSponsor.name.part[0].value" size="30" cssStyle="width:200px" readonly="true"/> <input type="button" value="Look Up" onclick="lookup4sponsor();"/>