    <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
    <script language="JavaScript">
   
   function trim(val) {
        var ret = val.replace(/^\s+/, '');
        ret = ret.replace(/\s+$/, '');
          return ret;
        }
   
   function checkIndicatorUpdate(indx) {
     var gp ="group4Update" +indx;
     var shw = "show"+indx;
       if (document.getElementById(gp).value == 'Yes'){
        showRowUpdate(document.getElementById(shw));
    } else {
        hideRowUpdate(document.getElementById(shw));
    }
   }

    function hideRowUpdate(row){          
        row.style.display = 'none'; 
    }
    function showRowUpdate(row){
        row.style.display = '';
    }


  function checkCodeUpdate(input,itera) {
    var inputElement = document.forms[0].elements[input];
    var nih1 = "programcodenihidUpdate"+itera;
       var nci1 = "programcodenciidUpdate"+itera;
    if (inputElement.options[inputElement.selectedIndex].value == "NCI-National Cancer Institute")
    {
        document.getElementById(nci1).style.display = '';
        document.getElementById(nih1).style.display = 'none';
    }else
    {
        document.getElementById(nci1).style.display = 'none';
        document.getElementById(nih1).style.display = '';
    }
 }
   
   
   
   function setProgramCodesUpdate(ref,iter){ 
    var nih = "programcodenihidUpdate"+iter;
     var nci = "programcodenciidUpdate"+iter;
     var other = "programcodeidUpdate"+iter;
   
        if (ref.value == 'NCI') {
            document.getElementById(nci).style.display = '';
            document.getElementById(nih).style.display = 'none';
            document.getElementById(other).style.display = 'none';
            
        } else if (ref.value == 'NIH') {
            document.getElementById(nih).style.display = '';
            document.getElementById(nci).style.display = 'none';
            document.getElementById(other).style.display = 'none';
        } else {
            document.getElementById(nih).style.display = 'none';
            document.getElementById(nci).style.display = 'none';
            document.getElementById(other).style.display = '';
        }
    }
    
    function removeAllOptionsUpdate(selectbox){
        var i;
        for(i=selectbox.options.length-1;i>=0;i--){
            selectbox.remove(i);
        }
}
    function addOptionUpdate(selectbox, value, text ){
        var optn = document.createElement("OPTION");
        optn.text = text;
        optn.value = value;
        selectbox.options.add(optn);
    }   
    function SelectSubCatUpdate(i){
        removeAllOptionsUpdate(document.getElementById('SubCatUpdate'));
        addOptionUpdate(document.getElementById('SubCatUpdate'), "", "-Select-", "");   
        if(i.value == 'IND'){
            addOption(document.getElementById('SubCatUpdate'),"CDER", "CDER");
            addOption(document.getElementById('SubCatUpdate'),"CBER", "CBER");
        }
        if(i.value == 'IDE'){
            addOption(document.getElementById('SubCatUpdate'),"CDRH", "CDRH");
        }
    }
   
    </script>
    <s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.NihInstituteCode@getDisplayNames()" />
    <s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
    <s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />
 
<c:if test="${indIdeUpdateDtos != null}">
    <c:if test="${fn:length(indIdeUpdateDtos) > 0}">
     <table class="form">    
        <tbody>
            <tr>
                <th>IND/IDE Types</th>
                <th>IND/IDE Number</th>
                <th>IND/IDE Grantor</th>
                <th>IND/IDE Holder Type</th>
                <th>NIH Institution, NCI Division/Program Code (if applicable)</th>
                <th>Expanded Access</th>
                <th>Expanded Access Type (if applicable)</th>
                <th></th>
             </tr>
             <s:iterator id="indIdeUpdateDtos" value="indIdeUpdateDtos" status="indidestats">
          <tr>
            
                <td style="white-space:nowrap;">                            
                <s:select id="group3Update" name="indIdeUpdateDtos[%{#indidestats.index}].indldeType" value="%{indIdeUpdateDtos[#indidestats.index].indldeType}" list="#{'IND':'IND', 'IDE':'IDE'}" onclick="SelectSubCatUpdate(this);"/>
         </td>
          <td>
               <s:textfield id="indIdesDTOs.indldeNumberUpdate" name="indIdeUpdateDtos[%{#indidestats.index}].indldeNumber" value="%{indIdeUpdateDtos[#indidestats.index].indldeNumber}" size="10" /> 
          </td>
          <td> 
             <s:if test="%{indIdeUpdateDtos[#indidestats.index].indldeType == 'IND'}">
                   <s:select id="SubCatUpdate" name="indIdeUpdateDtos[%{#indidestats.index}].grantor" value="%{indIdeUpdateDtos[#indidestats.index].grantor}" list="#{'CDER':'CDER', 'CBER':'CBER'}" cssStyle="width:75px" ></s:select>
            </s:if>
          <s:else>
                 <s:select id="SubCatUpdate" name="indIdeUpdateDtos[%{#indidestats.index}].grantor" value="%{indIdeUpdateDtos[#indidestats.index].grantor}" list="#{'CDRH':'CDRH'}" cssStyle="width:75px" ></s:select>
          </s:else>
           </td>   
            <td>
              <s:select id="holderTypeUpdate" name="indIdeUpdateDtos[%{#indidestats.index}].holderType"  value="%{indIdeUpdateDtos[#indidestats.index].holderType}" headerKey="" headerValue="-Select-" cssStyle="width:75px" onclick="setProgramCodesUpdate(this,%{#indidestats.index});"
                   list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}" />
             </td>
             <td>
              <s:div id="programcodenihidUpdate%{#indidestats.index}" cssStyle="display:''"><s:select id="programcodenihselectedvalueUpdate%{#indidestats.index}" headerKey="" headerValue="-Select-" cssStyle="width:300px" name="indIdeUpdateDtos[%{#indidestats.index}].nihInstHolder" value="%{indIdeUpdateDtos[#indidestats.index].nihInstHolder}" list="#phaseCodeValuesNIH" /></s:div>
              <s:div id="programcodenciidUpdate%{#indidestats.index}" cssStyle="display:none"><s:select id="programcodenciselectedvalueUpdate%{#indidestats.index}" headerKey="" headerValue="-Select-" cssStyle="width:300px" name="indIdeUpdateDtos[%{#indidestats.index}].nciDivProgHolder" value="%{indIdeUpdateDtos[#indidestats.index].nciDivProgHolder}" list="#phaseCodeValuesNCI" /></s:div>
              <s:div id="programcodeidUpdate%{#indidestats.index}" cssStyle="display:none"><s:select id="programcodenoneselectedUpdate%{#indidestats.index}" list="#{'-Select-':'-Select-'}"  cssStyle="width:300px"/></s:div>
              </td>
              <td>
                     <s:select id="group4Update%{#indidestats.index}" name="indIdeUpdateDtos[%{#indidestats.index}].expandedAccessIndicator"  value="%{indIdeUpdateDtos[#indidestats.index].expandedAccessIndicator}"  list="#{'No':'No', 'Yes':'Yes'}" onclick="checkIndicatorUpdate(%{#indidestats.index});"  />
                  </td>
                <td>
                 <s:div id="show%{#indidestats.index}" cssStyle="display:''">
                       <s:select id="expanded_status_update" headerKey="" headerValue="-Select-" name="indIdeUpdateDtos[%{#indidestats.index}].expandedAccessStatus" value="%{indIdeUpdateDtos[#indidestats.index].expandedAccessStatus}" list="#expandedAccessStatusCodeValues" />
                  </s:div>     
                  </td> 
               </tr> 
               
                
                
             </s:iterator>
                      
         </tbody>
</table>
</c:if>
</c:if>
