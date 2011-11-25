<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table width="100%">
    <tr>               
        <td colspan="2">
            <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/agencyDeviceNationalResult.jsp"/>
        </td>
    </tr>
    <tr>               
        <td colspan="2">
            <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/agencyDeviceEPRResult.jsp"/>  
        </td>
    </tr>
    <tr>               
        <td colspan="2">
            <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/agencyDeviceInstitutionalResult.jsp"/>         
        </td>
    </tr>
    <tr>               
        <td colspan="2">
            <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/agencyDeviceIndustrialResult.jsp"/> 
        </td>
    </tr>
</table>
