<%@ taglib prefix="s" uri="/struts-tags"%> 
<div class="padme5">
  <s:textfield id ="disease" readonly="true" name="patient.diseasePreferredName" maxlength="400" size="50" 
               cssStyle="width:280px;float:left" cssClass="readonly"/> 
  <s:hidden name="patient.sdcDiseaseIdentifier"/>
  <s:hidden name="patient.icd9DiseaseIdentifier"/>
  <ul>
      <li style="padding-left: 0"><a href="#" class="btn"
          onclick="lookup();" /><span class="btn_img"><span
          class="search">Look Up</span></span></a>
      </li>
  </ul>
</div>