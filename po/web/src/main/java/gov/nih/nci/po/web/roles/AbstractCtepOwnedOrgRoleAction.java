package gov.nih.nci.po.web.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;

/**
 * Used to capture common logic for CTEP-owned organizational role(s).
 * @author smatyas
 *
 * @param <ROLE>
 * @param <ROLECR>
 * @param <ROLESERVICE>
 */
public abstract class AbstractCtepOwnedOrgRoleAction<ROLE extends Correlation, 
ROLECR extends CorrelationChangeRequest<ROLE>, 
ROLESERVICE extends GenericStructrualRoleServiceLocal<ROLE>>
    extends AbstractOrganizationRoleAction<ROLE, ROLECR, ROLESERVICE> {

    private static final long serialVersionUID = 1L;
    
    /**
     * @return the allowable RoleStatus values
     */
    @Override
    public Collection<RoleStatus> getAvailableStatus() {
        if (getBaseRole().getId() != null) {
            Collection<RoleStatus> allowedTransitions = new ArrayList<RoleStatus>(getBaseRole().getPriorStatus()
                    .getAllowedTransitions());
            allowedTransitions.remove(RoleStatus.ACTIVE);
            return allowedTransitions;
        }
        List<RoleStatus> set = new ArrayList<RoleStatus>();
        set.add(RoleStatus.PENDING);
        return set;
    }
}
