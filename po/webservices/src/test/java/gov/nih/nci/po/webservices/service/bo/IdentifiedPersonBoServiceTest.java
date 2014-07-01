package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class IdentifiedPersonBoServiceTest extends AbstractRoleBoServiceTest<IdentifiedPerson, IdentifiedPersonCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new IdentifiedPersonBoService();
    }

    @Override
    protected IdentifiedPerson getBasicModel() {
        return ModelUtils.getBasicIdentifiedPerson();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<IdentifiedPerson> getEjbService() {
        return serviceLocator.getIdentifiedPersonService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<IdentifiedPersonCR> getCrService() {
        return serviceLocator.getIdentifiedPersonCRService();
    }

}
