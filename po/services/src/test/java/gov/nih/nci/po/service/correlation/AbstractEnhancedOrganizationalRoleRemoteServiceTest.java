package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

public abstract
    class AbstractEnhancedOrganizationalRoleRemoteServiceTest<T extends AbstractEnhancedOrganizationRoleDTO, CR extends CorrelationChangeRequest<?>>
    extends AbstractOrganizationalRoleRemoteServiceTest<T, CR> {


    @SuppressWarnings("unchecked")
    protected void fillInFields(T dto) throws Exception {
        Ii ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        dto.setPlayerIdentifier(ii);

        dto.setName(StringConverter.convertToSt("my name"));

        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:me@test.com"));
        tels.getItem().add(email);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:111-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-fax:222-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-tel:333-222-3333"));
        tels.getItem().add(phone);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.google.com"));
        tels.getItem().add(url);

        dto.setTelecomAddress(tels);

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality",
                "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(), getDefaultCountry().getName());
        dto.setPostalAddress(new DSet<Ad>());
        dto.getPostalAddress().setItem(Collections.singleton(ad));
    }

    @SuppressWarnings("unchecked")
    protected void setUpCorrelation2Address(T correlation1, T correlation2) {
        Ad ad = (Ad) correlation1.getPostalAddress().getItem().iterator().next();
        for (Adxp part : ad.getPart()) {
            if (!part.getType().equals(AddressPartType.CNT)) {
                part.setValue(part.getValue()+ "2");
            }
        }
        correlation2.getPostalAddress().setItem(Collections.singleton(ad));
    }

    protected void setUpCorrelation2Tels(T correlation2) throws Exception {
        for (Tel t : correlation2.getTelecomAddress().getItem()) {
            if (t instanceof TelEmail || t instanceof TelUrl) {
                t.setValue(
                        new URI(t.getValue().toString() + "n"));
            } else {
                t.setValue(new URI(t.getValue().toString() + "2"));
            }
        }
    }

    protected abstract void modifySubClassSpecificFieldsForCorrelation2(T correlation2);

    @Override
    @SuppressWarnings("unchecked")
    public void testSearch() throws Exception {
        Organization org2 = new Organization();
        org2.setName("org2 name");
        org2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        org2.setStatusCode(EntityStatus.ACTIVE);
        org2.getEmail().add(new Email("foo@example.com"));
        org2.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().saveOrUpdate(org2);

        Person person2 = new Person();
        person2.setFirstName("fname2");
        person2.setLastName("lname2");
        person2.setMiddleName("mname2");
        person2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        person2.setStatusCode(EntityStatus.ACTIVE);
        person2.getEmail().add(new Email("foo@example.com"));
        person2.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().saveOrUpdate(person2);

        T correlation1 = getSampleDto();
        Ii id1 = getCorrelationService().createCorrelation(correlation1);

        T correlation2 = getSampleDto();
        correlation2.getPlayerIdentifier().setExtension("" + org2.getId());
        setUpCorrelation2Address(correlation1, correlation2);
        setUpCorrelation2Tels(correlation2);
        modifySubClassSpecificFieldsForCorrelation2(correlation2);
        Ii id2 = getCorrelationService().createCorrelation(correlation2);

        T searchCriteria = null;

        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        searchCriteria = getEmptySearchCriteria();
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // test search by primary id
        Ii id = new Ii();
        id.setExtension(id1.getExtension());
        id.setRoot(id1.getRoot());
        id.setIdentifierName(id1.getIdentifierName());
        id.setDisplayable(id1.getDisplayable());
        id.setReliability(id1.getReliability());
        id.setScope(id1.getScope());

        searchCriteria.setIdentifier(IiConverter.convertToDsetIi(id));
        List<T> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        searchCriteria.getIdentifier().getItem().iterator().next().setExtension(id2.getExtension());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        searchCriteria.setIdentifier(null);
        searchCriteria.setPlayerIdentifier(correlation1.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        // test search by address
        Ad ad = (Ad) correlation2.getPostalAddress().getItem().iterator().next();
        searchCriteria.setPlayerIdentifier(null);
        searchCriteria.setPostalAddress(new DSet<Ad>());
        searchCriteria.getPostalAddress().setItem(Collections.singleton(ad));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        // remove 1 part of address at a time making sure the search still works
        while (ad.getPart().size() > 1) {
            Adxp part = ad.getPart().remove(0);
            if (part.getType().equals(AddressPartType.CNT)) {
                // remove country last
                ad.getPart().remove(0);
                ad.getPart().add(part);
            }
            results = getCorrelationService().search(searchCriteria);
            if (ad.getPart().size() == 1) {
                // when on just country there are 2 results
                assertEquals(2, results.size());
            } else {
                assertEquals(1, results.size());
                assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());
            }
        }

        // no parts left of address, so there should be no criteria.
        try {
            ad.getPart().remove(0);
            results = getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // verify the starts with search of all address parts
        Ad cor1Ad = (Ad) getSampleDto().getPostalAddress().getItem().iterator().next();
        searchCriteria.setPostalAddress(new DSet<Ad>());
        searchCriteria.getPostalAddress().setItem(Collections.singleton(cor1Ad));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());

        for (Adxp part : cor1Ad.getPart()) {
            if (!part.getType().equals(AddressPartType.CNT)) {
                part.setValue(part.getValue().toUpperCase() + "2");

                results = getCorrelationService().search(searchCriteria);
                assertEquals(1, results.size());
                assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

                part.setValue(part.getValue().substring(0, part.getValue().length() - 1).toUpperCase());

                results = getCorrelationService().search(searchCriteria);
                assertEquals(2, results.size());
            }
        }

        // search by status
        searchCriteria.setPostalAddress(null);
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.PENDING));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.ACTIVE));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // search by telco
        searchCriteria.setStatus(null);
        searchCriteria.setTelecomAddress(new DSet<Tel>());
        searchCriteria.getTelecomAddress().setItem(new HashSet<Tel>());
        try {
            results = getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }
        for (Tel t : correlation1.getTelecomAddress().getItem()) {
            searchCriteria.getTelecomAddress().getItem().add(t);
            results = getCorrelationService().search(searchCriteria);
            assertEquals(2, results.size());
        }
        searchCriteria.getTelecomAddress().getItem().clear();
        for (Tel t : getSampleDto().getTelecomAddress().getItem()) {
            if (t instanceof TelEmail || t instanceof TelUrl) {
                t.setValue(
                        new URI(t.getValue().toString() + "n"));
            } else {
                t.setValue(new URI(t.getValue().toString() + "2"));
            }
            searchCriteria.getTelecomAddress().getItem().add(t);
            results = getCorrelationService().search(searchCriteria);
            assertEquals(1, results.size());
            assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

            searchCriteria.getTelecomAddress().getItem().remove(t);
        }

        searchCriteria.getTelecomAddress().getItem().clear();
        testSearchOnSubClassSpecificFields(correlation1, id2, searchCriteria);
    }

    abstract protected void testSearchOnSubClassSpecificFields(T correlation1, Ii id2, T searchCriteria)
            throws NullifiedRoleException;

}
