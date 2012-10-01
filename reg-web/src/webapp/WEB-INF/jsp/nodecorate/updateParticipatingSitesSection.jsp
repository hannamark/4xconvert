<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="participatingSitesList.size > 0" >
    <tr>
        <th colspan="2">Participating Sites</th>
    </tr>
    <tr>
        <td colspan="2" class="space">
            <table class="data2">
                <tr> 
                    <td class="space">
                        <table class="form">
                            <tbody>
                                <tr>
                                    <th>Site</th>
                                    <th>Recruitment Status</th>
                                    <th>Date</th>
                                    <th>Program Code</th>
                                </tr>
                                <s:iterator id="participatingSitesList" value="participatingSitesList" status="psstats">
                                    <tr>
                                        <td>
                                            <s:hidden name="participatingSitesList[%{#psstats.index}].name" value="%{name}"/>
                                            <s:property value="%{name}"/>
                                        </td>
                                        <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                        <td>
                                            <label><s:select headerKey="" headerValue="--Select--"
                                                name="participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                                list="#recruitmentStatusValues" cssStyle="text-align:left;"/></label>
                                            <span class="formErrorMsg">
                                                <s:fielderror>
                                                    <s:param>participatingsite.recStatus<s:property value="%{#psstats.index}"/></s:param>
                                                </s:fielderror>
                                            </span>
                                        </td>
                                        <td>
                                            <label><s:textfield  name="participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}"/></label>
                                            <span class="formErrorMsg">
                                                <s:fielderror>
                                                    <s:param>participatingsite.recStatusDate<s:property value="%{#psstats.index}"/></s:param>
                                                </s:fielderror>
                                            </span>
                                        </td>
                                        <td>
                                            <label><s:textfield  name="participatingSitesList[%{#psstats.index}].programCode" value="%{programCode}"/></label>
                                            <s:hidden  name="participatingSitesList[%{#psstats.index}].id" value="%{id}"/>
                                        </td>
                                    </tr>
                                </s:iterator >
                            </tbody>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>        
    <tr>
        <td colspan="2" class="space">&nbsp;</td>
    </tr>
</s:if>