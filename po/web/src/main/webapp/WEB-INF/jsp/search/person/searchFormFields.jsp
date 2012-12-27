<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div>
	<div style="float: left;">
		<div class="boxouter" style="float: left; margin-right: 10px;">
			<h2>Basic Identifying Information</h2>
			<div class="box_white">
				<s:textfield label="%{getText('person.firstName')}"
					name="criteria.firstName" size="50" />
				<s:textfield label="%{getText('person.lastName')}"
					name="criteria.lastName" size="50" />
				<s:textfield label="%{getText('emailEntry.value')}"
					name="criteria.email" size="60" />
				<s:textfield label="Organization Affiliation" name="criteria.org"
					size="80" maxlength="250" />

				<po:inputRow>
					<po:inputRowElement>
						<s:textfield label="CTEP Person Identifier" name="criteria.ctepID"
							size="15" />
					</po:inputRowElement>
					<po:inputRowElement>
						<s:textfield label="PO ID" name="criteria.id" size="10" />
					</po:inputRowElement>
				</po:inputRow>
				<po:inputRow>
					<po:inputRowElement>
						<s:set name="statusValues"
							value="@gov.nih.nci.po.data.bo.EntityStatus@getAllowedSearchable()" />
						<s:select label="Status" name="criteria.statusCode"
							list="#statusValues" listKey="name()" listValue="name()"
							value="criteria.statusCode" headerKey=""
							headerValue="--Select a Status--" />
					</po:inputRowElement>
				</po:inputRow>

				<div class="clear"></div>
			</div>
		</div>
		<div class="boxouter" style="float: left; margin-right: 10px;">
			<h2>Address Information</h2>
			<div class="box_white">
				<po:addressForm formNameBase="searchPersonForm"
					addressKeyBase="criteria.person.postalAddress"
					address="${criteria.person.postalAddress}" required="false" />
				<div class="clear"></div>
			</div>
            <h2>Pending Roles</h2>
            <div class="box_white">
                <s:checkbox label="Has Pending CRS Role(s)" name="criteria.hasPendingCrsRoles" labelposition="right" />
                <s:checkbox label="Has Pending HCP Role(s)" name="criteria.hasPendingHcpRoles" labelposition="right" />
                <s:checkbox label="Has Pending OC Role(s)" name="criteria.hasPendingOcRoles" labelposition="right" />                            
                <s:checkbox label="Has Pending OPI Role(s)" name="criteria.hasPendingOpiRoles" labelposition="right" />
                <div class="clear"></div>
            </div>			
		</div>
	</div>

	<div class="clearfloat"></div>
</div>