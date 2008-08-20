<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<page:applyDecorator name="main">
        <form action="j_security_check">
            <c:if test="${not empty failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <div class="fieldrow">
                <label for="j_username">Username:</label>
                <div class="fieldbox_m required"><input name="j_username" maxlength="100" size="15" type="text"></div>
            </div>
            <div class="fieldrow">
                <label for="j_password">Password:</label>
                <div class="fieldbox_m required"><input name="j_password" maxlength="100" size="15" type="password"></div>
            </div>
            <div class="clearfloat"></div>
            <div class="btnwrapper">
                <ul class="btnrow">
                    <li><input type="submit" value="Login"/></li>
                </ul>
            </div>
        </form>
</div>
</page:applyDecorator>