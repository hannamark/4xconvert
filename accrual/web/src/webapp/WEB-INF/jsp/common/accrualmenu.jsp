<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP Viewer</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'run_reports'}">
              <li><a href="welcome.action" class="selected">Home</a></li>
           </c:when>
           <c:when test="${requestScope.topic == 'login'}">
              <li><a href="/accrual/logout.action">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="welcome.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">
                 <c:choose>
                    <c:when test="${requestScope.topic == 'run_sample'}">
                       <li><a href="sample.action" class="selected">Sample Action</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="sample.action" >Sample Action</a></li>
                    </c:otherwise>
                </c:choose>
              <li><a href="/accrual/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/accrual/public/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/accrual/public/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


