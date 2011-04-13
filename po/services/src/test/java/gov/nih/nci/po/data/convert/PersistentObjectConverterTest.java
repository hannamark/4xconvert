/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.Person;

import org.junit.Test;

/**
 * Tests for the persistent object converter.
 * @author Scott Miller
 */
public class PersistentObjectConverterTest {

    /**
     * Test that we get the correct exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedType() {
        new PersistentObjectConverter().convert(String.class, null);
    }

    /**
     * Test converting a null object .
     */
    @Test
    public void testConvertNullObject() {
        Ii id = new PersistentObjectConverter().convert(Ii.class, null);
        assertEquals(id.getNullFlavor(), NullFlavor.NI);
    }

    /**
     * test converting an object with an null id.
     */
    @Test
    public void testConvertNullId() {
        Address ad = new Address();
        ad.setId(null);
        Ii id = new PersistentObjectConverter().convert(Ii.class, ad);
        assertEquals(id.getNullFlavor(), NullFlavor.NI);
    }

    /**
     * Test converting an address.
     */
    @Test
    public void testConvertAddress() {
        Address ad = new Address();
        ad.setId(new Long(10));
        Ii id = new PersistentObjectConverter().convert(Ii.class, ad);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), null);
        assertEquals(id.getRoot(), null);
    }

    /**
     * Test converting a person.
     */
    @Test
    public void testConvertPerson() {
        Person p = new Person();
        p.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentPersonConverter().convert(Ii.class, p);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.PERSON_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.PERSON_ROOT);
    }
    
    /**
     * Test converting a patient person.
     */
    @Test
    public void testConvertPatient() {
        Patient p = new Patient();
        p.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentPatientConverter().convert(Ii.class, p);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals("10", id.getExtension());
        assertEquals(id.getIdentifierName(), IdConverter.PATIENT_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.PATIENT_ROOT);
    }

    /**
     * Test converting an address.
     */
    @Test
    public void testConvertOrg() {
        Organization o = new Organization();
        o.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentOrgConverter().convert(Ii.class, o);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.ORG_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.ORG_ROOT);
    }
    
    /**
     * Test converting a family.
     */
    @Test
    public void testConvertFamily() {
        Family fam = new Family();
        fam.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentFamilyConverter().convert(Ii.class, fam);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.FAMILY_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.FAMILY_ROOT);
    }
    
    /**
     * Test converting a crs.
     */
    @Test
    public void testConvertCrs() {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentCRSConverter().convert(Ii.class, crs);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.CLINICAL_RESEARCH_STAFF_ROOT);
    }
    
    /**
     * Test converting a hcf.
     */
    @Test
    public void testConvertHcf() {
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentHCFConverter().convert(Ii.class, hcf);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.HEALTH_CARE_FACILITY_ROOT);
    }
    
    /**
     * Test converting a hcp.
     */
    @Test
    public void testConvertHcp() {
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentHCPConverter().convert(Ii.class, hcp);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.HEALTH_CARE_PROVIDER_ROOT);
    }
    
    /**
     * Test converting a ip.
     */
    @Test
    public void testConvertIp() {
        IdentifiedPerson ip = new IdentifiedPerson();
        ip.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentIPConverter().convert(Ii.class, ip);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.IDENTIFIED_PERSON_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.IDENTIFIED_PERSON_ROOT);
    }
    
    /**
     * Test converting a ip.
     */
    @Test
    public void testConvertIo() {
        IdentifiedOrganization io = new IdentifiedOrganization();
        io.setId(new Long(10));
        Ii id = new PersistentObjectConverter.PersistentIOConverter().convert(Ii.class, io);
        assertEquals(id.getNullFlavor(), null);
        assertTrue(id.getDisplayable());
        assertEquals(id.getScope(), IdentifierScope.OBJ);
        assertEquals(id.getReliability(), IdentifierReliability.ISS);
        assertEquals(id.getExtension(), "10");
        assertEquals(id.getIdentifierName(), IdConverter.IDENTIFIED_ORG_IDENTIFIER_NAME);
        assertEquals(id.getRoot(), IdConverter.IDENTIFIED_ORG_ROOT);
    }
}