<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
        <div class="boxouter">
        <h2>Address Information</h2>
            <div class="box_white" id="cr.postalAddresses.div">
	            <c:if test="${fn:length(cr.postalAddresses) == 0}">
	            No Postal Address(es) found.
	            </c:if>
	            <c:forEach items="${cr.postalAddresses}" var="addy" varStatus="e">
	            <fieldset>
	                <legend>Address ${e.index + 1}</legend>
	                <div>
	                <po:address address="${addy}"/>
	                </div>
	            </fieldset>
	            </c:forEach>        
	        </div>       
        </div>       
