package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ClinicalResearchStaffBoServiceTest extends AbstractRoleBoServiceTest<ClinicalResearchStaff, ClinicalResearchStaffCR> {

    @Override
    protected void initServiceUnderTest() {
        this.service = new ClinicalResearchStaffBoService();
    }

    @Override
    protected ClinicalResearchStaff getBasicModel() {
        return ModelUtils.getBasicClinicalResearchStaff();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<ClinicalResearchStaff> getEjbService() {
        return serviceLocator.getClinicalResearchStaffService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<ClinicalResearchStaffCR> getCrService() {
        return serviceLocator.getClinicalResearchStaffCRService();
    }


}
