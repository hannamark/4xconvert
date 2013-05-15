<%@ taglib prefix="s" uri="/struts-tags"%> 
<div class="padme5">
  <s:textfield id ="sitedisease" readonly="true" name="patient.siteDiseasePreferredName" maxlength="400" size="50" 
               cssStyle="width:280px;float:left" cssClass="readonly"/> 
  <s:hidden name="patient.siteDiseaseIdentifier"/>
  <ul>
      <li style="padding-left: 0"><a href="#" class="btn"
          onclick="lookup('true');" /><span class="btn_img"><span
          class="search">Look Up</span></span></a>
      </li>
  </ul>
</div>