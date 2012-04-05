<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
<c:if test="${sessionScope.adminUsers != null && fn:length(sessionScope.adminUsers) > 0 && registryUserWebDTO.affiliatedOrganizationId != null}">
       <tr>
        <td colspan="2" class="space">
            <li><a href="javascript:void(0)" onclick="viewAdmin();"/>View Admins</a></li>
        </td>
       </tr>          
 </c:if>
 <c:if test="${requestScope.orgSelected != null && fn:length(sessionScope.adminUsers) == 0}">
     <tr>
        <td scope="row" class="label"> Request for Admin Access</td>
        <td><s:checkbox name="registryUserWebDTO.requestAdminAccess" fieldValue="true"/></td>
    </tr>
 </c:if>
 </table>
 
