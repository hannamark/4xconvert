package gov.nih.nci.pa.report.service.result;

import gov.nih.nci.coppa.iso.St;

/**
 * @author Hugh Reinhart
 * @since 03/19/2009
 */
public class TrialListResultDto {
    St officialTitle;

    /**
     * @return iso official title
     */
    public St getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle the iso official title
     */
    public void setOfficialTitle(St officialTitle) {
        this.officialTitle = officialTitle;
    }
}
