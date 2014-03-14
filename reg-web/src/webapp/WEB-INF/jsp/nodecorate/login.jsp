<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/loginValidation.js'/>"></script>
	
<!-- Begin page content -->
    <div class="row">
      <div class="col-xs-6 intro"> <img src="/registry/images/nci-logo.png" class="pull-left"/>
        <h4>Welcome to NCI&apos;s Clinical Trials Reporting Program</h4>
        <p>This site enables you to register a trial with NCI&apos;s Clinical Trials Reporting Program. You can:</p>
        <ul>
          <li>Register clinical trials</li>
          <li>Register multiple trials at one time using a <a href="#">batch upload template</a></li>
          <li>Search registered trails by Title, Phase, Trial Identifiers and Organizations</li>
		</ul>
		<p>Want to learn more about the Reporting Program? Visit the <a href="#">NCI Clinical Trials Reporting Program</a> website. You can also email NCICB Application Support at <a href="mailto:ncicb@pop.nci.nih.gov">ncicb@pop.nci.nih.gov</a> if you have questions or need assistance.</p>
      </div>
      
      <div class="col-xs-6">
        <ul class="nav nav-tabs">
          <li class="active"><a href="#sign-in" data-toggle="tab"><i class="fa-sign-in"></i>Sign In</a></li>
          <li><a href="#sign-up" data-toggle="tab"><i class="fa-pencil-square-o"></i>Sign Up</a></li>
          <li><a href="#forgot-password" data-toggle="tab"><i class="fa-key"></i>Forgot Password</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade in active" id="sign-in">
            <form class="form-horizontal" role="form" action="j_security_check" method="post" id="loginForm" name="loginForm" onsubmit="return validate();">
            	<c:if test="${not empty param.failedLogin}">
              		<p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            	</c:if>
              <div class="form-group">
                <label for="j_username" class="col-xs-3 control-label">Username</label>
                <div class="col-xs-7">
                  <input type="text" maxlength="100" size="25" class="form-control" id="j_username" name="j_username"  placeholder="Enter your username">
                </div>
              </div>
              <div class="form-group">
                <label for="j_password" class="col-xs-3 control-label">Password</label>
                <div class="col-xs-7">
                  <input type="password" maxlength="100" size="25" class="form-control" id="j_password" name="j_password" autocomplete="off" placeholder="Enter your password">
                </div>
              </div>
              <c:if test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
		         <c:choose>
		             <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1}">
		                  <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
		                    <input type="hidden" name="authenticationServiceURL"
		                         value="<c:out value="${item.value}"/>" />
		                  </c:forEach>
		             </c:when>
		             <c:otherwise>
		             	<div class="form-group">
			                <label for="authenticationServiceURL" class="col-xs-3 control-label">Account Source:</label>
			                <div class="col-xs-7">
			                  <select name="authenticationServiceURL" id="authenticationServiceURL" size="1" class="form-control" placeHolder="Select your account source">
		                        <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
		                        <c:choose>
		                            <c:when test="${fn:contains(item.value,'Dorian')}">
		                                <option value="<c:out value="${item.value}" />" selected="selected">
		                            </c:when>
		                            <c:otherwise>
		                                <option value="<c:out value="${item.value}" />">
		                            </c:otherwise>
		                        </c:choose>
		                                    <c:out value="${item.key}" />
		                                </option>
		                        </c:forEach>
		                    </select>
			                </div>
			              </div>
		             </c:otherwise>
		          </c:choose>
		      </c:if>
              <div class="bottom">
                <button type="button" class="btn btn-icon-alt btn-primary" onClick="document.loginForm.submit();">Sign In<i class="fa-arrow-circle-right"></i></button>
              </div>
            </form>
          </div>
          <div class="tab-pane fade" id="sign-up">
            <form class="form-horizontal" role="form" name="myAccount" method="POST" action="registerUsercreateAccount">
                <s:actionerror />
                <s:token/>
                <s:hidden name="userWebDTO.username" />
                 <%@ include file="/WEB-INF/jsp/nodecorate/accountCommonForm.jsp" %>
                 <div class="bottom">
	                <button type="button" class="btn btn-icon-alt btn-primary" onClick="document.myAccount.submit();">Sign Up<i class="fa-arrow-circle-right"></i></button>
	                <button type="button" class="btn btn-icon btn-default" onClick="reset();"><i class="fa-repeat"></i>Reset</button>
	              </div>
            </form>
          </div>
          <div class="tab-pane fade" id="forgot-password">
            <div class="tab-inside">
              <h4 class="heading"><span>Resetting Your Password</span></h4>
              <p>If you forgot your password, please visit the NCI Password Station at <a href="mailto:http://password.nci.nih.gov">http://password.nci.nih.gov</a> and follow the instructions there.</p>
              <p>If you need additional assistance or have questions, you can email NCI CBIIT Application Support at <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a>,
                or call <strong>240-276-5541</strong> or toll free <strong>888-478-4423</strong>.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


