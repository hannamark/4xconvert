<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
        <div>
             <div style="float:left;">
                <div class="boxouter" style="float:left;margin-right: 10px;">
                    <h2>Basic Identifying Information</h2>
                    <div class="box_white">
                    
                    <po:inputRow>
                        <po:inputRowElement>
                            <s:textfield label="%{getText('organization.ctepId')}" name="criteria.ctepID" size="10"/>                         
                        </po:inputRowElement>
                    </po:inputRow>
                    
                    <po:inputRow>
                    <po:inputRowElement>
                        <s:textfield label="%{getText('organization.id')}" name="criteria.id" size="10"/>
                    </po:inputRowElement>
                    <po:inputRowElement>
                        <s:set name="statusValues" value="@gov.nih.nci.po.data.bo.EntityStatus@getAllowedSearchable()" />
                           <s:select
                        label="%{getText('organization.statusCode')}"
                        name="criteria.statusCode"
                        list="#statusValues"
                        listKey="name()"
                        listValue="name()"
                        value="criteria.statusCode" 
                        headerKey="" headerValue="--Select a Status--" 
                        /> 
                    </po:inputRowElement>
                    </po:inputRow>
                        <s:textfield label="%{getText('organization.name')}" name="criteria.name" size="70"/>
                        <s:checkbox label="%{getText('organization.searchAliases')}" name="criteria.searchAliases" labelposition="right"/>
                        <s:textfield label="%{getText('family.name')}" name="criteria.familyName" size="70"/>
                        <s:checkbox label="%{getText('organization.hasChangeRequests')}" name="criteria.hasChangeRequests" labelposition="right" />
                        <s:checkbox label="%{getText('organization.hasPendingHcfRoles')}" name="criteria.hasPendingHcfRoles" labelposition="right" />
                        <s:checkbox label="%{getText('organization.hasPendingRoRoles')}" name="criteria.hasPendingRoRoles" labelposition="right" />
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