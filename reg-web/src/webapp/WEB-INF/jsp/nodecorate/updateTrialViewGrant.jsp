<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="fundingDtos != null && fundingDtos.size() > 0">
    <table class="form">
        <tbody> 
            <tr>
                <th>Funding Mechanism</th>
                <th>Institute Code</th>
                <th>Serial Number</th>
                <th>NCI Division/Program Code</th>
             </tr>
        <s:iterator id="fundingDtos" value="fundingDtos" status="fundstats">
            <tr>
                <td>                     
                    <s:hidden name="fundingDtos[%{#fundstats.index}].fundingMechanismCode" value="%{fundingMechanismCode}"/>
                    <s:property value="%{fundingMechanismCode}"/>                        
                </td>
                <td>            
                    <s:hidden name="fundingDtos[%{#fundstats.index}].nihInstitutionCode" value="%{nihInstitutionCode}"/>
                    <s:property value="%{nihInstitutionCode}"/>                                 
                </td>
                <td>
                    <s:hidden name="fundingDtos[%{#fundstats.index}].serialNumber" value="%{serialNumber}"/>
                    <s:property value="%{serialNumber}"/>
                </td>
                <td>          
                    <s:hidden name="fundingDtos[%{#fundstats.index}].nciDivisionProgramCode" value="%{nciDivisionProgramCode}"/>
                    <s:property value="%{nciDivisionProgramCode}"/>                                   
                    <s:hidden  name="fundingDtos[%{#fundstats.index}].id" value="%{id}"/>      
                </td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>