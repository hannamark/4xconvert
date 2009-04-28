package gov.nih.nci.pa.report.service.criteria;

import gov.nih.nci.coppa.iso.Bl;

/**
 * @author Hugh Reinhart
 * @since 03/19/2009
 */
public class TrialListCriteriaDto {
    private Bl ctrpOnly;

    /**
     * @return ctrpOnly
     */
    public Bl getCtrpOnly() {
        return ctrpOnly;
    }

    /**
     * @param ctrpOnly ctrpOnly
     */
    public void setCtrpOnly(Bl ctrpOnly) {
        this.ctrpOnly = ctrpOnly;
    }
}
