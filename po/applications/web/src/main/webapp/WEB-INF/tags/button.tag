<%@ tag display-name="button"  description="Displays the button"  body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="href" required="true" type="java.lang.String"%>
<%@ attribute name="onclick" required="false" type="java.lang.String"%>
<%@ attribute name="style" required="true" type="java.lang.String"%>
<%@ attribute name="text" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="false" type="java.lang.String" description="DOM element ID for the anchor, usefull for tests and scripts"%>
<c:set var="idHtml" value="id='${id}'"/>
<li><a href="${href}" onclick="${onclick} this.blur();" class="btn" ${empty id ? "" : idHtml}><span class="btn_img"><span class="${style}">${text}</span></span></a></li>