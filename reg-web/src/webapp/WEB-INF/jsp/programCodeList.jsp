<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="programcodes.page.title"/></title>
        <s:head/>        
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/programcode.js'/>"></script>        
    </head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-12 ">
			<h1 class="heading"><span><fmt:message key="programcodes.master.list"/></span></h1>
		</div>		
		<div class="col-md-12">
			<form class="form-horizontal">		
			  <div class="form-group">
			    <label for="exampleInputEmail1" class="col-sm-2 control-label"><fmt:message key="programcodes.organization.family.label"/></label>
			    <div class="col-sm-2">
			    <s:select name="orgFamilies"
			    	cssClass="form-control" 
			    	list="familyDTOs" 
       				listKey="identifier"
       				listValue="name"       
       				value="%{name}"
				/>
				</div>		
			  </div>			  
			  <div class="col-md-12">
			  	<hr />
			  </div>				
			  <div class="form-group">
			    <label for="programCodeDate" class="col-sm-2 control-label"><fmt:message key="programcodes.reporting.end.date.label"/></label>
			    <div class="col-sm-2">
			    	<div id="datetimepicker" class="datetimepicker input-append">
			    		<s:date name="selectedFamilyDTO.reportingPeriodEndDate" var="reportingPeriodDate" format="MM/dd/yyyy"/>						
				    	<s:textfield id="reportingPeriodEndDate" name="selectedFamilyDTO.reportingPeriodEndDate" data-format="MM/dd/yyyy" type="text" 
				    		cssClass="form-control" placeholder="mm/dd/yyyy" 
				    		value="%{reportingPeriodDate}" />
				    	<span class="add-on btn-default"><i class="fa-calendar"></i></span>
				    	<p class="text-success" id="date_flash" style="display:none;">Reporting period end date saved.</p>
				    </div>
			    </div>			    
			    <s:hidden id="poID" value="%{selectedFamilyDTO.poId}" />
			    <label for="programCodeLength" class="col-sm-2 control-label"><fmt:message key="programcodes.reporting.period.length.label"/></label>
			    <div class="col-sm-2">
				    <s:select
	                		id = "reportingPeriodLength"            
	                 		name="reportingPeriodLength"
	                 		list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10',
	                 			'11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
	                 			'20':'20','21':'21','22':'22','23':'23','24':'24'}"
	                 		value="selectedFamilyDTO.reportingPeriodLength"
	                 		cssClass="form-control"
	                 		onchange="changeReportingPeriodLength(this)"
	                 />			    	
					<p class="text-success" id="reporting_flash" style="display:none;">Reporting period length saved.</p>
			    </div>
			  </div>
			  <div class="col-md-12">
			  	<hr />
			  </div> 			    
			</form>		
		</div>
	</div>
</div>
</body>
