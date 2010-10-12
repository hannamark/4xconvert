<%@ taglib prefix="s" uri="/struts-tags"%> 
<div>
            <s:if test="labTestNameValues == null">
                <s:textfield readonly="true" name="webDTO.unit" maxlength="80" size="80" 
                        cssStyle="width:120px;float:left" cssClass="readonly"/> 
           
                 <ul style="margin-top: -7px;">
                    <li style="padding-left: 0">
                        <a href="#" class="btn" onclick="lookupUOM();">
                            <span class="btn_img">
                                <span class="search">Look Up</span>
                            </span>
                        </a>
                    </li>
                 </ul>
             </s:if>
             <s:if test="labTestNameValues != null && labTestUoMValues != null">
                <s:select headerKey="" headerValue="" name="webDTO.unit" value="webDTO.unit"
                list="labTestUoMValues"  cssStyle="width:150px" />
             </s:if>
    </div>
