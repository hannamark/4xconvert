/**
 * 
 */
package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author dkrylov
 * 
 */
public final class ParticipatingSiteMgmtServiceTest extends
        AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp("/trials/nci/NCI-2014-00496/sites");
        deactivateAllTrials();
    }

    @Test
    public void testInvalidCredentials() throws Exception {
        super.testInvalidCredentials();
    }

    @Test
    public void testValidCredentialsButNoRole() throws Exception {
        super.testValidCredentialsButNoRole();
    }

    @Test
    public void testAddSite() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);

        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        assertEquals(TEXT_PLAIN, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        verifySite(rConf, siteID, upd);
    }

    private void verifySite(TrialRegistrationConfirmation rConf, long siteID,
            ParticipatingSite ps) {
        clickAndWait("link=Participating Sites");
        clickAndWait("xpath=//table[@id='row']/tbody/tr[1]//td[7]//img");

        if (ps.getOrganization().getExistingOrganization() != null) {
            assertEquals(ps.getOrganization().getExistingOrganization()
                    .getPoID().toString(),
                    selenium.getValue("id=editOrg.identifier"));
        } else {
            assertEquals(ps.getOrganization().getNewOrganization().getName(),
                    selenium.getValue("id=editOrg.name"));
        }

        assertEquals(ps.getLocalTrialIdentifier(),
                selenium.getValue("id=siteLocalTrialIdentifier"));
        assertEquals(ps.getRecruitmentStatus().value(),
                selenium.getValue("id=recStatus"));
        assertEquals(DateFormatUtils.format(ps.getRecruitmentStatusDate()
                .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=recStatusDate"));
        assertEquals(ps.getProgramCode(), selenium.getValue("id=programCode"));
        assertEquals(ps.getTargetAccrualNumber().toString(),
                selenium.getValue("id=targetAccrualNumber"));
        assertEquals(
                DateFormatUtils.format(ps.getOpenedForAccrual()
                        .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=participatingOrganizationsedit_dateOpenedForAccrual"));
        assertEquals(
                DateFormatUtils.format(ps.getClosedForAccrual()
                        .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=participatingOrganizationsedit_dateClosedForAccrual"));

        selenium.click("link=Investigators");
        assertEquals(
                ps.getInvestigator().get(0).getPerson().getExistingPerson()
                        .getPoID().toString(),
                selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[1]/a"));
        assertEquals(
                ps.getInvestigator().get(0).getRole(),
                selenium.getText("xpath=//div[@id='saveAndShowContacts']//table[@id='row']//tr[1]//td[4]"));
        if (ps.getInvestigator().get(0).isPrimaryContact()) {
            selenium.click("link=Contact");
            assertEquals(ps.getInvestigator().get(0).getPerson()
                    .getExistingPerson().getPoID().toString(),
                    selenium.getValue("id=personContactWebDTO.selectedPersId"));
        }

    }

    @SuppressWarnings("unchecked")
    protected HttpResponse addSite(String idType, String trialID,
            ParticipatingSite o) throws ClientProtocolException, IOException,
            ParseException, JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<ParticipatingSite>(new QName(
                "gov.nih.nci.pa.webservices.types", "ParticipatingSite"),
                ParticipatingSite.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = submitEntityAndReturnResponse(entity,
                "/trials/" + idType + "/" + trialID + "/sites", TEXT_PLAIN,
                APPLICATION_XML);
        return response;

    }

    @SuppressWarnings("unchecked")
    protected ParticipatingSite readParticipatingSiteFromFile(String string)
            throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        ParticipatingSite o = ((JAXBElement<ParticipatingSite>) u
                .unmarshal(url)).getValue();
        return o;
    }

}
