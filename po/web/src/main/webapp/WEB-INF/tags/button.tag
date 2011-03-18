<%@ tag display-name="button"  description="Displays the button"  body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="href" required="true" type="java.lang.String"%>
<%@ attribute name="onclick" required="false" type="java.lang.String"%>
<%@ attribute name="style" required="true" type="java.lang.String"%>
<%@ attribute name="text" required="false" type="java.lang.String"%>
<%@ attribute name="textKey" required="false" type="java.lang.String"%>
<%@ attribute name="id" required="false" type="java.lang.String" description="DOM element ID for the anchor, usefull for tests and scripts"%>
<%@ attribute name="target" required="false" type="java.lang.String"%>
<c:choose>
  <c:when test="${not empty textKey}"><fmt:message key="${textKey}" var="finalText"/></c:when>
  <c:otherwise><c:set var="finalText"><c:out value="${text}" /></c:set></c:otherwise>
</c:choose>
<c:set var="idHtml" value="id='${id}'"/>
<c:set var="targetHtml" value="target='${target}'"/>
<li><a href="${href}" onclick="${onclick} this.blur();" class="btn" ${empty id ? "" : idHtml} ${empty target ? "" : targetHtml}><span class="btn_img"><span class="${style}">${finalText}</span></span></a></li>
