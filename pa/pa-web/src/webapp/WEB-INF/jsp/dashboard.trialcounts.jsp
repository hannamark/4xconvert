<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="milestones_in_progress" class="trial_count_panel">
	<h3>Milestones in Progress</h3>
	<div>
		<table id="milestones_in_progress_table">
			<thead>
				<tr>
					<th>Milestone (Excluding on-hold)</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="on_hold_trials" class="trial_count_panel">
	<h3>On-Hold Trials</h3>
	<div>
		<table id="on_hold_trials_table">
			<thead>
				<tr>
					<th>On-Hold Reason</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="trial_dist" class="trial_count_panel">
	<h3>Trial Submission Distribution</h3>
	<div>
		<table id="trial_dist_table">
			<thead>
				<tr>
					<th>Business Days Since Trial Submission</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="abstractors_work" class="trial_count_panel">
	<h3>Abstractors Work in Progress</h3>
	<div>
		<table id="abstractors_work_table">
			<thead>
				<tr>
					<th>Abstractor (Role)</th>
					<th>Admin</th>
					<th>Scientific</th>
					<th>Admin &amp; Scientific</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th colspan="4">AD=Admin/SC=Scientific/AS=Admin
						&amp; Scientific/SU=Super Abstractor</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>



