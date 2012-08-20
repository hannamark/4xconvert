<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
    <tr>
         <th colspan="2">Sponsor/Responsible Party</th>
    </tr>          
    <tr>
        <td scope="row" class="label">
            <label for="sponsor"> Sponsor:<span class="required">*</span></label> 
        </td>
        <td class="value">
            <div id="loadSponsorField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displaySponsor.jsp" %>
            </div>      
        </td>
    </tr>   
    <tr>
        <td scope="row" class="label">Responsible Party:<span class="required">*</span></td>
        <td>
            <s:radio id="gtdDTO.responsiblePartyType"  name="gtdDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
        </td>
        </tr>
         <s:if test="gtdDTO.responsiblePartyType == 'sponsor'">
            

             <tr id="rpcid" >
                <td scope="row" class="label">
                    <label for="responsiblepartycontact"> Responsible Party Personal Contact :</label> 
                </td>               
                <td class="value">
                    <div id="loadResponsibleContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
                    </div>                                    
                </td>
            </tr>
            <tr id="rpgcid">
                <td scope="row" class="label">
                    <label for="resPartyGenericContact">Responsible Party Generic Contact:</label> 
                </td>
                <td class="value">
                    <div id="loadResponsiblePartyGenericContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                    </div>      
                </td>
            </tr>   
        </s:if>
        <s:else>
            <tr id="rpcid" style="display:none">
                <td scope="row" class="label">
                    <label for="responsiblepartycontact"> Responsible Party Contact :</label> 
                </td>               
                <td class="value">
                    <div id="loadResponsibleContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
                    </div>                                    
                </td>
            </tr>
            <tr id="rpgcid" style="display:none">
                <td scope="row" class="label">
                    <label for="resPartyGenericContact">Responsible Party Generic Contact:</label> 
                </td>
                <td class="value">
                    <div id="loadResponsiblePartyGenericContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                    </div>      
                </td>
            </tr>
        </s:else>
        <tr>
            <td scope="row" class="label">
               Email Address:<span class="required">*</span>
            </td>
            <td class="value">
                <s:textfield name="gtdDTO.contactEmail"  id="gtdDTO.contactEmail" maxlength="199" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.contactEmail</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
            <td class="value">
                <s:textfield name="gtdDTO.contactPhone" id="gtdDTO.contactPhone" maxlength="199" size="100"  cssStyle="width:100px" />
                Extn:<s:textfield name="gtdDTO.contactPhoneExtn" id="gtdDTO.contactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.contactPhone</s:param>
                   </s:fielderror>                            
                 </span>
            </td>           
        </tr>             
</table>