<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="form">
    <s:hidden name="currentAction"/>
    <s:hidden name="disease.diseaseIdentifier"/>
    <s:hidden name="disease.studyDiseaseIdentifier"/>
    <tr>
        <td class="label"><s:label>Name:</s:label><span
            class="required">*</span></td>
        <td class="value" style="width: 250px">
            <s:textfield disabled="true" name="disease.preferredName" maxlength="160" size="160" 
                    cssStyle="width:280px;float:left"/> 
        </td>
        <td class="value">
            <s:if test="%{currentAction == 'create'}"> 
            <ul style="margin-top: -6px;">
                <li style="padding-left: 0"><a href="#" class="btn"
                    onclick="lookup();" /><span class="btn_img"><span
                    class="search">Look Up</span></span></a></li>
            </ul>
            </s:if>
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Code:</s:label></td>
        <td class="value">
            <s:textfield disabled="true" name="disease.code" cssStyle="width:100px;float:left"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>NCI Thesaurus Concept ID:</s:label></td>
        <td class="value">
            <s:textfield disabled="true" name="disease.conceptId" cssStyle="width:140px;float:left"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Menu Display Name:</s:label></td>
        <td class="value">
            <s:textfield disabled="true" name="disease.menuDisplayName" cssStyle="width:280px;float:left"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Parent Name:</s:label></td>
        <td class="value">
            <s:textfield disabled="true" name="disease.parentPreferredName" cssStyle="width:280px;float:left"/> 
        </td>
    </tr>
    <tr><td/>
        <td class="value">
            <s:checkbox name="disease.lead"/>
            <s:label cssClass="label">Lead?</s:label>
        </td>
    </tr>
</table>
