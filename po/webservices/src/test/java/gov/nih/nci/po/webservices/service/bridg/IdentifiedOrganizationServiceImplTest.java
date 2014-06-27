package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.IdentifiedOrganizationBoService;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class IdentifiedOrganizationServiceImplTest extends AbstractRoleServiceTest
    <    IdentifiedOrganization,
            IdentifiedOrganizationDTO,
            gov.nih.nci.po.data.bo.IdentifiedOrganization
            >{

    @Override
    protected void initService() {
        this.service = new IdentifiedOrganizationServiceImpl((IdentifiedOrganizationBoService) this.boService);
    }

    @Override
    protected IdentifiedOrganization getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension("1");

        II scoper = new II();
        scoper.setRoot(IdConverter.ORG_ROOT);
        scoper.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        scoper.setExtension("2");

        II assignedIdentifier = new II();
        assignedIdentifier.setRoot(IdConverter.ORG_ROOT);
        assignedIdentifier.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        assignedIdentifier.setExtension("3");

        IdentifiedOrganization identifiedOrganization = new IdentifiedOrganization();


        identifiedOrganization.setPlayerIdentifier(player);
        identifiedOrganization.setScoperIdentifier(scoper);
        identifiedOrganization.setAssignedId(assignedIdentifier);

        identifiedOrganization.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return identifiedOrganization;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.IdentifiedOrganization>> getBoServiceClass() {
        return IdentifiedOrganizationBoService.class;
    }



    @Override
    protected void setId(gov.nih.nci.po.data.bo.IdentifiedOrganization instance, long id) {
       instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.IdentifiedOrganization getBoInstance() {
        return new gov.nih.nci.po.data.bo.IdentifiedOrganization();
    }
}
