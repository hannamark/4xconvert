package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.ResearchOrganizationBoService;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

import static org.mockito.Mockito.mock;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ResearchOrganizationServiceImplTest extends AbstractRoleServiceTest
        <
                ResearchOrganization,
                ResearchOrganizationDTO,
                gov.nih.nci.po.data.bo.ResearchOrganization
                > {


    @Override
    protected void initService() {
       this.service = new ResearchOrganizationServiceImpl((ResearchOrganizationBoService) this.boService);
    }

    @Override
    protected ResearchOrganization getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension("1");

        ResearchOrganization researchOrganization = new ResearchOrganization();


        researchOrganization.setPlayerIdentifier(player);

        CD typeCd = new CD();
        typeCd.setCode("defaultType");
        researchOrganization.setTypeCode(typeCd);

        researchOrganization.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return researchOrganization;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.ResearchOrganization>> getBoServiceClass() {
        return ResearchOrganizationBoService.class;
    }


    @Override
    protected void setId(gov.nih.nci.po.data.bo.ResearchOrganization instance, long id) {
        instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.ResearchOrganization getBoInstance() {
        return new gov.nih.nci.po.data.bo.ResearchOrganization();
    }
}
