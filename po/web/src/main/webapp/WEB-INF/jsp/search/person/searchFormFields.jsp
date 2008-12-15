<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
        <div>
            <div style="float:left;">
                <div class="boxouter" style="float:left;margin-right: 10px;">
                    <h2>Basic Identifying Information</h2>
                    <div class="box_white">
                       <s:textfield label="%{getText('person.firstName')}" name="criteria.firstName" size="50"/>
                       <s:textfield label="%{getText('person.lastName')}" name="criteria.lastName" size="50"/>
                       <s:textfield label="%{getText('emailEntry.value')}" name="criteria.email" size="60"/>
                       <s:textfield label="Organization Affiliation" name="criteria.org" size="80" maxlength="250"/>
                       <s:textfield label="Investigator CTEP Identifier" name="criteria.ctepId" size="15"/>
                        <div class="clear"></div>
                    </div>
                </div>
                
            </div>
              
            <div class="clearfloat"></div>
        </div>