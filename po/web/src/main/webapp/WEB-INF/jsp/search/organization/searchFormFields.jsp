<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
        <div>
             <div style="float:left;">
                <div class="boxouter" style="float:left;margin-right: 10px;">
                    <h2>Basic Identifying Information</h2>
                    <div class="box_white">
                    <po:inputRow>
                    <po:inputRowElement>
                        <s:textfield label="%{getText('organization.id')}" name="criteria.organization.id" size="10"/>
                    </po:inputRowElement>
                    <po:inputRowElement>
                        <s:set name="statusValues" value="@gov.nih.nci.po.data.bo.EntityStatus@getAllowedSearchable()" />
                           <s:select
                        label="%{getText('organization.statusCode')}"
                        name="criteria.organization.statusCode"
                        list="#statusValues"
                        listKey="name()"
                        listValue="name()"
                        value="criteria.organization.statusCode" 
                        headerKey="" headerValue="--Select a Status--" 
                        /> 
                    </po:inputRowElement>
                    </po:inputRow>
                        <s:textfield label="%{getText('organization.name')}" name="criteria.organization.name" size="70"/>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="boxouter" style="float:left;margin-right: 10px;">
                    <h2>Address Information</h2>
                    <div class="box_white">
                        <po:addressForm formNameBase="searchOrganizationForm" addressKeyBase="criteria.organization.postalAddress" address="${criteria.organization.postalAddress}" required="false"/>
                        <div class="clear"></div>
                    </div>
                </div>
             </div>
             <div class="clearfloat"></div>
        </div>