<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.diseases.header" /></a></h2>
<div id="diseasesSection">
    <table class="form-table">
        <tbody>
            <viewer:valueRow labelFor="diseaseName" labelKey="studyProtocol.diseaseCondition">
                <div id="loadDetails">
                    <s:hidden id="criteria_diseaseCondition" name="criteria.diseaseConditionId"/>
                    <s:textfield id="diseaseName" name="diseaseName" readonly="true" maxlength="160" size="160" 
                                    cssStyle="width:280px;height:15px;" cssClass="readonly"/>
                    <ul class="btnrow"  style="margin-top: -20px;">
                        <li style="padding-left: 90px;">
                            <s:a href="#" cssClass="btn" onclick="lookup();">
                                <span class="btn_img">
                                    <span class="search">Look Up</span>
                                </span>
                            </s:a>
                        </li>
                    </ul>
                </div>
            </viewer:valueRow>
        </tbody>
    </table> 
</div>