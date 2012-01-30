<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--Primary Nav-->
<div id="nav">
  <ul id="mainmenu" class="menu" style="display: none;">
    <c:choose>
      <c:when test="${requestScope.topic == 'login'}">
        <li>
          <a href="${pageContext.request.contextPath}/home.action">&nbsp;Home&nbsp;&nbsp;&nbsp;</a>
        </li>
      </c:when>                      
      <c:otherwise>
        <li>
          <a href="welcome.action">&nbsp;Home</a>
        </li>
      </c:otherwise>
    </c:choose>
    <c:choose>
      <c:when test="${pageContext.request.remoteUser != null}">
        <c:if test="${(sessionScope.viewerRole == 'Abstractor')}">
          <li><a>&nbsp;Reports&nbsp;&nbsp;&nbsp;</a>
            <ul id="reportmenu">
              <li style="width: 210px;"><a href="#" onclick="submitXsrfForm('${pageContext.request.contextPath}/ctro/criteriaSummaryOfSubmission.action');">
                Summary of Submission</a>
              </li>
              <li style="width: 210px;"><a href="#" onclick="submitXsrfForm('${pageContext.request.contextPath}/ctro/criteriaSubmissionByInstitution.action');">
                Trials Submitted by Institution</a>
              </li>
              <li style="width: 210px;"><a href="#" onclick="submitXsrfForm('${pageContext.request.contextPath}/ctro/criteriaSummary4Report.action');">
                Summary 4 Type</a>
              </li>
              <li style="width: 210px;"><a href="#" onclick="submitXsrfForm('${pageContext.request.contextPath}/ctro/criteriaAdHocReport.action');">Ad Hoc</a>
              </li>
            </ul>
          </li>
        </c:if>
      </c:when>
    </c:choose>

    <li><a>&nbsp;Quick links&nbsp;&nbsp;&nbsp;</a>
      <ul id="quicklinks">
      <li style="width: 300px;"><a href="http://www.cancer.gov/ncictrp" class="external" target="new1">Clinical
                        Trials Reporting Program (CTRP)</a>
      </li>
      <li style="width: 300px;"><a href="http://www.cancer.gov/" class="external" target="new2">National
                        Cancer Institute (NCI)</a>
      </li>
      <li style="width: 300px;"><a href="http://ncicb.nci.nih.gov/" class="external" target="new3">NCI
                        Center for Bioinformatics (NCICB)</a>
      </li>
      <li style="width: 300px;"><a href="https://cabig.nci.nih.gov/" class="external" target="new4">caBIG&trade;
                        - Cancer Biomedical Informatics Grid&trade;</a>
      </li>
    </ul>
  </ul>
    
  <div class="clear"></div>

</div>
