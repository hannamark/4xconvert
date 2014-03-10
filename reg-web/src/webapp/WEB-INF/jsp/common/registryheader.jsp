<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@page import="gov.nih.nci.pa.util.CsmHelper"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="masthead">
    <div class="container">
      <div class="row">
        <div class="col-xs-9">
          <div class="navbar-brand"><a data-placement="top" rel="tooltip" href="#" data-original-title="Clinical Trials Reporting Program"><img src="${pageContext.request.contextPath}/images/logo.png"/></a></div>
        </div>
       <!--User Details-->
      <c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div class="col-xs-3">
          <div class="dropdown pull-right">
          	<a href="#" data-toggle="dropdown" class="dropdown-toggle nav-user"><c:out value="${CsmHelper.firstName}"/> <c:out value="${CsmHelper.lastName}"/></a>
            <ul class="dropdown-menu">
              <li><a class="account" data-toggle="modal" data-target="#myAccount" href="javascript:void(0)" onclick="submitXsrfForm('${showMyAccountUrl}');">My Account</a></li>
              <li><a href="#" class="help">Help</a></li>
              <li class="divider"></li>
              <li class="sign-out">
                <button type="button" class="btn btn-default btn-sm" onClick="javascript:void(0)"/>'>Sign Out</button>
              </li>
            </ul>
          </div>
        </div>
        </c:when>
        <c:otherwise>
	      Log  In 
        </c:otherwise>
	  </c:choose>
      </div>
    </div>
  </header>