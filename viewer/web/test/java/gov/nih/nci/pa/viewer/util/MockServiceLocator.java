/**
 *
 */
package gov.nih.nci.pa.viewer.util;

import gov.nih.nci.pa.report.service.AverageMilestoneLocal;
import gov.nih.nci.pa.report.service.SubmitterOrganizationLocal;
import gov.nih.nci.pa.report.service.TrialCountsLocal;
import gov.nih.nci.pa.report.service.TrialListLocal;
import gov.nih.nci.pa.report.service.TrialProcessingLocal;

/**
 * @author Hugh
 *
 */
public class MockServiceLocator implements ServiceLocator {

    public AverageMilestoneLocal getAverageMilestoneReportService() {
        return null;
    }

    public SubmitterOrganizationLocal getSubmitterOrganizationReportService() {
        return null;
    }

    public TrialCountsLocal getTrialCountsReportService() {
        return null;
    }

    public TrialListLocal getTrialListReportService() {
        return null;
    }

    public TrialProcessingLocal getTrialProcessingReportService() {
        return null;
    }

}
