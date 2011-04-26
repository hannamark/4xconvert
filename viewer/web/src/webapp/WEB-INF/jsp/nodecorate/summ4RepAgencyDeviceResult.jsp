<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>


<table width="100%">
<tr>               
<td colspan="2">
    <jsp:include page="/WEB-INF/jsp/nodecorate/summ4RepAgencyDeviceNationalResult.jsp"/>
</td>
</tr>
<tr>               
<td colspan="2">
    <jsp:include page="/WEB-INF/jsp/nodecorate/summ4RepAgencyDeviceEPPResult.jsp"/>  
</td>
</tr>
<tr>               
<td colspan="2">
    <jsp:include page="/WEB-INF/jsp/nodecorate/summ4RepAgencyDeviceInstitutionalResult.jsp"/>         
</td>
</tr>
<tr>               
<td colspan="2">
    <jsp:include page="/WEB-INF/jsp/nodecorate/summ4RepAgencyDeviceIndustrialResult.jsp"/> 
</td>
</tr>
</table>
       
