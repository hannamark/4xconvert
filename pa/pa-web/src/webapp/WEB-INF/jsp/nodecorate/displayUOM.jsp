<%@ taglib prefix="s" uri="/struts-tags"%> 
<div>

            <s:textfield readonly="true" name="webDTO.unit" maxlength="80" size="80" 
                    cssStyle="width:120px;float:left" cssClass="readonly"/> 
       
             <ul style="margin-top: -7px;">
                <li style="padding-left: 0"><a href="#" class="btn"
                    onclick="lookupUOM();" /><span class="btn_img"><span
                    class="search">Look Up</span></span></a></li>
            </ul>
    </div>
