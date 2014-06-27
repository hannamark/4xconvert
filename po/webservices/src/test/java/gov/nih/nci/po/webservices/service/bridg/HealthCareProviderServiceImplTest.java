package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.HealthCareProvider;
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
import gov.nih.nci.po.webservices.service.bo.HealthCareProviderBoService;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

import java.util.HashSet;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareProviderServiceImplTest extends AbstractRoleServiceTest
    <     HealthCareProvider,
            HealthCareProviderDTO,
            gov.nih.nci.po.data.bo.HealthCareProvider
            > {

    @Override
    protected void initService() {
        this.service = new HealthCareProviderServiceImpl((HealthCareProviderBoService) this.boService);
    }

    @Override
    protected HealthCareProvider getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.PERSON_ROOT);
        player.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        player.setExtension("1");

        II scoper = new II();
        scoper.setRoot(IdConverter.ORG_ROOT);
        scoper.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        scoper.setExtension("2");


        HealthCareProvider healthCareProvider = new HealthCareProvider();

        healthCareProvider.setPlayerIdentifier(player);
        healthCareProvider.setScoperIdentifier(scoper);

        DSet<Tel> telSet = new DSet<Tel>();
        telSet.setItem(new HashSet<Tel>());
        ModelUtils.ContactSpec contactSpec = ModelUtils.ContactSpec.newInstance()
                .withEmail("bob@foo.org")
                .withPhone("123-456-7890")
                .withUrl("http://www.example.org");

        try {
            healthCareProvider.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(contactSpec.asDset()));
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }


        Address address = ModelUtils.getBasicAddress();
        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        HashSet<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        healthCareProvider.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));
        healthCareProvider.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return healthCareProvider;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.HealthCareProvider>> getBoServiceClass() {
        return HealthCareProviderBoService.class;
    }



    @Override
    protected void setId(gov.nih.nci.po.data.bo.HealthCareProvider instance, long id) {
       instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.HealthCareProvider getBoInstance() {
        return new gov.nih.nci.po.data.bo.HealthCareProvider();
    }
}
