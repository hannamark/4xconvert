package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.HealthCareFacilityBoService;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

import java.util.HashSet;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareFacilityServiceImplTest extends AbstractRoleServiceTest
    <       HealthCareFacility,
            HealthCareFacilityDTO,
            gov.nih.nci.po.data.bo.HealthCareFacility
            >{


    @Override
    protected void initService() {
        this.service = new HealthCareFacilityServiceImpl((HealthCareFacilityBoService) this.boService);
    }

    @Override
    protected HealthCareFacility getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension("1");

        HealthCareFacility healthCareFacility = new HealthCareFacility();


        healthCareFacility.setPlayerIdentifier(player);

        DSet<Tel> telSet = new DSet<Tel>();
        telSet.setItem(new HashSet<Tel>());
        ModelUtils.ContactSpec contactSpec = ModelUtils.ContactSpec.newInstance()
                .withEmail("bob@foo.org")
                .withPhone("123-456-7890")
                .withUrl("http://www.example.org");

        try {
            healthCareFacility.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(contactSpec.asDset()));
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }


        Address address = ModelUtils.getBasicAddress();
        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        HashSet<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        healthCareFacility.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return healthCareFacility;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.HealthCareFacility>> getBoServiceClass() {
        return HealthCareFacilityBoService.class;
    }



    @Override
    protected void setId(gov.nih.nci.po.data.bo.HealthCareFacility instance, long id) {
        instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.HealthCareFacility getBoInstance() {
        return new gov.nih.nci.po.data.bo.HealthCareFacility();
    }
}
