package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractResearchOrganization;
import org.hibernate.validator.Validator;

import java.io.Serializable;

/**
 * Validates a Curatable instance.
 *
 * @author smatyas
 */
public class VaildResearchOrganizationTypeWithFundingMechanismValidator implements
        Validator<VaildResearchOrganizationTypeWithFundingMechanism>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(VaildResearchOrganizationTypeWithFundingMechanism arg0) {
        // noop
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object arg0) {
        return (arg0 instanceof AbstractResearchOrganization
                && isValid((AbstractResearchOrganization) arg0));
    }

    private boolean isValid(AbstractResearchOrganization aro) {
        return (
            (aro.getFundingMechanism() == null)
                ||
            (aro.getTypeCode() == null)
                ||
            (aro.getTypeCode() != null
                && !aro.getTypeCode().getFundingMechanisms().isEmpty()
                && aro.getTypeCode().getFundingMechanisms().contains(aro.getFundingMechanism())
            )
        );
    }
}
