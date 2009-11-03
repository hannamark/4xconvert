<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="plannedRadiationDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
</div>
<h3>Radiation Information</h3>
<table class="form">    
                    
    <tr>
        <td scope="row" class="label"><s:label>Dose Range:</s:label></td>
        <td class="value" >
            <s:textfield  name="minDoseValue" maxlength="160" /> 
             <s:textfield name="maxDoseValue" maxlength="160"/>         
        </td>
        <td scope="row" class="label"><s:label>Dose UOM:</s:label></td>
        <td class="value" >
        <div id="loadDoseUOMDetails">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseUOM.jsp"%>
        </div>   
       </td>
    </tr>
    <tr>
            
     <td scope="row" class="label"><s:label>Dose Form:</s:label></td>
         <td>
         <div id="loadDoseFormDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseForm.jsp"%>
         </div>     
        </td>
    </tr> 
    <tr>
            <td scope="row" class="label"><s:label>Dose Frequency:</s:label></td>
            <td>
           <div id="loadDoseFreqDetails"> 
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseFrequency.jsp"%>
          </div>    
    </td>
    </tr> 
   
    <tr>
        <td scope="row" class="label"><s:label>Dose Duration:</s:label></td>
        <td class="value">
            <s:textfield name="doseDurationValue" maxlength="160"  
                   /> 
        </td>
       <td scope="row" class="label"><s:label>Dose Duration UOM:</s:label></td>
        <td class="value" >
        <div id="loadDoseDurationUOMDetails"> 
          <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseDurationUOM.jsp"%>
        </div> 
       </td>
    </tr>
      <tr>
        <td scope="row" class="label"><s:label>Dose Regimen:</s:label></td>
        <td class="value" >
            <s:textfield name="doseRegimen" maxlength="160"  
                    /> 
        </td>
       
    </tr>
   <tr>
        <td scope="row" class="label"><s:label>Total Dose Range:</s:label></td>
        <td class="value" >
            <s:textfield name="minDoseTotalValue" maxlength="160" 
                    /> 
             <s:textfield name="maxDoseTotalValue" maxlength="160" 
                    />         
        </td>
       <td scope="row" class="label"><s:label>Total Dose UOM:</s:label></td>
        <td class="value" >
        <div id="loadTotalDoseUOMDetails"> 
           <%@ include file="/WEB-INF/jsp/nodecorate/displayTotalDoseUOM.jsp"%>
         </div>  
       </td>
    </tr>
     <tr>    
         <td scope="row" class="label"><s:label>Target Site:</s:label></td>
         <td>
         <div id="loadTargetSiteDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayTargetSite.jsp"%>
          </div>    
    </td>
    </tr> 
    <tr>
            <td scope="row" class="label"><s:label>Approach Site:</s:label></td>
            <td>
            <div id="loadApproachSiteDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayApproachSite.jsp"%>
             </div> 
    </td>
    </tr> 
 <tr>
       <td>
       </td>
       <td>
        </td>    
         <td>
            <s:a href="#" cssClass="btn" onclick="generate();">
              <span class="btn_img"><span class="submit">Generate</span></span>
         </s:a>
    </td>
    </tr> 
            </table>   
    
