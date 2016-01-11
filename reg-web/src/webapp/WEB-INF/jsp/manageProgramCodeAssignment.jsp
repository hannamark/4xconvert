<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="pcassignment.page.title" /></title>
        <s:head/>
        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        <style type="text/css">
            .txt-val {
                padding: 6px;
            }
            tr.cf {
                background: white !important;
                color: black !important;
            }
            a.mypgp, a.pgcrm {
                font-weight: normal !important;
            }
            div.ui-dialog-buttonset {
                float: none !important;
                text-align: center;
            }

        </style>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.buttons.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jszip.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/buttons.html5.min.js'/>"></script>

        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/manage.programcodes.js'/>"></script>
        <script type="text/javascript" language="javascript">
            document.observe("dom:loaded", function() {
                <s:iterator var="p" value="familyDto.programCodes">
                    <s:if test="active">
                        allProgramCodes.push({
                            id: <s:property value="id" /> ,
                            code: '<s:property value="programCode" />',
                            name: '<s:property value="programName" />',
                            familyId: <s:property value="familyDto.id" />,
                            familyPoId: <s:property value="familyDto.poId" />
                        });
                    </s:if>
                </s:iterator>
                refreshFilteredProgramCodes(jQuery);
            });

        </script>


    </head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-12 ">
			<h1 class="heading"><span><fmt:message key="pcassignment.heading"/></span></h1>
		</div>
		
		<div class="col-md-12">
            <s:form id="changeFamilyFrm" action="managePCAssignmentchangeFamily.action" cssClass="form-horizontal">
                <div class="form-group">
                    <label for="familyPoId" class="col-sm-2 control-label"><fmt:message key="programcodes.organization.family.label"/></label>
                    <div class="col-sm-2">
                        <s:select id="familyPoId" name="familyPoId" list="affiliatedFamilies" cssClass="form-control" headerKey="" headerValue="--Select--" listKey="id" listValue="name" />
                    </div>
                </div>
                <div class="col-md-12">
                    <hr />
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><fmt:message key="programcodes.reporting.end.date.label"/></label>
                    <div class="col-sm-2 txt-val"><s:date name="familyDto.reportingPeriodEndDate" format="MM/dd/yyyy"/></div>
                    <label class="col-sm-2 control-label"><fmt:message key="programcodes.reporting.period.length.label"/></label>
                    <div class="col-sm-2 txt-val"><s:property value="familyDto.reportingPeriodLength"/> </div>
                </div>
                <div class="col-md-12">
                    <hr />
                    <div id="trials" class="table-wrapper">
                        <div class="table-responsive">
                            <div id="row_wrapper" class="dataTables_wrapper no-footer">
                                <table id="trialsTbl" class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Trial ID(s)</th>
                                        <th>Title</th>
                                        <th>Lead Organization</th>
                                        <th>Principal Investigator</th>
                                        <th>Trial Status</th>
                                        <th>
                                            Program Code(s)
                                            <a name="fcpg-icon-loc"> </a>
                                            <s:if test="familyDto">
                                                <a id="fpgc-icon-a" href="#fcpg-icon-loc"><span id="fpgc-icon" class="glyphicon glyphicon-filter"></span></a>
                                                <div id="fpgc-div" style="display:none;">
                                                    <select id="fpgc-sel" multiple="multiple">
                                                        <s:iterator var="p" value="familyDto.programCodes">
                                                            <s:if test="active">
                                                                <option id="fpgc-opt-<s:property value="id" />" value="<s:property value="programCode" />" selected="selected" ><s:property value="programName" /></option>
                                                            </s:if>

                                                        </s:iterator>
                                                    </select>

                                                </div>
                                            </s:if>
                                        </th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>

                        </div>
                    </div>

                   <s:hidden name="pgcFilter" id="pgcFilter"  value="%{#parameters.pgcFilter}" />

                </div>
            </s:form>
		</div>
	</div>
</div>

<div id="dialog-participation" title="Participating sites"
     style="display: none;">
    <p>
		<span class="pgcpSite"></span> Participating Sites:
    </p>
    <div>
        <div class="table-header-wrap">
            <table class="table table-bordered" id="participationTbl" width="100%">
                <thead>
                <tr>
                    <th width="50%" nowrap="nowrap">Site Name</th>
                    <th width="50%" nowrap="nowrap">Investigators</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
