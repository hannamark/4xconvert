package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationalContactBoServiceTest extends AbstractRoleBoServiceTest<OrganizationalContact, OrganizationalContactCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new OrganizationalContactBoService();
    }

    @Override
    protected OrganizationalContact getBasicModel() {
        return ModelUtils.getBasicOrganizationalContact();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<OrganizationalContact> getEjbService() {
        return serviceLocator.getOrganizationalContactService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<OrganizationalContactCR> getCrService() {
        return serviceLocator.getOrganizationalContactCRService();
    }


}
