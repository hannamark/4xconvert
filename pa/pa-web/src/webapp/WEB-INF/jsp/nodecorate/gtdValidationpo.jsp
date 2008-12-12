<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>  
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    Lead Organization
                 <span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/leadOrg.jsp" %>
            </div>      
        </td>
    </tr>

    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    Principal Investigator
                 </dfn><span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadPersField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayLeadPrincipalInvestigator.jsp" %>
            </div>      
        </td>
    </tr>
    
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
            <input type="radio" name="gtdDTO.responsiblePartyType" value="pi"  onclick="manageRespPartyLookUp();"> PI 
            <input type="radio" name="gtdDTO.responsiblePartyType" value="sponsor" onclick="manageRespPartyLookUp();"> Sponsor
        </td>
        </tr>               
        <tr id="rpcid" >
            <td scope="row" class="label">
                <label for="responsiblepartycontact"> Responsible Party Contact :</label> 
            </td>               
            <td class="value">
                <div id="loadResponsibleContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
                </div>                                    
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
               Email Address:<span class="required">*</span>
            </td>
            <td class="value">
                <s:textfield name="gtdDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>contactEmail</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
            <td class="value">
                <s:textfield name="gtdDTO.contactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>contactPhone</s:param>
                   </s:fielderror>                            
                 </span>
            </td>           
        </tr>             
