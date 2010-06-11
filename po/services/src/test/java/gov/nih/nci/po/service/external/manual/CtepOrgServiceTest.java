package gov.nih.nci.po.service.external.manual;

import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.external.CtepImportServiceBean;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.service.external.CtepUtils;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

public class CtepOrgServiceTest {

    private static OrganizationService organizationService;

    String[] HCF_IDS = new String[] { "MD116", "NY304", "IA065", "11080", "AZ098", "NY344", "NY034", "CA469", "25011",
            "CA323" };

    String[] RO_IDS = new String[] { "BNR", "LEST", "CTX", "NANT", "AOIP", "MBS", "APH", "TRM", "CDG", "BER", "RSB013" };

    @BeforeClass
    public static void initImportTests() throws NamingException {
        Context ctepContext = CtepImportServiceBean.createCtepInitialContext();
        organizationService = (OrganizationService) ctepContext.lookup("OrganizationService");
    }

    @Test
    public void getHCFs() throws CTEPEntException {
        for (String id : HCF_IDS) {
            Ii ii = new Ii();
            ii.setExtension(id);
            ii.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
            HealthCareFacilityDTO hcf = organizationService.getHealthCareFacility(ii);
            print(hcf);
        }
    }

    @Test
    public void getROs() throws CTEPEntException {
        for (String id : RO_IDS) {
            Ii ii = new Ii();
            ii.setExtension(id);
            ii.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
            ResearchOrganizationDTO hcf = organizationService.getResearchOrganization(ii);
            print(hcf);
        }
    }

    private void print(HealthCareFacilityDTO hcf) {
        System.out.println(CtepUtils.toString(hcf));
    }
    private void print(ResearchOrganizationDTO hcf) {
        System.out.println(CtepUtils.toString(hcf));
    }
}
