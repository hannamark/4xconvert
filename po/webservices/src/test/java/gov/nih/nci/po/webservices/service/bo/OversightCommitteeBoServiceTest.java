package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OversightCommitteeBoServiceTest extends AbstractOrganizationRoleTest<OversightCommittee, OversightCommitteeCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new OversightCommitteeBoService();
    }

    @Override
    protected OversightCommittee getBasicModel() {
        return ModelUtils.getBasicOversightCommittee();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<OversightCommittee> getEjbService() {
        return serviceLocator.getOversightCommitteeService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<OversightCommitteeCR> getCrService() {
        return serviceLocator.getOversightCommitteeCRService();
    }


}
