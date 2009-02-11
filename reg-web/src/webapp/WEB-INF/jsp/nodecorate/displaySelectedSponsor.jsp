<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Sponsor Name" name="selectedSponsor.name.part[0].value" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> <input type="button" value="Look Up" onclick="lookup4sponsor();"/>